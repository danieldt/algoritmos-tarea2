import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Nodo interno
 * 
 *
 */
public class InnerNode extends Node{
	
	private int last_character_index;
	
	private List<Integer> leafLabels;
	
	public InnerNode(int i, Integer j) {
		super(i, j);
		this.children = new HashMap<Character, Node>();
		this.leafLabels = new ArrayList<Integer>();
	}
	
	public void addLeafLabel(int i){
		leafLabels.add(i);
	}
	
	public void setLeafLabel(List<Integer> list){
		this.leafLabels = list;
	}
	
	public List<Integer> getLeavesLabels(){
		return leafLabels;
	}
	
	protected Map<Character, Node> children;
	
	protected Node suffixLink; //s(v)
	
	@Override
	public void addChild(Character c, Node n){
		children.put(c, n);
	}
	
	@Override
	public Node getChild(char c) {
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
    @Override
    public Collection<Node> getChildren(){
    	return children.values();
    }

	@Override
	public void setEnd(int j) {
		last_character_index = j;
	}

	@Override
	public int end() {
		return last_character_index;
	}
	

	
	

}
