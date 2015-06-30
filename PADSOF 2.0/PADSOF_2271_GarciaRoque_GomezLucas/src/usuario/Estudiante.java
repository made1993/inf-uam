package usuario;
/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase Estudiante
 */
public class Estudiante extends Usuario{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor de la clase Estudiante
	 * @param nombre nombre del estudiante
	 * @param apellido apellido del estudiante
	 * @param correo correo del estudiante
	 * @param password password del estudiante
	 */
	public Estudiante(String nombre,String apellido,String correo,String password) {
		super(nombre, apellido, correo, password);
	}

	@Override
	public boolean isProfesor() {
		return false;
	}
	@Override
	public String toString() {
		return "Estudiante [IdUsuario=" + getIdUsuario() + ", Nombre="
				+ getNombre() + ", Correo=" + getCorreo()
				+ ", Password()=" + getPassword();
	}
}
