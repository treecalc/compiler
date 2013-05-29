/* use symbol tables / scopes to resolve names */

package treecalc.comp.vm; 

import static treecalc.comp.TcSimpleParser.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import treecalc.comp.GenNodeCalc;
import treecalc.comp.ModelSimple;
import treecalc.comp.ResolvedCalc;
import treecalc.comp.Scope;
import treecalc.comp.ScopeNode;
import treecalc.comp.ScopedSymbol;
import treecalc.comp.Symbol;
import treecalc.comp.TcAst;
import treecalc.comp.ModelSimple.NodeData;


public class VmNodes {
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private PrintStream out;
	
	private VmNodes(ModelSimple model, String foldernameout, String packagename) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename   = packagename;
	}

	private void run() throws IOException {
		out = new PrintStream(new FileOutputStream(foldernameout + "/" + packagename + ".tci", true), true, "ISO-8859-1");
		printCalc();
		printNodes();
		out.close();
	}

	private void printNodes() throws IOException {
		out.print(".nodes size=");
		out.println(model.getNodeDataSize());
		for (int i=0; i<model.getNodeDataSize(); i++) {
			printNode(model.getNodeData(i), i);
		}
	}

	private void printNode(ModelSimple.NodeData nodeData, int index) throws IOException {
		ScopeNode node = nodeData.node;
		List<Symbol> defines = node.getScopeDefines();
		/* node index, id, name */
		out.print(".node node=");
		out.print(index);
		out.print(" id=");
		out.print(node.getNodeId());
		if (!node.getNodeId().equals(node.getNodeName())) {
			out.print(" name=\"");
			out.print(node.getNodeName());
			out.print("\"");
		}
		out.print(" ;");
		printNodenameAbsolute(nodeData.node);
		out.println();

		/*
		 *   - inclusion    formula_id
		 *   - timesid      u4 (0=no!) 
		 *   - timesformula formula_id
		 *   - own   calcs: bitvector, u(calc number DIV 8)
		 *   - total calcs: bitvector, u(calc number DIV 8)
		 */
		
		TcAst inclusion = node.getAstInclusion();
		TcAst times = node.getAstTimes();
		if (inclusion!=null || times!=null) {
			out.print(".nodeformulas");
			out.print(" node=");
			out.print(nodeData.nodeindex);
			if (inclusion==null) {
			} else {
				TcAst formula = (TcAst) inclusion.getChild(0);
				int id = model.putFormula(formula);
				out.print(" inclusion=");
				out.print(id);
			}
			if (times==null) {
			} else {
				TcAst formula = (TcAst) times.getChild(0);
				int id = model.putFormula(formula);
				TcAst astTimesId = times.getChild(1);
				String timesname = astTimesId.getText().toUpperCase();
				int timesid = model.getTimesId(timesname);
				out.print(" timesformula=");
				out.print(id);
				out.print(" timesid=");
				out.print(timesid);
			}
			out.println();
		}
		for (Symbol symbol : defines) {
			if (symbol.getSymboltype()==Symbol.SYMBOLTYPE_NODERESULTDEF) {
				processCalc(symbol, nodeData);
			} else {
				out.println("   //unexpected symbol: " + symbol.getSymbolName() + ", type " + symbol.getSymboltype());
			}
		}
		printBitset(".calcdef", nodeData, nodeData.calcsOwnnode);
		printBitset(".calctotal", nodeData, nodeData.calcs);
		printEdge1(nodeData);
		
	}
	
	private void processCalc(Symbol symbol, ModelSimple.NodeData nodeData) {
		int nodeindex = nodeData.nodeindex;
		if (!(symbol instanceof ScopedSymbol)) {
			throw new RuntimeException("expected ScopedSymbol, but got " + symbol.getClass()+ " for symbol " + symbol.getSymbolName() + ", " + symbol.toString());
		}
		String name = symbol.getSymbolName();
		int    nargs = symbol.getNumArgs();
		ScopedSymbol calcsymbol = (ScopedSymbol) symbol;
		TcAst node = (TcAst) calcsymbol.getAst();

		List<Symbol> arguments = calcsymbol.getScopeDefines();
		if (arguments.size()!=nargs) {
			throw new RuntimeException("number of arguments is " + symbol.getNumArgs() 
					+ " but there are "+ arguments.size() + " defines in the symbol: " + calcsymbol.toString() 
					+ " - " + calcsymbol.getScopeDefines() + ", line " + node.getLine());
		}
		Integer index = model.getCalcindex(name, nargs);
		assert index!=null;
		
		//^(TT_RESULTDEF id arguments? formula) 
		TcAst nodeid = node.getChild(0);
		int nodeind=1;
		TcAst act = nodeind<node.getChildCount() ? (TcAst) node.getChild(nodeind) : null;
		TcAst astarguments=null;
		if (act!=null && act.getType()==TT_ARGDEF) {
			//^(KEYWORD_AS id)
			nodeind++;
			act = nodeind<node.getChildCount() ? (TcAst) node.getChild(nodeind) : null;
		}
		TcAst formula = node.getChild(node.getChildCount()-1);
		
		int id = model.putFormula(formula);
		out.print(".nodecalc");
		out.print(" node=");
		out.print(nodeindex);
//		out.print(name);
//		out.print(":");
//		out.print(nargs);
		out.print(" calc=");
		out.print(index);
		out.print(" formula=");
		out.print(id);
		if (VmFormula.isSimple(model, formula)) {
		} else {
		}
		out.println();
	}

	private void printNodenameAbsolute(ScopeNode node) {
		ArrayList<String> ids = new ArrayList<String>();
		for (ScopeNode currentnode = node; currentnode!=null; currentnode = currentnode.getParentNode()) {
			String nodename = currentnode.getNodeName();
			String nodeid   = currentnode.getNodeId();
			ids.add(nodeid);
		}
		int indend = ids.size()-1-1; // don't print topmost nodename
		if (indend<0) {
			out.print(node.getNodeName());
		} else {
			for (int j=indend; j>=0; j--) {
				if (j<indend) {
					out.print(".");
				}
				out.print(ids.get(j));
			}
		}
	}

	private void printCalc() throws IOException {
		out.print(".calcs size=");
		out.println(model.getCalcnamesSize());
		for (int i=0; i<model.getCalcnamesSize(); i++) {
			GenNodeCalc gencalc = model.getCalcname(i);
			String calcname = gencalc.getCalcname();
			int    nargs    = gencalc.getNumArgs();
			out.print(".calc calc=");
			out.print(i);
			out.print(" name=");
			out.print(calcname);
			out.print(" nargs=");
			out.print(nargs);

			/* call to calc from root */
			Scope scope = model.maintreeScope;
			int calcindex = model.getCalcindex(calcname, nargs);
			ResolvedCalc resolveret = scope.resolveCalc(model, calcname, nargs, false);
			
			switch(resolveret.getType()) {
			case ONE_SIMPLE: {
				int nodeindex = resolveret.getSumNode();
				out.print(" computenode=");
				out.print(nodeindex);
				break;
			}
			case LIST: {
				int nodeindex = resolveret.getSumNode();
				if (nodeindex<0) {
					throw new RuntimeException("could not get common parent for nodes " + resolveret.toString() + ", calc " + calcname + " (" + calcindex + ")");
				}
				out.print(" computestartnode=");
				out.print(nodeindex);
				break;
			}
			}
			out.println();
		}
		
	}
	
	private void printBitset(String prefix, NodeData nodeData, BitSet bs) {
		int nodeindex = nodeData.nodeindex;
		int start=0;
		boolean first=true;
		while ((start = bs.nextSetBit(start))>=0) {
			/* index has now next index of set bit */
			/* find next which is not set */
			int end = bs.nextClearBit(start);
			if (end<0) {
				end = model.getCalcnamesSize()-1;
			} else {
				end--;
			}
			out.print(prefix);
			out.print(" node=");
			out.print(nodeindex);
			if (start==end) {
				out.print(" calc=");
				out.print(start);
			} else {
				out.print(" calc_rangestart=");
				out.print(start);
				out.print(" calc_rangeend=");
				out.print(end);
			}
			out.println();
			start = end+1;
			if (start>=model.getCalcnamesSize()) {
				break;
			}
		}
	}
	private void printEdge1(NodeData data) {
		ScopeNode scopenode = data.node;
		int index = data.nodeindex;

		List<ScopeNode> childs = scopenode.getChildren();
		List<ScopeNode> links  = scopenode.getLinks();
		int size=childs.size()+links.size();
		out.print(".edges from=");
		out.print(index);
		out.print(" size=");
		out.println(size);
		int i=0;
		for (ScopeNode child : scopenode.getChildren()) {
			out.print(".edge");
			out.print(" ind=");
			out.print(i++);
			out.print(" from=");
			out.print(index);
			out.print(" to=");
			out.print(model.getNodeData(child).nodeindex);
			out.println();
		}
		for (ScopeNode link : scopenode.getLinks()) {
			out.print(".link");
			out.print(" ind=");
			out.print(i++);
			out.print(" from=");
			out.print(index);
			out.print(" to=");
			out.print(model.getNodeData(link).nodeindex);
			out.println();
		}
	}
	
	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		VmNodes obj = new VmNodes(model, foldernameout, packagename);
		obj.run();
	}
}
