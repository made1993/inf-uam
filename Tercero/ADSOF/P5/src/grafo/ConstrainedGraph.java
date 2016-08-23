package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Clase ConstrainedGraph <N, E>
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class ConstrainedGraph<N, E> extends Graph<N, E> {

	private Node<N> witness;

	/**
	 * Constructor de la clase ConstrainedGraph
	 */
	public ConstrainedGraph() {

		this.nodos = new ArrayList<Node<N>>();
		this.map = new HashMap<Node<N>, List<Edge<E>>>();
		this.witness = null;

	}

	/**
	 * Metodo para comprobar si todos los nodos cumplen una propiedad
	 * 
	 * @param pred
	 *            propiedad que tienen que cumplir los nodos
	 * @return true si cumplen la propiedad todos los nodos, flase en caso
	 *         contrario
	 */
	public boolean forAll(Predicate<Node<N>> pred) {

		this.witness = null;

		for (Node<N> n : this.nodos) {
			if (pred.test(n) == false) {
				return false;
			}
		}

		return true;

	}

	/**
	 * Metodo para comprobar si al menos un nodo cumple una propiedad
	 * 
	 * @param pred
	 *            propiedad que tiene que cumplir al menos un nodo
	 * @return true si cumple la propiedad al menos un nodo, flase en caso
	 *         contrario
	 */
	public boolean exists(Predicate<Node<N>> pred) {

		this.witness = null;

		for (Node<N> n : this.nodos) {
			if (pred.test(n) == true) {
				this.witness = n;
				return true;
			}
		}

		return false;

	}

	/**
	 * Metodo para comprobar si un unico nodo cumple una propiedad
	 * 
	 * @param pred
	 *            propiedad que tiene que cumplir un unico nodo
	 * @return true si cumple la propiedad un unico un nodo, flase en caso
	 *         contrario
	 */
	public boolean one(Predicate<Node<N>> pred) {

		this.witness = null;

		List<Node<N>> list = new ArrayList<Node<N>>();

		for (Node<N> n : this.nodos) {
			if (pred.test(n) == true) {
				list.add(n);
			}
		}

		if (list.size() == 1) {
			return true;
		}

		return false;

	}

	/**
	 * Metodo para obtener el witness de un grafo
	 * 
	 * @return nodo o null si no existe
	 */
	public Optional<Node<N>> getWitness() {
		return Optional.ofNullable(this.witness);
	}

}
