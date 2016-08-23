package tienda;

import descriptores.AutorTypeDescriptor;

/**
 * Clase Autor (Autor de un libro)
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class Autor extends Persona {

	private static AutorTypeDescriptor autor = new AutorTypeDescriptor();

	/**
	 * Constructor de la clase Autor
	 */
	public Autor() {

	}

	/**
	 * Metodo para obtener el typo de dato Autor
	 * 
	 * @return tipo de dato autor
	 */
	public static AutorTypeDescriptor getDescriptor() {
		return autor;
	}

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos autor
	 */
	@Override
	public String getType() {
		return "Autor";
	}

	/**
	 * toString de la clase autor
	 */
	public String toString() {
		return "[" + getId() + "]AUTOR: " + getNombre() + " " + getApellidos();
	}
}
