package grafo;

import java.util.*;

/**
 * Clase Node<N>
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class Node<N> {

	private static int numNodes = 0;
	private int id;
	private N info;
	private Graph<N, ?> graph = null;

	/**
	 * Constructor de la clase Node
	 * 
	 * @param i
	 *            info a asignarle al nodo
	 */
	public Node(N i) {
		this.info = i;
		this.id = Node.numNodes++;
	}

	/**
	 * Metodo para obtener el id de un nodo
	 * 
	 * @return el id del nodo
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metodo para asigar un id a un nodo
	 * 
	 * @param id
	 *            a asignar al nodo
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Metodo para obtener el valor del nodo
	 * 
	 * @return el valor del nodo
	 */
	public N getInfo() {
		return info;
	}

	/**
	 * Metodo para asignar un valor a un nodo
	 * 
	 * @param info
	 *            valor a asignar al nodo
	 */
	public void setInfo(N info) {
		this.info = info;
	}

	/**
	 * Metodo para obtener el grafo al cual pertenece el nodo
	 * 
	 * @return el grafo al cual pertenece el nodo
	 */
	public Graph<N, ?> getGraph() {
		return graph;
	}

	/**
	 * Metodo para asignar un grafo a un nodo
	 * 
	 * @param graph
	 *            a asignar al nodo
	 */
	public void setGraph(Graph<N, ?> graph) {
		this.graph = graph;
	}

	/**
	 * Metodo para comprobar si el nodo esta conectado a otro
	 * 
	 * @param n
	 *            info del nodo con el que se quiere comprobar
	 * @return true si esta conectado, flase en caso contrario
	 */
	public boolean isConnectedTo(N n) {

		for (Node<N> nodo : graph.getNodos()) {
			if (nodo.info.equals(n)) {
				return graph.conectado(this, nodo);
			}
		}
		return false;

	}

	/**
	 * Metodo para comprobar si el nodo esta conectado a otro
	 * 
	 * @param n
	 *            nodo con el que se quiere comprobar
	 * @return true si esta conectado, flase en caso contrario
	 */
	public boolean isConnectedTo(Node<N> n) {

		return graph.conectado(this, n);
	}

	/**
	 * Metodo para obtener los vecinos de un nodo
	 * 
	 * @return lista de nodos vecinos
	 */
	public List<Node<N>> neighbours() {

		List<Node<N>> list = new ArrayList<Node<N>>();

		for (Node<N> nodo : graph.getNodos()) {
			if (this.isConnectedTo(nodo)) {
				list.add(nodo);
			}
		}

		return list;
	}

	/**
	 * Metodo para obtener los ejes de un nodo
	 * 
	 * @param n
	 *            nodo del cual se quieren obtener los ejes
	 * @return lista con los ejes
	 */
	public List<?> getEdgeValues(Node<N> n) {

		return graph.getEdges(this, n);

	}

	/**
	 * toString de la clase Node
	 */
	public String toString() {
		return id + " [" + info.toString() + "]";
	}
}
