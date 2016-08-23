/**
 * 
 */
package test;

import gui.*;
import java.awt.*;
import javax.swing.*;
import control.*;
import mailUam.*;

/**
 * @author Antonio Gomez Lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase tester de la aplicacion
 */
public class MUamApi {

	/**
	 * @param args argumentos del main ninguno necesario
	 */
	public static void main(String[] args) {
		
		MailUam app = new MailUam();

		if (!app.existeDir("aplicacion")) {
			app.cargarDatos();
			app.crearDirectorios();
		}

		Ventana v = new Ventana(app);
	}

}
