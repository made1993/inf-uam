package es.uam.eps.bmi.search.searching;

import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;

import es.uam.eps.bmi.search.*;
import es.uam.eps.bmi.search.indexing.*;
import es.uam.eps.bmi.search.parsing.*;

/**
 * Clase que implementa la interfaz Searcher, creando un buscador literal
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class LiteralMatchingSearcher implements Searcher{

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
	
	//No se si puede mejorarse más
	//Sólo devuelve los docs que tienen puntuación, si hay alguno que no tiene nada, no lo devuelve.
	/** Devuelve una lista de ScoredTextDocument, obtenidos a partir de una consulta
	 * 
	 * @param query consulta que realizamos
	 * @return lista de ScoredTextDocument, asociada con los resultados obtenidos en la consulta
	 */
	@Override
	public List<ScoredTextDocument> search(String query) {
		
		//No hay que llamar a las funciones de tratar texto, porque es busqueda literal.
		/*String []tokens = query.split(" ");*/	
		List<String> pclaves = new ArrayList<>();
		String []tokens = this.index.normalizar(query);
		for(String s: tokens)
			pclaves.add(s);
		
		List<Posting> pos = this.index.getTermPostings(pclaves.get(0));
		pclaves.remove(0);
		List<String> comunes = new ArrayList<String>();
		Map<String,List<Posting>> comun = new LinkedHashMap<String,List<Posting>>();
		
		if(pos == null)
			return new ArrayList<ScoredTextDocument>();
		
		int flag = 1;
		//Con esto sacamos los identificadores de los documentos comunes a todas las palabras
		for(Posting p: pos){
			for(String s: pclaves){
				List<Posting> posaux = this.index.getTermPostings(s);
				if(!posaux.contains(p)){
					flag = 0;
					break; //Comprobar este break;
				}					
			}
			if(flag == 1){
				//for(String s: pclaves)
				//	System.out.println("El doc es: "+this.index.getDocIds().get(Integer.parseInt(p.getDocId()))+" y su numero es: "+p.getDocId());
				comunes.add(p.getDocId()); //Tendríamos los ids de los documentos que los contienen.
			}
			flag = 1;
		}
		
		int numPosting = 0;
		
		//Guardamos los postings de cada palabra en el hash
		for(String s: Arrays.asList(tokens)){
			List<Posting> posaux = this.index.getTermPostings(s);
			pos = new ArrayList<>();
			for(String ids: comunes){
				for(Posting p: posaux){
					if(ids.equals(p.getDocId())){
						pos.add(p);
					}	
				}
			}
			comun.put(s, pos);
			
		}
		numPosting = pos.size();
		//A partir de aquí, tenemos un hash con las palabras de la consulta(en orden), 
		//y los postings donde aparecen todas las palabras, y el tamaño de la lista más pequeña
		
		/*Buscar una forma de ordenar los postings, de tal forma que si tenemos 3 palabras, y 2 postings para cada una
			to  - IDposting: 3,60
			be  - IDposting: 3,60
			not - IDposting: 3,60
			Que quede algo así
		*/
		
		Map<String,Double> hashFreq = new LinkedHashMap<String,Double>();
		String docID = null;
		List<List<Long>> lista = new ArrayList<>();
		List<Long> listaAux = new ArrayList<>();
		for(int i = 0; i < numPosting; i++){
			Iterator<String> it = comun.keySet().iterator();
			for(String key: tokens){
				lista.add(comun.get(key).get(i).getTermPositions());
				docID = comun.get(key).get(i).getDocId();
			}
			/*while(it.hasNext()){
				String key = it.next();
				lista.add(comun.get(key).get(i).getTermPositions());
				docID = comun.get(key).get(i).getDocId();
			}*/
			
			listaAux = lista.get(0);
			lista.remove(0); //Get no elimina el primer valor, así que lo quitamos nosotros.
			double docFreq = 0;
			flag = 1;
			
			for(int j = 0; j < listaAux.size(); j++){
				Long x = listaAux.get(j);
				for(List<Long> l: lista){
					if(!l.contains(x+1)){
						flag = 0;
						break;
					}
					else x+=1;
				}
				if(flag == 1)
					docFreq++; //La frecuencia de la expresión en el documento
				flag = 1;
			}	
			if(docFreq != 0)
				hashFreq.put(docID,docFreq);
			docFreq = 0;
			lista.clear();
			listaAux.clear();
		}
		//Hasta aquí tendríamos las frecuencias de la expresión en cada documento.
		
		int totalDocs = this.index.getDocIds().size();
		
		Iterator<String> it = hashFreq.keySet().iterator();
		Map<String, Double> modulosDocs = this.index.getModulos(); //Serían los módulos de los documentos
		List<ScoredTextDocument> puntuacion = new ArrayList<>();
		List<String> docsId = this.index.getDocIds();
		while(it.hasNext()){
			String key = it.next();
			puntuacion.add(new ScoredTextDocument(key,(float) ((1+this.log2(hashFreq.get(key)) * this.log2(totalDocs/hashFreq.size()))/modulosDocs.get(key))));
		}
		
		Collections.sort(puntuacion);
		Collections.reverse(puntuacion);
		//Ahora tenemos una lista con todos las puntuaciaciones para cada documento
		return puntuacion;
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
	
	public static void main(String args[]) throws Exception{
		 		
		LiteralMatchingSearcher buscador = new LiteralMatchingSearcher();
		List<String> paths = buscador.lectorXML(); //Leemos del xml con la ruta de los índices
		List<String> stopwords = new ArrayList<>();
		Index i = new BasicIndexing(Boolean.parseBoolean(paths.get(2)),Boolean.parseBoolean(paths.get(3)));
		i.build(paths.get(0), paths.get(1), new HTMLSimpleParser());
		//i.load(paths.get(1).concat("/basic/")); //Le concatenamos la carpeta donde está el índice básico.
		String consulta = null;
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		buscador.build(i);
		TextDocument doc = null;
		List<String> docsId = i.getDocIds();
		
		while(true){
			System.out.print("Introduzca una consulta: ");
			consulta = entrada.readLine();
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
	}
	 	
}

	

