/***************************************************************************************************
* Redes de Comunicaciones II                                                                       *
* 25/2/2014                                                                                        *
*                                                                                                  *
*     Include necesario para el uso de las funciones públicas definidas para la práctica           *
*                                                                                                  *
***************************************************************************************************/

#ifndef _CHAT
#define _CHAT

#include <glib.h>
#include <gtk/gtk.h>

#ifndef FALSE
	#define FALSE	0
#endif
#ifndef TRUE
	#define TRUE	1
#endif


/* Función de ventana de error */
void error_window(char *msg);

/* Interfaz de impresión de textos */
void 	public_text	(int pagenum, char *username, char *text);
void 	private_text	(int pagenum, char *username, char *text);
void 	error_text	(int pagenum, char *errormessage);
void 	message_text	(int pagenum, char *message);

/* Funciones de conexión y desconexión */
void 	connect_client	(void);
void 	disconnect_client(void);

/* Funciones llamadas cuando se produce un cambio de estado */
void 	topic_protect	(gboolean state);
void 	extern_msg	(gboolean state);
void 	secret		(gboolean state);
void 	guests		(gboolean state);
void 	privated	(gboolean state);
void 	moderated	(gboolean state);

/* Función llamada cuando se introduce una entrada */
void 	new_text	(char *msg);

/* Funciones de manejo de páginas */
int 	current_page	(void);

int 	add_new_page	(char *label);
char * 	get_name_page	(int index);
int 	get_index_page	(char *name);
void 	delete_page	(int index);

/* Funciones para obtener valores de los campos de entrada */
char * get_nick		(void);
char * get_name		(void);
char * get_real_name	(void);
char * get_server	(void);
int    get_port		(void);

/* Funciones para obtener valores de los campos de entrada */
void set_nick		(char *);
void set_name		(char *);
void set_real_name	(char *);
void set_server		(char *);
void set_port		(int);

#endif
