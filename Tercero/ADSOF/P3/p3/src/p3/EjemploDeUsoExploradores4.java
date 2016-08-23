package p3;

public class EjemploDeUsoExploradores4 {

	//Ejemplo usando funcion cambiarDestino, puediendo recorrer los caminos
	public static void main(String[] args) {
		Posada solana = new Posada("Solana", 1);
		Posada romeral = new Posada("Romeral", 5);
		Posada tomelloso = new Posada("Tomelloso"); 
		Camino corto = new Camino(solana, romeral, 68);
		Explorador sancho = new Explorador("Sancho", 100, solana);
		solana.addCamino(corto);
		corto.cambiarDestino(tomelloso, 33);
		System.out.println(sancho);
		sancho.recorre(tomelloso); 
		System.out.println(sancho); 
		tomelloso.addCamino(new Camino(tomelloso, romeral, 11));
		sancho.recorre(tomelloso.getCamino(romeral));
		System.out.println(sancho);
	}
}
