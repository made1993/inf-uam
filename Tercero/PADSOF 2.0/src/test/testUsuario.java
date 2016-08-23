package test;


import java.io.IOException;
import mailUam.MailUam;
public class testUsuario {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		MailUam m= new MailUam();
		m.cargarDatos();
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
		
		
//		Usuario usr=new Estudiante(null, null, null, null);
//		usr=m.cargarUsuario("adrian.gabilondo@aus.net");
//		System.out.println(usr.getIdUsuario());
//		System.out.println("nombre:"+usr.getNombre());
//		System.out.println("apellido:"+usr.getApellido());
//		System.out.println("correo:"+usr.getCorreo());
//		System.out.println("password:"+usr.getPassword());
//		
//		ArrayList<Mensaje> mensajes = new ArrayList<>();
//		mensajes.add(new Mensaje("id1", usr));
//		mensajes.add(new Mensaje("id2", usr));
//		System.out.println("mensajes"+mensajes);
//		
		
		
		
		
		/*for(Grupo g: usr.getListaGrupos()){
			System.out.println("grupo: "+ g.getNombre());
		}
		for(Usuario u: usr.getContactos()){
			System.out.println("Contacto: "+u.getApellido());
		}*/
	}
}
