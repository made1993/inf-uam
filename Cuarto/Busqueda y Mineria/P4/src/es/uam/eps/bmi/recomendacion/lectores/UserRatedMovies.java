package es.uam.eps.bmi.recomendacion.lectores;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
/**
 * 
 * Clase que lee y el fichero de ratings y calcula las similitudes entre usuarios.
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 *         Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class UserRatedMovies implements Serializable {
	
	private HashMap<Integer, HashMap<Integer, Double>> datos = new HashMap<>();
	private HashMap<Integer, Integer> userIds = new HashMap<>();
	private HashMap<Integer, Double> medias = new HashMap<>();
	private double [][] similitudes; 

	/**
	 * @return the userIds
	 */
	public HashMap<Integer, Integer> getUserIds() {
		return userIds;
	}
	/**
	 * 
	 * @return el tamaño de los datos
	 */
	public int getSizeDatos(){
		int size=0;
		for(HashMap<Integer, Double> peliculas: datos.values()){
			size+=peliculas.size();
		}
		return size;
	}
	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(HashMap<Integer, Integer> userIds) {
		this.userIds = userIds;
	}
	/**
	 * Genera un fichero donde se guardan las similitudes
	 * @param file el fichero a escribir
	 */
	public void guardarDatos(String file) {
		try {
			FileOutputStream fos = new FileOutputStream("sims/"+file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(userIds);
			oos.writeObject(similitudes);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * Lee y carga las similitudes generadas en un ejecucion anterior,
	 * en caso de que este no exista lo volvera a generar.
	 * @param file el archivo a cargar
	 */
	public void cargarDatos(String file) {
		try {
			FileInputStream fos = new FileInputStream(file);
			ObjectInputStream oos = new ObjectInputStream(fos);
			userIds= (HashMap<Integer, Integer>) oos.readObject();
			similitudes=(double[][]) oos.readObject();
			oos.close();
		} catch (Exception e) {
			System.out.println(file);
			System.out.println("El fichero de similitudes no existe por tanto se pasara a calcular"
					+ "\nEsto pude tardar.");
			if(file.contains("Coseno")){
				similitudCoseno();
			}
			else if(file.contains("Pearson")){
				similitudPearson();
			}
		}

	}
	/**
	 * Lee los datos de los ratings
	 * @param file el archivo con los ratings
	 */
	public void leerDatos(String file) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(file)));
			String linea;
			linea = br.readLine();
			String[] data;
			while ((linea = br.readLine()) != null) {
				data = linea.split(" |\t|\n|\r");
				for (int i = 0; i < data.length; i++) {
					HashMap<Integer, Double> rating = datos.get(Integer.parseInt(data[0]));
					if (rating == null)
						rating = new HashMap<>();
					rating.put(Integer.parseInt(data[1]), Double.parseDouble(data[2]));
					datos.put(Integer.parseInt(data[0]), rating);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int value=0;
		for(Integer key: datos.keySet()){
			userIds.put(key, value++);
		}
		System.out.println("termina de leer datos de entrada");

	}
	/**
	 * Calcula las smilitudes mediante conseno y las escribe a fichero
	 */
	public void similitudCoseno() {
		System.out.println(userIds.size());
		similitudes= new double [userIds.size()][userIds.size()];		
		for (Entry<Integer, HashMap<Integer, Double>> pelicula1 : datos.entrySet()) {
			double denom1 = 0;
			for (Double tag : pelicula1.getValue().values())
				denom1 += tag * tag;

			for (Entry<Integer, HashMap<Integer, Double>> pelicula2 : datos.entrySet()) {
				int numTags = 0;
				for (Integer rating : pelicula1.getValue().keySet())
					if (pelicula2.getValue().containsKey(rating))
						numTags += pelicula2.getValue().get(rating) * pelicula1.getValue().get(rating);

				int denom2 = 0;
				for (Double tag1 : pelicula2.getValue().values())
					denom2 += tag1 * tag1;
				similitudes[userIds.get(pelicula1.getKey())][userIds.get(pelicula2.getKey())]=
						(double)numTags/(Math.sqrt(denom1)+Math.sqrt(denom2));
			}
			

		}
		
		guardarDatos("similitudUsersCoseno.dat");

	}
	/**
	 * Genera las medias de los usuarios
	 */
	public void mediasUsuarios(){
		int n=0;
		for (Entry<Integer, HashMap<Integer, Double>> user : datos.entrySet()) {
			n=0;
			Double media = null;
			
			for (Entry<Integer, Double> ratings : user.getValue().entrySet()) {
				media = medias.get(user.getKey());
				if(media==null){
					medias.put(user.getKey(), ratings.getValue());
					n++;
				}
				else{
					media+= ratings.getValue();
					medias.put(user.getKey(), media);
					n++;
				}
			}
			media = medias.get(user.getKey());
			medias.put(user.getKey(), media/n);
		}
	}
	/**
	 * Calcula las medias mediante pearson
	 */
	public void similitudPearson() {
		mediasUsuarios();
		System.out.println(userIds.size());
		similitudes= new double [userIds.size()][userIds.size()];		
		for (Entry<Integer, HashMap<Integer, Double>> pelicula1 : datos.entrySet()) {
			double denom1 = 0;
			double aux=0;
			for (Entry<Integer, Double> rate : pelicula1.getValue().entrySet()){
				aux=rate.getValue() - medias.get(pelicula1.getKey());
				denom1 += aux*aux;
			}

			for (Entry<Integer, HashMap<Integer, Double>> pelicula2 : datos.entrySet()) {
				int numer = 0;
				for (Integer rate : pelicula1.getValue().keySet())
					if (pelicula2.getValue().containsKey(rate))
						numer += (pelicula2.getValue().get(rate)  - medias.get(pelicula1.getKey()))
						* (pelicula1.getValue().get(rate) - medias.get(pelicula2.getKey()));

				int denom2 = 0;
				for (Double rate : pelicula2.getValue().values()){
					aux= rate - medias.get(pelicula2.getKey());
					denom2 += aux* aux;
				}
				similitudes[userIds.get(pelicula1.getKey())][userIds.get(pelicula2.getKey())]=
						(double)numer/(Math.sqrt(denom1)+Math.sqrt(denom2));
			}
			

		}
		
		guardarDatos("similitudUsersPearson.dat");

	}
	/**
	 * @return the similitudes
	 */
	public double[][] getSimilitudes() {
		return similitudes;
	}

	/**
	 * @param similitudes the similitudes to set
	 */
	public void setSimilitudes(double[][] similitudes) {
		this.similitudes = similitudes;
	}

	/**
	 * @return the datos
	 */
	public HashMap<Integer, HashMap<Integer, Double>> getDatos() {
		return datos;
	}

	/**
	 * @param datos
	 *            the datos to set
	 */
	public void setDatos(HashMap<Integer, HashMap<Integer, Double>> datos) {
		this.datos = datos;
	}

	public static void main(String[] args) {
		UserRatedMovies urm = new UserRatedMovies();
		urm.leerDatos("hetrec2011-movielens-2k-v2/user_ratedmovies.dat");
		urm.similitudPearson();
	}

}