package es.uam.ads.p2.tienda;
/**
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 * @version 1.0
 *
 * Se describe la clase Disco para que la tienda pueda gestionar los discos de la tienda
 *
*/
public class Disco extends Articulo {

	private String interprete;
	private int fecha;

	public Disco(int id, String titulo,String interprete, int fecha) {
		super(id,titulo);
		this.interprete = interprete;
		this.fecha = fecha;
	}

	public String toString(){
		return "["+getId()+"]DISCO: "+getTitulo()+" - "+this.interprete+"("+this.fecha+")";
	}
}
