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
	 * @param cuerpo
	 * @param remitente
	 */
	public Respuesta(int id,String cuerpo, Estudiante remitente) {
		super(id,cuerpo, remitente);
	}
	@Override
	public String toString(){
		return "["+this.getRemitente().getCorreo()+" "+this.getFecha().getTime()+"]\n"+this.getCuerpo();
	}
}
