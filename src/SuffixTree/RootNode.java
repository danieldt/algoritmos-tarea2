package SuffixTree;

public class RootNode extends InnerNode {

	

	public RootNode() {
		super(0, 0);
	}

	@Override
	public boolean isRoot(){
		return true;
	}

	@Override
	public int length() {
		return 0;
	}
}
