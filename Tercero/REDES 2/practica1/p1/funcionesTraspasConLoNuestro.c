

int iniciarServidorTCP(){
	int sockfd;

	sockfd = abrirSocketTCP();/*abrimos socket TCP*/
	if ( sockfd == -1 ){
		fprintf(stderr, "Error creando socket");
		exit(EXIT_FAILURE);
	}

	if ( abrirbind(sockfd) == -1 ){/*abrimos bind*/
		fprintf(stderr, "Error binding socket");
		exit(EXIT_FAILURE);
	}
	
	if ( abrirListen(sockfd) == -1 ){/*abrimos listen*/
		fprintf(stderr, "Error al escuchar");
		exit(EXIT_FAILURE);
	}
	return sockfd;
}

void accept_connection(int sockfd){
	int desc;
	struct sockaddr Conexion;
	if ((desc = aceptar(sockfd, Conexion)) == -1){
		fprintf(stderr, "Error accepting connection");
		exit(EXIT_FAILURE);
	}
	launch_service(desc);
	wait_finished_services();
	return;
}

void launch_service(int connval){
	int pid;
	long type, aux;
	pid = fork();
	if (pid < 0){
		exit(EXIT_FAILURE);
	}
	if (pid == 0){
		return;
	}
	recv(connval, &aux, sizeof(long), 0);
	type = ntohl(aux);
	database_access(connval, type, NULL); /*¿?*/
	close(connval);
	exit(EXIT_SUCCESS);
}
