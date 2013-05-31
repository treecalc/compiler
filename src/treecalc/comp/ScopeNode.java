package treecalc.comp; 

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import treecalc.comp.ModelSimple.NodeData;
import treecalc.comp.ResolvedCalc.ResolveType;

import static treecalc.comp.TcSimpleParser.*;

public class ScopeNode implements Scope {
	private final String id;
	private final String name;
	private final Scope parent; //global scope to resolve function names etc.
	private final ScopeNode parentNode;
	private TcAst astInclusion;
	private TcAst astTimes;
	private int   symbolcounter=0;
	
	private final ArrayList<ScopeNode> children = new ArrayList<ScopeNode>();
	private final ArrayList<ScopeNode> links    = new ArrayList<ScopeNode>();
	private final Map<String, Symbol>  defines  = new LinkedHashMap<String, Symbol>();
	
	public ScopeNode(String id, String name, ScopeNode parentNode, Scope parent) {
		this.id = id;
		this.name = name;
		this.parentNode = parentNode;
		this.parent = parent;
		if (parentNode!=null) {
			parentNode.children.add(this);
		}
	}
	public ScopeNode findFirst(String[] path, int[] tokentypes) {
		return findFirst(path, tokentypes, 0);
	}

	private ScopeNode findFirst(String[] path, int[] tokentypes, int index) {
		if (path==null) {
			return null;
		}
		if (path.length<=index) {
			return this;
		}
		String linkpath = path[index];
		int tokentype = tokentypes[index];
		switch(tokentype) {
		case ID: {
			for (ScopeNode child : children) {
				if (linkpath.equalsIgnoreCase(child.id)) {
					return child.findFirst(path, tokentypes, index+1);
				}
			}
			break;
		}
		case STRING: {
			for (ScopeNode child : children) {
				if (linkpath.equalsIgnoreCase(child.name)) {
					return child.findFirst(path, tokentypes, index+1);
				}
			}
			break;
		}
		case ASTERISK: {
			for (ScopeNode child : children) {
				ScopeNode found = child.findFirst(path, tokentypes, index+1);
				if (found!=null) {
					return found;
				}
			}
			break;
		}
		case DOUBLEASTERISK: {
			ScopeNode found = findFirst(path, tokentypes, index+1);
			if (found!=null) {
				return found;
			}
			/* notfound: lookup in children with unchanged path, stop on first success */
			for (ScopeNode child : children) {
				found = child.findFirst(path, tokentypes, index);
				if (found!=null) {
					return found;
				}
			}
//			String next = path[index+1];
//			int nexttokentype = tokentypes[index+1];
//			switch(nexttokentype) {
//			case ID: {
//				if (next.equals(id)) {
//					ScopeNode found = findFirst(path, tokentypes, index+2);
//					if (found!=null) {
//						return found;
//					}
//				}
//				break;
//			}
//			case STRING: {
//				if (next.equals(name)) {
//					ScopeNode found = findFirst(path, tokentypes, index+2);
//					if (found!=null) {
//						return found;
//					}
//				}
//			}
//			break;
//			}
		}
		}
		return null;
	}
	public void addLink(ScopeNode linkedNode) {
		links.add(linkedNode);
	}
	public List<ScopeNode> getLinks() {
		return links;
	}
	
	public String getNodeId() {
		return id;
	}
	public String getNodeName() {
		return name;
	}
	public ScopeNode getParentNode() {
		return parentNode;
	}
	public List<ScopeNode> getChildren() {
		return children;
	}
	@Override
	public String getFullScopeName() {
		ArrayList<String> scopenames = new ArrayList<String>();
		for (Scope current = this; current!=null; current = current.getParent()) {
			scopenames.add(0, current.getScopeName());
		}
		return scopenames.toString();
	}

	@Override
	public String getScopeName() {
		return name;
	}

	@Override
	public void define(Symbol sym) {
		defines.put(sym.getSymbolName() + ':' + sym.getNumArgs(), sym);
		sym.setScope(this);
	}

	@Override
	public Symbol resolve(String name, int nargs, boolean selfcalccall) {
		Symbol symbol = null;
		if (!(nargs==0 && selfcalccall)) { // P_Self = P_Self   ->  take P_Self from child nodes!!; P_Fact(n) = n<=0 ? 1 : P_Fact(n-1) is ok 
			symbol = defines.get(name + ':' + nargs);
			if (symbol!=null) {
				return symbol;
			}
		}
		for (ScopeNode child : children) {
			symbol = child.resolve(name, nargs, false);
			if (symbol!=null) {
				return symbol;
			}
		}
		for (ScopeNode link : links) {
			symbol = link.resolve(name, nargs, false);
			if (symbol!=null) {
				return symbol;
			}
		}
		if (parent!=null) {
			return parent.resolve(name, nargs, false);
		}
		return null;
	}

	@Override
	public ResolvedCalc resolveCalc(ModelSimple model, String calcname, int nargs, boolean selfcalccall) {
		String calcnameUpper = calcname.toUpperCase();
		int calcid = model.getCalcindex(calcnameUpper, nargs);
		ResolvedCalc ret = new ResolvedCalc();
		int nodeindex = model.getNodeData(this).nodeindex;
		ret.setScopeNodeStart(nodeindex);
		ArrayList<Integer> pathstart = new ArrayList<Integer>();
		boolean found = resolveCalcInternal(model, calcnameUpper, nargs, calcid, ret, false, nodeindex, selfcalccall, pathstart);
		if (found) {
			ret.inferSumNode(model);
		} else {
			throw new RuntimeException("could not resolveCalc " + calcname + " in scope " + this.toString());
		}
		return ret;
	}
	private boolean resolveCalcInternal(ModelSimple model, String calcname, int nargs, int calcid, ResolvedCalc ret, boolean inlink, int lastNodeInTree, boolean selfcalccall, ArrayList<Integer> path) {
		NodeData nodeData = model.getNodeData(this);
		int nodeindex = nodeData.nodeindex;
		
		if (!(nargs==0 && selfcalccall)) { // P_Self = P_Self   ->  take P_Self from child nodes!!; P_Fact(n) = n<=0 ? 1 : P_Fact(n-1) is ok 
			Symbol symbol = defines.get(calcname + ':' + nargs);
			if (symbol!=null) {
				ret.addSubnode(nodeData.nodeindex, path);
				return true;
			}
		}

		path.add(nodeindex);
		boolean found=false;
		for (ScopeNode child : children) {
			boolean foundinchild = child.resolveCalcInternal(model, calcname, nargs, calcid, ret, inlink, inlink ? lastNodeInTree : nodeindex, false, path);
			found = found || foundinchild;
		}
		/* lookup in links -> but don't add node id's from links but only the id of the last node
		 * above the links. Otherwise finding a common parent will not work
		 * TODO see if that can be done better
		 */
		boolean foundinlink = false;
		for (ScopeNode link : links) {
			boolean foundinchild = link.resolveCalcInternal(model, calcname, nargs, calcid, ret, true, inlink ? lastNodeInTree : nodeindex, false, path);
			foundinlink = foundinlink || foundinchild;
		}
		found = found | foundinlink;
		
		path.remove(path.size()-1);
		return found;
	}
	@Override
	public ScopeNode getScopeNode() {
		return this;
	}
	
	@Override
	public List<Symbol> getScopeDefines() {
		return new ArrayList<Symbol>(defines.values());
	}

	@Override
	public Scope getParent() {
		return parent; 
	}
	public TcAst getAstInclusion() {
		return astInclusion;
	}
	public void setAstInclusion(TcAst astInclusion) {
		this.astInclusion = astInclusion;
	}
	public TcAst getAstTimes() {
		return astTimes;
	}
	public void setAstTimes(TcAst astTimes) {
		this.astTimes = astTimes;
	}
	
	@Override
	public int getNewSymbolId() {
		return symbolcounter++;
	}
	@Override
	public int getSymbolIdCounter() {
		return symbolcounter;
	}
	
}
