package testers;

import java.text.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import ej3.Producto;
import ej3.Rule;
import ej3.RuleSet;

public class test3 {
	 public static void main(String...args) throws ParseException{
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 RuleSet<Producto> rs = new RuleSet<Producto>(); // Un conjunto de reglas aplicables sobre Productos
		 rs.add(
		 Rule.<Producto>rule("r1", "Rebaja un 10% los productos con fecha de caducidad cercana o pasada").when(pro -> Producto.getDateDiff(Calendar.getInstance().getTime(), pro.getCaducidad(), TimeUnit.DAYS) < 2 ).exec(pro -> pro.setPrecio(pro.getPrecio()-pro.getPrecio()*0.1))
		 );
		 rs.add(
		 Rule.<Producto>rule("r2", "Rebaja un 5% los productos que valen más de 10 euros").when(pro -> pro.getPrecio() > 10).exec(pro -> pro.setPrecio(pro.getPrecio()-pro.getPrecio()*0.05)));
		 List<Producto> str = Arrays.asList( new Producto(10, sdf.parse("15/03/2015")),new Producto(20, sdf.parse("20/03/2016"))); // parseamos a un Date
		
		 rs.setExecContext(str); // indicamos que el conjunto de reglas rs se ejecutará sobre str
		 rs.process(); // ejecutamos el conjunto de reglas*/
		 System.out.println(str); // imprimimos str
		 }
}
