package es.uam.ads.p5.juego;

import java.util.ArrayList;
import java.util.List;

import es.uam.ads.p5.AI.AIJuego;

/**
 * Clase que implementa los métodos dej juego del 4 en raya
 * 
 * @author Rodrigo Amaducci
 *
 */
public class Juego4Raya implements AIJuego<Tablero, Jugador, Movimiento>{

	@Override
	public List<Movimiento> posiblesMovimientos(Tablero tablero, Jugador player) {
		List <Movimiento> movs = new ArrayList<Movimiento>();
		for(int columna = 0; columna < 7; columna++){
			for(int fila = 0; fila < 6; fila++){
				if(tablero.getCelda(fila, columna).esVacia()){
					Celda c = new Celda(fila, columna);
					Movimiento m = new Movimiento(c, player);
					movs.add(m);
					break;
				}
			}
			
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
