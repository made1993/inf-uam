package es.uam.eps.bmi.redessociales;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * Clase que calcula metricas para pares de vértices
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 *         Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class MetricasParesVertices {

	/**
	 * Calcula el arraigo entre todos los pares de vértices del grafo
	 * 
	 * @param grafo
	 *            HashMap que contiene todo el grafo
	 * @return HashMap que contiene los pares de vertices y su valor del arraigo
	 */
	public HashMap<String, Double> calcularArraigo(HashMap<String, List<String>> grafo) {

		List<String> vecinosu = null;
		List<String> vecinosv = null;
		HashMap<String, Double> arraigo = new HashMap<String, Double>();

		for (String u : grafo.keySet()) {
			for (String v : grafo.keySet()) {
				// No calculamos el arraigo de un nodo consigo mismo, y si
				// tenemos el arraigo de dos nodos, no calculamos el inverso, si
				// no son vecinos, tampoco se calcula
				if (u.equals(v) || arraigo.get(u + "" + v) != null || arraigo.get(v + "" + u) != null
						|| !grafo.get(u).contains(v))
					continue;

				vecinosu = grafo.get(u);
				vecinosv = grafo.get(v);

				int cont = 0;
				int sizevecinosu = vecinosu.size() - 1;
				int sizevecinosv = vecinosv.size() - 1;
				for (String vecinov : vecinosv) {
					if (vecinosu.contains(vecinov) && !vecinov.equals(u))
						cont++;
				}

				arraigo.put(u + v, ((double) cont / (sizevecinosu + sizevecinosv - cont)));
			}
		}
		return arraigo;

	}

	/**
	 * Imprime en un fichero el valor del arraigo
	 * 
	 * @param arraigo
	 *            HashMap con el valor de arraigo de los nodos del grafo
	 * @param redSocial
	 *            red social
	 */
	public void printTop10Arraigo(HashMap<String, Double> arraigo, String redSocial) {

		ArrayList<Double> val = new ArrayList<>();
		ArrayList<String> key = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			val.add(0.0);
			key.add("");
		}
		for (Entry<String, Double> ent : arraigo.entrySet()) {
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
			BufferedWriter bw = new BufferedWriter(new FileWriter("bmi1415_p4_09_edgeEmbededness.txt"));
			int cont = arraigo.size();
			if (cont > val.size())
				cont = val.size();
			for (int i = 0; i < cont; i++)
				bw.write(redSocial + " " + key.get(i).charAt(0) + " " + key.get(i).charAt(1) + " " + val.get(i) + "\n");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Imprime en un fichero aquellos nodos que tengan un valor 0 en el arraigo
	 * 
	 * @param arraigo
	 *            HashMap con el valor de arraigo de los nodos del grafo
	 * @param redSocial
	 *            red social
	 */
	public void printArraigo0(HashMap<String, Double> arraigo, String redSocial){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("arraigo0"+redSocial+".txt"));
			for(Entry<String,Double> par: arraigo.entrySet()){
				if(par.getValue()==0)
					bw.write(redSocial + " " + par.getKey().charAt(0) + " " + par.getKey().charAt(1) + " " + par.getValue() + "\n");
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {

		HashMap<String, List<String>> small1 = LectorCSV.leerCSV("grafos/small1.csv", false);
		HashMap<String, List<String>> small2 = LectorCSV.leerCSV("grafos/small2.csv", false);
		HashMap<String, List<String>> twitter = LectorCSV.leerCSV("grafos/twitter.csv", false);
		HashMap<String, List<String>> facebook = LectorCSV.leerCSV("grafos/fb.csv", false);
		HashMap<String, List<String>> erdos = LectorCSV.leerCSV("grafos/GrafoErdosRenyi.csv", false);
		HashMap<String, List<String>> barabasi = LectorCSV.leerCSV("grafos/GrafoBarabasiAlbert.csv", false);
		MetricasParesVertices mpv = new MetricasParesVertices();
		HashMap<String, Double> arraigo = null;
		
		arraigo = mpv.calcularArraigo(small1);
		mpv.printTop10Arraigo(arraigo, "small1");
		mpv.printArraigo0(arraigo, "small1");
		
		arraigo = mpv.calcularArraigo(small2);
		mpv.printTop10Arraigo(arraigo, "small2");
		mpv.printArraigo0(arraigo, "small2");
		
		arraigo = mpv.calcularArraigo(twitter);
		mpv.printTop10Arraigo(arraigo, "twitter");
		mpv.printArraigo0(arraigo, "twitter");
		
		arraigo = mpv.calcularArraigo(facebook);
		mpv.printTop10Arraigo(arraigo, "facebook");
		mpv.printArraigo0(arraigo, "facebook");
		
		arraigo = mpv.calcularArraigo(erdos);
		mpv.printTop10Arraigo(arraigo, "erdos");
		mpv.printArraigo0(arraigo, "erdos");
		
		arraigo = mpv.calcularArraigo(barabasi);
		mpv.printTop10Arraigo(arraigo, "barabasi");
		mpv.printArraigo0(arraigo, "barabasi");

	}
}
