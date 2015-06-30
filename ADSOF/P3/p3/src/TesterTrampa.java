import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import p3.Camino;
import p3.Posada;
import p3.Trampa;


public class TesterTrampa {

	@Test
	public  void test() {
		Trampa t1= new Trampa(null, null, 2, 1, 1);
		assertNotEquals((Object) null, t1);
		assertEquals(2, t1.costeEspecial());
		assertEquals(true, t1.esTrampa());
		System.out.println("Tester de trampa exitoso");
	}
}
