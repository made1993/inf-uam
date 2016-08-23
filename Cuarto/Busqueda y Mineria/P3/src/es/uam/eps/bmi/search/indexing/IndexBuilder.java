package es.uam.eps.bmi.search.indexing;

import java.util.*;

import es.uam.eps.bmi.search.parsing.HTMLSimpleParser;

public class IndexBuilder {

	/*Comentar como he cambiado el constructor a angel*/
	public static void main (String[] args){
		
		List<String> paths = BasicIndexing.lectorXML();
		HTMLSimpleParser parseador = new HTMLSimpleParser();
		String destino;
		
		//Creamos el índice básico
		Index i = new BasicIndexing(Boolean.parseBoolean(paths.get(2)),Boolean.parseBoolean(paths.get(3)));
		destino = paths.get(1).concat("/basic");
		i.build(paths.get(0),destino,parseador);
		
		//Creamos el índice sin stopwords
		List<String> stopwords = new ArrayList<String>();
		i = new StopwordIndex(stopwords,"stopwords.txt",Boolean.parseBoolean(paths.get(2)),Boolean.parseBoolean(paths.get(3)));
		destino = paths.get(1).concat("/stopword");
		i.build(paths.get(0), destino, parseador);
		
		//Cremos el índice sin stopwords y haciendo stemmin
		stopwords = new ArrayList<String>();
		i = new StemIndex(stopwords,"stopwords.txt",Boolean.parseBoolean(paths.get(2)),Boolean.parseBoolean(paths.get(3)));
		destino = paths.get(1).concat("/stem");
		i.build(paths.get(0), destino, parseador);
		
				
		return;
	}
	
}
