import java.util.Collection;

/**
 * SuffixTree element
 */
public abstract class Node {

	// Indices que representan el rango de caracteres del arco que se 
	// uso para llegar a el
	protected int first_char_index;
	
	public Node(int i, Integer j){
		setStart(i);
		setEnd(j);
	}
	
	public void addChild(Character c, Node n){}
	
	public int start(){
		return first_char_index;
	}
	
	public void setStart(int i){
		first_char_index = i;
	}
	
	public abstract void setEnd(int j);
	
	public abstract int end();
	
	public boolean isRoot(){
		return false;
	}
	
	public boolean isLeaf(){
		return false;
	}
	
	public Node getChild(char c){
		return null;
	}
	
    public Collection<Node> getChildren(){
    	return null;
    }
	
	public Node getSuffixLink(){
		return null;
	}
	
    void setSuffixLink(Node n) {}
    
	
	public int length(){
		return this.end() - this.start();// + 1;
	}
	
	
	
}
