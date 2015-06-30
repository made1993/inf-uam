package mensaje;

import usuario.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase Mensaje Grupo hereda de mensaje
 */
public class MensajeGrupo extends Mensaje{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor de la clase {@link MensajeGrupo}
	 * @param cuerpo cuerpo del mensaje
	 * @param remitente el que envia el mensaje
	 */
	public MensajeGrupo(String cuerpo, Usuario remitente) {
		super(cuerpo, remitente);
		
	}

	
	@Override
	public String toString() {
		return " ["+this.getRemitente().getCorreo() +"]"+this.getCuerpo();
	}


	
}
