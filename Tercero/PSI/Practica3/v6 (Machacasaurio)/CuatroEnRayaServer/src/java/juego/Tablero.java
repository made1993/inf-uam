package juego;

import java.util.*;

/**
 * Clase que representa un tablero de las tres en raya
 * Un tablero contiene una matriz 3x3 de celdas
 */
public class Tablero implements Cloneable{
	/**
	 * Matriz con las celdas
	 */
	private final int MAXFILAS = 6;
    private final int MAXCOLUMNAS = 7;
	private Celda[][] celdas = new Celda[MAXFILAS][MAXCOLUMNAS];
	
	
	/**
	 * Crea celdas del tablero
	 */
	public Tablero() {
		for (int i = 0; i<MAXFILAS; i++)
			for (int j = 0;j<MAXCOLUMNAS; j++)
				this.celdas[i][j] = new Celda(i, j);
	}
	
	/**
	 * A�ade la ficha f a la celda con posici�n x, y. Para ello comprueba que
	 * la celda est� vac�a
	 * @param f : fila 
	 * @param c : columna
	 * @param ficha : ficha
	 * @return true si se ha podido a�adir, false en caso contrario
	 */
	public boolean addFicha(int f, int c, Ficha ficha) {
		if (this.celdas[f][c].esVacia()) {
			this.celdas[f][c].addFicha(ficha);
			return true;
		}
		return false;
	}
	
	/**
	 * Comprueba si el Jugador j es el ganador, viendo si tiene tres en raya en horizontal,
	 * vertical o diagonal
	 * @param j : Jugador
	 * @return true, si el jugador es ganador
	 */
	public boolean esGanador(Jugador j) {
		/*for (int linea = 0; linea < 3; linea ++ )
			if (this.tiene3RayaHorizontal(linea, j) || this.tiene3RayaVertical(linea, j)) return true;
		return this.tiene3RayaDiagonales(j);*/
		
		for(int fila = 0; fila < MAXFILAS; fila++){
    		for(int columna = 0; columna < MAXCOLUMNAS; columna++){
    			if(	(ComprobarVertical(fila, columna, 4) != 0 ||
				ComprobarHorizontal(fila, columna, 4) != 0 ||
				ComprobarDiagonalDerecha(fila, columna, 4) != 0 ||
				ComprobarDiagonalIzquierda(fila, columna, 4) != 0)  &&
                                celdas[fila][columna].getColor() == j)
			return true;	
    		}
		}
	return false;
	}
	
	/**
	 * Devuelve el Jugador ganador del juego, o null si no hay ganador
	 * @return el Jugador ganador del juego, o null si no hay ganador
	 */
	public Jugador ganador() {
		if (this.esGanador(Jugador.BLANCO)) return Jugador.BLANCO;
		if (this.esGanador(Jugador.NEGRO)) return Jugador.NEGRO;
		return null;
	}
	
	/**
	 * Obtiene una lista con las celdas libres
	 * @return una lista con las celdas libres
	 */
	public List<Celda> getCeldasLibres() {
		List<Celda> libres = new ArrayList<Celda>();
		for (int i = 0; i<MAXFILAS; i++)
			for (int j = 0;j<MAXCOLUMNAS; j++)
				if (this.celdas[i][j].esVacia()) libres.add(this.celdas[i][j]);
		return libres;
	}
	
	/**
	 * Realiza una copia del tablero
	 */
	public Tablero clone() {
		Tablero copia = new Tablero();
		for (int i = 0; i < MAXFILAS; i ++) 
			for (int j= 0; j<MAXCOLUMNAS; j++)
				copia.celdas[i][j] = this.celdas[i][j].clone();
		return copia;
	}
	
	/**
	 * Realiza un movimiento en el tablero
	 * @param mv Movimiento a efectuar
	 */
	public void mover(Movimiento mv) {
		this.addFicha(mv.getFila(), mv.getCol(), new Ficha(mv.getJugador()));
	}
	
	/**
	 * Comprueba si el juego es empate (no hay ganador ni casillas libres)
	 * @return true si es empate
	 */
	public boolean esEmpate() {		
		return this.ganador()==null && this.getCeldasLibres().size()==0;
	}
	
	@Override public String toString() {
		String tab = "";
		
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < MAXCOLUMNAS; j++)
				tab += (this.celdas[i][j].getColor()==null) ? "-" : this.celdas[i][j].getColor();
			tab+="\n";
		}
		return tab;
	}	
	
	//------------------------------------------------------------------- metodos auxiliares
	
	/**
	 * Chequea si el Jugador j tiene un tres en raya por las diagonales
	 * @param j
	 * @return
	 */
	private boolean tiene3RayaDiagonales(Jugador j) {	
		return (this.celdas[0][0].getColor() == j&&
			    this.celdas[1][1].getColor() == j&&
			    this.celdas[2][2].getColor() == j) ||
			    (this.celdas[0][2].getColor() == j&&
			     this.celdas[1][1].getColor() == j&&
			     this.celdas[2][0].getColor() == j);
	}
	
	/**
	 * Chequea si el Jugador j tiene un tres en raya en la fila "fila"
	 * @param j
	 * @return
	 */
	private boolean tiene3RayaHorizontal(int fila, Jugador j) {
		return this.celdas[fila][0].getColor() == j&&
			   this.celdas[fila][1].getColor() == j&&
			   this.celdas[fila][2].getColor() == j;
	}
	
	/**
	 * Chequea si el Jugador j tiene un tres en raya en la columna "column"
	 * @param j
	 * @return
	 */
	private boolean tiene3RayaVertical(int colum, Jugador j) {
		return this.celdas[0][colum].getColor() == j&&
			   this.celdas[1][colum].getColor() == j&&
			   this.celdas[2][colum].getColor() == j;
	}
	
	public Celda getCelda(int f, int c) {
	     if (0 <= f && f < MAXFILAS && 0 <= c && c < MAXCOLUMNAS) return this.celdas[f][c];
	     else return null;
	}
	
	boolean Comprobar4EnLinea(int fila, int columna, int incFila, int incColumna, int n) {
        if (fila >= 0 && fila < MAXFILAS && columna >= 0 && columna < MAXCOLUMNAS) {
            Jugador ficha = celdas[fila][columna].getColor();
            if(ficha == null) return false;

            for (int i = 1; i < n; i++) {
                fila += incFila;
                columna += incColumna;

                if (fila >= 0 && fila < MAXFILAS && columna >= 0 && columna < MAXCOLUMNAS) {
                    if (celdas[fila][columna].getColor() != ficha) {
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
    
    int ComprobarTablero(int n, Jugador j){
    	int num = 0;
    	
    	for(int fila = 0; fila < MAXFILAS; fila++){
    		for(int columna = 0; columna < MAXCOLUMNAS; columna++){
    			if(celdas[fila][columna].getColor() == j){
    				num += ComprobarVertical(fila, columna, n);
    				num += ComprobarHorizontal(fila, columna, n);
    				num += ComprobarDiagonalDerecha(fila, columna, n);
    				num += ComprobarDiagonalIzquierda(fila, columna, n);
    			}
    		}
		}
    	return num;
    }
}

