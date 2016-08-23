package clasificadores;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

import datos.Datos;

public class ClasificadorNaiveBayes extends Clasificador {
	private ClasificadorAPriori aPriori;
	private ArrayList<Hashtable<Double, ArrayList<Integer>>> tablaNB;  
	private ArrayList<Double> res;
	private int numDatos;
	
	ClasificadorNaiveBayes(boolean laplace){
		fLaplace=laplace;
	}
	
	public void laplace(){
		/*Recorremos todas las tablas*/
		for(Hashtable<Double, ArrayList<Integer>> tb:tablaNB){
			/*Detectamos si hay algun 0 en la tabla*/
			Enumeration<Double> keys =tb.keys();
			int on = 0;
			while(keys.hasMoreElements()){
				Double key =keys.nextElement();
				ArrayList<Integer> values=tb.get(key);
				for(int i=0;i<values.size();i++){
					if(values.get(i)==0){
						on=1;
						break;	
					}
				}
				if (on==1) break;
			}
			/*Si hay alguno, procedemos a sumar*/
			keys =tb.keys();
			if(on==1){
				while(keys.hasMoreElements()){
					Double key =keys.nextElement();
					ArrayList<Integer> values=tb.get(key);
					for(int i=0;i<values.size();i++){
						values.set(i, values.get(i)+1);
					}
					//tb.replace(key, values);
				}
			}
		}
	}
	
	public String maximaVerosimilitud(Datos d, String atributo, String valor, String clase){
		/*Recorremos todos los atributos*/
		for(int i=0; i<d.getSizeCountAtributos(); i++){
			if(d.getNombreAtributos().get(i).equals(atributo)){
				/*Hemos encontrado el que nos interesa*/
				Stack<String> aux = d.getClasesName();
				Stack<Double> aux2 = d.getClasesHashVal();
				/*Traducimos la clase*/
				while(!aux.empty()){
					String s = aux.pop();
					Double hashVal = aux2.pop();
					if(s.equals(clase)){
						Enumeration<Double> keys=tablaNB.get(i).keys();
						ArrayList<Integer> clases=null;
	
						if(d.getSimbolos().containsKey(valor) == false){
							clases= tablaNB.get(i).get(Double.parseDouble(valor));
						}
						else{
							clases= tablaNB.get(i).get(d.getSimbolos().get(valor));
						}
						Integer reps = 0;
						Double res = 0.0;
						/*Contamos los datos en las tablas*/
						while(keys.hasMoreElements()){
							Double aux1=keys.nextElement();
							reps+=tablaNB.get(i).get(aux1).get(d.getClasesValue(hashVal));
						}
						if(clases==null){
							res = aPriori.getRepClaseValue(hashVal)/(numDatos*1.0);
						}else{
							/*Vamos realizando los calculos*/
							Integer n=clases.get(d.getClasesValue(hashVal));
							res=n/(reps*1.0);
							return "P("+atributo+"="+valor+"|Class="+clase+") = "+(String.format("%.6f",res));
						}
						
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public void entrenamiento(Datos datosTrain) {
		aPriori = new ClasificadorAPriori();
		aPriori.entrenamiento(datosTrain);
		tablaNB= new ArrayList<Hashtable<Double, ArrayList<Integer>>>();
		numDatos = datosTrain.getNumDatos();
		
		/*Recorremos los atributos*/
		for(int i=0; i<datosTrain.getSizeTipoAtributos()-1; i++){
			tablaNB.add(new Hashtable<Double, ArrayList<Integer>>());
			/*Recorremos los datos*/
			for(int j=0; j<numDatos; j++){
				Double datoAux = datosTrain.getDato(j,datosTrain.getSizeCountAtributos()-1);
				if(tablaNB.get(i).containsKey(datosTrain.getDato(j,i))){
					/*Dato ya presente*/
					int n =  datosTrain.getClasesValue(datoAux);
					ArrayList<Integer> aux= tablaNB.get(i).get(datosTrain.getDato(j,i));
					aux.set(n, aux.get(n)+1);
				}else{
					/*Nuevo dato*/
					ArrayList<Integer> value = new ArrayList<Integer>();
					for(int k=0 ; k<datosTrain.getClases().size() ;k++){
						int n =  datosTrain.getClasesValue(datoAux);
						if(n==k)
							value.add(1);
						else
							value.add(0);
					}
					Hashtable<Double, ArrayList<Integer>> copia=tablaNB.get(i);
					copia.put(datosTrain.getDato(j,i), value);
					tablaNB.set(i, copia);
				}
			}
		}
		/*Aplicamos Laplace*/
		if(this.fLaplace)
			laplace();
	}
	private Double prediccion(Double h, Datos d, int fila){
		Double res;
		if(aPriori.getRepClaseValue(h)==null){
			res =  1/(numDatos*1.0);
		}else{
			res =  aPriori.getRepClaseValue(h)/(numDatos*1.0);
		}
		/*Recorremos los atributos*/
		for(int i=0;i<d.getSizeCountAtributos()-1;i++){
			Double key= d.getDato(fila, i);
			Enumeration<Double> keys=tablaNB.get(i).keys();
			ArrayList<Integer> clases= tablaNB.get(i).get(key);
			Integer reps=0;
			/*Contamos los datos en las tablas*/
			while(keys.hasMoreElements()){
				Double aux=keys.nextElement();
				reps+=tablaNB.get(i).get(aux).get(d.getClasesValue(h));
			}
			if(clases==null){
				if(aPriori.getRepClaseValue(h)==null){
					res =  1/(numDatos*1.0);
				}else{
					res =  aPriori.getRepClaseValue(h)/(numDatos*1.0);
				}
			}else{
				/*Vamos realizando los calculos*/
				Integer n=clases.get(d.getClasesValue(h));
				res*=n/(reps*1.0);	
			}
		}
		return res;
	}
	
	@Override
	public ArrayList<Double> clasifica(Datos datosTest) {
		ArrayList<Double> res = new ArrayList<Double>();
		/*Recorremos los datos*/
		for(int i=0;i<datosTest.getNumDatos();i++){
			Double max=-1.0, prec=-1.0, precKey=-1.0;
			Enumeration<Double> key= datosTest.getClases().keys();
			/*Recorremos las clases*/
			while(key.hasMoreElements()){
				precKey=key.nextElement();
				Double value=prediccion(precKey, datosTest, i);	
				/*Si se supera la probabilidad anterior se cambio la prediccion*/
				if(value>max){
					max=value;
					prec=precKey;
				}
			}
			res.add(prec);
		}
		this.res=res;
		return res;
	}

	@Override
	public ArrayList<Double> getResultado() {
		return res;
	}

}
