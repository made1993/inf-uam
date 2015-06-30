package pruebasJUnit;

import static org.junit.Assert.*;
import mensaje.MensajeColaborativo;

import org.junit.Test;

public class testMensajeColaborativo {

	@Test
	public void testSetCerrado() {
		MensajeColaborativo mc = new MensajeColaborativo("testSetCerrado", null);
		MensajeColaborativo mc2 = new MensajeColaborativo("testSetCerrado2", null);
		mc.getRespuestas().add(mc2);
		mc.setCerrado(true);
		assertTrue(mc2.isCerrado());
		
	}

	@Test
	public void testBuscarMensaje() {
		MensajeColaborativo mc = new MensajeColaborativo("testSetCerrado", null);
		MensajeColaborativo mc2 = new MensajeColaborativo("testSetCerrado2", null);
		mc.getRespuestas().add(mc2);
		assertEquals(mc.buscarMensaje(mc2.getIdMensaje()), mc2);
	}

}
