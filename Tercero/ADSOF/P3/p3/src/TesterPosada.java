
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import p3.Camino;
import p3.Luz;
import p3.Posada;


public class TesterPosada {

	@Test
	public  void test() {
		Posada p1= new Posada("p1");
		assertEquals("p1",p1.getNombre());
		assertEquals(2,p1.getEnerRec());
		assertEquals(0, p1.getNumCaminos());
		
		Posada p2 = new Posada("p2", 3);
		assertEquals("p2",p2.getNombre());
		assertEquals(3,p2.getEnerRec());
		assertEquals(0, p2.getNumCaminos());

		Posada p3 = new Posada("p3", 4,Luz.DIABLOICCA);
		assertEquals("p3",p3.getNombre());
		assertEquals(4,p3.getEnerRec());
		assertEquals(0, p3.getNumCaminos());
		
		Camino c1= new Camino(p1, p1, 2);
		p1.addCamino(c1);
		assertEquals(c1, p1.getCamino(p1));
		assertEquals((Object)null, p1.getCamino(p2));
		assertEquals(c1, p1.getCamino(1));
		assertEquals((Object)null, p1.getCamino(p2));
		assertEquals(1, p1.getNumCaminos());
		assertEquals("p1(2) [(p1--2-->p1)]", p1.toString());
		
		System.out.println("Exito al realizar las pruebas de Posada");
	}
	
}
