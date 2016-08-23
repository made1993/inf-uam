package es.uam.eps.bmi.search.searching;

import java.io.*;
import java.util.*;

import es.uam.eps.bmi.search.*;
import es.uam.eps.bmi.search.indexing.Index;
import es.uam.eps.bmi.search.indexing.LuceneIndex;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Clase que implementa la interfaz Searcher, creando un buscador
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class LuceneSearcher implements Searcher{

	private IndexSearcher searcher;
	private Analyzer analizador;
	private IndexReader reader;
	
	/** Construye un buscador a partir de un indice
	 * 
	 * @param index indice con los documentos necesario para construir el buscador
	 */
	@Override
	public void build(Index index) {
		
		try {
			this.reader = IndexReader.open(FSDirectory.open(new File(index.getPath())));
			this.searcher = new IndexSearcher(reader);
			this.analizador = new StandardAnalyzer(Version.LUCENE_36);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/** Devuelve una lista de ScoredTextDocument, obtenidos a partir de una consulta
	 * 
	 * @param query consulta que realizamos
	 * @return lista de ScoredTextDocument, asociada con los resultados obtenidos en la consulta
	 */
	@Override
	public List<ScoredTextDocument> search(String query){
		QueryParser parseador = new QueryParser(Version.LUCENE_31, "content", this.analizador);
		int cont = 10;
		List<ScoredTextDocument> lscoredTD = new ArrayList<>();
		try {
			Query consultap = parseador.parse(query);
			TopDocs results = this.searcher.search(consultap, 10);
		    ScoreDoc[] hits = results.scoreDocs;
		    if(results.totalHits < 5) cont = results.totalHits;
		    for(int i = 0; i < cont; i++)
		    	lscoredTD.add(new ScoredTextDocument(/*hits[i].doc*/this.reader.document(hits[i].doc).getFieldable("docID").stringValue(),hits[i].score));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lscoredTD;
	}
	
   /** A partir de un indice que cargamos, creamos un buscador y hacemos consultas sobre el índice 
	* 
	* @param args argumentos que le pasamos al main. El estilo de la entrada es:
	* 1ºEl lugar donde se encuentran los indices.
	*/
	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			System.out.println("Introduzca un directorio donde se encuentre un indice");
			System.exit(0);
		}
		
		Index i = new LuceneIndex(); 
		i.load(args[0]);
		
		LuceneSearcher buscador = new LuceneSearcher();
		buscador.build(i);
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		String consulta = null;
		
		while(true){
			System.out.print("Introduzca una consulta: ");
			consulta = entrada.readLine();
			consulta = consulta.trim();
			if(consulta.length() == 0 || consulta.charAt(0) == 'q')
				System.exit(0);
			for(ScoredTextDocument s: buscador.search(consulta)){
				System.out.println(s.getDocId()+" "+s.getScore());
			}
		}	
	}
}
