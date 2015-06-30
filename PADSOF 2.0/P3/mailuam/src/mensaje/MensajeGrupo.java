package mensaje;
import grupo.Grupo;

import java.util.Calendar;

import usuario.Usuario;

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
	private Grupo grupo;
	/*TODO*/
	/**
	 * Constructor de la clase {@link MensajeGrupo}
	 * @param cuerpo
	 * @param fecha
	 * @param grupo
	 * @param remitente
	 */
	public MensajeGrupo(int id, String cuerpo, Calendar fecha, Grupo grupo, Usuario remitente) {
		super(id, cuerpo, fecha, remitente);
		this.grupo=grupo;
		
	}
	/**
	 * @return the grupo
	 */
	public Grupo getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the Grupo to set
	 */
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}


	
	@Override
	public String toString() {
		return " ["+this.getRemitente().getCorreo() +"]"+this.getCuerpo();
	}
	
}
