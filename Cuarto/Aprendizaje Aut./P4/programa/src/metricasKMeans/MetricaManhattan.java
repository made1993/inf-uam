package metricasKMeans;

import interfazKMeans.metricaKMeans;

public class MetricaManhattan implements metricaKMeans{

	@Override
	public double metrica(Double[] x1, Double[] x2) {
		double distancia=0.0;
		for(int i=0; i< x1.length -1; i++){
			distancia+= Math.abs(x1[i]-x2[i]);
		}
		return distancia;
	}

}
