package es.uam.ads.p5.torneo;

import es.uam.ads.p5.AI.MiniMax;
import es.uam.ads.p5.juego.Heuristica3Raya;
import es.uam.ads.p5.juego.Juego3Raya;
import es.uam.ads.p5.juego.Jugador;
import es.uam.ads.p5.juego.Movimiento;
import es.uam.ads.p5.juego.Tablero;


/**
 * Clase que implementa un asalto del juego del 3 en raya
 * 
 * @author Iñaki Dominguez, Rodrigo Amaducci
 *
 */
public class Asalto {
	private int profb;
	private int profn;
	
	/**
	 * Constructor de la clase
	 * 
	 * @param profb profundidad de la heuristica del jugador blanco
	 * @param profn profundidad de la heuristica del jugador negro
	 */
	Asalto(int profb, int profn){
		this.profb = profb;
		this.profn = profn;
	}
	
	/**
	 * Obtiene el ganador del asalto
	 * 
	 * @param comienza jugador que juega primero
	 * @return -1 si gana blanco, 1 si gana negro, 0 si empatan
	 */
	public int realizarAsalto(Jugador comienza){
		Jugador player = comienza;
		Juego3Raya jrt = new Juego3Raya();
		Tablero t = new Tablero();
		Heuristica3Raya hrt = new Heuristica3Raya();
		int prof;
		Movimiento mov;
		MiniMax<Tablero, Jugador, Movimiento, Juego3Raya, Heuristica3Raya> m = new MiniMax<Tablero, Jugador, Movimiento, Juego3Raya, Heuristica3Raya> (jrt, hrt);
		
		player = jrt.cambioTurno(player);
		do {
			player = jrt.cambioTurno(player);
			prof = (player == Jugador.BLANCO) ? this.profb : this.profn;
			mov = m.mejorMovimiento(t, player, prof);
			if (mov != null) {
				t = jrt.estadoResultante(t, mov);
			}
		} while (!jrt.esFinal(t, player));
		
		if (jrt.esGanador(t, Jugador.BLANCO)) {
			return -1;
		} else if (jrt.esGanador(t, Jugador.NEGRO)){
			return 1;
		} else{
			return 0;
		}
		

	}
	
}
