package juego;

import AI.AIHeuristica;
/**
 * Clase que implemente la función heurística de la AI
 * 
 * @author Rodrigo Amaducci
 *
 */
public class Heuristica4Raya implements AIHeuristica<Tablero, Jugador>{

	Juego4Raya juego = new Juego4Raya();
	
	@Override
	public double calcula(Tablero t, Jugador j) {
		int n2 = t.ComprobarTablero(2, j);
		int n3 = t.ComprobarTablero(3, j);
		int n4 = t.ComprobarTablero(4, j);
		
		n2 -= t.ComprobarTablero(2, this.juego.cambioTurno(j));
		n3 -= t.ComprobarTablero(3, this.juego.cambioTurno(j));
		n4 -= t.ComprobarTablero(4, this.juego.cambioTurno(j));
		
		return n2*4 + n3*9 + n4*1000;
		/*if (t.esGanador(j)){
			return Double.POSITIVE_INFINITY;
		} else if (t.esGanador(this.juego.cambioTurno(j))){
			return Double.NEGATIVE_INFINITY;
		} else{
			return 0;
		}*/
	}

}
