/*boolean igual(List<Integer> cuaterna, int valor, int n){
        if(cuaterna.size() != n) return false;
        for(int i = 0; i < n; i++){
            if(cuaterna.get(i) != valor)
                return false;
        }     
        return true;
    }
    
    
    public int comprueba_linea (int[][] tablero, int n, int jugador){
        ganador = 0;
        int num_lineas = 0;
        int lineas_posibles = 8-n;
        
        
        //Buscar linea horizontal
        for (int i = 0; i < 6; i++){
            for(int j = 0; j < lineas_posibles; j++){
                List<Integer> cuaterna = new ArrayList<Integer>();
                for(int k = j; k < j+n; k++){
                    cuaterna.add(tablero[i][k]);
                }
                if (igual(cuaterna, tablero[i][j], n) && tablero[i][j] != 0) {
                    ganador = tablero[i][j];
                    if (ganador == jugador) {
                        num_lineas++;
                    }
                }
            }
        }
        

        //Vertical
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < lineas_posibles; j++){
                List<Integer> cuaterna = new ArrayList<Integer>();
                for(int k = 0; k < n; k++){
                    if((j+k) < 6) cuaterna.add(tablero[j+k][i]);
                }
                if(igual(cuaterna, tablero[j][i], n) && tablero[j][i] != 0){
                    ganador = tablero[j][i];
                    if(ganador == jugador){
                        num_lineas++;
                    }
                }
            }
        }

        //Diagonal
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < lineas_posibles - i; j++){
                List<Integer> cuaterna1 = new ArrayList<Integer>();
                List<Integer> cuaterna2 = new ArrayList<Integer>();
                List<Integer> cuaterna3 = new ArrayList<Integer>();
                List<Integer> cuaterna4 = new ArrayList<Integer>();
                
                for(int k = 0; k < n; k++){
                    if((i+j+k) < 6 && (j+k) < 7) cuaterna1.add(tablero[i+j+k][j+k]);
                    if((k+j) < 6 && (j+k+i) < 7) cuaterna2.add(tablero[k+j][i+j+k]);
                    if((i+j+k) < 6 && (6-(j+k)) < 7) cuaterna3.add(tablero[i+j+k][6-(j+k)]);
                    if((j+k) < 6 && (6-(i+j+k)) < 7) cuaterna4.add(tablero[j+k][6-(i+j+k)]);
                }
                    
                    if(igual(cuaterna1, cuaterna1.get(0), n) && tablero[i+j][j] != 0){
                        ganador = tablero[i+j][j];
                        if(ganador == jugador) num_lineas++;
                    } else if(igual(cuaterna2, cuaterna2.get(0), n) && tablero[j][i+j] != 0){
                        ganador = tablero[j][i+j];
                        if(ganador == jugador) num_lineas++;
                    } else if(igual(cuaterna3, cuaterna3.get(0), n) && tablero[i+j][j] != 0){
                        ganador = tablero[i+j][j];
                        if(ganador == jugador) num_lineas++;
                    } else if(igual(cuaterna4, cuaterna4.get(0), n) && tablero[j][6-(i+j)] != 0){
                        ganador = tablero[j][6-(i+j)];
                        if(ganador == jugador) num_lineas++;
                    }
                
            }
        }
        
        return num_lineas;
    }*/
