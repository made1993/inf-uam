#include "../includes/G-2313-07-P2demoniochat.h"

void demonioChat(){
	pid_t pid;
	int i;
	int tamano = 0;
	pid = fork(); /* Fork off the parent process */
	if (pid < 0)
		exit(EXIT_FAILURE);
	if (pid > 0)
		exit(EXIT_SUCCESS);


	umask(0); /* Change the file mode mask */
	setlogmask (LOG_UPTO (LOG_INFO)); /* Open logs here */
	openlog ("Server system messages:", LOG_CONS | LOG_PID | LOG_NDELAY, LOG_LOCAL3);
	syslog (LOG_ERR, "Initiating new server.");
	//printf("vamos pa hacer cosas\n");
	if (setsid()< 0) { /* Create a new SID for the child process */
		syslog (LOG_ERR, "Error creating a new SID for the child process.");
		exit(EXIT_FAILURE);
	}
	if ((chdir("/")) < 0) { /* Change the current working directory */
		syslog (LOG_ERR, "Error changing the current working directory = \"/\"");
		exit(EXIT_FAILURE);
	}
	 /* Close out the standard file descriptors */
	syslog (LOG_INFO, "Closing standard file descriptors");
	tamano =  getdtablesize();
	for(i = 0; i <  tamano; ++i){
		close(i);
	}

		serverConexionIRC();

	return;
}
