#include <stdio.h>
#include "../includes/sslconexion.h"

int main(int argc, char ** argv){
	if (argv[1]==NULL)
		return 0;
	else if(strcmp(argv[1],"c")==0){
		cliente();
	}
	else if(strcmp(argv[1],"s")==0){
		servidor();
	}
	else
		return 0;
	return 1;
}