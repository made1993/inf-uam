package clasificadores;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

import datos.Datos;

public class ClasificadorNaiveBayesNorm extends Clasificador {

	public enum TiposDeAtributos {Continuo, Nominal};
	private ClasificadorAPriori aPriori;
	private ArrayList<Hashtable<Double, ArrayList<Integer>>> tablaNB;  
	private ArrayList<Double> res;
	private ArrayList<ArrayList<Double>> medias;
	private ArrayList<ArrayList<Double>> varianzas;
	private int numDatos;
	
	ClasificadorNaiveBayesNorm(boolean laplace){
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
	
	public String datosNormal(Datos d, String atributo, String clase){
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
						return  "Atributo "+atributo+", Media="+medias.get(d.getClasesValue(hashVal).intValue()).get(i)+
								", Varianza="+varianzas.get(d.getClasesValue(hashVal).intValue()).get(i);
					}
				}
				
			}
		}
		
		return null;
	}
	
	public void normalizar(Datos datos){
		medias = new  ArrayList<ArrayList<Double>>();
		varianzas = new  ArrayList<ArrayList<Double>>();
		Enumeration<Double> keys_a = datos.getClases().keys();
		Stack<Double> keys = new Stack<Double>();
		while(keys_a.hasMoreElements()){
			Double key = keys_a.nextElement();
			keys.add(key);	
		}
		
		/*Recorremos las clases*/
		while(keys.isEmpty()==false){
			Double key = keys.pop();
			ArrayList<Double> mediastmp = new ArrayList<Double>();
			ArrayList<Double> varianzatmp = new ArrayList<Double>();
			/*Recorremos los atributos*/
			for(int j=0; j<datos.getSizeTipoAtributos()-1; j++){
				/*Solo realizamos los datos para los continuos*/
				if (!datos.isNominal(datos.getTipoAtributos().get(j))){
					Double media=0.0;
					Double varianza=0.0;
					Double count=0.0;
					/*Calculamos la media*/
					for(int i=0; i<datos.getNumDatos(); i++){
						if(datos.getDato(i, datos.getSizeTipoAtributos()-1) == key){
							media += datos.getDato(i, j);
							count++;
						}
					}
					media=media/count;
					/*Calculamos la varianza*/
					for(int i=0; i<datos.getNumDatos(); i++){
						if(datos.getDato(i, datos.getSizeTipoAtributos()-1) == key){
							varianza += Math.pow(datos.getDato(i, j)-media, 2);
						}
					}
					varianza=varianza/count;
					mediastmp.add(media);
					varianzatmp.add(varianza);
				}else{
					mediastmp.add(0.0);
					varianzatmp.add(0.0);
				}
			}
			medias.add(mediastmp);
			varianzas.add(varianzatmp);
		}
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
				if(tablaNB.get(i).containsKey(datosTrain.getDato(j,i))){
					/*Dato ya presente*/
					int n =  datosTrain.getClasesValue(datosTrain.getDato(j,datosTrain.getSizeCountAtributos()-1));
					ArrayList<Integer> aux= tablaNB.get(i).get(datosTrain.getDato(j,i));
					aux.set(n, aux.get(n)+1);
				}else{
					/*Nuevo dato*/
					ArrayList<Integer> value = new ArrayList<Integer>();
					for(int k=0 ; k<datosTrain.getClases().size() ;k++){
						int n =  datosTrain.getClasesValue(datosTrain.getDato(j,datosTrain.getSizeCountAtributos()-1));
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
		/*Hallamos la normal de los continuos*/
		this.normalizar(datosTrain);
		/*Aplicamos Laplace*/
		if(this.fLaplace)
			laplace();
	}
	
	private Double prediccion(Double h, Datos d, int fila){
		if(this.fLaplace)
			laplace();
		Double res =  aPriori.getRepClaseValue(h)/(numDatos*1.0);
		/*Recorremos los atributos*/
		for(int i=0; i<d.getSizeCountAtributos()-1; i++){
			if(d.isNominal(d.getTipoAtributos().get(i))){
				/*Atributo nominal*/
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
					res = aPriori.getRepClaseValue(h)/(numDatos*1.0);
				}
				else{
					/*Vamos realizando los calculos*/
					Integer n=clases.get(d.getClasesValue(h));
					res*=n/(reps*1.0);	
				}
			}else{
				/*Atributo continuo, obtenemos media y varianza y aplicamos la formula*/
				ArrayList<Double> m = medias.get(d.getClasesValue(h));
				ArrayList<Double> v = varianzas.get(d.getClasesValue(h));
				Double med = m.get(i);
				Double var = v.get(i);
				Double tmp = 1/(var*Math.sqrt(2*Math.PI));
				tmp = tmp * Math.pow(Math.E, -0.5*Math.pow((d.getDato(fila, i) -med)/var, 2));
				res*=tmp;
			}
		}
		return res;
	}
	
	@Override
	public ArrayList<Double> clasifica(Datos datosTest) {
		ArrayList<Double> res = new ArrayList<Double>();
		/*Recorremos los datos*/
		for(int i=0;i<datosTest.getNumDatos();i++){
			Double max=-1.0;
			Double prec=-1.0;
			Enumeration<Double> key= datosTest.getClases().keys();
			Double precKey=0.0;
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
