package usuario;

import grupo.*;


import mensaje.MensajeUsuario;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase Profesor
 */
public class Profesor extends Usuario{
	//TODO hay un fichero jar con estadisticas para mostrar % de alumnos que responden...
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * COnstructor de la clase {@link Profesor}
	 * @param nombre nombre del profesor
	 * @param apellido apellido del profesor
	 * @param correo correo del profesor
	 * @param password password del profesor
	 */
	public Profesor(String nombre,  String apellido, String correo, String password) {
		super(nombre, apellido, correo, password);
		
	}
		
	
	@Override
	public boolean isProfesor(){
		return true;
	}
	
	@Override
	public String toString() {
		return "Profesor [IdUsuario=" + getIdUsuario() + ", Nombre="
				+ getNombre() + ", Correo=" + getCorreo()
				+ ", Password()=" + getPassword();
	}
}
