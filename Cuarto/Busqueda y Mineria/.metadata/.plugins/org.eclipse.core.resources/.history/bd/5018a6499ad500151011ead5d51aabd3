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

   /**  Cargamos un índice, con la ruta que nos dan, y creamos un buscador para hacer consultas al índice
     *  mostrando los resultados más acertados por pantalla.
	 * 
	 * @param args argumentos que le pasamos al main, los argumentos que usa son:
	 * 1ºEl lugar donde se encuentran los indices creados.
	 * Si no se han creado los indices todavia hacer una llamada a build en vez de a load
	 * en la linea32
	 */
	
	
	/*public static void main(String []args) throws IOException{
		LuceneSearcher searcher = new LuceneSearcher();
		LuceneIndex index = new LuceneIndex();
		System.out.println(args[0]);
		index.load(args[0]);
		searcher.build(index);
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String[] consultas= {"obama family tree","french lick resort and casino",
				"getting organized", "toilet", "mitchell college"};
		
		for(String consulta: consultas){
			System.out.println("\nconsulta: "+consulta);
			consulta = consulta.trim();
			String parts[]= consulta.split(" ");
			boolean flag=false;
			List<ScoredTextDocument> res= new ArrayList<>();
			for(String s: parts){
				if(consulta.length() == 0 || consulta.charAt(0) == 'q')
					System.exit(0);
				for(ScoredTextDocument doc: searcher.search(consulta)){
					flag= false;
					for (ScoredTextDocument doc2: res){
						if(doc.getDocId().equals(doc2.getDocId())){
							flag= true;
							doc2.setScore(doc.getScore()+doc2.getScore());
						}
					}
					if(!flag)
						res.add(doc);
				}
			}
			for(ScoredTextDocument doc: res){
				System.out.println(doc.getDocId()+" "+doc.getScore());
			}
			
		}
	}*/
	
	
	
	public static void main(String []args) throws IOException{
		LuceneSearcher searcher = new LuceneSearcher();
		LuceneIndex index = new LuceneIndex();
		System.out.println(args[0]);
		index.load(args[0]);
		searcher.build(index);
		String consulta = new String();
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		while(true){
			System.out.print("Introduzca una consulta: ");
			consulta = entrada.readLine();
			consulta = consulta.trim();
			System.out.println(consulta);
			String parts[]= consulta.split(" ");
			boolean flag=false;
			List<ScoredTextDocument> res= new ArrayList<>();
			for(String s: parts){
				if(consulta.length() == 0 || consulta.charAt(0) == 'q')
					System.exit(0);
				for(ScoredTextDocument doc: searcher.search(consulta)){
					flag= false;
					for (ScoredTextDocument doc2: res){
						if(doc.getDocId().equals(doc2.getDocId())){
							flag= true;
							doc2.setScore(doc.getScore()+doc2.getScore());
						}
					}
					if(!flag)
						res.add(doc);
				}
			}
			for(ScoredTextDocument doc: res){
				System.out.println(doc.getDocId()+" "+doc.getScore());
			}
			
		}
	}
	/*public static void main(String []args) throws IOException{
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
	}*/
	
}
