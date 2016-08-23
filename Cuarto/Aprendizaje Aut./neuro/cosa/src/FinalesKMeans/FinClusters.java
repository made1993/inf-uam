package FinalesKMeans;

import java.util.ArrayList;

import interfazKMeans.finKMeans;

public class FinClusters implements finKMeans{

	ArrayList<Integer> clusters;
	int minReasignaciones;
	
	public FinClusters(int minReasignaciones) {
		this.clusters=new ArrayList<Integer>();
		this.minReasignaciones=minReasignaciones;
	}
	
	public void clonarClusters(ArrayList<Integer> clusters){
		this.clusters.clear();
		for (Integer i: clusters){
			this.clusters.add(i.intValue());
		}
		
	}
	
	public boolean isFin(ArrayList<Integer> clusters){
		int cambios=0;
		for(int i=0; i <this.clusters.size(); i++){
			if (!this.clusters.get(i).equals(clusters.get(i)))
				cambios++;
		
		}
		//System.out.println("cambios:"+cambios+" min:"+minReasignaciones);
		
		if (cambios<=minReasignaciones)
			return true;
		else
			return false;
	}
	@Override
	public boolean fin(ArrayList<Double[]> centroides, ArrayList<Integer> clusters) {
		if(this.clusters.size()==0){
			clonarClusters(clusters);
			return true;
		}
		//System.out.println(centroides);
		//System.out.println("clusters"+clusters);
		//System.out.println("this.clusters"+this.clusters+"\n");
		if(isFin(clusters)){
			this.clusters.clear();
			return false;
		}
		else{
			this.clusters.clear();
			clonarClusters(clusters);
			return true;
		}			
	}

}
