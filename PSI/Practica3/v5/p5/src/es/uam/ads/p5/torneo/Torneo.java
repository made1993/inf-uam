package es.uam.ads.p5.torneo;

import java.util.Collections;
import java.util.List;
/**
 * Clase que implementa un torneo
 * 
 * @author Iñaki Dominguez, Rodrigo Amaducci
 *
 */
public class Torneo {
	private List<Participante> participantes;
	private Lucha lucha;
	
	/**
	 * Costructor de la clase torneo
	 * 
	 * @param participantes lista de participantes del torneo
	 * @param lucha tipo de lucha que utilizara el torneo
	 */
	public Torneo(List<Participante> participantes, Lucha lucha){
		this.participantes = participantes;
		this.lucha = lucha;
	}
	
	/**
	 * Obtiene el jugador ganador del torneo
	 * 
	 * @return el participante que ha ganado
	 */
	public Participante ganador(){
		Collections.sort(this.participantes, Collections.reverseOrder(this.lucha));
		return this.participantes.get(0);
	}
	

	
}
