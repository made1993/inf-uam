package grafo;

/**
 * Clase Edge <E>
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class Edge<E> {
	private static int numEdges = 0;
	private int id;
	private E info;
	private Graph<?, E> graph = null;
	private Node<?> origen;
	private Node<?> destino;

	/**
	 * Constructor de la clase Edge
	 * 
	 * @param i
	 *            valor que cuesta recorrer el eje
	 */
	public Edge(E i) {
		this.info = i;
		this.id = Edge.numEdges++;
	}

	/**
	 * Contructor de la clase Edge
	 * 
	 * @param i
	 *            valor que cuesta recorrer el eje
	 * @param n1
	 *            nodo inicial del eje
	 * @param n2
	 *            nodo final del eje
	 */
	public Edge(E i, Node<?> n1, Node<?> n2) {
		this.info = i;
		this.id = Edge.numEdges++;
		this.origen = n1;
		this.destino = n2;
	}

	/**
	 * Metodo para obtener el numero de ejes
	 * 
	 * @return el numero de ejes
	 */
	public static int getNumEdges() {
		return numEdges;
	}

	/**
	 * Metodo para asignar un numero de ejes
	 * 
	 * @param numEdges
	 *            numero de ejes a asignar
	 */
	public static void setNumEdges(int numEdges) {
		Edge.numEdges = numEdges;
	}

	/**
	 * Metodo para obtener el id de un eje
	 * 
	 * @return el id del eje
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo para asigar un id a un eje
	 * 
	 * @param id
	 *            a asignar al eje
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo para obtener el valor del eje
	 * 
	 * @return el valor del eje
	 */
	public E getInfo() {
		return info;
	}

	/**
	 * Metodo para asignar un valor a un eje
	 * 
	 * @param info
	 *            valor a asignar al eje
	 */
	public void setInfo(E info) {
		this.info = info;
	}

	/**
	 * Metodo para obtener el grafo al cual pertenece el eje
	 * 
	 * @return el grafo al cual pertenece el eje
	 */
	public Graph<?, E> getGraph() {
		return graph;
	}

	/**
	 * Metodo para asignar un grafo a un eje
	 * 
	 * @param graph
	 *            a asignar al eje
	 */
	public void setGraph(Graph<?, E> graph) {
		this.graph = graph;
	}

	/**
	 * Metodo para obtener el nodo origen del eje
	 * 
	 * @return el nodo origen del eje
	 */
	public Node<?> getOrigen() {
		return origen;
	}

	/**
	 * Metodo para asignar un nodo origen a un eje
	 * 
	 * @param origen
	 *            nodo origen a asignar
	 */
	public void setOrigen(Node<?> origen) {
		this.origen = origen;
	}

	/**
	 * Metodo para obtener el nodo destino del eje
	 * 
	 * @return el nodo destino del eje
	 */
	public Node<?> getDestino() {
		return destino;
	}

	/**
	 * Metodo para asignar un nodo destino a un eje
	 * 
	 * @param origen
	 *            nodo destino a asignar
	 */
	public void setDestino(Node<?> destino) {
		this.destino = destino;
	}

	/**
	 * Metodo para saber si un nodo esta conectado a otro
	 * 
	 * @param o
	 *            nodo origen a comprobar
	 * @param d
	 *            nodo destino a comprobar
	 * @return true si esta conectado, false en caso contrario
	 */
	public boolean isConnnected(Node<?> o, Node<?> d) {
		if (o.equals(origen) && d.equals(destino))
			return true;
		return false;
	}

	/**
	 * toString de la clase Edge
	 */
	public String toString() {
		return "(" + origen.getId() + " --" + info.toString() + "--> "
				+ destino.getId() + ")";
	}
}
