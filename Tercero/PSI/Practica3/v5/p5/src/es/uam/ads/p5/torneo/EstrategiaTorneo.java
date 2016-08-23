package es.uam.ads.p5.torneo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Interfaz que define las funciones que implementarán las posibles estrategias del torneo
 * 
 * @author Iñaki Dominguez, Rodrigo Amaducci
 *
 */
public interface EstrategiaTorneo {
	/**
	 * Prepara la lista de participantes y la forma de ordenarlos
	 * 
	 * @param participantes lista de participantes del torneo
	 * @param lucha comparador con el criterio de ordenación de los participantes
	 */
	public void inicializarTorneo(List<Participante> participantes, Comparator<Participante> lucha);
	
	/**
	 * Calcula el siguiente enfrentamiento del torneo
	 * 
	 * @return un par con los dos participanter que se enfrentaran
	 */
	public Map.Entry<Participante, Participante> enfrentamientoProximaRonda();

	/**
	 * Calcula el ganador del siguiente enfrentamiento del torneo
	 * 
	 * @return el participante ganador
	 */
	public Participante ganadorProximaRonda();

	/**
	 * calcula el ganador final del torneo
	 * 
	 * @return el participante ganador
	 */
	public Participante ganadorTorneo();
}