import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import p3.Camino;
import p3.Posada;


public class TesterCamino {

	@Test
	public  void test() {
		Posada p1= new Posada("p1");
		Posada p2= new Posada("p2");
		Camino c1= new Camino(p1, p2, 1);
		
		assertNotEquals(null, (Object)p1);
		assertNotEquals(null, (Object)p2);
		assertNotEquals(null, (Object)c1);
		
		assertEquals(p2, c1.getDestino());
		assertEquals(p1, c1.getOrigen());
		assertEquals(1, c1.getEnergia().intValue());
		assertEquals(1, c1.costeReal());
		assertEquals("(p1--1-->p2)", c1.toString());
		
		c1.cambiarDestino(p1, 2);
		
		assertEquals(p1, c1.getDestino());
		assertEquals(2, c1.getEnergia().intValue());
		assertEquals(2, c1.costeReal());
		
		assertEquals(false, c1.esTrampa());
		
		c1.cambiarDestino(null, 0);
		assertEquals(null, c1.getDestino());
		assertEquals(1, c1.getEnergia().intValue());
		assertEquals(1, c1.costeReal());
		
		System.out.println("Tester de camino exitoso");
		
	}

}
