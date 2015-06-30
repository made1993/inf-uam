package tests;

import java.text.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import reglas.*;

/**
 * Clase Producto para probar el ejercicio 3
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
class Producto {
	private double precio;
	private Date caducidad;

	/**
	 * Constructor de la clase Producto
	 * 
	 * @param p
	 *            precio del producto
	 * @param c
	 *            fecha de caducidad del producto
	 */
	public Producto(double p, Date c) {
		this.precio = p;
		this.caducidad = c;
	}

	/**
	 * Metodo para obtener el precio de un producto
	 * 
	 * @return precio de un producto
	 */
	public double getPrecio() {
		return this.precio;
	}

	/**
	 * Metodo para asignar un precio a un producto
	 * 
	 * @param p
	 *            precio a asignar
	 */
	public void setPrecio(double p) {
		this.precio = p;
	}

	/**
	 * Metodo para obtener la fecha de caducidad de un producto
	 * 
	 * @return fecha de caducidad de un producto
	 */
	public Date getCaducidad() {
		return this.caducidad;
	}

	/**
	 * Metodo para obtener la diferencia entr dos fechas
	 * 
	 * @param date1
	 *            primer operando
	 * @param date2
	 *            segundo operando
	 * @param timeUnit
	 *            unidad de tiempo
	 * @return diferencia de las fechas
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	/**
	 * toString de la clase Producto
	 */
	@Override
	public String toString() {
		return this.precio + ", caducidad: " + this.caducidad;
	}
}

/**
 * Clase de prueba del ejercicio 3
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class testEjercicio3 {
	public static void main(String... args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		RuleSet<Producto> rs = new RuleSet<Producto>();
		// Un conjunto de reglas aplicables sobre Productos
		rs.add(Rule
				.<Producto> rule("r1",
						"Rebaja un 10% los productos con fecha de caducidad cercana o pasada")
				.when(pro -> Producto.getDateDiff(Calendar.getInstance()
						.getTime(), pro.getCaducidad(), TimeUnit.DAYS) < 2)
				.exec(pro -> pro.setPrecio(pro.getPrecio() - pro.getPrecio()
						* 0.1)))
				.add(Rule
						.<Producto> rule("r2",
								"Rebaja un 5% los productos que valen más de 10 euros")
						.when(pro -> pro.getPrecio() > 10)
						.exec(pro -> pro.setPrecio(pro.getPrecio()
								- pro.getPrecio() * 0.05)));
		List<Producto> str = Arrays.asList(
				new Producto(10, sdf.parse("15/03/2015")), // parseamos a un
															// Date
				new Producto(20, sdf.parse("20/03/2016")));
		rs.setExecContext(str);
		rs.process();
		System.out.println(str);
	}
}