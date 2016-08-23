package mensaje;
import java.util.Calendar;

import usuario.Usuario;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase MensajeUsuario
 */
public class MensajeUsuario extends Mensaje{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario destinatario;
	private boolean leido;
	private String sujeto;
	
	/**
	 * Constructor de la clase {@link MensajeUsuario}
	 * @param destinatario
	 * @param cuerpo
	 * @param fecha
	 * @param sujeto
	 * @param remitente
	 */
	public MensajeUsuario(int id, Usuario destinatario,String cuerpo, Calendar fecha, String sujeto,Usuario remitente) {
		super(id,cuerpo, fecha, remitente);
		this.destinatario=destinatario;
		this.leido=false;
		this.sujeto=sujeto;
	}
	/**emitente
	 * @return the idDestinatario
	 */
	public Usuario getIdDestinatario() {
		return destinatario;
	}
	/**
	 * @param destinatario the idDestinatario to set
	 */
	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}
	/**
	 * @return the leido
	 */
	public boolean isLeido() {
		return leido;
	}
	/**
	 * @param leido the leido to set
	 */
	public void setLeido(boolean leido) {
		this.leido = leido;
	}
	/**
	 * @return the sujeto
	 */
	public String getSujeto() {
		return sujeto;
	}
	/**
	 * @param sujeto the sujeto to set
	 */
	public void setSujeto(String sujeto) {
		this.sujeto = sujeto;
	}
	
	/**
	 * @return el mensaje leido
	 */
	public String verMensaje() {
		this.leido=true;
		return this.toString();
	}
	
	@Override
	public String toString() {
		setLeido(true);
		return "[Remitente:"+ getRemitente().getNombre()+"| fecha:"+this.getFecha().getTime()+"]\n"+this.getSujeto()+"\n";
	}
	
}
