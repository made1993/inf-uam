package pruebasJUnit;

import static org.junit.Assert.*;
import mensaje.*;
import buzon.*;

import org.junit.Test;

import usuario.Estudiante;
import usuario.Usuario;

public class testBuzon {

	@Test
	public void testAddMensajeBuzon() {
		Buzon b = new Buzon();
		MensajeUsuario m= new MensajeUsuario(null, "testAddMensajeBuzon", "testAddMensajeBuzon", null);
		b.addMensajeBuzon(m);
		assertTrue(b.getMensajes().contains(m));
	}


	@Test
	public void testRemoveMensaje() {
		Buzon b = new Buzon();
		Usuario u= new Estudiante("testRemoveMensaje", "testRemoveMensaje",
				"testRemoveMensaje", "testRemoveMensaje");
		MensajeUsuario m= new MensajeUsuario(u, "testRemoveMensaje", "testRemoveMensaje", u);
		b.addMensajeBuzon(m);
		b.removeMensaje(m);
		System.out.println(b.getMensajes());
		assertTrue(b.getMensajes().size()==0);
	}

}
