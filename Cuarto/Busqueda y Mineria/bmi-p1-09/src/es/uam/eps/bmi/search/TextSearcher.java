package es.uam.eps.bmi.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

import es.uam.eps.bmi.search.indexing.LuceneIndex;
import es.uam.eps.bmi.search.searching.LuceneSearcher;

/**
 * Clase compuesta por un main para realizar busquedas sobre unos indices
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class TextSearcher {

	public static void main(String []args) throws IOException{
		LuceneSearcher searcher = new LuceneSearcher();
		LuceneIndex index = new LuceneIndex();
		index.load(args[0]);
		searcher.build(index);
		String consulta = new String();
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		while(true){
			System.out.print("Introduzca una consulta: ");
			consulta = entrada.readLine();
			consulta = consulta.trim();
			if(consulta.length() == 0 || consulta.charAt(0) == 'q')
				System.exit(0);
			for(ScoredTextDocument s: searcher.search(consulta)){
				System.out.println(s.getDocId()+" "+s.getScore());
			}
		}			
	}
	
}
