package pruebasJUnit;

import static org.junit.Assert.*;
import mensaje.Pregunta;
import mensaje.Respuesta;

import org.junit.Test;

import usuario.Estudiante;

public class testPregunta {

	@Test
	public void testGetRespuestaDe() {
		Pregunta p = new Pregunta("testGetRespuestaDe", null);
		Estudiante u= new Estudiante("testGetRespuestaDe", "testGetRespuestaDe",
				"testGetRespuestaDe", "testGetRespuestaDe");
		Respuesta r = new Respuesta("testGetRespuestaDe", u);
		p.getListaRespuestas().add(r);
		assertEquals(p.getRespuestaDe(u), r);
	}

	@Test
	public void testAddRespuesta() {
		Pregunta p= new Pregunta("testAddRespuesta", null);
		Respuesta r= new Respuesta("testAddRespuesta", null);
		p.getListaRespuestas().add(r);
		assertTrue(p.getListaRespuestas().size()==1);
	}


	@Test
	public void testListarRespuestas() {
		Pregunta p= new Pregunta("testAddRespuesta", null);
		Respuesta r= new Respuesta("testAddRespuesta", null);
		p.getListaRespuestas().add(r);
		assertTrue(p.getListaRespuestas().contains(r));
	}

}
