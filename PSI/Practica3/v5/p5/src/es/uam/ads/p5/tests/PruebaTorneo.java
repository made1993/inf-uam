package es.uam.ads.p5.tests;

import java.util.List;
import java.util.Arrays;

import es.uam.ads.p5.juego.Heuristica3Raya;
import es.uam.ads.p5.juego.Jugador;
import es.uam.ads.p5.torneo.Lucha;
import es.uam.ads.p5.torneo.Participante;
import es.uam.ads.p5.torneo.Torneo;

public class PruebaTorneo {
	
	public static void main (String[] argv){
		Heuristica3Raya heuristica = new Heuristica3Raya();
		Participante p1 = new Participante("Daniel", 3, heuristica);
		Participante p2 = new Participante("Alex", 2, heuristica);
		Participante p3 = new Participante("Bea", 1, heuristica);
		Participante p4 = new Participante("David", 4, heuristica);
		Participante p5 = new Participante("Sarah", 5, heuristica);
		List<Participante> p = Arrays.asList(p1, p2, p3, p4, p5);
		
		Lucha l1 = new Lucha(5, Jugador.NEGRO);
		Lucha l2 = new Lucha(6, Jugador.BLANCO);
		Lucha l3 = new Lucha(15, Jugador.NEGRO);
		
		
		Torneo t1 = new Torneo(p, l1);
		System.out.println("El ganador del torneo con una lucha de 5 asaltos es " +t1.ganador().getNombre());
		Torneo t2 = new Torneo(p, l2);
		System.out.println("El ganador del torneo con una lucha de 6 asaltos es " +t2.ganador().getNombre());
		Torneo t3 = new Torneo(p, l3);
		System.out.println("El ganador del torneo con una lucha de 15 asaltos es " +t3.ganador().getNombre());
		
	}

}
