package clasesAlgoritmoGentecio;

import java.util.*;

import datos.Datos;
import interfacesAlgoritmoGenetico.*;

public class FitnessTicTac implements Fitness{

	@Override
	public void fit(Poblacion p, Datos datos) {
		for (Individuo inv: p.getIndividuos()){
			int aciertos=1;
			for(int i=0; i<datos.getNumDatos(); i++){
				for(Regla r: inv.getReglas()){
					boolean cond=true;
				    for(int j=0; j<datos.getSizeTipoAtributos()-1; j++){
						BitSet num1 = datos.getDatoBS(i, j);
						BitSet num2 = r.getRegla().get(j);
						Boolean b = num1.intersects(num2);
						if(b==false){
							cond=false;
							break;
						}
				    }

				    if(cond==true){
				    	if(datos.getDatoBS(i, datos.getSizeCountAtributos()-1).equals(r.getPrediccion())){
				    		aciertos++;
				    		break;
				    	}
				    }

				}	
			}
			inv.setFit(aciertos*1.0/datos.getNumDatos());
		}
	}
	
	public void generarBitSets(Datos datos){
		Hashtable<Double,BitSet> hash = new Hashtable<Double,BitSet>();
		datos.setHashClase(new Hashtable<Double,BitSet>());
		datos.iniDatosBs();
		int count = 0, count2=0;
		for(int i=0; i<datos.getNumDatos(); i++){
			int j;
			for( j=0; j<datos.getSizeCountAtributos()-1; j++){
				if(hash.containsKey(datos.getDato(i, j)))
					datos.setDatoBS(i, j, hash.get(datos.getDato(i, j)));
				else{
					BitSet tmp = new BitSet(3);
					tmp.set(count++);
					hash.put(datos.getDato(i, j), tmp);
					datos.setDatoBS(i, j, hash.get(datos.getDato(i, j)));
				}	
			}
			j = datos.getSizeCountAtributos()-1;
			if(datos.getHashClase().containsKey(datos.getDato(i, j)))
				datos.setDatoBS(i, j, datos.getHashClase().get(datos.getDato(i, j)));
			else{
				BitSet tmp = new BitSet(1);
				tmp.set(count2++);
				datos.hashClasePut(datos.getDato(i, j), tmp);
				datos.setDatoBS(i, j,datos.hashClaseGet(datos.getDato(i, j)));
			}

		}
		
	}

}
