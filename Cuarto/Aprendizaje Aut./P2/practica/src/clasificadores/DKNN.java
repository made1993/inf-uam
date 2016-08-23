package clasificadores;

import java.util.*;

import datos.Datos;
import particionado.*;

public class DKNN extends Clasificador{
	private Datos dataTest;
	private Datos dataTrain;
	private int knnMax;
	private int knnMin;
	private int knnIncr;
	private int knn;
	private ArrayList<Double> res;
	static private ArrayList<Integer> listK = new ArrayList<Integer>();
	
	public DKNN(int min, int max, int incr){
		knnMax=max;
		knnMin=min;
		knnIncr=incr;
	}
	
	@Override
	public void entrenamiento(Datos datosTrain) {
		dataTrain=datosTrain.copiarDatos();
		int porcentaje=10;
		EstrategiaParticionado part1 =new ValidacionCruzada(porcentaje);
		double val= Double.MAX_VALUE;
		if(knnMax>datosTrain.getNumDatos()/porcentaje)
			knnMax=datosTrain.getNumDatos()/porcentaje;
		for(int i=knnMin;i<knnMax; i+=knnIncr){
			KNN clas= new KNN(i);
			ArrayList<Double> errores = Clasificador.validacion(part1, datosTrain, clas);
			Double suma=0.0;
			for(Double e: errores){
				suma = suma + e;
			}
			if(val>suma/errores.size()*100){
				val=suma/errores.size()*100;
				knn=i;
			}
		}
		System.out.println("mejor:"+knn);
		listK.add(knn);
	}
	
	public static int getBestK(){
		Collections.sort(listK, new Comparator<Integer>(){
		    public int compare(Integer s1, Integer s2) {
		        return Integer.compare(s1, s2);
		    }
		});
		
		Hashtable<Integer,Integer> frequencymap = new Hashtable<Integer,Integer>();
		for(Integer a: listK) {
		  if(frequencymap.containsKey(a)) {
		    frequencymap.put(a, frequencymap.get(a)+1);
		  }
		  else{ frequencymap.put(a, 1); }
		}
		
		Enumeration<Integer> keys = frequencymap.keys();
		int max = 0;
		int key = 0;
		while(keys.hasMoreElements()){
			int a = keys.nextElement();
			if(frequencymap.get(a)>max){
				max=frequencymap.get(a);
				key=a;
			}
		}
		return key;
	}
	
	@Override
	public ArrayList<Double> clasifica(Datos datosTest) {
		dataTest= datosTest;
		Clasificador clas = new KNN(knn);
		clas.entrenamiento(dataTrain);
		res = clas.clasifica(dataTest);
		return res;
	}

	@Override
	public ArrayList<Double> getResultado() {
		return res;
	}
}