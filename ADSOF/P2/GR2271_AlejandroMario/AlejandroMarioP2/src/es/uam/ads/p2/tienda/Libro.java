package es.uam.ads.p2.tienda;
/**
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 * @version 1.0
 *
 * Se describe la clase Libro para que la tienda pueda gestionar los libros de la tienda
 *
*/
public class Libro extends Articulo {

	private String autor;
	private String editorial;

	public Libro(int id, String titulo,String autor, String editorial) {

		super(id,titulo);
		this.autor = autor;
		this.editorial = editorial;
	}

	public String toString(){
		return "["+getId()+"]LIBRO: "+getTitulo()+". "+this.autor+". "+this.editorial+"";
	}
}
