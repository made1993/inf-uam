package es.uam.eps.bmi.search.searching;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

import es.uam.eps.bmi.search.*;
import es.uam.eps.bmi.search.indexing.*;
import es.uam.eps.bmi.search.parsing.HTMLSimpleParser;

public class ProximalSearcher implements Searcher{

	private Index index;
	
	/** Construye un buscador a partir de un indice
	 * 
	 * @param index indice con los documentos necesario para construir el buscador
	 */
	@Override
	public void build(Index index) {
		this.index = index;
	}

	@Override
	public List<ScoredTextDocument> search(String query) {
		
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
		
		
		/////////////////// CAMBIO A PARTIR DE AQUÍ ///////////////////
		
		//Calculo el módulo de la consulta
		Map<String,Double> hashFreq = new LinkedHashMap<String,Double>();
		
		for(String key: tokens){
			Double d = hashFreq.get(key);
			if(d==null)
				d = new Double(1);
			else
				d+=1;
			hashFreq.put(key,d);
		}
		
		Iterator<String> it = hashFreq.keySet().iterator();
		double moduloq = 0;
		while(it.hasNext()){
			String key = it.next();
			moduloq+=Math.pow(hashFreq.get(key),2);
		}
		moduloq=Math.sqrt(moduloq);
		
		//Para cada documento
		hashFreq.clear();
		String docID = null;
		List<List<Long>> lista = new ArrayList<>();
		for(int i = 0; i < numPosting; i++){
			//Con esto sacamos las lista de posiciones que están en la posición i
			for(String key: tokens){
				lista.add(comun.get(key).get(i).getTermPositions());
				docID = comun.get(key).get(i).getDocId();
			}
			//A partir de aquí tenemos las listas con las posiciones
			
			double parada = 1;
			double a=-1;
			double b=0;
			double score = 0;
			
			while(parada == 1){
				b = this.getMaxMinValue(lista,a);
				a = this.getMinMaxValue(lista,b);
				if(a!=Double.MIN_VALUE && b!=Double.MAX_VALUE)
					score+=(1/(b-a-moduloq+2)); //Viene en las transparencias
				else
					parada = 0;
				
			}
			
			hashFreq.put(docID,score);
			lista.clear();
			
			
			/*long a=-1, auxA=0;
			long b=0, auxB=0;
			while(lista.size()!=0){
				List<List<Long>>listaAux = new ArrayList<>();
				for(int j=0; j<tokens.length; j++){
					listaAux.add(lista.get(0));
					listaAux.remove(0);
				}
				/*ESTO ES B*/
				/*for(int j=0; j<tokens.length; j++){
					Collections.
					if ()
				}*/
			
		}
		it = hashFreq.keySet().iterator();
		List<ScoredTextDocument> puntuacion = new ArrayList<>();
		double aux = 0;
		while(it.hasNext()){
			String key = it.next();
			aux = hashFreq.get(key);
			puntuacion.add(new ScoredTextDocument(key,(float)aux));
		}
		
		Collections.sort(puntuacion);
		Collections.reverse(puntuacion);
		return puntuacion;
	}

	private Double getMinMaxValue(List<List<Long>> lista,double limite){
		
		double aux = Double.MIN_VALUE;
		List<Double> listaux = new ArrayList<Double>();
		
		for(List<Long> l: lista){
			for(Long value: l){
				if(-1 < value && value <= limite && value >= aux)
					aux = value;
			}
			if(aux == Double.MIN_VALUE)
				return Double.MIN_VALUE;
			listaux.add(aux);
			aux = Double.MIN_VALUE;
		} 
		
		aux = Double.MAX_VALUE;
		for(Double d: listaux)
			if(d < aux)
				aux = d;
		
		return aux;
	}
	
	private Double getMaxMinValue(List<List<Long>> lista,double limite){
		
		double aux = Double.MAX_VALUE;
		List<Double> listaux = new ArrayList<Double>();
		
		for(List<Long> l: lista){
			for(Long value: l){
				if(limite < value && value < Double.MAX_VALUE && value < aux)
					aux = value;
			}
			if(aux == Double.MAX_VALUE)
				return Double.MAX_VALUE;
			listaux.add(aux);
			aux = Double.MAX_VALUE;
		} 
		
		aux = Double.MIN_VALUE;
		for(Double d: listaux)
			if(d > aux)
				aux = d;
		
		return aux;
	}

	public static List<String> lectorXML(){
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
	
	public static void main(String []args) throws IOException{
		ProximalSearcher searcher = new ProximalSearcher();
		BasicIndexing i= new BasicIndexing(false,false);
		List<String> lista = lectorXML();
		i.build(lista.get(0), lista.get(1), new HTMLSimpleParser());
		searcher.build(i);
		String consulta = new String();
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		List<String> docsId = i.getDocIds();
		
		while(true){
			System.out.print("Introduzca una consulta: ");
			consulta = entrada.readLine();
			consulta = consulta.trim();
			List<ScoredTextDocument> resultados = searcher.search(consulta);
			TextDocument doc = null;
			int fin = 15;
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
