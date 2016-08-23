package es.uam.eps.bmi.search.searching;

import es.uam.eps.bmi.search.ScoredTextDocument;
import es.uam.eps.bmi.search.TextDocument;
import es.uam.eps.bmi.search.indexing.*;
import es.uam.eps.bmi.search.parsing.HTMLSimpleParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;

import org.jsoup.Jsoup;

/**
 * Clase que implementa la interfaz Searcher, creando un buscador por el método tf-idf
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class TFIDFSearcher implements Searcher{

	private Index index;
	
	/** Construye un buscador a partir de un indice
	 * 
	 * @param index indice con los documentos necesario para construir el buscador
	 */	
	@Override
	public void build(Index index) {
		this.index = index;		
	}

	/** Función que realiza el logaritmo binario
	 * 
	 * @param x valor al que le queremos aplicar el logaritmo binario
	 * @return el logaritmo binario del valor pasado como parámetro
	 */
	private double log2(double x){
		return Math.log(x)/Math.log(2);
	}
		
	/** Calcula los valores tf-idf de un conjunto de palabras
	 * 
	 * @param palabras lista de palabras a las que vamos a calcular el tf-idf
	 * @return TreeMap con la palabra como clave y el tf-idf como valor
	 */
	private TreeMap<String, Double> tfidfDocs(List<String> palabras){
		TreeMap<String, Double> hash = new TreeMap<String,Double>();
		double tf = 0;
		double idf = 0;
		double totalDocs = this.index.getDocIds().size();
		Double maux = null;
		
		//Para cada palabra de la búsqueda, sacamos su tf-idf en cada documento, y los vamos sumando
		//para acumular los valores.
		for(String s: palabras){
			List<Posting> postings = this.index.getTermPostings(s);
			if(postings == null)
				continue;
			idf = this.log2(totalDocs/postings.size());
			for(Posting p: postings){
				tf = 1+this.log2(p.getTermFrequency());
				//Nos piden los tf-idf, sumamos unicamente los valores
				maux=hash.get(p.getDocId());
				if(maux == null)
					maux = new Double((tf*idf));
				else
					maux+=(tf*idf);
				hash.put(p.getDocId(), maux);
				
			}
		}
		
		return hash;
	}
	
	/** Devuelve una lista de ScoredTextDocument, obtenidos a partir de una consulta
	 * 
	 * @param query consulta que realizamos
	 * @return lista de ScoredTextDocument, asociada con los resultados obtenidos en la consulta
	 */
	@Override
	public List<ScoredTextDocument> search(String query) {
		TreeMap<String, Double> hash; //Nos ayudará a almacenar los documentos y sus scores
		HashMap<String, Double> modulos;
		
		//Tratamos las palabras clave como las del índice
		String []tokens = this.index.normalizar(query);
		List<String> pclaves = Arrays.asList(tokens);
		
		//Con esta llamada sacamos los valores tf-idf de los términos claves
		hash = this.tfidfDocs(pclaves);
		modulos = this.index.getModulos();
		
		//Dividimos el tf-idf acumulado de cada documento entre el módulo
		for(String key: hash.keySet())
			hash.put(key,(hash.get(key)/modulos.get(key)));
		
		List<ScoredTextDocument> docs = new ArrayList<ScoredTextDocument>();
		List<String> docsId = this.index.getDocIds();
		
		//Guardamos los resultados en ScoredTextDocuments
		double aux = 0;		
		for(String key: hash.keySet()){
			aux = hash.get(key);
			docs.add(new ScoredTextDocument(key,(float)aux));
		}
		
		//Los ordenamos, y hacemos el inverso
		Collections.sort(docs);
		Collections.reverse(docs);
		return docs;
	}
	
	/** Función que nos permite leer un xml con la información de donde guardar el índice y donde están los ficheros
	 *  para crearlo
	 * 
	 * 	@return lista con la ruta hasta los documentos, y donde guardar el índice
	 */
	private List<String> lectorXML(){
		List<String> paths = new ArrayList<>();
		try {
			String sCadena = null;
			BufferedReader bf = new BufferedReader(new FileReader("index-settings.xml"));
		    while ((sCadena = bf.readLine())!=null) 
		    	if(!Jsoup.parse(sCadena).text().equals(""))
		    		paths.add(Jsoup.parse(sCadena).text());
		    bf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return paths;
	}
		
	public static void main(String[] args) throws Exception{
		
				
		TFIDFSearcher buscador = new TFIDFSearcher();
		List<String> paths = buscador.lectorXML(); //Leemos del xml con la ruta de los índices
		List<String> stopwords = new ArrayList<>();
		//Index i = new StemIndex(stopwords,"stopwords.txt", false, false);
		//Index i = new BasicIndexing();
		Index i = new BasicIndexing(false, false);
		/*for(String s: paths)
			System.out.println(s);*/
		Runtime r=Runtime.getRuntime();
		long time=0;
		//time=System.nanoTime();
		//i.build(paths.get(0),paths.get(1),new HTMLSimpleParser());
		//time=System.nanoTime()-time; 

		//System.out.println("tiempo:"+time/Math.pow(10, 9));
		//r.gc();
		//System.out.println("Ocupada:"+(r.totalMemory()/Math.pow(2,20)-r.freeMemory()/Math.pow(2,20))+" MB");
		//i.build("/home/tron/Descargas/clueweb-1K/docs.zip", "/home/tron/1k", new HTMLSimpleParser());
		i.load(paths.get(1));
		
		buscador.build(i);
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		//String consulta = null;
		TextDocument doc = null;
		List<String> docsId = i.getDocIds();
		List<String> consultas= new ArrayList<String>();
		consultas.add("obama family tree");
		consultas.add("french lick resort and casino");
		consultas.add("getting organized");
		consultas.add("toilet");
		consultas.add("kcs");
		consultas.add("air travel information");
		consultas.add("appraisals");
		consultas.add("used car parts");
		consultas.add("cheap internet");
		r.gc();
		time=System.nanoTime();
		for(String consulta: consultas){
			if(consulta.length() == 0 || consulta.charAt(0) == 'q')
				System.exit(0);
			
			int fin = 15;
			List<ScoredTextDocument> resultados = buscador.search(consulta);
			if(fin > resultados.size())
				fin = resultados.size();
			
			for(int con = 0; con < fin; con++){
				System.out.println(docsId.get(Integer.parseInt(resultados.get(con).getDocId()))+" "+resultados.get(con).getScore());
				doc = i.getDocument(resultados.get(con).getDocId());
				System.out.println(doc.getDoc());
			}
		}
		time=System.nanoTime()-time ;
		System.out.println("tiempo:"+time/Math.pow(10, 9));
		System.out.println("Ocupada:"+(r.totalMemory()/Math.pow(2,20)-r.freeMemory()/Math.pow(2,20))+" MB");
		
	}	
}
