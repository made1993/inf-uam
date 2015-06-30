package mensaje;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import usuario.Usuario;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase Mensaje
 */
public class Mensaje implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private int idMensaje;
	private String cuerpo;
	private Calendar fecha;
	private Usuario Remitente;
	
	/**
	 * Constructor de la clase Mensaje
	 * @param cuerpo
	 * @param fecha
	 * @param remitente
	 */
	public Mensaje(int id, String cuerpo, Usuario remitente) {
		this.idMensaje=id;
		this.cuerpo = cuerpo;
		this.fecha = Calendar.getInstance();
		Remitente = remitente;
	}
	
	/**
	 * @return the idMensaje
	 */
	public int getIdMensaje() {
		return idMensaje;
	}
	/**
	 * @param idMensaje the idMensaje to set
	 */
	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

	/**
	 * @return the cuerpo
	 */
	public String getCuerpo() {
		return cuerpo;
	}
	/**
	 * @param cuerpo the cuerpo to set
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	/**
	 * @return the fecha
	 */
	public Calendar getFecha() {
		return fecha;
	}
	/**
	 * @return the fecha
	 */
	public String getFechaImp() {
		SimpleDateFormat format1 = new SimpleDateFormat("hh:mm dd/MM/yyyy");
		return format1.format(fecha.getTime()); 
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the remitente
	 */
	public Usuario getRemitente() {
		return Remitente;
	}
	/**
	 * @param remitente the remitente to set
	 */
	public void setRemitente(Usuario remitente) {
		Remitente = remitente;
	}
	
	@Override
	public String toString() {
		return "Mensaje [cuerpo=" + cuerpo
				+ ", fecha=" + fecha + ", Remitente=" + Remitente + "]";
	}
	
	
	
}
