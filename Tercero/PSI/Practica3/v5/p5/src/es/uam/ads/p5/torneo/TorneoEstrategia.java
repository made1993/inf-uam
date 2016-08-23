package es.uam.ads.p5.torneo;

import java.util.List;

/**
 * Clase que implementa un torneo utilizando un tipo de estrategia variable
 * 
 * @author Iñaki Dominguez, Rodrigo Amaducci
 *
 */
public class TorneoEstrategia {

	private List<Participante> participantes;
	private Lucha lucha;
	private EstrategiaTorneo estrategia;
	
	/**
	 * costructor de la clase
	 * 
	 * @param participantes lista de participantes del torneo
	 * @param lucha tipo de lucha que se usara
	 * @param e tipo de estrategia que se usara
	 */
	public TorneoEstrategia(List<Participante> participantes, Lucha lucha, EstrategiaTorneo e){
		this.participantes = participantes;
		this.lucha = lucha;
		this.estrategia = e;
		this.estrategia.inicializarTorneo(this.participantes, this.lucha);
	}
	
	/**
	 * Obtiene el participante ganador del torneo
	 * @return el participante ganador
	 */
	public Participante ganador(){
		return this.estrategia.ganadorTorneo();
	}
}
