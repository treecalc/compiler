/* use symbol tables / scopes to resolve names */

package treecalc.comp; 

import java.util.List;

import org.antlr.runtime.CommonToken;

import static treecalc.comp.TcSimpleParser.*;

public class FillModelStep2_ResolveNames {
	private final ModelSimple model;

	private FillModelStep2_ResolveNames(ModelSimple model) {
		this.model = model;
	}

	private void run() {
		visit(model.getRoot());
	}
	
	@SuppressWarnings("unchecked")
	private void visit(TcAst node) {
		int tokentype = node.getType();
		switch (tokentype) {
		case 0: { //list of elements -> ?
			throw new RuntimeException("did not expect list (token type 0): " + ", line " + node.getLine() + ": " +node.getText());
		}
		case TT_COMPUNIT: {
			List<TcAst> children = node.getChildren();
			for (TcAst child : children) {
				//can be one of KEYWORD_TREE, KEYWORD_CALC, KEYWORD_INPUT, KEYWORD_FUNC, KEYWORD_TABLE
				visit(child);
			}
			break;
		}
		case KEYWORD_TREE: {
			// ^(KEYWORD_TREE nodepath nodeinfo*)
			TcAst nodepath = node.getChild(0);
			//TT_NODEPATH
			visit(nodepath);
			for (int i=1; i<node.getChildCount(); i++) {
				TcAst nodeinfo = node.getChild(i);
				// KEYWORD_NODE oder KEYWORD_LINK
				visit(nodeinfo);
			}
			break;
		}
		case KEYWORD_NODE: {
			// ^(KEYWORD_NODE nodename ^(KEYWORD_AS id)? nodeinfo*)
			TcAst nodename = node.getChild(0);
			//constant|id
			//don't need to visit visit(nodename);
			
			int nodeind=1;
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
			visit(formula);
			break;
		}
		case TT_TIMESINFO: {
			//^(TT_TIMESINFO formula id)
			TcAst formula = node.getChild(0);
			TcAst id  = node.getChild(1);
			visit(formula);
			//don't need to visit id */
			break;
		}

		case ID: {
			break;
		}
		case KEYWORD_LINK: {
			TcAst nodepath = node.getChild(0);
			visit(nodepath);
			//^(KEYWORD_LINK ^(TT_NODEPATH linkpart*))
			//linkpart: ID | STRING | ASTERISK | DOUBLEASTERISK 
			;
			break;
		}
		case TT_NODEPATH: {
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst nodeid = node.getChild(i);
				visit(nodeid);
			}
			break;
		}
		case KEYWORD_CALC: {
			//^(KEYWORD_CALC  nodepath resultdef*)
			TcAst nodepath = node.getChild(0);
			visit(nodepath);
			for (int i=1; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				//TT_RESULTDEF
				visit(child);
			}
			break;
		}
			
		case KEYWORD_INPUT: {
			// ^(KEYWORD_INPUT id resultdef*)
			TcAst id = node.getChild(0);
			visit(id);
			if (node.getChildCount()>1) {
				for (int i=1; i<node.getChildCount(); i++) {
					TcAst child = node.getChild(i);
					//TT_RESULTDEF 
					visit(child);
				}
			} else {
			}
			break;
		}
		
		case KEYWORD_FUNC: {
			// ^(KEYWORD_FUNC resultdef)
			//   resultdef: ^(TT_RESULDEF ...)
			TcAst resultdef = node.getChild(0);
			visit(resultdef);
			break;
		}
		
		case KEYWORD_TABLE: {
			//^(KEYWORD_TABLE id colnames tableline*)
			TcAst id = node.getChild(0);
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
			binop(node);
			break;
		}
		
		case PLUS: 
		case MINUS: {
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
			if (node.getChildCount()==0) {
				/* usage in path */
			} else {
				binop(node);
			}
			break;
			

		case DOUBLEASTERISK:
			break;
			
		case KEYWORD_CASE: {
			//^(KEYWORD_CASE formula casewhen* casedefault?)
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
			TcAst casecompares = node.getChild(0);
			TcAst formula = node.getChild(1);
			visit(casecompares);
			visit(formula);
			break;
		}
		case TT_CASECONDITION: {
			// ^(TT_CASECONDITION casecompare*)
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				// TT_CASECOMPARISON
				visit(child);
			}
			break;
		}
		case TT_CASECOMPARISON: {
			// ^(TT_CASEONECOMPARISON constant) 
			TcAst constant = node.getChild(0);
			visit(constant);
			break;
		}

		case TT_CASERANGE: {
			// ^(TT_CASERANGE      caseconstant*)
			TcAst constantFrom = node.getChild(0);
			TcAst constantTo = node.getChild(1);
			visit(constantFrom);
			visit(constantTo);
			break;
		}
		case KEYWORD_DEFAULT: {
			// ^(KEYWORD_DEFAULT formula
			TcAst formulaDefault = node.getChild(0);
			visit(formulaDefault);
		}
		
		case KEYWORD_AS: {
			// ^(KEYWORD_AS id)
			TcAst nodeid = node.getChild(0);
//			visit(nodeid);
			break;
		}
		case TT_RESULTDEF: {
			//^(TT_RESULTDEF ID arguments? formula)
			//used for
			// - function definition
			// - calc definitions
			// - input result definitions
			TcAst id = node.getChild(0);
			TcAst parent = node.getParent();
			visit(id);
			TcAst arguments = node.getFirstChildWithType(TT_ARGDEF);
			if (arguments!=null && arguments.getChildCount()>0) {
				// TT_ARGDEF
				visit(arguments);
			}
			TcAst formula = node.getChild(node.getChildCount()-1);
			visit(formula);
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
			//^(TT_IDLIST colname*)
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				//ID | NUMBER | STRING
				visit(child);
			}
			break;
		}
		case TT_ARGDEF: {
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst id = node.getChild(i);
				//ID
				visit(id);
			}
			break;
		}
		case TT_PARAMETERS: {
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
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child = node.getChild(i);
				//formula
				visit(child);
			}
			break;
		}
		case TT_INPUTCALCCALLSIMPLE: {
			//^(TT_INPUTCALCCALLSIMPLE ID id)
			TcAst idname = node.getChild(0);
			TcAst idprop = node.getChild(1);
			String inputname = idname.getText().toUpperCase();
			String propname = idprop.getText().toUpperCase();

			/* replace attributeproperty NAME with name of attribute if that is
			 * used inside an attributeproperty of the own attribute
			 */
			if (propname.equals("NAME") && node.hasAncestor(KEYWORD_INPUT)) {
				TcAst input = node.getAncestor(KEYWORD_INPUT);
				TcAst inputid = input.getChild(0);
				String usedinInputname = inputid.getText();
				if (inputname.equalsIgnoreCase(usedinInputname)) {
					node.deleteChild(1);
					node.deleteChild(0);
					node.token = new CommonToken(STRING, usedinInputname);
					break;
				}
			}
			
			Scope scopeinput = idname.getScope();
			Symbol symbol = scopeinput.resolve(inputname, 0, false);
			if (symbol==null) {
				throw new RuntimeException("TT_INPUTCALCCALLSIMPLE: could not resolve " + inputname + " in " + scopeinput.getFullScopeName() + " line " + node.getLine());
			} else {
				ScopedSymbol scopedsymbol = (ScopedSymbol) symbol;
				Symbol symbolinputprop = scopedsymbol.resolve(propname, 0, false);
				if (symbolinputprop==null) {
					throw new RuntimeException("TT_INPUTCALCCALLSIMPLE: could not resolve calc " + propname + " within input " + inputname + " in " + scopeinput.getFullScopeName() + " line " + node.getLine());
				} 
			}
			break;
		}
		case TT_INPUTACCESSSIMPLE: {
			// ^(TT_INPUTACCESSSIMPLE ID)
			TcAst id = node.getChild(0);
			String idname = id.getText().toUpperCase();
			Scope scope = node.getScope();
			Symbol symbol = scope.resolve(idname, 0, false);
			if (symbol==null) {
				throw new RuntimeException("could not resolve " + idname + ", line " + id.getLine() + ", in scope " + scope.getFullScopeName() + ": " + scope.getScopeDefines());
			}
			if (node.hasAncestor(KEYWORD_INPUT)) {
				TcAst input = node.getAncestor(KEYWORD_INPUT);
				TcAst inputid = input.getChild(0);
				String usedinInputname = inputid.getText();
				if (idname.equalsIgnoreCase(usedinInputname)) {
					TcAst astCalc = node.getAncestor(TT_RESULTDEF);
					String calcName = astCalc.getChild(0).getText().toUpperCase();
					if (calcName.equalsIgnoreCase("CHECK")) {
						node.token.setType(TT_INPUTACCESSRAWSELF);
					}
				}
			}
			break;
		}
		case TT_INPUTORTABACCESSWITHINDEX: {
			// ^(TT_ATTRTABACCESSWITHINDEX ID index columnaccess?)
			TcAst id = node.getChild(0);
			TcAst index = node.getChild(1);
			TcAst columnaccess=null; 
			if (node.getChildCount()>2) {
				columnaccess = node.getChild(2);
			}
			String idname = id.getText().toUpperCase();
			Scope scope = node.getScope();
			Symbol symbol = scope.resolve(idname, 0, false);
			if (symbol==null) {
				throw new RuntimeException("could not resolve " + idname + ", line " + id.getLine() + ", in scope " + scope.getFullScopeName() + ": " + scope.getScopeDefines());
			}
			switch(symbol.getSymboltype()) {
			case Symbol.SYMBOLTYPE_INPUT:
				node.getToken().setType(TT_INPUTACCESS);
				break;
			case Symbol.SYMBOLTYPE_TABLE:
				node.getToken().setType(TT_TABLEACCESS);
				break;
			default:
				System.out.println("unexpected symboltype " + symbol.getSymboltype() + " for TT_INPUTORTABACCESSWITHINDEX, symbol " + symbol.getSymbolName() + " line " + node.getLine());
			}

			/* replace attributeproperty NAME with name of attribute if that is
			 * used inside an attributeproperty of the own attribute
			 */
			if (node.getType()==TT_INPUTACCESS && columnaccess!=null) {
				// TT_COLNAMESTATIC id
				TcAst namestatic = columnaccess.getChild(0);
				String propname = namestatic.getText().toUpperCase();
				if (propname.equals("NAME") && node.hasAncestor(KEYWORD_INPUT)) {
					TcAst input = node.getAncestor(KEYWORD_INPUT);
					TcAst inputid = input.getChild(0);
					String usedinInputname = inputid.getText();
					if (idname.equalsIgnoreCase(usedinInputname)) {
						node.deleteChild(2);
						node.deleteChild(1);
						node.deleteChild(0);
						node.token = new CommonToken(STRING, usedinInputname);
						break;
					}
				}
			}
			/* call to itself in check property -> change to unchecked! */
			if (node.getType()==TT_INPUTACCESS && columnaccess==null && node.hasAncestor(KEYWORD_INPUT)) {
				TcAst input = node.getAncestor(KEYWORD_INPUT);
				TcAst inputid = input.getChild(0);
				String usedinInputname = inputid.getText();
				if (idname.equalsIgnoreCase(usedinInputname)) {
					TcAst astCalc = node.getAncestor(TT_RESULTDEF);
					String calcName = astCalc.getChild(0).getText().toUpperCase();
					if (calcName.equalsIgnoreCase("CHECK")) {
						// TT_COLNAMESTATIC id
						CommonToken tokenUnchecked = new CommonToken(ID, "unchecked");
						TcAst astIdUnchecked = new TcAst(tokenUnchecked);
						astIdUnchecked.setScope(node.getScope());
						CommonToken tokenNameStatic = new CommonToken(TT_COLNAMESTATIC);
						TcAst astNameStatic = new TcAst(tokenNameStatic);
						astNameStatic.addChild(astIdUnchecked);
						astNameStatic.setScope(node.getScope());
						columnaccess = astNameStatic;
						node.addChild(columnaccess);
					}
				}
			}

			visit(id);
			visit(index);
			if (columnaccess!=null) {
				// TT_COLNAMESTATIC or TT_COLNAMEFORMULA
				visit(columnaccess);
			}
			
			break;
		}
		case TT_COLNAMESTATIC: {
			TcAst idname = node.getChild(0);
			visit(idname);
			break;
		}
			
		case TT_COLNAMEFORMULA: {
			TcAst formula = node.getChild(0);
			visit(formula);
			break;
		}

		case TT_DYNTABLE: {
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
			TcAst id = node.getChild(0);
			TcAst parameterListe = node.getChild(1);
			int nargs = parameterListe.getChildCount();
			String name = id.getText().toUpperCase();
			
			Scope scope = node.getScope();
			boolean selfcalccall = false;
			TcAst astCalc = node.getAncestor(KEYWORD_CALC); //see if the call to the property is within a node
			if (astCalc!=null) {
				TcAst astResult = node.getAncestor(TT_RESULTDEF);
				String definedinname = astResult.getChild(0).getText().toUpperCase();
				selfcalccall = name.equalsIgnoreCase(definedinname);
			}
			Symbol symbol = scope.resolve(name, nargs, selfcalccall);
			if (symbol==null) { //try without nargs information -> to find builtin, ...
				symbol = scope.resolve(name, 0, false);
			}
			if (symbol==null) {
				scope.resolve(name,  nargs, selfcalccall);
				scope.resolve(name,  0, false);
				throw new RuntimeException("TT_FUNORCALCCALL: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			}

			switch(symbol.getSymboltype()) {
			case Symbol.SYMBOLTYPE_BULITIN:
				node.getToken().setType(TT_BUILTIN);
				break;
			case Symbol.SYMBOLTYPE_FUNCTION:
				node.getToken().setType(TT_FUNCALL);
				break;
			case Symbol.SYMBOLTYPE_NODERESULTDEF:
				node.getToken().setType(TT_CALCCALL);
				break;
			default:
				throw new RuntimeException("unexpected symboltype " + symbol.getSymboltype() + " for TT_FUNORCALCCALL, symbol " + symbol.getSymbolName() + " line " + node.getLine());
			}
			//visit(id);
			visit(parameterListe);
			break;
		}
		case KEYWORD_SUMX: {
			TcAst id = node.getChild(0);
			TcAst formulafrom = node.getChild(1);
			TcAst formulato = node.getChild(2);
			TcAst formulaloop = node.getChild(3);
			visit(formulafrom);
			visit(formulato);
			visit(formulaloop);
			break;
		}
		case KEYWORD_VECTORX: {
			TcAst id = node.getChild(0);
			TcAst formulafrom = node.getChild(1);
			TcAst formulato = node.getChild(2);
			TcAst formulaloop = node.getChild(3);
			visit(formulafrom);
			visit(formulato);
			visit(formulaloop);
			break;
		}
		case KEYWORD_COLLATE: {
			TcAst id = node.getChild(0);
			int nargs = node.getChildCount()-1;
			Scope scope = id.getScope();
			String name = id.getText().toUpperCase();
			Symbol symbol = scope.resolve(name, nargs, false);
			if (symbol==null) {
				System.out.println("COLLATE: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + id.getLine());
				break;
			}
			for (int i=1; i<node.getChildCount(); i++) {
				visit(node.getChild(i));
			}
			break;
		}
		case KEYWORD_EXTRACT: {
			TcAst id = node.getChild(0);
			int nargs = node.getChildCount()-2;
			Scope scope = id.getScope();
			String name = id.getText().toUpperCase();
			Symbol symbol = scope.resolve(name, nargs, false);
			if (symbol==null) {
				System.out.println("COLLATE: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + id.getLine());
				break;
			}
			for (int i=1; i<node.getChildCount(); i++) {
				visit(node.getChild(i));
			}
			break;
		}
		
		case TT_USEID: {
			TcAst id = node.getChild(0);
			Scope scope = id.getScope();
			String name = id.getText().toUpperCase();
			boolean selfcalccall = false;
			TcAst astCalc = node.getAncestor(KEYWORD_CALC); //see if the call to the property is within a node
			if (astCalc!=null) {
				TcAst astResult = node.getAncestor(TT_RESULTDEF);
				String definedinname = astResult.getChild(0).getText().toUpperCase();
				selfcalccall = name.equalsIgnoreCase(definedinname);
			}

			Symbol symbol = scope.resolve(name, 0, selfcalccall);
			if (symbol==null) {
				symbol = scope.resolve(name, 0, selfcalccall);
				throw new RuntimeException("TT_USEID: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + id.getLine());
			}
			switch (symbol.getSymboltype()) {
			case Symbol.SYMBOLTYPE_BULITIN:
				node.getToken().setType(TT_BUILTIN);
				break;
			case Symbol.SYMBOLTYPE_FUNCTION:
				node.getToken().setType(TT_FUNCALL);
				break;
			case Symbol.SYMBOLTYPE_NODERESULTDEF:
				node.getToken().setType(TT_CALCCALL);
				break;
			case Symbol.SYMBOLTYPE_INPUT:
				node.getToken().setType(TT_INPUTACCESSSIMPLE);
				visit(node); //visit again
				break;
			case Symbol.SYMBOLTYPE_PARAMETER:
				node.getToken().setType(TT_USEVAR_PARAMETER);
				break;
			case Symbol.SYMBOLTYPE_TIMESINDEX:
				node.getToken().setType(TT_USEVAR_TIMESINDEX);
				break;
			case Symbol.SYMBOLTYPE_LOCALVAR:
				node.getToken().setType(TT_USEVAR_LOCAL);
				break;
			case Symbol.SYMBOLTYPE_TABLE:
				node.getToken().setType(TT_TABREF);
				break;
			case Symbol.SYMBOLTYPE_INPUTRESULTDEF:
			case Symbol.SYMBOLTYPE_TABLECOLUMN:
			default:
				System.out.println("unexpected symboltype " + symbol.getSymboltype() + " for TT_USEDID, symbol " + symbol.getSymbolName() + " line " + node.getLine());
			}
			break;
		}
		/* constants */
		case STRING:
			break;
		case NUMBER:
			break;
		case KEYWORD_TABROWS:
		case KEYWORD_TABCOLS: {
			TcAst tab = node.getChild(0); 
			visit(tab);
			break;
		}
		case KEYWORD_CELL:
		case KEYWORD_CELLX: {
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
		{
			for (int i=0; i<node.getChildCount(); i++) {
				visit(node.getChild(i));
			}
			break;
		}
		case KEYWORD_FUNCREF: {
			//^(KEYWORD_FUNCREF formula)
			TcAst astFuncid = node.getChild(0);
			if (astFuncid.getType()!=TT_USEID) {
				visit(astFuncid);
			} else {
				String name = astFuncid.getChild(0).getText().toUpperCase();
				Scope scope = node.getScope();
				Symbol symbol = scope.resolve(name, -1, false);
				if (symbol==null) {
					scope.resolve(name,  0, false);
					throw new RuntimeException("KEYWORD_FUNCREF: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
				}
				switch(symbol.getSymboltype()) {
				case Symbol.SYMBOLTYPE_FUNCTION:
					node.getToken().setType(TT_FUNCALL);
					break;
				default:
					throw new RuntimeException("unexpected symboltype " + symbol.getSymboltype() + " for TT_FUNORCALCCALL, symbol " + symbol.getSymbolName() + " line " + node.getLine());
				}
			}
			break;
			
		}
		case KEYWORD_COUNTERLIST: {
			Scope scope = node.getScope();
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst id = node.getChild(i);
				String name = id.getText().toUpperCase();
				Symbol symbol = scope.resolve(name, 0, false);
				if (symbol==null) {
					//delete it if not defined!
					node.deleteChild(i);
					i--;
				} else {
					if (symbol.getSymboltype()!=Symbol.SYMBOLTYPE_TIMESINDEX)  {
						System.out.println("unexpected symboltype in counterlist: " + name + " is something else, but not a autocounter. Check your 'times' definition. Autocounter is in line " + id.getLine());
					}
				}
			}
			break;
		}
			
		default:
			throw new RuntimeException("unknown token type: " + tokentype + " (" + TcSimpleParser.tokenNames[tokentype] + "), line " + node.getLine() + ": " +node.getText());
		}
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
	
	public static void build(ModelSimple model) {
		FillModelStep2_ResolveNames obj = new FillModelStep2_ResolveNames(model);
		obj.run();
	}
}
