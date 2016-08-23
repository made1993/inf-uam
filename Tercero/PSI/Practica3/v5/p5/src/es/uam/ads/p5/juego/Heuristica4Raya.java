package es.uam.ads.p5.juego;

import es.uam.ads.p5.AI.AIHeuristica;
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
		if (t.esGanador(j)){
			return Double.POSITIVE_INFINITY;
		} else if (t.esGanador(this.juego.cambioTurno(j))){
			return Double.NEGATIVE_INFINITY;
		} else{
			return 0;
		}
	}

}
