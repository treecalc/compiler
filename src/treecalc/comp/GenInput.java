package treecalc.comp; 

import java.util.ArrayList;
import java.util.HashMap;

import static treecalc.comp.TcSimpleParser.*;
/**
 * Class that is filled up and prepared before generating code
 * @author Stefan
 *
 */
public class GenInput {
	public static class GenInputCalc {
		private String calcname;
		private TcAst  calcast;
		GenInputCalc(String calcname, TcAst calcast) {
			this.calcname = calcname;
			this.calcast  = calcast;
		}
		public String getCalcname() {
			return calcname;
		}
		public TcAst getCalcast() {
			return calcast;
		}
	}
	
	private final int index;
	private final String name;
	private final TcAst ast;
	private HashMap<String,Integer> calcSet = new HashMap<String,Integer>();
	private ArrayList<GenInputCalc> calcList = new ArrayList<GenInputCalc>();
	
	public GenInput(String name, int index, TcAst ast) {
		this.name = name;
		this.index = index;
		this.ast = ast;
	}
	public int getIndex() {
		return index;
	}
	public String getName() {
		return name;
	}
	public TcAst getAst() {
		return ast;
	}
	public void addCalc(String calcname, TcAst calcast) {
		String cname = calcname.toUpperCase();
		int    ind = calcList.size(); 
		calcSet.put(cname, ind);
		calcList.add(new GenInputCalc(calcname, calcast));
	}
	public ArrayList<GenInputCalc> getCalcs() {
		return calcList;
	}
	public int getCalcsSize() {
		return calcList.size();
	}
	public GenInputCalc getCalc(int index) {
		return calcList.get(index);
	}
	public GenInputCalc getCalc(String calcname) {
		Integer index = calcSet.get(calcname.toUpperCase());
		if (index==null) {
			return null;
		}
		return getCalc(index);
	}
	public boolean hasCheck() {
		return calcSet.containsKey("CHECK");
	}
	public boolean hasDefault() {
		return calcSet.containsKey("DEFAULT");
	}
	public boolean hasVector() {
		return calcSet.containsKey("VECTOR");
	}
	public boolean hasTable() {
		return calcSet.containsKey("TABLE");
	}
	public boolean hasReference() {
		return calcSet.containsKey("REFERENCE");
	}
	public boolean hasFilter() {
		return calcSet.containsKey("FILTER");
	}
	public boolean hasDisplay() {
		return calcSet.containsKey("DISPLAY");
	}
	public boolean hasDisplaytext() {
		return calcSet.containsKey("DISPLAYTEXT");
	}
	public boolean hasAutocounter() {
		return calcSet.containsKey("AUTOCOUNTER");
	}
	public boolean isChoiceable() {
		return hasVector() || hasTable() || hasReference();
	}
	
	private ArrayList<String> autocounter = new ArrayList<String>();
	public void addAutocounter(String counter) {
		autocounter.add(counter.toUpperCase());
	}
	public ArrayList<String> getAutocounters() {
		return autocounter;
	}
}
