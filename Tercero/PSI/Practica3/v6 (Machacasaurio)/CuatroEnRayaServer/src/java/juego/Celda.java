package juego;

/**
 * Clase que representa una Celda de un Tablero de las 3 en raya
 */
public class Celda implements Cloneable{
	/**
	 * Ficha que contiene, o null
	 */
	private Ficha ficha;
	/**
	 * coordenadas de la celda
	 */
	private int fila, col;
	
	/**
	 * Construye una celda, en las coordenadas dadas
	 * @param fila
	 * @param col
	 */
	public Celda(int fila, int col) {
		this.fila = fila;
		this.col = col;
	}

	/**
	 * A�ade una ficha a la celda, si no est� ocupada
	 * @param f : Ficha a a�adir
	 * @return true si se ha podido a�adir la ficha
	 */
	public boolean addFicha(Ficha f) {
		if (this.esVacia()) {
			this.ficha = f;
			return true;
		}
		return false;
	}
	
	/**
	 * Comprueba si la celda est� vac�a
	 * @return true si est� vac�a
	 */
	public boolean esVacia() {
		return this.ficha == null;
	}
	
	/**
	 * Devuelve el color de la ficha de la celda, o null si est� vac�a
	 * @return el color de la ficha de la celda, o null si est� vac�a
	 */
	public Jugador getColor() {
		return (this.ficha!=null) ? this.ficha.getColor() : null;
	}
	
	
	public int getFila() {
		return this.fila;
	}
	
	public int getCol() {
		return this.col;
	}
	
	/**
	 * Devuelve una copia de la Celda
	 */
	@Override public Celda clone() {
		Celda copia = new Celda(this.fila, this.col);
		if (this.ficha!=null) copia.ficha = this.ficha.clone();
		return copia;
	}
	
	@Override public String toString() {
		return "("+this.fila+", "+this.col+")";
	}
}
