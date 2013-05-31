package treecalc.comp.vm; 

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import treecalc.comp.GenFunction;
import treecalc.comp.GenInput;
import treecalc.comp.GenTable;
import treecalc.comp.ModelSimple;
import treecalc.comp.PrintFormulaInfos;
import treecalc.comp.ResolvedCalc;
import treecalc.comp.Scope;
import treecalc.comp.ScopedSymbol;
import treecalc.comp.Symbol;
import treecalc.comp.TcAst;
import treecalc.comp.parser.TcSimpleParser;


import static treecalc.comp.parser.TcSimpleParser.*;

import treecalc.vm.asm.BFInfo;

public class VmFormula {
	private static PrintStream out;
	private final TcAst astFormula;
	private final ModelSimple model;
	private HashMap<TcAst,HashMap<String,String>> nodedata = new HashMap<TcAst,HashMap<String,String>>();
	private int labelcounter=0;
	private PrintFormulaInfos printFormulaInfos = new PrintFormulaInfos();

	private static HashMap<Integer, String> op = new HashMap<Integer,String>();
	static {
//		op.put(LOGICAL_OR              , "or"      ); need extra care
//		op.put(LOGICAL_AND             , "and"     ); need extra care
		op.put(LOGICAL_XOR             , "xor"     );
		op.put(COMPARE_EQUAL           , "eq"      ); // =     compare numerical equal
		op.put(COMPARE_EQUAL_CSTYLE    , "eq"      ); // ==     
		op.put(COMPARE_SMALLER         , "cmpsml"  ); // <     compare numerical less
		op.put(COMPARE_BIGGER          , "cmpbig"  ); // >     compare numerical greater
		op.put(COMPARE_LESSEQUAL       , "cmpsmleq"); // <=    compare numerical less equal
		op.put(COMPARE_BIGGEREQUAL     , "cmpbigeq"); // >=    compare numerical greater equal
		op.put(COMPARE_NOTEQUAL        , "cmpneq"  ); // <>    compare numerical not equal
		op.put(COMPARE_NOTEQUAL_CSTYLE , "cmpneq"  ); // <>   
		op.put(COMPARE_STR_EQUAL       , "cmpseq"     ); // s=    compare string equal
		op.put(COMPARE_STR_NOTEQUAL    , "cmpsneq"    ); // s<>   compare string not equal
		op.put(COMPARE_STR_ALIKE       , "cmpseqi"    ); // si=   compare string equal caseinsensitive
		op.put(COMPARE_STR_NOTALIKE    , "cmpsneqi"   ); // si<>  compare string not equal caseinsensitive
		op.put(COMPARE_STR_BEFORE      , "cmpsl"      ); // s<    compare string less
		op.put(COMPARE_STR_NOTBEFORE   , "cmpsgeq"    ); // s>=   compare string greater equal
		op.put(COMPARE_STR_AHEAD       , "cmpsli"     ); // si<   compare string less caseinsensitive
		op.put(COMPARE_STR_NOTAHEAD    , "cmpsgeqi"   ); // si>=  compare string greater equal caseinsensitive
		op.put(COMPARE_STR_BEHIND      , "cmpsg"      ); // s>    compare string greater 
		op.put(COMPARE_STR_NOTBEHIND   , "cmpsleq"    ); // s<=   compare string less equal
		op.put(COMPARE_STR_AFTER       , "cmpsgi"     ); // si>   compare string greater caseinsensitive
		op.put(COMPARE_STR_NOTAFTER    , "cmpsleqi"   ); // si<=  compare string less equal caseinsensitive
		op.put(STRCAT                  , "sappend" ); // &     string append  
		op.put(ASTERISK                , "mult"    ); // * 
		op.put(SLASH                   , "div"     ); // /
		op.put(DIV                     , "divint"  ); // DIV
		op.put(MOD                     , "modint"  ); // MOD
		op.put(POWER                   , "power"   ); // ^
		op.put(PLUS                    , "add"     ); 
		op.put(MINUS                   , "sub"     );
	}
	private static HashMap<Integer, String> unop = new HashMap<Integer,String>();
	static {
		unop.put(PLUS , "unplus" );
		unop.put(MINUS, "unminus");
	}
	
	private VmFormula(PrintStream out, TcAst astFormula, ModelSimple model) {
		VmFormula.out = out;
		this.astFormula = astFormula;
		this.model = model;
	}

	private String newLabel() {
		return "L" + labelcounter++;
	}
	private void setLabel(TcAst node, String what, String label) {
		HashMap<String, String> x = this.nodedata.get(node);
		if (x==null) {
			x = new HashMap<String,String>();
			this.nodedata.put(node, x);
		}
		x.put(what, label);
	}
	private String getLabel(TcAst node, String what) {
		HashMap<String, String> x = this.nodedata.get(node);
		if (x==null) {
			return null;
		}
		return x.get(what);
	}

	private void visit(TcAst node) {
		if (node==null) {
			return;
		}
		int tokentype = node.getType();
		switch (tokentype) {
		case LOGICAL_OR: {
			or(node);
			break;
		}
		case LOGICAL_AND: {
			and(node);
			break;
		}
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
		case ASTERISK:
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

		case KEYWORD_CASE: {
			//^(KEYWORD_CASE formula casewhen* casedefault?)
			boolean hasDefault = false;
			TcAst formula =  node.getChild(0);
			visit(formula); //stack: comparevalue
			ArrayList<String> labels = new ArrayList<String>();
			
			/* print conditions */
			for (int indstart=1, i=indstart; i<node.getChildCount(); i++) { //iterate over 'case' parts
				String label = newLabel();
				labels.add(label);
				TcAst child =  node.getChild(i);
				//KEYWORD_WHEN oder KEYWORD_DEFAULT
				switch (child.getType()) {
				case KEYWORD_WHEN: {
					//^(KEYWORD_WHEN casecompares formula)
					//casecompares: ^(TT_CASECONDITION casecompare*)
					//casecompare: ^(TT_CASECOMPARISON caseconstant) | ^(TT_CASERANGE caseconstant*)
					//caseconstant: STRING | NUMBER (NUMBER can be with leading '-')
					TcAst casecompares = child.getChild(0);
//					TcAst caseformula  = child.getChild(1);
					for (int indCompare=0; indCompare<casecompares.getChildCount(); indCompare++) {  
						out.println("   : dup"); //stack: comparevalue comparevalue
						TcAst casecompare = casecompares.getChild(indCompare); //comparison within 'case' part
						switch (casecompare.getType()) {
						case TT_CASECOMPARISON: {
							TcAst constant = casecompare.getChild(0);
							switch(constant.getType()) {
							case STRING: {
								out.print  ("   : pushconst \""); //stack: comparevalue comparevalue const
								out.print  (constant.getText());
								out.println('"');
								out.print  ("   : ifseqi "); //stack: comparevalue same_bool
								out.println(label);
								break;
							}
							case NUMBER: {
								out.print  ("   : pushconst ");
								out.println(Double.toString(Double.parseDouble(constant.getText())));
								out.print  ("   : ifnumequal ");
								out.println(label);
								break;
							}
							default: 
								throw new RuntimeException("expected STRING or NUMBER but got token type: " + constant.getType() + " (" + TcSimpleParser.tokenNames[constant.getType()] + "), line " + constant.getLine() + ": " +constant.getText());
							}
							break;
						}
						case TT_CASERANGE: {
							TcAst constantFrom = casecompare.getChild(0);
							TcAst constantTo   = casecompare.getChild(1);
							if (constantFrom.getType()!=constantTo.getType()) {
								throw new RuntimeException("in case range, types may not be mixed, but we got " + constantFrom.getType() + " and " + constantTo.getType() + ", line " + constantFrom.getLine());
							}
							switch(constantFrom.getType()) {
							case STRING: {
								out.print("   : pushconst \""); //stack: comparevalue comparevalue constFrom
								out.print(constantFrom.getText());
								out.println('"');
								out.print("   : cmpsgeqi "); //stack: comparevalue cmp1_bool
								out.println("   : over");     //stack: comparevalue cmp1_bool comparevalue
								out.print("   : pushconst \""); //stack: comparevalue cmp1_bool comparevalue constTo
								out.print(constantTo.getText());
								out.println('"');
								out.println("   : cmpsleqi "); //stack: comparevalue cmp1_bool cmp2_bool
								out.println("   : and"); //stack: comparevalue cmp_bool
								out.print("   : iftrue ");
								out.println(label);
								break;
							}
							case NUMBER: {
								out.print  ("   : pushconst "); //stack: comparevalue comparevalue constFrom
								out.println(Double.toString(Double.parseDouble(constantFrom.getText())));
								out.print  ("   : cmpbigeq "); //stack: comparevalue cmp1_bool
								out.println("   : over");     //stack: comparevalue cmp1_bool comparevalue
								out.print  ("   : pushconst \""); //stack: comparevalue cmp1_bool comparevalue constTo
								out.print  (Double.toString(Double.parseDouble(constantTo.getText())));
								out.println('"');
								out.println("   : cmpsmleq "); //stack: comparevalue cmp1_bool cmp2_bool
								out.println("   : and"); //stack: comparevalue cmp_bool
								out.print("   : iftrue ");
								out.println(label);
								break;
							}
							default: 
								throw new RuntimeException("expected STRING or NUMBER but got token type: " + constantFrom.getType() + " (" + TcSimpleParser.tokenNames[constantFrom.getType()] + "), line " + constantFrom.getLine() + ": " +constantFrom.getText());
							}
							break;
						}
						default: 
							throw new RuntimeException("expected TT_CASECOMPARISON or TT_CASERANGE but got token type: " + casecompare.getType() + " (" + TcSimpleParser.tokenNames[casecompare.getType()] + "), line " + casecompare.getLine() + ": " +casecompare.getText());
						}
					}
					break;
				}
				case KEYWORD_DEFAULT: {
					out.print("   : goto ");
					out.println(label);
					hasDefault=true;
					break;
				}
				default: 
					throw new RuntimeException("expected KEYWORD_WHEN or KEYWORD_DEFAULT but got token type: " + child.getType()+ " (" + TcSimpleParser.tokenNames[child.getType()] + "), line " + child.getLine() + ": " +child.getText());
				} //end of case
			}
			String labelEnd = newLabel();
			if (!hasDefault) {
				out.println("   : pushconst \": not found in case statement\"");
				out.println("   : sappend");
				out.println("   : error ");
				out.print  ("   : goto ");
				out.println(labelEnd);
			}
			/* print action parts */
			for (int indstart=1, i=indstart; i<node.getChildCount(); i++) { //iterate over 'case' parts
				TcAst child =  node.getChild(i);
				String label = labels.get(i-indstart); 
				//KEYWORD_WHEN oder KEYWORD_DEFAULT
				switch (child.getType()) {
				case KEYWORD_WHEN: {
					//^(KEYWORD_WHEN casecompares formula)
					//casecompares: ^(TT_CASECONDITION casecompare*)
					//casecompare: ^(TT_CASECOMPARISON caseconstant) | ^(TT_CASERANGE caseconstant*)
					//caseconstant: STRING | NUMBER (NUMBER can be with leading '-')
					TcAst caseformula  = child.getChild(1);
					out.print(" "); out.print(label); out.println(": ");
					out.println("   : pop"); //remove comparevalue from stack
					visit(caseformula);
					out.print  ("   : goto ");
					out.println(labelEnd);
					break;
				}
				case KEYWORD_DEFAULT: {
					//^(KEYWORD_DEFAULT formula)
					TcAst formulaDefault = child.getChild(0);
					out.print(" "); out.print(label); out.println(":");
					out.println("   : pop"); //remove comparevalue from stack
					visit(formulaDefault);
					out.print  ("   : goto ");
					out.println(labelEnd);
					break;
				}
				}
			}
			out.print(" ");	out.print(labelEnd); out.println(":");
			break;
		}

		case TT_PARAMETERS: {
			if (node.getChildCount()==0) {
			} else {
				for (int i=0; i<node.getChildCount(); i++) {
					TcAst formula =  node.getChild(i);
					//formula
					visit(formula);
				}
			}
			break;
		}
		case TT_INDEX: {
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child =  node.getChild(i);
				//formula
				visit(child);
			}
			break;
		}
		case TT_INPUTCALCCALLSIMPLE: {
			//^(TT_INPUTCALCCALL id id)
			TcAst idname =  node.getChild(0);
			TcAst idprop =  node.getChild(1);
			String inputname = idname.getText().toUpperCase();
			String propname = idprop.getText().toUpperCase();

			Scope scopeinput = idname.getScope();
			Symbol symbol = scopeinput.resolve(inputname, 0, false);
			if (symbol==null) {
				symbol = scopeinput.resolve(inputname, 0, false);
				throw new RuntimeException("TT_INPUTCALCCALL: could not resolve " + inputname + " in " + scopeinput.getFullScopeName() + " line " + node.getLine());
			} else {
				ScopedSymbol scopedsymbol = (ScopedSymbol) symbol;
				Symbol symbolinputprop = scopedsymbol.resolve(propname, 0, false);
				if (symbolinputprop==null) {
					throw new RuntimeException("TT_INPUTCALCCALL: could not resolve " + propname + " within input " + inputname + " in " + scopeinput.getFullScopeName() + " line " + node.getLine());
				} 
			}
			GenInput input = model.getInput(inputname);
			int inputcalcid = model.getInputcalcIndex(propname);
//			int[] autocounters = model.getInputAutocounters(input);
			if (inputcalcid>=0) {
				out.print("   : getinputcalc0 ");
				out.print(input.getIndex());
				out.print(' ');
				out.print(inputcalcid);
			} else if ("unchecked".equalsIgnoreCase(propname)) {
				out.print("   : getinputraw0 ");
				out.print(input.getIndex());
			} else {
				throw new RuntimeException("invalid input attribute: " + propname + " for input " + input.getName() + ", line " + idprop.getLine());
			}
			out.print(" ; ");
			out.print(inputname);
			out.print(".");
			out.print(propname);
			out.println();
			
			break;
		}
		case TT_INPUTACCESSSIMPLE: {
			// ^(TT_INPUTACCESSSIMPLE ID)
			TcAst idname = node.getChild(0);
			String name = idname.getText().toUpperCase();
			GenInput input = model.getInput(name);
			if (input==null) {
				throw new RuntimeException("did not find GenInput for name " + name + ", line " + idname.getLine());
			}
			out.print("   : getinput0 ");
			out.print(input.getIndex());
			out.print(" ; ");
			out.print(name);
			out.println();
			break;
		}
		case TT_INPUTACCESSRAWSELF: {
			// ^(TT_INPUTACCESSSRAWSELF ID)
			TcAst idname = node.getChild(0);
			String name = idname.getText().toUpperCase();
			GenInput input = model.getInput(name);
			if (input==null) {
				throw new RuntimeException("did not find GenInput for name " + name + ", line " + idname.getLine());
			}
			int inputindex = input.getIndex();
			out.println("   : load 0 //index");
			out.print("   : getinputrawself ");
			out.print(inputindex);
			out.print(" ; ");
			out.print(name);
			out.println();
			break;
		}
		case TT_INPUTACCESS: {
			// ^(TT_INPUTACCESS ID ^(TT_INDEX formula*) columnaccess?)
			TcAst idname = node.getChild(0);
			String name = idname.getText().toUpperCase();
			TcAst index = node.getChild(1);
			TcAst columnaccess = node.getChildCount()>2 ? node.getChild(2) : null;

			GenInput input = model.getInput(name);
			if (input==null) {
				throw new RuntimeException("did not find GenInput for name " + name + ", line " + idname.getLine());
			}
			for (int indindex=0; indindex<index.getChildCount(); indindex++) {
				TcAst indexformula = index.getChild(indindex);
				visit(indexformula);
			}
			if (columnaccess==null) {
				out.print("   : getinput ");
				out.print(input.getIndex());
				out.print(' ');
				out.print(index.getChildCount());
				out.print(" ; ");
				out.println(name);
			} else {
				//^(TT_COLNAMESTATIC  id)
				TcAst colast = columnaccess.getChild(0);
				String colname = colast.getText().toUpperCase();
				int inputcalcid = model.getInputcalcIndex(colname);
				if (inputcalcid>=0) {
					out.print("   : getinputcalc ");
					out.print(input.getIndex());
					out.print(' ');
					out.print(inputcalcid);
					out.print(' ');
					out.print(index.getChildCount());
				} else if ("unchecked".equalsIgnoreCase(colname)) {
					out.print("   : getinputraw ");
					out.print(input.getIndex());
					out.print(' ');
					out.print(index.getChildCount());
				} else {
					throw new RuntimeException("invalid input attribute: " + colname + " for input " + input.getName() + ", line " + colast.getLine());
				}
				out.print(" ; ");
				out.print(name);
				out.print(".");
				out.print(colname);
				out.println();
			}
			break;
		}

		case KEYWORD_TABROWS: {
			TcAst tab = node.getChild(0);
			if (tab.getType()==ID) {
				String tablename = tab.getText().toUpperCase();
				GenTable table = model.getTable(tablename);
				if (table==null) {
					throw new RuntimeException("can not find table " + tablename + ", line " + node.getLine());
				}
				out.print("   : pushconst ");
				out.print(table.getNumrows());
				out.print(" ; nr. rows ");
				out.println(tablename);
			} else {
				visit(tab);
				out.print("   : tabrows ");
			}
			break;
		}
		
		case KEYWORD_TABCOLS: {
			TcAst tab = node.getChild(0);
			if (tab.getType()==ID) {
				String tablename = tab.getText().toUpperCase();
				GenTable table = model.getTable(tablename);
				if (table==null) {
					throw new RuntimeException("can not find table " + tablename + ", line " + node.getLine());
				}
				out.print("   : pushconst ");
				out.print(table.getNumcols());
				out.print(" ; nr. cols ");
				out.println(tablename);
			} else {
				visit(tab);
				out.print("   : tabcols ");
			}
			break;
		}
		case KEYWORD_CELL:
		case KEYWORD_CELLX: {
			//^(KEYWORD_CELL(X) id|formula range range)
			TcAst tab = node.getChild(0);
			TcAst rows = node.getChild(1);
			TcAst cols = node.getChild(2);
			for (int i=0; i<rows.getChildCount(); i++) {
				visit(rows.getChild(i));
			}
			for (int i=0; i<cols.getChildCount(); i++) {
				visit(cols.getChild(i));
			}
			boolean rowsrange = rows.getChildCount()>1;
			boolean colsrange = cols.getChildCount()>1;
			int method = (node.getType()==KEYWORD_CELLX ? 200 : 100) + (rowsrange ? 20 : 10) + (colsrange ? 2 : 1);
			if (tab.getType()==ID) {
				String tablename = tab.getText().toUpperCase();
				GenTable table = model.getTable(tablename);
				String methodname=null;
				switch(method) {
				case 111: methodname = "   : tabcell"; break;
				case 211: methodname = "   : tabcell_cn"; break;
				case 112: methodname = "   : tabcellsrow"; break;
				case 212: methodname = "   : tabcellsrow_cn"; break;
				case 121: methodname = "   : tabcellscol"; break;
				case 221: methodname = "   : tabcellscol_cn"; break;
				case 122: methodname = "   : tabcells"; break;
				case 222: methodname = "   : tabcells_cn"; break;
				default: throw new RuntimeException("invalid cell/cellx in line " + tab.getLine());
				}
				if (table==null) {
					throw new RuntimeException("can not find table " + tablename + ", line " + node.getLine());
				}
				out.print(methodname);
				out.print(" ");
				out.print(table.getIndex());
				out.print(" ; ");
				out.print(tablename);
				out.println();
			} else {
				String methodname=null;
				switch(method) {
				case 111: methodname = "   : tabrefcell"; break;
				case 211: methodname = "   : tabrefcell_cn"; break;
				case 112: methodname = "   : tabrefcellsrow"; break;
				case 212: methodname = "   : tabrefcellsrow_cn"; break;
				case 121: methodname = "   : tabrefcellscol"; break;
				case 221: methodname = "   : tabrefcellscol_cn"; break;
				case 122: methodname = "   : tabrefcells"; break;
				case 222: methodname = "   : tabrefcells_cn"; break;
				default: throw new RuntimeException("invalid cell/cellx in line " + tab.getLine());
				}
				visit(tab);
				out.print(methodname);
				out.println();
			}
			break;
		}

		case KEYWORD_EXISTS: {
			//^(KEYWORD_EXISTS id|formula arg*)
			TcAst tab = node.getChild(0);
			if (tab.getType()==ID) {
				String tablename = tab.getText().toUpperCase();
				GenTable table = model.getTable(tablename);
				if (table==null) {
					throw new RuntimeException("can not find table " + tablename + ", line " + node.getLine());
				}
				for (int i=1; i<node.getChildCount(); i++) {
					visit(node.getChild(i));
				}
				out.print("   : tabfindrowexact ");
				out.print(table.getIndex());
				out.print(' ');
				out.print(node.getChildCount()-1);
				out.print(" ; ");
				out.println(tablename);
				out.println("   : bigeq0 ; found the row?");
			} else {
				for (int i=1; i<node.getChildCount(); i++) {
					visit(node.getChild(i));
				}
				visit(tab);
				out.print("   : tabreffindrowexact ");
				out.println(node.getChildCount()-1);
				out.println("   : bigeq0 ; found the row?");
			}
			break;
		}
		case KEYWORD_LOOKUPX:
		case KEYWORD_LOOKDOWNX:	{
			//^(LOOKUPX id|formula formula_key* formula_colname)
			TcAst tab = node.getChild(0);
			if (tab.getType()==ID) {
				String tablename = tab.getText().toUpperCase();
				GenTable table = model.getTable(tablename);
				if (table==null) {
					throw new RuntimeException("can not find table " + tablename + ", line " + node.getLine());
				}
				for (int i=1; i<node.getChildCount()-1; i++) {
					visit(node.getChild(i));
				}
				visit(node.getChild(node.getChildCount()-1)); //column name
				if (node.getType()==KEYWORD_LOOKUPX) {
					out.print("   : tabfindintervalupcolumn ");
				} else {
					out.print("   : tabfindintervaldowncolumn ");
				}
				out.print(table.getIndex());
				out.print(' ');
				out.print(node.getChildCount()-2);
				out.print(" ; ");
				out.print(tablename);
				out.println();
			} else {
				for (int i=1; i<node.getChildCount()-1; i++) {
					visit(node.getChild(i));
				}
				visit(node.getChild(node.getChildCount()-1)); //column name
				visit(tab);
				if (node.getType()==KEYWORD_LOOKUPX) {
					out.print("   : tabreffindintervalupcolumn ");
				} else {
					out.print("   : tabreffindintervaldowncolumn ");
				}
				out.print(node.getChildCount()-2);
				out.println();
			}
			break;
		}
		
		case KEYWORD_LOOKUP: {
			//^(LOOKUPX id|formula formula)
			TcAst tab = node.getChild(0);
			if (tab.getType()==ID) {
				String tablename = tab.getText().toUpperCase();
				GenTable table = model.getTable(tablename);
				if (table==null) {
					throw new RuntimeException("can not find table " + tablename + ", line " + node.getLine());
				}
				visit(node.getChild(1));
				out.print("   : tabfindintervalup ");
				out.print(table.getIndex());
				out.print(" ; ");
				out.print(tablename);
				out.println();
			} else {
				visit(node.getChild(1));
				visit(tab);
				out.print("   : tabreffindintervalup ");
				out.println();
			}
			break;
		}
		
		case TT_TABLEACCESS: {
			// ^(TT_TABLEACCESS ID ^(TT_INDEX formula*) columnaccess?)
			TcAst idname =  node.getChild(0);
			TcAst index = node.getChild(1);
			TcAst columnaccess = node.getChildCount()>2 ? node.getChild(2) : null;
			String tablename = idname.getText().toUpperCase();

			GenTable table = model.getTable(tablename);
			if (table==null) {
				throw new RuntimeException("can not find table " + tablename + ", line " + node.getLine());
			}
			for (int i=0; i<index.getChildCount(); i++) {
				visit(index.getChild(i));
			}
			if(columnaccess==null) {
				out.print("   : tabfindexact ");
				out.print(table.getIndex());
				out.print(' ');
				out.print(index.getChildCount());
				out.print(" ; ");
				out.println(tablename);
			} else {
				if (columnaccess.getType()==TT_COLNAMEFORMULA) {
					visit(columnaccess);
					out.print("   : tabfindexactcolumnbyname ");
					out.print(table.getIndex());
					out.print(' ');
					out.print(index.getChildCount());
					out.print(" ; ");
					out.print(tablename);
					out.println();
				} else {
					String colname = columnaccess.getChild(0).getText();
					int indcol = table.getColindex(colname);
					if (indcol<0) {
						throw new RuntimeException("invalid columnname " + colname + " for table " + tablename + ", line " + columnaccess.getLine());
					}
					out.print("   : tabfindexactcolumn ");
					out.print(table.getIndex());
					out.print(' ');
					out.print(index.getChildCount());
					out.print(' ');
					out.print(indcol);
					out.print(" ; search in table ");
					out.print(tablename);
					out.print(", column ");
					out.print(colname);
					out.print(" with ");
					out.print(index.getChildCount());
					out.print(" argument(s)");
					out.println();
				}
			}
			break;
		}

		case TT_COLNAMESTATIC: {
			TcAst idname =  node.getChild(0);
			String name = idname.getText();
			out.print("   : pushconst ");
			out.print('"');
			out.print(name);
			out.println('"');
			break;
		}

		case TT_COLNAMEFORMULA: {
			TcAst formula =  node.getChild(0);
			visit(formula);
			break;
		}

		case TT_DYNTABLE: {
			//^(TT_DYNTABLE dyntableformula ^(TT_INDEX formula*) columnaccess?)
			TcAst dyntableformula =  node.getChild(0);
			TcAst index = node.getChild(1);
			if (index.getType()!=TT_INDEX) {
				throw new RuntimeException("expected TT_INDEX but got " + index.getType() + ", line " + index.getLine());
			}
			TcAst columnaccess = node.getChildCount()>2 ?  node.getChild(2) : null;
			
			for (int i=0; i<index.getChildCount(); i++) {
				visit(index.getChild(i));
			}
			if(columnaccess==null) {
				visit(dyntableformula);
				out.print("   : tabreffindexact ");
				out.print(index.getChildCount());
				out.println();
			} else  {
				//^(TT_COLNAMESTATIC  id) | ^(TT_COLNAMEFORMULA formula)
				if (columnaccess.getType()==TT_COLNAMEFORMULA) {
					visit(columnaccess);
				} else {
					visit(columnaccess);
					//addVar(columnaccess, columnaccess.getChild(0).getText());
				}
				visit(dyntableformula);
				out.print("   : tabreffindexactcolumn ");
				out.println(index.getChildCount());
			}
			break;
		}
		case KEYWORD_IF: 
		case QUESTIONMARK: {
			//^(KEYWORD_IF formulaCond formulaThen formulaElse)
			String labelFalse = newLabel();
			String labelEnd   = newLabel();
			TcAst cond =  node.getChild(0);
			TcAst thenformula =  node.getChild(1);
			TcAst elseformula =  node.getChild(2);
			out.print("   //start of if statement, line ");	out.print(cond.getLine()); out.println();
			visit(cond);
			out.print("   : iffalse ");
			out.println(labelFalse);
			visit(thenformula);
			out.print("   : goto ");
			out.println(labelEnd);
			out.print(" ");
			out.print(labelFalse); out.println(":");
			visit(elseformula);
			out.print(" "); out.print(labelEnd); out.println(":");
			out.print("   //end of if statement");
			out.println();
			break;
		}
		case TT_BUILTIN: {
			TcAst id =  node.getChild(0);
			String name = id.getText().toUpperCase();

			Scope scope = id.getScope();
			Symbol symbol = scope.resolve(name, 0, false);
			if (symbol==null) {
				symbol = scope.resolve(name, 0, false);
				System.out.println("TT_BUILTIN: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			}
			//String funcname = symbol.getSymbolName().toUpperCase();
			TcAst parameterListe =  node.getChild(1);
			int numparams = parameterListe==null ? 0 : parameterListe.getChildCount();
			//visit(id);
			BFInfo funcinfo = BFInfo.getBySourceName(name);
			if (name.equalsIgnoreCase("void")) {
				out.println("   : pushnull");
				break;
			} else if (name.equalsIgnoreCase("vpmcompute")) {
				if (numparams<2) {
					throw new RuntimeException("at least two parameters expected for 'vpmcompute', but got " + numparams + ", line " + node.getLine());
				}
				TcAst astModelname = parameterListe.getChild(0);
				TcAst astFuncname = parameterListe.getChild(1);
				/* TODO: version with computation parameters */
				visit(astModelname);
				visit(astFuncname);
				out.println("   : dyncompute");
				break;
			} else if (name.equalsIgnoreCase("undefined")) {
				if (numparams!=1) {
					throw new RuntimeException("1 parameter expected for 'undefined', but got " + numparams + ", line " + node.getLine());
				}
				TcAst astInputnameAndIndex = parameterListe.getChild(0);
				visit(astInputnameAndIndex);
				out.println("   : inputtestnull");
				break;
			}
			else if (name.equalsIgnoreCase("message")) {
				if (numparams!=2) {
					throw new RuntimeException("two parameters expected for message, but got " + numparams + ", line " + node.getLine());
				}
				TcAst message = parameterListe.getChild(0); /* TODO: do something with the message */
				TcAst formula = parameterListe.getChild(1);
				out.println("   // start message ... text-part is ignored to date");
				visit(formula);
				break;
			} 
			
			if (funcinfo==null) {
				throw new RuntimeException("did not found builtin function " + name);
			}
			/* check number of parameters */
			if (numparams<funcinfo.getMinargs()) {
				throw new RuntimeException("too less number of parameters for " + name + ": got " + numparams + " but minumum is " + funcinfo.getMinargs() + ", line " + node.getLine());
			}
			if (funcinfo.getMaxargs()>=0 && numparams>funcinfo.getMaxargs())  {
				throw new RuntimeException("too much numbers of parameters for " + name + ": " + numparams + " but maximum is " + funcinfo.getMaxargs());
			}
			if (funcinfo.isInstancemethod() && funcinfo.getMaxargs()==0) {
				throw new RuntimeException("instance base method " + funcinfo.getJavaname() + " needs at least one parameter!");
			}
			int builtinid = funcinfo.getId();
			
			visit(parameterListe);
			out.print("   : builtin ");
			out.print(builtinid);
			out.print(" ");
			out.print(numparams);
			out.print(" ; ");
			out.print(name);
			out.println();
			break;
		}
		case TT_FUNCALL: {
			//^(TT_FUNCALL ID ^(TT_PARAMETERS formula*)?)
			TcAst id =  node.getChild(0);
			String name = id.getText().toUpperCase();
			TcAst parameterListe = node.getChildCount()>1 ? node.getChild(1) : null;
			int nargs = parameterListe!=null ? parameterListe.getChildCount() : 0;

			Scope scope = id.getScope();
			Symbol symbol = scope.resolve(name, nargs, false);
			if (symbol==null) {
				symbol = scope.resolve(name, nargs, false);
				System.out.println("TT_FUNCALL: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			}
//			out.println("   : pushconst0 ; space for return value"); 
			if (parameterListe!=null) {	
				visit(parameterListe);
			}
			GenFunction genFunction = model.getFunction(name);
			out.print("   : callfunc ");
			out.print(genFunction.getIndex());
			out.print(' ');
			out.print(nargs);
//			out.print(" 0");
			out.print(" ; ");
			out.print(name);
//			out.print(" last 0 is placeholder for jump address");
			out.println();
			break;
		}
		case TT_CALCCALL: {
			//^(TT_CALCCALL ID ^(TT_PARAMETERS formula*)?)
			TcAst id =  node.getChild(0);
			String name = id.getText().toUpperCase();
			TcAst parameterListe = node.getChildCount()>1 ? node.getChild(1) : null;
			int nargs = parameterListe!=null ? parameterListe.getChildCount() : 0;
			
			for (int indarg=0; indarg<nargs; indarg++) {
				visit(parameterListe.getChild(indarg));
			}

			Scope scope = node.getScope();
			boolean selfcalccall = false;
			TcAst astCalc = node.getAncestor(KEYWORD_CALC); //see if the call to the property is within a node
			if (astCalc!=null) {
				TcAst astResult = node.getAncestor(TT_RESULTDEF);
				String definedinname = astResult.getChild(0).getText().toUpperCase();
				selfcalccall = name.equalsIgnoreCase(definedinname);
			}
//			Scope scope = id.getScope();
			
			Symbol symbol = scope.resolve(name, nargs, selfcalccall);
			if (symbol==null) {
				symbol = scope.resolve(name, nargs, selfcalccall);
				throw new RuntimeException("TT_CALCCALL: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			}
			int calcid = model.getCalcindex(name, nargs);
			if (calcid<0) {
				throw new RuntimeException("did not find calc " + name + ", line " + id.getLine());
			}
			ResolvedCalc resolveret = scope.resolveCalc(model, name, nargs, selfcalccall);
			if (resolveret==null) {
				throw new RuntimeException("could not resolveCalc " + name + " (id=" + calcid + "), line " + id.getLine());
			} 
			int calcindex = model.getCalcindex(name, nargs);
			switch(resolveret.getType()) {
			case ONE_SIMPLE: {
				int nodeindex = resolveret.getSumNode();
				out.print("   : callnodecalc ");
				out.print(nodeindex);
				out.print(' ');
				out.print(calcindex);
				out.print(' ');
				out.print(nargs);
				out.print(" ; ");
				out.print(" node ");
				out.print(model.getNodeData(nodeindex).node.getNodeId());
				out.print(" calc ");
				out.print(model.getCalcname(calcindex));
				out.println();
				break;
			}
			case LIST: {
				int nodeindex = resolveret.getSumNode();
				if (nodeindex<0) {
					throw new RuntimeException("could not get common parent for nodes " + resolveret.toString() + ", calc " + name + " (" + calcindex + "), line " + id.getLine());
				}
				out.print("   : callnodecalcsum ");
				out.print(nodeindex);
				out.print(' ');
				out.print(calcindex);
				out.print(' ');
				out.print(nargs);
				out.print(' ');
				out.print((selfcalccall&&nargs==0) ? "0" : "1");				
				out.print(" ; ");
				out.print(" node ");
				out.print(model.getNodeData(nodeindex).node.getNodeId());
				out.print(" calc ");
				out.print(model.getCalcname(calcindex));
				out.println();
				break;
			}
			default: {
				throw new RuntimeException("unexpected resolveret: " + resolveret.toString() + ", line " + id.getLine());
			}
			}
			break;
		}
		case KEYWORD_SUMX:
		case KEYWORD_PRODX: 
		{
			TcAst id =  node.getChild(0);
			TcAst formulafrom =  node.getChild(1);
			TcAst formulato =  node.getChild(2);
			TcAst formulaloop =  node.getChild(3);
			
			String labelLoopStart = newLabel();
			String labelEnd       = newLabel();
			
			String countername = id.getText().toUpperCase();
			Scope loopscope = formulaloop.getScope();
			Symbol symbolCounter = loopscope.resolve(countername, 0, false);
//			Symbol symbolTo      = loopscope.resolve("_LOOPTO_" + countername,  0, false);
			Symbol symbolResult  = loopscope.resolve("_RESULT_" + countername,  0, false);
			int idCounter = symbolCounter.getId();
			int idResult = symbolResult.getId();
			
			out.println("   // start of loop statement");
			out.println("   // init result");
			switch(tokentype) {
			case KEYWORD_SUMX:
				out.println("   : pushconst0");
				out.print  ("   : store "); out.print(idResult); out.println();
				break;
			case KEYWORD_PRODX:
				out.println("   : pushconst1");
				out.print  ("   : store "); out.print(idResult); out.println();
			}
			visit(formulato);   //stack: to
			visit(formulafrom); //stack: to from
			out.println("   : dup"); //stack: to from from
			out.print  ("   : store "); out.print(idCounter); out.print(" ; store counter"); out.println(); //stack: to from
			out.println("   : over"); //stack: to from to 
			out.print  ("   : ifbig "); out.print(labelEnd); out.println(" ; end if from>to"); //stack: to
			out.print  (" "); out.print(labelLoopStart); out.println(":");
			visit(formulaloop); //stack: to value
			switch(tokentype) {
			case KEYWORD_SUMX:
				out.print  ("   : load "); out.print(idResult); out.print(" ; load result"); out.println(); //stack: to value result
				out.println("   : add"); //stack: to value+result
				out.print  ("   : store "); out.print(idResult); out.print(" ; store updated result"); out.println(); //stack: to
				break;
			case KEYWORD_PRODX:
				out.print  ("   : load "); out.print(idResult); out.print(" ; load result"); out.println(); //stack: to value result
				out.println("   : mult"); //stack: to value+result
				out.print  ("   : store "); out.print(idResult); out.print(" ; store updated result"); out.println(); //stack: to
			}
			out.println("   // update counter and test");
			out.print  ("   : load "); out.println(idCounter); //stack: to counter
			out.println("   : pushconst 1"); //stack: to counter 1
			out.println("   : add"); // stack: to newcounter
			out.println("   : dup"); //stack: to newcounter newcounter
			out.print("   : store "); out.println(idCounter); //stack: to newcounter
			out.println("   : over"); //stack: to newcounter to
			out.print("   : ifsmleq "); out.println(labelLoopStart);  //stack: to
			out.print(" "); out.print(labelEnd); out.println(":"); //stack:to 
			out.println("   : pop ; pop value_to");
			out.print  ("   : load "); out.print(idResult); out.println(); //stack:
			out.println("   // end of loop");
			break;
		}
		case KEYWORD_VECTORX: {
			TcAst id =  node.getChild(0);
			TcAst formulafrom =  node.getChild(1);
			TcAst formulato =  node.getChild(2);
			TcAst formulaloop =  node.getChild(3);
			
			String labelLoopStart = newLabel();
			String labelEnd       = newLabel();
			
			String countername = id.getText().toUpperCase();
			Scope loopscope = formulaloop.getScope();
			Symbol symbolCounter = loopscope.resolve(countername, 0, false);
//			Symbol symbolTo      = loopscope.resolve("_LOOPTO_" + countername,  0, false);
			Symbol symbolResult  = loopscope.resolve("_RESULT_" + countername,  0, false);
			int idCounter = symbolCounter.getId();
			int idResult = symbolResult.getId();
			
			out.println("   // start of loop statement");
			visit(formulato);   //stack: to
			visit(formulafrom); //stack: to from
			out.println("   : over"); //stack: to from to
			out.println("   : over"); //stack: to from to from
			out.println("   : sub"); //stack: to from to-from
			out.println("   // init result");
			out.println("   : createlistn");
			out.print  ("   : store "); out.print(idResult); out.println();
			out.println("   : dup"); //stack: to from from
			out.print  ("   : store "); out.print(idCounter); out.print(" ; store counter"); out.println(); //stack: to from
			out.println("   : over"); //stack: to from to 
			out.print  ("   : ifbig "); out.println(labelEnd); //stack: to
			out.print  (" "); out.print(labelLoopStart); out.println(":");
			out.print  ("   : load "); out.print(idResult); out.print(" ; load result"); out.println(); //stack: to result
			visit(formulaloop); //stack: to result value
			out.println("   : listappendelem1"); //stack: to result+value
			out.print  ("   : store "); out.print(idResult); out.print(" ; store updated result"); out.println(); //stack: to
			out.println("   // update counter and test");
			out.print  ("   : load "); out.println(idCounter); //stack: to counter
			out.println("   : pushconst 1"); //stack: to counter 1
			out.println("   : add"); // stack: to newcounter
			out.println("   : dup"); //stack: to newcounter newcounter
			out.print("   : store "); out.println(idCounter); //stack: to newcounter
			out.println("   : over"); //stack: to newcounter to
			out.print("   : ifsmleq "); out.println(labelLoopStart);  //stack: to
			out.print(" "); out.print(labelEnd); out.println(":"); //stack:to 
			out.println("   : pop ; pop value_to");
			out.print  ("   : load "); out.print(idResult); out.println(); //stack:
			out.println("   // end of loop");
			break;
		}
		case KEYWORD_COLLATE: {
			TcAst id =  node.getChild(0);
			int nargs = node.getChildCount()-1;
			String name = id.getText().toUpperCase();
			Scope scope = node.getScope();
//			Scope scope = id.getScope();
			
			for (int indarg=1; indarg<node.getChildCount(); indarg++) {
				visit(node.getChild(indarg));
			}
			
			Symbol symbol = scope.resolve(name, nargs, true);
			if (symbol==null) {
				throw new RuntimeException("TT_CALCCALL: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			}
			int calcindex = model.getCalcindex(name, nargs);
			if (calcindex<0) {
				throw new RuntimeException("did not find calc " + name + ", line " + id.getLine());
			}
			ResolvedCalc resolveret = scope.resolveCalc(model, name, nargs, true);
			if (resolveret==null) {
				throw new RuntimeException("could not resolveCalc " + name + " (id=" + calcindex + "), line " + id.getLine());
			} 
			switch(resolveret.getType()) {
			case ONE_SIMPLE: {
				int nodeindex = resolveret.getSumNode();
//				s = "Nodes.gc(" + resolveret.getSubnodes()[0] + ", " + model.getCalcindex(name) + ")" + "/* " + name + "*/ ";
				out.println("   // start collate with single node/calc");
				out.print("   : callnodecalc ");
				out.print(nodeindex);
				out.print(' ');
				out.print(calcindex);
				out.print(' ');
				out.print(nargs);
				out.print(" ; ");
				out.print(" node ");
				out.print(model.getNodeData(nodeindex).node.getNodeId());
				out.print(" calc ");
				out.print(model.getCalcname(calcindex));
				out.println();
				out.println("   : createlist1");
				break;
			}
			case LIST: {
				int nodeindex = resolveret.getSumNode();
				if (nodeindex<0) {
					throw new RuntimeException("could not get common parent for nodes " + resolveret.toString() + ", calc " + name + " (" + calcindex + "), line " + id.getLine());
				}
				out.print("   : callnodecalclist ");
				out.print(nodeindex);
				out.print(' ');
				out.print(calcindex);
				out.print(' ');
				out.print(nargs);
				out.print(" 1"); //selfcall
				out.print(" ; ");
				out.print(" node ");
				out.print(model.getNodeData(nodeindex).node.getNodeId());
				out.print(" calc ");
				out.print(model.getCalcname(calcindex));
				out.println();
				break;
			}
			default: {
				throw new RuntimeException("unexpected resolveret: " + resolveret.toString() + ", line " + id.getLine());
			}
			}
			break;
		}
		case KEYWORD_EXTRACT: {
			TcAst id =  node.getChild(0);
			String name = id.getText().toUpperCase();
			TcAst astPos = node.getChild(node.getChildCount()-1);
			int nargs = node.getChildCount()-2;

			visit(astPos);
			for (int indarg=1; indarg<node.getChildCount()-1; indarg++) {
				visit(node.getChild(indarg));
			}
			
			Scope scope = node.getScope();
//			Scope scope = id.getScope();
			
			Symbol symbol = scope.resolve(name, nargs, true);
			if (symbol==null) {
				symbol = scope.resolve(name, nargs, true);
				throw new RuntimeException("TT_CALCCALL: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			}
			int calcindex = model.getCalcindex(name, nargs);
			if (calcindex<0) {
				throw new RuntimeException("did not find calc " + name + ", line " + id.getLine());
			}
			ResolvedCalc resolveret = scope.resolveCalc(model, name, nargs, true);
			if (resolveret==null) {
				throw new RuntimeException("could not resolveCalc " + name + " (id=" + calcindex + "), line " + id.getLine());
			} 
			switch(resolveret.getType()) {
			case ONE_SIMPLE: {
				int nodeindex = resolveret.getSumNode();
				out.println("   // start collate with single node/calc");
				out.print("   : callnodecalc ");
				out.print(nodeindex);
				out.print(' ');
				out.print(calcindex);
				out.print(' ');
				out.print(nargs);
				out.print(" ; ");
				out.print(" node ");
				out.print(model.getNodeData(nodeindex).node.getNodeId());
				out.print(" calc ");
				out.print(model.getCalcname(calcindex));
				out.println();
				out.println("   : createlist1");
				out.println("   : listelem1 ; extract from collate");
				break;
			}
			case LIST: {
				int nodeindex = resolveret.getSumNode();
				if (nodeindex<0) {
					throw new RuntimeException("could not get common parent for nodes " + resolveret.toString() + ", calc " + name + " (" + calcindex + "), line " + id.getLine());
				}
				out.print("   : callnodecalclist ");
				out.print(nodeindex);
				out.print(' ');
				out.print(calcindex);
				out.print(' ');
				out.print(nargs);
				out.print(" 1"); //selfcall
				out.print(" ; ");
				out.print(" node ");
				out.print(model.getNodeData(nodeindex).node.getNodeId());
				out.print(" calc ");
				out.print(model.getCalcname(calcindex));
				out.println();
				out.println("   : listelem1 ; extract from collate");
				break;
			}
			default: {
				throw new RuntimeException("unexpected resolveret: " + resolveret.toString() + ", line " + id.getLine());
			}
			}
			break;
		}

		case TT_USEVAR_LOCAL: { //running vars from sumx prodx vectorx
			TcAst id =  node.getChild(0);
			Scope scope = id.getScope();
			String name = id.getText().toUpperCase();
			Symbol symbol = scope.resolve(name, 0, false);
			if (symbol==null) {
				symbol = scope.resolve(name, 0, false);
				throw new RuntimeException("TT_USEVAR_LOCAL: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			}
			out.print("   : load ");
			out.print(symbol.getId());
			out.print(" ; ");
			out.print(name);
			out.println();
			break;
		}
		case TT_USEVAR_PARAMETER: {
			TcAst id =  node.getChild(0);
			Scope scope = id.getScope();
			String name = id.getText().toUpperCase();
			Symbol symbol = scope.resolve(name, 0, false);
			if (symbol==null) {
				symbol = scope.resolve(name, 0, false);
				throw new RuntimeException("TT_USEVAR_PARAMETER: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			} 
			out.print("   : load ");
			out.print(symbol.getId());
			out.print(" ; ");
			out.print(name);
			out.println();
			break;
		}
		case TT_USEVAR_TIMESINDEX: {
			TcAst id =  node.getChild(0);
			Scope scope = id.getScope();
			String name = id.getText().toUpperCase();
			Symbol symbol = scope.resolve(name, 0, false);
			if (symbol==null) {
				symbol = scope.resolve(name, 0, false);
				throw new RuntimeException("TT_USEVAR_TIMESINDEX: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			}
			
			int timesid = model.getTimesId(name);
			out.print("   : pushtimescounter ");
			out.print(timesid);
			out.print(" ; ");
			out.println(name);
			break;
		}
		case TT_TABREF: {
			//^(TT_TABREF id)
			TcAst id = node.getChild(0);
			String name = id.getText().toUpperCase();
			GenTable table = model.getTable(name);
			int tableindex = table.getIndex();
			out.print("   : pushconst ");
			out.print(tableindex);
			out.print(" ; ");
			out.println(name);
		}
		/* constants */
		case STRING: {
			out.print("   : pushconst ");
			out.print('"');
			out.print(node.getText());
			out.println('"');
			break;
		}
		case NUMBER: {
			out.print("   : pushconst ");
			out.println(node.getText());
			break;
		}
		case KEYWORD_FUNCREF: {
			TcAst formula = node.getChild(0);
			int type = formula.getType();
			if (type==TT_FUNCALL && formula.getChildCount()==1) {
				String name = formula.getChild(0).getText().toUpperCase();
				GenFunction func = model.getFunction(name);
				if (func==null) {
					throw new RuntimeException("could not statically resolve function name " + name + ", line " + formula.getLine());
				}
				int i = func.getIndex();
				out.print("   : pushconst ");
				out.print(i);
				out.print(" ; ");
				out.print(name);
			} else if (type==STRING) {
				String name = formula.getText().toUpperCase();
				GenFunction func = model.getFunction(name);
				if (func==null) {
					throw new RuntimeException("could not statically resolve function name " + name + ", line " + formula.getLine());
				}
				int i = func.getIndex();
				out.print("   : pushconst ");
				out.print(i);
				out.print(" ; ");
				out.print(name);
			} else {
				visit(formula);
				out.println("   : getfuncref");
			}
			break;
		}
		case KEYWORD_DOCALL: {
			TcAst funcref = node.getChild(0);
			for (int indstart=1, i=indstart; i<node.getChildCount(); i++) {
				visit(node.getChild(i));
			}
			visit(funcref);
			out.print("   : calldynfunc ");
			out.print(node.getChildCount()-1);
			break;
		}
		case KEYWORD_COUNTERLIST: {
			Scope scope = node.getScope();
			out.print  ("   : pushconst ");
			out.println(node.getChildCount());
			out.println("   : createlistn ");
			for (int i=0; i<node.getChildCount(); i++) {
				out.println("   : dup");
				TcAst id =  node.getChild(i);
				String name = id.getText().toUpperCase();
				Symbol symbol = scope.resolve(name, 0, false);
				if (symbol==null) {
					symbol = scope.resolve(name, 0, false);
					throw new RuntimeException("TT_USEVAR_TIMESINDEX: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
				}
				int timesid = model.getTimesId(name);
				out.print  ("   : pushconst ");
				out.print  (timesid);
				out.print  (" ; counter ");
				out.println(name);
				out.println("   : listappendelem1");
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
		TcAst op =  node.getChild(0);
		visit(op);
		String opname = unop.get(node.getType());
		out.print("   : ");
		out.println(opname);
		
	}

	public void or(TcAst node) {
		if (node.getChildCount()!=2) {
			throw new RuntimeException("invalid number of childs for " + node.getText() + ", tokentype " + node.getType() + ", line " + node.getLine() + ": " +node.getChildCount());
		}
		TcAst left =  node.getChild(0);
		TcAst right =  node.getChild(1);

		String labelLeftFalse = newLabel();
		String labelTrue  = getLabel(node, "true");
		String labelFalse = getLabel(node, "false");
		String labelEnd   = getLabel(node, "end");
		boolean newLabelTrue  = labelTrue ==null;
		boolean newLabelFalse = labelFalse==null;
		boolean newLabelEnd   = labelEnd  ==null;
		if (newLabelTrue) {
			labelTrue = newLabel();
		}
		if (newLabelFalse) {
			labelFalse = newLabel();
		}
		if (newLabelEnd) {
			labelEnd  = newLabel();
		}
		setLabel(left, "true", labelTrue);
		setLabel(left, "false", labelLeftFalse);
		setLabel(left, "end", labelEnd);
		setLabel(right, "true", labelTrue);
		setLabel(right, "false", labelFalse);
		setLabel(right, "end", labelEnd);
		visit(left);
		if (!"true".equals(getLabel(left, "isbooljump"))) {
			out.print("   : iftrue "); out.println(labelTrue);
		}
		out.print(" "); out.print(labelLeftFalse); out.println(':');
		visit(right);
		if (!"true".equals(getLabel(right, "isbooljump"))) {
			out.print("   : iftrue "); out.println(labelTrue);
			out.print("   : goto "); out.println(labelFalse);
		}
		if (newLabelTrue) {
			out.print(" "); out.print(labelTrue); out.println(':');
			out.println("   : pushconst1");
			out.print("   : goto "); out.println(labelEnd);
		} 
		if (newLabelFalse) {
			out.print(" "); out.print(labelFalse); out.println(':');
			out.println("   : pushconst0");
		}
		if (newLabelEnd) {
			out.print(" "); out.print(labelEnd); out.println(':');
		}
		setLabel(node, "isbooljump", "true");
	}
	
	
	public void and(TcAst node) {
		if (node.getChildCount()!=2) {
			throw new RuntimeException("invalid number of childs for " + node.getText() + ", tokentype " + node.getType() + ", line " + node.getLine() + ": " +node.getChildCount());
		}
		TcAst left =  node.getChild(0);
		TcAst right =  node.getChild(1);

		String labelLeftTrue = newLabel();
		String labelTrue  = getLabel(node, "true");
		String labelFalse = getLabel(node, "false");
		String labelEnd   = getLabel(node, "end");
		boolean newLabelTrue  = labelTrue ==null;
		boolean newLabelFalse = labelFalse==null;
		boolean newLabelEnd   = labelEnd  ==null;
		if (newLabelTrue) {
			labelTrue = newLabel();
		}
		if (newLabelFalse) {
			labelFalse = newLabel();
		}
		if (newLabelEnd) {
			labelEnd  = newLabel();
		}
		setLabel(left, "true", labelLeftTrue);
		setLabel(left, "false", labelFalse);
		setLabel(left, "end", labelEnd);
		setLabel(right, "true", labelTrue);
		setLabel(right, "false", labelFalse);
		setLabel(right, "end", labelEnd);
		visit(left);
		if (!"true".equals(getLabel(left, "isbooljump"))) {
			out.print("   : iffalse "); out.println(labelFalse);
		}
		out.print(" "); out.print(labelLeftTrue); out.println(':');
		visit(right);
		if (!"true".equals(getLabel(right, "isbooljump"))) {
			out.print("   : iffalse "); out.println(labelFalse);
			out.print("   : goto "); out.println(labelTrue);
		}
		if (newLabelTrue) {
			out.print(" "); out.print(labelTrue); out.println(':');
			out.println("   : pushconst1");
			out.print("   : goto "); out.println(labelEnd);
		} 
		if (newLabelFalse) {
			out.print(" "); out.print(labelFalse); out.println(':');
			out.println("   : pushconst0");
		}
		if (newLabelEnd) {
			out.print(" "); out.print(labelEnd); out.println(':');
		}
		setLabel(node, "isbooljump", "true");
	}
	
	public void binop(TcAst node) {
		if (node.getChildCount()!=2) {
			throw new RuntimeException("invalid number of childs for " + node.getText() + ", tokentype " + node.getType() + ", line " + node.getLine() + ": " +node.getChildCount());
		}
		TcAst left =  node.getChild(0);
		TcAst right = node.getChild(1);

		visit(left); 
		visit(right);

		out.print("   : ");
		String opname = op.get(node.getType());
		out.println(opname);
	}
	
	public static PrintFormulaInfos printFormula(PrintStream out, TcAst astFormula, ModelSimple model) {
		VmFormula o = new VmFormula(out, astFormula, model);
		o.run();
		return o.printFormulaInfos;
	}
	
	public static boolean isSimple(ModelSimple model, TcAst node) {
		return new VmFormula((PrintStream)null, node, model).isSimple(node);
	}
	
//	public boolean isSimple(TcAst node) {
//		return true;
//	}
	public boolean isSimple(TcAst node) {
		if (node==null) {
			return true;
		}
		int tokentype = node.getType();
		switch (tokentype) {
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
		case ASTERISK:
		case SLASH:
		case DIV:
		case MOD:
		case POWER:
		case PLUS: 
		case MINUS: {
			for (int i=0; i<node.getChildCount(); i++) {
				boolean ret = isSimple(node.getChild(i));
				if (ret==false) {
					return ret;
				}
			}
			return true;
		}
		case KEYWORD_CASE: {
			//^(KEYWORD_CASE formula casewhen* casedefault?)
			return false;
		}
		case TT_PARAMETERS:
		case TT_INDEX: {
			for (int i=0; i<node.getChildCount(); i++) {
				boolean ret = isSimple(node.getChild(i));
				if (ret==false) {
					return ret;
				}
			}
			return true;
		}
		case TT_INPUTCALCCALLSIMPLE: {
			return true;
		}
		case TT_INPUTACCESSSIMPLE: {
			return true;
		}
		case TT_INPUTACCESSRAWSELF: {
			return true;
		}
		case TT_INPUTACCESS: {
			for (int i=0; i<node.getChildCount(); i++) {
				boolean ret = isSimple(node.getChild(i));
				if (ret==false) {
					return false;
				}
			}
			return true;
		}

		case KEYWORD_TABROWS: 
		case KEYWORD_TABCOLS: {
			return true;
		}
		case KEYWORD_CELL:
		case KEYWORD_CELLX: {
			return false;
		}

		case KEYWORD_EXISTS:
		case KEYWORD_LOOKUPX:
		case KEYWORD_LOOKDOWNX:
		case TT_TABLEACCESS:
		case TT_DYNTABLE: {
			return false;
		}
		case KEYWORD_IF: 
		case QUESTIONMARK: {
			for (int i=0; i<node.getChildCount(); i++) {
				boolean ret = isSimple(node.getChild(i));
				if (ret==false) {
					return false;
				}
			}
			return true;
		}
		case TT_BUILTIN: {
			for (int i=0; i<node.getChildCount(); i++) {
				boolean ret = isSimple(node.getChild(i));
				if (ret==false) {
					return false;
				}
			}
			return true;
		}
		case TT_FUNCALL: {
			return false;
		}
		case TT_CALCCALL: {
			//^(TT_CALCCALL ID ^(TT_PARAMETERS formula*)?)
			for (int i=0; i<node.getChildCount(); i++) {
				boolean ret = isSimple(node.getChild(i));
				if (ret==false) {
					return false;
				}
			}
			TcAst id =  node.getChild(0);
			String name = id.getText().toUpperCase();
			TcAst parameterListe = node.getChildCount()>1 ? node.getChild(1) : null;
			int nargs = parameterListe!=null ? parameterListe.getChildCount() : 0;
			Scope scope = node.getScope();
//			Scope scope = id.getScope();
			boolean selfcalccall = false;
			TcAst astCalc = node.getAncestor(KEYWORD_CALC); //see if the call to the property is within a node
			if (astCalc!=null) {
				TcAst astResult = node.getAncestor(TT_RESULTDEF);
				String definedinname = astResult.getChild(0).getText().toUpperCase();
				selfcalccall = name.equalsIgnoreCase(definedinname);
			}
			
			Symbol symbol = scope.resolve(name, nargs, selfcalccall);
			if (symbol==null) {
				symbol = scope.resolve(name, nargs, selfcalccall);
				throw new RuntimeException("TT_CALCCALL: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			}
			int calcid = model.getCalcindex(name, nargs);
			if (calcid<0) {
				throw new RuntimeException("did not find calc " + name + ", line " + id.getLine());
			}
			ResolvedCalc resolveret = scope.resolveCalc(model, name, nargs, selfcalccall);
			if (resolveret==null) {
				throw new RuntimeException("could not resolveCalc " + name + " (id=" + calcid + "), line " + id.getLine());
			} 
			switch(resolveret.getType()) {
			case ONE_SIMPLE: return true;
			case LIST: return false;
			}
			return false;
		}
		case KEYWORD_SUMX:
		case KEYWORD_PRODX:
		case KEYWORD_VECTORX: 
		case KEYWORD_COLLATE: 
		case KEYWORD_EXTRACT: 
			return false;
		case TT_USEVAR_LOCAL:
		case TT_USEVAR_PARAMETER:
		case TT_USEVAR_TIMESINDEX:
			return true;
		case TT_TABREF:
		case KEYWORD_FUNCREF:
			return true;
		case STRING: 
		case NUMBER: 
			return true;
		case KEYWORD_DOCALL: {
			for (int i=0; i<node.getChildCount(); i++) {
				boolean ret = isSimple(node.getChild(i));
				if (ret==false) {
					return false;
				}
			}
			return true;
		}
		default:
			return false;
		}
	}
	
	private static void printFormulaNodecalcList(int formulaind) {
		out.print(".formula");
		out.print(" formula=");
		out.print(formulaind);
		out.println(" simple=true");
		out.println("   ; argument 0: arguments_list");
		out.println("   ; argument 1: nodeid");
		out.println("   ; argument 2: calcid");
		out.println("   ; argument 3: nr of parameters");
		out.println("   ; argument 4: selfcall (0/1)");
		out.println("   ; argument 5: result");
		out.println("   ; localvar 6: formulaid_inclusion");
		out.println("   ; localvar 7: formulaid_times");
		out.println("   ; localvar 8: children_num");
		out.println("   ; localvar 9: children_ind");
		out.println("   ; localvar 10: times");
		out.println("   ; localvar 11: timescounter");
		out.println("   ; localvar 12: timesid");
		out.println("   ; localvar 13: formulaid for node-calc");
		out.println("   ; localvar 14: subnodeid");
		out.println("   : load 4 //selfcall");
		out.println("   : iffalse L1");
		out.println("   : load 1 //nodeid");
		out.println("   : load 2 //calcid");
		out.println("   : tree_nodecalcown");
		out.println("   : iffalse L1");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodeformulainc //formulaid");
		out.println("   : dup //formulaid_inclusion formulaid_inclusion");
		out.println("   : store 6 //formulaid_inclusion");
		out.println("   : ifnull L2 //jump if formulaid_inclusion<0");
		out.println("   : load 6 //formulaid_inclusion");
		out.println("   : pushconst0 //nargs=0");
		out.println("   : createlist0 //parameters (empty list)");
		out.println("   : callformuladyn //call inclusion formula");
		out.println("   : iftrue L2");
		out.println("   ; node is not included -> do not append anything");
		out.println("   : load 5");
		out.println("   : return");
		out.println(" L2: //calc in own node, node is included");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodeformulatimes //formulaid_times");
		out.println("   : dup //formulaid_times formulaid_times");
		out.println("   : store 7 //formulaid_times");
		out.println("   : ifnull L3 //if no formulaid_times -> no times-counter");
		out.println("   : load 7 //formulaid_times");
		out.println("   : pushconst0 //formulaid_times 0; no parameters");
		out.println("   : createlist0 //formulaid_times 0 []; parameters (empty list)");
		out.println("   : callformuladyn //times");
		out.println("   : dup //times times");
		out.println("   : pushconst0 //times times 0");
		out.println("   : ifsmleq L7 //times; counter<=0 -> return null");
		out.println("   : store 10 //store times");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodetimesid //timesid");
		out.println("   : store 12 //store timesid");
		out.println("   // init times counter in local var and on times-stack");
		out.println("   : pushconst0 //init timescounter");
		out.println("   : store 11   //init timescounter");
		out.println("   : load 12 //timesid");
		out.println("   : times_push");
		out.println("   // get and store formulaid");
		out.println("   : load 1 //nodeid");
		out.println("   : load 2 //calcid");
		out.println("   : tree_nodecalcformula //formulaid");
		out.println("   : store 13");
		out.println("   : load 5 //result");
		out.println(" L8: // start of loop over times .. calc in own node, node is included, we have a multiple-counter");
		out.println("   ; action: compute and add to result");
		out.println("   : load 13 //result formulaid");
		out.println("   : load 3 //result formulaid nargs");
		out.println("   : load 0 //result formulaid nargs args_list");
		out.println("   : callformuladyn //result newvalue");
		out.println("   : listappendelem1 //result");
		out.println("   : times_inctop");
		out.println("   : load 11 //result timescounter");
		out.println("   : pushconst1 //result timescounter 1");
		out.println("   : add //result timescounter+1");
		out.println("   : dup //result timescounter+1 timescounter+1");
		out.println("   : store 11 //result timescounter+1");
		out.println("   : load 10 //result timescounter+1 times");
		out.println("   : ifsml L8");
		out.println("   : times_pop");
		out.println("   : return");
		out.println(" L3: //calc in own node, node is included, no multiple-counter");
		out.println("   : load 5 //result");
		out.println("   : load 1 //nodeid");
		out.println("   : load 2 //calcid");
		out.println("   : tree_nodecalcformula //formulaid");
		out.println("   : load 3 //formulaid nargs");
		out.println("   : load 0 //formulaid nargs args_list");
		out.println("   : callformuladyn //result newvalue");
		out.println("   : listappendelem1 //result");
		out.println("   : return");
		out.println(" L1: //not defined in own node -> check if in child nodes");
		out.println("   : load 1 //nodeid");
		out.println("   : load 2 //calcid");
		out.println("   : tree_nodecalctotal");
		out.println("   : iftrue L6");
		out.println("     //not in child nodes -> return result unchanged");
		out.println("   : load 5");
		out.println("   : return");
		out.println(" L6: //defined in child nodes");
		out.println("   ; check if the node is included");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodeformulainc //formulaid");
		out.println("   : dup //formulaid_inclusion formulaid_inclusion");
		out.println("   : store 6 //formulaid_inclusion");
		out.println("   : ifnull L61 //jump if formulaid_inclusion<0");
		out.println("   : load 6 //formulaid_inclusion");
		out.println("   : pushconst0 //nargs=0");
		out.println("   : createlist0 //parameters (empty list)");
		out.println("   : callformuladyn //call inclusion formula");
		out.println("   : iftrue L61");
		out.println("   ; node is not included -> return 'null'");
		out.println("   : pushnull");
		out.println("   : return");
		out.println("L61: //defined in child nodes, and child node is included");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_subnodes_counter //numchilds");
		out.println("   : dup //numchilds numchilds");
		out.println("   : ifzero L7 //numchilds; if no children -> return null");
		out.println("   : store 8 //store numchilds; --");
		out.println("   : pushconst0 //init indchild");
		out.println("   : store 9");
		out.println("   ; check times");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodeformulatimes //formulaid_times");
		out.println("   : dup //formulaid_times formulaid_times");
		out.println("   : store 7 //formulaid_times");
		out.println("   : ifnull L40 //if no formulaid_times -> no times-counter");
		out.println("   : load 7 //formulaid_times");
		out.println("   : pushconst0 //formulaid_times 0; no parameters");
		out.println("   : createlist0 //formulaid_times 0 []; parameters (empty list)");
		out.println("   : callformuladyn //times");
		out.println("   : dup //times times");
		out.println("   : pushconst0 //times times 0");
		out.println("   : ifsmleq L7 //times; counter<=0 -> return null");
		out.println("   ; setup times-counter");
		out.println("   : store 10 //store times");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodetimesid //timesid");
		out.println("   : store 12 //store timesid");
		out.println("     // sum from children, we have times-counter -> outer loop over children, inner loop over times-counter");
		out.println(" L9: ;outer loop over children begin");
		out.println("   ; get subnode-id");
		out.println("   : load 1 //nodeid");
		out.println("   : load 9 //nodeid indchild");
		out.println("   : tree_subnodes_get //subnodeid");
		out.println("   : store 14 //store subnodeid");
		out.println("   // init times counter in local var and on times-stack");
		out.println("   : pushconst0 //init timescounter");
		out.println("   : store 11   //init timescounter");
		out.println("   : load 12 //timesid");
		out.println("   : times_push");
		out.println(" LA: //begin of inner loop over times");
		out.println("   : load 0; argument 0: arguments_list");
		out.println("   : load 14; argument 1: nodeid");
		out.println("   : load 2; argument 2: calcid");
		out.println("   : load 3; argument 3: nr of parameters");
		out.println("   : pushconst1; argument 4: selfcall (0/1)");
		out.println("   : load 5; argument 5: result");
		out.println("   : callformula " + formulaind + " 6 //recursive call -- newvalue");
		out.println("   : pop //result newvalue -- result; value was already appended to result list");
		out.println("   //increment and check times");
		out.println("   : times_inctop");
		out.println("   : load 11 //result timescounter");
		out.println("   : pushconst1 //result timescounter 1");
		out.println("   : add //result timescounter+1");
		out.println("   : dup //result timescounter+1 timescounter+1");
		out.println("   : store 11 //result timescounter+1");
		out.println("   : load 10 //result timescounter+1 times");
		out.println("   : ifsml LA");
		out.println("   : times_pop");
		out.println("   // increment and check current child");
		out.println("   : load 9 //result indchild");
		out.println("   : pushconst1 //result indchild 1");
		out.println("   : add //result indchild+1");
		out.println("   : dup //result indchild+1 indchild+1");
		out.println("   : store 9 //result indchild+1");
		out.println("   : load 8 //result indchild+1 numchilds");
		out.println("   : ifsml L9 //result");
		out.println("   : load 5");
		out.println("   : return");
		out.println("L40: //sum from children, we are included and have no times-counter");
		out.println("L41: ;loop over children begin");
		out.println("   : load 0; argument 0: arguments_list");
		out.println("   ; push arguments for tree_subnodes_get");
		out.println("   : load 1 //nodeid");
		out.println("   : load 9 //indchild");
		out.println("   : tree_subnodes_get //argument 1: nodeid of child");
		out.println("   : load 2; argument 2: calcid");
		out.println("   : load 3; argument 3: nr of parameters");
		out.println("   : pushconst1; argument 4: selfcall (0/1)");
		out.println("   : load 5; argument 5: result");
		out.println("   : callformula " + formulaind + " 6 //recursive call -- newvalue");
		out.println("   : pop //newvalue already appended to the list");
		out.println("   // increment and check current child");
		out.println("   : load 9 //result indchild");
		out.println("   : pushconst1 //result indchild 1");
		out.println("   : add //result indchild+1");
		out.println("   : dup //result indchild+1 indchild+1");
		out.println("   : store 9 //result indchild+1");
		out.println("   : load 8 //result indchild+1 numchilds");
		out.println("   : ifsml L41 //result");
		out.println("   : load 5");
		out.println("   : return");
		out.println(" L7: //no children or times-counter<=0 -> return result unchanged");
		out.println("   : load 5");
		out.println("   : return");
		out.println(".formuladone");
	}
	
	private static void printFormulaNodecalcSum(int formulaind) {
		out.print(".formula");
		out.print(" formula=");
		out.print(formulaind);
		out.println(" simple=true");
		out.println("   ; argument 0: arguments_list");
		out.println("   ; argument 1: nodeid");
		out.println("   ; argument 2: calcid");
		out.println("   ; argument 3: nr of parameters");
		out.println("   ; argument 4: selfcall (0/1)");
		out.println("   ; localvar 5: formulaid_inclusion");
		out.println("   ; localvar 6: formulaid_times");
		out.println("   ; localvar 7: children_num");
		out.println("   ; localvar 8: children_ind");
		out.println("   ; localvar 9: times");
		out.println("   ; localvar 10: timescounter");
		out.println("   ; localvar 11: timesid");
		out.println("   ; localvar 12: formulaid for node-calc");
		out.println("   ; localvar 13: subnodeid");
		out.println("   : load 4 //selfcall");
		out.println("   : iffalse L1");
		out.println("   : load 1 //nodeid");
		out.println("   : load 2 //calcid");
		out.println("   : tree_nodecalcown");
		out.println("   : iffalse L1");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodeformulainc //formulaid");
		out.println("   : dup //formulaid_inclusion formulaid_inclusion");
		out.println("   : store 5 //formulaid_inclusion");
		out.println("   : ifnull L2 //jump if formulaid_inclusion<0");
		out.println("   : load 5 //formulaid_inclusion");
		out.println("   : pushconst0 //nargs=0");
		out.println("   : createlist0 //parameters (empty list)");
		out.println("   : callformuladyn //call inclusion formula");
		out.println("   : iftrue L2");
		out.println("   ; node is not included -> return 'null'");
		out.println("   : pushnull");
		out.println("   : return");
		out.println(" L2: //calc in own node, node is included");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodeformulatimes //formulaid_times");
		out.println("   : dup //formulaid_times formulaid_times");
		out.println("   : store 6 //formulaid_times");
		out.println("   : ifnull L3 //if no formulaid_times -> no times-counter");
		out.println("   : load 6 //formulaid_times");
		out.println("   : pushconst0 //formulaid_times 0; no parameters");
		out.println("   : createlist0 //formulaid_times 0 []; parameters (empty list)");
		out.println("   : callformuladyn //times");
		out.println("   : dup //times times");
		out.println("   : pushconst0 //times times 0");
		out.println("   : ifsmleq L7 //times; counter<=0 -> return null");
		out.println("   : store 9 //store times");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodetimesid //timesid");
		out.println("   : store 11 //store timesid");
		out.println("   // init times counter in local var and on times-stack");
		out.println("   : pushconst0 //init timescounter");
		out.println("   : store 10   //init timescounter");
		out.println("   : load 11 //timesid");
		out.println("   : times_push");
		out.println("   // get and store formulaid");
		out.println("   : load 1 //nodeid");
		out.println("   : load 2 //calcid");
		out.println("   : tree_nodecalcformula //formulaid");
		out.println("   : store 12");
		out.println("   : pushnull //result");
		out.println(" L8: // start of loop over times .. calc in own node, node is included, we have a multiple-counter");
		out.println("   ; action: compute and add to result");
		out.println("   : load 12 //result formulaid");
		out.println("   : load 3 //result formulaid nargs");
		out.println("   : load 0 //result formulaid nargs args_list");
		out.println("   : callformuladyn //result newvalue");
		out.println("   : addnotnull //result");
		out.println("   : times_inctop");
		out.println("   : load 10 //result timescounter");
		out.println("   : pushconst1 //result timescounter 1");
		out.println("   : add //result timescounter+1");
		out.println("   : dup //result timescounter+1 timescounter+1");
		out.println("   : store 10 //result timescounter+1");
		out.println("   : load 9 //result timescounter+1 times");
		out.println("   : ifsml L8");
		out.println("   : times_pop");
		out.println("   : return");
		out.println(" L3: //calc in own node, node is included, no multiple-counter");
		out.println("   : load 1 //nodeid");
		out.println("   : load 2 //calcid");
		out.println("   : tree_nodecalcformula //formulaid");
		out.println("   : load 3 //formulaid nargs");
		out.println("   : load 0 //formulaid nargs args_list");
		out.println("   : callformuladyn //result");
		out.println("   : return");
		out.println(" L1: //not defined in own node -> check if in child nodes");
		out.println("   : load 1 //nodeid");
		out.println("   : load 2 //calcid");
		out.println("   : tree_nodecalctotal");
		out.println("   : iftrue L6");
		out.println("     //not in child nodes -> return null");
		out.println("   : pushnull");
		out.println("   : return");
		out.println(" L6: //defined in child nodes");
		out.println("   ; check if the node is included");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodeformulainc //formulaid");
		out.println("   : dup //formulaid_inclusion formulaid_inclusion");
		out.println("   : store 5 //formulaid_inclusion");
		out.println("   : ifnull L61 //jump if formulaid_inclusion<0");
		out.println("   : load 5 //formulaid_inclusion");
		out.println("   : pushconst0 //nargs=0");
		out.println("   : createlist0 //parameters (empty list)");
		out.println("   : callformuladyn //call inclusion formula");
		out.println("   : iftrue L61");
		out.println("   ; node is not included -> return 'null'");
		out.println("   : pushnull");
		out.println("   : return");
		out.println("L61: //sum from subnodes, the node is included");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_subnodes_counter //numchilds");
		out.println("   : dup //numchilds numchilds");
		out.println("   : ifzero L7 //numchilds; if no children -> return null");
		out.println("   : store 7 //store numchilds; --");
		out.println("   : pushconst0 //init indchild");
		out.println("   : store 8");
		out.println("   ; check times");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodeformulatimes //formulaid_times");
		out.println("   : dup //formulaid_times formulaid_times");
		out.println("   : store 6 //formulaid_times");
		out.println("   : ifnull L40 //if no formulaid_times -> no times-counter");
		out.println("   : load 6 //formulaid_times");
		out.println("   : pushconst0 //formulaid_times 0; no parameters");
		out.println("   : createlist0 //formulaid_times 0 []; parameters (empty list)");
		out.println("   : callformuladyn //times");
		out.println("   : dup //times times");
		out.println("   : pushconst0 //times times 0");
		out.println("   : ifsmleq L7 //times; counter<=0 -> return null");
		out.println("   ; setup times-counter");
		out.println("   : store 9 //store times");
		out.println("   : load 1 //nodeid");
		out.println("   : tree_nodetimesid //timesid");
		out.println("   : store 11 //store timesid");
		out.println("     // sum from children, we have times-counter -> outer loop over children, inner loop over times-counter");
		out.println("   : pushnull //result");
		out.println(" L9: ;outer loop over children begin");
		out.println("   ; get subnode-id");
		out.println("   : load 1 //nodeid");
		out.println("   : load 8 //nodeid indchild");
		out.println("   : tree_subnodes_get //subnodeid");
		out.println("   : store 13 //store subnodeid");
		out.println("   // init times counter in local var and on times-stack");
		out.println("   : pushconst0 //init timescounter");
		out.println("   : store 10   //init timescounter");
		out.println("   : load 11 //timesid");
		out.println("   : times_push");
		out.println(" LA: //begin of inner loop over times");
		out.println("   : load 0; argument 0: arguments_list");
		out.println("   : load 13; argument 1: nodeid");
		out.println("   : load 2; argument 2: calcid");
		out.println("   : load 3; argument 3: nr of parameters");
		out.println("   : pushconst1; argument 4: selfcall (0/1)");
		out.println("   : callformula " + formulaind + " 5 //recursive call -- result newvalue");
		out.println("   : addnotnull //add result -- result");
		out.println("   //increment and check times");
		out.println("   : times_inctop");
		out.println("   : load 10 //result timescounter");
		out.println("   : pushconst1 //result timescounter 1");
		out.println("   : add //result timescounter+1");
		out.println("   : dup //result timescounter+1 timescounter+1");
		out.println("   : store 10 //result timescounter+1");
		out.println("   : load 9 //result timescounter+1 times");
		out.println("   : ifsml LA");
		out.println("   : times_pop");
		out.println("   // increment and check current child");
		out.println("   : load 8 //result indchild");
		out.println("   : pushconst1 //result indchild 1");
		out.println("   : add //result indchild+1");
		out.println("   : dup //result indchild+1 indchild+1");
		out.println("   : store 8 //result indchild+1");
		out.println("   : load 7 //result indchild+1 numchilds");
		out.println("   : ifsml L9 //result");
		out.println("   : return");
		out.println("L40: //sum from children, we are included and have no times-counter");
		out.println("   : pushnull");
		out.println("L41: ;loop over children begin");
		out.println("   : load 0; argument 0: arguments_list");
		out.println("   ; push arguments for tree_subnodes_get");
		out.println("   : load 1 //nodeid");
		out.println("   : load 8 //indchild");
		out.println("   : tree_subnodes_get //argument 1: nodeid of child");
		out.println("   : load 2; argument 2: calcid");
		out.println("   : load 3; argument 3: nr of parameters");
		out.println("   : pushconst1; argument 4: selfcall (0/1)");
		out.println("   : callformula " + formulaind + " 5 //recursive call -- result newvalue");
		out.println("   : addnotnull //add result -- result");
		out.println("   // increment and check current child");
		out.println("   : load 8 //result indchild");
		out.println("   : pushconst1 //result indchild 1");
		out.println("   : add //result indchild+1");
		out.println("   : dup //result indchild+1 indchild+1");
		out.println("   : store 8 //result indchild+1");
		out.println("   : load 7 //result indchild+1 numchilds");
		out.println("   : ifsml L41 //result");
		out.println("   : return");
		out.println(" L7: //no children or times-counter<=0 -> return null");
		out.println("   : pushnull");
		out.println("   : return");
		out.println(".formuladone");
	}
	
	private void run() {
		visit(astFormula);
		out.println("   : return");
	}

	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		PrintStream out = new PrintStream(new FileOutputStream(foldernameout + "/" + packagename + ".tci", true), true, "ISO-8859-1");
		VmFormula.out = out;
		out.print(".formulas");
		out.print(" size=");

		int size=0;
		size += model.getFormulaSize(); //has two special formulas already
//		size += model.getFunctionSize(); //formulas to call function
//		size += model.getCalcnamesSize();
//		size += model.getInputSize(); //check / value
//		for (int i=0; i<model.getInputSize(); i++) {
//			GenInput input = model.getInput(i);
//			size += input.getCalcsSize(); //input.calc
//		}
		out.println(size);
		/* standard formulas */
		int formulaind = 0;
		printFormulaNodecalcList(formulaind);
		formulaind++;
		printFormulaNodecalcSum(formulaind);
		formulaind++;
		for (; formulaind<model.getFormulaSize(); formulaind++) {
			out.print(".formula");
			out.print(" formula=");
			out.print(formulaind);
			TcAst astFormula = model.getFormula(formulaind);
			out.print(" simple=");
			out.print(isSimple(model,  astFormula));
			out.print(" ; line ");
			out.print(astFormula.getLine());
			out.println();
			VmFormula.printFormula(out, astFormula, model);
			out.println(".formuladone");
		}
		/* access formulas */

//		for (int i=0; i<model.getFunctionSize(); i++, formulaind++) {
//			GenFunction func = model.getFunction(i);
//			out.print(".formula");
//			out.print(" formula=");
//			out.print(formulaind);
//			out.print(" simple=true");
//			out.print(" ; callstub for " + func.getName());
//			out.println();
//			out.print("   : callfunc ");
//			out.print(func.getIndex());
//			out.print(' ');
//			out.print(func.getArgSize());
//			out.println();
//			out.println("   : exit");
//			out.println(".formuladone");
//		}
		out.close();
	}
}
