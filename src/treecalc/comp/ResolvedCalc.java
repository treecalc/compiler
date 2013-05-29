package treecalc.comp; 

import java.util.ArrayList;
import java.util.Arrays;

import treecalc.comp.ModelSimple.NodeData;


public class ResolvedCalc {
	public enum ResolveType {
		//		SELF,       //recursive call
		ONE_SIMPLE, //in own node or subnode, without any inclusion rules / multiple things in the way
		LIST 		//in more than one subnode
	}
	private ResolveType type;
	/**
	 * list of resolved nodes that has the calc in it.
	 * Each entry is int[] with 
	 * last element: node where the calc is defined
	 * from start to end: nodes that have inclusion criteria or multiple formula
	 */
	private int[][] subnodes  = new int[10][]; //node id's of first nodes on the way down to the calcs
	private int len=0;
	private int nodestart=-1;
	private int sumnode = -1;
	public void setScopeNodeStart(int nodestart) {
		this.nodestart = nodestart;
	}
	public int getScopeNodeStart() {
		return nodestart;
	}

	public void setType(ResolveType type) {
		this.type = type;
	}
	public ResolveType getType() {
		return type;
	}
	public void addSubnode(int subnode, ArrayList<Integer> path) {
		int arrlen = subnodes.length;
		if (len>=arrlen) {
			subnodes  = Arrays.copyOf(subnodes , (len+1)*2);
		}
		int pathlen = path.size();
		int[] pathint = new int[pathlen+1];
		for (int i=0; i<pathlen; i++) {
			pathint[i] = path.get(i);
		}
		pathint[path.size()] = subnode;
		subnodes[len]  = pathint;
		len++;
	}
	private int[][] getSubnodes() {
		if (subnodes.length!=len) {
			subnodes  = Arrays.copyOf(subnodes, len);
		}
		return subnodes;
	}
	public void inferSumNode(ModelSimple model) {
		type = ResolveType.LIST;
		sumnode = this.nodestart; //find first node that has: inclusion criteria or multiple formula OR is last common predecessor of childnodes
		for(int depth=0; true; depth++) {
			int candidate = -1;
			for (int i=0; i<len; i++) {
				int[] path = subnodes[i];
				/* if one path is longer than another, we are done */
				if (depth>=path.length) {
					if (len==1) {
						type = ResolveType.ONE_SIMPLE;
					}
					return; 
				}
				int node = path[depth];
				if (candidate<0) {
					/* that node of the first path could be a common predecessor */
					candidate = node;
				} else {
					/* it is not if it is not part of all paths */
					if (candidate!=node) {
						return;
					}
				}
			}
			/* now we have a new common predecessor */
			sumnode = candidate;

			/* if it has inclusion/times formula then we are done */
			NodeData nodedata = model.getNodeData(sumnode);
			ScopeNode scopenode = nodedata.node;
			if (scopenode.getAstInclusion()!=null || scopenode.getAstTimes()!=null) {
				return;
			}
		}
	}
	public int getSumNode() {
		return sumnode;
	}
}
