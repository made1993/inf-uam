
uint64_t byteSub(uint64_t in){
	int i=0, row=0, column=0;
	
	uint64_t out=0;
	
	for(i=7; i>=0; i--){
		
		row = in >> i*8;
		row &= 0xf0;
		row >>= 4; 
		
		column = in >> i*8;
		column &= 0xf;
		printf("r:%d c:%d\n", row, column);
		out |= DIRECT_SBOX[row][column];
		if(i>0)
			out <<= 8;
	}
	return out;
}

uint64_t byteSubInv(uint64_t in){
	int i=0, row=0, column=0;
	
	uint64_t out=0;

	for(i=7; i>=0; i--){
		
		row = in >> i*8;
		row &= 0xf0;
		row >>= 4; 
		
		column = in >> i*8;
		column &= 0xf;

		printf("r:%d c:%d\n", row, column);
		out |= INVERSE_SBOX[row][column];
		if(i>0)
			out <<= 8;
	}
	return out;
}


uint8_t getSBox(uint8_t index) {
	/*Devolver la posicion correspondiente.*/
	return (*DIRECT_SBOX)[index];
}
uint8_t getInvSBox(uint8_t index) {
	/*Devolver la posicion correspondiente.*/
	return (*INVERSE_SBOX)[index];
}


