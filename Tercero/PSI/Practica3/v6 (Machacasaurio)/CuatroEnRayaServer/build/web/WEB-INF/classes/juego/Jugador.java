package juego;

/**
 * Enumerado que representa cada uno de los bandos de las 3 en raya. 
 */
public enum Jugador {
	BLANCO('O'), NEGRO('X');
	
	/**
	 * Simbolo que representa a cada jugador
	 */
	private char symbol; 
	
	/**
	 * Constructor privado del enum con el simbolo del jugador
	 * @param s
	 */
	private Jugador(char s) {
		this.symbol = s;
	}
		
	/**
	 * Devuelve el simbolo del jugador como un String
	 */
	@Override public String toString() {
		return String.valueOf(this.symbol);
	}
}	
