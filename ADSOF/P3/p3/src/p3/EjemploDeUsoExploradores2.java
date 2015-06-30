package p3;

public class EjemploDeUsoExploradores2 {
	
	//Ejemplo que puede recorrer los dos caminos por energia suficiente
	public static void main(String[] args) {
		
		Posada solana = new Posada("Solana", 1);
		Posada romeral = new Posada("Romeral", 5);
		Posada tomelloso = new Posada("Tomelloso"); // por defecto energia recuperada 2
		Explorador sancho = new Explorador("Sancho", 80, solana);
		solana.addCamino(new Camino(solana, romeral, 68));
		solana.addCamino(new Camino(solana, tomelloso, 33));
		System.out.println(sancho);
		sancho.recorre(romeral, tomelloso); // ira directo a romeral
		System.out.println(sancho); // energia 17 = 80 - 68 + 5
		romeral.addCamino(new Camino(romeral, tomelloso, 11));
		sancho.recorre(romeral.getCamino(tomelloso));
		System.out.println(sancho); // en tomelloso con energia 8 = 14 - 11 + 5
	}
}
