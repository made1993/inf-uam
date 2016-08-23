package es.uam.eps.bmi.redessociales;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * Clase que calcula las métricas para un nodo de un grafo
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class MetricasNodo {

	private static int epocas = 10;
	private static double r = 0.1;
	
	/**
	 * Calcula el coeficiente de clustering de un nodo en particular
	 * 
	 * @param nodoInicial
	 *            nodo del que queremos saber el coeficiente de clustering
	 * @param grafo
	 *            HashMap que contiene todo el grafo
	 * @return double devolvemos el coeficiente de clustering del nodo en
	 *         particular
	 */
	public double coeficienteClusteringNodo(String nodoInicial, HashMap<String, List<String>> grafo) {

		List<String> vecinosNodo = grafo.get(nodoInicial);
		List<String> aux = null;
		int cuenta = 0;
		int contador = 0;

		for (String nodo : vecinosNodo) {
			aux = grafo.get(nodo);
			for (int i = contador; i < vecinosNodo.size(); i++) {
				// La lista debe empezar desde
				// el valor de nodo
				if (aux != null && aux.contains(vecinosNodo.get(i)))
					cuenta++;
			}
			contador++;
		}

		return (2.0 * cuenta) / (vecinosNodo.size() * (vecinosNodo.size() - 1));

	}

	/** Calcula el page rank del grafo
	 * 
	 * @param iteracciones iteracciones que queremos que realice el algoritmo de page rank
	 * @param r constante
	 * @param grafo HashMap que contiene todo el grafo
	 * @return HashMap page rank de todos los nodos del grafo
	 */
	public HashMap<String, PageRankInfo> calcularPageRank(int iteraciones, double r,
			HashMap<String, List<String>> grafo) {

		HashMap<String, PageRankInfo> links = this.adaptarNodosPR(grafo);

		double valor_base = 1.0 / links.size();

		for (String s : links.keySet())
			links.get(s).setPrVal(valor_base);

		// Comprobamos si hay estados sumideros
		for (String s : links.keySet()) {
			PageRankInfo aux = links.get(s);
			if (aux.getSalidas() == 0) { // Estado sumidero
				aux.setSalidas(links.size()); // Le ponemos tantas salidas como
												// estados haya
				for (String s2 : links.keySet())
					links.get(s2).addLink(s); // Para cada nodo del grafo, el
												// añadimos una conexión con el
												// estado sumidero
			}
		}

		for (int i = 0; i < iteraciones; i++) {
			HashMap<String, PageRankInfo> links2 = new HashMap<String, PageRankInfo>();
			for (Entry<String, PageRankInfo> prInfo : links.entrySet()) {
				PageRankInfo prInfoNew = new PageRankInfo();
				prInfoNew.setLinks(prInfo.getValue().getLinks());
				prInfoNew.setSalidas(prInfo.getValue().getSalidas());
				//sum += prInfo.getValue().getPrVal();
				prInfoNew.setPrVal(prInfo.getValue().calcularPageRank(r, links));
				links2.put(prInfo.getKey(), prInfoNew);
			}
			//System.out.println("sum:" + sum);
			
			links = links2;
		}

		return links;
	}

	/*public HashMap<String, List<PageRankInfo>> calcularPageRankepocas(int iteraciones, double r,
			HashMap<String, List<String>> grafo) {

		HashMap<String, PageRankInfo> links = this.adaptarNodosPR(grafo);
		HashMap<String, List<PageRankInfo>> pageRank = new HashMap<String, List<PageRankInfo>>();
		
		double valor_base = 1.0 / links.size();

		for (String s : links.keySet())
			links.get(s).setPrVal(valor_base);

		// Comprobamos si hay estados sumideros
		for (String s : links.keySet()) {
			PageRankInfo aux = links.get(s);
			if (aux.getSalidas() == 0) { // Estado sumidero
				aux.setSalidas(links.size()); // Le ponemos tantas salidas como
												// estados haya
				for (String s2 : links.keySet())
					links.get(s2).addLink(s); // Para cada nodo del grafo, el
												// añadimos una conexión con el
												// estado sumidero
			}
		}

		for (int i = 0; i < iteraciones; i++) {
			HashMap<String, PageRankInfo> links2 = new HashMap<String, PageRankInfo>();
			for (Entry<String, PageRankInfo> prInfo : links.entrySet()) {
				PageRankInfo prInfoNew = new PageRankInfo();
				prInfoNew.setLinks(prInfo.getValue().getLinks());
				prInfoNew.setSalidas(prInfo.getValue().getSalidas());
				//sum += prInfo.getValue().getPrVal();
				prInfoNew.setPrVal(prInfo.getValue().calcularPageRank(r, links));
				links2.put(prInfo.getKey(), prInfoNew);
			}
			//System.out.println("sum:" + sum);
			
			links = links2;
			for(Entry<String,PageRankInfo> nodo: links.entrySet()){
				List<PageRankInfo> lista = pageRank.get(nodo.getKey());
				if(lista == null){
					lista = new ArrayList<PageRankInfo>();
				}
				lista.add(nodo.getValue());
				pageRank.put(nodo.getKey(), lista);
			}
			
		}

		return pageRank;
	}
	*/
	
	/** adapta la estructura del grafo para poder hacer page rank
	 * 
	 * @param grafo HashMap que contiene todo el grafo
	 * @return HashMap que tiene el grafo en un formato específico para calcular el page rank
	 */
	private HashMap<String, PageRankInfo> adaptarNodosPR(HashMap<String, List<String>> grafo) {

		PageRankInfo prInfo = new PageRankInfo();
		HashMap<String, PageRankInfo> links = new HashMap<String, PageRankInfo>();

		for (Entry<String, List<String>> nodo : grafo.entrySet()) {
			for (String link : nodo.getValue()) // Añadimos todos los links
				prInfo.addLink(link);
			prInfo.setSalidas(nodo.getValue().size());
			links.put(nodo.getKey(), prInfo);
			prInfo = new PageRankInfo();
		}

		return links;
	}

	/** Imprime en un fichero el top 10 del page rank
	 * 
	 * @param links grafo con los valores de page rank
	 * @param redSocial red social
	 */
	public void printTop10PR(HashMap<String, PageRankInfo> links, String redSocial) {
		ArrayList<Double> val = new ArrayList<>();
		ArrayList<String> key = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			val.add(0.0);
			key.add("");
		}
		for (Entry<String, PageRankInfo> ent : links.entrySet()) {
			Double value = ent.getValue().getPrVal();
			if (value > val.get(9)) {
				val.set(9, value);
				key.set(9, ent.getKey());
			}
			for (int i = 9; i > 0; i--) {
				if (val.get(i) < val.get(i - 1))
					break;
				Double dval = val.get(i - 1);
				String sval = key.get(i - 1);
				val.set(i - 1, val.get(i));
				key.set(i - 1, key.get(i));
				val.set(i, dval);
				key.set(i, sval);
			}
		}

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("bmi1415_p4_09_nodePageRank.txt"));
			int cont = links.size();
			if(cont > val.size())
				cont = val.size();
			for (int i = 0; i < cont; i++)
				bw.write(redSocial + " " + key.get(i) + " " + val.get(i)+"\n");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/** Imprime en un fichero el top 10 del clustering de los nodos
	 * 
	 * @param links grafo con los valores de page rank
	 * @param redSocial red social
	 */
	public void printTop10CN(HashMap<String, Double> valores, String redSocial) {

		ArrayList<Double> val = new ArrayList<>();
		ArrayList<String> key = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			val.add(0.0);
			key.add("");
		}
		for (Entry<String, Double> ent : valores.entrySet()) {
			Double value = ent.getValue();
			if (value > val.get(9)) {
				val.set(9, value);
				key.set(9, ent.getKey());
			}
			for (int i = 9; i > 0; i--) {
				if (val.get(i) < val.get(i - 1))
					break;
				Double dval = val.get(i - 1);
				String sval = key.get(i - 1);
				val.set(i - 1, val.get(i));
				key.set(i - 1, key.get(i));
				val.set(i, dval);
				key.set(i, sval);
			}
		}

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("bmi1415_p4_09_nodeClustering.txt"));
			int cont = valores.size();
			if(cont > val.size())
				cont = val.size();
			for (int i = 0; i < cont; i++)
				bw.write(redSocial + " " + key.get(i) + " " + val.get(i)+"\n");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Calcula el coeficiente de clustering de todos los nodos
	 * 
	 * @param grafo HashMap que contiene todo el grafo
	 * @return HashMap<String,Double> devolvemos el coeficiente de clustering de todos los nodos
	 */
	public HashMap<String,Double> coeficientesClusteringNodos(HashMap<String, List<String>> grafo){
		HashMap<String,Double> valores = new HashMap<String,Double>();
		
		for(String nodo: grafo.keySet())
			valores.put(nodo, this.coeficienteClusteringNodo(nodo, grafo));
		
		return valores;		
	}
	
	public static void main(String args[]) {

		HashMap<String, List<String>> small1 = LectorCSV.leerCSV("grafos/small1.csv", false);
		HashMap<String, List<String>> small2 = LectorCSV.leerCSV("grafos/small2.csv", false);
		HashMap<String, List<String>> twitter = LectorCSV.leerCSV("grafos/twitter.csv", false);
		HashMap<String, List<String>> facebook = LectorCSV.leerCSV("grafos/fb.csv", false);
		HashMap<String, List<String>> erdos = LectorCSV.leerCSV("grafos/GrafoErdosRenyi.csv", false);
		HashMap<String, List<String>> barabasi = LectorCSV.leerCSV("grafos/GrafoBarabasiAlbert.csv", false);
		MetricasNodo mn = new MetricasNodo();
		HashMap<String, PageRankInfo> pr = null;
		HashMap<String,Double> ccn = null;
		
		pr = mn.calcularPageRank(epocas, r, small1);
		mn.printTop10PR(pr, "small1");
		ccn = mn.coeficientesClusteringNodos(small1);
		mn.printTop10CN(ccn, "small1");
		
		/*pr = mn.calcularPageRank(epocas, r, small2);
		mn.printTop10PR(pr, "small2");
		ccn = mn.coeficientesClusteringNodos(small2);
		mn.printTop10CN(ccn, "small2");
		
		pr = mn.calcularPageRank(epocas, r, twitter);
		mn.printTop10PR(pr, "twitter");
		ccn = mn.coeficientesClusteringNodos(twitter);
		mn.printTop10CN(ccn, "twitter");
		
		pr = mn.calcularPageRank(epocas, r, facebook);
		mn.printTop10PR(pr, "facebook");
		ccn = mn.coeficientesClusteringNodos(facebook);
		mn.printTop10CN(ccn, "facebook");
		
		pr = mn.calcularPageRank(epocas, r, erdos);
		mn.printTop10PR(pr, "erdos");
		ccn = mn.coeficientesClusteringNodos(erdos);
		mn.printTop10CN(ccn, "erdos");
		
		pr = mn.calcularPageRank(epocas, r, barabasi);
		mn.printTop10PR(pr, "barabasi");
		ccn = mn.coeficientesClusteringNodos(barabasi);
		mn.printTop10CN(ccn, "barabasi");*/		
	}

}
