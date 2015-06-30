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
	 * @param mensaje
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

	/**
	 * @return los mensajes no leidos
	 */
	public ArrayList<MensajeUsuario> getMensajesNoLeidos() {
		ArrayList<MensajeUsuario> m=new ArrayList<MensajeUsuario>();
		for(MensajeUsuario mu: mensajes){
			if(!mu.isLeido())
				m.add(mu);
		}
		return m;
	}

	
	@Override
	public String toString() {
		String imp="";
		for(MensajeUsuario m:mensajes){
			imp+=m+"\n";
		}
		return imp;
	}

	public boolean removeMensaje(MensajeUsuario mensaje) {
		return mensajes.remove(mensaje);
	}
	
	
	
	
}
