package tienda;

/**
 * Se describe la clase Articulo para que la tienda pueda gestionar los
 * articulos
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public abstract class Articulo extends AbstractEntity {

	private String titulo;

	/**
	 * Constructor de la clase articulo
	 */
	public Articulo() {

	}

	/**
	 * Metodo para obtener el titulo del articulo
	 *
	 * @return el titulo del articulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Metodo para asignar un titulo a un articulo
	 *
	 * @param titulo
	 *            que se le va a asignar al articulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
