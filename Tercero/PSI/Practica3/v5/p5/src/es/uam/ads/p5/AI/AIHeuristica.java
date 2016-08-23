package es.uam.ads.p5.AI;

public interface AIHeuristica<TABLERO,JUGADOR> { 
	 // da una valoración del tablero actual desde el punto de vista del jugador j 
	 double calcula(TABLERO t, JUGADOR j); 
} 
