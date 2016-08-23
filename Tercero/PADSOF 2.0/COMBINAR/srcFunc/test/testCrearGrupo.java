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
		app.login("a.a@mail.gob", "a");
		//app.addMensajeGrupo(new MensajeGrupo(0, "prueba", app.getLogged()), app.buscarGrupo("normal1"));
		app.getLogged().getListaGrupos().get(1).addMensaje(new MensajeGrupo(0, "prueba", app.getLogged()));
		System.out.println("\n"+app.buscarGrupo("normal1"));
		System.out.println(app.getListaGrupos().get(0));
		System.out.println(app.getLogged().getListaGrupos().get(1)+"\n");
		//app.guardarGrupos();
		app.guardarUsuarios();
		app = new MailUam();
		app.login("a.a@mail.gob", "a");
		System.out.println("\n"+app.buscarGrupo("normal1"));
		System.out.println(app.getListaGrupos().get(0));
		System.out.println(app.getLogged().getListaGrupos().get(1)+"\n");
		//app.cargarUsuarios();
		//app.cargarGrupos();
	}

}
