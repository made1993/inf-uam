package es.uam.ads.p2.tienda;
/**
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 * @version 1.0
 *
 * Se describe la clase Tienda para gestionar los articulos de la tienda
 *
*/
public class Tienda {
	
	private String nombre;
	private String calle;
	private int cp;

	public Tienda(String nombre, String calle, int cp){
		this.nombre= nombre;
		this.calle= calle;
		this.cp= cp;
	}
}
