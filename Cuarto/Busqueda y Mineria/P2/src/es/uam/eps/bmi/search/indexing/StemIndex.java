package es.uam.eps.bmi.search.indexing;

import java.util.*;

import org.tartarus.snowball.ext.*;

/**
 * Clase que implementa la interfaz index, creando un indice sin stopwords y haciendo stemming de un buscador.
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class StemIndex extends StopwordIndex{

	private englishStemmer stemmer;
	
	public StemIndex(List<String> stopwords,String file, boolean indicesDisco ,boolean guardarIndices) {
		super(stopwords,file, indicesDisco, guardarIndices);
		this.stemmer = new englishStemmer();
		this.inputPath="stem";
	}
	
	/** Normalizamos las palabras antes de crear el índice con ellas, quitando stopwords
	 *  y haciendo stemming
	 * 
	 * @param s documento leido del zip
	 * @return array con cada una de las palabras del documento normalizadas
	 */
	@Override
	public String[] normalizar(String doc){
		String texto[] = super.normalizar(doc);
		List<String> aux= new ArrayList<>();
		
		for(String word: texto){
			stemmer.setCurrent(word);
			stemmer.stem();
			aux.add(stemmer.getCurrent());
		}
		
		String[] ret= new String[aux.size()];
		return aux.toArray(ret);
	}

}
