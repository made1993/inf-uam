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
	 * @param nombre
	 * @param apellido
	 * @param correo
	 * @param password
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
