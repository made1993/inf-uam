package tests;

import tienda.*;
import DAO.*;
import DAO.ITypeDescriptor.Type;

/**
 * Prueba de las clases de la tienda, TypeDescriptor y Entity
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class apto1 {
	public static void main(String[] args) {

		String string;
		Type tipo;

		Libro l1 = new Libro();

		System.out
				.println("Prueba getProperty de Entity: (Salida esperada: El Quijote)");
		l1.setTitulo("El Quijote");
		String titulo = (String) l1.getProperty("titulo");
		System.out.println("Titulo: " + titulo);

		ITypeDescriptor t1 = Libro.getDescriptor();

		System.out
				.println("Prueba getName de TypeDescriptor: (Salida esperada: Libro)");
		string = t1.getName();
		System.out.println("Tipo del t1: " + string);

		System.out
				.println("Prueba getType de TypeDescriptor: (Salida esperada: String)");
		tipo = t1.getType("titulo");
		System.out.println("Tipo del titulo de t1: " + tipo);

		System.out
				.println("Prueba getType del TypeDescriptor: (Salida esperada: Long)");
		tipo = t1.getType("autor");
		System.out.println("Tipo del autor de t1: " + tipo);

		Autor a1 = new Autor();

		System.out
				.println("Prueba setId y getId de Entity: (Salida esperada: 1)");
		a1.setId(1L);
		System.out.println("ID del autor a1: " + a1.getId());

		System.out
				.println("Prueba setProperty de Entity: (Salida esperada: Cervantes)");
		a1.setProperty("apellidos", "Cervantes");
		string = a1.getApellidos(); // devuelve Cervantes
		System.out.println("Apellidos del autor a1: " + string);

		System.out.println("Prueba getId y setAutor: (Salida esperada: 1)");
		l1.setAutor(a1.getId());
		System.out.println("Autor del libro l1: " + l1.getAutor());
	}
}
