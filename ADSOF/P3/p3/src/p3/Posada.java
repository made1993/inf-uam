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
		private Luz luz;
		/**
		 * Constructor de la clase Posada. 
		 * @param nombre Nombre de la posada.
		 */
		public Posada (String nombre) {
			this(nombre,ENER_REC );
		}
		/**
		 * Constructor de la clase Posada. 
		 * @param nombre Nombre de la posada.
		 * @param ener Energia que se recupera al entrar en una posada.
		 */
		public Posada (String nombre, int ener) {
			this.nombre = nombre;
			this.enerRec = ener;
			this.salidas = new ArrayList<Camino>();
			this.luz = Luz.BLANCA;
		}

		/**
		 * Constructor de la clase Posada. 
		 * @param nombre Nombre de la posada.
		 * @param ener Energia que se recupera al entrar en una posada.
		 * @param luz El tipo de luz que hay en la posada.
		 */
		public Posada (String nombre, int ener, Luz luz) {
			this(nombre,ener);
			this.luz = luz;
		}
		/**
		 * Devuelve el nombre.
		 * @return Devuelve un String con el nombre.
		 */
		public String getNombre() {
			return this.nombre;
		}
		/**
		 * Devuelve la energia de la posada.
		 * @return Entero con la energia ecuperada.
		 */
		public int getEnerRec() {
			return this.enerRec;
		}
		/**
		 * Dado un entero devuelve un camino de la lista de caminos que 
		 * se corresponde con el elemento de la lista. 
		 * @param c Entero con el camino que se quiere acceder.
		 * @return El camino indicado.
		 */
		public Camino getCamino(int c) {
			return this.salidas.get(c);
		}	
		/**
		 * Devuelve el numero de caminos.
		 * @return Un entero con el numero de caminos.
		 */
		public int getNumCaminos() {
			return this.salidas.size();
		}
		/**
		 * Dada una posada devuelve el camino que une a la posada actual
		 * y la posada que nos pasan.
		 * @param p Posada que nos pasan.
		 * @return Camino que une ambas posadas.
		 */
		public Camino getCamino (Posada p) {
			for (Camino s: this.salidas) {
				if(s.getDestino() == p) 
					return s;
			}
			
			return null;
		}
		/**
		 * Añade una camino a la lista de salidas.
		 * @param c Nuevo camino.
		 * @return Boleano que indica si se ha podido añadir el camino.
		 */
		public boolean addCamino (Camino c) {
			if(this.salidas.contains(c))
				return false;
			
			if (this.salidas.add(c)==true) 
				return true;
			return false;
		}
		/**
		 * Dada una luz cambia la luz de la posada.
		 * @param luz
		 */
		void cambiarLuz (Luz luz) {
			this.luz = luz;
		}
		/**
		 * Transforma el objeto Posada en un String
		 * @return String con el objeto 
		 */
		public String toString() {
			return this.nombre+"("+this.enerRec+") ["+this.salidas.toString()+"]";
			
		}		
}
