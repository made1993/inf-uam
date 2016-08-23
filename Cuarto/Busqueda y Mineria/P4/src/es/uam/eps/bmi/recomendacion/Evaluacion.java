package es.uam.eps.bmi.recomendacion;

import java.util.*;
import java.util.Map.Entry;

import es.uam.eps.bmi.recomendacion.lectores.UserRatedMovies;
/**
 * Clase que calcula el MAE y el RMSE de unos datos
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 *         Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class Evaluacion {

	private double part;

	private HashMap<Integer, HashMap<Integer, Double>> datosTrain;
	private HashMap<Integer, HashMap<Integer, Double>> datosTest;
	private RecomendadorContenido recCon;
	private RecomendadorColaborativo recCol ;
	private int func;
	private int k;
	
	
	/**
	 * Especifica que algortimo se va a usar para calculuar la evaluacion
	 * 
	 * @param part
	 *          valor del particionado, sobre 1
	 * 
	 * @param path
	 *          lugar donde se encuentran los archivos
	 * 
	 * @param k
	 * 			tamaño del vencindario
	 * 
	 * @param func
	 * 			algoritmo que se va usar para recomendar
	 * 			1 --> Contenido Coseno
	 * 			2 --> Contenido Jaccard
	 * 			3 --> Colaborativo Coseno
	 * 			4 --> Colaborativo Pearson		
	 * 	
	 *            
	 * @return la estructura incializada
	 */
	
	public Evaluacion(double part, String path, int k, int func) {
		this.part=part;
		datosTest= new HashMap<>();
		datosTrain= new HashMap<>();
		this.func= func;
		this.k= k;
		if(func <= 2)
			recCon= new RecomendadorContenido(path, k, 0);
		else
			recCol= new RecomendadorColaborativo(path, k, 0);
	}

	/**
	 * Separa los datos en train y test
	 * 
	 * 
	 */
	public void particion() {
		
		Random r = new Random();
		UserRatedMovies urm = null;
		if(recCon!=null)
			urm = recCon.getUrm();
		else
			urm = recCol.getUrm();
		for(Entry<Integer, HashMap<Integer,Double>> peliculas: urm.getDatos().entrySet()){
			for(Entry<Integer,Double> pelicula: peliculas.getValue().entrySet()){
				if (r.nextDouble()<part){
					HashMap<Integer, Double> peliculasTrain = datosTrain.get(peliculas.getKey());
					
					if(peliculasTrain ==null){
						peliculasTrain = new HashMap<>();
						peliculasTrain.put(pelicula.getKey(), pelicula.getValue());
						datosTrain.put(peliculas.getKey(), peliculasTrain);
					}
					else{
						peliculasTrain.put(pelicula.getKey(),pelicula.getValue());
					}
						
				}
				else{
					HashMap<Integer, Double> peliculasTest = datosTest.get(peliculas.getKey());
					
					if(peliculasTest ==null){
						peliculasTest = new HashMap<>();
						peliculasTest.put(pelicula.getKey(), pelicula.getValue());
						datosTest.put(peliculas.getKey(), peliculasTest);
					}
					else{
						peliculasTest.put(pelicula.getKey(),pelicula.getValue());
					}
				}
			}
		}
		
		
	}
	
	/**
	 * 
	 * Genera las similitudes con los datos de train
	 * 
	 */
	public void train(){
		if(func > 2){
			recCol.getUrm().setDatos(datosTrain);
			if(func == 3)
				recCol.getUrm().similitudCoseno();
			else if(func == 4)
				recCol.getUrm().similitudPearson();
		}
		else{
			recCon.getUrm().setDatos(datosTrain);
			if(func == 1)
				recCon.getMt().similitudCoseno();
			else if(func == 2)
				recCon.getMt().similitudBinJaccard();
		}
	}

	/**
	 * Calcula el MAE
	 * @return un double con el valor de MAE
	 */
	public double MAE() {

		double mae = 0;
		int size=0;
		for(Entry<Integer, HashMap<Integer,Double>> peliculas: datosTest.entrySet()){
			for(Entry<Integer,Double> pelicula: peliculas.getValue().entrySet()){
				double fallo=0;
				if(func>2){
					recCol.setK(k);
					fallo=Math.abs(recCol.knnColaborativo(peliculas.getKey(), pelicula.getKey()));
				}
				else{
					recCon.setK(k);
					fallo=Math.abs(recCon.knnContenido(peliculas.getKey(), pelicula.getKey()));
				}

				fallo -= pelicula.getValue();
				mae += Math.abs(fallo);
				size++;
			}
		}
		return mae/size;
	}

	/**
	 * Calcula el RMSE
	 * @return un double con el valor de RMSE
	 */
	public double RMSE() {

		double rmse = 0;
		int size=0;
		for(Entry<Integer, HashMap<Integer,Double>> peliculas: datosTest.entrySet()){
			for(Entry<Integer,Double> pelicula: peliculas.getValue().entrySet()){
				double fallo=0;
				if(func>2){
					recCol.setK(k);
					fallo=Math.abs(recCol.knnColaborativo(peliculas.getKey(), pelicula.getKey()));
				}
				else{
					recCon.setK(k);
					fallo=Math.abs(recCon.knnContenido(peliculas.getKey(), pelicula.getKey()));
				}
				fallo -= pelicula.getValue();
				rmse += fallo*fallo;
				size++;
			}
		}
		return Math.sqrt(rmse/size);
	}

	
	/**
	 * @return the part
	 */
	public double getPart() {
		return part;
	}

	/**
	 * @param part the part to set
	 */
	public void setPart(double part) {
		this.part = part;
	}

	/**
	 * @return the datosTrain
	 */
	public HashMap<Integer, HashMap<Integer, Double>> getDatosTrain() {
		return datosTrain;
	}

	/**
	 * @param datosTrain the datosTrain to set
	 */
	public void setDatosTrain(HashMap<Integer, HashMap<Integer, Double>> datosTrain) {
		this.datosTrain = datosTrain;
	}

	/**
	 * @return the datosTest
	 */
	public HashMap<Integer, HashMap<Integer, Double>> getDatosTest() {
		return datosTest;
	}

	/**
	 * @param datosTest the datosTest to set
	 */
	public void setDatosTest(HashMap<Integer, HashMap<Integer, Double>> datosTest) {
		this.datosTest = datosTest;
	}

	/**
	 * @return the recCon
	 */
	public RecomendadorContenido getRecCon() {
		return recCon;
	}

	/**
	 * @param recCon the recCon to set
	 */
	public void setRecCon(RecomendadorContenido recCon) {
		this.recCon = recCon;
	}

	/**
	 * @return the recCol
	 */
	public RecomendadorColaborativo getRecCol() {
		return recCol;
	}

	/**
	 * @param recCol the recCol to set
	 */
	public void setRecCol(RecomendadorColaborativo recCol) {
		this.recCol = recCol;
	}

	/**
	 * @return the func
	 */
	public int getFunc() {
		return func;
	}

	/**
	 * @param func the func to set
	 */
	public void setFunc(int func) {
		this.func = func;
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

	public static void main(String[] args) {
		
		Evaluacion ev= new Evaluacion(0.8, "hetrec2011-movielens-2k-v2", 40, 4);
	
		System.out.println("Creando particion");
		ev.particion();
		System.out.println("Train");
		ev.train();
		ev.setK(2);
		System.out.println("Test 2 vecinos");
		System.out.println("MAE:"+ev.MAE());
		System.out.println("RMSE:"+ev.RMSE());
		
		
		ev.setK(16);
		System.out.println("Test 16 vecinos");
		System.out.println("MAE:"+ev.MAE());
		System.out.println("RMSE:"+ev.RMSE());
		
		
		ev.setK(128);
		System.out.println("Test 128 vecinos");
		System.out.println("MAE:"+ev.MAE());
		System.out.println("RMSE:"+ev.RMSE());
		
		

	}
}
