

void swap(uint64_t *a, uint64_t *b){
	uint64_t c;
	c = *a;
	*a = *b;
	*b = c;
}

/*Compilar con DES_tables.c para las constantes*/
/**gcc -o main des.c -include (sitio donde este esto).h*/


uint64_t permutacion(uint64_t entrada, const unsigned short *tabla, int sizeEntrada, int sizeTabla){
	
	int i;
	int desplazamiento;	
	uint64_t primerbit;
	uint64_t mascara;
	uint64_t salida = 0x0;
	//ajustar para expansion:
	if(sizeEntrada < sizeTabla){
		entrada <<= (sizeTabla - sizeEntrada);
		sizeEntrada += (sizeTabla - sizeEntrada);
	}
	primerbit = (uint64_t)1 << (sizeEntrada -1);

	for(i = 0; i < sizeTabla; i++){
		desplazamiento = (int) tabla[i];
		mascara = primerbit >> (desplazamiento - 1);
		desplazamiento = desplazamiento -i -1;
		if(desplazamiento > 0){

			salida = salida | ((entrada & mascara) << (desplazamiento));
			
		}
		else {
			salida = salida | ((entrada & mascara) >> (-desplazamiento));
		}
	}
	//Ajustar hacia la derecha para reduccion
	if(sizeEntrada > sizeTabla){
		salida >>= (sizeEntrada - sizeTabla);
	}
	return salida;
}

uint64_t sbox(uint64_t entrada){
	
	int i;
	int desplazamiento;	
	uint64_t temporal;
	uint64_t mascara = 0xFC0000000000;
	uint64_t mascaraF1 = 0x800000000000;
	uint64_t mascaraF2 = 0x040000000000;
	uint64_t mascaraC = 0x7C0000000000;
	uint64_t salida = 0x0;
	uint64_t fila, fila1, fila2, columna;

	for(i = 0; i < NUM_S_BOXES; i++){
		

		temporal = entrada & mascara;
		
		/*Obtener fila*/
		fila1 = temporal & mascaraF1;							//Bit de mas peso 				 X _ _ _ _ _
		fila2 = temporal & mascaraF2;							//Bit de menor peso 			 _ _ _ _ _ Y
		fila1 >>= 4; 											//Desplazar para juntar los bits _ _ _ _ X _
		fila = (fila1 | fila2) >> SBOX_BLOCK_SIZE * (7 - i); 	//And + desplazo				 _ _ _ _ X Y


		columna = temporal & mascaraC; 							//Obtener columna				 _ W X Y Z _
		columna >>= SBOX_BLOCK_SIZE * (7 - i) + 1;		   		//Desplazar al principio

		//printf("%lx, %lx\n", fila, columna);
		//printf("%lx\n", S_BOXES[i][fila][columna]);
		salida |= S_BOXES[i][fila][columna];					//Or a la salida del resultado de la sbox
		//printf("Salida %lx\n", salida);
		salida <<= 4;											//desplazar la salida (hacer hueco siguiente)

		/*Ajustar mascaras para la siguiente iteracion*/
		mascara >>= SBOX_BLOCK_SIZE;
		mascaraF1 >>= SBOX_BLOCK_SIZE;
		mascaraF2 >>= SBOX_BLOCK_SIZE;
		mascaraC >>= SBOX_BLOCK_SIZE;
			
	}
	salida >>= 4;												//Deshacer ultimo desplazamiento
	return salida;
}

uint64_t rotate(uint64_t x, int rotation, int size, uint64_t mask) {

	return mask & ((x << rotation) | (x >> (size - rotation)));
}

uint64_t* generarClaves(uint64_t clave){
	
	int i;
	uint64_t k;
	uint64_t c;
	uint64_t d;
	uint64_t rotato1;
	uint64_t rotato2;
	uint64_t *subkeys;

	subkeys = (uint64_t *) malloc(ROUNDS * sizeof(uint64_t));
	//Aplicar permutacion PC1
	k = permutacion(clave, PC1, 64, 56);
	//k = 0b11110000110011001010101011110101010101100110011110001111;
	//Dividir la clave de 56 bits en 2 partes de 28.
	c = (k & superior56) >> 28;
	d = k & inferior56;
	
	for(i = 0; i < ROUNDS; i++) {

		c = rotate(c, ROUND_SHIFTS[i], 28, inferior56);
		d = rotate(d, ROUND_SHIFTS[i], 28, inferior56);
		
		k = c << 28 | d; //Concatenar 28 + 28 -> 56
		//Permutar PC2
		subkeys[i] = permutacion(k, PC2, 56, 48);
		//k = 0b000110110000001011101111111111000111000001110010;
	}

	return subkeys;
}


	/*	
	** cifrado c=1
	* descifrado c=0
	*/
uint64_t des(uint64_t M,uint64_t K, int c){
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
	for(i=0; i < ROUNDS; i++){

		C=permutacion(R, E,32,48);

		C^=Ks[i];
		C=sbox(C);

		C= permutacion(C,P,32,32);

		C=C^L;
		L=R;
		R=C;
	}

	M = R << 32;
	M |= L;
	M=permutacion(M,IP_INV,64,64);

	//printf("M=%lx\n",M );
	free(Ks);
	return M;
}

