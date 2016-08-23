package tienda;

import descriptores.DirectorTypeDescriptor;

/**
 * Clase Director (Director de una pelicula)
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class Director extends Persona {

	private static DirectorTypeDescriptor director = new DirectorTypeDescriptor();

	/**
	 * Constructor de la clase Director
	 */
	public Director() {

	}

	/**
	 * Metodo para obtener el typo de dato Director
	 * 
	 * @return tipo de dato director
	 */
	public static DirectorTypeDescriptor getDescriptor() {
		return director;
	}

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos director
	 */
	@Override
	public String getType() {
		return "Director";
	}

	/**
	 * toString de la clase Director
	 */
	public String toString() {
		return "[" + getId() + "]DIRECTOR: " + getNombre() + " "
				+ getApellidos();
	}

}
