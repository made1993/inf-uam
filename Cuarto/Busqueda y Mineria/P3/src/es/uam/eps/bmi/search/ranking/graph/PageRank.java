package es.uam.eps.bmi.search.ranking.graph;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.apache.hadoop.io.MapFile;
import org.apache.solr.update.RollbackUpdateCommand;

/*
 * metodo para calcular page rank a partir de fichero enlaces
 * metodo para escribirlo en un fichero
 * 
 */


public class PageRank {
	
	private final double r = 0.1; 
	private HashMap<String,PageRankInfo> links = new HashMap<String,PageRankInfo>();
	
	public double getScoreOf(String documentId){
		return this.links.get(documentId).getPrVal();
	}
	
	public void processLine(String line){
		String tokens[]=line.split(" ");
		if(tokens.length<2)
			return;
		String link= tokens[0];
		PageRankInfo pri= new PageRankInfo();
		pri.setSalidas(Integer.parseInt(tokens[1]));
		for(int i=2; i<tokens.length; i++){
			pri.addLink(tokens[i]);
		}
		
		links.put(link, pri);
	}
	
	public void printTop10(){
		ArrayList<Double> val= new ArrayList<>();
		ArrayList<String> key= new ArrayList<>();
		for(int i=0; i<10; i++){
			val.add(0.0);
			key.add("");
		}
		for(Entry<String, PageRankInfo> ent:links.entrySet()){
			Double value= ent.getValue().getPrVal();
			if(value>val.get(9)){
				val.set(9, value);
				key.set(9, ent.getKey());
			}
			for(int i=9;i>0; i--){
				if(val.get(i)<val.get(i-1))
					break;
				Double dval= val.get(i-1);
				String sval= key.get(i-1);
				val.set(i-1, val.get(i));
				key.set(i-1, key.get(i));
				val.set(i, dval);
				key.set(i, sval);
			}
		}
		System.out.println("TOP 10");
		for(int i=0; i< val.size(); i++){
			System.out.println(key.get(i)+"-->"+val.get(i));
		}
	}
	
	public void fileIn(String fname){
		BufferedReader br = null;
		String sCurrentLine=null;
		try {
			br = new BufferedReader(new FileReader(fname));
			while ((sCurrentLine = br.readLine()) != null) {
				processLine(sCurrentLine);
			}
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void calcularPageRank(int iteraciones){
		
		double valor_base = 1.0/links.size();
		
		for(String s: links.keySet())
			links.get(s).setPrVal(valor_base);
		
		//Comprobamos si hay estados sumideros
		for(String s: links.keySet()){
			PageRankInfo aux = links.get(s);
			if(aux.getSalidas() == 0){ //Estado sumidero
				aux.setSalidas(links.size()); //Le ponemos tantas salidas como estados haya
				for(String s2: links.keySet()) 
					links.get(s2).addLink(s); //Para cada nodo del grafo, el añadimos una conexión con el estado sumidero
			} 
		}
			
		double sum= 0;
		for(int i=0; i<iteraciones; i++){
			HashMap<String,PageRankInfo> links = new HashMap<String,PageRankInfo>();
			for(Entry< String,PageRankInfo> prInfo:this.links.entrySet()){
				PageRankInfo prInfoNew = new PageRankInfo();
				prInfoNew.setLinks(prInfo.getValue().getLinks());
				prInfoNew.setSalidas(prInfo.getValue().getSalidas());
				//System.out.println("Imprimo el valor: "+prInfo.getValue().calcularPageRank(r, this.links)+" "+prInfo.getKey());
				sum+=prInfo.getValue().getPrVal();
				prInfoNew.setPrVal(prInfo.getValue().calcularPageRank(r, this.links));
				links.put(prInfo.getKey(), prInfoNew);
			}
			System.out.println("sum:"+sum);
			this.links=links;
		}
		
		
	}
	
	
	public HashMap<String, PageRankInfo> getLinks() {
		return links;
	}




	public void setLinks(HashMap<String, PageRankInfo> links) {
		this.links = links;
	}

	public String toString(){
		String s= new String();
		for(Entry<String, PageRankInfo> e: links.entrySet()){
			s=s.concat("key:"+e.getKey()+"\n"+e.getValue().toString());
		}
		return s;
	}

	/*public static void main(String []args) {
		PageRank pr = new PageRank();
		pr.fileIn("links_1K.txt");
		System.out.println(pr.getLinks().size());
		System.out.println("calculando page rank");
		pr.calcularPageRank(10);
		System.out.println("page rank calculado");
		pr.printTop10();
	}*/

	public static void main(String [] args){
		PageRank pr= new PageRank();
		PageRankInfo prInfo= new PageRankInfo();
		
		//En addLink introducimos los nodos incidentes, y en salidas indicamos cuantas salidas tiene
		/*prInfo.addLink("c");
		prInfo.setSalidas(2);
		pr.getLinks().put("a", prInfo);
		
		prInfo= new PageRankInfo();
		prInfo.addLink("a");
		prInfo.setSalidas(1);
		pr.getLinks().put("b", prInfo);
		
		prInfo= new PageRankInfo();
		prInfo.addLink("a");
		prInfo.addLink("b");
		prInfo.setSalidas(1);
		pr.getLinks().put("c", prInfo);*/
		
		/*prInfo= new PageRankInfo();
		prInfo.addLink("c");
		prInfo.setSalidas(2);
		pr.getLinks().put("a", prInfo);
		
		prInfo= new PageRankInfo();
		prInfo.addLink("a");
		prInfo.setSalidas(1);
		pr.getLinks().put("b", prInfo);
		
		prInfo= new PageRankInfo();
		prInfo.addLink("a");
		prInfo.addLink("b");
		prInfo.addLink("d");
		prInfo.setSalidas(1);
		pr.getLinks().put("c", prInfo);
		
		prInfo= new PageRankInfo();
		prInfo.setSalidas(1);
		pr.getLinks().put("d", prInfo);*/
		
		prInfo.addLink("b");
		prInfo.addLink("c");
		prInfo.setSalidas(2);
		pr.getLinks().put("a", prInfo);
		
		prInfo= new PageRankInfo();
		prInfo.addLink("a");
		prInfo.addLink("b");
		prInfo.setSalidas(4);
		pr.getLinks().put("b", prInfo);
		
		prInfo= new PageRankInfo();
		prInfo.addLink("b");
		prInfo.addLink("d");
		prInfo.setSalidas(3);
		pr.getLinks().put("c", prInfo);
		
		prInfo= new PageRankInfo();
		prInfo.addLink("c");
		prInfo.setSalidas(1);
		pr.getLinks().put("d", prInfo);
		
		prInfo= new PageRankInfo();
		prInfo.addLink("a");
		prInfo.addLink("b");
		prInfo.addLink("c");
		prInfo.setSalidas(0);
		pr.getLinks().put("e", prInfo);
				
		pr.calcularPageRank(10);
		System.out.println(pr);

		pr.printTop10();
	}
	
}
