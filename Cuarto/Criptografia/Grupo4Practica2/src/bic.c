


void main (){
	uint64_t  salida1=0, salida2=0, aux=1, masc=0, entrada=0;
	int i=0, j=0, k=0, l=0, m=0, count=0;
	unsigned int** frecs=NULL,** frecs2=NULL;
	frecs = (unsigned int**) malloc(sizeof(int*)*4);
	frecs2 = (unsigned int**) malloc(sizeof(int*)*12);
	for(i=0; i<4; i++)
		frecs[i]= (unsigned int*) calloc(2, sizeof(int));
	
	for(i=0; i<12;i++){
		frecs2[i]=(unsigned int*) calloc(2, sizeof(int));
	}

	
	/*Para una sbox, probamos todos los comlementos de un bit*/
	for (l=0; l<64; l++, entrada++){ /* 64 ya que son todos los posibles valores de un numero de 6 bits*/
	

		//for (i=0; i<6; i++){
			/* se complmenta cualquier posible bit*/
			salida1 = sbox(entrada  ^ aux << i); 


			/*Comprobamos que bits valen 1*/
			
			for(j=0; j<4; j++){
				if (((salida1 >> j) & 1) == 1)
					frecs[j][1]++;
				else
					frecs[j][0]++;
			}

			/*Comprobamos que pares de bits valen 11*/

			for(j=0, count=0; j<4; j++){
				for(k=0; k<4; k++, masc=0){


					if(j==k) /*Para que no coja los pares 00,11,22,33*/
						continue;
					masc= 1<< j | 1<< k;
					//printf("%d: %lx ? %lx\n", count, salida1 & masc,  masc);
					if ((salida1 & masc) == masc){
						frecs2[count][1]++;
					}
					else{
						frecs2[count][0]++;
					}
					count++;

				}
			}
			

		//}

		
	}
	for(i=0, j=0, k=0; i<12; i++, k = (k+1) % 4){
		if(j==k)
			k++;
		
		printf("bits %d%d, p(%d%d)=%f == ", j, k, j, k, frecs2[i][1]*1.0/ (frecs2[i][0]+frecs2[i][1]));
		printf ("p(%d)*p(%d):%f=", j, k,  frecs[j][1]*1.0/(frecs[j][1]+frecs[j][0])* frecs[k][1]*1.0/(frecs[k][1]+frecs[k][0]));
		printf("-> p(%d):%f   p(%d):%f\n",j, frecs[j][1]*1.0/(frecs[j][1]+frecs[j][0]), k, frecs[k][1]*1.0/(frecs[k][1]+frecs[k][0]) );
		if(0 == (k+1)%4){
			j++;
			printf("\n");
		 }
	}	
}
