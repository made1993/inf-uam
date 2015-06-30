package grafo;

import java.util.*;
import java.util.Map.Entry;

/**
 * Clase Graph <N, E>
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class Graph<N, E> implements Collection<Node<N>> {

	protected List<Node<N>> nodos;
	protected Map<Node<N>, List<Edge<E>>> map;

	/**
	 * Constructor de la clase Graph
	 */
	public Graph() {
		this.nodos = new ArrayList<Node<N>>();
		this.map = new HashMap<Node<N>, List<Edge<E>>>();

	}

	/**
	 * Metodo para saber si un nodo esta conectado a otro
	 * 
	 * @param n1
	 *            primer nodo a comprobar
	 * @param n2
	 *            segundo nodo a comprobar
	 * @return true si esta conectado, false en caso contrario
	 */
	public boolean conectado(Node<N> n1, Node<N> n2) {

		for (Entry<Node<N>, List<Edge<E>>> entrada : map.entrySet()) {
			for (Edge<E> e : entrada.getValue()) {
				if (e.isConnnected(n1, n2))
					return true;
			}
		}

		return false;

	}

	/**
	 * Metodo para obtener los ejes entre dos nodos
	 * 
	 * @param n1
	 *            nodo inicial
	 * @param n2
	 *            nodo destino
	 * @return lista de ejes entre los dos nodos
	 */
	public List<E> getEdges(Node<N> n1, Node<N> n2) {

		List<E> list = new ArrayList<E>();

		for (Entry<Node<N>, List<Edge<E>>> entrada : map.entrySet()) {
			if (entrada.getKey().equals(n1))
				for (Edge<E> e : entrada.getValue()) {
					if (e.getDestino().equals(n2))
						list.add(e.getInfo());
				}

		}

		return list;

	}

	/**
	 * Metodo para obtener la lista de nodos de un grafo
	 * 
	 * @return la lista de nodos de un grafo
	 */
	public List<Node<N>> getNodos() {
		return this.nodos;
	}

	/**
	 * Metodo para obtener el mapa de un grafo
	 * 
	 * @return mapa del grafo
	 */
	public Map<Node<N>, List<Edge<E>>> getMap() {
		return this.map;
	}

	/**
	 * Metodo para conectar dos nodos con un eje
	 * 
	 * @param n1
	 *            nodo inicial
	 * @param e
	 *            eje para conectar los nodos
	 * @param n2
	 *            nodo final
	 * @return true si se ha conectado correctamente, false en caso contrario
	 */
	public boolean connect(Node<N> n1, E e, Node<N> n2) {

		Edge<E> edge = new Edge<E>(e, n1, n2);

		if ((nodos.contains(n1) && nodos.contains(n2)) == false) {
			return false;
		}

		if (map.containsKey(n1)) {
			map.get(n1).add(edge);
		} else {
			List<Edge<E>> lst = new ArrayList<Edge<E>>();
			lst.add(edge);
			map.put(n1, lst);
		}

		return false;
	}

	/**
	 * Metodo para eliminar un nodo
	 * 
	 * @param n
	 *            nodo a eliminar
	 * @return true si se elimina correctamente, false en caso contrario
	 */
	public boolean remove(Node<N> n) {
		if (nodos.contains(n)) {
			nodos.remove(n);
			map.remove(n);
			return true;
		}
		return false;
	}

	/**
	 * Metodo para eliminar un nodo de todo el grafo
	 * 
	 * @param n
	 *            nodo a eliminar
	 * @return true si se elimina correctamente, false en caso contrario
	 */
	public boolean removeAll(Node<N> n) {
		if (nodos.contains(n)) {
			while (nodos.remove(n))
				;
			while (map.remove(n) != null)
				;
			return true;
		}
		return false;
	}

	/**
	 * Metodo que anade un nodo a un grafo
	 * 
	 * @param e
	 *            nodo a anadir al grafo
	 * @return true si se ha anadido correctamente, false en caso contrario
	 */
	@Override
	public boolean add(Node<N> e) {
		if (nodos.contains(e))
			return false;
		nodos.add(e);
		e.setGraph(this);
		if (map.containsKey(e))
			System.out.println(map.put(e, null));
		return true;
	}

	/**
	 * Metodo para anadir los nodos a un grafo
	 * 
	 * @param c
	 *            coleccion de nodos a anadir
	 * @return true si se han anadido correctamente, false en caso contrario
	 */
	@Override
	public boolean addAll(Collection<? extends Node<N>> c) {
		boolean ret = true;
		for (Node<N> n : c)
			ret = add(n);
		return ret;
	}

	/**
	 * Metodo para limpiar los nodos
	 */
	@Override
	public void clear() {
		this.nodos.clear();
	}

	/**
	 * Metodo para ver si el grafo contiene un objeto
	 * 
	 * @param o
	 *            objeto a comprobar
	 * @return true si lo contine, false en caso conraio
	 */
	@Override
	public boolean contains(Object o) {
		if (nodos.contains(o))
			return true;
		return false;
	}

	/**
	 * Metodo para ver si el grafo contiene los objetos
	 * 
	 * @param c
	 *            coleccion de nodos a comprobar
	 * @return true si los contiene, false en caso contrario
	 */
	@Override
	public boolean containsAll(Collection<?> c) {

		return this.nodos.containsAll(c);
	}

	/**
	 * Metodo para comprobar si la lista de nodos esta vacia
	 * 
	 * @return true si esta vacia, false en caso contrario
	 */
	@Override
	public boolean isEmpty() {
		if (nodos.isEmpty())
			return true;
		return false;
	}

	/**
	 * Metodo iterator sobre los nodos
	 * 
	 * @return iterdor de la lista de nodos
	 */
	@Override
	public Iterator<Node<N>> iterator() {
		Iterator<Node<N>> i = nodos.iterator();
		return i;
	}

	/**
	 * Metodo para eliminar un nodo
	 * 
	 * @param n
	 *            nodo a eliminar
	 * @return true si se elimina correctamente, false en caso contrario
	 */
	@Override
	public boolean remove(Object o) {

		return this.nodos.remove(o);
	}

	/**
	 * Metodo para eliminar un nodo de todo el grafo
	 * 
	 * @param n
	 *            nodo a eliminar
	 * @return true si se elimina correctamente, false en caso contrario
	 */
	@Override
	public boolean removeAll(Collection<?> c) {

		return this.nodos.removeAll(c);
	}

	/**
	 * Metodo para ver si el grafo retiene los objetos de la coleccion
	 * 
	 * @param c
	 *            coleccion de objetos a comprobar
	 * @return true si se ha comprobado correctamente, false en caso contrario
	 */
	@Override
	public boolean retainAll(Collection<?> c) {

		return this.nodos.retainAll(c);
	}

	/**
	 * Metodo para obtener el tamano de la lista de nodos
	 * 
	 * @return tamano de la lista de nodos
	 */
	@Override
	public int size() {

		return this.nodos.size();
	}

	/**
	 * Metodo para converit la lista de nodos en array
	 * 
	 * @return array de nodos convertido
	 */
	@Override
	public Object[] toArray() {

		return this.nodos.toArray();
	}

	/**
	 * Metodo para convertir en generico un array
	 * 
	 * @return array convertido
	 */
	@Override
	public <T> T[] toArray(T[] a) {

		return this.nodos.toArray(a);
	}

	/**
	 * toString de la clase Grph
	 */
	public String toString() {
		String ret = new String();
		ret += "Nodes:\n";
		for (Node<N> n : nodos) {
			ret += n.toString() + "\n";
		}
		ret += "\nEdges:\n";
		for (Entry<Node<N>, List<Edge<E>>> e : map.entrySet()) {
			for (Edge<E> edge : e.getValue()) {
				ret += edge.toString() + "\n";
			}
		}
		return ret;
	}
}
