package grupo;
import java.util.ArrayList;

import mensaje.Pregunta;
import usuario.Profesor;
import usuario.Usuario;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase Grupo
 */
public class GrupoEstudio extends Grupo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Profesor profesor;
	private ArrayList<Pregunta> listaPreguntas;
	
	/**
	 * Constructor de la clase GrupoEstudio
	 * @param idGrupo
	 * @param nombre
	 * @param privado
	 * @param moderador
	 */
	public GrupoEstudio(int idGrupo, String nombre, boolean privado,
			Usuario moderador, Profesor profesor) {
		super(idGrupo, nombre, privado, moderador);
		listaPreguntas= new ArrayList<Pregunta>();
		this.profesor=profesor;
	}
	
	/**
	 * @return the listaPreguntas
	 */
	public ArrayList<Pregunta> getListaPreguntas() {
		return listaPreguntas;
	}

	/**
	 * @param listaPreguntas the listaPreguntas to set
	 */
	public void setListaPreguntas(ArrayList<Pregunta> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

	/**
	 * @return the idProfesor
	 */
	public Profesor getProfesor() {
		return profesor;
	}
	
	/**
	 * @param profesor the idProfesor to set
	 */
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
	/**
	 * Crea una pregunta en un grupo
	 * @param pregunta
	 * @param profesor
	 * @return true si se ha creado la pregunta false si no
	 */
	public boolean crearPregunta(int id,String pregunta, Profesor profesor){
		return listaPreguntas.add(new Pregunta(id, pregunta, profesor));
		
	}
	
	/**
	 * Lista todas las preguntas
	 * @return la lista de preguntas
	 */
	public String listarPreguntas(){
		String imp="";
		int i=1;
		for(Pregunta p: listaPreguntas)
			imp+=Integer.toString(i++)+"."+p.toString()+"\n";
		return imp;
	}
	
	@Override
	public boolean isGrupoEstudio(){
		return true;
	}
		
}
