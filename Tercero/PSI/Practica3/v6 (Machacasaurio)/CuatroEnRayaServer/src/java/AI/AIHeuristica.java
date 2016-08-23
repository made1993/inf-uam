package AI;

public interface AIHeuristica<TABLERO,JUGADOR> { 
	 // da una valoracion del tablero actual desde el punto de vista del jugador j 
	 double calcula(TABLERO t, JUGADOR j); 
} 
