public List<Integer> negamax(int[][] tablero, int profundidad, int jugador, int alpha, int beta){
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
			//mejor_jugada = jugada;
		}
		/*if(val > alpha){
			alpha = val;
		}
		if(alpha >= beta){
			break;
		}*/
		
		if(bestValue >= beta){
			retorno.add(bestValue);
			retorno.add(mejor_jugada);
			return retorno;
		} else{
			if(bestValue > alpha){
				alpha = bestValue;
			}
			mejor_jugada = jugada;
			
			retorno.add(bestValue);
			retorno.add(mejor_jugada);
			return retorno;
		}
	}
	
	/*retorno.add(bestValue);
	retorno.add(mejor_jugada);
	return retorno;*/
}
