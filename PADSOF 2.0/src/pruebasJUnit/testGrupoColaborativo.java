/**
 * 
 */
package pruebasJUnit;

import static org.junit.Assert.*;
import grupo.Grupo;
import grupo.GrupoColaborativo;
import grupo.GrupoEstudio;
import mensaje.Mensaje;
import mensaje.MensajeColaborativo;

import org.junit.Test;

import usuario.Profesor;
import usuario.Usuario;

/**
 * @author eps
 *
 */
public class testGrupoColaborativo {

	/**
	 * Test method for {@link grupo.GrupoColaborativo#isGrupoColaborativo()}.
	 */
	@Test
	public void testIsGrupoColaborativo() {
		boolean privado = false;
		Profesor profesor = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		Usuario moderador = null;
		GrupoEstudio ge = new GrupoEstudio(0, "ge1", privado, moderador, profesor);
		GrupoColaborativo gc = new GrupoColaborativo(0, "gc1");
		Grupo g = new Grupo(0, "g1", privado, moderador);
		
		assertFalse(ge.isGrupoColaborativo());
		assertFalse(g.isGrupoColaborativo());
		assertTrue(gc.isGrupoColaborativo());
	}

	/**
	 * Test method for {@link grupo.GrupoColaborativo#terminarColaboracion(mensaje.MensajeColaborativo)}.
	 */
	@Test
	public void testTerminarColaboracion() {
		Profesor profesor = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		GrupoColaborativo gc = new GrupoColaborativo(0, "gc1");
		MensajeColaborativo m = new MensajeColaborativo("cuerpo1", profesor);
		MensajeColaborativo r1 = new MensajeColaborativo("respuesta1", profesor);
		MensajeColaborativo r2 = new MensajeColaborativo("respuesta2", profesor);
		MensajeColaborativo r3 = new MensajeColaborativo("respuesta3", profesor);
		assertTrue(gc.addMensaje(m));
		assertTrue(m.addRespuesta(r1));
		assertTrue(gc.anadirRespuesta(m, r2));
		gc.terminarColaboracion(m);
		assertFalse(m.addRespuesta(r3));
	}

	/**
	 * Test method for {@link grupo.GrupoColaborativo#anadirRespuesta(mensaje.MensajeColaborativo, mensaje.MensajeColaborativo)}.
	 */
	@Test
	public void testAnadirRespuesta() {
		Profesor profesor = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		GrupoColaborativo gc = new GrupoColaborativo(0, "gc1");
		MensajeColaborativo m = new MensajeColaborativo("cuerpo1", profesor);
		MensajeColaborativo r1 = new MensajeColaborativo("respuesta1", profesor);
		MensajeColaborativo r2 = new MensajeColaborativo("respuesta2", profesor);
		MensajeColaborativo r3 = new MensajeColaborativo("respuesta3", profesor);
		assertTrue(gc.addMensaje(m));
		assertTrue(m.addRespuesta(r1));
		assertTrue(gc.anadirRespuesta(m, r2));
		gc.terminarColaboracion(m);
		assertFalse(m.addRespuesta(r3));
	}

	/**
	 * Test method for {@link grupo.GrupoColaborativo#buscarMensaje(int)}.
	 */
	@Test
	public void testBuscarMensajeInt() {
		Profesor profesor = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		GrupoColaborativo gc = new GrupoColaborativo(0, "gc1");
		MensajeColaborativo m = new MensajeColaborativo("cuerpo1", profesor);
		MensajeColaborativo r1 = new MensajeColaborativo("respuesta1", profesor);
		MensajeColaborativo r2 = new MensajeColaborativo("respuesta2", profesor);
		MensajeColaborativo r3 = new MensajeColaborativo("respuesta3", profesor);
		assertTrue(gc.addMensaje(m));
		assertTrue(m.addRespuesta(r1));
		assertTrue(r1.addRespuesta(r2));
		assertTrue(r2.addRespuesta(r3));
		MensajeColaborativo mc0 = gc.buscarMensaje(0);
		MensajeColaborativo mc1 = gc.buscarMensaje(m.getIdMensaje());
		MensajeColaborativo mc2 = gc.buscarMensaje(r1.getIdMensaje());
		MensajeColaborativo mc3 = gc.buscarMensaje(r2.getIdMensaje());
		MensajeColaborativo mc4 = gc.buscarMensaje(r3.getIdMensaje());
		assertNull(mc0);
		assertEquals(m,mc1);
		assertEquals(r1,mc2);
		assertEquals(r2,mc3);
		assertEquals(r3,mc4);
	}

}
