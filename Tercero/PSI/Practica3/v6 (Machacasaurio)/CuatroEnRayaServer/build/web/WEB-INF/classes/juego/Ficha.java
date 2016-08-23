package juego;

/**
 * Clase que representa una ficha de un determinado Jugador
 */
public class Ficha implements Cloneable{
	/**
	 * Jugador due�o de la ficha
	 */
	private Jugador color;
	
	public Ficha(Jugador c) {
		this.color = c;
	}
	
	public Jugador getColor() {
		return this.color;
	}
	
	/**
	 * Crea una copia de la ficha
	 */
	@Override public Ficha clone() {
		return new Ficha(this.color);
	}
}
