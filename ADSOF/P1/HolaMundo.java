/** 
 * @author Alejandro  
 * 
 * Esta aplicación muestra el mensaje "Hola mundo!" por pantalla 
 */ 
public class HolaMundo { 

public static void main (String[] args) {

	if (args.length == 0) {
		System.out.println ("Se espera mas de un argumento");
		return;
	} 

	Integer i=0;
	Integer j =0;

	while (args.length > i) {
		 j += Integer.parseInt(args[i]);
		i++;
	}
 	System.out.println ("Media:"+((j/args.length)*1.0)+"");
}
}
