

/**
 * Borde del arbol
 */
public class LeafNode extends Node {
	
	EndIndex end;
	
	public LeafNode(int i, Integer j) {
		super(i,j);
	}

	public LeafNode(int i, EndIndex j) {
		super(i, 0);
		this.end = j;
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
	
}
