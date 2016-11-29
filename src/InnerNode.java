import java.util.HashMap;
import java.util.Map;

/**
 * Nodo interno
 * 
 *
 */
public class InnerNode extends Node{
	
	public InnerNode(int i, Integer j) {
		super(i, j);
		this.children = new HashMap<Character, Node>();
	}

	protected Map<Character, Node> children;
	
	protected Node suffixLink; //s(v)
	
	@Override
	public void addChild(Character c, Node n){
		children.put(c, n);
	}
	
    Node getNode(char c) {
        return children.get(c);
    }
    
    @Override
    public Node getSuffixLink() {
        return suffixLink;
    }
    
    @Override
    void setSuffixLink(Node n) {
        this.suffixLink = n;
    }
	

	
	

}
