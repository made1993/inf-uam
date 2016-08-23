package es.uam.ads.p5.tests;

import java.util.List;
import java.util.Random;

import es.uam.ads.p5.AI.MiniMax;
import es.uam.ads.p5.juego.Heuristica4Raya;
import es.uam.ads.p5.juego.Juego4Raya;
import es.uam.ads.p5.juego.Jugador;
import es.uam.ads.p5.juego.Movimiento;
import es.uam.ads.p5.juego.Tablero;

public class PruebaCuatroEnRaya {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        

        // TODO code application logic here
        
    	Juego4Raya jrt;
    	Jugador player;
    	Tablero t;
		Heuristica4Raya hrt;
		MiniMax<Tablero, Jugador, Movimiento, Juego4Raya, Heuristica4Raya> m;
        int ganadas = 0;
        int perdidas = 0;
        
        int columna = 3;
        int fila = 0;
        int col;
        int prof = 5;

        int k;
        
        for(k = 0; k < 20; k++){
        	jrt = new Juego4Raya();
			player = Jugador.NEGRO;
			t = new Tablero();
			hrt = new Heuristica4Raya();

            while(!jrt.esFinal(t, Jugador.NEGRO) && !jrt.esFinal(t, Jugador.BLANCO)){
                /*Jugador 1*/
            	Movimiento mov;
            	m = new MiniMax<Tablero, Jugador, Movimiento, Juego4Raya, Heuristica4Raya> (jrt, hrt);
                mov = m.mejorMovimiento(t, player, prof);
                t = jrt.estadoResultante(t, mov);
                
                //mostrar(tablero);
                if(t.esGanador(player)){
                    ganadas++;
                } else{
                    /*Jugador -1*/
                    Random rand = new Random();
                    List<Movimiento> posiblesMovimientos = jrt.posiblesMovimientos(t, Jugador.BLANCO);
                    columna = rand.nextInt(posiblesMovimientos.size());
                    Movimiento aleat = posiblesMovimientos.get(columna);
                    t = jrt.estadoResultante(t, aleat);
                    
                    if(t.esGanador(Jugador.BLANCO)){
                        perdidas++;
                        
                        System.out.println(t.toString());
                    }
                }
            }
        }
        System.out.println(ganadas+" "+perdidas);
    }
    
    static void mostrar(int[][] tablero) {
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                System.out.print(tablero[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}