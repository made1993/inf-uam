package buzon;
import java.io.Serializable;
import java.util.ArrayList;

import mensaje.MensajeUsuario;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase Grupo
 */
public class Buzon implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<MensajeUsuario> mensajes;
	
	/**
	 * Constructor de la clase Buzon
	 */
	public Buzon() {
		this.mensajes = new ArrayList<MensajeUsuario>();
	}

	/**
	 * Añade un mensaje al buzon
	 * @param mensaje mensaje para a�adir al buzon
	 * @return true si lo añade false si no
	 */
	public boolean addMensajeBuzon(MensajeUsuario mensaje){
		return mensajes.add(mensaje);
	}

	/**
	 * @return the mensajes
	 */
	public ArrayList<MensajeUsuario> getMensajes() {
		return mensajes;
	}


	
	@Override
	public String toString() {
		String imp="";
		for(MensajeUsuario m:mensajes){
			imp+=m+"\n";
		}
		return imp;
	}

	/**
	 * Elimina una mensaje del buzon
	 * @param mensaje El mensaje a eliminar
	 * @return true en caso de exito, false en fallo
	 */
	public boolean removeMensaje(MensajeUsuario mensaje) {
		for(MensajeUsuario m: mensajes){
			if(m.getCuerpo().equals(mensaje.getCuerpo()) 
					&& m.getRemitente().getCorreo().equals(mensaje.getRemitente().getCorreo()) 
							&& m.getSujeto().equals(mensaje.getSujeto())){
				return mensajes.remove(m);
			}
		}
		return mensajes.remove(mensaje);
	}
	
	
	
	
}
