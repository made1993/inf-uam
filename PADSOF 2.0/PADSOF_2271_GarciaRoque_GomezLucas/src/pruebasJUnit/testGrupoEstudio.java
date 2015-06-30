/**
 * 
 */
package pruebasJUnit;

import static org.junit.Assert.*;
import grupo.*;

import org.junit.Test;

import usuario.*;

/**
 * @author eps
 *
 */
public class testGrupoEstudio {

	/**
	 * Test method for {@link grupo.GrupoEstudio#isGrupoEstudio()}.
	 */
	@Test
	public void testIsGrupoEstudio() {
		
		boolean privado = false;
		Profesor profesor = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		Usuario moderador = null;
		GrupoEstudio ge = new GrupoEstudio(0, "ge1", privado, moderador, profesor);
		GrupoColaborativo gc = new GrupoColaborativo(0, "gc1");
		Grupo g = new Grupo(0, "g1", privado, moderador);
		
		assertTrue(ge.isGrupoEstudio());
		assertFalse(g.isGrupoEstudio());
		assertFalse(gc.isGrupoEstudio());
		
	}


	/**
	 * Test method for {@link grupo.GrupoEstudio#getListaPreguntas()}.
	 */
	@Test
	public void testGetListaPreguntas() {
		boolean privado = false;
		Profesor profesor = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		Usuario moderador = null;
		GrupoEstudio ge = new GrupoEstudio(0, "ge1", privado, moderador, profesor);
		String pregunta1 = "Pregunta 1";
		String pregunta2 = "Pregunta 2";
		ge.crearPregunta(pregunta1, profesor);
		ge.crearPregunta(pregunta2, profesor);
		assertEquals(2, ge.getListaPreguntas().size());
		assertEquals(pregunta1, ge.getListaPreguntas().get(0).getCuerpo());
		assertEquals(pregunta2, ge.getListaPreguntas().get(1).getCuerpo());
	}


	/**
	 * Test method for {@link grupo.GrupoEstudio#getProfesor()}.
	 */
	@Test
	public void testGetProfesor() {
		boolean privado = false;
		Profesor profesor = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		Usuario moderador = null;
		GrupoEstudio ge = new GrupoEstudio(0, "ge1", privado, moderador, profesor);
		assertEquals(profesor, ge.getProfesor());
	}


	/**
	 * Test method for {@link grupo.GrupoEstudio#crearPregunta(java.lang.String, usuario.Profesor)}.
	 */
	@Test
	public void testCrearPregunta() {
		boolean privado = false;
		Profesor profesor = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		Usuario moderador = null;
		GrupoEstudio ge = new GrupoEstudio(0, "ge1", privado, moderador, profesor);
		String pregunta1 = "Pregunta 1";
		ge.crearPregunta(pregunta1, profesor);
		assertEquals(1, ge.getListaPreguntas().size());
		assertEquals(pregunta1, ge.getListaPreguntas().get(0).getCuerpo());
	}

	/**
	 * Test method for {@link grupo.GrupoEstudio#buscarPregunta(java.lang.String)}.
	 */
	@Test
	public void testBuscarPregunta() {
		boolean privado = false;
		Profesor profesor = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		Usuario moderador = null;
		GrupoEstudio ge = new GrupoEstudio(0, "ge1", privado, moderador, profesor);
		String pregunta1 = "Pregunta 1";
		String pregunta2 = "Pregunta 2";
		ge.crearPregunta(pregunta1, profesor);
		ge.crearPregunta(pregunta2, profesor);
		assertEquals(2, ge.getListaPreguntas().size());
		assertEquals(pregunta1, ge.getListaPreguntas().get(0).getCuerpo());
		assertEquals(pregunta2, ge.getListaPreguntas().get(1).getCuerpo());
		assertEquals(ge.buscarPregunta(pregunta1), ge.getListaPreguntas().get(0));
		assertEquals(ge.buscarPregunta(pregunta2), ge.getListaPreguntas().get(1));
		assertNull(ge.buscarPregunta("pregunta 3"));
	}

}
