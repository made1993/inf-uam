package tienda;

import descriptores.PeliculaTypeDescriptor;

/**
 * Clase Pelicula
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class Pelicula extends Articulo {

	private String genero;
	private Long director;
	private static PeliculaTypeDescriptor pelicula = new PeliculaTypeDescriptor();

	/**
	 * Constructor de la clase Pelicula
	 */
	public Pelicula() {
	}

	/**
	 * Constructor de la clase Pelicula
	 * 
	 * @param id
	 *            de la pelicula
	 * @param titulo
	 *            de la pelicula
	 * @param genero
	 *            de la pelicula
	 * @param director
	 *            de la pelicula
	 */
	public Pelicula(int id, String titulo, String genero, String director) {
	}

	/**
	 * Devuelve el genero de una pelicula
	 * 
	 * @return el genero de una pelicula
	 */
	public String getGenero() {
		return this.genero;
	}

	/**
	 * Asigna un genero a una pelicula
	 * 
	 * @param genero
	 *            a asignar a la pelicula
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * Asigna un director a una pelicula
	 * 
	 * @param director
	 *            a asignar a la pelicula
	 */
	public void setDirector(Long director) {
		this.director = director;
	}

	/**
	 * Devuelve el director de una pelicula
	 * 
	 * @return el director de una pelicula
	 */
	public Long getDirector() {
		return this.director;
	}

	/**
	 * toString de la clase Pelicula
	 */
	public String toString() {
		return "[" + getId() + "]PELICULA: " + getTitulo() + ". ("
				+ getGenero() + ") Dir: " + getDirector();
	}

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos pelicula
	 */
	@Override
	public String getType() {
		return "Pelicula";
	}

	/**
	 * Metodo para obtener el typo de dato Pelicula
	 * 
	 * @return tipo de dato pelicula
	 */
	public static PeliculaTypeDescriptor getDescriptor() {
		return pelicula;
	}

	/**
	 * Devuelve el valor de una propiedad de una entidad
	 * 
	 * @param property
	 *            de la entidad
	 * @return el objeto con el valor de la propiedad
	 */
	@Override
	public Object getProperty(String property) {
		if (property.equalsIgnoreCase("genero"))
			return getGenero();
		else if (property.equalsIgnoreCase("director"))
			return getDirector();
		else if (property.equalsIgnoreCase("id"))
			return getId();
		else if (property.equalsIgnoreCase("titulo"))
			return getTitulo();

		return null;
	}

	/**
	 * Modifica el valor de una propiedad
	 * 
	 * @param property
	 *            propiedad a modifcar
	 * @param value
	 *            valor para asignar
	 */
	@Override
	public void setProperty(String property, Object value) {
		if (property.equalsIgnoreCase("genero"))
			setGenero((String) value);
		else if (property.equalsIgnoreCase("director"))
			setDirector((long) value);
		else if (property.equalsIgnoreCase("id"))
			setId((long) value);
		else if (property.equalsIgnoreCase("titulo"))
			setTitulo((String) value);

	}
}
