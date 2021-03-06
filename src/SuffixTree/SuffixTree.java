package SuffixTree;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Suffix tree compuesto de un conjunto de nodos y  
 */
public class SuffixTree {
	static int[] tiempos;

	
	private RootNode root;
	private String source;
	
	/**
	 * Construye el suffix tree a partir del string s
	 */
	public SuffixTree(String s){
		
		this.source = s;
		//Construct I1
		this.root = new RootNode();
		tiempos=new int[s.length()];
		
	}

	public Node getRoot(){
		return root;
	}
	
	public String getSource(){
		return source;
	}
	
	
	/**
	 * Retorna el indice de la primera ocurrencia encontrada
	 */
	public int searchFirst(String word) {
		Node currentNode = root;
		
		//Por cada caracter de la palabra
		for (int i = 0; i < word.length(); ++i) {
			char ch = word.charAt(i);
			//Buscamos si hay un arco que comience con el caracter
			Node node = currentNode.getChild(ch);
			//Caso en que no se encuentra la palabra en el texto
			if(node == null){
				return -1;
			}
			//Ahora comparamos los caracteres con el del arco
			int charsToMatch = Math.min(word.length() - i, node.length()+1);
			for(int j = 1; j < charsToMatch; j++){
				//Caso en que no hay camino para seguir
				if(word.charAt(i+j) != source.charAt(node.start()+j)){
					return -1;
				}
			}
			//No hubo missmatchs, continuamos
			currentNode = node;
			i += charsToMatch - 1; //-1 porque hay un i++ en el for		
			
		}
		//No hubieron missmatchs, por lo tanto la palabra esta contenida
		//Ahora cada hoja que salga de este nodo representa un indice
		return root.getChild(word.charAt(0)).start();
	}

	/**
     * Encuentra todas las ocurrencias de la palabra buscada en el arbol, retornando los indices en donde aparece
     * 
     */
    public SearchResult searchAll(String word) {
    	long ti = System.currentTimeMillis();
    	Node currentNode = root;
    	List<Integer> result = new ArrayList<Integer>();
    	
    	//Por cada caracter de la palabra
    	for (int i = 0; i < word.length(); ++i) {
    		char ch = word.charAt(i);
    		//Buscamos si hay un arco que comience con el caracter
    		Node node = currentNode.getChild(ch);
    		//Caso en que no se encuentra la palabra en el texto
    		if(node == null){
    			return null;
    		}
    		//Ahora comparamos los caracteres con el del arco
    		int charsToMatch = Math.min(word.length() - i, node.length()+1);
    		for(int j = 1; j < charsToMatch; j++){
    			//Caso en que no hay camino para seguir
    			if(word.charAt(i+j) != source.charAt(node.start()+j)){
    				return null;
    			}
    		}
    		//No hubo missmatchs, continuamos
    		currentNode = node;
    		i += charsToMatch - 1; //-1 porque hay un i++ en el for		
    		
    	}
    	//No hubieron missmatchs, por lo tanto la palabra esta contenida
    	//Ahora cada hoja que salga de este nodo representa un indice
    	
    	List<Node> leaves = new ArrayList<Node>();
    	findLeafs(currentNode, leaves);
    	for (Node l : leaves){
    		result.add(l.getLabel());
    	}
    	Collections.sort(result);
    	
    	long tf = System.currentTimeMillis();
    	long tiempo=tf-ti;
    	return new SearchResult(result,tiempo);
    }
    
	public static int[] getTiempos() {
		return tiempos;
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

	private void findLeafs(Node node, List<Node> result) {
		//Si es hoja agregamos al resultado
		if(node.isLeaf()){
			result.add(node);
			return;
		}
		for(Node n : node.getChildren()){
			findLeafs(n, result);
		}
		
		
	}

	private void dfs(Node node, List<String> result, String path) {
		if (node == null)
			return;
		// Retornamos el path desde el root hasta la hoja
		if (node.isLeaf()){
			result.add(path + source.substring(node.start(), node.end()+1) + " " + node.getLabel() );
			return;
		}
		//Else estamos en un nodo interno
		for (Node n : node.getChildren()){
			dfs(n, result, path + source.substring(node.start(), node.end()+1));
		}
	}


	
    
}