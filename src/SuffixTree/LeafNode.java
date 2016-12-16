package SuffixTree;
import Experiment.EndIndex;

/**
 * Borde del arbol
 */
public class LeafNode extends Node {
	
	EndIndex end;
	
	int label;
	
	public LeafNode(int i, Integer j) {
		super(i,j);
	}

	public LeafNode(int i, EndIndex j, int l) {
		super(i, 0);
		this.end = j;
		this.label = l;
	}
	
	@Override
	public int end() {
		return end.getEnd();
	}	
	
	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public void setEnd(int j) {}
	
	@Override
	public int getLabel(){
		return label;
	}
	
}
