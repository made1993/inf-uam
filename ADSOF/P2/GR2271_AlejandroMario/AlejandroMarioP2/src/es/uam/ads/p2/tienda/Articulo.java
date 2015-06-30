package es.uam.ads.p2.tienda;
/**
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 * @version 1.0
 *
 * Se describe la clase Articulo para que la tienda pueda gestionar los articulos
 *
*/
public abstract class Articulo {

	private int id;
	private String titulo;

	public Articulo(int id, String titulo) {
		super();
		this.id = id;
		this.titulo = titulo;
	}

	/**
	 * Se implementa el metodo getId para obtener el id del articulo
	 *
	 * @return int Devuelve el id del articulo
	*/
	public int getId() {
		return id;
	}

	/**
	 * Se implementa el metodo setId para asignar un id a un articulo
	 *
	 * @param id El id que se le va a asignar al articulo
	*/
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Se implementa el metodo getTitulo para obtener el titulo del articulo
	 *
	 * @return String Devuelve el titulo del articulo
	*/
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Se implementa el metodo setTitulo para asignar un titulo a un articulo
	 *
	 * @param titulo El titulo que se le va a asignar al articulo
	*/
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
