package treecalc.comp; 

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;

public class ModelSimple {
	public ModelSimple(TcAst root) {
		this.root = root;
	}
	/* 
	 * SYMBOL TABLES
	 * =============
	 * Kind of symbols:
	 * - Function symbol         -> global
	 * - Table symbol            -> global
	 * - Input symbol            -> global
	 * - Input Property          -> defined within Input symbol, accessible through the Input globally
	 * - Result symbol           -> node, that has global scope as parent
	 * - Parameters              -> in function symbol 
	 * - sumx, vectorx variable  -> local scope
	 * - node multiple index     -> globally visible, at runtime check if active
	 * - builtin function symbol -> globally
	 * Global scope:
	 * - Function symbol
	 * - Table symbol
	 * - Input symbol
	 * - Node multiple index
	 * - builtin function
	 * Node scope:
	 * - definition of results in the node
	 * - resolve: number of parameters matters; child nodes, linked nodes and global scope 
	 * Function scope:
	 * - enclosing (global)
	 * - Main Tree
	 * - Parameters
	 */
	/* the global scope hosts functions, tables, inputs, node multiple index and builtin functions */
	public String[] builtin = new String[] {
			/* boolean */
			"FALSE",
			"NOT",
			"TRUE",
			/* table functions */
			"CELL", /* table function */
			"CELLX", /* table function */
			"EXISTS", /* table function */
			"INTERPOL",/* table function */
			"LOOKUP", /* table function */
			"LOOKUPX", /* table function */
			"LOOKDOWNX", /* table function */
			"SEARCH", /* table function */
			/* odbc access */
			"SELECT", /* odbc access */
			"SELECTX", /* odbc access */
			"V_SELECT", /* odbc access */
			"V_SELECTX", /* odbc access */
			"TABCOLS", /* table function */
			"TABROWS", /* table function */
			"ANSI",
			"CHR",
			/* string functions */
			"FORMAT",
			"INSTR",
			"LEFT",
			"LENGTH",
			"MID",
			"OEM",
			"RIGHT",
			"STRCMP",
			"STRICMP",
			"SUBST",
			"TOLOWER",
			"TOUPPER",
			"TRIM",
			/* math functions */
			"ABS",
			"ACOS",
			"ASIN",
			"ATAN",
			"ATAN2",
			"CEIL",
			"CONST",
			"COS",
			"COSH",
			"EXP",
			"FLOOR",
			"FMOD",
			"LOG",
			"LOG10",
			"MAX",
			"MIN",
			"ROUND",
			"SIN",
			"SINH",
			"SQRT",
			"TAN",
			"TANH",
			/* date functions */
			"DATE",
			"DATEDAY",
			"DAY",
			"DAYDATE",
			"DAYS",
			"MONTH",
			"MONTHS",
			"NOW", /* bad guy */
			"TODAY", /* bad guy */
			"YEAR",
			"YEARS",
			/* list */
			"V_",
			"LIST", //new
			"V_CONCAT",
			"V_CONSTRUCT",
			"V_DELETE",
			"V_ELEMENT",
			"E_",
			"V_ELEMENTS",
			"V_FIRST",
			"V_FRONT",
			"V_INSERT",
			"V_LAST",
			"V_LENGTH",
			"V_MAX",
			"V_MIN",
			"V_NUMSORT",
			"V_REPLACE",
			"V_REST",
			"V_SELECT",
			"V_SELECTX",
			"V_SORT",
			"V_STRING",
			"V_STRINGX",
			"V_TRANS",
			/* a kind of null */
			"VOID",
			/* informatoin */
			"ISBOOL",
			"ISDATE",
			"ISINTEGER",
			"ISNUMBER",
			"ISTIME",
			"UNDEFINED",
			/* loops */
			"SUMX",
			"PRODX",
			"VECTORX",
			/* function reference */
			"FUNCREF",
			"DOCALL",
			/* dynamic call */
			"VPMCOMPUTE",
			"VPMEXIST",
			"VPMTRY",
			/* error handling */
			"ERROR",
			"MESSAGE",
			/* get something from config file */
			"GETPRIVATEPROFILESTRING",
	};
	public Scope globalScope = new ScopeSimple("global", null);
	{
		for (String onebuiltin : builtin) {
			globalScope.define(new SymbolImpl(onebuiltin, Symbol.SYMBOLTYPE_BULITIN, 0));
		}
	}
	/* the root tree can resolve Result symbols for all nodes reachable by the main tree */ 
	public ScopeNode maintreeScope = new ScopeNode("maintree", "maintree", null, globalScope); /* root tree */
	/* the combinedScope looks up symbols in globalScope and rootScope. Defines are done in globalScope
	 * To be used within Global definitions (Function, Input, ...) that may access the main tree
	 */
	public Scope combinedScope = new ScopeComb(globalScope, maintreeScope);
	public ArrayList<ModelTree> trees = new ArrayList<ModelTree>();
	
	/* tables */
	private HashMap<String,GenTable> tablesMapName  = new HashMap<String,GenTable>();
	private HashMap<Integer,GenTable> tablesMapIndex = new HashMap<Integer,GenTable>();
	public void addTable(GenTable table) {
		if (table!=null) {
			tablesMapName.put(table.getName().toUpperCase(), table);
			tablesMapIndex.put(table.getIndex(), table);
		}
	}
	public GenTable getTable(String tablename) {
		return tablesMapName.get(tablename.toUpperCase());
	}
	public GenTable getTable(int tableindex) {
		return tablesMapIndex.get(tableindex);
	}
	public int getTableSize() {
		return tablesMapName.size();
	}
	
	/* functions */
	private HashMap<String,GenFunction> functionsMapName  = new HashMap<String,GenFunction>();
	private HashMap<Integer,GenFunction> functionsMapIndex = new HashMap<Integer,GenFunction>();
	public void addFunction(GenFunction function) {
		if (function!=null) {
			functionsMapName.put(function.getName(), function);
			functionsMapIndex.put(function.getIndex(), function);
		}
	}
	public GenFunction getFunction(String name) {
		return functionsMapName.get(name.toUpperCase());
	}
	public GenFunction getFunction(int index) {
		return functionsMapIndex.get(index);
	}
	public int getFunctionSize() {
		return functionsMapName.size();
	}
	
	/* inputs */
	private HashMap<String,GenInput> inputsMapName  = new HashMap<String,GenInput>();
	private HashMap<Integer,GenInput> inputsMapIndex = new HashMap<Integer,GenInput>();
	public void addInput(GenInput input) {
		if (input!=null) {
			inputsMapName.put(input.getName(), input);
			inputsMapIndex.put(input.getIndex(), input);
		}
	}
	public GenInput getInput(String name) {
		return inputsMapName.get(name.toUpperCase());
	}
	public GenInput getInput(int index) {
		return inputsMapIndex.get(index);
	}
	public int getInputSize() {
		return inputsMapName.size();
	}

	
	/* nodes */
	public int getNodeDataSize() {
		return nodelist.size();
	}
	public NodeData getNodeData(ScopeNode scopeNode) {
		return nodesdata.get(scopeNode);
	}
	public NodeData getNodeData(int nodeindex) {
		return nodelist.get(nodeindex);
	}
	public void addNodeData(ScopeNode scopeNode, NodeData nodeData) {
		nodesdata.put(scopeNode, nodeData);
		nodelist.put(nodeData.nodeindex, nodeData);
	}
	/**
	 * key: ScopeNode-Object
	 * value: useful Data for this object to be used for code generation
	 */
	private HashMap<ScopeNode, NodeData> nodesdata = new HashMap<ScopeNode, NodeData>();
	/**
	 * list of all NodeData objects. The position of the NodeData equals NodeData.nodeindex!
	 */
	private HashMap<Integer,NodeData> nodelist = new HashMap<Integer,NodeData>();
	/**
	 * Map of all all result-definition names within the nodes reachable from the main root node.
	 * name: property name (TODO: add arity to key)
	 * value: unique integer to be used as index into the bitset
	 */
	public int getCalcnamesSize() {
		return calclist.size();
	}
	public void addCalcname(String calcname, int nargs, int calcindex) {
		GenNodeCalc nodecalc = new GenNodeCalc(calcname, nargs);
		calcnames.put(nodecalc, calcindex);
		calclist.put(calcindex, nodecalc);
	}
	public GenNodeCalc getCalcname(int calcindex) {
		return calclist.get(calcindex);
	}
	public int getCalcindex(String calcname, int nargs) {
		GenNodeCalc nodecalc = new GenNodeCalc(calcname, nargs);
		Integer ind = calcnames.get(nodecalc);
		return ind!=null ? ind : -1;
	}
	private HashMap<GenNodeCalc, Integer> calcnames = new HashMap<GenNodeCalc, Integer>();
	private HashMap<Integer, GenNodeCalc> calclist = new HashMap<Integer, GenNodeCalc>();
	
	public static class NodeData {
		public int nodeindex;
		public ScopeNode node;
		public BitSet calcsOwnnode; //calcs defined in this node. Bit indizes are according to calcnames
		public BitSet calcs; //calcs defined in this and all child/linked nodes. Bit indizes are according to calcnames
	}
	
	/**
	 * @return closest common parent, -1 if nodes argument is empty
	 */
	public int getCommonParent(int[] nodes) {
		int len = nodes==null ? 0 : nodes.length;
		if (len==0) {
			return -1;
		}
		NodeData[] nodesdata = new NodeData[len];
		for (int i=0; i<len; i++) {
			NodeData nodedata = this.getNodeData(nodes[i]);
			nodesdata[i] = nodedata;
		}
		/* start at any node and progress up to the parent until this is a parent of all other nodes as well */
		NodeData candidate = nodesdata[0];
		int candidateindex;
		boolean found;
		do {
			found = true;
			candidateindex = candidate.nodeindex;
			for (int i=1; i<len; i++) {
				NodeData nodedata = nodesdata[i];
				while (nodedata!=null) {
					if (nodedata.nodeindex==candidateindex) {
						break;
					}
					nodedata = getNodeData(nodedata.node.getParentNode());
				}
				if (nodedata==null) {
					found = false;
					break;
				}
			}
			candidate = getNodeData(candidate.node.getParentNode());
		} while (!found && candidate!=null);
		return found ? candidateindex : -1;
	}
	
	private TcAst root;
	public void setAstRoot(TcAst root) {
		this.root = root;
	}
	public TcAst getRoot() {
		return root;
	}
	
	HashMap<String, Integer> timesMap = new HashMap<String, Integer>();
	HashMap<Integer, String> timesMapIndex = new HashMap<Integer, String>();
	public void putTimesName(String name) {
		String nameUpper = name.toUpperCase();
		Integer oldind = timesMap.get(nameUpper);
		if (oldind==null) {
			int newind = timesMap.size();
			timesMap.put(nameUpper, newind);
			timesMapIndex.put(newind, nameUpper);
		}
	}
	public int getTimesId(String name) {
		String nameUpper = name.toUpperCase();
		Integer ind = timesMap.get(nameUpper);
		return ind!=null ? ind : -1;
	}
	public String getTimesName(int id) {
		return timesMapIndex.get(id);
	}
	public int getTimesSize() {
		return timesMap.size();
	}
	/**
	 * @param inputname
	 * @return array of timesindex for the given input
	 */
	public int[] getInputAutocounters(GenInput input) {
		if (input.hasAutocounter()) {
			ArrayList<String> autocounters = input.getAutocounters();
			int[] autoindexarr = new int[autocounters.size()];
			int ind=0;
			for (String autocounter : autocounters) {
				int autoindex = this.getTimesId(autocounter);
				autoindexarr[ind++] = autoindex;
			}
			return autoindexarr;
		} else {
			return null;
		}
	}
	private HashMap<String, FormulaConstant> constants = new HashMap<String, FormulaConstant>();
	public void putConstant(FormulaConstant constant) {
		constants.put(constant.getNameInMethod(), constant);
	}
	public HashMap<String, FormulaConstant> getConstants() {
		return constants;
	}
	
	private HashMap<String, Integer> inputcalcMap = new HashMap<String, Integer>();
	private HashMap<Integer, String> inputcalcIndex = new HashMap<Integer, String>();
	public int getInputcalcSize() {
		return inputcalcMap.size();
	}
	public void putInputcalc(String inputcalcName) {
		String nameUpper = inputcalcName.toUpperCase();
		Integer i = inputcalcMap.get(nameUpper);
		if (i==null) {
			int pos = inputcalcMap.size();
			inputcalcMap.put(nameUpper, pos);
			inputcalcIndex.put(pos, nameUpper);
		}
	}
	public int getInputcalcIndex(String inputcalcName) {
		Integer i = inputcalcMap.get(inputcalcName.toUpperCase());
		return i!=null ? i : -1;
	}
	public String getInputcalcName(int index) {
		return inputcalcIndex.get(index);
	}
	
	private HashMap<Integer, String> cacheIds = new HashMap<Integer, String>();
	public int getCacheId(String description) {
		while (true) {
			double r = Math.random();
			int val = (int) (r * Integer.MAX_VALUE);
			if (!cacheIds.containsKey(val)) {
				cacheIds.put(val, description);
				return val;
			}
		}
	}
	public HashMap<Integer, String> getCacheIds() {
		return cacheIds;
	}
	
	private HashMap<Integer, TcAst> formulasIndex = new HashMap<Integer, TcAst>();
	{ 
		formulasIndex.put(0, null);
		formulasIndex.put(1, null);
	}
	private HashMap<TcAst, Integer> formulasMap = new HashMap<TcAst, Integer>(); 
	public int putFormula(TcAst formula) {
		Integer id = formulasMap.get(formula);
		if (id!=null) {
			return id;
		}
		id = formulasIndex.size();
		formulasIndex.put(id, formula);
		formulasMap.put(formula,  id);
		return id;
	}
	public int getFormulaSize() {
		return formulasIndex.size();
	}
	public TcAst getFormula(int id) {
		return formulasIndex.get(id);
	}
}
