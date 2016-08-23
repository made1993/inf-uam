package clasificadores;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

import datos.*;

public class ClasificadorAPriori extends Clasificador {
	
	private Double maxClase;
	private ArrayList<Double> resultado;
	private Hashtable<Double,Integer> repClase;
	private int numDatos;
	private Stack<String> names;
	
	public String toString(){
		String res = "";
		Enumeration<Double> keys = repClase.keys();
		while(keys.hasMoreElements()){
			Double key = keys.nextElement();
			
			res = res+"\nP(Class="+names.pop()+") = "+(String.format("%.2f", repClase.get(key)/(numDatos*1.0)));
		}
		return res.substring(1);		
	}
	
	public ArrayList<Double> getResultado() {
		return resultado;
	}
	
	public Integer getRepClaseValue(Double key){
		return repClase.get(key);
	}

	public void setResultado(ArrayList<Double> resultado) {
		this.resultado = resultado;
	}

	@Override
	public void entrenamiento(Datos datosTrain) {
		double clase;
		repClase= new Hashtable<Double, Integer>();
		this.numDatos = datosTrain.getNumDatos();
		this.names = datosTrain.getClasesName();
		
		/*Recorremos los datos contando el numero de repeticiones de cada clase*/
		for(int i=0; i<datosTrain.getNumDatos(); i++){
			clase = datosTrain.getDato(i, datosTrain.getSizeTipoAtributos()-1);
			if(repClase.get(clase)==null)
				repClase.put(clase, 0);
			//else
				//repClase.replace(clase, repClase.get(clase)+1);
		}
		
		/*Seleccionamos la clase con mayor numero de repeticiones*/
		Integer maxValue=0;
		for (Double d:repClase.keySet()){
			if (repClase.get(d)>maxValue){
				maxValue =repClase.get(d); 
				maxClase= d;
			}
		}	
	}
	
	@Override
	public ArrayList<Double> clasifica(Datos datosTest) {
		ArrayList<Double> res = new ArrayList<Double>();
		for(int i=0; i<datosTest.getNumDatos(); i++){
			res.add(maxClase);
		}
		this.resultado=res;
		return res;
	}

	public Double getMaxClase() {
		return maxClase;
	}

	public void setMaxClase(Double maxClase) {
		this.maxClase = maxClase;
	}
	
}