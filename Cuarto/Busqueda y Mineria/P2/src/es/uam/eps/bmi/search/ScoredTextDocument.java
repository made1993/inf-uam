package es.uam.eps.bmi.search;

/**
 * Clase que asociada a un documento, que nos indica la puntuación del mismo dada una consulta.
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class ScoredTextDocument implements Comparable<ScoredTextDocument>{

	private String id;
	private double score;
	
	public ScoredTextDocument(String id,float score){
		this.id = id;
		this.score = score;
	}
	
	/**
	 * Compara la puntuacion de un documento con otro dado.
	 * 
	 * @param scoreddoc el documento con el que comparar.
	 * @return -1 si el documento tiene una puntuacion peor que el dado.
	 * 1 si la puntuacion es mejor. 0 si tienen la misma puntuacion.
	 */
	@Override
	public int compareTo(ScoredTextDocument scoreddoc) {
		if(this.score < scoreddoc.getScore()) return -1;
		else if(this.score > scoreddoc.getScore()) return 1;
		else return 0;
		/*Double d = this.score;
		Double d2 = scoreddoc.getScore();
		return d.compareTo(d2);*/
	}
	/**
	 * Devuelve el id del documento.
	 * @return un string con el id del documento.
	 */
	public String getDocId(){
		return this.id;
	}
	/**
	 * Devuelve la puntuacion del documento.
	 * @return un double con la puntuacion del documento.
	 */
	public double getScore(){
		return this.score;
	}
	/**
	 * Modifica la puntuacion del documento
	 * @param score un double con la nueva puntuacion.
	 */
	public void setScore(double score){
		this.score = score;
	}
}
