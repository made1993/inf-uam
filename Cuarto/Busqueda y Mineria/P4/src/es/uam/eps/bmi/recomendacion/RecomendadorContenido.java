package es.uam.eps.bmi.recomendacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import es.uam.eps.bmi.recomendacion.lectores.MovieTags;
import es.uam.eps.bmi.recomendacion.lectores.UserRatedMovies;

import java.util.Set;


/**
 * Clase que recomineda una pelicula segun los distintos algoritmos de conenido
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 *         Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class RecomendadorContenido {
	private UserRatedMovies urm;
	private MovieTags mt;
	private int k;
	
	
	/**
	 * Especifica que algortimo se va a usar para calculuar la evaluacion
	 * y genera/carga las similitudes.
	 * 
	 * @param path
	 *          lugar donde se encuentran los archivos
	 * 
	 * @param k
	 * 			tamaño del vencindario
	 * 
	 * @param func
	 * 			algoritmo que se va usar para recomendar
	 * 			1 --> Colaborativo Coseno
	 * 			2 --> Colaborativo Pearson		
	 * 	
	 *            
	 * @return la estructura incializada
	 */
	public RecomendadorContenido(String path, int k, int func){
		urm = new UserRatedMovies();
		mt=new MovieTags();
		this.k=k;
		urm.leerDatos(path+"/user_ratedmovies.dat");
		mt.leerDatos(path+"/movie_tags.dat");
		if(func==1)
			mt.cargarSimilitudes("sims/similitudMoviesCoseno.dat");
		else if (func==2) {
			mt.cargarSimilitudes("sims/similitudMoviesJaccard.dat");
		}
	}

	/**
	 * Calcula las similitudes mediante jaccard
	 */
	public void jaccardContenido(){
		mt.similitudBinJaccard();
	}
	/**
	 * Calcula las similitudes mediante coseno
	 */
	public void cosenoContenido(){
		mt.similitudCoseno();
	}
	
	/**
	 * Calcula el raintg medio de una pelicula
	 * @param movieId la pelicula
	 * @return el valor del rating estimado
	 */
	public double media(int movieId){
		double media=0;
		int size=0;
		for(HashMap<Integer, Double> peliculas: urm.getDatos().values()){
			Double rate= peliculas.get(movieId);
			if(rate!=null){
				media+= rate;
				size++;
			}
				
		}
		if(media==0)
			return 0;
		return media/size;
	}
	/**
	 * Devuelve el rating estimado de una pelicula
	 * @param user usario al que recomendaremos la pelicula
	 * @param movie pelicula que recomendaremos
	 * @return valore que recomendaremos
	 */
	
	public double knnContenido(int userId, int movieId){
		
		if(urm.getDatos().get(userId)==null || mt.getDatos().get(movieId)==null){
			return media(movieId);
		}
		int col=0;
		int ln =mt.getMovieIds().get(movieId);
		ArrayList<Integer> maxSimKey= new ArrayList<>();
		ArrayList<Double> maxSimValue= new ArrayList<>();
		for(int i=0; i<k; i++){
			maxSimKey.add(0);
			maxSimValue.add(0.0);
		}
		
		/**
		 * Este bucle mierdoso de abajo es para
		 * oredenar los k vecinos mas proximos.
		 * */
		double val;
		int keyAux;
		for(Integer key :mt.getMovieIds().keySet()){
			if(key.equals(movieId) || !urm.getDatos().get(userId).containsKey(key))
				continue;
			val=mt.getSimilitudes()[ln][mt.getMovieIds().get(key)];
			if(maxSimValue.get(k-1) < val ){
				maxSimValue.set(k-1, val);
				maxSimKey.set(k-1, key);
				for(int i= k-1; i>0; i--){
					if(maxSimValue.get(i-1) > maxSimValue.get(i)){
						break;
					}
					val=maxSimValue.get(i-1);
					maxSimValue.set(i-1, maxSimValue.get(i));
					maxSimValue.set(i, val);
					
					keyAux=maxSimKey.get(i-1);
					maxSimKey.set(i-1, maxSimKey.get(i));
					maxSimKey.set(i, keyAux);
				}
			}
		}
		
		/**
		 * Ahora es cuando se calcula el valor de 
		 * knn para la recomnedacion de la peli esta
		 */
		
		double rating=0;
		double simTot=0;
		if(maxSimValue.get(0)==0){
			return media(movieId);
		}
		for(int i=0; i< maxSimKey.size(); i++){
			
			
			ln=mt.getMovieIds().get(movieId);
			if(urm.getDatos().get(userId).containsKey(maxSimKey.get(i)))
				rating+=  urm.getDatos().get(userId).get(maxSimKey.get(i)) * maxSimValue.get(i);
			simTot+= maxSimValue.get(i);
		}
		return rating/simTot;
	}



	/**
	 * @return the urm
	 */
	public UserRatedMovies getUrm() {
		return urm;
	}

	/**
	 * @param urm the urm to set
	 */
	public void setUrm(UserRatedMovies urm) {
		this.urm = urm;
	}

	/**
	 * @return the mt
	 */
	public MovieTags getMt() {
		return mt;
	}

	/**
	 * @param mt the mt to set
	 */
	public void setMt(MovieTags mt) {
		this.mt = mt;
	}

	/**
	 * @return the k
	 */
	public int getK() {
		return k;
	}

	/**
	 * @param k the k to set
	 */
	public void setK(int k) {
		this.k = k;
	}

	public static void main(String [] args){
		Scanner reader = new Scanner(System.in);
		System.out.println("Numero de vecinos:");
		int k = reader.nextInt();
		System.out.println("1 para coseno 2 para jaccard:");
		int func = reader.nextInt();
		RecomendadorContenido r= new RecomendadorContenido("hetrec2011-movielens-2k-v2", k, func);
		while (true){
			System.out.println("UserId:");
			int userId = reader.nextInt();
			System.out.println("MovieId:");
			int movieId = reader.nextInt();
			System.out.println(r.knnContenido(userId, movieId));
			System.out.println("Fin?: SI -> 1, NO -> 2");
			if(reader.nextInt()==1){
				break;
			}
		}
		reader.close();
	}
}
