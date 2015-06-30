/**
 * 
 */
package pruebasJUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import usuario.Estudiante;
import usuario.Profesor;
import usuario.Visitante;

/**
 * @author eps
 *
 */
public class testVisitante {

	/**
	 * Test method for {@link usuario.Visitante#isProfesor()}.
	 */
	@Test
	public void testIsProfesor() {
		Profesor p = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		Estudiante e = new Estudiante("Estudiante", "estu", "estu.e@estuadiante.gob", "estu");
		Visitante v = new Visitante("visi", "asd", "asd", "asd");
		assertFalse(e.isProfesor());
		assertFalse(v.isProfesor());
		assertTrue(p.isProfesor());
	}

	/**
	 * Test method for {@link usuario.Visitante#isVisitante()}.
	 */
	@Test
	public void testIsVisitante() {
		Profesor p = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		Estudiante e = new Estudiante("Estudiante", "estu", "estu.e@estuadiante.gob", "estu");
		Visitante v = new Visitante("visi", "asd", "asd", "asd");
		assertFalse(e.isVisitante());
		assertTrue(v.isVisitante());
		assertFalse(p.isVisitante());
	}

}
