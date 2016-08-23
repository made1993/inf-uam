package FinalesKMeans;

import java.util.ArrayList;

import interfazKMeans.finKMeans;

public class FinRondas implements finKMeans {

	private ArrayList<Double[]> centroidesAnt;
	
	int minRondas;
	int ronda;
	public FinRondas(int minRondas) {
		centroidesAnt=null;
		this.minRondas= minRondas;
		ronda=0;
				
	}

	private Double[] clonarCentroide(int indice, ArrayList<Double[]> centroides){
		int size =centroides.get(0).length;
		Double[] ret = new Double[size];
		for(int i=0; i< size;i++){
			ret[i]=centroides.get(indice)[i];
		}
		return ret;
	}
	public ArrayList<Double[]> clonarCentroides(ArrayList<Double[]> centroides){
		ArrayList<Double[]> ret = new ArrayList<Double[]>();
		for(int i=0; i < centroides.size();i++){
			ret.add(clonarCentroide(i, centroides));
		}
		return ret;
	}
	private boolean iguales(ArrayList<Double[]> centroides){
		boolean ret =true;
		for (int i=0; i<centroides.size(); i++){
			for(int j=0; j<centroides.get(i).length-1; j++){
				
				if(!centroides.get(i)[j].equals(centroidesAnt.get(i)[j])){
					ret = false;	
				}
			}
		}
		return ret;
	}
	@Override
	public boolean fin(ArrayList<Double[]> centroides,ArrayList<Integer> clusters) {
		ronda++;
		if (minRondas<=ronda){
			ronda=0;
			centroidesAnt=null;
			return false;
		}
		
		
		if(centroidesAnt==null){
			centroidesAnt= clonarCentroides(centroides);
			return true;
		}
		else if(iguales(centroides)){
			centroidesAnt=null;
			ronda=0;
			return false;
		}
		else{
			centroidesAnt= clonarCentroides(centroides);
			return true;
		}
	}

}
