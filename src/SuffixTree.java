import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Suffix tree compuesto de un conjunto de nodos y  
 */
public class SuffixTree {
	
    public static void main(String args[]){
        SuffixTree st = SuffixTreeFactory.build("mississipi$");
        
        List<String> paths = st.getPaths();
        for (String p : paths){
        	System.out.println(p);
        }
    }
	
	
	private RootNode root;

	private String source;
	
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
	//TODO
    public Collection<Integer> search(String word) {
        return null;
    }
    
    
    /**
     * Retorna todos los paths del root hasta cada hoja
     * @return
     */
    public List<String> getPaths(){
    	List<String> result = new ArrayList<String>();
    	
    	Collection<Node> children = root.getChildren();
    	for (Node n : children){
    		dfs(n, result, "");
    	}
    	return result;
    }

	private void dfs(Node node, List<String> result, String path) {
		if (node == null)
			return;
		// Retornamos el path desde el root hasta la hoja
		if (node.isLeaf()){
			result.add(path + source.substring(node.start(), node.end()+1) );
			return;
		}
		//Else estamos en un nodo interno
    	for (Node n : node.getChildren()){
    		dfs(n, result, path + source.substring(node.start(), node.end()+1));
    	}
	}
    
    
}
