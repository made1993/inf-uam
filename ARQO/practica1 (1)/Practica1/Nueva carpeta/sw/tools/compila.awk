BEGIN{
	procesaD=0;
	procesaI=0;
	fI="instrucciones";
	fD="datos";
}

{
	if( $4 ~ /\..+:/ )
	{
		if( $4 == ".data:" )
		{
			procesaD=1;
			procesaI=0;
		}
		else if( $4 == ".text:" )
		{
			procesaD=0;
			procesaI=1;
		}
		else
		{
			procesaD=0;
			procesaI=0;
		}
	}
	else if( procesaD == 1 )
	{
		num=strtonum("0x" $1);
		aux=sprintf("%08x\t%s", num, $2)
		print aux >> fD;
		if( $3 ~ /[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]/ )
		{
			aux=sprintf("%08x\t%s", num +4, $3);
			print aux >> fD;
			if( $4 ~ /[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]/ )
			{
				aux=sprintf("%08x\t%s", num +8, $4);
				print aux >> fD;
				if( $5 ~ /[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]/ )
				{
					aux=sprintf("%08x\t%s", num +12, $5);
					print aux >> fD;
				}
			}
		}
	}
	else if( procesaI == 1)
	{
		num=strtonum("0x" $1);
		aux=sprintf("%08x\t%s", num, $2);
		print aux >> fI;
		if( $3 ~ /[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]/ )
		{
			aux=sprintf("%08x\t%s", num +4, $3);
			print aux >> fI;
			if( $4 ~ /[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]/ )
			{
				aux=sprintf("%08x\t%s", num +8, $4);
				print aux >> fI;
				if( $5 ~ /[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]/ )
				{
					aux=sprintf("%08x\t%s", num +12, $5);
					print aux >> fI;
				}
			}
		}
	}
}
