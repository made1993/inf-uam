package tienda;

import descriptores.LibroTypeDescriptor;

/**
 * Clase Autor (Autor de un libro)
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class Libro extends Articulo {

	private Long autor;
	private String editorial;
	private static LibroTypeDescriptor libro = new LibroTypeDescriptor();

	/**
	 * Constructor de la clase Libro
	 */
	public Libro() {

	}

	/**
	 * Devuelve el autor de un libro
	 * 
	 * @return el autor del libro
	 */
	public Long getAutor() {
		return autor;
	}

	/**
	 * Asigna un autor a un libro
	 * 
	 * @param autor
	 *            a asignar al libro
	 */
	public void setAutor(Long autor) {
		this.autor = autor;
	}

	/**
	 * Devuelve la editorial de un libro
	 * 
	 * @return la editorial del libro
	 */
	public String getEditorial() {
		return editorial;
	}

	/**
	 * Asigna una editorial a un libro
	 * 
	 * @param editorial
	 *            a asignar al libro
	 */
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	/**
	 * Metodo para obtener el typo de dato Libro
	 * 
	 * @return tipo de dato libro
	 */
	public static LibroTypeDescriptor getDescriptor() {
		return libro;
	}

	/**
	 * toString de la clase Libro
	 */
	public String toString() {
		return "[" + getId() + "]LIBRO: " + getTitulo() + ". " + this.autor
				+ ". " + this.editorial + "";
	}

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos libro
	 */
	@Override
	public String getType() {
		return "Libro";
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
		if (property.equalsIgnoreCase("autor"))
			return this.autor;
		else if (property.equalsIgnoreCase("editorial"))
			return this.editorial;
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
		if (property.equalsIgnoreCase("autor"))
			this.autor = (Long) value;
		else if (property.equalsIgnoreCase("editorial"))
			this.editorial = (String) value;
		else if (property.equals("id"))
			setId((Long) value);
		else if (property.equalsIgnoreCase("titulo"))
			setTitulo((String) value);

	}

}
