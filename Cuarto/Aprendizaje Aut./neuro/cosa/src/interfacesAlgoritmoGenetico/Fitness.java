package interfacesAlgoritmoGenetico;

import clasesGeneticoNeuronal2.Poblacion;
import datos.*;

public interface Fitness {
	public void fit(Poblacion p, Datos datos);
	public void generarBitSets(Datos datos);

}
