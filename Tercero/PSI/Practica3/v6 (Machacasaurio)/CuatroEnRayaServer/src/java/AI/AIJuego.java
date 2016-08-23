package AI;

import java.util.List;



public interface AIJuego<TABLERO, JUGADOR, MOVIMIENTO> { 
	 // Devuelve la lista de los movimientos posibles para el jugador en el tablero 
	 List<MOVIMIENTO> posiblesMovimientos(TABLERO tablero, JUGADOR player); 
	 
	 // devuelve el tablero resultante de aplicar el movimiento al tablero. En las 
	 // implementaciones, es aconsejable realizar previamente una copia del tablero original 
	 TABLERO estadoResultante(TABLERO tablero, MOVIMIENTO movimiento); 
	 
	 // devuelve el jugador que mueve al cambiar el turno 
	 JUGADOR cambioTurno(JUGADOR player); 
	 
	 // devuelve si el tablero es final (es empate o alg�n jugador gana) 
	 boolean esFinal(TABLERO tablero, JUGADOR player); 
	 
	 // devuelve si el jugador es ganador 
	 boolean esGanador(TABLERO tablero, JUGADOR player);

	MOVIMIENTO esVacio(TABLERO tablero, JUGADOR j); 
}
