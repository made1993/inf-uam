package es.uam.eps.bmi.search.searching;

import es.uam.eps.bmi.search.ScoredTextDocument;
import es.uam.eps.bmi.search.indexing.Index;

import java.util.*;

/**
 * Interfaz para crear un buscador
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public interface Searcher{
	
	public void build(Index index);
	public List<ScoredTextDocument> search(String query);
	
}

