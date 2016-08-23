package tienda;

import java.util.*;
import DAO.IIndex;
import tienda.AllowNullComparator;

/**
 * Clase Index para implementar la interfaz IIndex
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class Index implements IIndex {

	private SortedMap<Object, Set<Long>> indices;

	/**
	 * Constructor de la clase Index
	 */
	public Index() {

		this.indices = new TreeMap<>(AllowNullComparator.Instance);

	}

	/**
	 * Funcion para relacionar value con key
	 * 
	 * @param key
	 *            para relacionar
	 * @param value
	 *            relacionado con key
	 */
	@Override
	public void add(Object key, Long value) {

		Set<Long> set = indices.get(key);
		if (set == null) {
			set = new HashSet<>();
			indices.put(key, set);
		}
		set.add(value);

	}

	/**
	 * Funcion para eliminar la relacion entre value y key
	 * 
	 * @param key
	 *            para eliminar su relacion
	 * @param value
	 *            para eliminar de key
	 */
	@Override
	public void delete(Object key, Long value) {

		Set<Long> setkey = indices.get(key);
		setkey.remove(value);
		if (setkey.size() == 0) {
			indices.remove(key);
		}
	}

	/**
	 * Lista los valores relacionados con key
	 * 
	 * @param key
	 *            para buscar
	 * @return coleccion con los valores relacionados con key
	 */
	@Override
	public Collection<Long> search(Object key) {

		if (key == null) {
			return null;
		}

		Set<Long> setkey = indices.get(key);

		if (setkey == null) {
			return Collections.emptySet();
		}

		return Collections.unmodifiableSet(setkey);
	}

	/**
	 * Devuelve la lista de valores desde from a to
	 * 
	 * @param from
	 *            valor desde el cual se va a buscar
	 * @param to
	 *            valor hasta el cual se va a buscar
	 * @return coleccion con los valores encontrados
	 */
	@Override
	public Collection<Long> search(Object from, Object to) {

		Collection<Set<Long>> set;
		Collection<Long> retorno = new HashSet<Long>();
		String c = null;
		int i;
		if (to == null) {
			c = indices.lastKey().toString();
			i = Integer.valueOf(c);
			i++;
			c = Integer.toString(i);
		}

		if (from == null && to == null) {
			set = indices.subMap(indices.firstKey(), (Object) c).values();
			for (Iterator<Set<Long>> iterador = set.iterator(); iterador
					.hasNext();) {
				retorno.addAll(iterador.next());
			}
		} else if (from == null) {
			set = indices.subMap(indices.firstKey(), to).values();
			for (Iterator<Set<Long>> iterador = set.iterator(); iterador
					.hasNext();) {
				retorno.addAll(iterador.next());
			}
		} else if (to == null) {
			set = indices.subMap(from, (Object) c).values();
			for (Iterator<Set<Long>> iterador = set.iterator(); iterador
					.hasNext();) {
				retorno.addAll(iterador.next());
			}
		} else {
			set = indices.subMap(from, to).values();
			for (Iterator<Set<Long>> iterador = set.iterator(); iterador
					.hasNext();) {
				retorno.addAll(iterador.next());
			}
		}

		return Collections.unmodifiableSet((Set<Long>) retorno);
	}

}
