package grafo;

import java.util.*;
import java.util.function.Predicate;

/**
 * Clase BlackBoxComparator<N, E>
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class BlackBoxComparator<N, E> implements
		Comparator<ConstrainedGraph<N, E>> {

	/**
	 * Enumerado Criteria para comparar las propiedades de Grafos
	 */
	public enum Criteria {
		Existential {
			@Override
			public <N, E> boolean eval(ConstrainedGraph<N, E> g,
					Predicate<Node<N>> p) {
				return g.exists(p);
			}
		},
		Unitary {
			@Override
			public <N, E> boolean eval(ConstrainedGraph<N, E> g,
					Predicate<Node<N>> p) {
				return g.one(p);
			}
		},
		Universal {
			@Override
			public <N, E> boolean eval(ConstrainedGraph<N, E> g,
					Predicate<Node<N>> p) {
				return g.forAll(p);
			}
		};

		public abstract <N, E> boolean eval(ConstrainedGraph<N, E> g,
				Predicate<Node<N>> p);
	}

	private Map<Criteria, List<Predicate<Node<N>>>> criteria = new EnumMap<Criteria, List<Predicate<Node<N>>>>(
			Criteria.class);

	/**
	 * Metodo para comparar dos ConstrainedGraph
	 * 
	 * @param o1
	 *            primer grrafo a comparar
	 * @param o2
	 *            segundo grafo a comparar
	 * @return 1 si o1 cumple mas propiedades que o2, -1 si o1 cumple menos
	 *         propiedades que o2 y 0 si o1 y o2 cumplen las mismas propiedades
	 */
	@Override
	public int compare(ConstrainedGraph<N, E> o1, ConstrainedGraph<N, E> o2) {

		int contador_o1 = 0, contador_o2 = 0;

		for (Criteria c : this.criteria.keySet()) {
			for (Predicate<Node<N>> p : this.criteria.get(c)) {
				if (c.eval(o1, p)) {
					contador_o1++;
				}
				if (c.eval(o2, p)) {
					contador_o2++;
				}
			}
		}

		if (contador_o1 > contador_o2) {
			return 1;
		} else if (contador_o1 < contador_o2) {
			return -1;
		}

		return 0;
	}

	/**
	 * Metodo para anadir un predicado a la clave Criteria del mapa
	 * 
	 * @param c
	 *            clave a la que se le va a anadir el predicado
	 * @param p
	 *            predicado a anadir a la clave criteria
	 * @return BlackBoxComparator a la cual se han anadido los elementos
	 */
	public BlackBoxComparator<N, E> addCriteria(Criteria c, Predicate<Node<N>> p) {

		if (this.criteria.containsKey(c) == false) {
			this.criteria.put(c, new ArrayList<Predicate<Node<N>>>());
		}

		this.criteria.get(c).add(p);

		return this;

	}

}
