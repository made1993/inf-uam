/**
 * 
 */
package pruebasJUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import usuario.*;

/**
 * @author eps
 *
 */
public class testEstudiante {

	/**
	 * Test method for {@link usuario.Estudiante#isProfesor()}.
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
}
