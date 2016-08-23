package es.uam.eps.bmi.search.parsing;

import org.jsoup.Jsoup;

/**
 * Clase que implementa la interfaz TextParser, para parsear un texto
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class Parser implements TextParser{

	@Override
	public String parse(String text) {
		return Jsoup.parse(text).text();
	}
	

}
