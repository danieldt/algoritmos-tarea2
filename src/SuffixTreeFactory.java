import java.util.HashMap;
import java.util.Map;

public class SuffixTreeFactory {
	
	
	private static SuffixTree ST;
	private static String input;
	
	// Active Point
	private static Node activeNode; // Nodo de donde el punto activo comienza
	private static int activeEdge; // Llave del arco seleccionado del nodo activo
	private static int activeLength; // En que punto estamos del arco seleccionado
	
	// TODO al parecer Integer se copia por valor y no referencia
	private static EndIndex end; // Ultimo indice leido, final global para las hojas
	private static int remainingSuffixCount; // Cantidad de sufijos que se tienen que crear (+1 por cada fase, -1 cuando creamos una nueva hoja)
	
	private static int labelCount; //Para nombrar las hojas
	
	public static int total_cost_count; //contador de costo total realizado
	public static Map<Integer, Integer> phaseCost; //guarda costo de cada fase
	
	public static SuffixTree build(String in){
		total_cost_count = 0;
		phaseCost = new HashMap<Integer, Integer>();
		
		input = in;
		ST = new SuffixTree(input);
		
		// Init variables
		activeNode = ST.getRoot();
		activeLength = 0;
		activeEdge = -1;
		labelCount = 0;
				
		end = new EndIndex(-1);
		remainingSuffixCount = 0;
		
		// Por cada caracter del input creamos iterativamente el suffix tree implicito
		for(int i = 0; i < input.length(); i++){
			
			long tic = System.nanoTime();
			addPrefix(i);				
			long tfc = System.nanoTime();
	    	ST.tiempos[i]=(int)(tfc-tic);
			
		}
		return ST;		
	}

	/**
	 * Para cada prefijo se parte en el punto activo (Active Point) y agrega el caracter usando las reglas de extension:
	 * Regla 1: Si S[j...i] termina en una hoja, agregamos S[i+1] al final de la etiqueta que lleva a ese arco
	 * Regla 2: Si S[j...i] termina en medio de un arco y el caracter que sigue no es S[i+1] entonces creamos una nueva hoja con etiqueda S[i+1]
	 * Regla 3: Si S[j...i] termina en medio de un arco y el siguiente caracter es S[i+1] entonces no hacemos nada (puesto que el arbol es implicito)
	 * 
	 * @param i
	 */
	private static void addPrefix(int i) {
		
		char current_char = input.charAt(i);

		InnerNode lastCreatedInnerNode = null;
		
		end.plusPlus();
		remainingSuffixCount++;
		
		int phase_cost_count = 0;
		while(remainingSuffixCount > 0){
			//El costo de una fase es cuantas veces se hace el while
			total_cost_count++;
			phase_cost_count++;
			// Caso en que comenzamos a buscar desde la raiz
			if(activeLength == 0){
				//Regla #3, avanzamos 1 en direccion del arco
				if(selectNode(i) != null){
					activeEdge = selectNode(i).start();
					activeLength++;
					return;
				}
				// Creamos una nueva hoja en la raiz
				else {
					ST.getRoot().addChild(current_char, new LeafNode(i, end, labelCount));
					labelCount++;
					remainingSuffixCount--;
				}
			}
			// Active lenght != 0 -> Estamos en medio de un camino
			else {
				char next_char = nextChar(i);
				
				// Si es que existe el siguente caracter en el camino
				if(next_char != 0){
					// Caso de extension # 3 
					if(current_char == next_char){
						
						//Caso en que habiamos creado un nodo anteriormente
						if (lastCreatedInnerNode != null){
							lastCreatedInnerNode.setSuffixLink(selectNode());
						}
						//Avanzamos a la siguiente posicion
						Node node = selectNode();
						//Caso en que tengamos que cambiar de nodo
						if(node.length() < activeLength){
							activeNode = node;
							activeLength -= node.length();
							activeEdge = node.getChild(input.charAt(i)).start();
									
						}
						//Continuamos una posicion en este nodo
						else { 
							activeLength++;
						}
						break;
					}
					// Siguiente caracter no se encuentra en el camino, extendemoss usando # 2
					else{
						Node node = selectNode();
						
						// Creamos nuevo nodo interno, desde el comienzo hasta el quiebre
						InnerNode newInnerNode = new InnerNode(node.start(), node.start() + activeLength - 1);
						// Creamos nueva hoja
						LeafNode newLeafNode = new LeafNode(i, end, labelCount);
						labelCount++;
						
						// Actualizamos el indice del primer caracter del nodo seleccionado
						node.setStart(node.start() + activeLength);
						
						// Actualizamos hijos
						newInnerNode.addChild(current_char, newLeafNode);
						newInnerNode.addChild(input.charAt(node.start()), node);
						// Reemplazamos el nodo viejo por el nuevo en el nodo activo
						activeNode.addChild(input.charAt(newInnerNode.start()), newInnerNode); 
						
						// Si se habia creado un nuevo nodo interno en esta fase entonces lo linkeamos con el creado recientemente
						if (lastCreatedInnerNode != null){
							lastCreatedInnerNode.setSuffixLink(newInnerNode);
						}
						
						// Actualizamos el nuevo nodo creado y lo linkeamos a la raiz
						lastCreatedInnerNode = newInnerNode;
						newInnerNode.setSuffixLink(ST.getRoot());
						
						// Seguimos el link si es que no estamos en root
						if(!activeNode.isRoot()){
							activeNode = activeNode.getSuffixLink();
						}
						// Si estamos en la raiz actualizamos 
						else{
							activeEdge++;
							activeLength--;
						}
						remainingSuffixCount--;
					}
					
				}
				// Caso en que estamos al final del arco y hay que crear una nueva hoja que le siga
				else {
					
					Node node = selectNode();
					LeafNode newLeafNode = new LeafNode(i, end, labelCount);
					labelCount++;
					node.addChild(current_char, newLeafNode);
					// El nodo seleccionado queda como un nuevo nodo interno
					if (lastCreatedInnerNode != null){
						lastCreatedInnerNode.setSuffixLink(node);
					}
					lastCreatedInnerNode = (InnerNode) node;
					
					// Seguimos el link si es que no estamos en root
					if(!activeNode.isRoot()){
						activeNode = activeNode.getSuffixLink();
					}
					// Si estamos en la raiz actualizamos 
					else{
						activeEdge++;
						activeLength--;
					}
					remainingSuffixCount--;
				}
			}
		//endwhile	
		}
		if(phase_cost_count > 1){
			phaseCost.put(i, phase_cost_count);
		}
	}
	
	/**
	 * Encuentra el siguiente caracter para comparar con el de la fase actual, usando la tecnica skip/count 	
	 * 
	 * @param i
	 * @return
	 */
	private static char nextChar(int i) {
		
		Node node = selectNode();
		
		// Si el caracter esta dentro del arco
		if (node.length() >= activeLength){
			return input.charAt(node.start() + activeLength);
		}
		// necesitamos el caracter justo despues del final
		if(node.length() + 1 == activeLength){
			
			// Caso en que no se pueda seguir
			if(node.getChild(input.charAt(i)) == null){
				return 0;
			}
			//else -> existe el mismo character en algun arco que le sigue
			return input.charAt(i);
		}
		// else -> continuamos busqueda en el siguiente nodo, usando skip/count
		activeNode = node;
		activeLength = activeLength - node.length() - 1;
		activeEdge = activeEdge + node.length() + 1;
		return nextChar(i);
		
	}

	/**
	 * Selecciona el hijo del nodo activo que lleva el arco que empieza con el caracter en i
	 * 
	 * @param i
	 * @return
	 */
	private static Node selectNode(int i){
		return activeNode.getChild(input.charAt(i));
	}
	
	/**
	 * Selecciona el hijo del nodo activo que que lleva desde el arco seleccionado 
	 * @return
	 */
	private static Node selectNode(){
		return selectNode(activeEdge);
	}
	
	
		
	
	

}

