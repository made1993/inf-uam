package es.uam.ads.p5.tests;

import es.uam.ads.p5.AI.MiniMax;
import es.uam.ads.p5.juego.Heuristica3Raya;
import es.uam.ads.p5.juego.Juego3Raya;
import es.uam.ads.p5.juego.Jugador;
import es.uam.ads.p5.juego.Movimiento;
import es.uam.ads.p5.juego.Tablero;

public class AI3Raya {
	public static void main (String[] argv){
		Juego3Raya jrt;
		Jugador player;
		Tablero t;
		Heuristica3Raya hrt;
		MiniMax<Tablero, Jugador, Movimiento, Juego3Raya, Heuristica3Raya> m;

		int empates = 0;
		int ganadas = 0;
		int perdidas = 0;
		for(int i = 1; i <= 6; i++){
			for(int j = 1; j <= 6; j++){
				for(int n = 0; n < 5; n++){
					jrt = new Juego3Raya();
					player = Jugador.NEGRO;
					t = new Tablero();
					hrt = new Heuristica3Raya();
					int prof;
					Movimiento mov;
					m = new MiniMax<Tablero, Jugador, Movimiento, Juego3Raya, Heuristica3Raya> (jrt, hrt);
					do {
						player = jrt.cambioTurno(player);
						prof = (player == Jugador.BLANCO) ? i : j;
						mov = m.mejorMovimiento(t, player, prof);
						t = jrt.estadoResultante(t, mov);
					} while (!jrt.esFinal(t, player));
					if (jrt.esGanador(t, player)) {
						if(player == Jugador.BLANCO){
							ganadas++;
						} else{
							perdidas++;
						}
					} else {
						empates++;
					}
				}
				System.out.println("El jugador de profundidad " +i+ " ha ganado "+ganadas+ " veces, perdido " +perdidas+ " y empatado " +empates+ " contra el jugador de profundidad " +j + ".");
				empates = 0;
				perdidas = 0;
				ganadas = 0;
			}
		}
	}

}
