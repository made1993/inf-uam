package p3;

public class EjemploDeUsoExploradores1 {
	
	//Ejemplo que no tiene energia para ir a ninguna posada, 
	// y se queda siempre en la actual (solana)
	public static void main(String[] args) {
		
		Posada solana = new Posada("Solana", 1);
		Posada romeral = new Posada("Romeral", 5);
		Posada tomelloso = new Posada("Tomelloso"); // por defecto energia recuperada 2
		Explorador sancho = new Explorador("Sancho", 30, solana);
		solana.addCamino(new Camino(solana, romeral, 68));
		solana.addCamino(new Camino(solana, tomelloso, 33));
		System.out.println(sancho);
		sancho.recorre(romeral, tomelloso); // No recorre camino
		System.out.println(sancho); // Se encuentra en solana
		tomelloso.addCamino(new Camino(tomelloso, romeral, 11));
		sancho.recorre(tomelloso.getCamino(romeral)); //No recorre camino
		System.out.println(sancho); // Se encuentra en solana
	}
}
