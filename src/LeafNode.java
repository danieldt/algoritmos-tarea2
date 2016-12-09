

/**
 * Borde del arbol
 */
public class LeafNode extends Node {
	
	EndIndex end;
	
	public LeafNode(int i, Integer j) {
		super(i,j);
	}

	public LeafNode(int i, EndIndex j) {
		super(i, j.getEnd());
		this.first_char_index = i;
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
	
}
