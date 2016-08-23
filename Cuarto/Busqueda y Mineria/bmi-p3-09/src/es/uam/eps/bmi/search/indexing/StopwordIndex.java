package es.uam.eps.bmi.search.indexing;

import java.io.*;
import java.util.*;

import es.uam.eps.bmi.search.parsing.HTMLSimpleParser;

/**
 * Clase que implementa la interfaz index, creando un indice sin stopwords de un buscador.
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class StopwordIndex extends BasicIndexing{

	private List<String> stopwords;
	
	public StopwordIndex(List<String> stopwords,String file, boolean indicesDisco, boolean guardarIndices) {
		super(indicesDisco, guardarIndices);
		this.stopwords = stopwords;
		this.leerStopWords(file);
		this.inputPath="stopword";
	}

	/** Leemos los stopwords del documento que se nos indique
	 * 
	 * @param file ruta al documento donde están los stopwords
	 */
	public void leerStopWords(String file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null)
				this.stopwords.add(line);	
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Normalizamos las palabras antes de crear el índice con ellas, quitando stopwords
	 * 
	 * @param s documento leido del zip
	 * @return array con cada una de las palabras del documento normalizadas
	 */
	@Override
	public String[] normalizar(String doc){
		String texto[] = super.normalizar(doc);
		List<String> aux= new ArrayList<>();
		int i=0;
		
		for(i=0; i<texto.length;i++){
			if(!stopwords.contains(texto[i].toLowerCase())){
				aux.add(texto[i]);
			}
		}
		
		String[] ret= new String[aux.size()];
		aux.toArray(ret);
		return aux.toArray(ret);
	}
		
	public static void main (String[] args){
		
		List<String> stopwords = new ArrayList<>();
		StopwordIndex i= new StemIndex(stopwords,"stopwords.txt", true, true);
		//List<String> paths = i.lectorXML();
		//i.readZips2("/home/tron/Descargas/clueweb-1K/docs.zip");
		
		long time=0;
		Runtime r=Runtime.getRuntime();
		time=System.nanoTime();
		//i.build(paths.get(0), paths.get(1), new HTMLSimpleParser());

		i.build("/home/tron/Descargas/clueweb-1K/docs.zip", "/home/tron/1k", new HTMLSimpleParser());
		time= System.nanoTime()-time;
		r.getRuntime().gc();
		System.out.println("tiempo:"+time/Math.pow(10, 9));
		
		System.out.println("Ocupada:"+(r.totalMemory()/Math.pow(2,20)-r.freeMemory()/Math.pow(2,20))+" MB");
		System.out.println("Libre:"+r.freeMemory()/Math.pow(2,20)+" MB");
		//System.out.println(i.getDocIds().get(0));
		System.out.println("Numero de terminos:"+i.getTerms().size());

		System.out.println(i.getTermPostings("obama").size());
		//System.out.println(i.getTerms());
		/*long time=0;
		Runtime r=Runtime.getRuntime();
		Set<String> voc=null;
		time=System.nanoTime();
		i.build("/home/flyn/Descargas/clueweb-1K/docs.zip","", new HTMLSimpleParser());
		time= System.nanoTime()-time;
		System.out.println("tiempo:"+time/Math.pow(10, 9));
		System.out.println("Ocupada:"+r.totalMemory()/Math.pow(2,20)+" MB");
		System.out.println("Libre:"+r.freeMemory()/Math.pow(2,20)+" MB");
		System.out.println("Vocabulario: "+i.getVocabulario().size());
		for(String s: i.getVocabulario()){
			System.out.println("palabra:["+s+"]"+" size:"+s.length());
			System.out.println("Posts:"+ i.getTermPostings(s));
			break;
		}*/
	}
	
	
}
