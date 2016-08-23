package es.uam.ads.p5.torneo;

import java.util.Comparator;

import es.uam.ads.p5.juego.Jugador;

/**
 * Implementa los metodos por los cuales se obtendra el ganador de una lucha
 * 
 * @author iñaki Dominguez, Rodrigo Amaducci
 *
 */
public class Lucha implements Comparator<Participante>{
	private int num_asaltos;
	private Jugador comienza;
	
	/**
	 * constructor de la clase
	 * 
	 * @param num_asaltos numero de asaltos de la lucha
	 * @param comienza jugador que juega primero
	 */
	public Lucha(int num_asaltos, Jugador comienza){
		this.num_asaltos = num_asaltos;
		this.comienza = comienza;
	}
	
	/**
	 * Calcula el ganador de una lucha de dos participantes
	 * 
	 * @param p1 primer participante
	 * @param p2 segundo participante
	 * @return el marcador final. si es mayor que 0, gana p2, si es menor, gana p1, si es 0, ha habido un empate
	 */
	public int luchar(Participante p1, Participante p2){
		int marcador = 0;
		Asalto asalto = new Asalto(p1.getProfundidad(), p2.getProfundidad());
		
		for(int i=0; i<this.num_asaltos; i++){
			marcador += asalto.realizarAsalto(this.comienza);
			this.comienza = (this.comienza == Jugador.BLANCO) ? Jugador.NEGRO : Jugador.BLANCO;
		}
		
		return marcador;
	}

	@Override
	public int compare(Participante p1, Participante p2) {
		int ganador = luchar(p1, p2);
		
		if (ganador < 0) { //gana blanco (p1)
			return 1;
		} else if (ganador > 0) { //gana negro (p2)
			return -1;
		} else { //empate
			return 0;
		}
	}

}
