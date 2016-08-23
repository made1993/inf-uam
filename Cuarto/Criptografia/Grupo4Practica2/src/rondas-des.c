

/*Cuenta los bits de diferencia entre n1 y n2*/
int dHamming(uint64_t n1, uint64_t n2){
	int i=0;
	int count=0;
	for(; i<64; i++, n1>>=1, n2>>=1){
		if( (n1&1) ^ (n2&1) )
			count++; 
		
	}

	return count;
}

uint64_t desRondas(int rondas, uint64_t M,uint64_t K, int c){
	uint64_t L , R, C;
	int i=0;
	uint64_t* Ks=  generarClaves(K);
	uint64_t* Ks2 = (uint64_t *)malloc(16 * sizeof(uint64_t));

	if ( !c){
		for (i = 0; i < ROUNDS; i++)
			Ks2[i]  = Ks[ROUNDS -1 -i];
		free(Ks);
		Ks = Ks2;

	}
	M = permutacion(M, IP, 64, 64);
	L = (M & superior64) >> 32;
	R = M & inferior64;	
	//printf("inicial R=%lx  L=%lx\n",L,R);
	for(i=0; i < rondas; i++){

		C=permutacion(R, E,32,48);
	//	printf("expansion C = %lx\n",C );
		C^=Ks[i];
	//	printf("ks[i] xor C = %lx\n",C );
		C=sbox(C);
	//	printf("sbox C = %lx\n",C );
		C= permutacion(C,P,32,32);
	//	printf("permutacion P = %lx\n",C );
		C=C^L;
	//	printf("L xor C = %lx\n",C );
		L=R;
		R=C;
		//printf("R=%lx  L=%lx\n\n",R,L);
	}

	M = R << 32;
	M |= L;
	M=permutacion(M,IP_INV,64,64);

	//printf("M=%lx\n",M );
	free(Ks);
	return M;
}

int* desRondasF(int rondas, uint64_t M,uint64_t K, int c){
	uint64_t L , R, C;
	int i=0;
	int *distanciasH;
	distanciasH = (int*)malloc(16 * sizeof(int));
	uint64_t* Ks=  generarClaves(K);
	uint64_t* Ks2 = (uint64_t *)malloc(16 * sizeof(uint64_t));

	if ( !c){
		for (i = 0; i < ROUNDS; i++)
			Ks2[i]  = Ks[ROUNDS -1 -i];
		free(Ks);
		Ks = Ks2;

	}
	M = permutacion(M, IP, 64, 64);
	L = (M & superior64) >> 32;
	R = M & inferior64;	
	//printf("inicial R=%lx  L=%lx\n",L,R);
	for(i=0; i < rondas; i++){

		C=permutacion(R, E,32,48);
		C^=Ks[i];
		C=sbox(C);
		C= permutacion(C,P,32,32);
		// CALCULA LA DISTANCIAH ENTRE R Y SALIDA SBOX PASADA POR PERM P
		distanciasH[i] = dHamming(R, C);
		C=C^L;
		L=R;
		R=C;
		//printf("R=%lx  L=%lx\n\n",R,L);
	}

	M = R << 32;
	M |= L;
	M=permutacion(M,IP_INV,64,64);

	//printf("M=%lx\n",M );
	free(Ks);
	return distanciasH;
}


int main (){

	uint64_t m=0x0123456789abcdef, k=0x133457799bbcdff1,* salidas;
	int i=0;
	int * distanciasH;
	FILE * fout= stdout;
	salidas=(uint64_t*) calloc(32, sizeof(uint64_t));
	printf("Bits de cambio en el mensaje completo para cada número de rondas\n");
	for (i=1; i<=32 ; i++){
		salidas[i-1]=desRondas(i, m, k, 1);
	}
	for (i=0; i<32;i++){
		fprintf(fout, "%d,", dHamming(m, salidas[i]));
	}
	distanciasH = desRondasF(16, m, k, 1);
	printf("\nBits de cambio entre la entrada a la sbox previa permutacion y la salida de la función f previa xor para cada ronda\n");
	for(i = 0; i < 16; i++) {
		fprintf(fout, "%d,", distanciasH[i]);
	}
	printf("\n");
	free(distanciasH);
	
}
