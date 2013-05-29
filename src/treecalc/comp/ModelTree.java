package treecalc.comp; 

import java.util.Arrays;

public class ModelTree {
	private String[] id;
	private ScopeNode rootnode;
	public ModelTree() {
	}
	public boolean matches(String[] searchid) {
		return Arrays.equals(id, searchid);
	}
	/**
	 * returns true if the id starts with the searchid 
	 * @param searchid
	 * @return
	 */
	public boolean startsWith(String[] searchid) {
		for (int i=0; i<searchid.length; i++) {
			String onecomp = id[i];
			if (i>=id.length){
				return false;
			}
			String search = searchid[i];
			if (!onecomp.equals(search)) {
				return false;
			}
		}
		return true;
	}

	public String[] getId() {
		return id;
	}
	public void setId(String[] id) {
		this.id = id;
	}
	public ScopeNode getRootnode() {
		return rootnode;
	}
	public void setRootnode(ScopeNode rootnode) {
		this.rootnode = rootnode;
	}
	public String getName() {
		StringBuffer s = new StringBuffer(id[0].length()+20);
		for (int i=0; i<id.length; i++) {
			if (i>0) {
				s.append('.');
			}
			s.append(id[i]);
		}
		return s.toString();
	}
	@Override
	public String toString() {
		return "tree:" + Arrays.toString(id);
	}
}
