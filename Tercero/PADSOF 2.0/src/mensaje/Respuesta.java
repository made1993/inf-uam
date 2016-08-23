package mensaje;


import usuario.Estudiante;


/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase Grupo
 */
public class Respuesta extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase {@link Respuesta}
	 * @param cuerpo cuerpo de la respuesta
	 * @param remitente usaurio que envia la respuesta
	 */
	public Respuesta(String cuerpo, Estudiante remitente) {
		super(cuerpo, remitente);
	}
	@Override
	public String toString(){
		return "["+this.getRemitente().getCorreo()+" "+this.getFecha().getTime()+"]\n"+this.getCuerpo();
	}
}
