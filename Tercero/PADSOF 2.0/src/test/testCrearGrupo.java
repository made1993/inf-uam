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
		System.out.println(app.getListaTodosGrupos().size());
	}

}
