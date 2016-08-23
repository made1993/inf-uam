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
public class C4Heuristica {

    private final int MAXFILAS = 6;
    private final int MAXCOLUMNAS = 7;
    private int[][] tablero;
    //private int ganador;

    boolean Comprobar4EnLinea(int fila, int columna, int incFila, int incColumna, int n) {
        if (fila >= 0 && fila < MAXFILAS && columna >= 0 && columna < MAXCOLUMNAS) {
            int ficha = tablero[fila][columna];
            if(ficha == 0) return false;

            for (int i = 1; i < n; i++) {
                fila += incFila;
                columna += incColumna;

                if (fila >= 0 && fila < MAXFILAS && columna >= 0 && columna < MAXCOLUMNAS) {
                    if (tablero[fila][columna] != ficha) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    int ComprobarDiagonalDerecha(int fila, int columna, int n) {
        int num = 0;
        // Se prueban todas las combinaciones en las que esta involucrada la ficha recien puesta
        //for (int i = 0; i < 4; i++) {
            if (Comprobar4EnLinea(fila, columna, -1, -1, n)) {
                num++;
            }
            if (Comprobar4EnLinea(fila, columna, 1, 1, n)) {
                num++;
            } 
        //}
        return num;
    }
    
    int ComprobarDiagonalIzquierda(int fila, int columna, int n) {
        int num = 0;
        // Se prueban todas las combinaciones en las que esta involucrada la ficha recien puesta
        //for (int i = 0; i < 4; i++) {
            if (Comprobar4EnLinea(fila, columna, -1, 1, n)) {
                num++;
            }
            if (Comprobar4EnLinea(fila, columna, 1, -1, n)) {
                num++;
            }
        //}
        return num;
    }
    
    int ComprobarHorizontal(int fila, int columna, int n) {
        int num = 0;
        // Se prueban todas las combinaciones en las que esta involucrada la ficha recien puesta
            if (Comprobar4EnLinea(fila, columna, 0, -1, n)) {
                num++;
            }
            if(Comprobar4EnLinea(fila, columna, 0, 1, n)){
                num++;
            }
        return num;
    }
    
    int ComprobarVertical(int fila, int columna, int n) {
        int num = 0;
        // Se prueban todas las combinaciones en las que esta involucrada la ficha recien puesta
            if (Comprobar4EnLinea(fila, columna, -1, 0, n)) {
                num++;
            }
            if(Comprobar4EnLinea(fila, columna, 1, 0, n)){
                num++;
            }
        return num;
    }
    
    /**
     * @param t
     * @return 
     */
    public boolean game_over(int [][] t) {
        Integer tablas = 0;
        this.tablero = t;
  
        
        if(tablero[5][0] != 0 && tablero[5][1] != 0 && tablero[5][2] != 0 && tablero[5][3] != 0 && tablero[5][4] != 0 && tablero[5][5] != 0 && tablero[5][6] != 0) {
            tablas = 1;
        }
        return (ganador()) || (tablas==1);
        
    }
    public boolean ganador(int fila, int columna){
        return ComprobarVertical(fila, columna, 4) != 0 ||
                ComprobarHorizontal(fila, columna, 4) != 0 ||
                ComprobarDiagonalDerecha(fila, columna, 4) != 0 ||
                ComprobarDiagonalIzquierda(fila, columna, 4) != 0;
    }
    
    public boolean ganador(){
    	for(int fila = 0; fila < 6; fila++){
    		for(int columna = 0; columna < 7; columna++){
    			if(	ComprobarVertical(fila, columna, 4) != 0 ||
				ComprobarHorizontal(fila, columna, 4) != 0 ||
				ComprobarDiagonalDerecha(fila, columna, 4) != 0 ||
				ComprobarDiagonalIzquierda(fila, columna, 4) != 0) 
			return true;	
    		}
	}
	return false;
    }
    
    public int comprueba_linea(int n, int fila, int columna){
        int num_lineas = 0;
        
        num_lineas += ComprobarVertical(fila, columna, n);
        num_lineas += ComprobarHorizontal(fila, columna, n);
        num_lineas += ComprobarDiagonalDerecha(fila, columna, n);
        num_lineas += ComprobarDiagonalIzquierda(fila, columna, n);
        
        return num_lineas;
    }
    
    public void setFila(int f){
        this.fila = f;
    }
    
    public void setColumna(int c){
        this.columna = c;
    }

    void setTablero(int[][] tableroaux) {
        this.tablero = tableroaux;
    }
       
}

