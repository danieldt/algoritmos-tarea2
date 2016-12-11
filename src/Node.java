import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * SuffixTree element
 */
public abstract class Node {

	// Indices que representan el rango de caracteres del arco que se 
	// uso para llegar a el
	protected int first_char_index;
	//Lista de indices que este nodo hace referencia
	protected List<Integer> indexes;
	
	public Node(int i, Integer j){
		setStart(i);
		setEnd(j);
		indexes = new ArrayList<Integer>();
		indexes.add(i);
	}
	
	protected void addIndex(int i){
		indexes.add(i);
		
		//Agregamos referencia a todos los sufijos tambien
		Node node = this.getSuffixLink();
		if(!node.isRoot()){
			node.addIndex(i);
		}
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
	
	public int getLabel(){
		return -1;
	}
	
	
	
	
}
