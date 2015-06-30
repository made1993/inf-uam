/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebacuatroenraya;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author skynet
 */
public class NegaMax {   
    private final C4Heuristica c4h;
    
    public NegaMax (C4Heuristica c4h){
        this.c4h = c4h;
    }
    


    public int inserta_ficha(int[][] tablero, int jugada, int jugador) {
        for(int i = 0; i < 6; i++){
            if(tablero[i][jugada] == 0){
                tablero[i][jugada] = jugador;
                return i;
            }
        }
        return -1;
    }
    
    
    

    public boolean gameover(int[][] tablero, int jugador) {
        return c4h.game_over(tablero, jugador);
    }

    private int evalua_jugada(int fila, int columna) {
        //c4h.setTablero(tablero);
        int n2 = c4h.comprueba_linea(2, fila, columna);
        int n3 = c4h.comprueba_linea(3, fila, columna);
        int n4 = c4h.comprueba_linea(4, fila, columna);
        
        return 4 * n2 + 9 * n3 + 1000 * n4;
    }
    
    private int[][] copiar(int[][] tablero) {
        int[][] aux = new int[6][7];
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                aux[i][j] = tablero[i][j];
            }
        }
        return aux;
    }
    
    private boolean esVacio(int[][] t){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                if (t[i][j] != 0) return false;
            }
        }
        return true;
    }
    
    
    /*public List<Integer> negamax(int[][] tablero, int profundidad, int jugador, int alpha, int beta){
	List<Integer> retorno = new ArrayList<Integer>();
        int mejor_jugada = 3;
	int bestValue = Integer.MIN_VALUE;

	if(profundidad == 0 || gameover(tablero) == true){
		retorno.add(jugador * evalua_jugada(tablero, jugador));
		retorno.add(mejor_jugada);
		return retorno;
	}
	
	
	
	for(int jugada = 0; jugada < 7; jugada++){
                int[][] tableroaux = copiar(tablero);
            
		int val = -negamax(tableroaux, profundidad-1, -beta, -alpha, -jugador).get(0);
		
		if(val > bestValue){
			bestValue = val;
			mejor_jugada = jugada;
		}
		if(val > alpha){
			alpha = val;
		}
		if(alpha >= beta){
			break;
		}
	}
	
	retorno.add(bestValue);
	retorno.add(mejor_jugada);
	return retorno;
}*/
    
    /*public List<Integer> negamax(int[][] tablero, int profundidad, int jugador, int alpha, int beta, int fila, int columna) {
        List<Integer> retorno = new ArrayList<Integer>();
        int mejor_jugada = 3;
        int bestValue = Integer.MIN_VALUE;
        int[][] tableroaux;

        if (profundidad == 0 || gameover(tablero, -jugador) || gameover(tablero, jugador)) {
            c4h.setTablero(tablero);
            retorno.add(evalua_jugada(fila, columna));
            retorno.add(mejor_jugada);
            return retorno;
        }

        for (int jugada = 0; jugada < 7; jugada++) {
	    tableroaux = copiar(tablero);
            fila = inserta_ficha(tableroaux, jugada, jugador);

            int val = -negamax(tableroaux, profundidad - 1, -beta, -alpha, -jugador, fila, jugada).get(0);

            if(val == 0) val = Integer.MIN_VALUE;
            if (val > bestValue) {
                bestValue = val;
                mejor_jugada = jugada;
            }

            if (bestValue >= beta) {
                retorno.add(bestValue);
                retorno.add(mejor_jugada);
                return retorno;
            } else {
                if (bestValue > alpha) {
                    alpha = bestValue;
                }         
            }
        }

        retorno.add(bestValue);
        retorno.add(mejor_jugada);
        return retorno;
    }*/
    
    public List<Integer> negamax(int[][] tablero, int profundidad, int jugador, int alpha, int beta, int fila, int columna) {
        List<Integer> retorno = new ArrayList<Integer>();
        int mejor_jugada = 3;
        int bestValue = Integer.MIN_VALUE;
        int[][] tableroaux;

        if (profundidad == 0 || gameover(tablero, jugador) || gameover(tablero, -jugador)) {
            c4h.setTablero(tablero);
            retorno.add(jugador*evalua_jugada(fila, columna));
            retorno.add(mejor_jugada);
            return retorno;
        }

        for (int jugada = 0; jugada < 7; jugada++) {
	    tableroaux = copiar(tablero);
            fila = inserta_ficha(tableroaux, jugada, jugador);

            int val = -negamax(tableroaux, profundidad - 1, -jugador, -beta, -alpha, fila, jugada).get(0);

            if(val == 0) val = Integer.MIN_VALUE;
            if (val > bestValue) {
                bestValue = val;
                mejor_jugada = jugada;
            }

            if (bestValue >= beta) {
                retorno.add(bestValue);
                retorno.add(mejor_jugada);
                return retorno;
            } else {
                if (bestValue > alpha) {
                    alpha = bestValue;
                }   
            }
        }

        retorno.add(bestValue);
        retorno.add(mejor_jugada);
        return retorno;
    }
    
}


