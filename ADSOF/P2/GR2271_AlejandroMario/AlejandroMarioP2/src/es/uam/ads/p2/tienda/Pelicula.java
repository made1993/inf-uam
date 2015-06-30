package es.uam.ads.p2.tienda;
/**
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 * @version 1.0
 *
 * Se describe la clase Pelicula para que la tienda pueda gestionar las peliculas de la tienda
 *
*/
public class Pelicula extends Articulo {

	private String genero;
	private String director;
	
	public Pelicula(int id,String titulo,String genero, String director) {
		super(id,titulo);
		this.genero = genero;
		this.director = director;
	}

	public String toString(){
		return "["+getId()+"]PELICULA: "+getTitulo()+". ("+this.genero+") Dir: "+this.director;
	}
}
