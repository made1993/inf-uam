package juego;

/**
 * Clase que representa un movimiento de las tres en raya. La informaci�n necesaria es la celda en la que
 * se va a colocar la ficha de un determinado jugador. 
 */
public class Movimiento {
	/**
	 * Celda donde se va a colocar una ficha
	 */
	private Celda c;
	/**
	 * Jugador due�o de la ficha
	 */
	private Jugador player;
	
	/**
	 * Constructor que crea un movimiento
	 * @param c: Celda en la que se va a poner una ficha
	 * @param jugador: Jugador que realiza el movimiento
	 */
	public Movimiento(Celda c, Jugador jugador){
		this.c = c;
		this.player=jugador;		
	}
	
	public int getFila() {
		return this.c.getFila();
	}
	
	public int getCol() {
		return this.c.getCol();
	}
	
	public Jugador getJugador() {
		return player;
	}

	@Override public String toString(){
		return "Celda: " + this.c + ", Player:" + player.toString();
	}
}
