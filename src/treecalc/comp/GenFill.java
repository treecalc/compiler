package treecalc.comp; 

import static treecalc.comp.parser.TcSimpleParser.*;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import treecalc.comp.GenInput.GenInputCalc;
import treecalc.comp.ModelSimple.NodeData;


public class GenFill {
	private final ModelSimple model;
	private GenInput input;
	private GenTable table;
	public GenFill(ModelSimple model) {
		this.model = model;
	}
	public void run() {
		fillFunctions(model.getRoot());
		fillInputs(model.getRoot());
		fillInputsAutocounter();
		fillCalcnames(model.maintreeScope, new HashSet<ScopeNode>());
		fillNodedata(model.maintreeScope);
		fillTables(model.getRoot());
	}
	
	private void fillInputsAutocounter() {
		for (int i=0; i<model.getInputSize(); i++) {
			input = model.getInput(i);
			GenInputCalc calc = input.getCalc("AUTOCOUNTER");
			if (calc!=null) {
				fillAutocounter(calc.getCalcast());
			}
		}
	}
	private void fillAutocounter(TcAst node) {
		if (node==null) {
			return;
		}
		int type = node.getType();
		switch(type) {
		case TT_RESULTDEF: {
			//^(TT_RESULTDEF id arguments? formula)
			fillAutocounter(node.getChild(node.getChildCount()-1));
			break;
		}
		case KEYWORD_COUNTERLIST: {
			for (int i=0; i<node.getChildCount(); i++) {
				String name = node.getChild(i).getText().toUpperCase();
				input.addAutocounter(name);
			}
		}
		default: {
			for (int i=0; i<node.getChildCount(); i++) {
				fillAutocounter(node.getChild(i));
			}
		}
		}
	}
		
	
	/**
	 * Fill list 'functions' and put GenFunction objects into ModelSimple
	 * @param node
	 */
	private void fillFunctions(TcAst node) {
		switch (node.getType()) {
		case TT_COMPUNIT: {
			if (node.getChildCount()==0) {
				break;
			}
			for (int i=0; i<node.getChildCount(); i++) {
				fillFunctions(node.getChild(i));
			}
			break;
		}
		case KEYWORD_FUNC: {
			//^(KEYWORD_FUNC ^(TT_RESULTDEF id arguments? formula))
			int funcindex = model.getFunctionSize();
			TcAst calc = node.getChild(0);
			TcAst id = calc.getChild(0);
			TcAst astArgs = calc.getFirstChildWithType(TT_ARGDEF);
			int nargs = astArgs!=null ? astArgs.getChildCount() : 0;
			String funcname = id.getText().toUpperCase();
			Scope scope = calc.getScope();
			Symbol symbol = scope.resolve(funcname, nargs, false);
			ScopedSymbol calcsymbol = (ScopedSymbol) symbol;
			List<Symbol> arguments = calcsymbol.getScopeDefines();
			GenFunction genfunc = new GenFunction(funcname, funcindex, calc);
			for (Symbol argsymbol : arguments) {
				String argname = argsymbol.getSymbolName();
				genfunc.addArgname(argname);
			}
			model.addFunction(genfunc);
			break;
		}
		}
	}
	private void fillInputs(TcAst node) {
		switch (node.getType()) {
		case TT_COMPUNIT: { 
			if (node.getChildCount()==0) {
				break;
			}
			for (int i=0; i<node.getChildCount(); i++) {
				fillInputs(node.getChild(i));
			}
			break;
		}
		case KEYWORD_INPUT: {
			//^(KEYWORD_INPUT id resultdef*)
			TcAst id = node.getChild(0);
			String name = id.getText().toUpperCase();
			int ind = model.getInputSize();
			input = new GenInput(name, ind, node);
			model.addInput(input);
			for (int i=1; i<node.getChildCount(); i++) {
				fillInputs(node.getChild(i));
			}
			break;
		}
		case TT_RESULTDEF: {
			//^(TT_RESULTDEF id arguments? formula)
			TcAst id = node.getChild(0);
			String calcname = id.getText().toUpperCase();
			input.addCalc(calcname, node);
			model.putInputcalc(calcname);
			break;
		}
		}
	}

	
	private void fillCalcnames(ScopeNode node, HashSet<ScopeNode> processedlinks) {
		/* create new data object and add it to the nodelist */
		List<Symbol> nodedefines = node.getScopeDefines();
		if (nodedefines!=null) {
			for (Symbol calcsymbol : nodedefines) {
				String calcname = calcsymbol.getSymbolName();
				int nargs = calcsymbol.getNumArgs();
				int oldindex = model.getCalcindex(calcname, nargs);
				if (oldindex<0) {
					int calcindex = model.getCalcnamesSize();
					model.addCalcname(calcname, nargs, calcindex);
				}
			}
		}
		for (ScopeNode child : node.getChildren()) {
			fillCalcnames(child, processedlinks);
		}
		for (ScopeNode link : node.getLinks()) {
			if (!processedlinks.contains(link)) {
				processedlinks.add(link);
				fillCalcnames(link, processedlinks);
			}
		}
	}
	
	private NodeData fillNodedata(ScopeNode node) {
		NodeData data = model.getNodeData(node);
		if (data!=null) {
			return data;
		}
		/* create new data object and add it to the nodelist */
		data = new NodeData();
		data.node = node;
		data.nodeindex = model.getNodeDataSize();
		model.addNodeData(node, data);

		/* set one bit for each calc defined in the node */
		List<Symbol> nodedefines = node.getScopeDefines();
		BitSet calcsOwnnodeBitset = new BitSet(model.getCalcnamesSize());
		data.calcsOwnnode = calcsOwnnodeBitset;
		for (Symbol calcsymbol: nodedefines) {
			String calcname = calcsymbol.getSymbolName();
			int nargs = calcsymbol.getNumArgs();
			int calcindex = model.getCalcindex(calcname, nargs);
			if (calcindex<0) {
				System.out.println("did not find " + calcname + " in calcnames!");
			} else {
				calcsOwnnodeBitset.set(calcindex);
			}
		}
		data.calcs = null;
		BitSet calcsChildsAndLinks = new BitSet(model.getCalcnamesSize());
		
		/* walk through children and links, set bitsets for total calc names */
		for (ScopeNode child : node.getChildren()) {
			NodeData datachild = fillNodedata(child);
			BitSet calcsChild = datachild.calcs;
			if (!calcsChild.isEmpty()) {
				calcsChildsAndLinks.or(calcsChild);
			}
		}
		for (ScopeNode link : node.getLinks()) {
			NodeData datalink = fillNodedata(link);
			BitSet calcsLink = datalink.calcs;
			if (!calcsLink.isEmpty()) {
				calcsChildsAndLinks.or(calcsLink);
			}
		}
		if (calcsChildsAndLinks.isEmpty()) {
			data.calcs = data.calcsOwnnode;
		} else {
			calcsChildsAndLinks.or(data.calcsOwnnode);
			data.calcs = calcsChildsAndLinks;
		}
		return data;
	}

	private void fillTables(TcAst node) {
		if (node==null) {
			return;
		}
		int tokentype = node.getType();
		switch (tokentype) {
		case TT_COMPUNIT: {
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				fillTables(child);
			}
			break;
		}
		case KEYWORD_TABLE: {
			String name = node.getChild(0).getText().toUpperCase();
			int index = model.getTableSize();
			table = new GenTable(name, index, node);
			model.addTable(table);
			fillGenTable();
			table.sortAndFill();
			break;
		}
		}
	}
	
	/**
	 * @param node
	 * @return new GenTable filled up with data from Ast.
	 */
	private void fillGenTable() {
		//^(KEYWORD_TABLE id colnames tableline*)
		//                   colnames: ^(TT_IDLIST colname*)
		//                   colname: ID | NUMBER | STRING
		//                   tableline: ^(TT_TABLELINE tablecell*)
		//                   tablecell: ID | NUMBER | STRING
		TcAst node = table.getAst();

		TcAst astColnames = node.getChild(1);
		int numcol = astColnames.getChildCount();
		String[] colnames = new String[numcol];
		for (int i=0; i<astColnames.getChildCount(); i++) {
			TcAst child = astColnames.getChild(i);
			colnames[i] = child.getText().toUpperCase();
		}
		table.setColnames(colnames);
		int numrow = node.getChildCount()-2;
		String[][] data = new String[numrow][]; 
		int indrow=0;
		for (int i=2; i<node.getChildCount(); i++, indrow++) {
			String[] row = new String[numcol];
			data[indrow] = row;
			TcAst astTableline = node.getChild(i);
			for (int indcol=0; indcol<astTableline.getChildCount(); indcol++) {
				TcAst astTablecell = astTableline.getChild(indcol);
				String value = astTablecell.getText();
				row[indcol] = value;
			}
		}
		table.setData(data);
	}
	
	public static void fillModel(ModelSimple model) {
		GenFill o = new GenFill(model);
		o.run();
	}
}
