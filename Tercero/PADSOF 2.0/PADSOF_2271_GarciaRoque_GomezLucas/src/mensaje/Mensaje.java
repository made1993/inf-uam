package mensaje;

import mailUam.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private static int contId=0;
	private int idMensaje;
	private String cuerpo;
	private Calendar fecha;
	private Usuario Remitente;
	
	/**
	 * Constructor de la clase Mensaje
	 * @param cuerpo cuerpo del mensae
	 * @param remitente usuario que lo envia
	 */
	public Mensaje(String cuerpo, Usuario remitente) {
		cargarId();
		this.idMensaje=contId++;
		this.cuerpo = cuerpo;
		this.fecha = Calendar.getInstance();
		Remitente = remitente;
		guardarId();
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
	
	
	
	/**
	 * @return the contId
	 */
	public static int getContId() {
		return contId;
	}

	/**
	 * @param contId the contId to set
	 */
	public static void setContId(int contId) {
		Mensaje.contId = contId;
	}
	
	/**
	 * @return la barra del sistema operativo
	 */
	public String getBarra(){
		if (MailUam.getOS().equals("Windows 7")) {
			return MailUam.barraWindows;
		} else {
			return MailUam.barraLinuxMAC;
		}
	}
	
	/**
	 * Carga el id del fichero MailUam.ficheroId
	 */
	public void cargarId() {
		String file = MailUam.nmAplicacion + getBarra()	+ MailUam.ficheroId;
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			contId = (int) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
			contId = 0;
		} 
	}
	
	/**
	 * Guarda el id en el fichero MailUam.ficheroId
	 */
	public void guardarId() {
		String file = MailUam.nmAplicacion + getBarra()	+ MailUam.ficheroId;
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeObject(contId);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Mensaje ["+idMensaje+"cont "+contId+"cuerpo=" + cuerpo
				+ ", fecha=" + fecha + ", Remitente=" + Remitente + "]";
	}
	
	
	
}
