package test;
import java.util.ArrayList;

import usuario.*;
import mailUam.*;
import mensaje.MensajeGrupo;
import mensaje.Pregunta;
import grupo.*;

public class testCrearGrupo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MailUam app = new MailUam();
		app.cargarGrupos();
		app.cargarUsuarios();
		app.login("a.a@mail.gob", "a");
		app.addMensajeGrupo(new MensajeGrupo(0, "prueba", app.getLogged()), app.buscarGrupo("normal1"));
		app.guardarGrupos();
		app.guardarUsuarios();
		app = new MailUam();
		app.cargarUsuarios();
		app.cargarGrupos();
	}

}
