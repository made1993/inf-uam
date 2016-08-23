/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebacuatroenraya;

import java.util.Random;

/**
 *
 * @author skynet
 */
public class PruebaCuatroEnRaya {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        

        // TODO code application logic here
        
        NegaMax ng;
        C4Heuristica c4h = new C4Heuristica();
        int ganadas = 0;
        int perdidas = 0;
        int[][] tablero;
        
        int columna = 3;
        int fila = 0;

        int negamax;
        int col;

        int k;
        
        for(k = 0; k < 100; k++){
            c4h = new C4Heuristica();
            ng = new NegaMax(c4h);
            tablero = new int[6][7];

            while(!ng.gameover(tablero, 1) && !ng.gameover(tablero, -1)){
                /*Jugador 1*/
                columna = ng.negamax(tablero, 1, 1, Integer.MIN_VALUE+1, Integer.MAX_VALUE, fila, columna).get(1);
                fila = ng.inserta_ficha(tablero, columna, 1);
                
                //mostrar(tablero);
                if(ng.gameover(tablero, 1)){
                    ganadas++;
                } else{
                    /*Jugador -1*/
                    Random rand = new Random();
                    columna = rand.nextInt(7);
                    fila = ng.inserta_ficha(tablero, columna, -1);
                    
                    if(c4h.ganador(fila, columna)){
                        perdidas++;
                        
                        mostrar(tablero);
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
           
