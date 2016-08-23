  /***************************************************************************************************
* Redes de Comunicaciones II                                                                       *
* 25/2/2014                                                                                        *
*                                                                                                  *
*    Interfaz para el cliente IRC.                                                                 *
*                                                                                                  *
*    Interfaz simple para IRC que permite toda la interacción mínima con el usuario. Dispone de    *
*    un campo de introducción de la url del servidor, un botón de conexión, una ventana de texto   *
*    para la salida de los mensajes enviados y recibidos, un campo de introducción de mensajes     *
*    y un botón para el envío de los mensajes.                                                     *
*                                                                                                  *
*    Todos los callbacks están completamente resueltos salvo los que tiene que resolver el alumno  *
*    que son el de envío de mensajes y el de conexión. Ambos callbacks llaman a las funciones      *
*    	void connectClient(void)                                                                     *
*       void disconnectClient(void)                                                                *
*       void topicProtect(gboolean state)                                                          *
*       void externMsg(gboolean state)                                                             *
*       void secret(gboolean state)                                                                *
*       void guests(gboolean state)                                                                *
*       void privated(gboolean state)                                                              *
*       void moderated(gboolean state)                                                             *
*       void newText (const char *msg)                                                             *
*                                                                                                  *
*    Se proporcionan tres funciones para la impresión de mensajes y errores que el alumno podrá    *
*    utilizar en cualquier punto del programa:                                                     *
*        void publicText(char *user, char *text)                                                   *
*        void privateText(char *user, char *text)                                                  *
*        void errorText(char *errormessage)                                                        *
*        void messageText(char *message)                                                           *
*                                                                                                  *
*    Se proporcionan funciones para obtener algunas variables del entorno gráfico:                 *
*        char * getApodo(void)                                                                     *
*        char * getNombre(void)                                                                    *
*        char * getNombreReal(void)                                                                *
*        char * getServidor(void)                                                                  *
*        int getPuerto(void)                                                                       *
*                                                                                                  *
*    Se proporciona una función para presentar ventanas de error:                                  *
*        void errorWindow(char *msg)                                                               *
*                                                                                                  *
*    Se proporcionan funciones para el manejo de páginas:                                          *
*        int    currentPage()                                                                      *
*        int    addNewPage(char *label)                                                            *
*        void   deletePage(int index)                                                              *
*        char * getNamePage(int index)                                                             *
*                                                                                                  *
***************************************************************************************************/

#include <stdlib.h>
#include <string.h>
#include <redes2/chat.h>

#define MAX_PAGES	16


/* Variables globales */
GtkWidget *window;
GtkWidget *eApodo, *eNombre, *eNombreR, *eServidor, *ePuerto;
GtkTextIter iter[MAX_PAGES];
GtkTextBuffer *buffer[MAX_PAGES];
GtkWidget *topicB, *externB, *secretB, *guestB, *privateB, *moderatedB, *notebook;
gboolean states[MAX_PAGES][6];

int maxpages = 0;

void 		scrolling	(GtkWidget *widget, gpointer data);
void 		connectCB 	(GtkButton *button,gpointer user_data);
void 		disconnectCB 	(GtkButton *button,gpointer user_data);
gboolean 	topicProtectCB	(GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data);
gboolean 	externMsgCB	(GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data);
gboolean 	secretCB	(GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data);
gboolean 	guestsCB	(GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data);
gboolean 	privateCB	(GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data);
gboolean 	moderatedCB	(GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data);
void 		readMessageCB	(GtkWidget *msg, gpointer user_data);
void 		setTopicProtect	(gboolean state);
void 		setExternMsg	(gboolean state);
void 		setSecret	(gboolean state);
void 		setGuests	(gboolean state);
void 		setPrivate	(gboolean state);
void 		setModerated	(gboolean state);
void 		setFlags	(int arrayind);
void 		pageShowed	(GtkWidget *scr, gpointer user_data);
void 		ConnectArea	(GtkWidget *vbox);
void 		StateArea	(GtkWidget *vbox);
void 		ChatArea	(GtkWidget *vbox);


gboolean toggleButtonState(GtkToggleButton *togglebutton){return gtk_toggle_button_get_active(GTK_TOGGLE_BUTTON(togglebutton));}

/**
 * @page current_page \b current_page
 *
 * @brief Obtiene el índice de la página solicitada.
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b int \b current_page \b (\b void\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Esta función devuelve el índice de la página activa. Es importante que se
 * mantenga la asignación entre canal y página activa o que cuando se cree la
 * página activa se haga con el nombre del canal para poder recuperarlo con la
 * función get_name_page().
 * 
 * No recibe ningún parámetro.
 *
 * @section retorno RETORNO
 * 
 * Devuelve el índice de la página activa.
 *
 * @section seealso VER TAMBIÉN
 * \b add_new_page(3), \b delete_page(3), \b get_name_page(3), \b set_current_page(3)
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

int current_page(){return gtk_notebook_get_current_page(GTK_NOTEBOOK(notebook));}

/**
 * @page set_current_page \b set_current_page
 *
 * @brief Activa la página indicada.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b void \b set_current_page \b (\b int \b page_num \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Esta función activa la página indicada. Recibe como parámetro la página a activar.
 *
 * @section retorno RETORNO
 * 
 * No devuelve nada
 *
 * @section seealso VER TAMBIÉN
 * \b add_new_page(3), \b delete_page(3), \b get_name_page(3), \b current_page(3)
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void set_current_page(int page){gtk_notebook_set_current_page(GTK_NOTEBOOK(notebook),page);}

/*******************************************************************************
*  Lee los valores de inicio del chat y los devuelven del tipo que corresponda *
*******************************************************************************/

/**
 * @page get_nick \b get_nick
 *
 * @brief Obtiene el valor introducido en el campo de apodo.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b char\b * \b get_nick \b (\b void\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Obtiene el valor introducido en el campo de apodo.
 * 
 * No recibe ningún parámetro.
 *
 * @section retorno RETORNO
 * 
 * Devuelve el una cadena con el apodo del usuario. No hay que liberar esta cadena.
 *
 * @section seealso VER TAMBIÉN
 * \b get_name(3), \b get_real_name(3), \b get_server(3), \b get_port(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

char * get_nick() {return (char *) gtk_entry_get_text(GTK_ENTRY(eApodo));}

/**
 * @page get_name \b get_name
 *
 * @brief Obtiene el valor introducido en el campo de nombre.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b char\b * \b get_name \b (\b void\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Obtiene el valor introducido en el campo de nombre.
 *
 * No recibe ningún parámetro.
 *
 * @section retorno RETORNO
 * 
 * Devuelve el una cadena con el nombre del usuario. No hay que liberar esta cadena.
 *
 * @section seealso VER TAMBIÉN
 * \b get_nick(3), \b get_real_name(3), \b get_server(3), \b get_port(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

char * get_name() {return (char *) gtk_entry_get_text(GTK_ENTRY(eNombre));}

/**
 * @page get_real_name \b get_real_name
 *
 * @brief Obtiene el valor introducido en el campo de nombre real.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b char\b * get_real_name \b (\b void\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Obtiene el valor introducido en el campo de nombre real.
 *
 * No recibe ningún parámetro.
 *
 * @section retorno RETORNO
 * 
 * Devuelve el una cadena con el nombre real usuario. No hay que liberar esta cadena.
 *
 * @section seealso VER TAMBIÉN
 * \b get_nick(3), \b get_name(3), \b get_server(3), \b get_port(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

char * get_real_name()  {return (char *) gtk_entry_get_text(GTK_ENTRY(eNombreR));}

/**
 * @page get_server \b get_server
 *
 * @brief Obtiene el valor introducido en el campo de servidor.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b char\b * \b get_server \b (\b void\b )
 * 
 * @section descripcion DESCRIPCIÓN
 * 
 * Obtiene el valor introducido en el campo de servidor.
 * 
 * No recibe ningún parámetro.
 *
 * @section retorno RETORNO
 * 
 * Devuelve el una cadena con el servidor. No hay que liberar esta cadena.
 *
 * @section seealso VER TAMBIÉN
 * \b get_nick(3), \b get_name(3), \b get_real_name(3), \b get_port(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

char * get_server() {return (char *) gtk_entry_get_text(GTK_ENTRY(eServidor));}

/**
 * @page get_port \b get_port
 *
 * @brief Obtiene el valor del campo de puerto.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b int \b get_port \b (\b void\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * 
 * No recibe ningún parámetro.
 *
 * @section retorno RETORNO
 * 
 * Devuelve el valor del puerto elegido. 
 *
 * @section seealso VER TAMBIÉN
 * \b get_nick(3), \b get_name(3), \b get_real_name(3), \b get_server(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

int    get_port() {return atoi(gtk_entry_get_text(GTK_ENTRY(ePuerto)));}

/*******************************************************************************
*  Lee los valores de inicio del chat y los devuelven del tipo que corresponda *
*******************************************************************************/

/**
 * @page set_nick \b set_nick
 *
 * @brief Pone el campo de apodo a un valor.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b void \b set_nick \b (\b char *\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Pone el apodo indicado por el parámetro en el campo del apodo.
 * 
 * @section retorno RETORNO
 * 
 * No devuelve nada
 *
 * @section seealso VER TAMBIÉN
 * \b set_name(3), \b set_real_name(3), \b set_server(3), \b set_port(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void set_nick(char *nick) {gtk_entry_set_text(GTK_ENTRY(eApodo),nick);}

/**
 * @page set_name \b set_name
 *
 * @brief Pone el campo de nombre a un valor.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b void \b set_name \b (\b char *\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Pone el nombre indicado por el parámetro en el campo del nombre.
 * 
 * @section retorno RETORNO
 * 
 * No devuelve nada
 *
 * @section seealso VER TAMBIÉN
 * \b set_nick(3), \b set_real_name(3), \b set_server(3), \b set_port(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/


void set_name(char *name) {gtk_entry_set_text(GTK_ENTRY(eNombre),name);}

/**
 * @page set_real_name \b set_real_name
 *
 * @brief Pone el campo de nomebre real a un valor.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b void \b set_real_name \b (\b char *\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Pone el nombre real indicado por el parámetro en el campo del nombre real.
 * 
 * @section retorno RETORNO
 * 
 * No devuelve nada
 *
 * @section seealso VER TAMBIÉN
 * \b set_nick(3), \b set_name(3), \b set_server(3), \b set_port(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void set_real_name(char *realname)  {gtk_entry_set_text(GTK_ENTRY(eNombreR),realname);}

/**
 * @page set_server \b set_server
 *
 * @brief Pone el campo de servidor a un valor.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b void \b set_server \b (\b char *\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Pone el servidor indicado por el parámetro en el campo del servidor.
 * 
 * @section retorno RETORNO
 * 
 * No devuelve nada
 *
 * @section seealso VER TAMBIÉN
 * \b set_nick(3), \b set_name(3), \b set_real_name(3), \b set_port(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void set_server(char *server) {gtk_entry_set_text(GTK_ENTRY(eServidor),server);}

/**
 * @page set_port \b set_port
 *
 * @brief Pone el campo de puerto a un valor.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b void \b set_port \b (\b int \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Pone el puerto indicado por el parámetro en el campo del puerto.
 * 
 * @section retorno RETORNO
 * 
 * No devuelve nada
 *
 * @section seealso VER TAMBIÉN
 * \b set_nick(3), \b set_name(3), \b set_real_name(3), \b set_server(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void   set_port(int port) 
{
  char aux[16]; 

  sprintf(aux,"%d",port); 
  gtk_entry_set_text(GTK_ENTRY(ePuerto),aux);
}

/**
 * @page error_window \b error_window
 *
 * @brief Presenta una ventana de error.
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b error_window( \b (\b char\b *\b msg\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Permite presentar un error en una ventana. 
 * 
 * Recibe como parámetro el mensaje a reproducir.
 *
 * @section retorno RETORNO
 * 
 * No devuelve nada.
 * 
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void error_window(char *msg)
{ 
  GtkWidget *pError;

  pError = gtk_message_dialog_new (GTK_WINDOW(window), /* Diálogo error envío */
         GTK_DIALOG_MODAL |GTK_DIALOG_DESTROY_WITH_PARENT,
         GTK_MESSAGE_ERROR,
         GTK_BUTTONS_CLOSE,
         "Error:\n%s",msg);

  gtk_dialog_run(GTK_DIALOG(pError));
  gtk_widget_destroy (pError);
}


/**
 * @page public_text \b public_text
 *
 * @brief Permite presentar un texto con el formato de color de un texto publico.
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b public_text \b (\b char\b *\b pagenum\b, \b char\b *\b user\b, \b char\b *\b text\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Permite presentar un texto con el formato de color de un texto publico en una
 * página en el que se presenta el nombre del usuario con un determinado color y 
 * el texto con otro y distintos del resto de los mensajes.
 * 
 * Recibe como parámetros el índice de la página, el usuario y el mensaje.
 *
 * @section retorno RETORNO
 * 
 * No devuelve nada.
 * 
 * @section seealso VER TAMBIÉN
 * \b private_text(3), \b error_text(3), \b message_text(3).
 * 
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void public_text(int pagenum, char *user, char *text)
{
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), user, -1, "blue_fg", "bold", "lmarg",  NULL);
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), ": ", -1, "blue_fg", "bold", "lmarg",  NULL);
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), text, -1, "italic",  NULL);
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), "\n", -1, "italic",  NULL);
}

/**
 * @page private_text \b private_text
 *
 * @brief Permite presentar un texto con el formato de color de un texto privado.
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b private_text \b (\b char\b *\b pagenum\b, \b char\b *\b user\b, \b char\b *\b text\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Permite presentar un texto con el formato de color de un texto privado en una
 * página en el que se presenta el nombre del usuario con un determinado color y 
 * el texto con otro y distintos del resto de los mensajes.
 * 
 * Recibe como parámetros el índice de la página, el usuario y el mensaje.
 *
 * @section retorno RETORNO
 * 
 * No devuelve nada.
 * 
 * @section seealso VER TAMBIÉN
 * \b public_text(3), \b error_text(3), \b message_text(3).
 * 
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void private_text(int pagenum, char *user, char *text)
{
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), user, -1, "blue_fg", "bold", "lmarg",  NULL);
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), ": ", -1, "blue_fg", "bold", "lmarg",  NULL);
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), text, -1, "green_fg",  NULL);
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), "\n", -1, "green_fg",  NULL);
}

/**
 * @page error_text \b error_text
 *
 * @brief Permite presentar un texto de error con el formato de color correspondiente.
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b error_text \b (\b char\b *\b pagenum\b, \b char\b *\b errtext\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Permite presentar un texto de error con el formato de color correspondiente y
 * llamativo.
 * 
 * Recibe como parámetros el índice de la página y el mensaje de error.
 *
 * @section retorno RETORNO
 * 
 * No devuelve nada.
 * 
 * @section seealso VER TAMBIÉN
 * \b public_text(3), \b private_text(3), \b message_text(3).
 * 
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void error_text(int pagenum, char *errormessage)
{
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), "Error: ", -1, "magenta_fg", "black_bg","italic", "bold", "lmarg",  NULL);
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), errormessage, -1, "magenta_fg", "black_bg","italic", "bold", "lmarg",  NULL);
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), "\n", -1, "magenta_fg",  NULL);
}

/**
 * @page message_text \b message_text
 *
 * @brief Permite presentar un texto cualquier con el formato de color correspondiente.
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b message_text \b (\b char\b *\b pagenum\b, \b char\b *\b text\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Permite presentar un texto general con el formato de color correspondiente y
 * llamativo.
 * 
 * Recibe como parámetros el índice de la página y el mensaje.
 *
 * @section retorno RETORNO
 * 
 * No devuelve nada.
 * 
 * @section seealso VER TAMBIÉN
 * \b public_text(3), \b private_text(3), \b error_text(3).
 * 
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void message_text(int pagenum, char *message)
{
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), message, -1, "magenta_fg", "italic", "bold", "lmarg",  NULL);
    gtk_text_buffer_insert_with_tags_by_name(buffer[pagenum], &(iter[pagenum]), "\n", -1, "magenta_fg",  NULL);
}

/**
 * @brief Añade una nueva página de chat.
 *
 * @page add_new_page \b add_new_page
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b int \b add_new_page \b (\b char\b *\b label\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Esta función añade una nueva página de chat con la etiqueta indicada en el
 * parámetro de entrada. Esta etiqueta puede leerse con la función getNamePage().
 *
 * @section retorno RETORNO
 * 
 * Devuelve el índice de la página creada o -1 si supera el límite de páginas.
 *
 * @section seealso VER TAMBIÉN
 * \b currentPage(3), \b delete_page(3), \b get_name_page(3), \b get_index_page(3), \b set_current_page(3), \b num_opened_pages(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

int add_new_page(char *label)
{
  GtkWidget *tab_label;
  GtkWidget *scroll, *view;
  GtkAdjustment *adjustment;
  int numpage;
  
  if(maxpages >= MAX_PAGES) return -1;
  
  tab_label = gtk_label_new(label);
  
  scroll           = gtk_scrolled_window_new(NULL,NULL);
  numpage          = gtk_notebook_append_page (GTK_NOTEBOOK(notebook),scroll, tab_label);
  view             = gtk_text_view_new();  
  buffer[numpage] = gtk_text_view_get_buffer(GTK_TEXT_VIEW(view));
  gtk_scrolled_window_add_with_viewport(GTK_SCROLLED_WINDOW(scroll),view);
  
  gtk_text_view_set_editable(GTK_TEXT_VIEW(view),FALSE);
  gtk_text_view_set_wrap_mode(GTK_TEXT_VIEW(view),GTK_WRAP_WORD_CHAR);
  gtk_widget_set_size_request(scroll,600,360);
  adjustment = (GtkAdjustment *) gtk_adjustment_new(0.,0., 396.,18.,183.,396.);
  gtk_scrolled_window_set_vadjustment (GTK_SCROLLED_WINDOW(scroll),adjustment);
  gtk_scrolled_window_set_policy(GTK_SCROLLED_WINDOW(scroll),GTK_POLICY_AUTOMATIC,GTK_POLICY_AUTOMATIC);
  gtk_scrolled_window_set_placement(GTK_SCROLLED_WINDOW(scroll),GTK_CORNER_BOTTOM_LEFT);
  
  gtk_text_buffer_create_tag(buffer[numpage], "lmarg", "left_margin", 5, NULL);
  gtk_text_buffer_create_tag(buffer[numpage], "red_fg","foreground", "red", NULL); 
  gtk_text_buffer_create_tag(buffer[numpage], "blue_fg","foreground", "blue", NULL); 
  gtk_text_buffer_create_tag(buffer[numpage], "magenta_fg","foreground", "#FF00FF", NULL);
  gtk_text_buffer_create_tag(buffer[numpage], "black_bg","background", "black", NULL);
  gtk_text_buffer_create_tag(buffer[numpage], "green_fg","foreground", "#00BB00", NULL); 
  gtk_text_buffer_create_tag(buffer[numpage], "italic", "style", PANGO_STYLE_ITALIC, NULL);
  gtk_text_buffer_create_tag(buffer[numpage], "bold", "weight", PANGO_WEIGHT_BOLD, NULL);

  gtk_text_buffer_get_iter_at_offset(buffer[numpage], &(iter[numpage]), 0);
  
  g_signal_connect(G_OBJECT(scroll), "size-allocate", G_CALLBACK(scrolling), NULL);
  g_signal_connect(G_OBJECT(scroll), "map", G_CALLBACK(pageShowed), NULL);
 
  gtk_widget_show_all(notebook);
  ++maxpages;
  return (numpage);
}

/**
 * @page get_name_page \b get_name_page
 *
 * @brief Obtiene el nombre de una página.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b int \b get_name_page \b (\b int\b index\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Con esta función puede verse la etiqueta de una página. Si se usa como etiqueta
 * el nombre del canal puede obtenerse el nombre del canal de la propia etiqueta.
 *
 * @section retorno RETORNO
 * 
 * Devuelve un puntero a una cadena de caracteres que no hay que liberar.
 *
 * @section seealso VER TAMBIÉN
 * \b current_page(3), \b delete_page(3), \b add_new_page(3), \b get_index_page(3), \b set_current_page(3), \b num_opened_pages(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

char * get_name_page(int index)
{
  GtkWidget *pageaux;
  pageaux = gtk_notebook_get_nth_page (GTK_NOTEBOOK(notebook), index);
  return (char *) gtk_notebook_get_tab_label_text (GTK_NOTEBOOK(notebook), pageaux);
}

/**
 * @page get_index_page \b get_index_page
 *
 * @brief Obtiene el índice de una página con un determinado nombre
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b int \b get_index_page \b (\b char\b *\b name\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Con esta función puede obtenerse el índice de una página a partir de la 
 * etiqueta de la página.
 *
 * @section retorno RETORNO
 * 
 * Devuelve el índice de la página y un valor negativo en el caso de que
 * no haya ninguna página con esa etiqueta.
 *
 * @section seealso VER TAMBIÉN
 * \b current_page(3), \b delete_page(3), \b add_new_page(3), \b get_name_page(3), \b num_opened_pages(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/
int get_index_page(char * name)
{
  GtkWidget *pageaux;
  register int i;

  for(i=0; i< maxpages;++i)
  {
    pageaux = gtk_notebook_get_nth_page (GTK_NOTEBOOK(notebook), i);
    if(!strcmp((char *) gtk_notebook_get_tab_label_text (GTK_NOTEBOOK(notebook), pageaux),name)) break;
  }
  if(i == maxpages) return -1;
  return i;
}

/**
 * @page delete_page \b delete_page
 *
 * @brief Elimina una página.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b int \b delete_page \b (\b int\b *\b index\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Elimina la página indicada por el índice.
 *
 * @section retorno RETORNO
 * 
 * No devuelve nada.
 *
 * @section seealso VER TAMBIÉN
 * \b current_page(3), \b get_name_page(3), \b add_new_page(3), \b get_index_page(3), \b num_opened_pages(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

void delete_page(int index)
{
  register int i,j;
  
  if(index <= 0 || index >= maxpages) return;
  for(i=index; i < maxpages-1; ++i)
  {
    if(i <= 0 || i >= MAX_PAGES) return;
    buffer[i] = buffer[i+1];
    iter[i] = iter[i+1];
    for (j = 0; j< 6; ++j) states[i][j] = states[i+1][j];
  }
  if(i < MAX_PAGES) for (j = 0; j< 6; ++j) states[i][j] = FALSE;
  --maxpages;
  gtk_notebook_remove_page (GTK_NOTEBOOK(notebook), index);
}

/**
 * @page num_opened_pages \b num_opened_pages
 *
 * @brief Devuelve el número de páginas abiertas exceptuando la de sistema.
 *
 * @section SYNOPSIS
 *  \b #include \b <redes2/chat.h>
 *
 *  \b int \b num_opened_pages \b (\b void \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Devuelve el número de páginas abiertas exceptuando la de sistema.
 *
 * @section retorno RETORNO
 * 
 * Devuelve el número de páginas abiertas exceptuando la de sistema.
 *
 * @section seealso VER TAMBIÉN
 * \b current_page(3), \b get_name_page(3), \b add_new_page(3), \b get_index_page(3).
 *
 * @section authors AUTOR
 * Eloy Anguiano (eloy.anguiano@uam.es)
*/

int num_opened_pages(){ return maxpages-1; }


/*******************************************************************************
*  Realiza un scrolling cada vez que se presenta un mensaje                    *
*                                                                              *
*  Parámetros y retorno según callbacks de gnome                               *
*                                                                              *
*******************************************************************************/

void scrolling(GtkWidget *widget, gpointer data)
{ 
  GtkAdjustment *adjustment;

  adjustment = gtk_scrolled_window_get_vadjustment(GTK_SCROLLED_WINDOW(widget));
  adjustment->value = adjustment->upper;
  gtk_scrolled_window_set_vadjustment(GTK_SCROLLED_WINDOW(widget),adjustment);
}


/*******************************************************************************
*  Funciones de uso interno                                                    *
*******************************************************************************/

void connectCB (GtkButton *button,gpointer user_data){connect_client();}
void disconnectCB (GtkButton *button,gpointer user_data){disconnect_client();}

gboolean topicProtectCB (GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data)
{
  states[current_page()][0] = TRUE^toggleButtonState(togglebutton);
  if(event->type == GDK_BUTTON_PRESS) topic_protect(states[current_page()][0]);
  return FALSE;
}

gboolean externMsgCB (GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data)
{
  states[current_page()][1] = TRUE^toggleButtonState(togglebutton);
  if(event->type == GDK_BUTTON_PRESS) extern_msg(states[current_page()][1]);
  return FALSE;
}

gboolean secretCB (GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data)
{
  states[current_page()][2] = TRUE^toggleButtonState(togglebutton);
  if(event->type == GDK_BUTTON_PRESS) secret(states[current_page()][2]);
  return FALSE;
}

gboolean guestsCB (GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data)
{
  states[current_page()][3] = TRUE^toggleButtonState(togglebutton);
  if(event->type == GDK_BUTTON_PRESS) guests(states[current_page()][3]);
  return FALSE;
}

gboolean privateCB (GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data)
{
  states[current_page()][4] = TRUE^toggleButtonState(togglebutton);
  if(event->type == GDK_BUTTON_PRESS) privated(states[current_page()][4]);
  return FALSE;
}

gboolean moderatedCB (GtkToggleButton *togglebutton, GdkEvent *event, gpointer user_data)
{
  states[current_page()][5] = TRUE^toggleButtonState(togglebutton);
  if(event->type == GDK_BUTTON_PRESS) moderated(states[current_page()][5]);
  return FALSE;
}

                
void readMessageCB (GtkWidget *msg, gpointer user_data)
{
  new_text((char *) gtk_entry_get_text(GTK_ENTRY(msg)));
  gtk_entry_set_text(GTK_ENTRY(msg),"");
}
       
void setTopicProtect	(gboolean state){gtk_toggle_button_set_active(GTK_TOGGLE_BUTTON(topicB)		, state);}
void setExternMsg	(gboolean state){gtk_toggle_button_set_active(GTK_TOGGLE_BUTTON(externB)	, state);}
void setSecret		(gboolean state){gtk_toggle_button_set_active(GTK_TOGGLE_BUTTON(secretB)	, state);}
void setGuests		(gboolean state){gtk_toggle_button_set_active(GTK_TOGGLE_BUTTON(guestB)		, state);}
void setPrivate		(gboolean state){gtk_toggle_button_set_active(GTK_TOGGLE_BUTTON(privateB)	, state);}
void setModerated	(gboolean state){gtk_toggle_button_set_active(GTK_TOGGLE_BUTTON(moderatedB)	, state);}

void setFlags(int arrayind)
{
	setTopicProtect	(states[arrayind][0]);
	setExternMsg	(states[arrayind][1]);
	setSecret	(states[arrayind][2]);
	setGuests	(states[arrayind][3]);
	setPrivate	(states[arrayind][4]);
	setModerated	(states[arrayind][5]);
}

void pageShowed (GtkWidget *scr, gpointer user_data)
{
  setFlags(current_page());
}


/*******************************************************************************
*  Presenta la zona de conexión                                                *
*                                                                              *
*******************************************************************************/

void ConnectArea(GtkWidget *vbox)
{
  GtkWidget *table;
  GtkWidget *hb1, *hb2, *hb3, *hb4, *hb5, *hb6;
  GtkWidget *l1, *l2, *l3, *l4, *l5;
  GtkWidget *frm;
  GtkWidget *bt1, *bt2;

  frm = gtk_frame_new("Conexión");
  gtk_box_pack_start(GTK_BOX(vbox), frm,FALSE,FALSE,2);
  table = gtk_table_new(1, 6, FALSE);
  gtk_container_add(GTK_CONTAINER(frm), table);

  hb1 = gtk_hbox_new(FALSE,2);
  hb2 = gtk_hbox_new(FALSE,2);
  hb3 = gtk_hbox_new(FALSE,2);
  hb4 = gtk_hbox_new(FALSE,2);
  hb5 = gtk_hbox_new(FALSE,2);
  hb6 = gtk_hbox_new(FALSE,2);
  gtk_table_attach(GTK_TABLE(table), hb1, 0, 1, 0, 1, GTK_FILL | GTK_SHRINK, GTK_SHRINK, 2, 2);
  gtk_table_attach(GTK_TABLE(table), hb2, 0, 1, 1, 2, GTK_FILL | GTK_SHRINK, GTK_SHRINK, 2, 2);
  gtk_table_attach(GTK_TABLE(table), hb3, 0, 1, 2, 3, GTK_FILL | GTK_SHRINK, GTK_SHRINK, 2, 2);
  gtk_table_attach(GTK_TABLE(table), hb4, 0, 1, 3, 4, GTK_FILL | GTK_SHRINK, GTK_SHRINK, 2, 2);
  gtk_table_attach(GTK_TABLE(table), hb5, 0, 1, 4, 5, GTK_FILL | GTK_SHRINK, GTK_SHRINK, 2, 2);
  gtk_table_attach(GTK_TABLE(table), hb6, 0, 1, 5, 6, GTK_FILL | GTK_SHRINK, GTK_SHRINK, 2, 2);
  l1 = gtk_label_new("Apodo");
  l2 = gtk_label_new("Nombre");
  l3 = gtk_label_new("Nombre real");
  l4 = gtk_label_new("Servidor");
  l5 = gtk_label_new("Puerto");
  eApodo    = gtk_entry_new();
  eNombre   = gtk_entry_new();
  eNombreR  = gtk_entry_new();
  eServidor = gtk_entry_new();
  ePuerto   = gtk_entry_new();
  bt1 = gtk_button_new_with_mnemonic("_Conectar");
  bt2 = gtk_button_new_with_mnemonic("_Desconectar");

  gtk_box_pack_start(GTK_BOX(hb1), l1 ,FALSE,FALSE,2);
  gtk_box_pack_start(GTK_BOX(hb2), l2 ,FALSE,FALSE,2);
  gtk_box_pack_start(GTK_BOX(hb3), l3 ,FALSE,FALSE,2);
  gtk_box_pack_start(GTK_BOX(hb4), l4 ,FALSE,FALSE,2);
  gtk_box_pack_start(GTK_BOX(hb5), l5 ,FALSE,FALSE,2);
  gtk_box_pack_start(GTK_BOX(hb6), bt1,TRUE ,TRUE ,2);

  gtk_box_pack_end(GTK_BOX(hb1), eApodo   ,FALSE,FALSE,2);
  gtk_box_pack_end(GTK_BOX(hb2), eNombre  ,FALSE,FALSE,2);
  gtk_box_pack_end(GTK_BOX(hb3), eNombreR ,FALSE,FALSE,2);
  gtk_box_pack_end(GTK_BOX(hb4), eServidor,FALSE,FALSE,2);
  gtk_box_pack_end(GTK_BOX(hb5), ePuerto  ,FALSE,FALSE,2);
  gtk_box_pack_end(GTK_BOX(hb6), bt2      ,TRUE ,TRUE ,2);

  g_signal_connect(G_OBJECT(bt1), "clicked", G_CALLBACK(connectCB), NULL);
  g_signal_connect(G_OBJECT(bt2), "clicked", G_CALLBACK(disconnectCB), NULL);

}




/*******************************************************************************
*  Presenta la zona de estado                                                  *
*                                                                              *
*******************************************************************************/

void StateArea(GtkWidget *vbox)
{
  GtkWidget *vboxi;
  GtkWidget *frm;

  frm = gtk_frame_new("Estado");
  gtk_box_pack_start(GTK_BOX(vbox), frm,FALSE,FALSE,2);
  vboxi = gtk_vbox_new(FALSE,2);
  gtk_container_add(GTK_CONTAINER(frm), vboxi);

  topicB	= gtk_check_button_new_with_mnemonic("_Protección de tópico");
  externB	= gtk_check_button_new_with_mnemonic("Mensajes _externos");
  secretB	= gtk_check_button_new_with_mnemonic("_Secreto");
  guestB	= gtk_check_button_new_with_mnemonic("Sólo _invitados");
  privateB	= gtk_check_button_new_with_mnemonic("Pri_vado");
  moderatedB	= gtk_check_button_new_with_mnemonic("_Moderado");

  gtk_box_pack_start(GTK_BOX(vboxi), topicB	,TRUE ,TRUE ,2);
  gtk_box_pack_start(GTK_BOX(vboxi), externB	,TRUE ,TRUE ,2);
  gtk_box_pack_start(GTK_BOX(vboxi), secretB	,TRUE ,TRUE ,2);
  gtk_box_pack_start(GTK_BOX(vboxi), guestB	,TRUE ,TRUE ,2);
  gtk_box_pack_start(GTK_BOX(vboxi), privateB	,TRUE ,TRUE ,2);
  gtk_box_pack_start(GTK_BOX(vboxi), moderatedB	,TRUE ,TRUE ,2);

  g_signal_connect(G_OBJECT(topicB)	, "button-press-event", G_CALLBACK(topicProtectCB),	NULL);
  g_signal_connect(G_OBJECT(externB)	, "button-press-event", G_CALLBACK(externMsgCB),	NULL);
  g_signal_connect(G_OBJECT(secretB)	, "button-press-event", G_CALLBACK(secretCB),		NULL);
  g_signal_connect(G_OBJECT(guestB)	, "button-press-event", G_CALLBACK(guestsCB),		NULL);
  g_signal_connect(G_OBJECT(privateB)	, "button-press-event", G_CALLBACK(privateCB),		NULL);
  g_signal_connect(G_OBJECT(moderatedB)	, "button-press-event", G_CALLBACK(moderatedCB),	NULL);
}

   
   

/*******************************************************************************
*  Presenta la zona de chat                                                    *
*                                                                              *
*******************************************************************************/
   
void ChatArea(GtkWidget *vbox)
{
  GtkWidget *hbox;
  GtkWidget *label, *msg;
  
  label    = gtk_label_new("Mensaje");
  hbox     = gtk_hbox_new(FALSE,2),
  msg      = gtk_entry_new();
  notebook = gtk_notebook_new();
  
  gtk_box_pack_start(GTK_BOX(vbox), notebook,FALSE,FALSE,2); 
  gtk_box_pack_end(GTK_BOX(vbox), hbox,FALSE,FALSE,2);
  gtk_box_pack_start(GTK_BOX(hbox), label,FALSE,FALSE,2);
  gtk_box_pack_end(GTK_BOX(hbox), msg,FALSE,FALSE,2);

  gtk_widget_set_size_request(msg,600,-1);
  gtk_notebook_set_scrollable(GTK_NOTEBOOK(notebook),TRUE);
  
  g_signal_connect(G_OBJECT(msg), "activate", G_CALLBACK(readMessageCB), NULL);
 
  add_new_page("** System **");
}



/*******************************************************************************
*  Prrograma principal                                                         *
*                                                                              *
*******************************************************************************/

int main(int argc, char**argv)
{
  GtkWidget *hboxg, *vbox1,*vbox2;
  register int i,j;

  for(i=0;i<MAX_PAGES;++i) for(j=0;j<6;++j) states[i][j] = FALSE;
  
  //g_type_init (); /* Necesario para tener funcionalidad de hilos */
  gdk_threads_init ();
  gdk_threads_enter ();
  gtk_init(&argc, &argv); /* Inicia gnome */

  window = gtk_window_new(GTK_WINDOW_TOPLEVEL); /* Ventana principal */
 
  gtk_window_set_title(GTK_WINDOW(window), "IRC Chat"); /* Título ventana principal */
  gtk_window_set_default_size(GTK_WINDOW(window), 800, 350); /* Tamaño ventana principal */
  gtk_window_set_position(GTK_WINDOW(window), GTK_WIN_POS_CENTER); /* Posición ventana principal */

  /* Estructura global */
  hboxg = gtk_hbox_new(FALSE,5);
  vbox1 = gtk_vbox_new(FALSE,5);
  vbox2 = gtk_vbox_new(FALSE,5);
  gtk_container_add(GTK_CONTAINER(window), hboxg);
  gtk_box_pack_start(GTK_BOX(hboxg), vbox1,FALSE,FALSE,1);
  gtk_box_pack_start(GTK_BOX(hboxg), vbox2,FALSE,FALSE,1);
  ConnectArea(vbox1);
  StateArea(vbox1);
  ChatArea(vbox2);

  g_signal_connect(G_OBJECT(window), "destroy", G_CALLBACK(gtk_main_quit), NULL);

  gtk_widget_show_all(window); /* Presentación de las ventanas */

  gdk_threads_leave (); /* Salida de hilos */
  gtk_main(); /* Administración de la interacción */

  return 0;
}
