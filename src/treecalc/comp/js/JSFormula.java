package treecalc.comp.js;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import treecalc.comp.FormulaConstant;
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
import treecalc.comp.TcSimpleParser;

import treecalc.rt.V;
import treecalc.rt.VDouble;
import treecalc.rt.VFuncref;
import treecalc.rt.VString;
import treecalc.rt.VTabref;

import static treecalc.comp.TcSimpleParser.*;

public class JSFormula {
	private final PrintStream out;
	private final TcAst astFormula;
	private final ModelSimple model;
	private int indent = 0;
	private final String prefix;
	private final String postfix;
	private static HashMap<Integer,String> indents = new HashMap<Integer,String>(20);
	private HashMap<TcAst,String> localvars = new HashMap<TcAst,String>();
	private int localvar=0;
	private PrintFormulaInfos printFormulaInfos = new PrintFormulaInfos();

	public static String getConstNameInMethod(V constant) {
		
		String prefix = JSConstants.NS_CONSTANTS + ".";
		
		if (constant instanceof VDouble) {
			if (constant.isInteger()) {
				return prefix + "_i" + constant.toString(); 
			} else {
				return prefix + "_d" + constant.toString().replace('.', '_');
			} 
		} else if (constant instanceof VString) {
			String s = constant.toString();
			Pattern pattern = Pattern.compile("\\W", Pattern.MULTILINE); //non-word character: [^a-zA-Z_0-9]
			Matcher matcher = pattern.matcher(s);
			if (!matcher.find()) {
				return prefix + "_s" + (s.length()==0 ? "_empty" : s);
			} else {
				return null;
			}
		} else if (constant instanceof VFuncref) {
			return prefix + "_funcref" + constant.funcrefValue();
		} else if (constant instanceof VTabref) {
			return prefix + "_tabref" + constant.tabrefValue();
		} else {
			return null;
		}
	}

	private String getConstantFormula(String createstatement, V vconstant) {
		String name = getConstNameInMethod(vconstant);
		if (name!=null) {
			FormulaConstant constant = new FormulaConstant(name, vconstant, createstatement);
			printFormulaInfos.addConstant(constant);
			model.putConstant(constant);
			return name;
		} else {
			return createstatement; //is not shared
		}
	}
	
	private static HashMap<Integer, String> op = new HashMap<Integer,String>();
	static {
//		op.put(LOGICAL_OR              , "or"      ); need extra care
//		op.put(LOGICAL_AND             , "and"     ); need extra care
		op.put(LOGICAL_XOR             , "xor"     );
		op.put(COMPARE_EQUAL           , "eq"      ); // =     compare numerical equal
		op.put(COMPARE_EQUAL_CSTYLE    , "eq"      ); // ==     
		op.put(COMPARE_SMALLER         , "sml"     ); // <     compare numerical less
		op.put(COMPARE_BIGGER          , "gt"     ); // >     compare numerical greater
		op.put(COMPARE_LESSEQUAL       , "smleq"   ); // <=    compare numerical less equal
		op.put(COMPARE_BIGGEREQUAL     , "ge"      ); // >=    compare numerical greater equal
		op.put(COMPARE_NOTEQUAL        , "neq"     ); // <>    compare numerical not equal
		op.put(COMPARE_NOTEQUAL_CSTYLE , "neq"     ); // <>   
		op.put(COMPARE_STR_EQUAL       , "seq"     ); // s=    compare string equal
		op.put(COMPARE_STR_NOTEQUAL    , "sneq"    ); // s<>   compare string not equal
		op.put(COMPARE_STR_ALIKE       , "seqi"    ); // si=   compare string equal caseinsensitive
		op.put(COMPARE_STR_NOTALIKE    , "sneqi"   ); // si<>  compare string not equal caseinsensitive
		op.put(COMPARE_STR_BEFORE      , "sl"      ); // s<    compare string less
		op.put(COMPARE_STR_NOTBEFORE   , "sgeq"    ); // s>=   compare string greater equal
		op.put(COMPARE_STR_AHEAD       , "sli"     ); // si<   compare string less caseinsensitive
		op.put(COMPARE_STR_NOTAHEAD    , "sgeqi"   ); // si>=  compare string greater equal caseinsensitive
		op.put(COMPARE_STR_BEHIND      , "sg"      ); // s>    compare string greater 
		op.put(COMPARE_STR_NOTBEHIND   , "sleq"    ); // s<=   compare string less equal
		op.put(COMPARE_STR_AFTER       , "sgi"     ); // si>   compare string greater caseinsensitive
		op.put(COMPARE_STR_NOTAFTER    , "sleqi"   ); // si<=  compare string less equal caseinsensitive
		op.put(STRCAT                  , "append"  ); // &     string append  
		op.put(ASTERISK                , "mult"    ); // * 
		op.put(SLASH                   , "div"     ); // /
		op.put(DIV                     , "divint"  ); // DIV
		op.put(MOD                     , "modint"  ); // MOD
		op.put(POWER                   , "power"   ); // ^
		op.put(PLUS                    , "add"     ); 
		op.put(MINUS                   , "subtract"     );
	}
	private static HashMap<Integer, String> unop = new HashMap<Integer,String>();
	static {
		unop.put(PLUS , "unplus" );
		unop.put(MINUS, "unminus");
	}
	static class BFInfo {
		final String javaname;
		/**
		 * minimum number of arguments
		 */
		final int minargs;
		/**
		 * maximum number of arguments or -1 if variable number of arguments without limit
		 */
		final int maxargs;
		final boolean instancemethod;
		BFInfo(String javaname, int minargs, int maxargs, boolean instancemethod) {
			this.javaname = javaname;
			this.minargs = minargs;
			this.maxargs = maxargs;
			this.instancemethod = instancemethod;
		}
	}
	private static HashMap<String, BFInfo> bf = new HashMap<String, BFInfo>();
	static {
		// special void
		bf.put("FALSE"                         ,new BFInfo("bf_false", 0, 0, false));
		bf.put("NOT"                           ,new BFInfo("bf_not", 1, 1, true));
		bf.put("TRUE"                          ,new BFInfo("bf_true", 0, 0, false));
		/* table functions */
		// special bf.put("cell"                          ,"cell"                            );
		// special bf.put("cellx"                         ,"cellx"                           );
		// special bf.put("exists"                        ,"exists"                          );
		// special bf.put("interpol"                      ,"interpol"                        );
		// special bf.put("lookup"                        ,"lookup"                          );
		// special bf.put("lookupx"                       ,"lookupx"                         );
		// special bf.put("lookdownx"                     ,"lookdownx"                       );
		// special bf.put("search"                        ,"search"                          );
		// special bf.put("tabcols"                       ,"tabcols"                         );
		// special bf.put("tabrows"                       ,"tabrows"                         );
		/* odbc access */
		bf.put("SELECT"                        ,new BFInfo("bf_select", 3, -1, false));
		bf.put("SELECTX"                       ,new BFInfo("bf_selectx", 4, -1, false));
		bf.put("V_SELECT"                      ,new BFInfo("bf_v_select", 3, -1, false));
		bf.put("V_SELECTX"                     ,new BFInfo("bf_v_selectx", 4, -1, false));
		/* string functions */
		bf.put("FORMAT"                        ,new BFInfo("bf_format", 2, 2, true));
		bf.put("INSTR"                         ,new BFInfo("bf_instr", 2, 2, true));
		bf.put("LEFT"                          ,new BFInfo("bf_left", 2, 2, true));
		bf.put("LENGTH"                        ,new BFInfo("bf_length", 1, 1, true));
		bf.put("MID"                           ,new BFInfo("bf_mid", 2, 3, true));
		bf.put("RIGHT"                         ,new BFInfo("bf_right", 2, 2, true));
		bf.put("STRCMP"                        ,new BFInfo("bf_strcmp", 2, 2, true));
		bf.put("STRICMP"                       ,new BFInfo("bf_stricmp", 2, 2, true));
		bf.put("SUBST"                         ,new BFInfo("bf_subst", 3, 3, true));
		bf.put("TOLOWER"                       ,new BFInfo("bf_tolower", 1, 1, true));
		bf.put("TOUPPER"                       ,new BFInfo("bf_toupper", 1, 1, true));
		bf.put("TRIM"                          ,new BFInfo("bf_trim", 1, 1, true));
		bf.put("CHR"                           ,new BFInfo("bf_chr", 1, 1, true));
		bf.put("ANSI"                          ,new BFInfo("bf_ansi", 1, 1, true));
		bf.put("OEM"                           ,new BFInfo("bf_oem", 1, 1, true));
		/* math functions */
		bf.put("ROUND"                         ,new BFInfo("bf_round", 1, 2, true));
		bf.put("CEIL"                          ,new BFInfo("bf_ceil", 1, 1, true));
		bf.put("FLOOR"                         ,new BFInfo("bf_floor", 1, 1, true));
		bf.put("MIN"                           ,new BFInfo("bf_min", 2, -1, false));
		bf.put("MAX"                           ,new BFInfo("bf_max", 2, -1, false));
		bf.put("SQRT"                          ,new BFInfo("bf_sqrt", 1, 1, true));
		bf.put("EXP"                           ,new BFInfo("bf_exp", 1, 1, true));
		bf.put("FMOD"                          ,new BFInfo("bf_fmod", 2, 2, true));
		bf.put("ABS"                           ,new BFInfo("bf_abs", 1, 1, true));
		/* unimportant math functions */
		bf.put("ACOS"                          ,new BFInfo("bf_acos", 1, 1, false));
		bf.put("ASIN"                          ,new BFInfo("bf_asin", 1, 1, false));
		bf.put("ATAN"                          ,new BFInfo("bf_atan", 1, 1, false));
		bf.put("ATAN2"                         ,new BFInfo("bf_atan2", 1, 1, false));
		bf.put("COS"                           ,new BFInfo("bf_cos", 1, 1, false));
		bf.put("COSH"                          ,new BFInfo("bf_cosh", 1, 1, false));
		bf.put("SIN"                           ,new BFInfo("bf_sin", 1, 1, false));
		bf.put("SINH"                          ,new BFInfo("bf_sinh", 1, 1, false));
		bf.put("TAN"                           ,new BFInfo("bf_tan", 1, 1, false));
		bf.put("TANH"                          ,new BFInfo("bf_tanh", 1, 1, false));
		bf.put("LOG"                           ,new BFInfo("bf_log", 1, 1, false));
		bf.put("LOG10"                         ,new BFInfo("bf_log10", 1, 1, false));
		/* date functions */
		bf.put("DATE"                          ,new BFInfo("bf_date", 4, 5, false));
		bf.put("DATEDAY"                       ,new BFInfo("bf_dateday", 1, 1, true));
		bf.put("DAY"                           ,new BFInfo("bf_day", 1, 1, true));
		bf.put("DAYDATE"                       ,new BFInfo("bf_daydate", 2, 2, true));
		bf.put("DAYS"                          ,new BFInfo("bf_days", 1, 1, true));
		bf.put("MONTH"                         ,new BFInfo("bf_month", 1, 1, true));
		bf.put("MONTHS"                        ,new BFInfo("bf_months", 1, 1, true));
		bf.put("NOW"                           ,new BFInfo("bf_now", 1, 1, false));
		bf.put("TODAY"                         ,bf.get("NOW"));
		bf.put("YEAR"                          ,new BFInfo("bf_year", 1, 1, true));
		bf.put("YEARS"                         ,new BFInfo("bf_years", 1, 1, true));
		/* list */
		bf.put("V_"                            ,new BFInfo("bf_list", 0, -1, false));
		bf.put("LIST"                          ,bf.get("V_"));
		bf.put("V_CONCAT"                      ,new BFInfo("bf_v_concat", 2, 2, true));                        
		bf.put("V_CONSTRUCT"                   ,new BFInfo("bf_v_construct", 1, 1, true));
		bf.put("V_DELETE"                      ,new BFInfo("bf_v_delete", 2, 2, true));
		bf.put("V_ELEMENT"                     ,new BFInfo("bf_v_element", 2, -1, true));
		bf.put("E_"                            ,bf.get("V_ELEMENT"));
		bf.put("V_ELEMENTS"                    ,new BFInfo("bf_v_elements", 3, 3, true));
		bf.put("V_FIRST"                       ,new BFInfo("bf_v_first", 1, 1, true));
		bf.put("V_FRONT"                       ,new BFInfo("bf_v_front", 1, 1, true));
		bf.put("V_INSERT"                      ,new BFInfo("bf_v_insert", 3, 3, true));
		bf.put("V_LAST"                        ,new BFInfo("bf_v_last", 1, 1, true));
		bf.put("V_LENGTH"                      ,new BFInfo("bf_v_length", 1, 1, true));
		bf.put("V_MAX"                         ,new BFInfo("bf_v_max", 1, 1, true));
		bf.put("V_MIN"                         ,new BFInfo("bf_v_min", 1, 1, true));
		bf.put("V_NUMSORT"                     ,new BFInfo("bf_v_numsort", 1, 1, true));
		bf.put("V_REPLACE"                     ,new BFInfo("bf_v_replace", 3, 3, true));
		bf.put("V_REST"                        ,new BFInfo("bf_v_rest", 1, 1, true));
		bf.put("V_SORT"                        ,new BFInfo("bf_v_sort", 1, 1, true));
		bf.put("V_STRING"                      ,new BFInfo("bf_v_string", 1, 2, true));
		bf.put("V_STRINGX"                     ,new BFInfo("bf_v_stringx", 1, 1, true));
		bf.put("v_trans"                       ,new BFInfo("bf_v_trans", 1, 1, true));
		/* information */
		bf.put("ISBOOL"                        ,new BFInfo("bf_isbool", 1, 1, true));
		bf.put("ISDATE"                        ,new BFInfo("bf_isdate", 1, 1, true));
		bf.put("ISINTEGER"                     ,new BFInfo("bf_isinteger", 1, 1, true));
		bf.put("ISNUMBER"                      ,new BFInfo("bf_isnumber", 1, 1, true));
		bf.put("ISTIME"                        ,new BFInfo("bf_istime", 1, 1, true));
		//bf.put("UNDEFINED"                     ,new BFInfo("bf_undefined", 1, 1, false));
		/* loops */
		//special bf.put("sumx"                          ,"sumx"                            );
		//special bf.put("prodx"                          ,"prodx"                            );
		//special bf.put("vectorx"                       ,"vectorx"                         );
		/* function reference */
		//special bf.put("funcref"                       ,"funcref"                         );
		//special bf.put("docall"                        ,"docall"                          );
		/* dynamic call */
		bf.put("VPMCOMPUTE"                    ,new BFInfo("bf_vpmcompute", 2, -1, false));
		bf.put("VPMTRY"                        ,new BFInfo("bf_vpmtry", 2, -1, false));
		bf.put("VPMEXIST"                      ,new BFInfo("bf_vpmexist", 1, 3, false));
		/* error handling */
		bf.put("ERROR"                         ,new BFInfo("bf_error", 1, 2, false));
//special		"message"
		/* get something from config file */
		bf.put("CONST"                         ,new BFInfo("bf_const", 1, 1, false));
		bf.put("GETPRIVATEPROFILESTRING"       ,new BFInfo("bf_getprivateprofilestring", 4, 4, false));
	}
	
	private JSFormula(PrintStream out, TcAst astFormula, ModelSimple model, int indent, String prefix, String postfix) {
		this.out = out;
		this.astFormula = astFormula;
		this.model = model;
		this.indent = indent;
		this.prefix  = prefix;
		this.postfix = postfix;
	}
	private void indent() {
		if (indent==0) {
			return;
		}
		String s = indents.get(indent);
		if (s==null) {
			char[] carr = new char[indent];
			Arrays.fill(carr, '\t');
			s = new String(carr);
			indents.put(indent, s);
		}
		out.print(s);
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
			addVar(node);
			out.print("var ");
			out.print(getVar(node));
			out.println("; //will hold result of case"); indent();
			
			TcAst formula =  node.getChild(0);
			visit(formula);
			// numeric (all int, no ranges):
			// switch(formula) {
			// case cw1:
			// case cw2: {
			//     casethen
			//     break;
			// }
			// case cw3:
			// ...
			// default: {
			// }
			
			// non-numeric or ranges:
			// V v1 //result
			// V v2 = formula;
			// if (v2==case1 || v2==case2 || v2>=case3_from && v2<=case3_to) {
			//    case1Formula;
			//    v1 = resultCase1Formula;
			// } else if (...) {
			// } else { //default
			// }
			out.print("var ");
			String comparevar = getVarTemp();
			String comparevarstr = getVarTemp();
			out.print(comparevar);
			out.print(" = ");
			out.print(getVar(formula));
			out.println("; //case condition"); indent();
			out.print("var ");
			out.print(comparevarstr);
			out.print(" = ");
			out.print(comparevar);
			out.println(".stringValue();");
			out.println(); indent();
			for (int indstart=1, i=indstart; i<node.getChildCount(); i++) { //iterate over 'case' parts
				TcAst child =  node.getChild(i);
				if (i>indstart) {
					out.print("else ");
				}
				//KEYWORD_WHEN oder KEYWORD_DEFAULT
				switch (child.getType()) {
				case KEYWORD_WHEN: {
					//^(KEYWORD_WHEN casecompares formula)
					//casecompares: ^(TT_CASECONDITION casecompare*)
					//casecompare: ^(TT_CASECOMPARISON caseconstant) | ^(TT_CASERANGE caseconstant*)
					//caseconstant: STRING | NUMBER (NUMBER can be with leading '-')
					TcAst casecompares = child.getChild(0);
					TcAst caseformula  = child.getChild(1);
					out.print("if (");
					for (int indCompare=0; indCompare<casecompares.getChildCount(); indCompare++) {  
						TcAst casecompare = casecompares.getChild(indCompare); //comparison within 'case' part
						if (indCompare>0) {
							out.print(" || ");
						}
						out.print('(');
						switch (casecompare.getType()) {
						case TT_CASECOMPARISON: {
							TcAst constant = casecompare.getChild(0);
							switch(constant.getType()) {
							case STRING: {
								out.print(comparevarstr);
								out.print(".equals(\"");
								out.print(constant.getText());
								out.print("\")");
								break;
							}
							case NUMBER: {
								out.print(comparevar);
								out.print(".doubleValue()");
								out.print("==");
								out.print(Double.toString(Double.parseDouble(constant.getText())));
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
								out.print(comparevarstr);
								out.print(".compareTo(\"");
								out.print(constantFrom.getText());
								out.print("\")>=0 && ");
								out.print(comparevarstr);
								out.print(".compareTo(\"");
								out.print(constantTo.getText());
								out.print("\")<=0");
								break;
							}
							case NUMBER: {
								out.print(comparevar);
								out.print(".doubleValue()>=");
								out.print(Double.toString(Double.parseDouble(constantFrom.getText())));
								out.print(" && ");
								out.print(comparevar);
								out.print(".doubleValue()<=");
								out.print(Double.toString(Double.parseDouble(constantTo.getText())));
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
						out.print(')');
					}
					out.println(") {"); //closing ) for if(....)
					indent++; indent();
					visit(caseformula);
					out.print(getVar(node));
					out.print(" = ");
					out.print(getVar(caseformula));
					out.println(';');
					indent--; indent();
					out.println("}"); 
					indent();
					break;
				}
				case KEYWORD_DEFAULT: {
					//^(KEYWORD_DEFAULT formula)
					TcAst formulaDefault = child.getChild(0);
					out.println("{"); indent++; indent();
					visit(formulaDefault);
					out.print(getVar(node));
					out.print(" = ");
					out.print(getVar(formulaDefault));
					out.println(';');
					indent--; indent();
					out.println("}");
					indent();
					break;
				}
				default: 
					throw new RuntimeException("expected KEYWORD_WHEN or KEYWORD_DEFAULT but got token type: " + child.getType()+ " (" + TcSimpleParser.tokenNames[child.getType()] + "), line " + child.getLine() + ": " +child.getText());
				} //end of cse
			}
			break;
		}
		case KEYWORD_WHEN: { //case part
			//^(KEYWORD_WHEN casecompares formula)
			TcAst casecompares =  node.getChild(0);
			TcAst formula =  node.getChild(1);
			//visit(casecompares);
			//visit(formula);
			break;
		}
		case TT_CASECONDITION: {
			// ^(TT_CASECONDITION casecompare*)
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst child =  node.getChild(i);
				// TT_CASECOMPARISON
				//visit(child);
			}
			break;
		}
		case TT_CASECOMPARISON: {
			// ^(TT_CASEONECOMPARISON constant) 
			TcAst constant =  node.getChild(0);
			//visit(constant);
			break;
		}

		case TT_CASERANGE: {
			// ^(TT_CASERANGE      caseconstant*)
			TcAst constantFrom =  node.getChild(0);
			TcAst constantTo =  node.getChild(1);
			//visit(constantFrom);
			//visit(constantTo);
			break;
		}
		case KEYWORD_DEFAULT: {
			// ^(KEYWORD_DEFAULT formula
			TcAst formulaDefault =  node.getChild(0);
			//visit(formulaDefault);
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
			String propname = idprop.getText();

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
			int[] autocounters = model.getInputAutocounters(input);
			if (autocounters!=null) {
				addVar(node, JSInput.NS_INPUT + "." + inputname + ".$" + propname + "(_s, (V[]) null)");
			} else {
				addVar(node, JSInput.NS_INPUT + "." + inputname + ".$" + propname + "(_s)");
			}
			
			//visit(idname);
			//visit(idprop);
			break;
		}
//		case TT_INPUTCALCCALL: {
//			//^(TT_INPUTCALCCALL ID index columnaccess)
//			TcAst idname = node.getChild(0);
//			TcAst index  = node.getChild(1);
//			TcAst colaccess = node.getChild(2);
//			
//			if (index.getType()!=TT_COLNAMESTATIC) {
//				System.out.println("TT_INPUTCALCCALL: only static input calc names are allowed. " + idname.getText() + " line " + colaccess.getLine());
//			}
//			TcAst calc = colaccess.getChild(0);
//			
//			String inputname = idname.getText().toUpperCase();
//			String calcname  = calc.getText().toUpperCase();
//
//			Scope scopeinput = idname.getScope();
//			Symbol symbol = scopeinput.resolve(inputname);
//			if (symbol==null) {
//				symbol = scopeinput.resolve(inputname);
//				System.out.println("TT_INPUTCALCCALL: could not resolve " + inputname + " in " + scopeinput.getFullScopeName() + " line " + node.getLine());
//				break;
//			} 
//			ScopedSymbol scopedsymbol = (ScopedSymbol) symbol;
//			Symbol symbolinputprop = scopedsymbol.resolve(calcname);
//			if (symbolinputprop==null) {
//				System.out.println("TT_INPUTCALCCALL: could not resolve " + calcname + " within input " + inputname + " in " + scopeinput.getFullScopeName() + " line " + node.getLine());
//				break;
//			}
//			//visit(idname);
//			//visit(idprop);
//			break;
//		}
		case TT_INPUTACCESSSIMPLE: {
			// ^(TT_INPUTACCESSSIMPLE ID)
			TcAst idname = node.getChild(0);
			String name = idname.getText().toUpperCase();
			GenInput input = model.getInput(name);
			if (input==null) {
				throw new RuntimeException("did not find GenInput for name " + name + ", line " + idname.getLine());
			}					
			addVar(node, "tc.i." + name + ".$$value(_s)");
			
//			if (input.hasCheck()) {
//				addVar(node, "I." + name + "$CHECK(_s, (V[]) null)");
//			} else {
//				int inputindex = input.getIndex();
//				int[] autocounters = model.getInputAutocounters(input);
//				if (autocounters!=null) {
//					model.getInputAutocounters(input);
//					String scounters = "new int[] " + Arrays.toString(autocounters).replace('[', '{').replace(']', '}');
//					addVar(node, "_s.getInputAutocounter(" + inputindex + ", " + scounters + " /* " + name + "*/" + ")");
//				} else {
//					addVar(node, "_s.getInput(" + inputindex + "/* " + name + "*/" + ")");
//				}
//			}
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
			addVar(node, "_s.getInput(" + inputindex + "/* " + name + "*/" + ", indices)");
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
			StringBuffer s = new StringBuffer(64);
			if (columnaccess==null) {
				if (input.hasCheck()) {
					s.append(JSInput.NS_INPUT + ".");
					s.append(name);
					s.append(".$$value(_s");
				} else {
					s.append("_s.getInput(");
					s.append(input.getIndex());
					s.append(" /* " + name + " */ ");
				} 
			} else {
				//^(TT_COLNAMESTATIC  id)
				TcAst colast = columnaccess.getChild(0);
				// String colname = colast.getText().toUpperCase();
				s.append(JSInput.NS_INPUT + ".");
				s.append(name);
				s.append(".$");
				s.append(colast.getText());
				s.append("(_s");
			}
			for (int indindex=0; indindex<index.getChildCount(); indindex++) {
				s.append(", ");
				TcAst indexformula = index.getChild(indindex);
				s.append(getVar(indexformula));
			}
			s.append(')');
			addVar(node, s.toString());
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
				String sc = getConstantFormula(String.valueOf(table.getNumrows()), V.getInstance(table.getNumrows()));
				addVar(node, sc);
			} else {
				visit(tab);							
				addVar(node, buildTableMethodCall(getVar(tab), "getNumrows", new String[]{}));
			}
			break;
		}
		
		// TODO: vhhbr01
		case KEYWORD_TABCOLS: {
			TcAst tab = node.getChild(0);
			if (tab.getType()==ID) {
				String tablename = tab.getText().toUpperCase();
				GenTable table = model.getTable(tablename);
				if (table==null) {
					throw new RuntimeException("can not find table " + tablename + ", line " + node.getLine());
				}
				String sc = getConstantFormula(
						"V.getInstance(" + table.getNumcols() + ")",
						V.getInstance(table.getNumcols())
					);
				addVar(node, sc);
			} else {
				visit(tab);
				String s = "((V) Tables.dynamicCall("
						+ getVar(tab) + ", Tables.CALL_NUMCOLS))";
				addVar(node, s);
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
			int method = (rowsrange ? 20 : 10) + (colsrange ? 2 : 1);
			if (tab.getType()==ID) {
				String tablename = tab.getText().toUpperCase();
				GenTable table = model.getTable(tablename);
				String methodname=null;
				switch(method) {
				case 11: methodname = "getCell"; break;
				case 12: methodname = "getCellsRow"; break;
				case 21: methodname = "getCellsColumn"; break;
				case 22: methodname = "getCells"; break;
				default: methodname = method + "!?";
				}
				if (node.getType()==KEYWORD_CELLX) {
					methodname += "ByName";
				}
				if (table==null) {
					throw new RuntimeException("can not find table " + tablename + ", line " + node.getLine());
				}				
				addVar(node, buildTableMethodCall("'" + tablename + "'", methodname, buildParameterArray(rows, cols)));
			} else {
				String callconst=null;
				switch(method) {
				case 11: callconst = "getCell"; break;
				case 12: callconst = "getCellsRow"; break;
				case 21: callconst = "getCellsColumn"; break;
				case 22: callconst = "getCells"; break;
				default: callconst = method + "!?";
				}
				if (node.getType()==KEYWORD_CELLX) {
					callconst+="N";
				}
				visit(tab);
				for (int i=0; i<rows.getChildCount(); i++) {
					visit(rows.getChild(i));
				}
				for (int i=0; i<cols.getChildCount(); i++) {
					visit(rows.getChild(i));
				}				
				addVar(node, buildTableMethodCall(getVar(tab), callconst, buildParameterArray(rows, cols)));
			}
			break;
		}

		case KEYWORD_EXISTS: {
			//^(KEYWORD_EXISTS id|formula range range)
			TcAst tab = node.getChild(0);
			String tablename;
			if (tab.getType()==ID) {
				tablename = tab.getText();
				GenTable table = model.getTable(tablename);
				if (table==null) {
					throw new RuntimeException("can not find table " + tablename + ", line " + node.getLine());
				}
				tablename = "'" + tablename + "'";
			} else {
				visit(tab);
				tablename = getVar(tab);
			}
			for (int i=1; i<node.getChildCount(); i++) {
				visit(node.getChild(i));
			}												
			String[] params = new String[node.getChildCount()-1];			
			for (int i=1; i<node.getChildCount(); i++) {
				params[i-1] = getVar(node.getChild(i));
			}
			StringBuffer s = new StringBuffer(256);
			s.append("(");
			s.append(buildTableMethodCall(tablename, JSTables.FUNCTION_FINDROWEXACT, params));
			s.append(">=0)");
			addVar(node, s.toString());
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
				for (int i=1; i<node.getChildCount(); i++) {
					visit(node.getChild(i));
				}
				StringBuffer s = new StringBuffer(128);
				s.append(tablename);
				s.append(".");
				if (node.getType()==KEYWORD_LOOKUPX) {					
					s.append(JSTables.FUNCTION_FINDINTERVALUPCOLUMN);					
				} else {
					s.append(JSTables.FUNCTION_FINDINTERVALDOWNCOLUMN);
				}
				s.append("(");
				s.append(getVar(node.getChild(node.getChildCount()-1)));
				for (int startind=1, i=startind; i<node.getChildCount()-1; i++) {
					s.append(", ");
					s.append(getVar(node.getChild(i)));
				}
				s.append(')');
				addVar(node, s.toString());
			} else {
				visit(tab);
				int size = node.getChildCount();
				for (int i=1; i<size; i++) {
					visit(node.getChild(i));
				}																			
				String method = (node.getType()==KEYWORD_LOOKUPX) ? JSTables.FUNCTION_FINDINTERVALUPCOLUMN : JSTables.FUNCTION_FINDINTERVALDOWNCOLUMN;
				String[] params = new String[size-1];
				params[0] = getVar(node.getChild(size-1));
				for (int i=1; i<size-1; i++) {
					params[i] = getVar(node.getChild(i));					
				}				
				addVar(node, buildTableMethodCall(getVar(tab), method, params));				
			}
			break;
		}
		// TODO: vhhbr01
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
				StringBuffer s = new StringBuffer(128);
				s.append(tablename);
				s.append(".findIntervalUp(");
				s.append(getVar(node.getChild(1)));
				s.append(')');
				addVar(node, s.toString());
			} else {
				visit(tab);
				visit(node.getChild(1));
				StringBuffer s = new StringBuffer(256);
				s.append("((V) Tables.dynamicCall(");
				printVarname(tab, s);
				s.append(", ");
				s.append("Tables.CALL_INTERVAL_UP");
				s.append(", ");
				s.append(getVar(node.getChild(1)));
				s.append("))");
				addVar(node, s.toString());
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
			if(columnaccess!=null) {
				//^(TT_COLNAMESTATIC  id) | ^(TT_COLNAMEFORMULA formula)
				if (columnaccess.getType()==TT_COLNAMEFORMULA) {
					visit(columnaccess);
				} else {
					addVar(columnaccess, columnaccess.getChild(0).getText());
				}
			}
			StringBuffer s = new StringBuffer(128);
			s.append(JSTables.NS_TABLES);
			s.append(".");
			s.append(tablename);
			if (columnaccess==null) {
				s.append(".findExact(");
			} else {
				if (columnaccess.getType()==TT_COLNAMEFORMULA) {
					s.append(".");
					s.append(JSTables.FUNCTION_FINDEXACTCOLUMN);
					s.append("(");
					s.append(getVar(columnaccess));
					s.append(", ");
				} else {
					String colname = getVar(columnaccess);
					int colind = table.getColindex(colname);
					if (colind<0) {
						throw new RuntimeException("invalid columnname " + colname + " for table " + tablename + ", line " + columnaccess.getLine());
					}
					s.append(".");
					s.append(JSTables.FUNCTION_FINDEXACTCOLUMNINDEX);
					s.append("(");					
					s.append(colind);
					s.append(" /* ");
					s.append(colname);
					s.append(" */ ");
					s.append(", ");
				}
			}			
			for (int i=0; i<index.getChildCount(); i++) {
				if (i>0) {
					s.append(", ");
				}
				s.append(getVar(index.getChild(i)));
			}
			s.append(')');
			addVar(node, s.toString());
			break;
		}
		// TODO: vhhbr01
		case TT_COLNAMESTATIC: {
			TcAst idname =  node.getChild(0);
			String name = idname.getText();
			String s = getConstantFormula("V.getInstance(\"" + name + "\")", V.getInstance(name));
			//visit(idname);
			addVar(node, s);
			break;
		}

		case TT_COLNAMEFORMULA: {
			TcAst formula =  node.getChild(0);
			visit(formula);
			addVar(node, getVar(formula));
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
			
			//start COPY
			// ^(TT_TABLEACCESS ID ^(TT_INDEX formula*) columnaccess?)
			TcAst idname =  node.getChild(0);

			visit(dyntableformula);
			for (int i=0; i<index.getChildCount(); i++) {
				visit(index.getChild(i));
			}
			if(columnaccess!=null) {
				//^(TT_COLNAMESTATIC  id) | ^(TT_COLNAMEFORMULA formula)
				if (columnaccess.getType()==TT_COLNAMEFORMULA) {
					visit(columnaccess);
				} else {
					visit(columnaccess);
					//addVar(columnaccess, columnaccess.getChild(0).getText());
				}
			}			
			String method = null;
			ArrayList<String> list = new ArrayList<String>();
			
			if (columnaccess == null) {
				method = JSTables.FUNCTION_FINDEXACT;				
			}
			else {
				method = JSTables.FUNCTION_FINDEXACTCOLUMN;
				list.add(getVar(columnaccess));
			}						
			for (int i=0; i<index.getChildCount(); i++) {
				list.add(getVar(index.getChild(i)));
			}
			
			addVar(node, buildTableMethodCall(getVar(dyntableformula), method, list.toArray(new String[list.size()])));
			break;
		}
		case KEYWORD_IF: 
		case QUESTIONMARK: {
			//^(KEYWORD_IF formulaCond formulaThen formulaElse)
			TcAst cond =  node.getChild(0);
			TcAst thenformula =  node.getChild(1);
			TcAst elseformula =  node.getChild(2);
			printif(node, cond, thenformula, elseformula);
			break;
		}
		// TODO: vhhbr01
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
			BFInfo funcinfo = bf.get(name);
			if (name.equalsIgnoreCase("void")) {
				addVar(node, "VNull.vnull");
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
				addVar(node, "V.getInstance(_s.getSubmodel(" + getVar(astModelname) + ".stringValue()).calculate(" + getVar(astFuncname) + ".stringValue()))");
				break;
			} else if (name.equalsIgnoreCase("undefined")) {
				if (numparams!=1) {
					throw new RuntimeException("1 parameter expected for 'undefined', but got " + numparams + ", line " + node.getLine());
				}
				TcAst astInputnameAndIndex = parameterListe.getChild(0);
				visit(astInputnameAndIndex);
				addVar(node, "V.getInstance(_s.getInputIsNull(" + getVar(astInputnameAndIndex) + ".stringValue()))");
				break;
			}
			else if (name.equalsIgnoreCase("message")) {
				if (numparams!=2) {
					throw new RuntimeException("two parameters expected for message, but got " + numparams + ", line " + node.getLine());
				}
				TcAst message = parameterListe.getChild(0); /* TODO: do something with the message */
				TcAst formula = parameterListe.getChild(1);
				visit(formula);
				addVar(node, getVar(formula));
				break;
			} 
			
			if (funcinfo==null) {
				throw new RuntimeException("did not found builtin function " + name);
			}
			
			/* check number of parameters */
			if (numparams<funcinfo.minargs) {
				throw new RuntimeException("too less number of parameters for " + name + ": got " + numparams + " but minumum is " + funcinfo.minargs + ", line " + node.getLine());
			}
			if (funcinfo.maxargs>=0 && numparams>funcinfo.maxargs)  {
				throw new RuntimeException("too much numbers of parameters for " + name + ": " + numparams + " but maximum is " + funcinfo.maxargs);
			}
			if (funcinfo.instancemethod && funcinfo.maxargs==0) {
				throw new RuntimeException("instance base method " + funcinfo.javaname + " needs at least one parameter!");
			}
			if (!funcinfo.instancemethod) { //static method
				if (numparams==0) {
					addVar(node, funcinfo.javaname + "()");
				} else {
					visit(parameterListe);
					StringBuffer s = new StringBuffer(128);
					s.append(funcinfo.javaname); s.append('(');
					for (int i=0; i<parameterListe.getChildCount(); i++) {
						TcAst child = parameterListe.getChild(i);
						if (i>0) {
							s.append(", ");
						}
						s.append(getVar(child));
					}
					s.append(')');
					addVar(node, s.toString());
				}
			} else { //is instancemethod
				visit(parameterListe);
				StringBuffer s = new StringBuffer(128);
				printVarname(parameterListe.getChild(0), s);
				s.append('.');
				s.append(funcinfo.javaname);
				s.append('(');
				for (int indstart=1, i=indstart; i<parameterListe.getChildCount(); i++) {
					TcAst child = parameterListe.getChild(i);
					if (i>indstart) {
						s.append(", ");
					}
					s.append(getVar(child));
				}
				s.append(')');
				addVar(node, s.toString());
			}
			break;
		}
		case TT_FUNCALL: {
			//^(TT_FUNCALL ID ^(TT_PARAMETERS formula*)?)
			TcAst id =  node.getChild(0);
			String name = id.getText();
			String nameUpper = id.getText().toUpperCase();
			TcAst parameterListe = node.getChildCount()>1 ? node.getChild(1) : null;
			int nargs = parameterListe!=null ? parameterListe.getChildCount() : 0;

			Scope scope = id.getScope();
			Symbol symbol = scope.resolve(nameUpper, nargs, false);
			if (symbol==null) {
				symbol = scope.resolve(nameUpper, nargs, false);
				System.out.println("TT_FUNCALL: could not resolve " + nameUpper + " in " + scope.getFullScopeName() + " line " + node.getLine());
			}
			if (parameterListe==null || parameterListe.getChildCount()==0) {
				addVar(node, JSFunctions.NS_FUNCTIONS + "." + nameUpper + "(_s)");
			} else {
				visit(parameterListe);
				StringBuffer s=new StringBuffer(128);
				s.append(JSFunctions.NS_FUNCTIONS);
				s.append(".");
				s.append(nameUpper);
				s.append("(_s, ");
				for (int i=0; i<parameterListe.getChildCount(); i++) {
					TcAst formula = parameterListe.getChild(i);
					if (i>0) {
						s.append(", ");
					}
					printVarname(formula, s);
				}
				s.append(')');
				addVar(node, s.toString());
			}
			break;
		}
		// TODO: vhhbr01
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
			StringBuffer s=null;
			int calcindex = model.getCalcindex(name, nargs);
			switch(resolveret.getType()) {
			case ONE_SIMPLE: {
				int nodeindex = resolveret.getSumNode();
				s = new StringBuffer(64);
				s.append("N" + nodeindex + "." + "C" + calcindex + "(_s");
				for (int indarg=0; indarg<nargs; indarg++) {
					s.append(", ");
					s.append(getVar(parameterListe.getChild(indarg)));
				}
				s.append(")"); 
				break;
			}
			case LIST: {
				int nodeindex= resolveret.getSumNode();
				if (nodeindex<0) {
					throw new RuntimeException("could not get common parent for nodes " + resolveret.toString() + ", calc " + name + " (" + calcindex + "), line " + id.getLine());
				}
				s = new StringBuffer(128);
				s.append("Nodes.cc(" + nodeindex + ", " + calcid + ((selfcalccall&&nargs==0) ? ", false" : ", true") + ", _s");
				for (int indarg=0; indarg<nargs; indarg++) {
					s.append(", ");
					s.append(getVar(parameterListe.getChild(indarg)));
				}
				s.append(") /* " + name + "*/ ");
				break;
			}
			default: {
				throw new RuntimeException("unexpected resolveret: " + resolveret.toString() + ", line " + id.getLine());
			}
			}
			addVar(node, s.toString());
			//visit(id);
			visit(parameterListe);
			break;
		}
		case KEYWORD_SUMX:
		case KEYWORD_PRODX: 
		{
			TcAst id =  node.getChild(0);
			TcAst formulafrom =  node.getChild(1);
			TcAst formulato =  node.getChild(2);
			TcAst formulaloop =  node.getChild(3);
			
			String sumvar = getVarTemp() + "d";
			String indvar = "_" + id.getText().toLowerCase();
			String vardoubleto = getVarTemp() + "d";
			out.print("var ");
			out.print(sumvar);
			switch(tokentype) {
			case KEYWORD_SUMX:
				out.println(" = 0; //start sumx"); indent();
				break;
			case KEYWORD_PRODX:
				out.println(" = 1; //start prodx"); indent();
			}

			visit(formulafrom);
			visit(formulato);

			out.print("var ");
			out.print(vardoubleto);
			out.print(" = ");
			out.print(getVar(formulato));
			out.println(".doubleValue();"); indent();

			out.print("for (var ");
			out.print(indvar);
			out.print("=");
			out.print(getVar(formulafrom));
			out.print("; ");
			out.print(indvar);
			out.print(".doubleValue()<=");
			out.print(vardoubleto);
			out.print("; ");
			out.print(indvar);
			out.print("=(");
			out.print(indvar);
			out.println(".doubleValue()+1)) {");
			indent++; indent();
		
			visit(formulaloop);
			out.print(sumvar);
			switch(tokentype) {
			case KEYWORD_SUMX:
				out.print(" += ");
				break;
			case KEYWORD_PRODX:
				out.print(" *= ");
			}
			out.print(getVar(formulaloop));
			out.println(".doubleValue();");
			indent--; indent();
			out.println("}"); indent();

			addVar(node);
			out.print("var ");
			out.print(getVar(node));
			out.print(" = ");
			out.print(sumvar);
			out.println("; //result of sumx");
			indent();
			
			break;
		}
		case KEYWORD_VECTORX: {
			TcAst id =  node.getChild(0);
			TcAst formulafrom =  node.getChild(1);
			TcAst formulato =  node.getChild(2);
			TcAst formulaloop =  node.getChild(3);
			
			visit(formulafrom);
			visit(formulato);

			String vardoublefrom = getVarTemp() + "d";
			out.print("var ");
			out.print(vardoublefrom);
			out.print(" = ");
			out.print(getVar(formulafrom));
			out.println(".doubleValue();"); indent();

			String vardoubleto = getVarTemp() + "d";
			out.print("var ");
			out.print(vardoubleto);
			out.print(" = ");
			out.print(getVar(formulato));
			out.println(".doubleValue();"); indent();
			
			String sumvar = getVarTemp() + "a";
			String indvar = "_" + id.getText().toLowerCase();
			
			out.print("var ");
			out.print(sumvar);
			out.println(" = [];");
			
			indent();
			out.print("for (var ");
			out.print(indvar);
			out.print("=");
			out.print(getVar(formulafrom));
			out.print("; ");
			out.print(indvar);
			out.print("<=");
			out.print(vardoubleto);
			out.print("; ");
			out.print(indvar);			
			out.println("++) {");
			indent++; indent();
		
			visit(formulaloop);
			
			String tempvar = getVarTemp();
			out.print("var ");
			out.print(tempvar);
			out.print(" = ");
			out.print(getVar(formulaloop));
			out.println(';'); indent();
			
			out.print("if (");
			out.print(tempvar);
			out.print("!=null && !");
			out.print(tempvar);
			out.println(".isNull()) {"); indent++; indent();
			out.print(sumvar);
			out.print(".add(");
			out.print(tempvar);
			out.println(");"); indent--; indent();
			out.println("}");
			
			indent--; indent();
			out.println("}"); indent();

			addVar(node);
			out.print("var ");
			out.print(getVar(node));
			out.print(" = ");
			out.print(sumvar);
			out.println("; //result of vectorx");
			indent();
			
			break;
		}
		// TODO: vhhbr01
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
				StringBuffer s = new StringBuffer(128);
				s.append("V.getInstance(new V[] { N" + nodeindex + "." + "C" + calcindex + "(_s"); 
				for (int indarg=1; indarg<node.getChildCount(); indarg++) {
					s.append(", ");
					s.append(getVar(node.getChild(indarg)));
				}
				s.append(") })"); 
				addVar(node, s.toString());
				break;
			}
			case LIST: {
				int nodeindex = resolveret.getSumNode();
				if (nodeindex<0) {
					throw new RuntimeException("could not get common parent for nodes " + resolveret.toString() + ", calc " + name + " (" + calcindex + "), line " + id.getLine());
				}
				String tempname = getVarTemp();
				out.println("final ArrayList<V> " + tempname + " = new ArrayList<V>();"); indent();
				out.print  ("Nodes.ccc(" + nodeindex + ", " + calcindex + ", _s, " + tempname);
				for (int indarg=1; indarg<node.getChildCount(); indarg++) {
					out.print  (", ");
					out.print  (getVar(node.getChild(indarg)));
				}
				out.println (");"+ " /* " + name + "*/ "); indent();
				addVar(node);
				out.println("final V " + getVar(node) + " = V.getInstance(" + tempname + ");"); indent();
				break;
			}
			default: {
				throw new RuntimeException("unexpected resolveret: " + resolveret.toString() + ", line " + id.getLine());
			}
			}
			break;
		}
		// TODO: vhhbr01
		case KEYWORD_EXTRACT: {
			TcAst id =  node.getChild(0);
			String name = id.getText().toUpperCase();
			TcAst astPos = node.getChild(node.getChildCount()-1);
			int nargs = node.getChildCount()-2;

			for (int indarg=1; indarg<node.getChildCount()-1; indarg++) {
				visit(node.getChild(indarg));
			}
			visit(astPos);
			
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
				StringBuffer s=new StringBuffer(128);
				s.append("(new V[] { N" + nodeindex + "." + "C" + calcindex + "(_s");
				for (int indarg=1; indarg<node.getChildCount()-1; indarg++) {
					s.append(", ");
					s.append(getVar(node.getChild(indarg)));
				}
				s.append(") })[(int)" + getVar(astPos) + ".longValue()]");
				addVar(node, s.toString());
				break;
			}
			case LIST: {
				int nodeindex = resolveret.getSumNode();
				if (nodeindex<0) {
					throw new RuntimeException("could not get common parent for nodes " + resolveret.toString() + ", calc " + name + " (" + calcindex + "), line " + id.getLine());
				}
				String tempname = getVarTemp();
				out.println("final ArrayList<V> " + tempname + " = new ArrayList<V>();"); indent();
				out.print  ("Nodes.ccc(" + nodeindex + ", " + calcindex + ", _s, " + tempname);
				for (int indarg=1; indarg<node.getChildCount()-1; indarg++) {
					out.print(", ");
					out.print(getVar(node.getChild(indarg)));
				}
				out.println(");"+ " /* " + name + "*/ "); indent();
				addVar(node);
				out.println("final V " + getVar(node) + " = " + tempname + ".get((int)" + getVar(astPos) + ".longValue());"); indent();
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
				System.out.println("TT_USEVAR_LOCAL: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
			} else {
				addVar(node, "_" + symbol.getSymbolName().toLowerCase());
			}
			//visit(id);
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
			addVar(node, name.toLowerCase());
			//visit(id);
			break;
		}
		// TODO: vhhbr01
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
			String s = "_s.getTimesCounterV(" + timesid + " /* " + name + " */" + ")";
			addVar(node, s);
			break;
		}
		// TODO: vhhbr01
		case TT_TABREF: {
			//^(TT_TABREF id)
			TcAst id = node.getChild(0);
			String name = id.getText().toUpperCase();
			GenTable table = model.getTable(name);
			int tableindex = table.getIndex();
			String sc = getConstantFormula(
					"V.getInstanceTabref(" + tableindex + ")",
					V.getInstanceTabref(tableindex)
					);
			addVar(node, sc);
//			addVar(node, "vTabrefValue(getInstance(\"" + name + "\"))");
		}
		/* constants */
		case STRING: {
			String sc = getConstantFormula("'" + node.getText().replace("'","\\'") + "'",
				V.getInstance(node.getText())
			);
			
			addVar(node, sc);
			//out.print(node.getText());
			break;
		}
		case NUMBER: {
			String sc = getConstantFormula(
					node.getText(),
					V.getInstance(Double.parseDouble(node.getText()))
					);
			addVar(node, sc);
			//out.print(node.getText());
			break;
		}
		// TODO: vhhbr01
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
				String sc = getConstantFormula(
							"V.getInstanceFuncref(" + i + ")",
							V.getInstanceFuncref(i)
						);
				addVar(node, sc + " /*" +name + "*/ ");
			} else if (type==STRING) {
				String name = formula.getText().toUpperCase();
				GenFunction func = model.getFunction(name);
				if (func==null) {
					throw new RuntimeException("could not statically resolve function name " + name + ", line " + formula.getLine());
				}
				int i = func.getIndex();
				String sc = getConstantFormula("'" + name + "'", 
						//"V.getInstanceFuncref(" + i + ")",
						V.getInstanceFuncref(i)
					);
				addVar(node, sc + " /*" +name + "*/ ");
			} else {
				visit(formula);
				String s = "F.getFuncref(" + getVar(formula) + ")";
				addVar(node, s);
			}
			break;
		}
		// TODO: vhhbr01
		case KEYWORD_DOCALL: {
			TcAst funcref = node.getChild(0);
			visit(funcref);
			for (int indstart=1, i=indstart; i<node.getChildCount(); i++) {
				visit(node.getChild(i));
			}
			StringBuffer s = new StringBuffer(128);
			s.append("F.dynamicCall(");
			s.append(getVar(funcref));
			s.append(", _s");
			for (int indstart=1, i=indstart; i<node.getChildCount(); i++) {
				s.append(", ");
				s.append(getVar(node.getChild(i)));
			}
			s.append(')');
			addVar(node, s.toString());
			break;
		}
		// TODO: vhhbr01
		case KEYWORD_COUNTERLIST: {
			Scope scope = node.getScope();
			StringBuffer s = new StringBuffer(128);
			s.append("[");
			for (int i=0; i<node.getChildCount(); i++) {
				TcAst id =  node.getChild(i);
				String name = id.getText().toUpperCase();
				Symbol symbol = scope.resolve(name, 0, false);
				if (symbol==null) {
					symbol = scope.resolve(name, 0, false);
					throw new RuntimeException("TT_USEVAR_TIMESINDEX: could not resolve " + name + " in " + scope.getFullScopeName() + " line " + node.getLine());
				}
				
				int timesid = model.getTimesId(name);
				if (i>0) {
					s.append(", ");
				}
				s.append("_s.getTimesCounterV(" + timesid + " /* " + name + " */" + ")");
			}
			s.append("]");
			addVar(node, s.toString());
			break;
		}
		default:
			throw new RuntimeException("unknown token type: " + tokentype + " (" + TcSimpleParser.tokenNames[tokentype] + "), line " + node.getLine() + ": " +node.getText());
		}
	}
	
	public static Object[] dropFirstElement(Object[] a) {		
		List<Object> list = new ArrayList<Object>(Arrays.asList(a));
		Object[] ret = (list.size() > 0) ? new Object[list.size()-1] : new Object[0];
		if (list.size() > 0) {
			list.remove(0);
			list.toArray(ret);
		}
		return ret;
	}
	
	private String buildTableMethodCall(String tablename, String method, String[] params) {
		StringBuffer s = new StringBuffer(256);
		s.append(JSTables.NS_TABLES);
		s.append("[");
		s.append(tablename);
		s.append(".toUpperCase()].");
		s.append(method);
		s.append("(");		
		String p = Arrays.toString(params); 
		s.append(p.substring(1,p.length()-1));									
		s.append(")");
		return s.toString();
	}
	
	private String[] buildParameterArray(TcAst rows, TcAst cols) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i=0; i<rows.getChildCount(); i++) {
			list.add(getVar(rows.getChild(i)));
		}
		for (int i=0; i<cols.getChildCount(); i++) {
			list.add(getVar(cols.getChild(i)));
		}		
		return list.toArray(new String[list.size()]);
	}
	
	private void printif(TcAst node, TcAst cond, TcAst thenformula,	TcAst elseformula) {
		addVar(node);
		out.print("var "); printVarname(node); out.println(';'); indent();
		visit(cond);
		out.print("if (");
		printVarname(cond);
		out.println(") {");
		/* then */
		indent++; indent();
		visit(thenformula);
		printVarname(node);
		out.print(" = ");
		printVarname(thenformula);
		out.println(';'); 
		indent--; indent();
		out.println("} else {"); 
		/* else */
		indent++; indent();
		visit(elseformula);
		printVarname(node);
		out.print(" = ");
		printVarname(elseformula);
		out.println(';'); 
		indent--; indent();
		out.println("}"); indent();
	}
	
	public void unop(TcAst node) {
		if (node.getChildCount()!=1) {
			throw new RuntimeException("invalid number of childs for " + node.getText() + ", tokentype " + node.getType() + ", line " + node.getLine() + ": " +node.getChildCount());
		}
		TcAst op =  node.getChild(0);
		visit(op);
		String opname = unop.get(node.getType());
		addVar(node, getVar(op) + '.' + opname + "()");
	}

	private void printVarname(TcAst node) {
		String varname = localvars.get(node);
		if (varname!=null) {
			out.print(localvars.get(node));
		} else {
			out.print(" /* could not find local var for ast " + node.getText() + "(type " + TcSimpleParser.tokenNames[node.getType()] + ") " + ", line " + node.getLine() + " */ ");
		}
	}
	private void printVarname(TcAst node, StringBuffer sout) {
		String varname = localvars.get(node);
		if (varname!=null) {
			sout.append(localvars.get(node));
		} else {
			sout.append(" /* could not find local var for ast " + node.getText() + "(type " + TcSimpleParser.tokenNames[node.getType()] + ") " + ", line " + node.getLine() + " */ ");
		}
	}
	private void printVarname(TcAst node, StringBuilder sb) {
		String varname = localvars.get(node);
		if (varname!=null) {
			sb.append(localvars.get(node));
		} else {
			sb.append(" /* could not find local var for ast " + node.getText() + "(type " + TcSimpleParser.tokenNames[node.getType()] + ") " + ", line " + node.getLine() + " */ ");
		}
	}
	private void addVar(TcAst node) {
		localvar++;
		localvars.put(node,  "_" + localvar);
	}
	private String getVarTemp() {
		localvar++;
		return "_" + localvar;
	}
	private void addVar(TcAst node, String varname) {
		localvars.put(node,  varname);
	}
	private String getVar(TcAst node) {
		return localvars.get(node);
	}

	public void or(TcAst node) {
		if (node.getChildCount()!=2) {
			throw new RuntimeException("invalid number of childs for " + node.getText() + ", tokentype " + node.getType() + ", line " + node.getLine() + ": " +node.getChildCount());
		}
		TcAst left =  node.getChild(0);
		TcAst right =  node.getChild(1);

		addVar(node);
		out.print("var "); printVarname(node); out.println(';');indent();
		
		visit(left);
		out.print("if (");
		printVarname(left);
		out.println("==false) {"); 
		indent++; indent();
		visit(right);
		printVarname(node); 
		out.print(" = "); 
		printVarname(right); 
		out.println(";");
		indent--; indent();
		out.println("} else {");
		indent++; indent();
		printVarname(node); 
		out.println(" = true;"); 
		indent--; indent();
		out.println("}"); indent();
	}
	
	
	public void and(TcAst node) {
		if (node.getChildCount()!=2) {
			throw new RuntimeException("invalid number of childs for " + node.getText() + ", tokentype " + node.getType() + ", line " + node.getLine() + ": " +node.getChildCount());
		}
		TcAst left =  node.getChild(0);
		TcAst right =  node.getChild(1);

		addVar(node);
		out.print("var "); printVarname(node); out.println(';'); indent();
		
		visit(left);
		out.print("if (");
		printVarname(left);
		out.println(".booleanValue()) {"); 
		indent++; indent();
		visit(right);
		printVarname(node); 
		out.print(" = "); 
		printVarname(right); 
		out.println(";");
		indent--; indent();
		out.println("} else {");
		indent++; indent();
		printVarname(node); 
		String sc = getConstantFormula("V.getInstance(0)", V.getInstance(0));
		out.println(" = " + sc + " /*false*/;"); 
		indent--; indent();
		out.println("}"); indent();
	}
	
	public void binop(TcAst node) {
		if (node.getChildCount()!=2) {
			throw new RuntimeException("invalid number of childs for " + node.getText() + ", tokentype " + node.getType() + ", line " + node.getLine() + ": " +node.getChildCount());
		}
		TcAst left =  node.getChild(0);
		TcAst right = node.getChild(1);

		visit(left); 
		visit(right);

		addVar(node);
		out.print("var "); printVarname(node); 
		out.print(" = ");
		printVarname(left);
		String opname = op.get(node.getType());
		out.print('.');
		out.print(opname);
		out.print('(');
		printVarname(right);
		out.println(");"); indent();
	}
	
	public static PrintFormulaInfos printFormula(PrintStream out, TcAst astFormula, ModelSimple model, int indent, String prefix, String postfix) {
		JSFormula o = new JSFormula(out, astFormula, model, indent, prefix, postfix);
		o.run();
		return o.printFormulaInfos;
	}
	public static boolean isSimple(ModelSimple model, TcAst node) {
		return new JSFormula((PrintStream)null, node, model,0, "", "").isSimple(node);
	}
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
	
	private void run() {
		indent();
		visit(astFormula); 
		if (prefix!=null) {
			out.print(prefix);
		}
		printVarname(astFormula);
		if (postfix!=null) {
			out.print(postfix);
		}
		out.println(";");
	}
}
