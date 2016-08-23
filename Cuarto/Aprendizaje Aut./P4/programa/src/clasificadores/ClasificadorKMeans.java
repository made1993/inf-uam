package clasificadores;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import datos.Datos;
import interfazKMeans.*;

public class ClasificadorKMeans extends  Clasificador  {
	
	int nClusters;
	metricaKMeans metrica;
	ArrayList<Double[]> centroides;
	ArrayList<Integer> clusters;
	finKMeans fin;
	ArrayList<Double> res;
	
	public ClasificadorKMeans(finKMeans fin,metricaKMeans metrica, int nClusters) {
		this.metrica=metrica;
		this.nClusters=nClusters;
		this.fin=fin;
		this.centroides= new ArrayList<Double[]>();
		this.clusters= new ArrayList<Integer>();
	}
	/**
	 * Clona cosas.
	 * 
	 * @param indice
	 * @param datosTrain
	 * @return
	 */
	private Double[] clonar(int indice, Datos datosTrain){
		int size =datosTrain.getSizeCountAtributos();
		Double[] ret = new Double[size];
		for(int i=0; i< size;i++){
			ret[i]=datosTrain.getDato(indice, i);
		}
		return ret;
	}
	/**
	 * Selecciona aleatoriamente que posiciones van a ser
	 * el centroide de los clusters.
	 * 
	 * @param datosTrain
	 */
	private void generarCentroides( Datos datosTrain){
		Random r= new Random();
		for (int i=0; i < nClusters; i++){
			int indice= r.nextInt(datosTrain.getNumDatos());
			centroides.add(clonar(indice,datosTrain));
		}
		
	}
	
	
	/**
	 * Cambia el valor de los cnetroides de cada cluster 
	 * poniendolos a cero.
	 */
	private void resetCentroides(){
		for (int i=0; i<clusters.size(); i++){
			for(int j=0; j<centroides.get(clusters.get(i)).length; j++){
				centroides.get(clusters.get(i))[j]=0.0;
			}
		}
	}
	/**
	 * Busca que datos pertenecen al cluster identificado
	 * por "indice" y suma las cordenadas de los al centroide.
	 * 
	 * @param indice
	 * @param dato
	 */
	private void sumCentroide(int indice, Double[] dato){
		for (int i=0; i<dato.length; i++){
			centroides.get(indice)[i]+=dato[i];
		}
	}
	
	/**
	 * Divide las coordeandas del centroide por un valor.
	 * 
	 * @param indice
	 * @param val
	 */
	private void divCentroide(int indice, int val){
		for (int i=0; i<centroides.get(indice).length; i++){
			centroides.get(indice)[i]/=val;
		}
	}
	
	/**
	 * Reubica los centroides de los clusters.
	 * 
	 * @param datos
	 */
	private void reubicarCentroides(Datos datos){
		resetCentroides();
		
		for(int i=0; i<nClusters; i++){
			int rps=0;
			for(int j=0; j<clusters.size(); j++){
				if(clusters.get(j)==i){
					sumCentroide(i, clonar(j, datos));
					rps++;
				}
			
			}
			divCentroide(i, rps);
		}
	}
	
	/**
	 * Asigna un dato al centroide mas cercano.
	 * 
	 * @param datosTrain
	 */
	public void asignacionCluster(Datos datosTrain){
		clusters.clear();
		for(int i=0; i < datosTrain.getNumDatos(); i++){
			double min=Double.MAX_VALUE;
			int centr=0;
			for(int j=0; j < centroides.size();j++){
				double val=metrica.metrica(centroides.get(j), clonar(i, datosTrain));
				if(min > val){
					min =val;
					centr=j;
				}
			}
			clusters.add(centr);
		}
	}
	/**
	 * Imprime que predice cada cluster.
	 */
	public void printPrediccion(){
		for (int i=0; i< centroides.size(); i++){
			System.out.println("Cluster "+i+" predice:"+ centroides.get(i)[centroides.get(i).length-1]);
		}
	}
	
	/**
	 * Asigna que predice cada cluster haciendo la moda.
	 * 
	 * @param datos
	 */
	public void prediccionCluster(Datos datos){
		for (int i=0; i<nClusters;i++){
			Hashtable<Double, Integer> countCluster= new Hashtable<Double, Integer>();
			for(int j=0; j<datos.getNumDatos(); j++){
				Double key= datos.getDato(j, datos.getSizeCountAtributos()-1);
				if(clusters.get(j)==i){
					if(!countCluster.containsKey(key)){
						countCluster.put(key, 1);
					}
					else{
						countCluster.put(key, countCluster.get(key)+1);
					}
				}
			}
			
			Enumeration<Double> keys= countCluster.keys();
			double prediccion=0.0;
			int max=0;
			while(keys.hasMoreElements()){
				Double key= keys.nextElement();
				if(max<countCluster.get(key)){
					max = countCluster.get(key);
					prediccion=key;
				}
			}
			centroides.get(i)[datos.getSizeCountAtributos()-1]=prediccion;
		}
	}
	
	@Override
	public void entrenamiento(Datos datosTrain) {
		generarCentroides(datosTrain);
		do{
			asignacionCluster(datosTrain);
			reubicarCentroides(datosTrain);
		}while(fin.fin(centroides,clusters));
		//System.out.println("termina");
		//asignacionCluster(datosTrain);
		prediccionCluster(datosTrain);
		//printPrediccion();
	}

	@Override
	public ArrayList<Double> clasifica(Datos datosTest) {
		res= new ArrayList<Double>();
		for(int i=0; i<datosTest.getNumDatos(); i++){
			double min= Double.MAX_VALUE;
			double prediccion=0.0, val=0.0;
			for(int j=0; j<centroides.size(); j++){
				val= metrica.metrica(centroides.get(j), clonar(i, datosTest));
				if (min > val){
					prediccion =centroides.get(j)[centroides.get(j).length -1];
					min= val;
				}
			}
			res.add(prediccion);
		}
		centroides.clear();
		return res;
	}

	@Override
	public ArrayList<Double> getResultado() {
		return res;
	}

}
