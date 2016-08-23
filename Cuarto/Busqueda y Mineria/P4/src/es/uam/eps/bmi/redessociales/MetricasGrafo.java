package es.uam.eps.bmi.redessociales;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import edu.uci.ics.jung.algorithms.scoring.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * Clase que calcula las métricas para un grafo
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class MetricasGrafo {

	/**
	 * Calcula el número de tripletes de un grafo
	 * 
	 * @param grafo
	 *            HashMap que contiene todo el grafo
	 * @return int devolvemos el valor de los tripletes
	 */
	private int tripletes(HashMap<String, List<String>> grafo) {

		int tripletes = 0;

		for (Entry<String, List<String>> nodo : grafo.entrySet())
			tripletes += nodo.getValue().size() * (nodo.getValue().size() - 1) * 0.5;

		return tripletes;
	}

	/**
	 * Calcula el valor hash de un string. Suma los códigos ascii de las letras
	 * 
	 * @param s
	 *            String del cual queremos saber su valor hash
	 * @return int con el valor hash asociado a ese string
	 */
	private int getHash(String s) {
		int value = 0;

		for (int i = 0; i < s.length(); i++)
			value += (int) s.charAt(i);

		return value;
	}

	/**
	 * Calcula el coeficiente de clustering de un grafo no dirigido
	 * 
	 * @param grafo
	 *            HashMap que contiene todo el grafo
	 * @return double devolvemos el coeficiente de clustering del grafo
	 */
	public double coeficienteClusteringGrafo(HashMap<String, List<String>> grafo) {

		List<String> vecinos = null;
		List<String> vecinos2 = null;
		List<String> triangulos = new ArrayList<String>();

		// Sacamos los triangulos que hay en el grafo
		for (String nodo : grafo.keySet()) {
			vecinos = grafo.get(nodo);
			for (String nodo2 : vecinos) {
				vecinos2 = grafo.get(nodo2);
				for (String nodo3 : vecinos2) {
					if (grafo.get(nodo3).contains(nodo)) {
						triangulos.add(new String(nodo + "" + nodo2 + "" + nodo3));
						break;
					}
				}
			}
		}

		List<String> triangulosFinales = new ArrayList<String>();
		List<Integer> hashTrinagulos = new ArrayList<Integer>();
		int total = 0;

		// Quitamos los triangulos repetidos
		for (String s : triangulos) {
			total = this.getHash(s);
			if (hashTrinagulos.contains(total) == false) {
				triangulosFinales.add(s);
				hashTrinagulos.add(total);
			}
		}

		// Devolvemos el valor de clustering del grafo
		return (double) (3 * triangulosFinales.size()) / (double) this.tripletes(grafo);

	}

	/**
	 * Imprime en un fichero el valor de clustering
	 * 
	 * @param coeficiente
	 *            valor de clustering del grafo
	 * @param redSocial
	 *            red social
	 */
	public void imprimir_clustering_grafo(double coeficiente, String redSocial) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter("bmi1415_p4_09_graphClustering.txt"));
			bw.write(redSocial + " " + coeficiente + "\n");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calcula para cada grado, el número de nodos que lo tienen
	 * 
	 * @param grafo
	 *            HashMap que contiene todo el grafo
	 * @return HashMap<Integer, Integer> devuelve el número de nodos para cada grado
	 */
	public HashMap<Integer, Integer> calcularGradoNodos(HashMap<String, List<String>> grafo) {

		HashMap<Integer, Integer> distribucionGrado = new HashMap<Integer, Integer>();

		for (Entry<String, List<String>> nodo : grafo.entrySet()) {
			Integer value = distribucionGrado.get(nodo.getValue().size());

			if (value == null)
				value = new Integer(1);
			else
				value++;

			distribucionGrado.put(nodo.getValue().size(), value);
		}

		return distribucionGrado;
	}

	/**
	 * Imprime en un fichero el grado de los nodos
	 * 
	 * @param distribucionGrado
	 *           el conjunto de grados de cada nodo
	 * @param redSocial
	 *            red social
	 */
	public void printGradoGrafo(HashMap<Integer, Integer> distribucionGrado, String redSocial) {
		try {
			BufferedWriter bw = new BufferedWriter(
					new FileWriter("bmi1415_p4_09_distribucionGrado" + redSocial + ".txt"));
			for (Entry<Integer, Integer> entry : distribucionGrado.entrySet())
				bw.write(entry.getKey() + " " + entry.getValue() + "\n");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Imprime en un fichero el grado entrante de un nodo y su page rank
	 * 
	 * @param grafo
	 *           HashMap que contiene el grafo
	 * @param pr
	 *            valores de page rank
	 */
	public void gradoEntranteyPR(HashMap<String, List<String>> grafo, HashMap<String, PageRankInfo> pr,
			String redSocial) {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("GradoPageRank" + redSocial + ".txt"));
			for(Entry<String, List<String>> nodo : grafo.entrySet())
				bw.write(nodo.getKey() + " " + nodo.getValue().size()+" "+pr.get(nodo.getKey()).getPrVal()+"\n");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return;
	}

	/**
	 * Imprime en un fichero el grado entrante de un nodo y su betweeness
	 * 
	 * @param grafo
	 *           HashMap que contiene el grafo
	 * @param ficherografo
	 *            fichero que contiene el grafo
	 */
	public void gradoEntranteyBetweeness(String redSocial, HashMap<String, List<String>> grafo, String ficherografo) {

		Graph<String, Integer> graph = new UndirectedSparseGraph<String, Integer>();

		try {
			int cont = 1;
			String linea = null;
			String[] data;
			BufferedReader br = new BufferedReader(new FileReader(new File(ficherografo)));
			while ((linea = br.readLine()) != null) {
				data = linea.split(" |\t|,");
				graph.addEdge(cont, data[0], data[1]);
				cont++;
			}
			br.close();

			BetweennessCentrality<String, Integer> bc = new BetweennessCentrality<String, Integer>(graph);
			BufferedWriter bw = new BufferedWriter(new FileWriter("GradoyBetweeness" + redSocial + ".txt"));
			for (Entry<String, List<String>> nodo : grafo.entrySet())
				bw.write(nodo.getKey() + " " + nodo.getValue().size() + " " + bc.getVertexScore(nodo.getKey())+"\n");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Imprime en un fichero los vecinos de un nodo y su media de vecinos
	 * 
	 * @param grafo
	 *           HashMap que contiene el grafo
	 * @param redSocial
	 *            red social
	 */
	public void paradojaAmistad(HashMap<String, List<String>> grafo, String redSocial) {

		HashMap<String, Double> paradoja = new HashMap<String, Double>();
		double cont = 0;

		for (Entry<String, List<String>> nodo : grafo.entrySet()) {
			for (String vecino : nodo.getValue())
				cont += grafo.get(vecino).size();
			cont /= nodo.getValue().size();
			paradoja.put(nodo.getKey(), cont);
			cont = 0;
		}

		// Escribimos en un formato: vertice, numero de vecinos, vecino y num
		// vecinos y media
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("ParadojaAmistad" + redSocial + ".txt"));
			for (Entry<String, Double> nodo : paradoja.entrySet()) {
				bw.write(nodo.getKey() + " " + grafo.get(nodo.getKey()).size() + " ");
				for (String vecino : grafo.get(nodo.getKey()))
					bw.write(vecino + "(" + grafo.get(vecino).size() + "), ");
				bw.write(nodo.getValue() + "\n");
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Calcula la asortatividad de un grafo
	 * 
	 * @param grafo
	 *           HashMap que contiene el grafo
	 * @param redSocial
	 *           Red social del grafo
	 */
	public void asortatividad(HashMap<String, List<String>> grafo,String redSocial){
		
		double valores = 0;
		List<String> conexiones = new ArrayList<String>();
		double cuadrados = 0;
		double cubos = 0;
		
		for(Entry<String,List<String>> nodo: grafo.entrySet()){
			for(String vecino: nodo.getValue()){
				if(conexiones.contains(nodo.getKey().concat(vecino)) == false && conexiones.contains(vecino.concat(nodo.getKey())) == false){
					conexiones.add(nodo.getKey().concat(vecino));
					valores+=grafo.get(vecino).size()*grafo.get(nodo.getKey()).size();
				}
			}
			cuadrados+=Math.pow(nodo.getValue().size(), 2.0);
			cubos+=Math.pow(nodo.getValue().size(), 3.0);
		}
		cuadrados=Math.pow(cuadrados, 2.0);
		
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("bmi1415_p4_09_graphAssortativity.txt"));
			bw.write(redSocial+" "+(4*conexiones.size()*valores - cuadrados)/(2*conexiones.size()*cubos -cuadrados));
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	public static void main(String args[]) {

		HashMap<String, List<String>> small1 = LectorCSV.leerCSV("grafos/small1.csv", false);
		HashMap<String, List<String>> small2 = LectorCSV.leerCSV("grafos/small2.csv", false);
		HashMap<String, List<String>> twitter = LectorCSV.leerCSV("grafos/twitter.csv", false);
		HashMap<String, List<String>> facebook = LectorCSV.leerCSV("grafos/fb.csv", false);
		HashMap<String, List<String>> erdos = LectorCSV.leerCSV("grafos/GrafoErdosRenyi.csv", false);
		HashMap<String, List<String>> barabasi = LectorCSV.leerCSV("grafos/GrafoBarabasiAlbert.csv", false);
		MetricasGrafo mg = new MetricasGrafo();
		MetricasNodo mn = new MetricasNodo();
		
		/*mg.imprimir_clustering_grafo(mg.coeficienteClusteringGrafo(small1), "small1");
		mg.asortatividad(small1,"small1");
		mg.imprimir_clustering_grafo(mg.coeficienteClusteringGrafo(small2), "small2");
		mg.asortatividad(small2,"small2");
		mg.imprimir_clustering_grafo(mg.coeficienteClusteringGrafo(twitter), "twitter");
		mg.asortatividad(twitter,"twitter");
		mg.imprimir_clustering_grafo(mg.coeficienteClusteringGrafo(facebook), "facebook");
		mg.asortatividad(facebook,"facebook");
		mg.imprimir_clustering_grafo(mg.coeficienteClusteringGrafo(erdos), "Erdos");
		mg.asortatividad(erdos,"erdos");
		mg.imprimir_clustering_grafo(mg.coeficienteClusteringGrafo(barabasi), "BarabasiAlbert");
		mg.asortatividad(barabasi,"barabasi");*/
		
		//mg.printGradoGrafo(mg.calcularGradoNodos(small1), "small1");
		//mg.printGradoGrafo(mg.calcularGradoNodos(small2), "small2");
		/*mg.printGradoGrafo(mg.calcularGradoNodos(twitter), "twitter");
		mg.printGradoGrafo(mg.calcularGradoNodos(facebook), "facebook");
		mg.printGradoGrafo(mg.calcularGradoNodos(erdos), "erdos");
		mg.printGradoGrafo(mg.calcularGradoNodos(barabasi), "barabasi");
		
		mg.gradoEntranteyPR(small1, mn.calcularPageRank(10, 0.1, small1), "small1");
		mg.gradoEntranteyPR(small2, mn.calcularPageRank(10, 0.1, small2), "small2");
		mg.gradoEntranteyPR(twitter, mn.calcularPageRank(10, 0.1, twitter), "twitter");
		mg.gradoEntranteyPR(facebook, mn.calcularPageRank(10, 0.1, facebook), "facebook");
		mg.gradoEntranteyPR(erdos, mn.calcularPageRank(10, 0.1, erdos), "erdos");
		mg.gradoEntranteyPR(barabasi, mn.calcularPageRank(10, 0.1, barabasi), "barabasi");

		mg.gradoEntranteyBetweeness("small1", small1, "grafos/small1.csv");
		mg.gradoEntranteyBetweeness("small2", small2, "grafos/small2.csv");*/
		mg.gradoEntranteyBetweeness("twitter", twitter, "grafos/twitter.csv");
		/*mg.gradoEntranteyBetweeness("facebook", facebook, "grafos/fb.csv");
		mg.gradoEntranteyBetweeness("erdos", erdos, "grafos/GrafoErdosRenyi.csv");
		mg.gradoEntranteyBetweeness("barabasi", barabasi, "grafos/GrafoBarabasiAlbert.csv");*/
		
		/*mg.paradojaAmistad(small1, "small1");
		mg.paradojaAmistad(small2, "small2");
		mg.paradojaAmistad(twitter, "twitter");
		mg.paradojaAmistad(facebook, "facebook");
		mg.paradojaAmistad(erdos, "erdos");
		mg.paradojaAmistad(barabasi, "barabasi");*/
		
		//mg.gradoEntranteyPR(grafo, mn.calcularPageRankepocas(10, 0.1, grafo), "small1");
		//mg.paradojaAmistad(grafo, "small2");
		//mg.printGradoGrafo(mg.calcularGradoNodos(grafo),"small2");		
		//mg.imprimir_clustering_grafo(mg.coeficienteClusteringGrafo(grafo), "small2");
		//mg.gradoEntranteyBetweeness("small2", grafo, "small/small2.csv");
		//System.out.println(mg.asortatividad(grafo));
	}

}
