package treecalc.comp;

import java.util.HashMap;

public class PrintFormulaInfos {
	private HashMap<String, FormulaConstant> constants = new HashMap<String, FormulaConstant>();
	public void addConstant(FormulaConstant constant) {
		constants.put(constant.getNameInMethod(), constant);
	}
	public HashMap<String, FormulaConstant> getConstants() {
		return constants;
	}
}
