package treecalc.comp;

import treecalc.rt.V;

public class FormulaConstant {
	private final String nameInMethod;
	private final V constant;
	private final String createstatement;
	public FormulaConstant(String nameInMethod, V constant, String createstatement) {
		this.nameInMethod = nameInMethod;
		this.constant = constant;
		this.createstatement = createstatement;
	}
	public String getNameInMethod() {
		return nameInMethod;
	}
	public V getConstant() {
		return constant;
	}
	public String getCreatestatement() {
		return createstatement;
	}
}
