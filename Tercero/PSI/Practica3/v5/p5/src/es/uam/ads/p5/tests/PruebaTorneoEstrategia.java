package es.uam.ads.p5.tests;

import java.util.Arrays;
import java.util.List;

import es.uam.ads.p5.juego.Heuristica3Raya;
import es.uam.ads.p5.juego.Jugador;
import es.uam.ads.p5.torneo.Eliminatoria;
import es.uam.ads.p5.torneo.EstrategiaTorneo;
import es.uam.ads.p5.torneo.Lucha;
import es.uam.ads.p5.torneo.Participante;
import es.uam.ads.p5.torneo.TorneoEstrategia;

public class PruebaTorneoEstrategia {
	
	public static void main (String[] argv){
		Heuristica3Raya heuristica = new Heuristica3Raya();
		Participante p1 = new Participante("Daniel", 1, heuristica);
		Participante p2 = new Participante("Alex", 2, heuristica);
		Participante p3 = new Participante("Bea", 3, heuristica);
		Participante p4 = new Participante("David", 4, heuristica);
		Participante p5 = new Participante("Sarah", 5, heuristica);
		List<Participante> p = Arrays.asList(p1, p2, p3, p4, p5);
		
		
		Lucha l1 = new Lucha(5, Jugador.NEGRO);
		Lucha l2 = new Lucha(6, Jugador.BLANCO);
		Lucha l3 = new Lucha(15, Jugador.NEGRO);
		EstrategiaTorneo e = new Eliminatoria();
		
		TorneoEstrategia t1 = new TorneoEstrategia(p, l1, e);
		System.out.println("El ganador del torneo con una lucha de 5 asaltos es " +t1.ganador().getNombre());
		TorneoEstrategia t2 = new TorneoEstrategia(p, l2, e);
		System.out.println("El ganador del torneo con una lucha de 6 asaltos es " +t2.ganador().getNombre());
		TorneoEstrategia t3 = new TorneoEstrategia(p, l3, e);
		System.out.println("El ganador del torneo con una lucha de 15 asaltos es " +t3.ganador().getNombre());
		
		Participante aux = new Participante("Chuck", 6, heuristica);
		
		Participante p6 = new Participante("Daniel", 4, heuristica);
		Participante p7 = new Participante("Alex", 4, heuristica);
		Participante p8 = new Participante("Bea", 4, heuristica);
		Participante p9 = new Participante("David", 4, heuristica);
		Participante p10 = new Participante("Sarah", 4, heuristica);

		System.out.println("\nAl añadir un participante con profundidad 6, ése ganará siempre.");
		for(int i = 0; i < 10; i++){
			List<Participante> paux = Arrays.asList(p1, p2, p3, p4, p5, aux);
			TorneoEstrategia t4 = new TorneoEstrategia(paux, l1, e);
			System.out.println("\tEl ganador del torneo es " +t4.ganador().getNombre());
		}
		System.out.println("\nAl ser todos los participantes de la misma profundidad, el ganador será aleatorio.");
		for(int i = 0; i < 10; i++){
			List<Participante> part2 = Arrays.asList(p6, p7, p8, p9, p10);
			TorneoEstrategia t5 = new TorneoEstrategia(part2, l1, e);
			System.out.println("\tEl ganador del torneo es " +t5.ganador().getNombre());
		}
	}

}
