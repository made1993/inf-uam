package clasesGeneticoNeuronal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import clasificadores.ClasificadorRetropropagacion;
import datos.Datos;
import redNeuronal.*;
public class Regla {

	ClasificadorRetropropagacion red;
	int voto;
	
	public Regla(int nEntradas, int nSalidas, int maxCapas, int maxNeur) {
		Random r= new Random();
		int kpax=0;
		if(maxCapas>0)
			kpax=r.nextInt(maxCapas+1);
		ArrayList<Integer> capas= new ArrayList<>();
		capas.add(nEntradas);
		int capaAnt=maxNeur;
		for(int i=0; i<kpax; i++){
			capaAnt=r.nextInt(capaAnt-nSalidas)+nSalidas;
			if(capaAnt<=nSalidas)
				break;
			capas.add(capaAnt);
		}
		capas.add(nSalidas);
		voto=r.nextInt(4)+1;
		red=  new ClasificadorRetropropagacion(capas, 0.01, 50);
	}
	public void incrVoto(){
		Random r= new Random();
		voto+= r.nextInt(4)+1;
	}

	public ClasificadorRetropropagacion getRed() {
		return red;
	}

	public void setRed(ClasificadorRetropropagacion red) {
		this.red = red;
	}
	public int getVoto(){
		return voto;
	}
	
}
