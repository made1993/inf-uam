package test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import usuario.*;
import mailUam.*;
import mensaje.MensajeGrupo;
import grupo.*;
import mailUam.MailUam;
public class testUsuario {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		MailUam m= new MailUam();
		m.cargarDatos("alumnos.txt", "profesores.txt");
		m.crearDirectorios();
		/*Estudiante usr = new Estudiante("carlos", "el de los mocos largos", "adrian.gabilondo@aus.net", "hola");
		m.guardarUsuario(usr);
		usr= null;
		usr=m.cargarUsuario("adrian.gabilondo@aus.net");
		System.out.println(usr.getCorreo());*/
		/*Usuario usr1=new Estudiante("Hola", "mundo", "adrian.gabilondo@aus.net", "penes");
		usr1.addContacto(usr1);
		usr1.addGrupo(new Grupo(1, "hola", false, null));
		usr1.guardarUsuario();
		usr1=null;*/
		Usuario usr=new Estudiante(null, null, null, null);
		usr=usr.cargarUsuario("adrian.gabilondo@aus.net");
		System.out.println(usr.getIdUsuario());
		System.out.println("nombre:"+usr.getNombre());
		System.out.println("apellido:"+usr.getApellido());
		System.out.println("correo:"+usr.getCorreo());
		System.out.println("password:"+usr.getPassword());
		/*for(Grupo g: usr.getListaGrupos()){
			System.out.println("grupo: "+ g.getNombre());
		}
		for(Usuario u: usr.getContactos()){
			System.out.println("Contacto: "+u.getApellido());
		}*/
	}
}
