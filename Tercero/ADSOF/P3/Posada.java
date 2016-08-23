package p3;
import p3.Camino;
import java.util.*;
/**
 * @author e303702
 *
 */
public class Posada {
	
		private String nombre;
		private int enerRec;
		private ArrayList <Camino>salidas; 
		public static final int ENER_REC = 2;
		
		public Posada (String nombre) {
			this(nombre,ENER_REC );
		}
		
		public Posada (String nombre, int ener) {
			this.nombre = nombre;
			this.enerRec = ener;
			this.salidas = new ArrayList<Camino>();
		}
		
		public String getNombre() {
			return this.nombre;
		}

		public int getEnerRec() {
			return this.enerRec;
		}

		public Camino getCamino(int c) {
			return this.salidas.get(c);
		}	
		
		public int getNumCaminos() {
			return this.salidas.size();
		}
		
		public Camino getCamino (Posada p) {
			for (Camino s: this.salidas) {
				if(s.getDestino() == p) 
					return s;
			}
			
			return null;
		}
		
		public boolean addCamino (Camino c) {
			if(this.salidas.contains(c))
				return false;
			
			if (this.salidas.add(c)==true) 
				return true;
			return false;
		}

		public String toString() {
			return this.nombre+"("+this.enerRec+") ["+this.salidas.toString()+"]";
			
		}		
}
