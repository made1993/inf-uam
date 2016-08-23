package particionado;

import java.util.ArrayList;

import datos.Datos;

public interface EstrategiaParticionado{
	public String getNombreEstrategia();
	public int getNumeroParticiones();
	public ArrayList<Particion> crearPartciones(Datos datos);
}