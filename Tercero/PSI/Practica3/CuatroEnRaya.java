/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuatro.en.raya;

import java.util.ArrayList;

/**
 *
 * @author ccpp
 */
public class CuatroEnRaya {

    /**
     * @param args the command line arguments
     */
    ArrayList<Integer> tablero;
    public static boolean game_over(int [][] tablero) {
        Integer i=0, j=0;
        for(j=0;j<7;i++){
            if(tablero[0][j]!=0){
                i=1;
            }
        }
        if(ganador(tablero)&&(i==0)){
            return false;    
        }
        else{
            return true;
        }
        
    }
    public static boolean ganador(int [][]tablero ){
        return comprueba_linea(tablero, 4,0);
    }
    public static boolean comprueba_linea(int [][]tablero,int i, int j){
        return true;
    } 
    
}
