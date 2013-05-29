package treecalc.comp; 

public class GenNodeCalc {
	private final String calcname;
	private final int nargs;

	public GenNodeCalc(String calcname, int nargs) {
		this.calcname = calcname.toUpperCase();
		this.nargs = nargs;
	}
	@Override
	public String toString() {
		return calcname + ":" + nargs;
	}
	
	public String getCalcname() {
		return calcname;
	}
	
	public int getNumArgs() {
		return nargs;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((calcname == null) ? 0 : calcname.hashCode());
		result = prime * result + nargs;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass())
			return false;
		GenNodeCalc other = (GenNodeCalc) obj;
		if (nargs != other.nargs) {
			return false;
		}
		if (calcname == null) {
			if (other.calcname != null) {
				return false;
			}
		} else if (!calcname.equalsIgnoreCase(other.calcname)) {
			return false;
		}
		return true;
	}
}
