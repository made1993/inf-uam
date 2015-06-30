package grupo;

import java.util.ArrayList;

import mensaje.Pregunta;
import usuario.Profesor;
import usuario.Usuario;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase Grupo
 */
public class GrupoEstudio extends Grupo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Profesor profesor;
	private ArrayList<Pregunta> listaPreguntas;

	/**
	 * Constructor de la clase GrupoEstudio
	 * 
	 * @param idGrupo id del grupo
	 * @param nombre nombre del grupo
	 * @param privado si el grupo es provaod o no
	 * @param moderador usuario moderador del grupo
	 * @param profesor profesor del grupo
	 */
	public GrupoEstudio(int idGrupo, String nombre, boolean privado,
			Usuario moderador, Profesor profesor) {
		super(idGrupo, nombre, privado, moderador);
		listaPreguntas = new ArrayList<Pregunta>();
		this.profesor = profesor;
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
	 * 
	 * @param pregunta pregunar a crear
	 * @param profesor profesor que realiza la pregunta
	 * @return true si se ha creado la pregunta false si no
	 */
	public boolean crearPregunta(String pregunta, Profesor profesor) {
		return listaPreguntas.add(new Pregunta(pregunta, profesor));

	}

	/**
	 * Lista todas las preguntas
	 * 
	 * @return la lista de preguntas
	 */
	public String listarPreguntas() {
		String imp = "";
		int i = 1;
		for (Pregunta p : listaPreguntas)
			imp += Integer.toString(i++) + "." + p.toString() + "\n";
		return imp;
	}
	/**
	 * busca una pregunta
	 * @param nombre nomvre de la pregunta
	 * @return la pregunta buscada
	 */
	public Pregunta buscarPregunta(String nombre) {
		for (Pregunta p : listaPreguntas) {
			if (p.getCuerpo().equals(nombre))
				return p;
		}
		return null;

	}

	@Override
	public boolean isGrupoEstudio() {
		return true;
	}

}
