package es.uam.ads.p5.torneo;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

/**
 * Clase que implementa el tipo de estrategia Eliminatoria
 * 
 * @author Iñaki Dominguez, Rodrigo Amaducci
 *
 */
public class Eliminatoria implements EstrategiaTorneo{

	private List<Participante> primeraMitad;
	private List<Participante> segundaMitad;
	private List<Participante> ganadoresRonda;
	private Comparator<Participante> lucha;
	private int nEnfrentamientos;
	
	@Override
	public void inicializarTorneo(List<Participante> participantes, Comparator<Participante> lucha) {
		this.primeraMitad = new ArrayList<Participante>();
		this.segundaMitad = new ArrayList<Participante>();
		this.ganadoresRonda = new ArrayList<Participante>();
		this.lucha = lucha;
		this.nEnfrentamientos = 0;
		
		Collections.sort(participantes, Collections.reverseOrder(new OrdenaParticipantes()));
		for(int i = 0; i < participantes.size()/2; i++){
			this.primeraMitad.add(participantes.get(i));
		}
		for(int i = participantes.size()/2; i < participantes.size(); i++){
			this.segundaMitad.add(participantes.get(i));
		}
	}

	@Override
	public Entry<Participante, Participante> enfrentamientoProximaRonda() {
		AbstractMap.SimpleEntry<Participante, Participante> e = new AbstractMap.SimpleEntry<Participante, Participante>(this.primeraMitad.get(this.nEnfrentamientos), this.segundaMitad.get(this.nEnfrentamientos));
		this.nEnfrentamientos++;
		if(this.nEnfrentamientos == this.primeraMitad.size() || this.nEnfrentamientos == this.segundaMitad.size()){
			if(this.nEnfrentamientos < this.primeraMitad.size()){
				this.ganadoresRonda.add(this.primeraMitad.get(this.nEnfrentamientos));
			}
			if(this.nEnfrentamientos < this.segundaMitad.size()){
				this.ganadoresRonda.add(this.segundaMitad.get(this.nEnfrentamientos));
			}
			this.nEnfrentamientos = 0;
		}
		return e;
	}

	@Override
	public Participante ganadorProximaRonda() {
		AbstractMap.SimpleEntry<Participante, Participante> e = new AbstractMap.SimpleEntry<Participante, Participante>(this.enfrentamientoProximaRonda());
		Participante p1 = e.getKey();
		Participante p2 = e.getValue();
		int i = 0;
		while (i == 0){
			i = this.lucha.compare(p1, p2);
		}
		if (i == 1){
			return p1;
		} else{
			return p2;
		}
	}

	@Override
	public Participante ganadorTorneo() {
		ArrayList<Participante> aux;
		do{
			do{
				this.ganadoresRonda.add(this.ganadorProximaRonda());
			}while(this.nEnfrentamientos != 0);
			aux =new ArrayList<Participante>(this.ganadoresRonda);
			this.inicializarTorneo(this.ganadoresRonda, this.lucha);
		}while(aux.size() > 1);
		return aux.get(0);
	}

	/**
	 * Clase que implementa un comparador para ordenar a los participantes segun la profundidad de su heuristica
	 * 
	 * @author Iñaki Dominguez, Rodrigo Amaducci
	 *
	 */
	private class OrdenaParticipantes implements Comparator<Participante>{

		@Override
		public int compare(Participante o1, Participante o2) {
			return o1.getProfundidad() - o2.getProfundidad();
		}
		
	}
}
