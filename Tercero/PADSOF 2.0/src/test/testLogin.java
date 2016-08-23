package test;

import java.io.IOException;

import grupo.Grupo;
import usuario.Estudiante;
import usuario.Profesor;
import usuario.Usuario;
import mailUam.MailUam;

public class testLogin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MailUam app = new MailUam();
		System.out.println("Imprimiendo app\n"+app.toString());
		
		System.out.println("Login profesor");
		System.out.println(app.login("elvis.cordero@mail.gob","escomb467"));
		if(app.getLogged().isProfesor())
			System.out.println("Es profesor");
		else
			return;
		app.logout();
		System.out.println("Login estudiante");
		System.out.println(app.login("guillermo.rojo@aus.net","goroat836"));
		if(!app.getLogged().isProfesor())
			System.out.println("Es estudiante");
		else
			return;
		app.logout();
		app.guardarUsuario();
		app.guardarGrupos();
		
	}

}
