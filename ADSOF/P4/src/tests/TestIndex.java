package tests;

import java.util.*;
import tienda.*;

/**
 * Prueba de las clases de la tienda, TypeDescriptor, Entity y de Index
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class TestIndex {

	public static void main(String[] args) {

		Index indice = new Index();
		Collection<Long> coleccion = new HashSet<Long>();
		Collection<Long> coleccion_search = new HashSet<Long>();
		Collection<Long> coleccion_error = new HashSet<Long>();

		indice.add("12", 1L);
		indice.add("12", 2L);
		indice.add("14", 3L);
		indice.add("15", 4L);
		indice.add("16", 5L);

		System.out.println("Prueba add y search: (Salida esperada: 1, 2)");
		coleccion = indice.search("12");
		for (Iterator<Long> iterador = coleccion.iterator(); iterador.hasNext();) {
			System.out.println("Elemento coleccion: " + iterador.next());
		}

		System.out
				.println("Prueba search: (Salida esperada: La clave 1 no contiene 1L)");
		coleccion_error = indice.search("1");
		if (coleccion_error.isEmpty()) {
			System.out.println("La clave 1 no contiene 1L");
		}

		System.out.println("Prueba delete y search: (Salida esperada: false)");
		indice.delete("16", 5L);
		System.out.println("Coleccion: " + indice.search("16").contains(5L));

		System.out
				.println("Prueba search (from, to): (Salida esperada: 1, 2, 3, 4)");
		coleccion_search = indice.search("11", "17");
		for (Iterator<Long> iterador = coleccion_search.iterator(); iterador
				.hasNext();) {
			System.out.println("Elemento coleccion_search: " + iterador.next());
		}

	}

}
