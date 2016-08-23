package mensaje;
import java.util.ArrayList;

import usuario.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase Grupo
 */
public class Pregunta extends Mensaje{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Respuesta> listaRespuestas;
	
	
/**
 * Constructor de pregunta
 * @param id
 * @param cuerpo
 * @param remitente
 */
	public Pregunta(int id, String cuerpo, Profesor remitente) {
		super(id,cuerpo,remitente);
		this.listaRespuestas = new ArrayList<Respuesta>();
	}
	
	/**
	 * Devuelve una respuesta
	 * @param pos
	 * @return la respuesta de la posicion pos
	 */
	public Respuesta getRespuesta(int pos){
		return listaRespuestas.get(pos);
	}
	
	/**
	 * @return the listaRespuestas
	 */
	public ArrayList<Respuesta> getListaRespuestas() {
		return listaRespuestas;
	}

	/**
	 * @param listaRespuestas the listaRespuestas to set
	 */
	public void setListaRespuestas(ArrayList<Respuesta> listaRespuestas) {
		this.listaRespuestas = listaRespuestas;
	}

	/**
	 * 
	 * @param estudiante
	 * @return la respuesta de un estudiante
	 */
	public Respuesta getRespuestaDe(Estudiante estudiante){
		for(Respuesta r: listaRespuestas){
			if(r.getRemitente().equals(estudiante))
				return r;
		}
		return null;
	}
	
	/**
	 * anade una respuesta
	 * @param cuerpo
	 * @param remitente
	 */
	public void addRespuesta(String cuerpo, Estudiante remitente){
		Respuesta rsp= new Respuesta(0, cuerpo, remitente);
		this.getListaRespuestas().add(rsp);
	}

	/**
	 * @return the listaRespuestas
	 */
	public ArrayList<Respuesta> getRespuestas(){
		return listaRespuestas;
	}
	/**
	 * Devuelve informacion sobre las respuestas de la pregunta
	 * @return Un String con la informacion de las respuestas ok
	 * 
	 */
	public String listarRespuestas(){
		String imp="";
		int i=1;
		for(Respuesta p: listaRespuestas)
			imp+=Integer.toString(i++)+"."+p.toString()+"\n";
		return imp;
	}
	@Override
	public String toString() {
		return "["
				//+ ""+getRemitente().getCorreo()+""
						+ ","+getFecha().getTime()+"]\n "+getCuerpo() ;
	}
	

}
