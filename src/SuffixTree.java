import java.util.Collection;

/**
 * Suffix tree compuesto de un conjunto de nodos y  
 */
public class SuffixTree {
	
    public static void main(String args[]){
        SuffixTree st = SuffixTreeFactory.build("mississippi");

        int x = 0;
        
    }
	
	
	private Node root;

	private String source;
	
	
	private Node lastCreatedLeaf; //Guardamos ultimo nodo agregado
	
	public Node getRoot(){
		return root;
	}
	
	public String getSource(){
		return source;
	}
	
	
	/**
	 * Construye el suffix tree a partir del string s
	 */
	public SuffixTree(String s){
		
		this.source = s;
		//Construct I1
		this.root = new RootNode();
		
	}	

	/**
     * Encuentra todas las ocurrencias de la palabra buscada en el arbol
     */
    public Collection<Integer> search(String word) {
        return null;
    }
    
    
}
