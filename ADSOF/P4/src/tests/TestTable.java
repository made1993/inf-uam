package tests;

import java.util.Collection;
import java.util.HashSet;
import DAO.*;
import tienda.*;

/**
 * Prueba de las clases de la tienda, TypeDescriptor, Entity, Index y Table
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class TestTable {

	public static void main(String[] args) {

		ITypeDescriptor td = Libro.getDescriptor();
		ITable tabla = new Table(td);
		Libro l0 = new Libro();
		Libro l1 = new Libro();
		Libro l2 = new Libro();
		Collection<Long> coleccion1 = new HashSet<Long>();
		Collection<Long> coleccion2 = new HashSet<Long>();
		Collection<Long> coleccion3 = new HashSet<Long>();

		System.out
				.println("Prueba updateEntity y getId: (Salida esperada: 0, 0)");
		l0.setTitulo("El Quijote");
		System.out.println("Id Libro l0: " + tabla.updateEntity(l0));
		System.out.println("Id Libro l0: " + l0.getId());

		System.out
				.println("Prueba updateEntity y getId: (Salida esperada: 1, 1)");
		l1.setEditorial("sma");
		System.out.println("Id Libro l1: " + tabla.updateEntity(l1));
		System.out.println("Id Libro l1: " + l1.getId());

		System.out
				.println("Prueba updateEntity y getId: (Salida esperada: 2, 2)");
		l2.setEditorial("santillana");
		System.out.println("Id Libro l2: " + tabla.updateEntity(l2));
		System.out.println("Id Libro l2: " + l2.getId());

		System.out
				.println("Prueba getType y getName: (Salida esperada: Libro)");
		System.out.println("Tipo de la tabla: " + tabla.getType().getName());

		System.out
				.println("Prueba getEntity y getProperty: (Salida esperada: El Quijote)");
		Libro l_aux0 = (Libro) tabla.getEntity(0L);
		System.out.println("Titulo del Libro l_aux0: "
				+ l_aux0.getProperty("titulo"));

		System.out
				.println("Prueba getEntity y getProperty: (Salida esperada: sma)");
		Libro l_aux1 = (Libro) tabla.getEntity(1L);
		System.out.println("Editorial del Libro l_aux1: "
				+ l_aux1.getProperty("editorial"));

		System.out
				.println("Prueba updateEntity y search: (Salida esperada: true)");
		tabla.updateEntity(l_aux0);
		tabla.updateEntity(l_aux1);
		coleccion1 = tabla.search("id", 4L);
		if (coleccion1 == null) {
			System.out.println("Error en tabla search");
		} else {
			System.out.println("Search funciona correctamente: "
					+ coleccion1.contains(4L));
		}

		System.out.println("Prueba delete y search: (Salida esperada: false)");
		tabla.delete(l_aux1);
		coleccion2 = tabla.search("id", 4L);
		if (coleccion2.isEmpty()) {
			System.out.println("Encuentra el id 4 en tabla: "
					+ coleccion2.contains(4L));
		} else {
			System.out.println("Error en tabla delete");
		}

		// La funcion search(property, from, to) no funciona correctamente
		System.out.println("Prueba search de from-to: (Salida esperada: true)");
		coleccion3 = tabla.search("titulo", "El Quijote", "El Quijote");
		if (coleccion3.isEmpty()) {
			System.out.println("Error en funcion delete from to");
		} else {
			System.out.println("Search from-to funciona correctamente: "
					+ coleccion3.contains(3L));
		}

	}

}
