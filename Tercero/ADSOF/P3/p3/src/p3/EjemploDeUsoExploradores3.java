package p3;

public class EjemploDeUsoExploradores3 {

	//Ejemplo energia suficiente pero no existen caminos para recorrer
	public static void main(String[] args) {
		Posada solana = new Posada("Solana", 1);
		Posada romeral = new Posada("Romeral", 5);
		Posada tomelloso = new Posada("Tomelloso"); // por defecto energia recuperada 2
		Explorador sancho = new Explorador("Sancho", 50, solana);
		System.out.println(sancho);
		sancho.recorre(romeral, tomelloso); // No recorre ninguno porque no hay caminos
		System.out.println(sancho); // En solana
		tomelloso.addCamino(new Camino(tomelloso, romeral, 11));
		sancho.recorre(tomelloso.getCamino(romeral));
		System.out.println(sancho); // En solana con la energia inicial
	}
}
