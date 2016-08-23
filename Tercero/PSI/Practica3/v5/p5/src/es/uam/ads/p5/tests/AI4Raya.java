package es.uam.ads.p5.tests;

import java.util.Random;

import es.uam.ads.p5.AI.MiniMax;
import es.uam.ads.p5.juego.Heuristica4Raya;
import es.uam.ads.p5.juego.Juego4Raya;
import es.uam.ads.p5.juego.Jugador;
import es.uam.ads.p5.juego.Movimiento;
import es.uam.ads.p5.juego.Tablero;

public class AI4Raya {
	public static void main (String[] argv){
		Juego4Raya jrt;
		Jugador player;
		Tablero t;
		Heuristica4Raya hrt;
		MiniMax<Tablero, Jugador, Movimiento, Juego4Raya, Heuristica4Raya> m;
		Random rand = new Random();

		int empates = 0;
		int ganadas = 0;
		int perdidas = 0;
		for(int i = 3; i <= 4; i++){
			for(int j = 1; j <= 1; j++){
				for(int n = 0; n < 1; n++){
					jrt = new Juego4Raya();
					player = Jugador.NEGRO;
					t = new Tablero();
					hrt = new Heuristica4Raya();
					int prof;
					Movimiento mov;
					m = new MiniMax<Tablero, Jugador, Movimiento, Juego4Raya, Heuristica4Raya> (jrt, hrt);
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
