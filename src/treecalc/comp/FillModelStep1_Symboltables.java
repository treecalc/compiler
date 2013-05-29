/* buildup all scopes (with symbol tables) and symbols */

package treecalc.comp; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static treecalc.comp.TcSimpleParser.*;

public class FillModelStep1_Symboltables {
	private final ModelSimple model;

	private Scope currentscope;
	
	private FillModelStep1_Symboltables(ModelSimple model) {
		this.model = model;
	}

	/* 
	 * SYMBOL TABLES
	 * =============
	 * Kind of symbols:
	 * - Function symbol
	 * - Table symbol
	 * - Input symbol
	 * - Result symbol ("property")
	 * - Variable (Parameter, current index node, current index sumx/vectorx
	 * - Input Property: input(...).name
	 * - builtin function symbol (id with special meaning)
	 * Global:
	 * - Function     ok
	 * - Table        ok
	 * - Input
	 * - Input property
	 * - builtin function
	 * - current multiple index (globally visible, at runtime check if active!)
	 * Main tree:
	 * - definition of all child nodes
	 * Within function:
	 * - enclosing (global)
	 * - Main Tree
	 * - Parameters
	 * Within sumx, vectorx: enclosing + current index.
	 * Node:
	 * - enclosing (Global)
	 * - Definitions in child nodes (name + number of parameters!)
	 */
	
	private void run()  {
		visit(model.getRoot());
	}
	
	@SuppressWarnings("unchecked")
	private Object visit(TcAst node) {
		int tokentype = node.getType();
		switch (tokentype) {
		case 0: { //list of elements -> ?
			throw new RuntimeException("did not expect list (token type 0): " + ", line " + node.getLine() + ": " +node.getText());
		}
		case TT_COMPUNIT: {
			currentscope = model.globalScope;
			node.setScope(currentscope);
			List<TcAst> children = node.getChildren();
			for (TcAst child : children) {
				//can be one of KEYWORD_TREE, KEYWORD_CALC, KEYWORD_INPUT, KEYWORD_FUNC, KEYWORD_TABLE
				visit(child);
			}
			break;
		}
		case KEYWORD_TREE: {
			// ^(KEYWORD_TREE nodepath nodeinfo*)
			currentscope = node.getScope();
			//TcAst nodepath = node.getChild(0);
			//TT_NODEPATH
			//visit(nodepath);
			for (int i=1; i<node.getChildCount(); i++) {
				TcAst nodeinfo = node.getChild(i);
				// KEYWORD_NODE oder KEYWORD_LINK
				visit(nodeinfo);
			}
			break;
		}
		case KEYWORD_NODE: {
			// ^(KEYWORD_NODE nodename ^(KEYWORD_AS id)? ^(TT_INCLUSIONFORMULA formula)? ^(TT_TIMESINFO formula id)? nodeinfo*)
			currentscope = node.getScope();
			TcAst astNodename = node.getChild(0);
			String nodename = astNodename.getText();
			//constant|ID
			//visit(nodename);
			int nodeind=1;
			//constant|id

			TcAst act = nodeind<node.getChildCount() ? node.getChild(nodeind) : null;
			TcAst astnodeid=null;
			if (act!=null && act.getType()==KEYWORD_AS) {
				//^(KEYWORD_AS id)
				nodeind++;
				act = nodeind<node.getChildCount() ? node.getChild(nodeind) : null;
			} 
			if (act!=null && act.getType()==TT_INCLUSIONFORMULA) {
				TcAst inclusion = act;
				visit(inclusion);
				nodeind++;
				act = nodeind<node.getChildCount() ? node.getChild(nodeind) : null;
			}
			if (act!=null && act.getType()==TT_TIMESINFO) {
				TcAst timesinfo = act;
				visit(timesinfo);
				nodeind++;
				act = nodeind<node.getChildCount() ? node.getChild(nodeind) : null;
			}
			
			for (int i=nodeind; i<node.getChildCount(); i++) {
				TcAst nodeinfo = node.getChild(i);
				// KEYWORD_NODE oder KEYWORD_LINK
				visit(nodeinfo);
			}
			break;
		}
		case TT_INCLUSIONFORMULA: {
			//^(TT_INCLUSIONFORMULA formula)
			TcAst formula = node.getChild(0);
			currentscope = node.getScope();
			visit(formula);
			currentscope = node.getScope();
			break;
		}
		case TT_TIMESINFO: {
			//^(TT_TIMESINFO formula id)
			TcAst formula = node.getChild(0);
			TcAst id  = node.getChild(1);
			currentscope = formula.getScope();
			visit(formula);
			//don't need to visit id */
			break;
		}
		case ID: {
			node.setScope(currentscope);
			break;
		}
		case KEYWORD_LINK: {
			node.setScope(currentscope);
			TcAst nodepath = node.getChild(0);
			visit(nodepath);
			//^(KEYWORD_LINK ^(TT_NODEPATH linkpart*))
			//linkpart: ID | STRING | ASTERISK | DOUBLEASTERISK 
			;
			break;
		}
		case TT_NODEPATH: {
			StringBuffer np = new StringBuffer(512);
			for (int i=0; i<node.getChildCount(); i++) {
				if (i>0) {
					np.append('.');
				}
				TcAst nodeid = node.getChild(i);
				np.append(nodeid.getText());
//				visit(nodeid);
			}
			return np.toString();
		}

		case KEYWORD_CALC: {
			node.setScope(currentscope);
			//^(KEYWORD_CALC nodepath resultdef*)
			TcAst nodepath = node.getChild(0);
			//^(TT_NODEPATH id*)
			String[] path = new String[nodepath.getChildCount()];
			int[] tokentypes = new int[nodepath.getChildCount()];
			for (int i=0; i<nodepath.getChildCount(); i++) {
				TcAst child = nodepath.getChild(i);
				path[i] = child.getText();
				tokentypes[i] = child.getType();
			}
			ModelTree tree = this.findTree(model.trees, path);
			if (tree==null) {
				System.out.println("did not find tree for CALC " + Arrays.toString(path));
			}
			ScopeNode rootNode = tree.getRootnode();
			ScopeNode scopeNode = rootNode.findFirst(path, tokentypes);
			if (scopeNode==null) {
				System.out.println("could not find scopeNode for CALC " + Arrays.toString(path));
			}
			currentscope = scopeNode;
			// TT_NODEPATH
			for (int i=1; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				//TT_RESULTDEF
				visit(child);
			}
			break;
		}
			
		case KEYWORD_INPUT: {
			currentscope = model.combinedScope;
			node.setScope(currentscope);
			// ^(KEYWORD_INPUT id resultdef*)
			TcAst id = node.getChild(0);

			String inputname = id.getText().toUpperCase();
			ScopedSymbol inputsymbol = new ScopedSymbol(
					"input:" + inputname,
					Symbol.SYMBOLTYPE_INPUT,
					inputname,
					0,
					currentscope
			);
			inputsymbol.setAst(node);
			currentscope.define(inputsymbol);
			/* define special parameters for formulas within input */
//			currentscope.define(new SymbolImpl("name", Symbol.SYMBOLTYPE_INPUTRESULTDEF));
			currentscope.define(new SymbolImpl("unchecked", Symbol.SYMBOLTYPE_INPUTRESULTDEF, 0));
			currentscope = inputsymbol;

			visit(id);
			if (node.getChildCount()>1) {
				for (int i=1; i<node.getChildCount(); i++) {
					TcAst child = node.getChild(i);
					//TT_RESULTDEF 
					visit(child);
				}
			} else {
			}
			
			currentscope = currentscope.getParent();
			break;
		}
		
		case KEYWORD_FUNC: {
			//currentscope = model.globalScope;
			currentscope = model.combinedScope;
			node.setScope(currentscope);
			// ^(KEYWORD_FUNC resultdef)
			//   resultdef: ^(TT_RESULDEF ...)
			TcAst resultdef = node.getChild(0);
			visit(resultdef);
			break;
		}
		
		case KEYWORD_TABLE: {
			currentscope = model.globalScope;
			node.setScope(currentscope);
			//^(KEYWORD_TABLE id colnames tableline*)
			TcAst id = node.getChild(0);
			
			String inputname = id.getText().toUpperCase();
			Symbol symbol = new SymbolImpl(inputname, Symbol.SYMBOLTYPE_TABLE, 0);
			currentscope.define(symbol);
			
			visit(id);
			
			TcAst colnames = node.getChild(1);
			// ^(TT_IDLIST colname*)
			visit(colnames);
			int startind=2;
			for (int i=startind; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				//^(TT_TABLELINE ...
				visit(child);
			}
			break;
		}
		
		case LOGICAL_OR:
		case LOGICAL_AND:
		case LOGICAL_XOR:
		case COMPARE_EQUAL:
		case COMPARE_EQUAL_CSTYLE:
		case COMPARE_SMALLER:
		case COMPARE_BIGGER:
		case COMPARE_LESSEQUAL:
		case COMPARE_BIGGEREQUAL:
		case COMPARE_NOTEQUAL:
		case COMPARE_NOTEQUAL_CSTYLE:
		case COMPARE_STR_EQUAL:
		case COMPARE_STR_NOTEQUAL:
		case COMPARE_STR_ALIKE:
		case COMPARE_STR_NOTALIKE:
		case COMPARE_STR_BEFORE:
		case COMPARE_STR_NOTBEFORE:
		case COMPARE_STR_AHEAD:
		case COMPARE_STR_NOTAHEAD:
		case COMPARE_STR_BEHIND:
		case COMPARE_STR_NOTBEHIND:
		case COMPARE_STR_AFTER:
		case COMPARE_STR_NOTAFTER:
		case STRCAT:
		case EXPONENT:
		case SLASH:
		case DIV:
		case MOD:
		case POWER:  {
			node.setScope(currentscope);
			binop(node);
			break;
		}
		
		case PLUS: 
		case MINUS: {
			node.setScope(currentscope);
			if (node.getChildCount()==1) {
				unop(node);
			} else if (node.getChildCount()==2) {
				binop(node);
			} else {
				throw new RuntimeException("invalid number of childs for " + node.getText() + ", tokentype " + tokentype + ", line " + node.getLine() + ": " +node.getChildCount());
			}
			break;
		}
		case ASTERISK:
			node.setScope(currentscope);
			if (node.getChildCount()==0) {
				/* usage in path */
			} else {
				binop(node);
			}
			break;
			

		case DOUBLEASTERISK:
			node.setScope(currentscope);
			break;
			
		case KEYWORD_CASE: {
			//^(KEYWORD_CASE formula casewhen* casedefault?)
			node.setScope(currentscope);
			TcAst formula = node.getChild(0);
			visit(formula);
			int indstart=1;
			for (int i=indstart; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				//KEYWORD_WHEN oder KEYWORD_DEFAULT
				visit(child);
			}
			break;
		}
		case KEYWORD_WHEN: { //case part
			//^(KEYWORD_WHEN casecompares formula)
			node.setScope(currentscope);
			TcAst casecompares = node.getChild(0);
			TcAst formula = node.getChild(1);
			visit(casecompares);
			visit(formula);
			break;
		}
		case TT_CASECONDITION: {
			// ^(TT_CASECONDITION casecompare*)
			node.setScope(currentscope);
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				// TT_CASECOMPARISON
				visit(child);
			}
			break;
		}
		case TT_CASECOMPARISON: {
			// ^(TT_CASEONECOMPARISON constant) 
			node.setScope(currentscope);
			TcAst constant = node.getChild(0);
			visit(constant);
			break;
		}

		case TT_CASERANGE: {
			// ^(TT_CASERANGE      caseconstant*)
			node.setScope(currentscope);
			TcAst constantFrom = node.getChild(0);
			TcAst constantTo = node.getChild(1);
			visit(constantFrom);
			visit(constantTo);
			break;
		}
		case KEYWORD_DEFAULT: {
			// ^(KEYWORD_DEFAULT formula
			node.setScope(currentscope);
			TcAst formulaDefault = node.getChild(0);
			visit(formulaDefault);
		}
		
		case KEYWORD_AS: {
			// ^(KEYWORD_AS id)
			node.setScope(currentscope);
			TcAst nodeid = node.getChild(0);
			visit(nodeid);
			break;
		}
		case TT_RESULTDEF: {
			Scope savedscope = currentscope;
			node.setScope(currentscope);
			//^(TT_RESULTDEF ID arguments? formula)
			//used for
			// - function definition
			// - calc definitions
			// - input result definitions
			TcAst id = node.getChild(0);
			TcAst arguments = (TcAst) node.getFirstChildWithType(TT_ARGDEF);
			int nargs = arguments != null ? arguments.getChildCount() : 0;
			TcAst parent = node.getParent();
			switch (parent.getType()) {
			case KEYWORD_FUNC: {
				String funcname = id.getText().toUpperCase();
				ScopedSymbol funcsymbol = new ScopedSymbol(
						"function:" + funcname,
						Symbol.SYMBOLTYPE_FUNCTION,
						funcname, 
						nargs,
						currentscope
				);
				funcsymbol.setAst(node);
				currentscope.define(funcsymbol);
				currentscope = funcsymbol;
				break;
			}
			case KEYWORD_INPUT: {
//				currentscope = model.combinedScope;
//				node.setScope(currentscope);

				TcAst inputid = (TcAst) parent.getFirstChildWithType(ID);
				String inputname = inputid.getText().toUpperCase();
				String propname = id.getText().toUpperCase();
				String inputpropname = inputname + "." + propname; 
				ScopedSymbol inputsymbol = new ScopedSymbol(
						"inputprop:" + inputpropname,
						Symbol.SYMBOLTYPE_INPUTRESULTDEF,
						propname,
						0,
						currentscope
				);
				inputsymbol.setAst(node);
				inputsymbol.define(new SymbolImpl("index", Symbol.SYMBOLTYPE_PARAMETER, 0));
				inputsymbol.define(new SymbolImpl("index2", Symbol.SYMBOLTYPE_PARAMETER, 0));
				if (propname.equals("FILTER")) {
					inputsymbol.define(new SymbolImpl("key", Symbol.SYMBOLTYPE_PARAMETER, 0));
				} else if (propname.equals("DISPLAYTEXT")) {
					inputsymbol.define(new SymbolImpl("key", Symbol.SYMBOLTYPE_PARAMETER, 0));
					inputsymbol.define(new SymbolImpl("text", Symbol.SYMBOLTYPE_PARAMETER, 0));
				}
				currentscope.define(inputsymbol);
				currentscope = inputsymbol;
				break;
			}
			case KEYWORD_CALC: {
				String calcname = id.getText().toUpperCase();
				ScopedSymbol calcsymbol = new ScopedSymbol(
						"calc:" + calcname,
						Symbol.SYMBOLTYPE_NODERESULTDEF,
						calcname,
						nargs,
						currentscope
				);
				calcsymbol.setAst(node);
				currentscope.define(calcsymbol);
				currentscope = calcsymbol;
				break;
			}
			default: 
				throw new RuntimeException("invalid parent type for TT_RESULTDEF: " + parent.getType() + " (" + TcSimpleParser.tokenNames[parent.getType()] + ")");
			}
			
			visit(id);
			if (arguments!=null && arguments.getChildCount()>0) {
				// TT_ARGDEF
				visit(arguments);
			}
			TcAst formula = node.getChild(node.getChildCount()-1);
			visit(formula);
			
			
			currentscope = savedscope;
			break;
		}
		case TT_TABLELINE: {
			// ^(TT_TABLELINE tablecell*)
			for (int i=0; i<node.getChildCount(); i++) {
				if (i>0) {
				}
				TcAst child = node.getChild(i);
				// tablecell: constant|ID
				visit(child);
			}
			break;
		}
		case TT_IDLIST: {
			node.setScope(currentscope);
			//^(TT_IDLIST colname*)
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				//ID | NUMBER | STRING
				visit(child);
			}
			break;
		}
		case TT_ARGDEF: {
			node.setScope(currentscope);
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst id = node.getChild(i);
				//ID
				currentscope.define(new SymbolImpl(id.getText(), Symbol.SYMBOLTYPE_PARAMETER, 0));
				id.setScope(currentscope);
				visit(id);
			}
			break;
		}
		case TT_PARAMETERS: {
			node.setScope(currentscope);
			if (node.getChildCount()==0) {
			} else {
				for (int i=0; i<node.getChildCount(); i++) {
					TcAst formula = node.getChild(i);
					//formula
					visit(formula);
				}
			}
			break;
		}
		case TT_INDEX: {
			node.setScope(currentscope);
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				//formula
				visit(child);
			}
			break;
		}
		case TT_INPUTCALCCALLSIMPLE: {
			//^(TT_INPUTCALCCALLSIMPLE ID id)
			node.setScope(currentscope);
			TcAst idname = node.getChild(0);
			TcAst idprop = node.getChild(1);
			
			if (idname.getText().equalsIgnoreCase("SELF")) {
				TcAst input = (TcAst) node.getAncestor(KEYWORD_INPUT);
				if (input!=null) {
					TcAst inputid = input.getChild(0);
					idname.getToken().setText(inputid.getText());
				}
			}
			visit(idname);
			visit(idprop);
			break;
		}
		case TT_INPUTORTABACCESSWITHINDEX: {
			node.setScope(currentscope);
			// ^(TT_ATTRTABACCESSWITHINDEX ID index columnaccess?)
			TcAst idname = node.getChild(0);
			if (idname.getText().equalsIgnoreCase("SELF")) {
				TcAst input = (TcAst) node.getAncestor(KEYWORD_INPUT);
				if (input!=null) {
					TcAst inputid = input.getChild(0);
					idname.getToken().setText(inputid.getText());
				}
			}
			TcAst index = node.getChild(1);
			TcAst columnaccess=null; 
			if (node.getChildCount()>2) {
				columnaccess = node.getChild(2);
			}
			visit(idname);
			visit(index);
			if (columnaccess!=null) {
				// TT_COLNAMESTATIC or TT_COLNAMEFORMULA
				visit(columnaccess);
			}
			
			break;
		}
		case TT_COLNAMESTATIC: {
			node.setScope(currentscope);
			TcAst idname = node.getChild(0);
			visit(idname);
			break;
		}
			
		case TT_COLNAMEFORMULA: {
			node.setScope(currentscope);
			TcAst formula = node.getChild(0);
			visit(formula);
			break;
		}

		case TT_DYNTABLE: {
			node.setScope(currentscope);
			//^(TT_DYNTABLE dyntableformula index? columnaccess?)
			TcAst dyntableformula = node.getChild(0);
			TcAst index = node.getChildCount()>1 ? node.getChild(1) : null;
			TcAst columnaccess = node.getChildCount()>2 ? node.getChild(2) : null;
			visit(dyntableformula);
			
			//index: TT_INDEX
			if (index!=null) {
				visit(index);
				//columnaccess: TT_COLUMNNAME*
				if (columnaccess!=null) {
					visit(columnaccess);
				}
			}
			
			break;
		}
		case KEYWORD_IF: 
		case QUESTIONMARK: {
			node.setScope(currentscope);
			//^(KEYWORD_IF formulaCond formulaThen formulaElse)
			TcAst cond = node.getChild(0);
			TcAst thenformula = node.getChild(1);
			TcAst elseformula = node.getChild(2);
			visit(cond);
			visit(thenformula);
			visit(elseformula);
			break;
		}
		case TT_FUNORCALCCALL: {
			node.setScope(currentscope);
			TcAst id = node.getChild(0);
			TcAst parameterListe = node.getChild(1);
			visit(id);
			visit(parameterListe);
			break;
		}
		case KEYWORD_COLLATE: {
			// ^(KEYWORD_COLLATE id formulaarguments*)
			node.setScope(currentscope);
			TcAst id = node.getChild(0);
			visit(id);
			for (int i=1; i<node.getChildCount(); i++) {
				visit(node.getChild(i));
			}
			break;
		}
		case KEYWORD_EXTRACT: {
			// ^(KEYWORD_EXTRACT id formulaarguments* formulaindex)
			node.setScope(currentscope);
			TcAst id = node.getChild(0);
			visit(id);
			for (int i=1; i<node.getChildCount(); i++) {
				visit(node.getChild(i));
			}
			break;
		}
		case KEYWORD_SUMX: {
			// ^(KEYWORD_SUMX id formulafrom formulato formulaloop)
			node.setScope(currentscope);
			TcAst id = node.getChild(0);
			TcAst from = node.getChild(1);
			TcAst to = node.getChild(2);
			TcAst loop = node.getChild(3);
			
			visit(from);
			visit(to);

			/* loop-part is executed in new scope */
			ScopeSimple newscope = new ScopeSimple("sumx_" + id.getText(), currentscope);
			newscope.define(new SymbolImpl(id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
//			newscope.define(new SymbolImpl("_LOOPTO_"  + id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
			newscope.define(new SymbolImpl("_RESULT_" + id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
			newscope.define(new SymbolImpl("_COUNTER_" + id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
			currentscope = newscope;
			visit(loop);
			currentscope = newscope.getParent();
			break;
		}
		case KEYWORD_PRODX: {
			// ^(KEYWORD_SUMX id formulafrom formulato formulaloop)
			node.setScope(currentscope);
			TcAst id = node.getChild(0);
			TcAst from = node.getChild(1);
			TcAst to = node.getChild(2);
			TcAst loop = node.getChild(3);
			
			visit(from);
			visit(to);

			/* loop-part is executed in new scope */
			ScopeSimple newscope = new ScopeSimple("sumx_" + id.getText(), currentscope);
			newscope.define(new SymbolImpl(id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
//			newscope.define(new SymbolImpl("_LOOPTO_"  + id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
			newscope.define(new SymbolImpl("_RESULT_" + id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
			newscope.define(new SymbolImpl("_COUNTER_" + id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
			currentscope = newscope;
			visit(loop);
			currentscope = newscope.getParent();
			break;
		}
		case KEYWORD_VECTORX: {
			// ^(KEYWORD_SUMX id formulafrom formulato formulaloop)
			node.setScope(currentscope);
			TcAst id = node.getChild(0);
			TcAst from = node.getChild(1);
			TcAst to = node.getChild(2);
			TcAst loop = node.getChild(3);
			
			visit(from);
			visit(to);
			
			/* loop is executed in new scope */
			ScopeSimple newscope = new ScopeSimple("vectorx_" + id.getText(), currentscope);
			newscope.define(new SymbolImpl(id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
//			newscope.define(new SymbolImpl("_LOOPTO_"  + id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
			newscope.define(new SymbolImpl("_COUNTER_" + id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
			newscope.define(new SymbolImpl("_RESULT_" + id.getText(), Symbol.SYMBOLTYPE_LOCALVAR, 0));
			currentscope = newscope;
			visit(loop);
			currentscope = newscope.getParent();
			break;
		}
		
		case TT_USEID: {
			node.setScope(currentscope);
			TcAst id = node.getChild(0);
			if (id.getText().equalsIgnoreCase("SELF")) {
				TcAst input = (TcAst) node.getAncestor(KEYWORD_INPUT);
				if (input!=null) {
					TcAst inputid = input.getChild(0);
					id.getToken().setText(inputid.getText());
				}
			}
			visit(id);
			break;
		}
		/* constants */
		case STRING:
			node.setScope(currentscope);
			break;
		case NUMBER:
			node.setScope(currentscope);
			break;
		case KEYWORD_TABROWS:
		case KEYWORD_TABCOLS: {
			node.setScope(currentscope);
			TcAst tab = node.getChild(0); 
			visit(tab);
			break;
		}
		case KEYWORD_CELL:
		case KEYWORD_CELLX: {
			node.setScope(currentscope);
			TcAst tab = node.getChild(0);
			TcAst rangeFrom = node.getChild(1);
			TcAst rangeTo   = node.getChild(2);
			visit(tab);
			visit(rangeFrom);
			visit(rangeTo);
			break;
		}
		case TT_RANGE: {
			TcAst from = node.getChild(0);
			TcAst to   = node.getChildCount()>1 ? node.getChild(1) : null;
			
			visit(from);
			if (to!=null) {
				visit(to);
			}
			break;
		}
		case KEYWORD_EXISTS: {
			node.setScope(currentscope);
			TcAst tab = node.getChild(0);
			visit(tab);
			for (int i=1; i<node.getChildCount(); i++) {
				TcAst keyformula = node.getChild(i);
				visit(keyformula);
			}
			break;
		}
		case KEYWORD_LOOKUP:
		case KEYWORD_INTERPOL:
		case KEYWORD_LOOKUPX:
		case KEYWORD_LOOKDOWNX: 
		case KEYWORD_DOCALL:
		case KEYWORD_FUNCREF:
		case KEYWORD_COUNTERLIST: {
			node.setScope(currentscope);
			for (int i=0; i<node.getChildCount(); i++) {
				visit(node.getChild(i));
			}
			break;
		}
		default:
			throw new RuntimeException("unknown token type: " + tokentype + " (" + TcSimpleParser.tokenNames[tokentype] + "), line " + node.getLine() + ": " +node.getText());
		}
		return null;
	}

	public void unop(TcAst node) {
		if (node.getChildCount()!=1) {
			throw new RuntimeException("invalid number of childs for " + node.getText() + ", tokentype " + node.getType() + ", line " + node.getLine() + ": " +node.getChildCount());
		}
		TcAst op = node.getChild(0);
		visit(op);
	}

	public void binop(TcAst node) {
		if (node.getChildCount()!=2) {
			throw new RuntimeException("invalid number of childs for " + node.getText() + ", tokentype " + node.getType() + ", line " + node.getLine() + ": " +node.getChildCount());
		}
		TcAst left = node.getChild(0);
		TcAst right = node.getChild(1);
		visit(left);
		visit(right);
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
				if (!link[i].equals(candidate[i])) { 
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

	
	public static void build(ModelSimple model)  {
		FillModelStep1_Symboltables obj = new FillModelStep1_Symboltables(model);
		obj.run();
	}
}
