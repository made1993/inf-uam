int comp_horz(int tab[6][7], int jugador, int fila,int countv){
	int i;

	for(i=0;i<7;i++){
			if(tab[fila][i]==jugador){
				countv++;
				if(countv==4){
					return 0;
				}
			}
			else{
				countv=0;
			}
		}
		return-1;
}
int comp_ver(int tab[6][7], int jugador, int col,int countv){
	int i;
	for(i=0;i<6;i++){
			if(tab[i][col]==jugador){
				countv++;
				if(countv==4){
					return 0;
				}
			}
			else{
				countv=0;
			}
		}
		return-1;
}
int comp_diad(int tab[6][7], int jugador,int countv){
	int i,j,fila,col;
	for(i=0,fila=i;i<6;i++, fila=i){
		for(j=0,col=j;j<7;j++,col=j){
			printf("fila:%d col:%d\n",fila,col);
			while(tab[fila][col]==jugador){
				countv++;
				printf("fila:%d col:%d countv:%d\n",fila,col,countv);
				if(countv==4){
					return 0;
				}
				col++;
				fila++;
			}
			countv=0;
		}
	}
	return-1;
}
int comp_diai(int tab[6][7], int jugador, int countv){

	int i,j,fila,col;
	for(i=0,fila=i;i<6;i++, fila=i){
		for(j=0,col=j;j<7;j++,col=j){
			printf("fila:%d col:%d\n",fila,col);
			while(tab[fila][col]==jugador){
				countv++;
				printf("fila:%d col:%d countv:%d\n",fila,col,countv);
				if(countv==4){
					return 0;
				}
				col--;
				fila++;
			}
			countv=0;
		}
	}
	return-1;
	
}
int vct2 (int tab[6][7], int jugador){
	int i=0,j=0;
	printf("comp_ver\n");
	for(i=0;i<7;i++){
		if(comp_ver(tab,jugador,i,0)==0){
			return 0;
		}
	}
	printf("comp_horz\n");
	for(i=0;i<6;i++){
		if(comp_horz(tab,jugador,i,0)==0){
			return 0;
		}
	}
	printf("comp_diai\n");
	if(comp_diai(tab,jugador,0)==0){
		return 0;
	}
		
	printf("comp_diad\n");
	if(comp_diad(tab,jugador,0)==0){
		return 0;
	}
}
