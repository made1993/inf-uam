package es.uam.ads.p5.juego;

import java.util.ArrayList;
import java.util.List;

import es.uam.ads.p5.AI.AIJuego;

/**
 * Clase que implementa los métodos dej juego del 3 en raya
 * 
 * @author inaki
 *
 */
public class Juego3Raya implements AIJuego<Tablero, Jugador, Movimiento>{

	@Override
	public List<Movimiento> posiblesMovimientos(Tablero tablero, Jugador player) {
		List <Movimiento> movs = new ArrayList<Movimiento>();
		for(Celda c:tablero.getCeldasLibres()){
			Movimiento m = new Movimiento(c, player);
			movs.add(m);
		}
		return movs;
	}

	@Override
	public Tablero estadoResultante(Tablero tablero, Movimiento movimiento) {
		Tablero t = tablero.clone();
		t.mover(movimiento);
		return t;
	}

	@Override
	public Jugador cambioTurno(Jugador player) {
		return (player == Jugador.BLANCO) ? Jugador.NEGRO : Jugador.BLANCO;
	}

	@Override
	public boolean esFinal(Tablero tablero, Jugador player) {
		return tablero.esGanador(player) || tablero.esGanador(this.cambioTurno(player))|| tablero.esEmpate();
	}

	@Override
	public boolean esGanador(Tablero tablero, Jugador player) {
		return tablero.esGanador(player);
	}
}
