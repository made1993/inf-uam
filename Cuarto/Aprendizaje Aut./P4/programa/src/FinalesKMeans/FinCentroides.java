package FinalesKMeans;

import java.util.ArrayList;

import interfazKMeans.finKMeans;

public class FinCentroides implements finKMeans{
private ArrayList<Double[]> centroidesAnt;
	
	int reasignaciones;
	public FinCentroides(int reasignaciones) {
		centroidesAnt=null;
		this.reasignaciones=reasignaciones;
				
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
		
		int cambios=0;
		for (int i=0; i<centroides.size(); i++){
			for(int j=0; j<centroides.get(i).length-1; j++){
				
				if(!centroides.get(i)[j].equals(centroidesAnt.get(i)[j])){
					cambios++;	
				}
			}
		}
		if (cambios<=reasignaciones)
			return true;
		else 
			return true;
	}
	@Override
	public boolean fin(ArrayList<Double[]> centroides,ArrayList<Integer> clusters) {
		
		
		if(centroidesAnt==null){
			centroidesAnt= clonarCentroides(centroides);
			return true;
		}
		else if(iguales(centroides)){
			centroidesAnt=null;
			return false;
		}
		else{
			centroidesAnt= clonarCentroides(centroides);
			return true;
		}
	}

}
