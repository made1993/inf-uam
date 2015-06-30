package AI;

import java.util.List;
import java.lang.Math;

public class MiniMax < TABLERO, JUGADOR, MOVIMIENTO, AIJ extends AIJuego<TABLERO, JUGADOR, MOVIMIENTO>, AIH extends AIHeuristica<TABLERO, JUGADOR>> {
	private static final int MAXIMO_POSIBLE = Integer.MAX_VALUE;
	private static final int MINIMO_POSIBLE = Integer.MIN_VALUE;
	private static final int MAXIMIZAR = 1;
	private static final int MINIMIZAR = 0;
	private AIJ aij;
	private AIH aih;
	
	public MiniMax(AIJ aij, AIH aih){
		this.aij = aij;
		this.aih = aih;	
	}
	
	public MOVIMIENTO mejorMovimiento(TABLERO tablero, JUGADOR player, int profundidad) {
		if(this.aij.esVacio(tablero, player) != null){
			return this.aij.esVacio(tablero, player);
		}
		
		double maxValor = MINIMO_POSIBLE;
		double valor;
		TABLERO t_aux;
		List<MOVIMIENTO> posibles = this.aij.posiblesMovimientos(tablero, player);
		if(posibles.isEmpty()){
			return null;
		}
		MOVIMIENTO m = posibles.get(0);
		JUGADOR siguiente_turno = aij.cambioTurno(player);
		
		
		for(MOVIMIENTO m_aux:posibles){
			t_aux = aij.estadoResultante(tablero, m_aux);
			valor = evaluar(t_aux, player, siguiente_turno, MINIMIZAR, 1, profundidad);
			
			if(valor > maxValor){
				m = m_aux;
				maxValor = valor;
			} else if (valor == maxValor){
				if(Math.random() > 0.5){
					m = m_aux;
				}
			}
		}
		
		return m;
	}


	double evaluar(TABLERO tablero, JUGADOR jugador, JUGADOR siguiente_turno, int opcion, int profundidad, int max_profundidad) {
		
		double valor;

		if (aij.esFinal(tablero, jugador) || profundidad >= max_profundidad) {
			return aih.calcula(tablero, jugador);
		}

		valor = (opcion == MAXIMIZAR) ? MINIMO_POSIBLE : MAXIMO_POSIBLE;
		List<MOVIMIENTO> movs = aij.posiblesMovimientos(tablero, siguiente_turno);
		siguiente_turno = aij.cambioTurno(siguiente_turno);
		TABLERO t_aux;

		for (MOVIMIENTO aux : movs) {
			t_aux = aij.estadoResultante(tablero, aux);
			if (opcion == MAXIMIZAR) {
				valor = Math.max(
						valor,
						evaluar(t_aux, jugador, siguiente_turno, MINIMIZAR, profundidad + 1, max_profundidad));
			} else {
				valor = Math.min(
						valor,
						evaluar(t_aux, jugador, siguiente_turno, MAXIMIZAR, profundidad + 1, max_profundidad));
			}
		}
		return valor;
	}

}
