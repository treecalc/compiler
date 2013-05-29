package treecalc.comp; 

import java.util.Arrays;
import java.util.Comparator;

/**
 * Class that is filled up and prepared before generating code
 * @author Stefan
 *
 */
public class GenTable {
	private final int index;
	private final String name;
	private final TcAst ast;
	private String[] colnames;
	private String[][] data;
	/**
	 * if a value can be converted to a Byte / Short / Integer / Float / Double 
	 * and back to a string by toString(), that object is used.
	 * Otherwise, the String object from data[][] is used.
	 */
	private Object[][] dataObjects;
	/**
	 * true for a column if all values are numeric and can be converted to/from double without loosing the format 
	 */
	private boolean[] colnumericsuper; 
	/**
	 * true if the first column is numeric and has continuous numbers in step 1
	 */
	private boolean directAccess=false;
	/**
	 * if directAccess is true, this is the number of the first row
	 */
	private int directAccessOffset;
	
	/**
	 * length equals the number of columns
	 * true if all rows of the column can be converted to numbers
	 */
	private boolean[] colnumeric;
	/**
	 * length equals the number of columns
	 * true if for each block of unique keys "left" of the current column,
	 * all values can be converted to numbers and there is at least on duplicate,
	 * e.g.:
	 * e1) a 1    e2) a 1
	 *     a 2        a 1.0
	 *     b 1        b 1
	 *     b 2        b 2
	 * e1 => colnumericunique[0]=false, because it's not numeric
	 * e1 => colnumericunique[1]=true, because for block a values 1 and 2 are non-overlapping, same for b.
	 * e2 => colnumericunique[1]=false, because for block a, values 1 and 1.0 are numerically equivalent
	 */
	private boolean[] colnumericunique;
	private boolean shuffled = false;
	private int[] rowindori; // original row indizes
	private int numrows;
	private int numcols;
	public GenTable(String name, int index, TcAst ast) {
		this.name = name;
		this.index = index;
		this.ast = ast;
	}
	public int getIndex() {
		return index;
	}
	public TcAst getAst() {
		return ast;
	}
	public int getNumrows() {
		return numrows; 
	}
	public int getNumcols() {
		return numcols;
	}
	public void addRow(Object[] valuesRow) {
	}
	public void setColnames(String[] colnames) {
		this.colnames = colnames;
		this.numcols = colnames.length;
	}
	public String getName() {
		return name;
	}
	public String[] getColnames() {
		return colnames;
	}
	public String[][] getData() {
		return data;
	}
	public Object[][] getDataObjects() {
		return dataObjects;
	}
	public void setData(String[][] data) {
		this.data = data;
		this.numrows = data!=null ? data.length : 0;
		for (int i=0; i<this.numrows; i++) {
			String[] row = data[i];
			assert row.length==numcols;
		}
	}
	public boolean[] getColnumeric() {
		return colnumeric;
	}
	public boolean[] getColnumericsuper() {
		return colnumericsuper;
	}
	public boolean[] getColnumericunique() {
		return colnumericunique;
	}
	
	/**
	 * sorts the data and fills up informational fields
	 * You have to call that function before seriously using it for generation stuff
	 */
	public void sortAndFill() {
		fillColnumeric();
		sort();
		fillColnumericdup();
		fillDataObjects();
		fillOffset();
	}
	
	private void fillOffset() {
		this.directAccess=false;
		this.directAccessOffset = 0;
		
		if (numcols==0) {
			return;
		}
		if (!colnumeric[0] || !colnumericunique[0]) {
			return;
		}
		if (numrows==0) {
			return;
		}
		int ifirst=0;
		int i0=0;
		for (int indrow=0; indrow<numrows; indrow++) {
			String v1 = data[indrow][0];
			double d1 = Double.parseDouble(v1);
			int i1 = (int) d1;
			if (d1 - (double) i1 != 0) {
				return;
			}
			if (indrow>0) {
				if (i1 != i0+1) {
					return;
				}
			} else {
				ifirst = i1;
			}
			i0 = i1;
		}
		this.directAccess = true;
		this.directAccessOffset = ifirst;
	}
	private Object stringToObject(String value) {
		double d;
		try { 
			d = Double.parseDouble(value);
			try {
				byte b = Byte.parseByte(value);
				if (Byte.toString(b).equals(value)) {
					return b;
				}
			} catch (NumberFormatException e) {};
			try {
				short s = Short.parseShort(value);
				if (Short.toString(s).equals(value)) {
					return s;
				}
			} catch (NumberFormatException e) {};
			try { 
				int i = Integer.parseInt(value);
				if (Integer.toString(i).equals(value)) {
					return i;
				}
			} catch (NumberFormatException e) {};
			try { 
				float f = Float.parseFloat(value);
				if (Float.toString(f).equals(value)) {
					return f;
				}
			} catch (NumberFormatException e) {};
			if (Double.toString(d).equals(value)) {
				return d;
			}
		} catch (NumberFormatException e) {
		}
		return value;
	}
	
	private void fillDataObjects() {
		dataObjects = new Object[numrows][];
		for (int indrow=0; indrow<numrows; indrow++) {
			String[] row = data[indrow];
			Object[] rowObjects = new Object[numcols];
			dataObjects[indrow] = rowObjects;
			for (int indcol=0; indcol<numcols; indcol++) {
				String value = row[indcol];
				Object o = stringToObject(value);
				rowObjects[indcol] = o;
			}
		}
	}
	
	/**
	 * fill up colnumericdup: true if the column is numeric AND if at least two values exist with
	 * - same numerical value
	 * - different string representation
	 * Precondition: data is already sorted (-> by calling sort())
	 */
	private void fillColnumericdup() {
		colnumericunique = new boolean[numcols];
		for (int indcol=0; indcol<numcols; indcol++) {
			boolean allnumeric = colnumeric[indcol];
			boolean dup=true;
			if (allnumeric) {
				/* if numeric: search through all rows of the column.
				 * if the values of two successive values are numericalle the same, but 
				 * their string values are different, then we have a dangerous duplicate we have to take care of
				 */
				dup = false;
				if (numrows>0) {
					String[] row0=null;
					String s0=null;
					double d0=0;
					for (int indrow=0; indrow<numrows; indrow++) {
						String[] row1 = data[indrow];
						String s1 = row1[indcol];
						double d1 = Double.parseDouble(s1);
						boolean skip = false;
						if (indrow==0) {
							skip = true;
						} else {
							/* check if "group" has changed -> if so, don't compare because
							 * just duplicates within a group are of interest
							 */
							for (int j=0; j<indcol; j++) {
								if (!row1[j].equalsIgnoreCase(row0[j])) {
									skip = true;
									break;
								}
							}
						}
						if (!skip) {
							if (d0==d1 && s0.compareToIgnoreCase(s1)!=0) {
								dup = true; 
								break;
							}
						}
						row0 = row1;
						s0 = s1;
						d0 = d1;
					}
				}
			}
			colnumericunique[indcol] = !dup;
		}
	}
	/**
	 * fill up colnumeric: true if all rows of a column can be converted to a double value
	 */
	private void fillColnumeric() {
		colnumeric = new boolean[numcols];
		colnumericsuper = new boolean[numcols];
		for (int indcol=0; indcol<numcols; indcol++) {
			boolean allnumeric=true;
			boolean allnumericsuper=true;
			for (int indrow=0; indrow<numrows; indrow++) {
				String value = data[indrow][indcol];
				try {
					Double.parseDouble(value);
					String dvalue = stringToObject(value).toString();
					if (!value.equals(dvalue)) {
						allnumericsuper = false;
					}
				} catch (NumberFormatException e) {
					allnumeric = false;
					allnumericsuper = false;
					break;
				}
				
			}
			colnumeric[indcol] = allnumeric;
			colnumericsuper[indcol] = allnumericsuper;
		}
	}
	
	/**
	 * sort data and fill up reordered and rowindori
	 */
	static class SortDataRow {
		String[] row;    //data of row
		int      indori; //original index
	}
	
	private void sort() {
		/* copy data into new array with original indizes, so we still have that information after the sort */
		SortDataRow[] data4sort = new SortDataRow[numrows];
		for (int indrow=0; indrow<numrows; indrow++) {
			SortDataRow sortrow = new SortDataRow();
			sortrow.row = data[indrow];
			sortrow.indori = indrow;
			data4sort[indrow] = sortrow;
		}
		Arrays.sort(data4sort, new Comparator<SortDataRow>() {
			@Override
			public int compare(SortDataRow so1, SortDataRow so2) {
				String[] o1 = so1.row;
				String[] o2 = so2.row;
				for (int indcol=0; indcol<numcols; indcol++) {
					String s1 = o1[indcol];
					String s2 = o2[indcol];
					if (colnumeric[indcol]) {
						double val1 = Double.parseDouble(s1);
						double val2 = Double.parseDouble(s2);
						int comp = Double.compare(val1,  val2);
						if (comp!=0) {
							return comp;
						}
					} else {
						int comp = s1.compareToIgnoreCase(s2);
						if (comp!=0) {
							return comp;
						}
					}
				}
				return 0;
			}
		});
		/* fill up data[] with sorted data and rowindori */
		rowindori = new int[numrows];
		for (int indrow=0; indrow<numrows; indrow++) {
			SortDataRow x = data4sort[indrow];
			data[indrow] = x.row;
			int oldind = x.indori;
			rowindori[oldind] = indrow;
			if (oldind!=indrow) {
				shuffled = true;
			}
		}
	}
	public boolean isShuffled() {
		return shuffled;
	}
	public boolean isDirectAccess() {
		return directAccess;
	}
	public int getDirectAccessOffset() {
		return directAccessOffset;
	}
	/**
	 * @return original ordering of the data rows
	 */
	public int[] getRowindori() {
		return rowindori;
	}

	public int getColindex(final String name) {
		final String nameup = name.toUpperCase();
		for (int i=0; i<numcols; i++) {
			if (nameup.equals(colnames[i])) {
				return i;
			}
		}
		return -1;
	}

}
