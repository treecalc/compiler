/* use symbol tables / scopes to resolve names */

package treecalc.comp.java; 

import static treecalc.comp.TcSimpleParser.TT_ARGDEF;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import treecalc.comp.FormulaConstant;
import treecalc.comp.GenNodeCalc;
import treecalc.comp.ModelSimple;
import treecalc.comp.PrintFormulaInfos;
import treecalc.comp.ResolvedCalc;
import treecalc.comp.Scope;
import treecalc.comp.ScopeNode;
import treecalc.comp.ScopedSymbol;
import treecalc.comp.Symbol;
import treecalc.comp.TcAst;
import treecalc.comp.ModelSimple.NodeData;

import treecalc.rt.V;

public class JavaNodes {
	public final static boolean DEBUGOUT = false;
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private final boolean gwt;
	private final boolean trace;
	private PrintStream out;
	private int stmtsmethod=0;
	private final int MAX_STMTS_IN_METHOD=5000;
	private HashMap<String, FormulaConstant> constants;
	
	
	private JavaNodes(ModelSimple model, String foldernameout, String packagename, boolean gwt, boolean trace) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename   = packagename;
		this.gwt = gwt;
		this.trace = trace;
	}

	private void run() throws IOException {
		String classname = "Nodes"; //rootnode.getNodeName() + "_nodes";
		/* create the directory */
		new File(foldernameout).mkdirs();
		out = new PrintStream(foldernameout + "/" + classname + ".java", "UTF-8");
		if (packagename!=null && packagename.length()>0) {
			out.print("package ");
			out.print(packagename);
			out.println(";");
			out.println();
		}
		out.println("import java.util.ArrayList;");
		if (gwt) {
			out.println("import treecalc.rt.gwt.BitSet;");
		} else {
			out.println("import java.util.BitSet;");
		}
		out.println("import java.util.HashMap;");

		out.println();
		out.println("import treecalc.rt.S;");
		out.println("import treecalc.rt.V;");
		out.println("import treecalc.rt.VNull;");

		out.println();
		out.print("public final class ");
		out.print(classname);
		out.println(" {");
		print();
		out.println("}");
		out.close();

		printNodeClasses();
	}

	private void printNodeClasses() throws IOException {
		for (int i=0; i<model.getNodeDataSize(); i++) {
			printNodeClass(model.getNodeData(i), i);
		}
	}

	private void printNodeClass(ModelSimple.NodeData nodeData, int nodeindex) throws IOException {
		constants = new HashMap<String, FormulaConstant>();
		ScopeNode node = nodeData.node;
		List<Symbol> defines = node.getScopeDefines();
		String classname = "N" + nodeindex;
		out = new PrintStream(foldernameout + "/" + classname + ".java", "UTF-8");

		if (packagename!=null && packagename.length()>0) {
			out.print("package ");
			out.print(packagename);
			out.println(";");
			out.println();
		}
		
		out.println("import java.util.ArrayList;");
		out.println();
		out.println("import static treecalc.rt.V.*;");
		out.println("import static treecalc.rt.B.*;");
		out.println();
		out.println("import treecalc.rt.S;");
		if (trace) {
			out.println("import treecalc.rt.S.Traceaction;");
		}
		out.println("import treecalc.rt.V;");
		out.println("import treecalc.rt.VNull;");
		out.println("import treecalc.rt.VDouble;");
		
		out.println();
		out.print("public final class ");
		out.print(classname);
		out.print(" { //");
		printNodenameAbsolute(nodeData.node);
		
		out.println();
		out.println("   /* is node included? */");
		out.println("   public static boolean i(final S _s) {");
		TcAst inclusion = node.getAstInclusion();
		if (inclusion==null) {
			out.println("      return true;");
		} else {
//			out.println("      return");
			TcAst formula = (TcAst) inclusion.getChild(0);
			printFormula("return ", formula, ".booleanValue()");
			//JavaPrintFormulaPostfix.printFormula(out, formula, 2);
		}
		out.println("   }");

		out.println();
		out.println("   /* is node included multiple times? */");
		out.println("   public static int t(final S _s) {");
		TcAst times = node.getAstTimes();
		if (times==null) {
			out.println("      return 1;");
		} else {
			TcAst formula = (TcAst) times.getChild(0);
			printFormula("return (int) ", formula, ".longValue()");
		}
		out.println("   }");
		out.println();
		
		out.println("   public static V c(final int c, final S _s, final V...args) {");
		out.println("      switch(c) {");
		out.println("      case -2: return V.getInstance(i(_s));");
		out.println("      case -1: return V.getInstance(t(_s));");
		for (Symbol symbol : defines) {
			String name = symbol.getSymbolName();
			int    nargs = symbol.getNumArgs();
			if (symbol.getSymboltype()==Symbol.SYMBOLTYPE_NODERESULTDEF) {
				Integer indexcalc = model.getCalcindex(name, nargs);
				assert indexcalc!=null;
				out.print("      case ");
				out.print(indexcalc);
				out.print(": return C");
				out.print(indexcalc);
				out.print("(_s");
				for (int indarg=0; indarg<nargs; indarg++) {
					out.print(", ");
					out.print("args[");
					out.print(indarg);
					out.print(']');
				}
				out.println("); ");

			} else {
				out.println("      //unexpected symbol: " + symbol.getSymbolName() + ", type " + symbol.getSymboltype());
			}
		}
		out.println("      default: return null;");
		out.println("      } //end of switch");
		out.println("   }");
		for (Symbol symbol : defines) {
			if (symbol.getSymboltype()==Symbol.SYMBOLTYPE_NODERESULTDEF) {
				printCalc(symbol, nodeindex);
			} else {
				out.println("   //unexpected symbol: " + symbol.getSymbolName() + ", type " + symbol.getSymboltype());
			}
		}
		printConstants();
		out.println("}");
		out.close();
	}
	
	private void printFormula(String prefix, TcAst child, String postfix) {
		PrintFormulaInfos printFormulaInfos = JavaFormula.printFormula(out, child, model, 2, prefix, postfix);
		HashMap<String, FormulaConstant> constantMap = printFormulaInfos.getConstants();
		for (Entry<String,FormulaConstant> entry : constantMap.entrySet()) {
			String constname = entry.getKey();
			FormulaConstant constant = entry.getValue();
			constants.put(constname, constant);
		}
	}

	private void printCalc(Symbol symbol, int nodeindex) {
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
		
		out.println();
		out.print("   public static final V ");
		Integer index = model.getCalcindex(name, nargs);
		assert index!=null;
		out.print('C'); out.print(index);
		out.print("(final S _s");
		for (Symbol argsymbol : arguments) {
			String argname = argsymbol.getSymbolName().toLowerCase();
			out.print(", final V ");
			out.print(argname);
		}
		out.print(") { //"); out.print(name); out.println(", line " + node.getLine());
		
		//^(TT_RESULTDEF id arguments? formula) 
		TcAst nodeid = node.getChild(0);
		int nodeind=1;
		TcAst act = nodeind<node.getChildCount() ? (TcAst) node.getChild(nodeind) : null;
		TcAst astarguments=null;
		StringBuffer description = new StringBuffer(128);
		description.append('N');
		description.append(nodeindex);
		description.append('.');
		description.append(name.toUpperCase());
		String sDescription = description.toString();
		if (act!=null && act.getType()==TT_ARGDEF) {
			//^(KEYWORD_AS id)
			nodeind++;
			act = nodeind<node.getChildCount() ? (TcAst) node.getChild(nodeind) : null;
		}
		TcAst formula = node.getChild(node.getChildCount()-1);

		if (trace) {
			out.println("      StringBuffer $$argss = new StringBuffer();");
			boolean first=true;
			for (Symbol argument : arguments) {
				String argname = argument.getSymbolName();
				if (!first) {
					out.println("      $$argss.append(\", \");");
				}
				out.println("      $$argss.append(\"" + argname + "=\");");
				out.println("      $$argss.append(" + argument.getSymbolName().toLowerCase() + ");");
				first = false;
			}
			out.println("      String $$args = $$argss.toString();");
		}
		if (JavaFormula.isSimple(model, formula)) {
			if (trace) {
				out.println("      _s.trace(\"" + sDescription + "(\" + $$args + \")\", null, Traceaction.CALL);");
				printFormula("final V $$return = ", formula, "");
				out.println("      _s.trace(\"" + sDescription + "(\" + $$args + \")\", $$return.toString(), Traceaction.RETURN_SIMPLE);");
				out.println("      return $$return;");
			} else {
				printFormula("return ", formula, "");
			}
		} else {
			out.print  ("      final Object cacheKey = _s.getCacheKey(" + model.getCacheId(sDescription));
			for (Symbol argument : arguments) {
				String argname = argument.getSymbolName();
				out.print(", ");
				out.print(argname.toLowerCase());
			}
			out.println(");");
			
			out.println("      final V $$returncache = _s.readCache(cacheKey);");
			out.println("      if($$returncache!=null) {");
			if (trace) {
				out.println("         _s.trace(\"" + sDescription + "(\" + $$args + \")\", $$returncache.toString(), Traceaction.RETURN_HIT);");
			}
			out.println("         return $$returncache;");
			out.println("      }");
			if (trace) {
				out.println("      _s.trace(\"" + sDescription + "(\" + $$args + \")\", null, Traceaction.CALL);");
			}
			printFormula("final V $$return = ", formula, "");
			out.println("      _s.writeCache(cacheKey, $$return);");
			if (trace) {
				out.println("      _s.trace(\"" + sDescription + "(\" + $$args + \")\", $$return.toString(), Traceaction.RETURN_MISS);");
			}
			out.println("      return $$return;");
		}
		out.println("   }");
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
	
	private void print() throws IOException {
		if (DEBUGOUT) {
			printDebug();
		}
		/* variable definitions */
		/* ==================== */
		/* calcs */
		out.println("   /* calc names to index */");
		out.print("   private static final HashMap<String,Integer> cni = new HashMap<String,Integer>(");
		out.print(model.getCalcnamesSize());
		out.println(", 0.5f);");
		
		/* calc defined in node? */
		out.println("   /* calcs defined in one node (node calcs own) */");
		out.print("   private static final BitSet[] nco = new BitSet[");
		out.print(model.getNodeDataSize());
		out.println("];");
		
		/* calc defined in node or subnodes (children, links)?*/
		out.println("   /* calcs defined in node or subnodes (node calcs total) */");
		out.print("   private static final BitSet[] nct = new BitSet[");
		out.print(model.getNodeDataSize());
		out.println("];");
		
		/* edges */
		out.println("   /* edges between nodes */");
		out.print("   private static final int[][] edg = new int[");
		out.print(model.getNodeDataSize());
		out.println("][];");
		out.println("   private static final int[] e0 = new int[] {};");
		
		/* some general methods */
		printGenericCall();
		printGenericCallIncluded();
		printGenericCallTimes();
		printChildCall();
		printCollateCall();
		printMaintreeCall();
		printGetCalcIndex();
		
		
		/* static initializer and initialization methods */
		/* ============================================= */
		out.println();
		out.println("   static {");
		out.print("      for (int i=0; i<");
		out.print(model.getNodeDataSize());
		out.println("; i++) {");
		out.print("         nco[i] = new BitSet(");
		out.print(model.getCalcnamesSize());
		out.println(");");
		out.print("         nct[i] = new BitSet(");
		out.print(model.getCalcnamesSize());
		out.println(");");
		out.println("      }");
		int initmethod=0;
		
		out.println("      final HashMap<String,Integer> c = cni;");
		for (int i=0; i<model.getCalcnamesSize(); i++) {
			GenNodeCalc gencalc = model.getCalcname(i);
			String calcname = gencalc.getCalcname();
			int    nargs    = gencalc.getNumArgs();
			out.print("      c.put(\"");
			out.print(calcname);
			out.print(':');
			out.print(nargs);
			out.print("\",");
			out.print(i);
			out.println(");");
			stmtsmethod += 2;
		}
		
		out.println("      BitSet b;");
		for (int i=0; i<model.getNodeDataSize(); i++) {
			NodeData data = model.getNodeData(i);
			if (stmtsmethod>MAX_STMTS_IN_METHOD) {
				initmethod++;
				out.print("      init");
				out.print(initmethod);
				out.println("();");
				out.println("   }");
				out.print("   private static final void init");
				out.print(initmethod);
				out.println("() {");
			    out.println("      BitSet b;");
				stmtsmethod=0;
			}
			print1(data);
		}
		out.println("      int[] e;");
		
		for (int i=0; i<model.getNodeDataSize(); i++) {
			NodeData data = model.getNodeData(i);
			if (stmtsmethod>MAX_STMTS_IN_METHOD) {
				initmethod++;
				out.print("      init");
				out.print(initmethod);
				out.println("();");
				out.println("   }");
				out.print("   private static final void init");
				out.print(initmethod);
				out.println("() {");
				out.println("      int[] e;");
				stmtsmethod=0;
			}
			printEdge1(data);
		}
		out.println("   }"); //end of static initializer (or of static init method)  
	}

	private void printMaintreeCall() {
		out.println("   /* call main tree */");
		out.println("   public static V cmt(int c, S _s, V...args) {");
		out.println("      switch(c) {");
		for (int c=0; c<model.getCalcnamesSize(); c++) {
			out.print  ("      case ");
			out.print  (c);
			out.print  (": ");
			Scope scope = model.maintreeScope;
			GenNodeCalc gencalc = model.getCalcname(c);
			String calcname = gencalc.getCalcname();
			int nargs = gencalc.getNumArgs();
			int calcindex = model.getCalcindex(calcname, nargs);
			ResolvedCalc resolveret = scope.resolveCalc(model, calcname, nargs, false);
			switch(resolveret.getType()) {
			case ONE_SIMPLE: {
				int nodeindex = resolveret.getSumNode();
				out.print  ("return ");
				out.print  ("N");
				out.print  (nodeindex);
				out.print  (".C");
				out.print  (c);
				out.print  ("(_s");
				for (int indarg=0; indarg<nargs; indarg++) {
					out.print  (", ");
					out.print  ("args[");
					out.print  (indarg);
					out.print  (']');
				}
				out.println(");");
				break;
			}
			case LIST: {
				int nodeindex = resolveret.getSumNode();
				if (nodeindex<0) {
					throw new RuntimeException("could not get common parent for nodes " + resolveret.toString() + ", calc " + calcname + " (" + calcindex + ")");
				}
				out.print  ("return cc(");
				out.print  (nodeindex);
				out.print  (", ");
				out.print  (c);
				out.println(", true, _s, args);");
				break;
			}
			}
		}
		out.println("      } //end of switch(c)");
		out.println("   return null;");
		out.println("   }");
	}

	private void printGetCalcIndex() {
		out.println();
		out.println("   public static int getCalcIndex(String calcnameUppercase) {");
		out.println("      Integer calcindex = cni.get(calcnameUppercase);");
		out.println("      return calcindex!=null ? calcindex : -1;");
		out.println("   }");
	}

	private void printChildCall() {
		out.println("   /* child call; n:node index; c:calc index */");
		out.println("   public static V cc(final int n, final int c, boolean self, final S _s, final V...args) {");
		out.println("      /* see if it is defined in node n */");
		out.println("      if (self && nco[n].get(c)) {");
		out.println("         if (!gci(n, _s)) {");
		out.println("            return VNull.vnull;");
		out.println("         }");
		out.println("         /* get info about timer (timerid or <0 and number) ... is encoded as two ints in a long */");
		out.println("         long timeslong = gct(n, _s);");
		out.println("         int  timesid   = (int) timeslong;");
		out.println("         if (timesid<0) { //no multiple");
		out.println("            return gc(n, c, _s, args);");
		out.println("         } else { //iterate and put times info into status");
		out.println("            int times = (int) (timeslong>>32);");
		out.println("            if (times<=0) { //nothing to do");
		out.println("               return VNull.vnull;");
		out.println("            }");
		out.println("            _s.pushTimesCounter(timesid);");
		out.println("            /* just like with children, sum over the node multiple times and accumulate the computed values (or return the one and only unchanged) */");
		out.println("            V ret = VNull.vnull;");
		out.println("            double sum=0.0;");
		out.println("            int i=0;");
		out.println("            for (int t=0; t<times; t++) {");
		out.println("               final V elem = gc(n, c, _s, args);");
		out.println("               if (elem!=null && !elem.isNull()) {");
		out.println("                  if(i==0) {");
		out.println("                     ret = elem;");
		out.println("                  } else if (i==1) {");
		out.println("                     sum = ret.doubleValue() + elem.doubleValue();");
		out.println("                  } else {");
		out.println("                     sum += elem.doubleValue();");
		out.println("                  }");
		out.println("                  i++;");
		out.println("               }");
		out.println("               /* increment to counter */");
		out.println("               _s.incTimesCounterTop();");
		out.println("            }");
		out.println("            _s.popTimesCounter();");
		out.println("            return i>1 ? V.getInstance(sum) : ret;");
		out.println("         }");
		out.println("      }");
		out.println("      /* if not, see if it is defined in child/linked nodes */");
		out.println("      if (nct[n].get(c)) {");
		out.println("         /* check if the node is included */");
		out.println("         if (!gci(n, _s)) {");
		out.println("            return VNull.vnull;");
		out.println("         }");
		out.println("         /* get info about timer (timerid or <0 and number) ... is encoded as two ints in a long */");
		out.println("         V ret = VNull.vnull;");
		out.println("         double sum=0.0;");
		out.println("         int i=0;");
		out.println("         long timeslong = gct(n, _s);");
		out.println("         int  timesid   = (int) timeslong;");
		out.println("         if (timesid<0) { //no multiple");
		out.println("            final int[] edges = edg[n];");
		out.println("            for (int edge : edges) {");
		out.println("               final V elem = cc(edge, c, true, _s, args);");
		out.println("               if (elem!=null && !elem.isNull()) {");
		out.println("                  if(i==0) {");
		out.println("                     ret = elem;");
		out.println("                  } else if (i==1) {");
		out.println("                     sum = ret.doubleValue() + elem.doubleValue();");
		out.println("                  } else {");
		out.println("                     sum += elem.doubleValue();");
		out.println("                  }");
		out.println("                  i++;");
		out.println("               } //end of if(elem!=null..)");
		out.println("            } //end of for(edge)");
		out.println("            return i>1 ? V.getInstance(sum) : ret;");
		out.println("         } else { //timesid>=0 -> we are in a 'times'-node ");
		out.println("            int timesnumber = (int) (timeslong>>32);");
		out.println("            if (timesnumber>0) {");
		out.println("               _s.pushTimesCounter(timesid);");
		out.println("               final int[] edges = edg[n];");
		out.println("               for (int edge : edges) {");
		out.println("                  for (int t=0; t<timesnumber; t++) {");
		out.println("                     _s.setTimesCounterTop(t);");
		out.println("                     final V elem = cc(edge, c, true, _s, args);");
		out.println("                     if (elem!=null && !elem.isNull()) {");
		out.println("                        if(i==0) {");
		out.println("                           ret = elem;");
		out.println("                        } else if (i==1) {");
		out.println("                           sum = ret.doubleValue() + elem.doubleValue();");
		out.println("                        } else {");
		out.println("                           sum += elem.doubleValue();");
		out.println("                        }");
		out.println("                        i++;");
		out.println("                     } //end of if(elem!=null..)");
		out.println("                  } //end of for(t)");
		out.println("               } //end of for(edge)");
		out.println("               _s.popTimesCounter();");
		out.println("               return i>1 ? V.getInstance(sum) : ret;");
		out.println("            } else /* timesnumber<=0 */ {");
		out.println("               return VNull.vnull;");
		out.println("            }");
		out.println("         }");
		out.println("      }");
		out.println("      return VNull.vnull;");
		out.println("   }");
		out.println();
	}

	private void printCollateCall() {
		out.println("   /* child collate call; n:node index; c:calc index */");
		out.println("   /* same as cc(..), but results are not summed but instead put into a list */");
		out.println("   public static void ccc(final int n, final int c, final S _s, final ArrayList<V> results, final V...args) {");
		out.println("      /* see if it is defined in node n */");
		out.println("      if (nco[n].get(c)) {");
		out.println("         if (!gci(n, _s)) {");
		out.println("            return;");
		out.println("         }");
		out.println("         /* get info about timer (timerid or <0 and number) ... is encoded as two ints in a long */");
		out.println("         long timeslong = gct(n, _s);");
		out.println("         int  timesid   = (int) timeslong;");
		out.println("         if (timesid<0) { //no multiple");
		out.println("            final V elem = gc(n, c, _s, args);");
		out.println("            if (elem!=null && elem!=VNull.vnull) {");
		out.println("               results.add(elem);");
		out.println("            }");
		out.println("            return;");
		out.println("         } else { //iterate and put times info into status");
		out.println("            int times = (int) (timeslong>>32);");
		out.println("            if (times<=0) { //nothing to do");
		out.println("               return;");
		out.println("            }");
		out.println("            _s.pushTimesCounter(timesid);");
		out.println("            /* just like with children, sum over the node multiple times and accumulate the computed values (or return the one and only unchanged) */");
		out.println("            for (int t=0; t<times; t++) {");
		out.println("               final V elem = gc(n, c, _s, args);");
		out.println("               if (elem!=null && !elem.isNull()) {");
		out.println("                  results.add(elem);");
		out.println("               }");
		out.println("               /* increment to counter */");
		out.println("               _s.incTimesCounterTop();");
		out.println("            }");
		out.println("            _s.popTimesCounter();");
		out.println("            return;");
		out.println("         }");
		out.println("      }");
		out.println("      /* if not, see if it is defined in child/linked nodes */");
		out.println("      if (nct[n].get(c)) {");
		out.println("         /* check if the node is included */");
		out.println("         if (!gci(n, _s)) {");
		out.println("            return;");
		out.println("         }");
		out.println("         /* get info about timer (timerid or <0 and number) ... is encoded as two ints in a long */");
		out.println("         long timeslong = gct(n, _s);");
		out.println("         int  timesid   = (int) timeslong;");
		out.println("         if (timesid<0) { //no multiple");
		out.println("            final int[] edges = edg[n];");
		out.println("            for (int edge : edges) {");
		out.println("               ccc(edge, c, _s, results, args);");
		out.println("            }");
		out.println("            return;");
		out.println("         } else { //timesid>=0 -> we are in a 'times'-node ");
		out.println("            int timesnumber = (int) (timeslong>>32);");
		out.println("            if (timesnumber>0) {");
		out.println("               _s.pushTimesCounter(timesid);");
		out.println("               final int[] edges = edg[n];");
		out.println("               for (int edge : edges) {");
		out.println("                  for (int t=0; t<timesnumber; t++) {");
		out.println("                     _s.setTimesCounterTop(t);");
		out.println("                     ccc(edge, c, _s, results, args);");
		out.println("                  }");
		out.println("               }");
		out.println("               _s.popTimesCounter();");
		out.println("               return;");
		out.println("            } else /* timesnumber<=0 */ {");
		out.println("               return;");
		out.println("            }");
		out.println("         }");
		out.println("      }");
		out.println("   }");
		out.println();
	}

	
	private void printGenericCall() {
		out.println();
		out.println("/**"); 
		out.println(" * generic call of node/calc");
		out.println(" * @param n nodeid");
		out.println(" * @param c calcid");
		out.println(" */");
		out.println("   public static V gc(final int n, final int c, final S _s, final V...args) {");
		out.println("      switch(n) {");
		int nodeindexmaxlen = (int) Math.max(1, Math.ceil(Math.log10(model.getNodeDataSize())));
		String formatstr = "%-" + nodeindexmaxlen + "d";
		for (int i=0; i<model.getNodeDataSize(); i++) {
			String is = String.format(formatstr, i);
			out.print("      case ");
			out.print(is);
			out.print(": return N");
			out.print(is);
			out.print(".c(c, _s, args);  //");
			printNodenameAbsolute(model.getNodeData(i).node);
			out.println();
		}
		out.println("      default: return null;");
		out.println("      }");
		out.println("   }");
		out.println();
	}

	private void printGenericCallIncluded() {
		out.println();
		out.println("/**"); 
		out.println(" * generic call of inclusion for node");
		out.println(" * @param n nodeid");
		out.println(" */");
		out.println("   public static boolean gci(final int n, final S _s) {");
		out.println("      switch(n) {");
		int nodeindexmaxlen = (int) Math.max(1, Math.ceil(Math.log10(model.getNodeDataSize())));
		String formatstr = "%-" + nodeindexmaxlen + "d";
		for (int i=0; i<model.getNodeDataSize(); i++) {
			NodeData nodedata = model.getNodeData(i);
			ScopeNode scopenode = nodedata.node;
			TcAst astInclusion = scopenode.getAstInclusion();
			String is = String.format(formatstr, i);
			out.print("      case ");
			out.print(is);
			out.print(": ");
			if (astInclusion!=null) {
				out.print("return N");
				out.print(is);
				out.print(".i(_s);  //");
				printNodenameAbsolute(nodedata.node);
			} else {
				out.print("return true;");
			}
			out.println();
		}
		out.println("      default: return false;");
		out.println("      }");
		out.println("   }");
		out.println();
	}

	private void printGenericCallTimes() {
		out.println();
		out.println("/**"); 
		out.println(" * generic call of current times number for node");
		out.println(" * @param n nodeid");
		out.println(" * @return high 32bit: counterindex (<0 if there is none); low 32bit: value");
		out.println(" */");
		out.println("   public static long gct(final int n, final S _s) {");
		boolean hasTimes=false;
		for (int i=0; i<model.getNodeDataSize(); i++) {
			NodeData nodedata = model.getNodeData(i);
			ScopeNode scopenode = nodedata.node;
			TcAst astTimes = scopenode.getAstTimes();
			if (astTimes!=null) {
				hasTimes=true;
				break;
			}
		}
		if (!hasTimes) {
			out.println("      return -1L;");
			out.println("   }");
			return;
		}
		out.println("      //list of counters:");
		for (int i=0; i<model.getTimesSize(); i++) {
			String timesname = model.getTimesName(i);
			out.print("      //");
			out.print(i);
			out.print(':');
			out.println(timesname);
		}
		out.println("      switch(n) {");
		int nodeindexmaxlen = (int) Math.max(1, Math.ceil(Math.log10(model.getNodeDataSize())));
		String formatstr = "%-" + nodeindexmaxlen + "d";
		for (int i=0; i<model.getNodeDataSize(); i++) {
			NodeData nodedata = model.getNodeData(i);
			ScopeNode scopenode = nodedata.node;
			TcAst astTimes = scopenode.getAstTimes();
			String is = String.format(formatstr, i);
			out.print("      case ");
			out.print(is);
			out.print(": ");
			if (astTimes!=null) {
				TcAst astId = astTimes.getChild(1);
				String timesname = astId.getText().toUpperCase();
				int timesid = model.getTimesId(timesname);
				out.print("return ");
				out.print("((long) N");
				out.print(is);
				out.print(".t(_s)<<32)");
				out.print(" + ");
				out.print(timesid);
				out.printf("; //");
				printNodenameAbsolute(nodedata.node);
			} else {
				out.print("return -1;");
			}
			out.println();
		}
		out.println("      default: return -1;");
		out.println("      }");
		out.println("   }");
		out.println();
	}

	private void printDebug() {
		for (int i=0; i<model.getNodeDataSize(); i++) {
			NodeData node = model.getNodeData(i);
			out.print("   //node ");
			out.print(i);
			out.print(':');
			printNodenameAbsolute(node.node);
		}
		out.println();
//		for (int i=0; i<calclist.size(); i++) {
//			out.print("   //calc ");
//			out.print(i);
//			out.print(':');
//			out.println(calclist.get(i));
//		}
	}

	private void print1(NodeData data) {
		ScopeNode scopenode = data.node;
		int index = data.nodeindex;
		printBitset("nco", index, data.calcsOwnnode);
		if (!data.calcsOwnnode.equals(data.calcs)) {
			printBitset("nct", index, data.calcs);
		} else {
			out.print("      nct[");
			out.print(index);
			out.print("] = nco[");
			out.print(index);
			out.println("];");
			stmtsmethod++;
		} 
	}
	private void printBitset(String varname, int varindex, BitSet bs) {
		int start=0;
		boolean first=true;
		while ((start = bs.nextSetBit(start))>=0) {
			if (first) {
				first=false;
				out.print("      b=");
				out.print(varname);
				out.print('[');
				out.print(varindex);
				out.print("];");
				stmtsmethod++;
			}
			/* index has now next index of set bit */
			/* find next which is not set */
			int end = bs.nextClearBit(start);
			if (end<0) {
				end = model.getCalcnamesSize()-1;
			} else {
				end--;
			}
			out.print(" b.set(");
			out.print(start);
			if (start!=end) {
				out.print(',');
				out.print(end+1);
			}
			out.print(");");
			stmtsmethod++;
			start = end+1;
			if (start>=model.getCalcnamesSize()) {
				break;
			}
		}
		if (!first) {
			out.println();
		}
	}
	private void printEdge1(NodeData data) {
		ScopeNode scopenode = data.node;
		int index = data.nodeindex;
		
		/* walk through children and links, set bitsets for total calc names */
		List<ScopeNode> childs = scopenode.getChildren();
		List<ScopeNode> links  = scopenode.getLinks();
		int size=childs.size()+links.size();
		if (size==0) {
			out.print("      edg[");
			out.print(index);
			out.print("]=e0;");
		} else {
			out.print("      e=edg[");
			out.print(index);
			out.print("]=new int[");
			out.print(size);
			out.print("];");
			stmtsmethod+=2;
			int ind=0;
			for (ScopeNode child : scopenode.getChildren()) {
				out.print(" e[");
				out.print(ind);
				out.print("]=");
				out.print(model.getNodeData(child).nodeindex);
				out.print(';');
				ind++;
				stmtsmethod++;
			}
			for (ScopeNode link : scopenode.getLinks()) {
				out.print(" e[");
				out.print(ind);
				out.print("]=");
				out.print(model.getNodeData(link).nodeindex);
				out.print(';');
				ind++;
				stmtsmethod++;
			}
		}
		out.println();
	}
	
	private void printConstants() {
		out.println();
		for (Entry<String,FormulaConstant> entry : constants.entrySet()) {
			String constname = entry.getKey();
			FormulaConstant constant = entry.getValue();
			String name = constant.getNameInMethod();
			String createStatement = constant.getCreatestatement();
			V vconstant = constant.getConstant();
			out.print  ("   private final static V ");
			out.print  (name);
			out.print  (" = ");
			out.print  ("_C.");
			out.print  (name);
			out.println(";");
		}
	}


	public static void generate(ModelSimple model, String foldernameout, String packagename, boolean gwt, boolean trace) throws IOException {
		JavaNodes obj = new JavaNodes(model, foldernameout, packagename, gwt, trace);
		obj.run();
	}
}
