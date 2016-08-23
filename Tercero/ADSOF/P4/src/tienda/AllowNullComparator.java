package tienda;

import java.util.*;

/**
 * Clase para comparar objetos
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class AllowNullComparator implements Comparator<Object> {

	public static final AllowNullComparator Instance = new AllowNullComparator();

	/**
	 * Metodo para comparar dos objetos
	 * 
	 * @param a
	 *            primer objeto a comparar
	 * @param b
	 *            segundo objeto a comparar
	 * @return entero que devuelve la comparacion
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compare(Object a, Object b) {
		if (a == null) {
			return b == null ? 0 : 1;
		} else if (b == null) {
			return 1;
		} else {
			return ((Comparable<Object>) a).compareTo(b);
		}
	}

}
