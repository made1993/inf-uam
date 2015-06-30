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
	 * @param nombre
	 * @param apellido
	 * @param correo
	 * @param password
	 */
	public Profesor(String nombre,  String apellido, String correo, String password) {
		super(nombre, apellido, correo, password);
		
	}
	
	/**
	 * Crea un grupo de estudio
	 * @param id
	 * @param nombre
	 * @param privado
	 * @return si puede crear el grpuo false si no
	 */
	public boolean crearGrupoEstudio(int id,String nombre, boolean privado,boolean moderado){//TODO revisar el id		
		if(moderado)
			return addGrupo(new GrupoEstudio(id,nombre, privado,this,this));
		else
			return addGrupo(new GrupoEstudio(id,nombre, privado,null,this));
	}
	
	/**
	 * Envia un mensaje a los alumnos de un grupo
	 * @param id
	 * @param grupo
	 * @param cuerpo
	 * @param sujeto
	 * @return  si puede enviar un mensaje false si no
	 */
	public boolean enviarMensajeAlumnos(int id,GrupoEstudio grupo, String cuerpo, String sujeto){
		for (Grupo g: this.getListaGrupos()){
			if(g.equals(grupo)&& g.isGrupoEstudio()){
				for (Usuario u: grupo.getListaUsuarios()){
					u.addMensajeBuzon(new MensajeUsuario(id,u, cuerpo, grupo.getNombre(), this));
				}
				return true;
			}
		}
		return false;
	}
	public String listarGruposEstudio(){
		String imp="";
		int i=1;
		for(Grupo g: this.getListaGrupos()){
			if(g.isGrupoEstudio())
				imp+=Integer.toString(i)+"."+g.getNombre()+"\n";
			i++;
		}
		return imp;
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
