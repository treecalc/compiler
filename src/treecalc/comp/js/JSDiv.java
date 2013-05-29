package treecalc.comp.js;

/* use symbol tables / scopes to resolve names */

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import treecalc.comp.ModelSimple;
import treecalc.comp.ScopeNode;
import treecalc.comp.ModelSimple.NodeData;



public class JSDiv {
	public final static boolean DEBUGOUT = false;
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private PrintStream out;
	private JSPrintHelper p;
	public static final String NS_STATUS = "tc.s";
	

	private JSDiv(ModelSimple model, String foldernameout, String packagename) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename = packagename;
	}

	private void run() throws IOException {
		String classname = "_S";
		out = new PrintStream(foldernameout + "/" + classname + ".js", "ISO-8859-1");
		p = new JSPrintHelper(out);
		
		int indent = 0;
		p.pl(indent, NS_STATUS + " = {");
		printInputValues(indent+1);
		printGetInputIndex(indent+1);
		printReset(indent+1);
		printInputCalcNames(indent+1);
		printInputCalcIndex(indent+1);
		printGetInput(indent+1);
//		printGetInputIsNull(indent+1);
//		printGetAutocounterValues(indent+1);
//		printGetInputAutocounter(indent+1);
		printSetInputII(indent+1);
		printSetInput(indent+1);
//		printGetInputListIA(indent+1);
//		printGetInputList(indent+1);
//		printCalculateMaintreeIA(indent+1);
//		printCalculateMaintree(indent+1);
		printIsWhitespace(indent+1);
		printParseSetvarName(indent+1);
		printArrayCopy(indent+1);
		printBuildKey(indent+1);
		// printIntList(indent+1);
		
		
		
//		out.println("      @Override");
//		out.println("      public int hashCode() {");
//		out.println("         final int prime = 31;");
//		out.println("         int result = 1;");
//		out.println("         result = prime * result + (int) (key ^ (key >>> 32));");
//		out.println("         result = prime * result + keylen;");
//		out.println("         result = prime * result + Arrays.hashCode(vals);");
//		out.println("         return result;");
//		out.println("      }");
//		out.println();
//		out.println("      @Override");
//		out.println("      public boolean equals(Object obj) {");
//		out.println("         IntList other = (IntList) obj;");
//		out.println("         if (key != other.key)");
//		out.println("            return false;");
//		out.println("         if (keylen != other.keylen)");
//		out.println("            return false;");
//		out.println("         if (!Arrays.equals(vals, other.vals))");
//		out.println("            return false;");
//		out.println("         return true;");
//		out.println("      }");
//		out.println("   }");
//
		printParseInteger(indent+1);
		printParseExponent(indent+1);		
		printParseCalcname(indent+1);

//
//		
//		out.println("   /**");
//		out.println("    * left  32bit: counter");
//		out.println("    * right 32bit: timesid ");
//		out.println("    */");
//		for (int i=0; i<model.getTimesSize(); i++) {
//			String countername = model.getTimesName(i);
//			out.println("   //" + i + ": " + countername);
//		}
//		out.println("   private long[] timesStack = new long[10];");
//		out.println("   private int    timesLen   = 10;");
//		out.println("   private int    timesSize  = 0;");
//		out.println("   @Override");
//		out.println("   public void pushTimesCounter(int timesid) {");
//		out.println("      long newval = timesid;");
//		out.println("      if (timesLen<=timesSize) {");
//		out.println("         timesLen <<= 1;");
//		out.println("         timesStack = Arrays.copyOf(timesStack, timesLen);");
//		out.println("      }");
//		out.println("      timesStack[timesSize++] = newval;");
//		out.println("   }");
//		out.println();
//		out.println("   @Override");
//		out.println("   public void incTimesCounterTop() {");
//		out.println("      timesStack[timesSize-1] += 0x100000000L;");
//		out.println("   }");
//		out.println();
//		out.println("   @Override");
//		out.println("   public void setTimesCounterTop(int value) {");
//		out.println("      int i = timesSize-1;");
//		out.println("      int counterid = (int) timesStack[i];");
//		out.println("      long newval = ((long)value<<32) + counterid;");
//		out.println("      timesStack[i] = newval;");
//		out.println("   }");
//		out.println();
//		out.println("   @Override");
//		out.println("   public void popTimesCounter() {");
//		out.println("      timesSize--;");
//		out.println("   }");
//		out.println();
//		out.println("   @Override");
//		out.println("   public int getTimesCounter(int timesid) {");
//		out.println("      for (int i=timesSize; --i>=0;) {");
//		out.println("         long cand = timesStack[i];");
//		out.println("         int  id = (int) cand;");
//		out.println("         if (id==timesid) {");
//		out.println("            return (int) (cand >> 32);");
//		out.println("         }");
//		out.println("      }");
//		out.println("      return 0;");
//		out.println("   }");
//		out.println();
//		out.println("   @Override");
//		out.println("   public V getTimesCounterV(int timesid) {");
//		out.println("      for (int i=timesSize; --i>=0;) {");
//		out.println("         long cand = timesStack[i];");
//		out.println("         int  id = (int) cand;");
//		out.println("         if (id==timesid) {");
//		out.println("            int val = (int) (cand >> 32);");
//		out.println("            return V.getInstance(val);");
//		out.println("         }");
//		out.println("      }");
//		out.println("      return VDouble.vdbl0;");
//		out.println("   }");
//		out.println();
//		out.println("   public static class CacheKey {");
//		out.println("      private final int id;");
//		out.println("      private final V[] args;");
//		out.println("      private final long[] counters;");
//		out.println("      public CacheKey(int id, V[] args, long[] counters) {");
//		out.println("         this.id = id;");
//		out.println("         this.args = args;");
//		out.println("         this.counters = counters;");
//		out.println("      }");
//		out.println("      @Override");
//		out.println("      public int hashCode() {");
//		out.println("         final int prime = 31;");
//		out.println("         int result = 1;");
//		out.println("         result = prime * result + Arrays.hashCode(args);");
//		out.println("         result = prime * result + Arrays.hashCode(counters);");
//		out.println("         result = prime * result + id;");
//		out.println("         return result;");
//		out.println("      }");
//		out.println("      @Override");
//		out.println("      public boolean equals(Object obj) {");
//		out.println("         if (this == obj) {");
//		out.println("            return true;");
//		out.println("         }");
//		out.println("         if (obj == null) {");
//		out.println("            return false;");
//		out.println("         }");
//		out.println("         if (!(obj instanceof CacheKey)) {");
//		out.println("            return false;");
//		out.println("         }");
//		out.println("         CacheKey other = (CacheKey) obj;");
//		out.println("         if (id != other.id) {");
//		out.println("            return false;");
//		out.println("         }");
//		out.println("         if (!Arrays.equals(args, other.args)) {");
//		out.println("            return false;");
//		out.println("         }");
//		out.println("         if (!Arrays.equals(counters, other.counters)) {");
//		out.println("            return false;");
//		out.println("         }");
//		out.println("         return true;");
//		out.println("      }");
//		out.println("   }");
//
//		out.println();
//		out.println("   private static class LruCache<Key,Value> extends LinkedHashMap<Key, Value> {");
//		out.println("      private static final long serialVersionUID = 1L;");
//		out.println("      public LruCache(int initialCapacity, float loadFactor) {");
//		out.println("         super(initialCapacity, loadFactor, true);");
//		out.println("      }");
//		out.println("      protected boolean removeEldestEntry(Map.Entry<Key, Value> eldest) {");
//		out.println("         return size() > 20000;");
//		out.println("      }");
//		out.println("   }");
//		out.println();

		printCacheMethods(indent+1);
		
		printCalculateInput(indent+1);
		printCalculateInputCalc(indent+1);
		printCalculateFunction(indent+1);

		printCalculateTable(indent+1);

//		out.println("   @Override");
//		out.println("   public long getCalculateId(String name) {");
//		out.println("      /* <0 if notfound, id to be used with calculate(int id,...) otherwise");
//		out.println("       * high int: index; low int: lowest bits encode what it is, higher bits have additional index if necessary");
//		out.println("       * calcindex     000.....000: calc  (number of arguments is fixed to the number of arguments we got in name!)");
//		out.println("       * inputindex    000.....001: input ");
//		out.println("       * inpid         calcid..010: input-calc");
//		out.println("       * funcid        000.....011: func");
//		out.println("       * tabid         000.....100: table");
//		out.println("       * tabid         colind..101: table-col");
//		out.println("       */");
//		out.println("       Object[] parseRet = parseCalcname(name);");
//		out.println("       String nameWithParamcounter = (String) parseRet[0];");
//		out.println("       V[] args = (V[]) parseRet[1];");
//		out.println("       String nameUpper = (String) parseRet[2];");
//		out.println("       String calcnameUppercase = (String) parseRet[3];");
//		out.println("       int type = (Integer) parseRet[4];");
//		out.println("       switch (type) {");
//		out.println("       case 0: { //calc without parameters or input or function without parameters");
//		out.println("          if (calcnameUppercase==null) {");
//		out.println("             /* calc? */");
//		out.println("             final int calcindex = Nodes.getCalcIndex(nameWithParamcounter);");
//		out.println("             if (calcindex>=0) {");
//		out.println("                return ((long)calcindex<<32) + 0L;");
//		out.println("             }");
//		out.println("             /* input? */");
//		out.println("             final int inputindex = getInputIndex(nameUpper);");
//		out.println("             if (inputindex>=0) {");
//		out.println("                return ((long)inputindex<<32) + 1L;");
//		out.println("             }");
//		out.println("             /* function? */");
//		out.println("             final int funcindex = F.getFuncIndex(nameUpper);");
//		out.println("             if (funcindex>=0) {");
//		out.println("                return ((long)funcindex<<32) + 3L;");
//		out.println("             }");
//		out.println("             /* table? */");
//		out.println("             final int tabindex = Tables.getTableid(nameUpper);");
//		out.println("             if (tabindex>=0) {");
//		out.println("                return ((long)tabindex<<32) + 4L;");
//		out.println("             }");
//		out.println("             /* calc with number of parameters (:numparam)? */");
//		out.println("             final int calcindex2 = Nodes.getCalcIndex(nameUpper);");
//		out.println("             if (calcindex2>=0) {");
//		out.println("                return ((long)calcindex2<<32) + 0L;");
//		out.println("             }");
//		out.println("          } else {");
//		out.println("             /* input-calc */");
//		out.println("             final int inputindex = getInputIndex(nameUpper);");
//		out.println("             if (inputindex>=0) {");
//		out.println("                final int inputcalcid = getInputCalcIndex(calcnameUppercase);");
//		out.println("                if (inputcalcid>=0) {");
//		out.println("                   return ((long)inputindex<<32)");
//		out.println("                         + (long) ((int)(inputcalcid<<3)) //make sure it doesn't shift into inputindex ");
//		out.println("                         + 2L;");
//		out.println("                }");
//		out.println("             }");
//		out.println("             final int tabindex = Tables.getTableid(nameUpper);");
//		out.println("             if (tabindex>=0) {");
//		out.println("                int colind = (Integer) Tables.dynamicCall(");
//		out.println("                      V.getInstanceTabref(tabindex), ");
//		out.println("                      Tables.CALL_COLINDEX,");
//		out.println("                      calcnameUppercase");
//		out.println("                      );");
//		out.println("                return ((long)tabindex<<32)");
//		out.println("                      + (long) ((int)(colind<<3)) //make sure it doesn't shift into inputindex ");
//		out.println("                      + 5L;");
//		out.println("             }");
//		out.println("          }");
//		out.println("          break;");
//		out.println("       }");
//		out.println("       case 1: { //calc or function");
//		out.println("          /* calc? */");
//		out.println("          if (calcnameUppercase!=null) {");
//		out.println("             break;");
//		out.println("          }");
//		out.println("          final int calcindex = Nodes.getCalcIndex(nameWithParamcounter);");
//		out.println("          if (calcindex>=0) {");
//		out.println("            return ((long)calcindex<<32) + 0L;");
//		out.println("          }");
//		out.println("          /* function? */");
//		out.println("          final int funcindex = F.getFuncIndex(nameUpper);");
//		out.println("          if (funcindex>=0) {");
//		out.println("            return ((long)funcindex<<32) + 3L;");
//		out.println("          }");
//		out.println("          break;");
//		out.println("       }");
//		out.println("       case 2: { //input or table");
//		out.println("          if (calcnameUppercase==null) {");
//		out.println("             final int inputindex = getInputIndex(nameUpper);");
//		out.println("             if (inputindex>=0) {");
//		out.println("                return ((long)inputindex<<32) + 1L;");
//		out.println("             }");
//		out.println("             final int tabindex = Tables.getTableid(nameUpper);");
//		out.println("             if (tabindex>=0) {");
//		out.println("                return ((long)tabindex<<32) + 4L;");
//		out.println("             }");
//		out.println("             break;");
//		out.println("          } else {");
//		out.println("             final int inputindex = getInputIndex(nameUpper);");
//		out.println("             if (inputindex>=0) {");
//		out.println("                final int inputcalcid = getInputCalcIndex(calcnameUppercase);");
//		out.println("                if (inputcalcid>=0) {");
//		out.println("                   return inputindex + inputcalcid;");
//		out.println("                }");
//		out.println("                break;");
//		out.println("             }");
//		out.println("             final int tabindex = Tables.getTableid(nameUpper);");
//		out.println("             if (tabindex>=0) {");
//		out.println("                int colind = (Integer) Tables.dynamicCall(");
//		out.println("                      V.getInstanceTabref(tabindex), ");
//		out.println("                      Tables.CALL_COLINDEX,");
//		out.println("                      calcnameUppercase");
//		out.println("                      );");
//		out.println("                return ((long)tabindex<<32)");
//		out.println("                      + (long) ((int)(colind<<3)) //make sure it doesn't shift into inputindex ");
//		out.println("                      + 5L;");
//		out.println("             }");
//		out.println("          }");
//		out.println("       } //end of case 2");
//		out.println("       } //end of switch");
//		out.println("       throw new RuntimeException(\"invalid calculation string '\" + name + \"'\");");
//		out.println("   }");
		
		printCalculate(indent+1);

//		out.println("   @Override");
//		out.println("   public String calculate(long id) {");
//		out.println("      return calculate(id, (int[]) null);");
//		out.println("   }");
//		out.println("   @Override");
//		out.println("   public String calculate(long id, String... args) {");
//		out.println("      V[] vargs;");
//		out.println("      if (args==null) {");
//		out.println("         vargs = new V[0];");
//		out.println("      } else {");
//		out.println("         final int len = args.length;");
//		out.println("         vargs = new V[args.length];");
//		out.println("         for (int i=0; i<len; i++) {");
//		out.println("            vargs[i] = V.getInstance(args[i]);");
//		out.println("         }");
//		out.println("      }");
//		out.println("      int index = (int) (id>>32);");
//		out.println("      int lowint  = (int) id;");
//		out.println("      int whatitis = lowint & 0x7;");
//		out.println("      switch (whatitis) {");
//		out.println("      case 0: { //calcindex     000.....000: calc");
//		out.println("         return Nodes.cmt(index, this, vargs).stringValue();");
//		out.println("      }");
//		out.println("      case 1: { // inputindex    000.....001: input");
//		out.println("         return ((V) I.dynamicCall(index, -2, this, vargs)).stringValue();");
//		out.println("      }");
//		out.println("      case 2: { // inpid         calcid..010: input-calc");
//		out.println("         final int calcid = lowint >> 3;");
//		out.println("          return ((V) I.dynamicCall(index, calcid, this, vargs)).stringValue();");
//		out.println("      }");
//		out.println("      case 3:  { //funcid        000.....011: func");
//		out.println("         return F.dynamicCall(V.getInstanceFuncref(index), this, vargs).stringValue();");
//		out.println("      }");
//		out.println("      case 4:  { //tabid         000.....100: table");
//		out.println("         return ((V) Tables.dynamicCall(V.getInstanceTabref(index), Tables.CALL_EXACT, (Object[]) vargs)).stringValue();");
//		out.println("      }");
//		out.println("      case 5: { //tabid         colind..101: table-col");
//		out.println("         final int colind = lowint >> 3;");
//		out.println("             int lenvargs = vargs.length;");
//		out.println("             Object[] dynargs = new Object[lenvargs+1];");
//		out.println("             dynargs[0] = colind;");
//		out.println("              System.arraycopy(vargs, 0, dynargs, 1, lenvargs);");
//		out.println("              return ((V) Tables.dynamicCall(");
//		out.println("                     V.getInstanceTabref(index), ");
//		out.println("                      Tables.CALL_EXACT_COLIND,");
//		out.println("                      dynargs");
//		out.println("                      )).stringValue();");
//		out.println("      }");
//		out.println("      }");
//		out.println("      throw new RuntimeException(\"invalid calculation id: \" + id);");
//		out.println("   }");
//		out.println("   @Override");
//		out.println("   public String calculate(long id, int... args) {");
//		out.println("      V[] vargs;");
//		out.println("      if (args==null) {");
//		out.println("         vargs = new V[0];");
//		out.println("      } else {");
//		out.println("         final int len = args.length;");
//		out.println("         vargs = new V[args.length];");
//		out.println("         for (int i=0; i<len; i++) {");
//		out.println("            vargs[i] = V.getInstance(args[i]);");
//		out.println("         }");
//		out.println("      }");
//		out.println("      int index = (int) (id>>32);");
//		out.println("      int lowint  = (int) id;");
//		out.println("      int whatitis = lowint & 0x7;");
//		out.println("      switch (whatitis) {");
//		out.println("      case 0: { //calcindex     000.....000: calc");
//		out.println("         return Nodes.cmt(index, this, vargs).stringValue();");
//		out.println("      }");
//		out.println("      case 1: { // inputindex    000.....001: input");
//		out.println("         return ((V) I.dynamicCall(index, -2, this, vargs)).stringValue();");
//		out.println("      }");
//		out.println("      case 2: { // inpid         calcid..010: input-calc");
//		out.println("         final int calcid = lowint >> 3;");
//		out.println("          return ((V) I.dynamicCall(index, calcid, this, vargs)).stringValue();");
//		out.println("      }");
//		out.println("      case 3:  { //funcid        000.....011: func");
//		out.println("         return F.dynamicCall(V.getInstanceFuncref(index), this, vargs).stringValue();");
//		out.println("      }");
//		out.println("      case 4:  { //tabid         000.....100: table");
//		out.println("         return ((V) Tables.dynamicCall(V.getInstanceTabref(index), Tables.CALL_EXACT, (Object[]) vargs)).stringValue();");
//		out.println("      }");
//		out.println("      case 5: { //tabid         colind..101: table-col");
//		out.println("         final int colind = lowint >> 3;");
//		out.println("             int lenvargs = vargs.length;");
//		out.println("             Object[] dynargs = new Object[lenvargs+1];");
//		out.println("             dynargs[0] = colind;");
//		out.println("              System.arraycopy(vargs, 0, dynargs, 1, lenvargs);");
//		out.println("              return ((V) Tables.dynamicCall(");
//		out.println("                     V.getInstanceTabref(index), ");
//		out.println("                      Tables.CALL_EXACT_COLIND,");
//		out.println("                      dynargs");
//		out.println("                      )).stringValue();");
//		out.println("      }");
//		out.println("      }");
//		out.println("      throw new RuntimeException(\"invalid calculation id: \" + id);");
//		out.println("   }");
		
//		printNeeded(indent);

//		out.println();
//		out.println("   private HashMap<String,S> submodels = new HashMap<String,S>();");
//		out.println("   public S getSubmodel(String vpmname) {");
//		out.println("      S submodel = submodels.get(vpmname);");
//		out.println("      if (submodel!=null) {");
//		out.println("         return submodel;");
//		out.println("      }");
//		out.println("");
//		out.println("      String modelname = new File(vpmname).getName().toLowerCase();");
//		out.println("      int ind = modelname.lastIndexOf('.');");
//		out.println("      if (ind>0) {");
//		out.println("         modelname = modelname.substring(0, ind);");
//		out.println("      }");
//		out.println("      modelname = modelname.replaceAll(\"\\\\W\", \"_\");");
//		out.println("      try {");
//		out.println("         Class<?> myclass = Class.forName(\"gen.\" + modelname + \"._S\");");
//		out.println("         Constructor<?> constr = myclass.getConstructor(new Class[0]);");
//		out.println("         Object obj = constr.newInstance(new Object[0]);");
//		out.println("         submodel = (S) obj;");
//		out.println("         submodels.put(vpmname, submodel);");
//		out.println("         return submodel;");
//		out.println("      } catch (ClassNotFoundException e) {");
//		out.println("      } catch (SecurityException e) {");
//		out.println("      } catch (NoSuchMethodException e) {");
//		out.println("      } catch (IllegalArgumentException e) {");
//		out.println("      } catch (InstantiationException e) {");
//		out.println("      } catch (IllegalAccessException e) {");
//		out.println("      } catch (InvocationTargetException e) {");
//		out.println("      }");
//		out.println("      return null;");
//		out.println("   }");
//		out.println("}");

		p.pl(indent+1, "finalElement : {}");
		p.pl(indent, "}");
		
		print_in(indent);
		out.close();
	}
	
	private void printInputValues(int indent) {
		
//		out.println("   /**");
//		out.println("    * values for inputs without indizes, or with all '0' indizes.");
//		out.println("    * Those are NOT written into inputvalues, just into this \"fast access\" array.");
//		out.println("    */");		
		p.pl(indent, "inputvalues0dim: [],");
//		out.println("   /**");
//		out.println("    * HashMap<IntList, V> first value is inputid, then indizes follow.");
//		out.println("    *        The last index is >0 for sure,");
//		out.println("    *        minimum length of the int[] is 2 (otherwise the value lands in inputvalues0dim).");
//		out.println("    */");
		p.pl(indent, "inputvalues: {},");		
						
		String[] inputNames = new String[model.getInputSize()];
		for (int i=0; i<inputNames.length; i++) {
			inputNames[i] = model.getInput(i).getName();
		}
		p.printProperty(indent, "ina", inputNames);
//		for (int i=0; i<model.getInputSize(); i++) {
//			GenInput input = model.getInput(i);
//			String name = input.getName();
//			int    ind  = input.getIndex();
//			out.print  ("      i[");
//			out.print  (ind);
//			out.print  ("]=\"");
//			out.print  (name);
//			out.println("\";");
//		}
		
		
		
		
//		out.print  ("      for (int ind=0; ind<");
//		out.print  (model.getInputSize());
//		out.println("; ind++) {");
//		out.println("         in.put(i[ind], ind);");
//		out.println("      }");
//		out.println("   } //end of static initializer");
	}
	
	private void print_in(int indent) {
		p.pl(indent, NS_STATUS + "._in = (function() {");
		p.pl(indent+1, "var map = {};");
		p.pl(indent+1, "for (var i=0; i<" + model.getInputSize() + "; i++) {");		
		p.pl(indent+2, "map[tc.s.ina[i]] = i;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return map;");
		p.pl(indent, "})();");
	}
	
	private void printGetInputIndex(int indent) {
		p.pl(indent, "getInputIndex: function(nameUppercase) {");
		p.pl(indent+1, "var i = this._in[nameUppercase];");
		p.pl(indent+1, "return (undefined !== i) ? i : -1;");
		p.pl(indent, "},");
	}
	
	private void printReset(int indent) {
		p.pl(indent, "reset: function() {");
		p.pl(indent+1, "this.inputvalues0dim = [];");
		p.pl(indent+1, "this.inputvalues = {};");
		p.pl(indent, "},");	
	}
	
	private void printInputCalcNames(int indent) {				
		p.pl(indent, "ic : {");
		
		int size = model.getInputcalcSize();
		for (int i=0; i<size-1; i++) {
			p.pl(indent+1, "'" + model.getInputcalcName(i) + "': " + i + ",");			
		}
		if (0 < size) {
			p.pl(indent+1, "'" + model.getInputcalcName(size-1) + "': " + (size-1));
		}
		p.pl(indent, "},");	
	}
	
	private void printInputCalcIndex(int indent) {
		p.pl(indent, "getInputCalcIndex: function(name) {");
		p.pl(indent+1, "var i = this.ic[name];");
		p.pl(indent+1, "return (i === undefined) ? -1 : i;");
		p.pl(indent, "},");		
	}
	
	private void printGetInput(int indent) {
		p.pl(indent, "getInput: function(inputid /*, ... index*/) {");
		p.pl(indent+1, "var indices = Array.prototype.slice.call(arguments);");
		p.pl(indent+1, "indices.shift();");
		p.pl(indent+1, "indices = tc.i.helper.getIndexNorm(indices);");		
		p.pl(indent+1, "if (indices == 0) {");
		p.pl(indent+2, "return this.inputvalues0dim[inputid];");
		p.pl(indent+1, "}");		
		p.pl(indent+1, "return this.inputvalues[this.buildKey(inputid, indices)];");		
		p.pl(indent, "},");	
	}
	
	private void printGetInputIsNull(int indent) {
		p.pl(indent, "getInputIsNull: function(name) {");
		p.pl(indent+1, "var parsedname = this.parseSetvarName(name);");
		p.pl(indent+1, "if (parsedname == null) {");
		p.pl(indent+2, "return true;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var justnameUpper = parsedname[0];");
		p.pl(indent+1, "var index = parsedname[1];");
		p.pl(indent+1, "var inputid = this.getInputIndex(justnameUpper);");
		p.pl(indent+1, "if (inputid<0) {");
		p.pl(indent+2, "return true;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var vindex;");
		p.pl(indent+1, "if (index==null) {");
		p.pl(indent+2, "vindex = [];");
		p.pl(indent+1, "} else {");
		p.pl(indent+2, "var indexlen = index.length;");
		p.pl(indent+2, "vindex = new Array(indexlen);");
		p.pl(indent+2, "for (var i=0; i<indexlen; i++) {");
		p.pl(indent+3, "vindex[i] = index[i];");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "try {");
		p.pl(indent+2, "this.getInput(inputid, vindex);");
		p.pl(indent+2, "return false;");
		p.pl(indent+1, "} catch(e) {");
		p.pl(indent+2, "return true;");
		p.pl(indent+1, "}");
		p.pl(indent, "},");
	}
	
	private void printGetAutocounterValues(int indent) {		
		p.pl(indent, "getAutocounterValues: function(autocounters) {");
		p.pl(indent+1, "var clen = autocounters.length;");
		p.pl(indent+1, "var valarr = new Array(clen);");
		p.pl(indent+1, "var valarrsize = 0;");
		p.pl(indent+1, "var lastnotzero = -1;");
		p.pl(indent+1, "for (var i=0; i<clen; i++) {");
		p.pl(indent+2, "var c = autocounters[i];");
		p.pl(indent+2, "for (var j=this.timesSize; --j>=0;) {");
		p.pl(indent+3, "var cand = this.timesStack[j];");
		p.pl(indent+3, "var id = cand;");
		p.pl(indent+3, "if (id == c) {");
		p.pl(indent+4, "var val = cand >> 32;");
		p.pl(indent+4, "if (val>0) {");
		p.pl(indent+5, "lastnotzero = valarrsize;");
		p.pl(indent+5, "valarr[valarrsize] = val;");
		p.pl(indent+4, "} else {");
		p.pl(indent+5, "valarr[valarrsize] = 0;");
		p.pl(indent+4, "}");
		p.pl(indent+4, "valarrsize++;");
		p.pl(indent+4, "break;");
		p.pl(indent+3, "}");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (lastnotzero+1<clen) {");
		p.pl(indent+2, "return valarr.slice(0,lastnotzero+1);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "} else {");
		p.pl(indent+2, "return valarr;");
		p.pl(indent+1, "}");
		p.pl(indent, "}");		
	}
	
	private void printGetInputAutocounter(int indent) {
		p.pl(indent, "getInputAutocounter: function(inputid, autocounters) {");
		p.pl(indent+1, "var clen = autocounters.length;");
		p.pl(indent+1, "var valarr = new Array(clen);");
		p.pl(indent+1, "var valarrsize = 0;");
		p.pl(indent+1, "var lastnotzero = -1");
		p.pl(indent+1, "for (var i=0; i<clen; i++) {");
		p.pl(indent+2, "var c = autocounters[i];");
		p.pl(indent+2, "for (var j=this.timesSize; --j>=0) {");
		p.pl(indent+3, "var cand = timesStack[j];");
		p.pl(indent+3, "var id = +cand;");
		p.pl(indent+3, "if (id == c) {");
		p.pl(indent+4, "var val = cand >> 32;");
		p.pl(indent+4, "if (val>0) {");
		p.pl(indent+5, "lastnotzero = valarrsize;");
		p.pl(indent+5, "valarr[valarrsize] = val;");
		p.pl(indent+4, "}");
		p.pl(indent+4, "valarrsize++;");
		p.pl(indent+4, "break;");
		p.pl(indent+3, "}");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (lastnotzero<0) {");
		p.pl(indent+2, "var ret = inputvalues0dim[inputid];");
		p.pl(indent+2, "if (undefined != ret) {");
		p.pl(indent+3, "return ret;");
		p.pl(indent+2, "} else {");
		p.pl(indent+2, "throw 'ExceptionNeedMoreInput';");
		p.pl(indent+2, "}");
		p.pl(indent+1, "} else {");
		// TODO:
		// out.println("         final V ret = inputvalues.get(new IntList(inputid, valarr, lastnotzero+1));");
		p.pl(indent+2, "var ret = inputvalues.get(this.buildKey(inputid, valarr));");
		p.pl(indent+2, "if (ret!=null) {");
		p.pl(indent+3, "return ret;");
		p.pl(indent+2, "} else {");
		p.pl(indent+3, "throw 'ExceptionNeedMoreInput';");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent, "},");		
	}

	private void printSetInputII(int indent) {
		p.pl(indent, "setInputII: function(inputid, value /*, ... indices */) {");
		p.pl(indent+1, "if (!this.cacheEmpty) {");
		p.pl(indent+2, "this.initCache();");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var indices = Array.prototype.slice.call(arguments);");
        p.pl(indent+1, "indices.shift();");
        p.pl(indent+1, "indices.shift();");		
		p.pl(indent+1, "if (indices.length==0) {");
		p.pl(indent+2, "this.inputvalues0dim[inputid]=value;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var i;");
		p.pl(indent+1, "for (i=indices.length; --i>=0;) {");
		p.pl(indent+2, "var ind = indices[i];");
		p.pl(indent+2, "if (ind>0) {");
		p.pl(indent+3, "break;");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (i<0) {");
		p.pl(indent+2, "this.inputvalues0dim[inputid] = value;");
		p.pl(indent+2, "return;");
		p.pl(indent+1, "}");		
		p.pl(indent+1, "this.inputvalues[this.buildKey(inputid,indices)] = value;");
		p.pl(indent, "},");		
	}
	
	private void printSetInput(int indent) {
		p.pl(indent, "setInput: function(name, value) {");
		p.pl(indent+1, "if (!this.cacheEmpty) {");
		p.pl(indent+2, "this.initCache();");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var parsedname = this.parseSetvarName(name);");
		p.pl(indent+1, "if (parsedname == null) {");
		p.pl(indent+2, "return;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var justnameUpper = parsedname[0];");
		p.pl(indent+1, "var indices = parsedname[1];");
		p.pl(indent+1, "var id = this.getInputIndex(justnameUpper);");
		p.pl(indent+1, "if (indices == null) {");
		p.pl(indent+2, "this.inputvalues0dim[id] = value;");
		p.pl(indent+2, "return;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "this.inputvalues[this.buildKey(id,indices)] = value;");
		p.pl(indent, "},");		
	}
	
	private void printGetInputListIA(int indent) {
		p.pl(indent, "getInputListIA: function(inputid /*,...indices*/) {");
		p.pl(indent+1, "if (1 == arguments.length) {");
		p.pl(indent+2, "// Todo: return (List<String[]>) I.dynamicCall(inputid, -1, this);");
		p.pl(indent+1, "}");
		p.printArgumentsToArrayAndShift(indent+1);		
		p.pl(indent+1, "var vindex = new Array(indices.length);");
		p.pl(indent+1, "for (var i=0; i<indices.length; i++) {");
		p.pl(indent+2, "vindex[i] = indices[i];");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return null; // Todo: (List<String[]>) I.dynamicCall(inputid, -1, this, vindex);");
		p.pl(indent, "},");		
	}
	
	private void printGetInputList(int indent) {
		p.pl(indent, "getInputList: function(name) {");
		p.pl(indent+1, "var parsedname = this.parseSetvarName(name);");
		p.pl(indent+1, "if (parsedname == null) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var justnameUpper = parsedname[0];");
		p.pl(indent+1, "var indices = parsedname[1];");
		p.pl(indent+1, "int inputid = getInputIndex(justnameUpper);");
		p.pl(indent+1, "return this.getInputListIA(inputid, indices);");
		p.pl(indent, "},");		
	}
	
	private void printCalculateMaintreeIA(int indent) {
		p.pl(indent, "calculateMaintreeIA: function(calcindex /* ... args */) {");
						
		List<ScopeNode> mainchildren = model.maintreeScope.getChildren();
		if (mainchildren!=null && mainchildren.size()>0) {
			ScopeNode mainnode = mainchildren.get(0);
			NodeData mainnodeData = model.getNodeData(mainnode);
			
			// TODO: nodeindex public machen
			// int mainnodeindex = mainnodeData.nodeindex;
			
			p.pl(indent+1, "var args = Array.prototype.slice.call(arguments);");
			p.pl(indent+1, "args.shift();");
			p.pl(indent+1, "if (args.length > 0) {");
			p.pl(indent+2, "var argsV = new Array(args.length);");
			p.pl(indent+2, "for (var i=0; i<args.length; i++) {");
			p.pl(indent+2, "argsV[i] = args[i];");
			p.pl(indent+1, "}");
			// TODO:
		}
//			p.pl(indent+1, "return Nodes.cmt(calcindex");
//			
//			out.println("      int arglen = args!=null ? args.length : 0;");
//			out.println("      if(arglen>0) {");
//			out.println("         V[] argsV = new V[arglen];");
//			out.println("         for (int i=0; i<arglen; i++) {");
//			out.println("            argsV[i] = V.getInstance(args[i]);");
//			out.println("         }");
//			out.print  ("         return Nodes.cmt(");
//			out.println("calcindex, this, argsV).stringValue();");
//			out.println("      } else {");
//			out.print  ("         return Nodes.cmt(");
//			out.println("calcindex, this).stringValue();");
//			out.println("      }");
//		} else {
//			out.println("      throw new RuntimeException(\"the main tree is empty, can not compute anything there\");"); 
//		}
		p.pl(indent, "},");
	}
	
	private void printCalculateMaintree(int indent) {
		p.pl(indent, "calculateMaintree: function(name) {");
		p.pl(indent+1, "var parseRet = this.parseCalcname(name);");
		p.pl(indent+1, "var calcname = parseRet != null ? parseRet[0] : null;");
		p.pl(indent+1, "var calcindex = Nodes.getCalcIndex(calcname);");
		p.pl(indent+1, "if (calcindex>=0) {");
		p.pl(indent+2, "return Nodes.cmt(calcindex, this, parseRet[1]);");
		p.pl(indent+1, "} else {");
		p.pl(indent+2, "throw 'Invalid calculation name ' + name;");
		p.pl(indent+1, "}");		
	}
	
	private void printIsWhitespace(int indent) {
		p.pl(indent, "isWhitespace: function(cc) {");
		p.pl(indent+1, "return ((9 == cc) || (10 == cc) || (12 == cc) || (13 == cc) || (32 == cc));");		
		p.pl(indent, "},");
	}
	
	private void printParseSetvarName(int indent) {
		p.pl(indent, "parseSetvarName: function(setvarname) {");
		p.pl(indent+1, "if ((null == setvarname) || (0 == setvarname.length)) {");
		p.pl(indent+2, "return ['', null];");
		p.pl(indent+1, "}");		
		p.pl(indent+1, "var indStartBracket = setvarname.indexOf('[');");
		p.pl(indent+1, "if (indStartBracket < 0) {");		
		p.pl(indent+2, "var ret = setvarname.toUpperCase();");
		p.pl(indent+2, "return [ret, null];");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var name = setvarname.substring(0, indStartBracket).toUpperCase();");
		p.pl(indent+1, "var tail = setvarname.substring(indStartBracket+1,setvarname.length);");
		p.pl(indent+1, "var indices = [];");
		p.pl(indent+1, "var lastNotZero = -1;");
		p.pl(indent+1, "var indind = -1;");
		p.pl(indent+1, "var c;");
		p.pl(indent+1, "for (var i=0; i<tail.length; i++) {");				
		p.pl(indent+2, "if (this.isWhitespace(tail.charCodeAt(i))) {");			
		p.pl(indent+3, "continue;");
		p.pl(indent+2, "}");
		p.pl(indent+2, "var index = 0;");
		p.pl(indent+2, "indind++;");
		p.pl(indent+2, "c = tail.charAt(i);");
		p.pl(indent+2, "while(!isNaN(c)) {");		
		p.pl(indent+3, "index*=10;");
		p.pl(indent+3, "index+=+c;");
		p.pl(indent+3, "i++;");
		p.pl(indent+3, "if (this.isWhitespace(tail.charCodeAt(i))) {");
		p.pl(indent+4, "break;");
		p.pl(indent+3, "}");
		p.pl(indent+3, "c = tail.charAt(i);");
		p.pl(indent+2, "}");		
		p.pl(indent+2, "if (index > 0) {");
		p.pl(indent+3, "lastNotZero = indind;");
		p.pl(indent+2, "}");
		p.pl(indent+3, "indices[indind] = index;");		
		p.pl(indent+2, "if (c != ';' && c != ',') {");
		p.pl(indent+3, "break;");
		p.pl(indent+2, "}");		
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (c != ']') {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (lastNotZero<0) {");
		p.pl(indent+1, "return [name,null];");
		p.pl(indent+1, "}");
		// p.pl(indent+1, "var ret = ind.slice(lastNotZero+1);");
		p.pl(indent+1, "return [name,indices];");
		p.pl(indent, "},");
//		out.println("      if (c != ']') {");
//		out.println("         return null;");
//		out.println("      }");
//		out.println("      c = i.next();");
//		out.println("      while (c==' ' || c=='\\t' || c=='\\f' || c=='\\r' || c=='\\n') {");
//		out.println("         c = i.next();");
//		out.println("      }");
//		out.println("      if (c != StringCharacterIterator.DONE) {");
//		out.println("         return null;");
//		out.println("      }");
//		out.println("      if (lastNotZero<0) {");
//		out.println("         return new Object[] { name, null };");
//		out.println("      }");
//		out.println("      int[] ret = Arrays.copyOf(ind, lastNotZero+1);");
//		out.println("      return new Object[] { name, ret };");
//		out.println("   }");
	}
	
	private void printArrayCopy(int indent) {
		// TODO: Exception Handling return null oder throw Exception?
		
		p.pl(indent, "arraycopy: function(src, srcStart, dst, dstStart, length) {");	
		p.pl(indent+1, "for (var i=0; i<length; i++) {");
		p.pl(indent+2, "dst[dstStart+i] = src[srcStart+i];");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return dst;");
		p.pl(indent, "},");
	}
	
	private void printBuildKey(int indent) {
		p.pl(indent, "buildKey: function(id, indices) {");
		p.pl(indent+1, "var str = (typeof indices === 'object') ? tc.i.helper.getIndexNorm(indices).join() : '';");		
		p.pl(indent+1, "return id + ',' + str;");
		p.pl(indent, "},");
	}
	
	private void printParseInteger(int indent) {
		p.pl(indent+0, "parseInteger: function(calcname, c, i) {");
		p.pl(indent+1, "if (c<0x30 /* 0 */ || c>0x39 /* 9 */) {");
		p.pl(indent+2, "throw \"invalid argument: Integer expected at position \" + i;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "while (c>=0x30 && c<=0x39) {");
		p.pl(indent+2, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return i;");
		p.pl(indent+0, "},");
	}
	
	private void printParseExponent(int indent) {
		p.pl(indent+0, "parseExponent: function(calcname, c, i) {");
		p.pl(indent+1, "// EXPONENT => e[-]?[0-9]+");
		p.pl(indent+1, "if (c!=0x65 /* 'e' */ && c!=0x45 /* 'E' */) {");
		p.pl(indent+2, "throw \"invalid argument: exponent expected at position \" + i;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "if (c==0x2D /* '-' */) {");
		p.pl(indent+2, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return this.parseInteger(calcname, c, i);");
		p.pl(indent+0, "},");
	}
	
	private void printIntList(int indent) {
		p.pl(indent, "intList: function(ikey, vals, len) {");		
		p.pl(indent+1, "var key;");
		p.pl(indent+1, "var vals;");
		p.pl(indent+1, "var keylen;");		
		p.pl(indent+1, "if (len<=0) {");
		p.pl(indent+2, "this.key = ikey;");
		p.pl(indent+2, "return;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "this.key = ikey << 32 + vals[0];");
		p.pl(indent+1, "if (len>1) {");
		p.pl(indent+2, "this.keylen = len-1;");
		p.pl(indent+2, "var v = new Array(this.keylen);");
		p.pl(indent+2, NS_STATUS + ".arraycopy(vals, 1, v, 0, this.keylen);");
		p.pl(indent+2, "this.vals = v;");
		p.pl(indent+1, "}");			
		p.pl(indent, "},");
//		out.println();
//		out.println("   private static class IntList {");
//		out.println("      long key;");
//		out.println("      int[] vals;");
//		out.println("      int keylen;");
//		out.println("      IntList(int ikey, int[] vals, int len) {");
//		out.println("         if (len<=0) {");
//		out.println("            this.key = ikey;");
//		out.println("            return;");
//		out.println("         }");
//		out.println("         this.key = (long) ikey << 32 + vals[0];");
//		out.println("         if (len>1) {");
//		out.println("            keylen = len-1;");
//		out.println("            int[] v = new int[keylen];");
//		out.println("            System.arraycopy(vals, 1, v, 0, keylen);");
//		out.println("            this.vals = v;");
//		out.println("         }");
//		out.println("      }");
//		out.println();
	}
	
	private void printParseCalcname(int indent) {
		p.pl(indent+0, "parseCalcname: function(calcname) {");
		p.pl(indent+1, "var c;");
		p.pl(indent+1, "var arg=null;");
		p.pl(indent+1, "var i = 0;");
		p.pl(indent+1, "var ptype; // 0: no parenthesis; 1:function (); 2:table/input []");
		p.pl(indent+1, "var token;");
		p.pl(indent+1, "var arg = new Array();");
		p.pl(indent+1, "");
		p.pl(indent+1, "/* skip leading whitespaces */");
		p.pl(indent+1, "c = calcname.charCodeAt(0);");
		p.pl(indent+1, "while (c==0x09 || c==0x0A ||c==0x0C ||  c==0x0D || c==0x20) {");
		p.pl(indent+2, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var inddot=-1;");
		p.pl(indent+1, "var tokenStartind = i;");
		p.pl(indent+1, "while (c && c != 0x28 /* ( */ && c != 0x5B /* [ */ && c!=0x09 && c!=0x0A && c!=0x0C && c!=0x0D && c!=0x20) {");
		p.pl(indent+2, "if (c==0x2E /* '.' */) {");
		p.pl(indent+3, "inddot=i;");
		p.pl(indent+2, "}");
		p.pl(indent+2, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var name = calcname.substring(tokenStartind, i).toUpperCase();");
		p.pl(indent+1, "");
		p.pl(indent+1, "/* eat whitespaces before '(' or '[' */");
		p.pl(indent+1, "while (c==0x09 || c==0x0A ||c==0x0C ||  c==0x0D || c==0x20) {");
		p.pl(indent+2, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "");
		p.pl(indent+1, "/* eat ( or [ */");
		p.pl(indent+1, "if (c == 0x28 /* '(' */) {");
		p.pl(indent+2, "ptype = 1; // function with parameters");
		p.pl(indent+2, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "} else if (c == 0x5B /* '[' */) { // table access or input");
		p.pl(indent+2, "ptype = 2;");
		p.pl(indent+2, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "} else {");
		p.pl(indent+2, "ptype = 0; // no arguments");
		p.pl(indent+1, "}");
		p.pl(indent+1, "while (c==0x09 || c==0x0A ||c==0x0C ||  c==0x0D || c==0x20) {");
		p.pl(indent+2, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (ptype==0 && c) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var first=true;");
		p.pl(indent+1, "/* get arguments */");
		p.pl(indent+1, "while (c) {");
		p.pl(indent+2, "/* are at beginning of next argument */");
		p.pl(indent+2, "while (c==0x09 || c==0x0A ||c==0x0C ||  c==0x0D || c==0x20) {");
		p.pl(indent+3, "c = calcname.charCodeAt(++i);");
		p.pl(indent+2, "}");
		p.pl(indent+2, "if ((ptype==1 && c==0x29 /* ')' */) || (ptype==2 && c==0x5D /* ']' */)) {");
		p.pl(indent+3, "break;");
		p.pl(indent+2, "}");
		p.pl(indent+2, "if (!first && (c==0x2C /* ',' */ || c==0x3B /* ';' */)) {");
		p.pl(indent+3, "c = calcname.charCodeAt(++i); /* eat , or ; */");
		p.pl(indent+3, "while (c==0x09 || c==0x0A ||c==0x0C ||  c==0x0D || c==0x20) {");
		p.pl(indent+4, "c = calcname.charCodeAt(++i);");
		p.pl(indent+3, "}");
		p.pl(indent+2, "}");
		p.pl(indent+2, "");
		p.pl(indent+2, "first=false;");
		p.pl(indent+2, "if (c == 0x22 /* '\"' */) { /* start of string argument */");
		p.pl(indent+3, "c = calcname.charCodeAt(++i); /* skip \" */");
		p.pl(indent+3, "tokenStartind = i;");
		p.pl(indent+3, "while (c != 0x22 /* '\"' */ && c) {");
		p.pl(indent+4, "c = calcname.charCodeAt(++i);");
		p.pl(indent+3, "}");
		p.pl(indent+3, "if (!c) {");
		p.pl(indent+4, "return null;");
		p.pl(indent+3, "}");
		p.pl(indent+3, "/* string may be ended with more than one \" -> those are added to the argument */");
		p.pl(indent+3, "while (c == 0x22 /* '\"' */) {");
		p.pl(indent+4, "c = calcname.charCodeAt(++i);");
		p.pl(indent+3, "}");
		p.pl(indent+3, "arg.push(calcname.substring(tokenStartind, i-1));");
		p.pl(indent+2, "} else {");
		p.pl(indent+3, "tokenStartind = i;");
		p.pl(indent+3, "//  -?{INTEGER}");
		p.pl(indent+3, "//  -?{INTEGER}[.,]?{EXPONENT}");
		p.pl(indent+3, "//  -?{INTEGER}[.,]{INTEGER}{EXPONENT}?");
		p.pl(indent+3, "//  -?[.,]{INTEGER}{EXPONENT}?     ohne - mit .: gefährlicher Fall");
		p.pl(indent+3, "if (c==0x2D /* '-' */) {");
		p.pl(indent+4, "//gotMinus=true;");
		p.pl(indent+4, "c = calcname.charCodeAt(++i);");
		p.pl(indent+3, "}");
		p.pl(indent+3, "if (c==0x2C /* ',' */ || c==0x2E /* '.' */) {");
		p.pl(indent+4, "//  [.,]{INTEGER}{EXPONENT}?");
		p.pl(indent+4, "c = calcname.charCodeAt(++i);");
		p.pl(indent+4, "i = this.parseInteger(calcname, c, i); c = calcname.charCodeAt(i);");
		p.pl(indent+4, "if(c==0x65 /* 'e' */ || c==0x45 /* 'E' */) {");
		p.pl(indent+5, "i = this.parseExponent(calcname, c, i); c = calcname.charCodeAt(i);");
		p.pl(indent+4, "}");
		p.pl(indent+4, "arg.push(calcname.substring(tokenStartind, i));");
		p.pl(indent+3, "} else {");
		p.pl(indent+4, "//  {INTEGER}");
		p.pl(indent+4, "//  {INTEGER}[.,]?{EXPONENT}");
		p.pl(indent+4, "//  {INTEGER}[.,]{INTEGER}{EXPONENT}?");
		p.pl(indent+4, "var done = false;");
		p.pl(indent+4, "i = this.parseInteger(calcname, c, i); c = calcname.charCodeAt(i);");
		p.pl(indent+4, "if (c==0x2E /* '.' */ || c==0x2C /* ',' */) {");
		p.pl(indent+5, "// special Case: {INTEGER} gefolgt von ',' als Parameter-Trennzeichen");
		p.pl(indent+5, "//  {INTEGER}[.,]{EXPONENT}");
		p.pl(indent+5, "//  {INTEGER}[.,]{INTEGER}{EXPONENT}?");
		p.pl(indent+5, "if (c==0x2C /* ',' */) {");
		p.pl(indent+6, "c = calcname.charCodeAt(++i);");
		p.pl(indent+6, "if (c<=0x30 /* 0 */ && c>= 0x39 /* 9 */ && c!=0x65 /* 'e' */ && c!=0x45 /* 'E' */) {");
		p.pl(indent+7, "done = true;");
		p.pl(indent+7, "arg.push(calcname.substring(tokenStartind, i));");
		p.pl(indent+6, "}");
		p.pl(indent+6, "c = calcname.charCodeAt(--i);");
		p.pl(indent+5, "}");
		p.pl(indent+5, "if (!done) {");
		p.pl(indent+6, "c = calcname.charCodeAt(++i);");
		p.pl(indent+6, "if (c==0x65 /* 'e' */ || c==0x45 /* 'E' */) {");
		p.pl(indent+7, "i = this.parseExponent(calcname, c, i); c = calcname.charCodeAt(i);");
		p.pl(indent+7, "arg.push(calcname.substring(tokenStartind, i));");
		p.pl(indent+6, "} else if (c>=0x30 /* 0*/ && c<=0x39 /*9*/) {");
		p.pl(indent+7, "i = this.parseInteger(calcname, c, i); c = calcname.charCodeAt(i);");
		p.pl(indent+7, "if (c==0x65 /* 'e' */ || c==0x45 /* 'E' */) {");
		p.pl(indent+8, "i = this.parseExponent(calcname, c, i); c = calcname.charCodeAt(i);");
		p.pl(indent+7, "}");
		p.pl(indent+7, "arg.push(calcname.substring(tokenStartind, i));");
		p.pl(indent+6, "} else {");
		p.pl(indent+7, "return null;");
		p.pl(indent+6, "}");
		p.pl(indent+5, "}");
		p.pl(indent+4, "} else if (c==0x65 /* 'e' */ || c==0x45 /* 'E' */){");
		p.pl(indent+5, "//  {INTEGER}{EXPONENT}");
		p.pl(indent+5, "i = this.parseExponent(calcname, c, i); c = calcname.charCodeAt(i);");
		p.pl(indent+5, "arg.push(calcname.substring(tokenStartind, i));");
		p.pl(indent+4, "} else {");
		p.pl(indent+5, "//  {INTEGER}");
		p.pl(indent+5, "arg.push(calcname.substring(tokenStartind, i));");
		p.pl(indent+4, "}");
		p.pl(indent+3, "}");
		p.pl(indent+2, "} // end of parsing one argument");
		p.pl(indent+2, "");
		p.pl(indent+2, "while (c==0x09 || c==0x0A ||c==0x0C ||  c==0x0D || c==0x20) {");
		p.pl(indent+3, "c = calcname.charCodeAt(++i);");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (ptype==1 || ptype==2) {");
		p.pl(indent+2, "if (ptype==1 && c!=0x29 /*')'*/) {");
		p.pl(indent+3, "throw \"')' missing\";");
		p.pl(indent+2, "}");
		p.pl(indent+2, "if (ptype==2 && c!=0x5D /* ']' */) {");
		p.pl(indent+3, "throw \"']' missing\";");
		p.pl(indent+2, "}");
		p.pl(indent+2, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "while (c==0x09 || c==0x0A ||c==0x0C ||  c==0x0D || c==0x20) {");
		p.pl(indent+2, "c = calcname.charCodeAt(++i);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var subcalcname;");
		p.pl(indent+1, "if (c==0x2E /* '.' */) {");
		p.pl(indent+2, "subcalcname = calcname.substring(i+1).toUpperCase();");
		p.pl(indent+2, "c = undefined;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (c) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (arg==undefined) {");
		p.pl(indent+2, "arg = new Array(0);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (inddot>=0 && ptype==0) {");
		p.pl(indent+2, "subcalcname = name.substring(inddot+1);");
		p.pl(indent+2, "name = name.substring(0, inddot);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return {");
		p.pl(indent+2, "nameParams: name+':'+arg.length,");
		p.pl(indent+2, "arg: arg,");
		p.pl(indent+2, "name: name,");
		p.pl(indent+2, "subcalcname: subcalcname,");
		p.pl(indent+2, "ptype: ptype,");
		p.pl(indent+2, "toString: function() {");
		p.pl(indent+3, "return this.nameParams + \", arg: [\" + arg + \"], subcalcname:\" + this.subcalcname + \", ptype:\" + this.ptype;");
		p.pl(indent+2, "}");
		p.pl(indent+1, "};");
		p.pl(indent+0, "},");
	}
	
	// TODO: Implement caching
	private void printCacheMethods(int indent) { 
		p.pl(indent, "initCache: function() {");
		p.pl(indent, "},");
//		out.println("   private void initCache() {");
//		out.println("      cache = new LruCache<Object,V>(100000, 0.5f);");
//		out.println("      cacheEmpty = true;");
//		out.println("   }");
//		out.println();
		
		p.pl(indent, "cacheEmpty: false,");
//		out.println("   private boolean cacheEmpty;");
//		out.println("   private LruCache<Object,V> cache;");
//		out.println("   {");
//		out.println("      initCache();");
//		out.println("   }");
//
		p.pl(indent, "getCacheKey: function(id) {");
		p.pl(indent+1, "return 0;");
		p.pl(indent, "},");
//		out.println();
//		out.println("   @Override");
//		out.println("   public Object getCacheKey(final int id, final V...args) {");
//		out.println("      return new CacheKey(id, args, timesSize==0 ? null : Arrays.copyOf(timesStack, timesSize));");
//		out.println("   }");
//		out.println();
		
		p.pl(indent, "readCache: function(key) {");
		p.pl(indent+1, "return undefined;");
		p.pl(indent, "},");
//		out.println("   @Override");
//		out.println("   public V readCache(final Object key) {");
//		out.println("      return cache.get(key);");
//		out.println("   }");
//		out.println();
		
		p.pl(indent, "writeCache: function(key, value) {");
		p.pl(indent+1, "// todo: implement");
		p.pl(indent, "},");
//		out.println("   @Override");
//		out.println("   public void writeCache(final Object key, final V value) {");
//		out.println("      cache.put(key, value);");
//		out.println("      cacheEmpty = false;");
//		out.println("   }");
//		out.println();
	}
	
	private void printCalculateInput(int indent) {
		p.pl(indent, "calculateInput: function(name) {");
		p.pl(indent+1, "var parsedname = this.parseSetvarName(name);");
		p.pl(indent+1, "if (parsedname == null) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");		
		p.pl(indent+1, "var indices = (null == parsedname[1]) ? [] : parsedname[1];");		
		p.pl(indent+1, "return " + JSInput.NS_INPUT + "[parsedname[0]].$$value(this, indices);");
		p.pl(indent, "},");		
	}
	
	private void printCalculateFunction(int indent) {
		p.pl(indent+0, "calculateFunction: function(name) {");
		p.pl(indent+1, "var parseRet = this.parseCalcname(name);");
		p.pl(indent+1, "var args = parseRet.arg;");
		p.pl(indent+1, "args.unshift(this);");
		p.pl(indent+1, "return " + JSFunctions.NS_FUNCTIONS + "[parseRet.name].apply(" + JSFunctions.NS_FUNCTIONS + "[parseRet.name], args);");
		p.pl(indent+0, "},");
	}
	
	private void printCalculateInputCalc(int indent) {
		p.pl(indent+0, "calculateInputCalc: function(name, calcname) {");
		p.pl(indent+1, "var p = this.parseSetvarName(name);");
		p.pl(indent+1, "if (p == null) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var justnameUpper = p[0];");
		p.pl(indent+1, "var indices = p[1];");
		p.pl(indent+1, "// var inputid = this.getInputIndex(justnameUpper);");
		p.pl(indent+1, "if (indices == null) {");
		p.pl(indent+2, "indices = [];");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var inputcalcid = this.getInputCalcIndex(calcname.toUpperCase());");
		p.pl(indent+1, "if (inputcalcid<0) {");
		p.pl(indent+2, "throw 'Invalid calcname ' + calcname;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return tc.i[justnameUpper]['$'+calcname](indices);");
		p.pl(indent+0, "},");
	}
	
	private void printCalculateTable(int indent) {
		p.pl(indent+0, "calculateTable: function(name) {");
		p.pl(indent+1, "var p = this.parseCalcname(name);");
		p.pl(indent+1, "var args = p[1];");
		p.pl(indent+1, "var tabname = p[2];");
		p.pl(indent+1, "var colname = p[3];");
		p.pl(indent+1, "var ret;");
		p.pl(indent+1, "if (colname == null) {");
		p.pl(indent+2, "ret = tc.t[tabname].findExact(args);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "else {");
		p.pl(indent+2, "args.unshift(colname);");
		p.pl(indent+2, "ret = tc.t[tabname].findExactColumn(args);");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return ret;");
		p.pl(indent+0, "},");
	}
	
	private void printCalculate(int indent) {
		p.pl(indent+0, "calculate: function(name) {");
		p.pl(indent+1, "var p = this.parseCalcname(name);");
		p.pl(indent+1, "");
		p.pl(indent+1, "var inputIndex = this.getInputIndex(p.name);");
		p.pl(indent+1, "switch(p.ptype) {");
		p.pl(indent+1, "case 0: {");
		p.pl(indent+2, "// calc without parameters or input or function without parameters");
		p.pl(indent+2, "if (undefined == p.subcalcname) {");
		p.pl(indent+3, "/* calc? */");
		p.pl(indent+3, "// TODO  Nodes.getCalcIndex(nameWithParamcounter);");
		p.pl(indent+6, "");
		p.pl(indent+3, "if (inputIndex >= 0) {");
		p.pl(indent+4, "return tc.i[p.name].$$value(this, p.arg);");
		p.pl(indent+3, "}");
		p.pl(indent+3, "if (tc.f.helper.fn.indexOf(p.name) !== -1) {");
		p.pl(indent+4, "return tc.f[p.name](this, p.arg);");
		p.pl(indent+3, "}");
		p.pl(indent+2, "}");
		p.pl(indent+2, "else {");
		p.pl(indent+3, "/* input-calc */");
		p.pl(indent+3, "if (inputIndex < 0) {");
		p.pl(indent+4, "break;");
		p.pl(indent+3, "}");
		p.pl(indent+3, "var inputcalcId = this.getInputCalcIndex(p.subcalcname);");
		p.pl(indent+3, "if (inputcalcId < 0 ) {");
		p.pl(indent+4, "break;");
		p.pl(indent+3, "}");
		p.pl(indent+3, "return tc.i[p.name]['$'+subcalcname.toLowerCase()](this, arg);");
		p.pl(indent+2, "}");
		p.pl(indent+2, "break;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "case 1: {");
		p.pl(indent+2, "//calc or function");
		p.pl(indent+2, "if (undefined !== p.subcalcname) {");
		p.pl(indent+3, "break;");
		p.pl(indent+2, "}");
		p.pl(indent+2, "// TODO: final int calcindex = Nodes.getCalcIndex(nameWithParamcounter);");
		p.pl(indent+2, "");
		p.pl(indent+2, "if (tc.f.helper.fn.indexOf(p.name) !== -1) {");
		p.pl(indent+3, "var args = p.arg.slice();");
		p.pl(indent+3, "args.unshift(this);");
		p.pl(indent+3, "return tc.f[p.name].apply(tc.f, args) + '';");
		p.pl(indent+2, "}");
		p.pl(indent+2, "break;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "case 2: {");
		p.pl(indent+2, "//input or table");
		p.pl(indent+2, "if (undefined === p.subcalcname) {");
		p.pl(indent+3, "if (inputIndex >= 0) {");
		p.pl(indent+4, "return tc.i[p.name].$$value(this, arg);");
		p.pl(indent+3, "}");		
		p.pl(indent+3, "if (tc.t.functions.tn.indexOf(p.name) !== -1) {");
		p.pl(indent+4, "return tc.t.tables[p.name].findExact(p.arg);");
		p.pl(indent+3, "}");
		p.pl(indent+3, "break;");
		p.pl(indent+2, "}");
		p.pl(indent+2, "else {");
		p.pl(indent+3, "if (inputIndex >= 0) {");
		p.pl(indent+4, "var inputcalcId = this.getInputCalcIndex(p.subcalcname);");
		p.pl(indent+4, "if (inputcalcId < 0) {");
		p.pl(indent+5, "break;");
		p.pl(indent+4, "}");
		p.pl(indent+4, "return tc.i[p.name]['$' + p.subcalcname.toLowerCase()](this, arg);");
		p.pl(indent+3, "}");		
		p.pl(indent+3, "if (tc.t.functions.tn.indexOf(p.name) !== -1) {");
		p.pl(indent+4, "return tc.t.tables[p.name].findExactColumn(arg.unshift(p.subcalcname));");
		p.pl(indent+3, "}");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "");
		p.pl(indent+1, "throw 'Invalid calculation string \\'' + name + '\\'';");
		p.pl(indent+0, "},");
//		out.println("   @Override");
//		out.println("   public String calculate(String name) {");
//		out.println("       Object[] parseRet = parseCalcname(name);");
//		out.println("       String nameWithParamcounter = (String) parseRet[0];");
//		out.println("       V[] args = (V[]) parseRet[1];");
//		out.println("       String nameUpper = (String) parseRet[2];");
//		out.println("       String calcnameUppercase = (String) parseRet[3];");
//		out.println("       int type = (Integer) parseRet[4];");
//		out.println("       switch (type) {");
//		out.println("       case 0: { //calc without parameters or input or function without parameters");
//		out.println("          if (calcnameUppercase==null) {");
//		out.println("             /* calc? */");
//		out.println("             final int calcindex = Nodes.getCalcIndex(nameWithParamcounter);");
//		out.println("             if (calcindex>=0) {");
//		out.println("                return Nodes.cmt(calcindex, this, args).stringValue();");
//		out.println("             }");
//		out.println("             /* input? */");
//		out.println("             final int inputindex = getInputIndex(nameUpper);");
//		out.println("             if (inputindex>=0) {");
//		out.println("                return ((V)I.dynamicCall(inputindex, -2, this, args)).stringValue();");
//		out.println("             }");
//		out.println("             /* function? */");
//		out.println("             final int funcindex = F.getFuncIndex(nameUpper);");
//		out.println("             if (funcindex>=0) {");
//		out.println("                return F.dynamicCall(V.getInstanceFuncref(funcindex), this, args).stringValue();");
//		out.println("             }");
//		out.println("          } else {");
//		out.println("             /* input-calc */");
//		out.println("             final int inputindex = getInputIndex(nameUpper);");
//		out.println("             if (inputindex<0) {");
//		out.println("                break;");
//		out.println("             }");
//		out.println("             final int inputcalcid = getInputCalcIndex(calcnameUppercase);");
//		out.println("             if (inputcalcid<0) {");
//		out.println("                break;");
//		out.println("             }");
//		out.println("             return ((V) I.dynamicCall(inputindex, inputcalcid, this, args)).stringValue();");
//		out.println("          }");
//		out.println("          break;");
//		out.println("       }");
//		out.println("       case 1: { //calc or function");
//		out.println("          /* calc? */");
//		out.println("          if (calcnameUppercase!=null) {");
//		out.println("             break;");
//		out.println("          }");
//		out.println("          final int calcindex = Nodes.getCalcIndex(nameWithParamcounter);");
//		out.println("          if (calcindex>=0) {");
//		out.println("             return Nodes.cmt(calcindex, this, args).stringValue();");
//		out.println("          }");
//		out.println("          /* function? */");
//		out.println("          final int funcindex = F.getFuncIndex(nameUpper);");
//		out.println("          if (funcindex>=0) {");
//		out.println("             return F.dynamicCall(V.getInstanceFuncref(funcindex), this, args).stringValue();");
//		out.println("          }");
//		out.println("          break;");
//		out.println("       }");
//		out.println("       case 2: { //input or table");
//		out.println("          if (calcnameUppercase==null) {");
//		out.println("             final int inputindex = getInputIndex(nameUpper);");
//		out.println("             if (inputindex>=0) {");
//		out.println("                return ((V)I.dynamicCall(inputindex, -2, this, args)).stringValue();");
//		out.println("             }");
//		out.println("             final int tabindex = Tables.getTableid(nameUpper);");
//		out.println("             if (tabindex>=0) {");
//		out.println("                return ((V) Tables.dynamicCall(");
//		out.println("                      V.getInstanceTabref(tabindex), ");
//		out.println("                      Tables.CALL_EXACT, ");
//		out.println("                      (Object[]) args");
//		out.println("                      )).stringValue();");
//		out.println("             }");
//		out.println("             break;");
//		out.println("          } else {");
//		out.println("             final int inputindex = getInputIndex(nameUpper);");
//		out.println("             if (inputindex>=0) {");
//		out.println("                final int inputcalcid = getInputCalcIndex(calcnameUppercase);");
//		out.println("                if (inputcalcid<0) {");
//		out.println("                   break;");
//		out.println("                }");
//		out.println("                return ((V) I.dynamicCall(inputindex, inputcalcid, this, args)).stringValue();");
//		out.println("             }");
//		out.println("             final int tabindex = Tables.getTableid(nameUpper);");
//		out.println("             if (tabindex>=0) {");
//		out.println("                int lenvargs = args.length;");
//		out.println("                V[] dynargs = new V[lenvargs+1];");
//		out.println("                dynargs[0] = V.getInstance(calcnameUppercase);");
//		out.println("                System.arraycopy(args, 0, dynargs, 1, lenvargs);");
//		out.println("                return ((V) Tables.dynamicCall(");
//		out.println("                      V.getInstanceTabref(tabindex), ");
//		out.println("                      Tables.CALL_EXACT_COL,");
//		out.println("                      (Object[]) dynargs");
//		out.println("                      )).stringValue();");
//		out.println("             }");
//		out.println("          }");
//		out.println("       } //end of case 2");
//		out.println("       } //end of switch");
//		out.println("       throw new RuntimeException(\"invalid calculation string '\" + name + \"'\");");
//		out.println("   }");
	}
	
	private void printNeeded(int indent) {
		p.pl(indent, "needed: function(inputname, calcname) {");
		p.pl(indent+1, "var parsedname = this.parseSetVarName(inputname);");
		p.pl(indent+1, "if (parsedname == null) {");
		p.pl(indent+2, "return false;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var justnameUpper = parsedname[0];");
		p.pl(indent+1, "var index = parsedname[1];");
		p.pl(indent+1, "if (index == null) {");
		p.pl(indent+2, "index = [];");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var inputid = this.getInputIndex(justnameUpper);");
		p.pl(indent+1, "if (inpudid<0) {");
		// TODO:
		// out.println("         throw new RuntimeException(\"Invalid inputname \" + inputname);");
		p.pl(indent+1, "throw 'RuntimeException'");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var indexlen = index.length;");
		p.pl(indent+1, "var vindex = new Array(indexlen);");
		p.pl(indent+1, "for (var i=0; i<indexlen; i++) {");
		p.pl(indent+2, "vindex[i] = index[i];");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var value = this.getInput(inputid, vindex);");
		p.pl(indent+1, "this.setInput(inputid, null, index); // set to undefined");
		// TODO:
		p.pl(indent, "}");
		


//		out.println("      V value = getInput(inputid, vindex);");
//		out.println("      setInput(inputid, null, index); // set to undefined");
//		out.println("      boolean needed = false;");
//		out.println("      try {");
//		out.println("         calculate(calcname);");
//		out.println("         needed = false;");
//		out.println("      } catch (ExceptionNeedMoreInput e) {");
//		out.println("         needed = true;");
//		out.println("         String inputnameNeeded = e.getInputname();");
//		out.println("         if (inputnameNeeded.equalsIgnoreCase(justnameUpper)) {");
//		out.println("            int[] indexNeeded = e.getIndex();");
//		out.println("            int neededLen = indexNeeded!=null ? indexNeeded.length : 0;");
//		out.println("            if (neededLen==indexlen) {");
//		out.println("               for (int i=0; i<indexlen; i++) {");
//		out.println("                  if (indexNeeded[i]!=index[i]) {");
//		out.println("                     needed = false;");
//		out.println("                     break;");
//		out.println("                  }");
//		out.println("               }");
//		out.println("            } else if (neededLen<indexlen) {");
//		out.println("               for (int i=0; i<neededLen; i++) {");
//		out.println("                  if (indexNeeded[i]!=index[i]) {");
//		out.println("                     needed = false;");
//		out.println("                     break;");
//		out.println("                  }");
//		out.println("               }");
//		out.println("               for (int i=neededLen; i<indexlen; i++) {");
//		out.println("                  if (index[i]!=0) {");
//		out.println("                     needed = false;");
//		out.println("                     break;");
//		out.println("                  }");
//		out.println("               }");
//		out.println("            } else if (neededLen>indexlen) {");
//		out.println("               for (int i=0; i<indexlen; i++) {");
//		out.println("                  if (indexNeeded[i]!=index[i]) {");
//		out.println("                     needed = false;");
//		out.println("                     break;");
//		out.println("                  }");
//		out.println("               }");
//		out.println("               for (int i=indexlen; i<neededLen; i++) {");
//		out.println("                  if (indexNeeded[i]!=0) {");
//		out.println("                     needed = false;");
//		out.println("                     break;");
//		out.println("                  }");
//		out.println("               }");
//		out.println("            }");
//		out.println("         }");
//		out.println("      }");
//		out.println("      setInput(inputid, value, index); // set to original value");
//		out.println("      return needed;");
//		out.println("   }");
//
	}
	
	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		JSDiv obj = new JSDiv(model, foldernameout, packagename);
		obj.run();
	}
}
