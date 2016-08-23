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
	/*TODO*/
	/**
	 * Constructor de la clase {@link MensajeGrupo}
	 * @param cuerpo
	 * @param fecha
	 * @param grupo
	 * @param remitente
	 */
	public MensajeGrupo(int id, String cuerpo, Usuario remitente) {
		super(id, cuerpo, remitente);
		
	}

	
	@Override
	public String toString() {
		return " ["+this.getRemitente().getCorreo() +"]"+this.getCuerpo();
	}
	
}
