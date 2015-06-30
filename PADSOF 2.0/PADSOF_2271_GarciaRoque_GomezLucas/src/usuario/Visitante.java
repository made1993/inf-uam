package usuario;

public class Visitante extends Usuario{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Visitante(String nombre, String apellido, String correo,
			String password) {
		super(nombre, apellido, correo, password);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public boolean isProfesor() {
		return false;
	}
	
	@Override
	public boolean isVisitante(){
		return true;
	}

}
