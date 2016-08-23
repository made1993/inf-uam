package test;

import grupo.*;

import java.io.*;
import java.util.*;
import mailUam.*;
import mensaje.*;
import usuario.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase de pruebas de mail Uam
 */
public class pruebaMailUam {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException  {
//		MailUam app= new MailUam();
//		if(!app.existeDir("aplicacion")){
//			app.cargarDatos();
//			app.crearDirectorios();
//		}
//		String correo = null;
//		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//		String tusr="a";
//		while(!tusr.equals("P")&&!tusr.equals("E")){
//	        System.out.println("Porfesor (P) o Estudiante (E): ");
//	       
//			tusr = bufferRead.readLine();
//		}
//        
//        if (tusr.equals("E")){
//        	Estudiante usr = new Estudiante(null, null, null, null);
//         	
//			while((usr=app.loginEstudiante())==null);
//			
//         	System.out.println("Binvenido/a:"+usr.getNombre()+" "+usr.getApellido());
//         	while (true){
//	         	listarOpcionesUsurio();
//	         	switch(retOpcion()){
//	        	case 1://Ver Perfil
//	         		System.out.println(usr.verPerfil());
//	         		break;
//	         	case 2://Listar mensajes
//	         		int i;
//	         		while (true){
//		         		i=1;
//						usr=(Estudiante)usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//						
//		         		for(MensajeUsuario m:usr.getBuzon().getMensajes()){
//		         			System.out.println("Mensaje:"+ i++);
//		         			System.out.println("Autor:"+m.getRemitente().getCorreo());
//		         			System.out.println("Ausnto:"+m.getCuerpo());
//		         		}
//		         		System.out.println();
//		         		System.out.println("Ver mensaje (escriba el numero del mensaje que quiere ver o 0 para salir):");
//		         		i=retOpcion();
//		         		if (i==0){
//		         			break;
//		         		}
//		         		else if(usr.getBuzon().getMensajesNoLeidos().size()>=i){
//		         			System.out.println(usr.getBuzon().getMensajes().get(i-1));
//		         		}
//	         		}
//	         		break;
//	         	case 3://Listar mis grupos
//					usr=(Estudiante)usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//					
//	         		while(true){
//	         			i=1;
//	         			for(Grupo g: usr.getListaGrupos()){
//	         				System.out.println("Numero:"+i++);
//	         				System.out.println("Nombre:"+g.getNombre());
//	         			}
//	         			System.out.println("Para entrar a ver un grupo pulse su numero o 0 para salir:");
//	         			i= retOpcion();
//	         			if (i==0)
//	         				break;
//	         			else if(usr.getListaGrupos().size()>=i){
//	         				Grupo grupo= new Grupo(0, null, false, null);
//	         				
//							grupo=grupo.cargarGrupo(usr.getListaGrupos().get(i-1).getNombre(),app.getBarra());
//							System.out.println(grupo);
//	         				usr.getListaGrupos().set(i-1, grupo);
//	         				
//	         				
//		         			System.out.println("1.Escribir un mensaje:");
//		         			
//		         			if(grupo.getModerador()!=null&&grupo.getModerador().getCorreo().equals(usr.getCorreo())){
//	         					System.out.println("2.Añadir usuario");
//	         					System.out.println("3.Expulsar usuario");
//	         				}
//		         			if(grupo.isGrupoEstudio()){
//	         					System.out.println("4.Ver pregunta");
//	         				}
//		         			int j=retOpcion();
//		         			if (j==1){
//		         				System.out.println("Escriba su mensjae y pulse enter");
//		         				String msj=retString();
//		         				System.out.println("Quiere mandar este mensaje 1 si 0 o enter no:");
//		         				if(retOpcion()==1){
//		         					MensajeGrupo mensaje= new MensajeGrupo(0, msj, Calendar.getInstance(), usr.getListaGrupos().get(i-1), usr);
//			         				grupo=grupo.cargarGrupo(usr.getListaGrupos().get(i-1).getNombre(),app.getBarra());
//									usr.getListaGrupos().set(i-1, grupo);
//		         					usr.getListaGrupos().get(i-1).addMensaje(mensaje);
//		         					grupo=usr.getListaGrupos().get(i-1);
//		         					grupo.guardarGrupo(app.getBarra());
//									usr.guardarUsuario(app.getBarra());
//									
//		         				}
//		         			}
//		         			else if(j==2&&grupo.getModerador().getCorreo().equals(usr.getCorreo())){
//		         				System.out.println("Correo:");
//		         	    		correo= retString();
//		         	    		if(correo.isEmpty())
//		         	    			continue;
//		         	    		if(app.existeDir("aplicacion"+app.getBarra()+"usuario"+app.getBarra()+correo)){
//		         	    			Usuario usr1 = new Estudiante(null, null,null,null);
//		         	    			
//									usr1= usr1.cargarUsuario(correo,app.getBarra());
//									
//									grupo.addUsuario(usr1);
//		         	    			usr1.addGrupo(grupo);
//									usr1.guardarUsuario(app.getBarra());
//		         	    		}
//		         			}
//		         			else if(j==3&&grupo.getModerador().getCorreo().equals(usr.getCorreo())){
//		         				for(Usuario u: grupo.getListaUsuarios()){
//		         					System.out.println(u.getCorreo());
//		         				}
//		         				System.out.println("Correo:");
//		         	    		correo= retString();
//		         	    		if(correo.isEmpty())
//		         	    			continue;
//		         	    		if(app.existeDir("aplicacion"+app.getBarra()+"usuario"+app.getBarra()+correo)){
//		         	    			Usuario usr1 = new Estudiante(null, null,null,null);
//		         	    			
//									usr1= usr1.cargarUsuario(correo,app.getBarra());
//									grupo.removeUsuario(usr1);
//									grupo.guardarGrupo(app.getBarra());
//		         	    			usr1.removeGrupo(grupo);
//									usr1.guardarUsuario(app.getBarra());
//		         	    		}
//		         			}
//		         			
//		         			else if(j==4&&grupo.isGrupoEstudio()){
//		         				GrupoEstudio gestudio= (GrupoEstudio) grupo.cargarGrupo(grupo.getNombre(),app.getBarra());
//		         				System.out.println(gestudio.listarPreguntas());
//		         				System.out.println("Responder a la pregunta (numero de la pregunta o 0):");
//		         				j= retOpcion();
//		         				if(j==0);
//		         				else{
//		         					System.out.println("Texto de la pregunta:");
//		         					String str=retString();
//		         					System.out.println("Desea enciar esta pregunta 1 si 0 no:");
//		         					if(retOpcion()==1){
//		         						gestudio= (GrupoEstudio) grupo.cargarGrupo(grupo.getNombre(),app.getBarra());
//		         						gestudio.getListaPreguntas().get(j-1).addRespuesta(str,usr);
//		         						gestudio.guardarGrupo(app.getBarra());
//		         					}
//		         				}
//		         				
//		         			}
//	         			}
//	         		}
//	             	break;
//	         	case 4://Listar contactos
//					usr=(Estudiante) usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//					
//         			for(Usuario u: usr.getContactos()){
//         				System.out.println(u.getNombre()+" "+u.getApellido()+" "+u.getCorreo());
//         			}
//	             	break;
//	         	case 5://Enviar mensaje a un usuario
//	         		System.out.println("Nuevo mensaje");
//	         		correo= null;
//         			System.out.println("Destinatario:");
//         			correo= retString();	
//	         		if(!app.existeDir("aplicacion"+app.getBarra()+"usuario"+app.getBarra()+correo)){
//	         			System.out.println("No existe el usuario");
//	         			break;
//	         		}
//	         		Usuario dst= new Estudiante(null, null, null, null);
//	         		dst= dst.cargarUsuario(correo,app.getBarra());
//	         		System.out.println("Asunto:");
//         			String asunto= retString();
//         			System.out.println("Mensaje, si escribes salto de linea significa que terminas el mensaje:");
//         			String mensaje= retString();
//         			System.out.println("¿Quieres mandar este mensaje? si(1 enter) no (0):");
//         			if(retOpcion()==0){
//         				break;
//         			}
//         			MensajeUsuario m= new MensajeUsuario(0, dst, mensaje, Calendar.getInstance(), asunto, usr);
//         			dst.addMensajeBuzon(m);
//         			
//					dst.guardarUsuario(app.getBarra());
//					
//         			break;
//	         	case 6://Buscar un usuario
//	         		System.out.println("Escriba el nombre del usuario que desea buscar:");
//	         		String str1 = retString();
//					app.buscarUsuarioDir(str1);
//					
//	             	break;
//	         	case 7://Buscar un grupo
//	         		System.out.println("Escriba el nombre del grupo que desea buscar:");
//	         		String str = retString();
//					app.buscarGrupoDir(str);
//					
//	             	break;
//	         	case 8://Unirse a un grupo
//	         		System.out.println("Nombre del grupo:");
//	         		String strgrupo =retString();
//	         		if(app.existeDir("aplicacion"+app.getBarra()+"grupo"+app.getBarra()+strgrupo)){
//	         			Grupo grupo = new Grupo(0, null, false, null);
//	         			grupo= grupo.cargarGrupo(strgrupo,app.getBarra());
//	         			if(grupo.isPrivado()){
//	         				break;
//	         			}
//	         			grupo.addUsuario(usr);
//	         			grupo.guardarGrupo(app.getBarra());
//	         			usr.addGrupo(grupo);
//	         			usr.guardarUsuario(app.getBarra());
//	         			
//	         		}
//	         		else{
//	         			System.out.println("No existe el grupo:"+strgrupo);
//	         		}
//	             	break;
//	         	case 9://Salir de un grupo
//					usr=(Estudiante)usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//					
//         			i=1;
//         			for(Grupo g: usr.getListaGrupos()){
//         				System.out.println("Numero:"+i++);
//         				System.out.println("Nombre:"+g.getNombre());
//         			}
//         			System.out.println("Grupo del que quiere salir (numero 0 o enter si no quiere salir)");
//         			i=retOpcion();
//         			if(i==0){
//         			}
//         			else if(i<= usr.getListaGrupos().size()){
//         				Grupo grupo= new Grupo(0, null, false, null);
//         				grupo=grupo.cargarGrupo(usr.getListaGrupos().get(i-1).getNombre(),app.getBarra());
//         				grupo.removeUsuario(usr);
//         				usr.getListaGrupos().remove(i-1);
//         				
//						usr.guardarUsuario(app.getBarra());
//						grupo.guardarGrupo(app.getBarra());
//						
//         			}
//	             	break;
//	         	case 10://Anadir un contacto
//					usr= (Estudiante) usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//					
//	         		System.out.println("Correo Usuario:");
//	         		correo=retString();
//	         		if(app.existeDir("aplicacion"+app.getBarra()+"usuario"+app.getBarra()+correo)){
//	         			Usuario cnt= new Estudiante(null,null,null,null);
//						
//						cnt=cnt.cargarUsuario(correo,app.getBarra());
//						usr.addContacto(cnt);
//						
//							usr.guardarUsuario(app.getBarra());
//						
//	         		}
//	         		else
//	         			System.out.println("No existe un usuario con ese correo");
//	         		break;
//	         	case 11://Quitar un contacto	         		
//					usr=(Estudiante) usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//					
//	         		i=1;
//         			for(Usuario u: usr.getContactos()){
//         				System.out.println("Numero:"+i++);
//         				System.out.println(u.getNombre()+" "+u.getApellido()+" "+u.getCorreo());
//         			}
//         			System.out.println("Elija el usuario que quiere eliminar(escriba el numero o 0 o enter si quiere salir):");
//         			i=retOpcion();
//         			if(i==0){}
//         			else if(i<=usr.getContactos().size()){
//         				usr.getContactos().remove(i-1);
//         				usr.guardarUsuario(app.getBarra());						
//         			}
//         			
//	             	break;
//	         	case 12://Crear Grupo
//	         	    System.out.println("Nombre de grupo ");
//	         	    String nombre=retString();
//	         	    System.out.println("Desea crear este grupo 1 si 0 o enter no:");
//	         	    if(retOpcion()!=1){
//	         	    	break;
//	         	    }
//	         	    System.out.println("Moderado 1 si 0 no donode usted sera el moderador:");
//	         	    int mod=retOpcion();
//	         	    System.out.println("Privado 1 si 0 no:");
//	         	    int prv=retOpcion();
//	         	    Grupo grupo= new Grupo(0, nombre, false, null);
//	         	    grupo.addUsuario(usr);
//	         	    usr.addGrupo(grupo);
//	         	    
//						usr.guardarUsuario(app.getBarra());
//					
//	         	    if(mod==1)
//	         	    	grupo.setModerador(usr);
//	         	    if(prv==1)
//	         	    	grupo.setPrivado(true);
//	         	    
//	         	   while(true){
//	         	    	System.out.println("0.Terminar");
//	         	    	System.out.println("1.Añadir usuario");
//	         	    	System.out.println("2.Buscar usuario");
//	         	    	i= retOpcion();
//	         	    	if(i==0){
//	         	    		break;
//	         	    	}
//	         	    	else if(i==1){
//	         	    		System.out.println("Correo:");
//	         	    		correo= retString();
//	         	    		if(app.existeDir("aplicacion"+app.getBarra()+"usuario"+app.getBarra()+correo)){
//	         	    			Usuario usr1 = new Estudiante(null, null,null,null);
//	         	    			
//								usr1= usr1.cargarUsuario(correo,app.getBarra());
//								
//								grupo.addUsuario(usr1);
//	         	    			usr1.addGrupo(grupo);
//								usr1.guardarUsuario(app.getBarra());
//								
//	         	    			
//	         	    		}
//	         	    		else{
//	         	    			System.out.println("No existe el usuario"+correo);
//	         	    		}
//	         	    	}
//	         	    	else if(i==2){
//	         	    		System.out.println("Parte del nombre del usuario:");
//							app.buscarUsuarioDir(retString());
//							
//	         	    	}
//	         	    	else;
//	         	    		
//	         	    }
//	         	   	File fu=new File("aplicacion"+app.getBarra()+"grupo"+app.getBarra()+grupo.getNombre());
//	         	   	fu.mkdir();
//						grupo.guardarGrupo(app.getBarra());
//					
//	             	break;
//	         	case 13://Salir
//	         		opcionSalir();
//	             	return;
//	             default:
//	            	 opcionInvalida();
//	            	 break;
//	             		
//	         	}
//         	}
//        }
//        else{
//        	Profesor usr = new Profesor(null, null, null, null);
//         	try {
//				while((usr=app.loginProfesor())==null);
//			} catch (ClassNotFoundException | IOException e) {
//				
//				e.printStackTrace();
//			}
//         	System.out.println("Binvenido/a:"+usr.getNombre()+" "+usr.getApellido());
//         	while (true){
//	         	listarOpcionesUsurio();
//	         	listarOpcionesProfesor();
//	         	switch(retOpcion()){
//	         	case 1://Ver Perfil
//	         		System.out.println(usr.verPerfil());
//	         		break;
//	         	case 2://Listar mensajes
//	         		int i;
//	         		while (true){
//		         		i=1;
//						usr=(Profesor)usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//						
//		         		for(MensajeUsuario m:usr.getBuzon().getMensajes()){
//		         			System.out.println("Mensaje:"+ i++);
//		         			System.out.println("Autor:"+m.getRemitente().getCorreo());
//		         			System.out.println("Ausnto:"+m.getCuerpo());
//		         		}
//		         		System.out.println();
//		         		System.out.println("Ver mensaje (escriba el numero del mensaje que quiere ver o 0 para salir):");
//		         		i=retOpcion();
//		         		if (i==0){
//		         			break;
//		         		}
//		         		else if(usr.getBuzon().getMensajesNoLeidos().size()>=i){
//		         			System.out.println(usr.getBuzon().getMensajes().get(i-1));
//		         		}
//	         		}
//	         		break;
//	         	case 3://Listar mis grupos
//					usr=(Profesor)usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//					
//	         		while(true){
//	         			i=1;
//	         			for(Grupo g: usr.getListaGrupos()){
//	         				System.out.println("Numero:"+i++);
//	         				System.out.println("Nombre:"+g.getNombre());
//	         			}
//	         			System.out.println("Para entrar a ver un grupo pulse su numero o 0 para salir:");
//	         			i= retOpcion();
//	         			if (i==0)
//	         				break;
//	         			else if(usr.getListaGrupos().size()>=i){
//	         				Grupo grupo= new Grupo(0, null, false, null);
//	         				
//							grupo=grupo.cargarGrupo(usr.getListaGrupos().get(i-1).getNombre(),app.getBarra());
//							System.out.println(grupo);
//	         				usr.getListaGrupos().set(i-1, grupo);
//	         				
//	         				
//		         			System.out.println("1.Escribir un mensaje:");
//		         			
//		         			if(grupo.getModerador()!=null&&grupo.getModerador().getCorreo().equals(usr.getCorreo())){
//	         					System.out.println("2.Añadir usuario");
//	         					System.out.println("3.Expulsar usuario");
//	         				}
//		         			if(grupo.isGrupoEstudio()){
//	         					System.out.println("4.Enviar pregunta");
//	         					System.out.println("5.Ver pregunta");
//	         				}
//		         			int j=retOpcion();
//		         			if (j==1){
//		         				System.out.println("Escriba su mensjae y pulse enter");
//		         				String msj=retString();
//		         				System.out.println("Quiere mandar este mensaje 1 si 0 o enter no:");
//		         				if(retOpcion()==1){
//		         					MensajeGrupo mensaje= new MensajeGrupo(0, msj, Calendar.getInstance(), usr.getListaGrupos().get(i-1), usr);
//			         				grupo=grupo.cargarGrupo(grupo.getNombre(),app.getBarra());
//									usr.getListaGrupos().set(i-1, grupo);
//		         					usr.getListaGrupos().get(i-1).addMensaje(mensaje);
//		         					grupo=usr.getListaGrupos().get(i-1);
//		         					grupo.guardarGrupo(app.getBarra());
//									usr.guardarUsuario(app.getBarra());
//									
//		         				}
//		         			}
//		         			else if(j==2&&grupo.getModerador().getCorreo().equals(usr.getCorreo())){
//		         				System.out.println("Correo:");
//		         	    		correo= retString();
//		         	    		if(correo.isEmpty())
//		         	    			continue;
//		         	    		if(app.existeDir("aplicacion"+app.getBarra()+"usuario"+app.getBarra()+correo)){
//		         	    			Usuario usr1 = new Estudiante(null, null,null,null);
//		         	    			
//									usr1= usr1.cargarUsuario(correo,app.getBarra());
//									
//									grupo.addUsuario(usr1);
//		         	    			usr1.addGrupo(grupo);
//									usr1.guardarUsuario(app.getBarra());
//		         	    		}
//		         			}
//		         			else if(j==3&&grupo.getModerador().getCorreo().equals(usr.getCorreo())){
//		         				for(Usuario u: grupo.getListaUsuarios()){
//		         					System.out.println(u.getCorreo());
//		         				}
//		         				System.out.println("Correo:");
//		         	    		correo= retString();
//		         	    		if(correo.isEmpty())
//		         	    			continue;
//		         	    		if(app.existeDir("aplicacion"+app.getBarra()+"usuario"+app.getBarra()+correo)){
//		         	    			Usuario usr1 = new Estudiante(null, null,null,null);
//		         	    			
//									usr1= usr1.cargarUsuario(correo,app.getBarra());
//									grupo.removeUsuario(usr1);
//									grupo.guardarGrupo(app.getBarra());
//		         	    			usr1.removeGrupo(grupo);
//									usr1.guardarUsuario(app.getBarra());
//		         	    		}
//		         			}
//		         			else if(j==4&&grupo.isGrupoEstudio()){
//		         				System.out.println("Escriba el texto de la pregunta");
//		         				String msj=retString();
//		         				System.out.println("Quiere mandar este mensaje 1. si 0. no:");
//		         				if(retOpcion()==1){
//		         					GrupoEstudio gestudio= new GrupoEstudio(0, null, false, null, null); 
//		         					gestudio=(GrupoEstudio) grupo.cargarGrupo(usr.getListaGrupos().get(i-1).getNombre(),app.getBarra());
//		         					gestudio.crearPregunta(0, msj, usr);
//		         					usr.getListaGrupos().set(i-1, gestudio );
//		         					gestudio.guardarGrupo(app.getBarra());
//									usr.guardarUsuario(app.getBarra());
//									
//		         				}
//		         				
//		         			}
//		         			else if(j==5&&grupo.isGrupoEstudio()){
//		         				GrupoEstudio gestudio= (GrupoEstudio) grupo.cargarGrupo(grupo.getNombre(),app.getBarra());
//		         				System.out.println(gestudio.listarPreguntas());
//		         				System.out.println("Ver respuestas a la pregunta (numero de la pregunta):");
//		         				j= retOpcion();
//		         				if(j==0);
//		         				else{
//		         					System.out.println(gestudio.getListaPreguntas().get(j-1).listarRespuestas());
//		         				}
//		         				
//		         			}
//	         			}
//	         		}
//	             	break;
//	         	case 4://Listar contactos
//					usr=(Profesor) usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//					
//         			for(Usuario u: usr.getContactos()){
//         				System.out.println(u.getNombre()+" "+u.getApellido()+" "+u.getCorreo());
//         			}
//	             	break;
//	         	case 5://Enviar mensaje a un usuario
//	         		System.out.println("Nuevo mensaje");
//	         		correo= null;
//         			System.out.println("Destinatario:");
//         			correo= retString();	
//	         		if(!app.existeDir("aplicacion"+app.getBarra()+"usuario"+app.getBarra()+correo)){
//	         			System.out.println("No existe el usuario");
//	         			break;
//	         		}
//	         		Usuario dst= new Estudiante(null, null, null, null);
//	         		dst= dst.cargarUsuario(correo,app.getBarra());
//	         		System.out.println("Asunto:");
//         			String asunto= retString();
//         			System.out.println("Mensaje, si escribes salto de linea significa que terminas el mensaje:");
//         			String mensaje= retString();
//         			System.out.println("¿Quieres mandar este mensaje? si(1 enter) no (0):");
//         			if(retOpcion()==0){
//         				break;
//         			}
//         			MensajeUsuario m= new MensajeUsuario(0, dst, mensaje, Calendar.getInstance(), asunto, usr);
//         			dst.addMensajeBuzon(m);
//         			
//					dst.guardarUsuario(app.getBarra());
//					
//         			break;
//	         	case 6://Buscar un usuario
//	         		System.out.println("Escriba el nombre del usuario que desea buscar:");
//	         		String str1 = retString();
//					app.buscarUsuarioDir(str1);
//					
//	             	break;
//	         	case 7://Buscar un grupo
//	         		System.out.println("Escriba el nombre del grupo que desea buscar:");
//	         		String str = retString();
//					app.buscarGrupoDir(str);
//					
//	             	break;
//	         	case 8://Unirse a un grupo
//	         		System.out.println("Nombre del grupo:");
//	         		String strgrupo =retString();
//	         		if(app.existeDir("aplicacion"+app.getBarra()+"grupo"+app.getBarra()+strgrupo)){
//	         			Grupo grupo = new Grupo(0, null, false, null);
//	         			grupo= grupo.cargarGrupo(strgrupo,app.getBarra());
//	         			if(grupo.isPrivado()){
//	         				break;
//	         			}
//	         			grupo.addUsuario(usr);
//	         			grupo.guardarGrupo(app.getBarra());
//	         			usr.addGrupo(grupo);
//	         			usr.guardarUsuario(app.getBarra());
//	         			
//	         		}
//	         		else{
//	         			System.out.println("No existe el grupo:"+strgrupo);
//	         		}
//	             	break;
//	         	case 9://Salir de un grupo
//					usr=(Profesor)usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//					
//         			i=1;
//         			for(Grupo g: usr.getListaGrupos()){
//         				System.out.println("Numero:"+i++);
//         				System.out.println("Nombre:"+g.getNombre());
//         			}
//         			System.out.println("Grupo del que quiere salir (numero 0 o enter si no quiere salir)");
//         			i=retOpcion();
//         			if(i==0){
//         			}
//         			else if(i<= usr.getListaGrupos().size()){
//         				Grupo grupo= new Grupo(0, null, false, null);
//         				grupo=grupo.cargarGrupo(usr.getListaGrupos().get(i-1).getNombre(),app.getBarra());
//         				grupo.removeUsuario(usr);
//         				usr.getListaGrupos().remove(i-1);
//         				
//						usr.guardarUsuario(app.getBarra());
//						grupo.guardarGrupo(app.getBarra());
//						
//         			}
//	             	break;
//	         	case 10://Anadir un contacto
//					usr= (Profesor) usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//					
//	         		System.out.println("Correo Usuario:");
//	         		correo=retString();
//	         		if(app.existeDir("aplicacion"+app.getBarra()+"usuario"+app.getBarra()+correo)){
//	         			Usuario cnt= new Estudiante(null,null,null,null);
//						
//						cnt=cnt.cargarUsuario(correo,app.getBarra());
//						usr.addContacto(cnt);
//						
//							usr.guardarUsuario(app.getBarra());
//						
//	         		}
//	         		else
//	         			System.out.println("No existe un usuario con ese correo");
//	         		break;
//	         	case 11://Quitar un contacto
//	         		
//					usr=(Profesor) usr.cargarUsuario(usr.getCorreo(),app.getBarra());
//					
//	         		i=1;
//         			for(Usuario u: usr.getContactos()){
//         				System.out.println("Numero:"+i++);
//         				System.out.println(u.getNombre()+" "+u.getApellido()+" "+u.getCorreo());
//         			}
//         			System.out.println("Elija el usuario que quiere eliminar(escriba el numero o 0 o enter si quiere salir):");
//         			i=retOpcion();
//         			if(i==0){}
//         			else if(i<=usr.getContactos().size()){
//         				usr.getContactos().remove(i-1);
//         				usr.guardarUsuario(app.getBarra());						
//         			}
//         			
//	             	break;
//	         	case 12://Crear Grupo
//	         	    System.out.println("Nombre de grupo ");
//	         	    String nombre=retString();
//	         	    System.out.println("Desea crear este grupo 1 si 0 o enter no:");
//	         	    if(retOpcion()!=1){
//	         	    	break;
//	         	    }
//	         	    System.out.println("Moderado 1 si 0 no donode usted sera el moderador:");
//	         	    int mod=retOpcion();
//	         	    System.out.println("Privado 1 si 0 no:");
//	         	    int prv=retOpcion();
//	         	    Grupo grupo= null;
//	         	    System.out.println("Grupo de estudio 1 si 0 no:");
//	         	    if(retOpcion()==1){
//	         	    	 grupo= new GrupoEstudio(0, nombre, false, null, usr);
//	         	    }
//	         	    else{
//	         	    	 grupo= new Grupo(0, nombre, false, null);
//	         	    }
//	         	    grupo.addUsuario(usr);
//	         	    usr.addGrupo(grupo);
//	         	    
//					usr.guardarUsuario(app.getBarra());
//					
//	         	    if(mod==1)
//	         	    	grupo.setModerador(usr);
//	         	    if(prv==1)
//	         	    	grupo.setPrivado(true);
//	         	    
//	         	   while(true){
//	         	    	System.out.println("0.Terminar");
//	         	    	System.out.println("1.Añadir usuario");
//	         	    	System.out.println("2.Buscar usuario");
//	         	    	i= retOpcion();
//	         	    	if(i==0){
//	         	    		break;
//	         	    	}
//	         	    	else if(i==1){
//	         	    		System.out.println("Correo:");
//	         	    		correo= retString();
//	         	    		if(app.existeDir("aplicacion"+app.getBarra()+"usuario"+app.getBarra()+correo)){
//	         	    			Usuario usr1 = new Estudiante(null, null,null,null);
//	         	    			
//								usr1= usr1.cargarUsuario(correo,app.getBarra());
//								
//								grupo.addUsuario(usr1);
//	         	    			usr1.addGrupo(grupo);
//								usr1.guardarUsuario(app.getBarra());
//								
//	         	    			
//	         	    		}
//	         	    		else{
//	         	    			System.out.println("No existe el usuario"+correo);
//	         	    		}
//	         	    	}
//	         	    	else if(i==2){
//	         	    		System.out.println("Parte del nombre del usuario:");
//							app.buscarUsuarioDir(retString());
//							
//	         	    	}
//	         	    	else;
//	         	    		
//	         	    }
//	         	   	File fu=new File("aplicacion"+app.getBarra()+"grupo"+app.getBarra()+grupo.getNombre());
//	         	   	fu.mkdir();
//						grupo.guardarGrupo(app.getBarra());
//					
//	             	break;
//	         	case 13://Salir
//	         		opcionSalir();
//	             	return;
//
//
//
//	        	case 14://Enviar mensaje alumnos
//	         		System.out.println(usr.listarGruposEstudio());
//	         		System.out.println("Elija un grupo o 0:");
//	         		i= retOpcion();
//	         		if(i==0||i>usr.getListaGrupos().size())
//	         			break;
//	         		System.out.println("Escriba el mensaje:");
//	         		String msj= retString();
//	         		System.out.println("�Quieres mandar este mensaje? si(1 enter) no (0):");
//					if(retOpcion()==0){
//         				break;
//         			}
//	         		Grupo gestudio= new GrupoEstudio(0, null, false, null, null);
//	         		gestudio=  gestudio.cargarGrupo(usr.getListaGrupos().get(i-1).getNombre(),app.getBarra());
//	         		System.out.println("Mensaje enviado a:");
//	         		for(Usuario u: gestudio.getListaUsuarios()){
//	         			System.out.println(u.getCorreo());
//						u=u.cargarUsuario(u.getCorreo(),app.getBarra());
//	         			MensajeUsuario msg= new MensajeUsuario(0, u, gestudio.getNombre(), Calendar.getInstance(),msj , usr);
//	         			u.addMensajeBuzon(msg);
//						u.guardarUsuario(app.getBarra());
//	         		}
//	         		break;
//	             default:
//	            	 opcionInvalida();
//	            	 break;
//	             		
//	         	}
//         	}
//        }
      

	       	
	}
	public static void opcionSalir(){
		System.out.println("Adios.");
	}
	public static void opcionInvalida(){
		System.out.println("Opcion no valida.");
	}
	public static String retString(){
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		String str;
		try {
			str = bufferRead.readLine();
			return str;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public static int retOpcion(){
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		 System.out.println("Introduzca una opcion:");
	     try {
			String str = bufferRead.readLine();
			if(str.isEmpty())
				return 0;
			int i= Integer.parseInt(str);
			return i;
		} catch (IOException e) {
			
			return 0;
		}
	}
	  public static void listarOpcionesUsurio(){
		  System.out.println("Elija una opcion(para ello intoroduzca el numero correspondiente):");
		  System.out.println("1.Ver Perfil");
		  System.out.println("2.Listar mensajes");
		  System.out.println("3.Listar mis grupos");
		  System.out.println("4.Listar contactos");
		  System.out.println("5.Enviar mensaje a un usuario");
		  System.out.println("6.Buscar un usuario");
		  System.out.println("7.Buscar un grupo");
		  System.out.println("8.Unirse a un grupo");
		  System.out.println("9.Salir de un grupo");
		  System.out.println("10.Añadir un contacto");
		  System.out.println("11.Quitar un contacto");
		  System.out.println("12.Crear grupo");
		  System.out.println("13.Salir");
	  }
	  public static void listarOpcionesProfesor(){
		 System.out.println("14.Enviar mensaje alumnos");
	  }
}
