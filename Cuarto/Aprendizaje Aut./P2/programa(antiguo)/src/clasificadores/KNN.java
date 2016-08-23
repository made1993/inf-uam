package clasificadores;

import java.util.*;

import datos.Datos;
import datos.Datos.TiposDeAtributos;

public class KNN extends Clasificador{
	Datos dataTest;
	Datos dataTrain;
	int knn;
	public KNN(int k){
		knn=k;
	}
	private ArrayList<Double> res;
	
	@Override
	public void entrenamiento(Datos datosTrain) {
		dataTrain=datosTrain;
	}

	@Override
	public ArrayList<Double> clasifica(Datos datosTest) {
		dataTest= datosTest;
		ArrayList<Double> sol= new ArrayList<Double>();
		//Recorremos todas las filas de test
		//System.out.println("trainS="+ dataTrain.getNumDatos()+"  testS="+datosTest.getNumDatos()+"  knn="+knn);
		for(int i=0; i<dataTest.getNumDatos();i++){
			double[][] des= new double[dataTrain.getNumDatos()][2];
			Hashtable<Double, Double> prediccion= new Hashtable<Double, Double>();
			//Recorremos nuestra base de conocimiento
			for(int j=0;j<dataTrain.getNumDatos();j++){
				Double de=0.0;
				//Distancia entre los atributos
				for(int k=0;k<dataTrain.getSizeCountAtributos()-1;k++){
					if(dataTrain.getTipoAtributos().get(k)==TiposDeAtributos.Continuo)
						de+=Math.pow(dataTrain.getDato(j, k)-dataTest.getDato(i, k), 2);
					else{
						if(dataTrain.getDato(j, k)==dataTest.getDato(i, k))
							de++;
					}
						
				}
				de=Math.sqrt(de);
				//Guardamos una distancia y el valor de la clase
				des[j][0]=de;
				des[j][1]=dataTrain.getDato(j, dataTrain.getSizeCountAtributos()-1);
			}
			
			Arrays.sort(des, new Comparator<double[]>() {
				@Override
				public int compare(double[] o1, double[] o2) {
					return Double.compare(o1[0], o2[0]);
				}
			});
			
			for(int k=0;k<knn;k++){
				if(false==prediccion.containsKey(des[k][1]))
					prediccion.put(des[k][1], 1.0);
				else
					prediccion.put(des[k][1], prediccion.get(des[k][1])+1);
			}
			Enumeration<Double> maximos = prediccion.keys();
			Double maximo = 0.0;
			Double clase = 0.0;
			while(maximos.hasMoreElements()){
				Double key = maximos.nextElement();
				Double valor = prediccion.get(key);
				if(maximo < valor){
					maximo = valor;
					clase = key;
				}
			}
			sol.add(clase);	

		}		
		res = sol;
		return sol;
	}

	@Override
	public ArrayList<Double> getResultado() {
		return res;
	}

}
