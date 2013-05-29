package treecalc.comp; 

import java.util.ArrayList;

/**
 * Class that is filled up and prepared before generating code
 * @author Stefan
 *
 */
public class GenFunction {
	private final int index;
	private final String name;
	private ArrayList<String>argnames = new ArrayList<String>();
	private final TcAst ast;

	public GenFunction(String name, int index, TcAst ast) {
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
	public void addArgname(String argname) {
		argnames.add(argname.toUpperCase());
	}
	public ArrayList<String> getArgnames() {
		return argnames;
	}
	public int getArgSize() {
		return argnames.size();
	}
	public String getArgname(int index) {
		return argnames.get(index);
	}
	public TcAst getAst() {
		return ast;
	}
}
