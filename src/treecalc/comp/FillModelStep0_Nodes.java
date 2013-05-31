/**
 * build scope tree(s) for node structure and resolve links
 * Scope is set at least for
 * - TREE
 * - NODE
 * - TT_INCLUSIONFORMULA and formula therof
 * - TT_TIMESINFO and formula thereof (and multiple-id-symbol created in global scope)
 */


package treecalc.comp; 

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.antlr.runtime.CommonToken;

import static treecalc.comp.TcSimpleParser.*;

@SuppressWarnings("unchecked")
public class FillModelStep0_Nodes {
	private final ModelSimple model;
	private ScopeNode currentnodescope;
	private boolean firstTree=true;
	
	private FillModelStep0_Nodes(ModelSimple model) {
		this.model = model;
	}

	private void run() {
		currentnodescope = model.maintreeScope;
		visitNodes(model.getRoot());
		visitLinks(model.getRoot());
	}

	private void visitNodes(TcAst node) {
		int tokentype = node.getType();
		switch (tokentype) {
		case TT_COMPUNIT: {
			List<TcAst> children = node.getChildren();
			for (TcAst child : children) {
				visitNodes(child);
			}
			break;
		}
		case KEYWORD_TREE: {
			// ^(KEYWORD_TREE nodepath nodeinfo*)
			if (firstTree) {
				currentnodescope = model.maintreeScope;
				firstTree=false;
			} else {
				currentnodescope = new ScopeNode("tree", "tree", null, model.globalScope); //prepare access to global symbols:)
			}
			node.setScope(currentnodescope);
			TcAst nodepath = (TcAst) node.getChild(0);
			//TT_NODEPATH
			visitNodes(nodepath);
			for (int i=1; i<node.getChildCount(); i++) {
				TcAst nodeinfo = (TcAst) node.getChild(i);
				// KEYWORD_NODE oder KEYWORD_LINK
				visitNodes(nodeinfo);
			}
			ModelTree tree = new ModelTree();
			String[] np = new String[nodepath.getChildCount()];
			for (int i=0; i<nodepath.getChildCount(); i++) {
				np[i] = nodepath.getChild(i).getText();
			}
			tree.setId(np);
			tree.setRootnode((ScopeNode) node.getScope());
			model.trees.add(tree);
			
			currentnodescope = null;
			break;
		}
		case KEYWORD_NODE: {
			// ^(KEYWORD_NODE nodename ^(KEYWORD_AS id)? ^(TT_INCLUSIONFORMULA formula)? ^(TT_TIMESINFO formula id)? nodeinfo*)
			TcAst astnodename = (TcAst) node.getChild(0);
			int nodeind=1;
			//constant|id

			TcAst act = nodeind<node.getChildCount() ? (TcAst) node.getChild(nodeind) : null;
			TcAst astnodeid=null;
			if (act!=null && act.getType()==KEYWORD_AS) {
				//^(KEYWORD_AS id)
				astnodeid = (TcAst) act.getChild(0);
				nodeind++;
				act = nodeind<node.getChildCount() ? (TcAst) node.getChild(nodeind) : null;
			} 
			String nodename = astnodename.getText();
			String nodeid    = astnodeid!=null ? astnodeid.getText() : nodename;
			
			ScopeNode newscope = new ScopeNode(nodeid, nodename, currentnodescope, model.globalScope);
			node.setScope(newscope);
			currentnodescope = newscope;

			
			if (act!=null && act.getType()==TT_INCLUSIONFORMULA) {
				TcAst inclusion = act;
				newscope.setAstInclusion(inclusion);
				visitNodes(inclusion);
				nodeind++;
				act = nodeind<node.getChildCount() ? (TcAst) node.getChild(nodeind) : null;
			}
			if (act!=null && act.getType()==TT_TIMESINFO) {
				TcAst timesinfo = act;
				newscope.setAstTimes(timesinfo);
				visitNodes(timesinfo);
				nodeind++;
				act = nodeind<node.getChildCount() ? (TcAst) node.getChild(nodeind) : null;
			}
			
			
			if (nodeind<node.getChildCount()) {
				for (int i=nodeind; i<node.getChildCount(); i++) {
					TcAst nodeinfo = (TcAst) node.getChild(i);
					// KEYWORD_NODE oder KEYWORD_LINK
					visitNodes(nodeinfo);
				}
			} 
			currentnodescope = currentnodescope.getParentNode();
			break;
		}
		case  TT_INCLUSIONFORMULA: {
			//^(TT_INCLUSIONFORMULA formula)
			TcAst formula = (TcAst) node.getChild(0);
			TcAst parent = (TcAst) node.getParent();
			node.setScope(parent.getScope());
			formula.setScope(parent.getScope());
			break;
		}
		case TT_TIMESINFO: {
			//^(TT_TIMESINFO formula id)
			TcAst parent = (TcAst) node.getParent();
			node.setScope(parent.getScope());
			TcAst formula = (TcAst) node.getChild(0);
			formula.setScope(node.getScope());
			TcAst id  = (TcAst) node.getChild(1);
			String varname = id.getText();
			Symbol multiplesymbol = new SymbolImpl(varname, Symbol.SYMBOLTYPE_TIMESINDEX, 0);
			model.putTimesName(varname);
			multiplesymbol.setScope(model.globalScope);
			model.globalScope.define(multiplesymbol);
			break;
			
		}
		case TT_NODEPATH: {
			node.setScope(currentnodescope);
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child = (TcAst) node.getChild(i);
				String id = child.getText();

				ScopeNode newscope = new ScopeNode(id, id, currentnodescope, model.globalScope);
				node.setScope(newscope);
				currentnodescope = newscope;
			}
		}
		} // end of switch
	}
	
	private Object visitLinks(TcAst node) {
		int tokentype = node.getType();
		switch (tokentype) {
		case TT_COMPUNIT: {
			List<TcAst> children = node.getChildren();
			for (TcAst child : children) {
				visitLinks(child);
			}
			//^(KEYWORD_FUNC ^(TT_RESULTDEF ID arguments? formula))
			CommonToken tokenFunc = new CommonToken(KEYWORD_FUNC);
			CommonToken tokenResultdef = new CommonToken(TT_RESULTDEF);
			CommonToken tokenId = new CommonToken(ID, "$VERSION");
			CommonToken tokenConst = new CommonToken(STRING, "TcGen " + new SimpleDateFormat("dd.MM.yyyy kk:mm:ss").format(new GregorianCalendar().getTime()));
			TcAst astConst = new TcAst(tokenConst);
			TcAst astId    = new TcAst(tokenId);
			TcAst astResultdef = new TcAst(tokenResultdef);
			astResultdef.addChild(astId);
			astResultdef.addChild(astConst);
			TcAst astFunc = new TcAst(tokenFunc);
			astFunc.addChild(astResultdef);
			node.addChild(astFunc);
			break;
		}
		case KEYWORD_TREE: {
			// ^(KEYWORD_TREE nodepath nodeinfo*)
			for (int i=1; i<node.getChildCount(); i++) {
				TcAst nodeinfo = (TcAst) node.getChild(i);
				// KEYWORD_NODE oder KEYWORD_LINK
				visitLinks(nodeinfo);
			}
			break;
		}
		case KEYWORD_NODE: {
			// ^(KEYWORD_NODE nodename ^(KEYWORD_AS id)? ^(TT_INCLUSIONFORMULA formula)? ^(TT_TIMESINFO formula id)? nodeinfo*)
			for (int i=1; i<node.getChildCount(); i++) {
				TcAst child = (TcAst) node.getChild(i);
				// KEYWORD_AS or TT_INCLUSIONFORMULA or TT_TIMESINFO or nodeinfo
				visitLinks(child);
			}
			break;
		}
		case KEYWORD_LINK: {
			// ^(KEYWORD_LINK  nodepath)
			TcAst parent = (TcAst) node.getParent();
			ScopeNode parentNode = (ScopeNode) parent.getScope();
			TcAst nodepath = (TcAst) node.getChild(0);
			Object[] linkret = (Object[]) visitLinks(nodepath);
			String[] link = (String[]) linkret[0];
			int[]  linktype = (int[]) linkret[1];
			/* find tree */
			ModelTree tree = findTree(model.trees, link);
			if (tree==null) {
				findTree(model.trees, link);
				throw new RuntimeException("did not find tree for link '" + Arrays.toString(link) + "'");
			}
			ScopeNode rootNode = tree.getRootnode();
			ScopeNode scopeNode = rootNode.findFirst(link, linktype);
			if (scopeNode==null) {
				throw new RuntimeException("could not resolve link " + Arrays.toString(link) + " in line " + node.getLine());
			} 
			//				System.out.println("resolved link " + Arrays.toString(link));
			node.setScope(scopeNode);
			parentNode.addLink(scopeNode);
			break;
		}
		case TT_NODEPATH: {
			String[] np = new String[node.getChildCount()];
			int[] nptype = new int[node.getChildCount()];
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child = (TcAst) node.getChild(i);
				// ID | STRING | ASTERISK | DOUBLEASTERISK
				nptype[i] = child.getType();
				np[i] = child.getText();
			}
			return new Object[] { np, nptype };
		}
		} // end of switch
		return null;
	}

	private ModelTree findTree(ArrayList<ModelTree> trees, String[] link) {
		for (ModelTree tree : trees) {
			String[] candidate = tree.getId();
			int len = candidate.length;
			if (link.length<len) {
				continue;
			}
			boolean bingo=true;
			for (int i=0; i<len; i++) {
				if (!link[i].equalsIgnoreCase(candidate[i])) { 
					bingo=false;
					break;
				}
			}
			if (bingo) {
				return tree;
			}
		}
		return null;
	}

	public static void build(ModelSimple model) {
		FillModelStep0_Nodes obj = new FillModelStep0_Nodes(model);
		obj.run();
	}
}
