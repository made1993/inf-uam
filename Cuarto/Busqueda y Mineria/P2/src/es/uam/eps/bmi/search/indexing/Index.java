package es.uam.eps.bmi.search.indexing;

import java.util.*;

import es.uam.eps.bmi.search.TextDocument;
import es.uam.eps.bmi.search.parsing.TextParser;

/**
 * Interfaz con los metodos que debera implementar un indice.
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public interface Index{
	void build(String inputCollectionPath, String outputIndexPath, TextParser textParser);
	void load(String indexPath);
	String getPath();
	List<String> getDocIds();
	TextDocument getDocument(String docId);
	List<String> getTerms();
	List<Posting> getTermPostings (String term);
	HashMap<String, Double> getModulos();
	String[] normalizar(String s);
}
