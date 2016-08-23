package datos;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class  Datos {
	
	public static enum TiposDeAtributos {Continuo, Nominal};
	private Hashtable<String, Double> simbolos = new Hashtable<String, Double>();
	private ArrayList<Integer> countAtributos;
	private ArrayList<TiposDeAtributos> tipoAtributos;
	private Hashtable<Double,Integer> clases;
	private double [][]datos;
	private BitSet [][]datosBS;
	private Stack<String> clasesName = new Stack<String>();
	private Stack<Double> clasesHashVal = new Stack<Double>();
	private ArrayList<String> nombreAtributos;
	private int numDatos=0;
	Hashtable<Double,BitSet> hashClase;
	
	public Hashtable<String, Double> getSimbolos(){
		return simbolos;
	}
	
	public ArrayList<String> getNombreAtributos(){
		return nombreAtributos;
	}
	
	public Stack<String> getClasesName(){
		return clasesName;
	}
	
	public Stack<Double> getClasesHashVal(){
		return clasesHashVal;
	}
	
	public Datos(int numDatos, ArrayList<TiposDeAtributos> tipos) {
		this.numDatos=numDatos;
		tipoAtributos = tipos;
		clases = new Hashtable<Double,Integer>();
		countAtributos = new ArrayList<Integer>();
		datos = new double[numDatos][tipos.size()];
	}
	
	public void redondear(int divisor){
		for(int i=0; i<this.numDatos; i++){
			for(int j=0; j<countAtributos.size()-1; j++){
				this.datos[i][j]=Math.round(this.datos[i][j]/divisor);
			}
		}
	}

	public void Norm() {
		ArrayList<Double> medias= new ArrayList<Double>();
		ArrayList<Double> varianzas= new ArrayList<Double>();
		
		for(int i=0;i<countAtributos.size()-1;i++){
			medias.add(0.0);
			varianzas.add(0.0);
		}
		
		for (int i=0;i<countAtributos.size()-1;i++){
			double media=0.0;
			for (int j=0;j<numDatos;j++){
				media+=datos[j][i];
			}	
			medias.set(i,media/numDatos);		
		}
		for (int i=0;i<countAtributos.size()-1;i++){
			for (int j=0;j<numDatos;j++){
				varianzas.set(i,varianzas.get(i)+Math.pow(datos[j][i] -medias.get(i), 2));
			}	
			varianzas.set(i,varianzas.get(i)/numDatos);
		}

		for (int i=0;i<countAtributos.size()-1;i++){
			for (int j=0;j<numDatos;j++){
				if(varianzas.get(i)==0.0)
					datos[j][i]=(datos[j][i]-medias.get(i));
				
				else
					datos[j][i]=(datos[j][i]-medias.get(i))/Math.sqrt(varianzas.get(i));
			}	
		}
		
		return;
	}
	
	public boolean isNominal(TiposDeAtributos i){
		if(i==TiposDeAtributos.Nominal){
			return true;
		}
		return false;
	}
	
	public void iniDatosBs(){
		datosBS= new BitSet[numDatos][getSizeCountAtributos()];
	}
	
	public void hashClasePut(Double key, BitSet value){
		hashClase.put(key, value);
	}
	public BitSet hashClaseGet(Double key){
		return hashClase.get(key);
	}
	public Hashtable<Double, BitSet> getHashClase() {
		return hashClase;
	}

	public void setHashClase(Hashtable<Double, BitSet> hashClase) {
		this.hashClase = hashClase;
	}

	public Hashtable<Double, Integer> getClases() {
		return clases;
	}
	
	public Integer getClasesValue(Double key) {
		return clases.get(key);
	}

	public void setClases(Hashtable<Double, Integer> clases) {
		this.clases = clases;
	}

	public ArrayList<Integer> getCountAtributos() {
		return countAtributos;
	}

	public void setCountAtributos(ArrayList<Integer> countAtributos) {
		this.countAtributos = countAtributos;
	}

	public ArrayList<TiposDeAtributos> getTipoAtributos() {
		return tipoAtributos;
	}

	public void setTipoAtributos(ArrayList<TiposDeAtributos> tipoAtributos) {
		this.tipoAtributos = tipoAtributos;
	}

	public double[][] getDatos() {
		return datos;
	}
	
	public void setDatoBS(int i, int j, BitSet valor) {
		datosBS[i][j] = valor;
	}
	
	public BitSet getDatoBS(int i, int j) {
		return datosBS[i][j];
	}

	public void setDatos(double[][] datos) {
		this.datos = datos;
	}

	public void setNumDatos(int numDatos) {
		this.numDatos = numDatos;
	}

	public double getDato(int i, int j){
		return datos[i][j];
	}
	
	public int getNumDatos(){
		return numDatos;
	}
	
	public int getSizeTipoAtributos(){
		return tipoAtributos.size();
	}
	
	public int getSizeCountAtributos(){
		return countAtributos.size();
	}
	
	public Datos copiarDatos(){
		Datos particion = new Datos(this.datos.length, this.tipoAtributos);
		for (int i=0, j=0;i<this.datos.length;i++){
			particion.datos[j++]=this.datos[i];
		}

		particion.setNumDatos(this.datos.length);
		particion.setTipoAtributos(this.getTipoAtributos());
		particion.setCountAtributos(this.getCountAtributos());
		particion.clases=this.clases;
		return particion;
	}
	
	public Datos getParticion(ArrayList<Integer> indices){
		/*Devuelve un nuevo objeto datos construidos a partir de las lineas que se quieren*/
		
		Datos particion = new Datos(indices.size(), this.tipoAtributos);
		for (int i=0, j=0;i<this.datos.length;i++){
			if(indices.contains(i)){
				particion.datos[j++]=this.datos[i];
			}
		}

		particion.setNumDatos(indices.size());
		particion.setTipoAtributos(this.getTipoAtributos());
		particion.setCountAtributos(this.getCountAtributos());
		particion.clases=this.clases;
		return particion;
	}
	
	public void mezclarDatos(){
		/*Metodo no necesario porque ya se eligen indices aleatorios en la validacion*/
		
		int pos;
		Stack <Integer> nums = new Stack <Integer> ();
		double [][]datosNuevos = new double[numDatos][tipoAtributos.size()];
		
		/*Obtener los indices mezclados aleatoriamente*/
	    for (int i=0; i<numDatos ; i++) {
	    	pos = (int) Math.floor(Math.random() * numDatos );
	    	while (nums.contains(pos)) {
	    		pos = (int) Math.floor(Math.random() * numDatos );
	    	}
	    	nums.push(pos);
	    }
	    
	    /*Hacemos la nueva matriz de datos*/
	    for (int i=0; i<numDatos; i++) {
	    	datosNuevos[i]= datos[nums.pop()];
	    }
	    datos = datosNuevos;
		return;
	}
	
	public String maximaVerosimilitud(String atributo, String valor, String clase){
		/*Recorremos todos los atributos*/
		for(int i=0; i<this.getSizeCountAtributos(); i++){
			if(this.getNombreAtributos().get(i).equals(atributo)){
				/*Hemos encontrado el que nos interesa*/
				Stack<String> aux = this.getClasesName();
				Stack<Double> aux2 = this.getClasesHashVal();
				/*Traducimos la clase*/
				while(!aux.empty()){
					String s = aux.pop();
					Double hashVal = aux2.pop();
					if(s.equals(clase)){
						/*Recorremos los atributos*/
						Double numerador=0.0, denominador=0.0;
						/*Recorremos todos los datos*/
						for(int j=0; j<this.numDatos; j++){
							if(this.getDato(j, this.getSizeCountAtributos()-1)==hashVal){
								denominador++;
								if(this.getDato(j, i)==this.simbolos.get(valor))
									numerador++;
							}
						}
						return "P("+atributo+"="+valor+"|Class="+clase+") = "+(String.format("%.2f",numerador/denominador));
					}
				}
			}
		}
		return null;
	}
	
	public static Datos cargaDeFichero(String nombreDeFichero) {
		BufferedReader br = null;
		try {

			String sCurrentLine;

			/*Leemos las primeras lineas*/
			br = new BufferedReader(new FileReader(nombreDeFichero));
			int numDatos = Integer.parseInt(br.readLine());
			sCurrentLine= br.readLine();
			String [] auxstr = sCurrentLine.split(",");
		    ArrayList<String> tmp = new ArrayList<String>();
			for(String s: auxstr){
				tmp.add(s);
			}

			/*Leemos si los atributos son nominales o continuos*/
			sCurrentLine=br.readLine();
			String [] parts = sCurrentLine.split(",");
			ArrayList<TiposDeAtributos> tipos=new ArrayList<TiposDeAtributos>();
			for(String s: parts){
				if (s.equals("Nominal")){
					tipos.add(TiposDeAtributos.Nominal);
				}else if(s.equals("Continuo")){
					tipos.add(TiposDeAtributos.Continuo);
				}
			}

			/*Creamos el objetos Datos*/
			Datos data = new Datos(numDatos, tipos);
			data.nombreAtributos=tmp;
			for(int i=0;i<parts.length;i++)
				data.countAtributos.add(0);
			int i =0;
			double hashval=0;
			/*Leemos todas las lineas que contienen datos*/
			while ((sCurrentLine = br.readLine()) != null) {
				parts = sCurrentLine.split(",");
				int j=0;
				/*Leemos cada una de las columnas de los datos*/
				for (String s: parts){
					try{
						if (tipos.get(j) == TiposDeAtributos.Continuo){
							//Guardamos los continuos tal cual, salvo las ?
							if(s!="?"){
								data.datos[i][j] = Double.parseDouble(s);
							}else
								data.datos[i][j]=Double.MAX_VALUE;
						}else{
							//De tipo nominal
							if(data.simbolos.get(s)==null){
								//Nuevo valor, obtenemos valor hash
								data.simbolos.put(s, hashval);
								data.countAtributos.set(j, data.countAtributos.get(j)+1);
								if(j==data.countAtributos.size()-1){
									data.clasesName.push(s);
									data.clasesHashVal.push(hashval);
								}
								data.datos[i][j] = hashval;
								hashval++;
							}else{
								//Ya existe el valor en el hash
								data.datos[i][j] = data.simbolos.get(s);
							}		
						}
					}
					catch(NumberFormatException e){

					}
					j++;
				}
				i++;
			}
			
			int n=0;
			double media=0.0;
			/*Recorremos los atributos*/
			for(int k=0;k<data.getSizeTipoAtributos();k++){
				/*Si el atributo es continuo, obtenemos la media*/
				if(data.getTipoAtributos().get(k)==TiposDeAtributos.Continuo){
					for(int j=0;j<data.getNumDatos();j++){
						if(data.datos[j][k]!=Double.MAX_VALUE){
							media+=data.datos[j][k];
							n++;
						}
					}
					media/=n;
					/*Sustituimos los valores desconocidos por la media*/
					for(int j=0;j<data.getNumDatos();j++){
						if(data.datos[j][k]==Double.MAX_VALUE){
							data.datos[j][k]=media;

						}
					}
				}

			}
			
			/*Realizamos un hash de las clases*/
			Hashtable<Double, Integer> aux=new Hashtable<Double, Integer>();
			for(int k=0, j=0; k<numDatos; k++){
				if(!aux.containsKey(data.getDato(k, data.getSizeTipoAtributos()-1))){
					aux.put(data.getDato(k, data.getSizeTipoAtributos()-1), j++);
				}
			}
			data.setClases(aux);
			
			return data;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	public String toString(){
		String str= new String();
		for(int i=0;i<this.numDatos;i++){
			for(int j=0;j<this.countAtributos.size();j++){
				str+="["+datos[i][j]+"]";
			}
			str+="\n";
		}
		return str;
	}
}
