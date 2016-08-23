package es.uam.eps.bmi.redessociales;

import java.io.*;
import java.util.*;

/**
 * Clase que lee un csv que contiene un grafo
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 *         Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class LectorCSV {

	/**
	 * Lee un csv que contiene a un grafo
	 * 
	 * @param file
	 *            archivo donde se encuentra el grafo
	 * 
	 * @param dirigido
	 *            booleano que nos idica si el grafo es dirigido o no
	 *            
	 * @return double devolvemos el coeficiente de clustering del grafo
	 */
	public static HashMap<String, List<String>> leerCSV(String file, boolean dirigido) {

		HashMap<String, List<String>> grafo = new HashMap<String, List<String>>();

		try {
			String linea = null;
			String[] data;
			BufferedReader br = new BufferedReader(new FileReader(new File(file)));
			List<String> lista = null;
			while ((linea = br.readLine()) != null) {
				data = linea.split(" |\t|,");
				lista = grafo.get(data[0]);
				if (lista == null)
					lista = new ArrayList<String>();

				lista.add(data[1]);
				grafo.put(data[0], lista);
				if (!dirigido) {
					lista = grafo.get(data[1]);
					if (lista == null)
						lista = new ArrayList<String>();
					if (lista.contains(data[0]) == false) {
						lista.add(data[0]);
						grafo.put(data[1], lista);
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return grafo;
	}
}
