
public class SuffixTreeFactory {
	
	
	private static SuffixTree ST;
	private static String input;
	
	// Active Point
	private static Node activeNode; // Nodo de donde el punto activo comienza
	private static int activeEdge; // Llave del arco seleccionado del nodo activo
	private static int activeLength; // En que punto estamos del arco seleccionado
	
	// TODO al parecer Integer se copia por valor y no referencia
	private static Integer end; // Ultimo indice leido, final global para las hojas
	private static int remainingSuffixCount; // Cantidad de sufijos que se tienen que crear
	
	public static SuffixTree build(String in){
		
		input = in;
		ST = new SuffixTree(input);
		
		// Init variables
		activeNode = ST.getRoot();
		activeLength = 0;
		activeEdge = -1;
				
		end = new Integer(-1);
		remainingSuffixCount = 0;
		
		// Por cada caracter del input creamos iterativamente el suffix tree implicito
		for(int i = 0; i < input.length(); i++){
			addPrefix(i);
			
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
		
		end++;
		remainingSuffixCount++;
		while(remainingSuffixCount > 0){
			
			// Caso en que comenzamos a buscar desde la raiz
			if(activeLength == 0){
				
				if(selectNode(i) != null){
					activeEdge = selectNode(i).start();
					activeLength++;
					break;
				}
				// Creamos una nueva hoja en la raiz
				else {
					ST.getRoot().addChild(current_char, new LeafNode(i, end));
					remainingSuffixCount--;
				}
			}
			// Active lenght != 0 -> Estamos en medio de un camino
			else {
				char next_char = nextChar(i);
				
				// Si es que existe el siguente caracter en el camino
				if(next_char != 0){
					// Caso de extension # 3, 
					if(current_char == next_char){
						// TODO check code
						break;
					}
					// Siguiente caracter no se encuentra en el camino, extendemoss usando # 2
					else{
						Node node = selectNode();
						
						// Creamos nuevo nodo interno, desde el comienzo hasta el quiebre
						InnerNode newInnerNode = new InnerNode(node.start(), node.start() + activeLength - 1);
						// Creamos nueva hoja
						LeafNode newLeafNode = new LeafNode(i, end);
						
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
					LeafNode newLeafNode = new LeafNode(i, end);
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
	 * Selecciona el siguiente nodo a partir del nodo activo
	 * 
	 * @param i
	 * @return
	 */
	private static Node selectNode(int i){
		return activeNode.getChild(input.charAt(i));
	}
	
	private static Node selectNode(){
		return selectNode(activeEdge);
	}
	
	
		
	
	

}
