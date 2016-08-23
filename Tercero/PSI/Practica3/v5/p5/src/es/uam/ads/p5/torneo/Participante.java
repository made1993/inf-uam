package es.uam.ads.p5.torneo;

import es.uam.ads.p5.AI.AIHeuristica;
import es.uam.ads.p5.juego.Jugador;
import es.uam.ads.p5.juego.Tablero;
 /**
  * Clase que implementa el objeto Participante
  * 
  * @author Iñaki Dominguez, Rodrigo Amaducci
  *
  */
public class Participante {
	
	private String nombre;
	private int profundidad;
	private AIHeuristica<Tablero, Jugador> heuristica;
	
	/**
	 * Constructor de la clase
	 * 
	 * @param n nombre del participante
	 * @param prof profundidad de su heuristica
	 * @param h heuristica utilizada
	 */
	public Participante(String n, Integer prof, AIHeuristica<Tablero, Jugador> h){
		this.nombre = n;
		this.profundidad = prof;
		this.heuristica = h;
	}

	public String getNombre(){
		return this.nombre;
	}

	public int getProfundidad() {
		return this.profundidad;
	}

	public AIHeuristica<Tablero, Jugador> getHeuristica() {
		return this.heuristica;
	}

	
	
}
