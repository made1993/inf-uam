package clasificadores;

import java.util.*;

import datos.Datos;
import datos.Datos.TiposDeAtributos;

public class KNNNB extends Clasificador{
	Datos dataTest;
	Datos dataTrain;
	int knn;
	public KNNNB(int k){
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
				//Guardamos una distancia y que fila de los datos es
				des[j][0]=de;
				des[j][1]=j;
			}
			
			Arrays.sort(des, new Comparator<double[]>() {
				@Override
				public int compare(double[] o1, double[] o2) {
					return Double.compare(o1[0], o2[0]);
				}
			});
			
			/*Creamos un clasificador NB*/
			Clasificador nb = new ClasificadorNaiveBayes(true);
			/*Creamos un indice con los k datos mas proximos*/
			ArrayList<Integer> trainInd = new ArrayList<Integer>();
			for(int k=0;k<knn;k++){
				trainInd.add((int)des[k][1]);
			}
			/*Entrenamos NB con los k datos mas proximos*/
			nb.entrenamiento(dataTrain.getParticion(trainInd));
			/*Clasificamos el dato*/
			ArrayList<Integer> testInd = new ArrayList<Integer>();
			testInd.add(i);
			ArrayList<Double> resNB = nb.clasifica(datosTest.getParticion(testInd));
			
			sol.add(resNB.get(0));	

		}		
		res = sol;
		return sol;
	}

	@Override
	public ArrayList<Double> getResultado() {
		return res;
	}

}
