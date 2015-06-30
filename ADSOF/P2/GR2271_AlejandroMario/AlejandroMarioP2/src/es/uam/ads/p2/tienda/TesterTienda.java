package es.uam.ads.p2.tienda;
public class TesterTienda {
	
public static void main(String[] args) {
	Libro l = new Libro(1, "La historia interminable", "Ende, Michael", "Ed. Alfaguara");
	Disco d = new Disco(12, "Evanescence", "Fallen", 2003);
	Pelicula p = new Pelicula(34, "Cadena Perpetua", "Drama", "Frank Darabont");
	System.out.println(l);
	System.out.println(d);
	System.out.println(p);
	}
}