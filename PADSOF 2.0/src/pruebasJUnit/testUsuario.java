/**
 * 
 */
package pruebasJUnit;

import static org.junit.Assert.*;

import java.io.IOException;

import grupo.Grupo;
import mailUam.MailUam;
import mensaje.MensajeUsuario;

import org.junit.Test;

import usuario.Estudiante;
import usuario.Profesor;
import usuario.Usuario;
import usuario.Visitante;

/**
 * @author eps
 *
 */
public class testUsuario {

	/**
	 * Test method for {@link usuario.Usuario#listarMensajes()}.
	 */
	@Test
	public void testListarMensajes() {
		Usuario p = (Usuario) new Profesor("profe","profe","profe.profe@profe.com","profe");
		assertEquals(0, p.listarMensajes().size());
		p.addMensajeBuzon(new MensajeUsuario(p, "cuerpo", "mensaje1", p));
		p.addMensajeBuzon(new MensajeUsuario(p, "cuerpo", "mensaje2", p));
		assertEquals(2, p.listarMensajes().size());
	}


	/**
	 * Test method for {@link usuario.Usuario#addGrupo(grupo.Grupo)}.
	 */
	@Test
	public void testAddGrupo() {
		Usuario p = (Usuario) new Profesor("profe","profe","profe.profe@profe.com","profe");
		assertEquals(0, p.getListaGrupos().size());
		Grupo grupo = new Grupo(0, "g1", false, null);
		assertTrue(p.addGrupo(grupo));
		assertTrue(p.addGrupo(grupo));
		assertEquals(1, p.getListaGrupos().size());
	}

	/**
	 * Test method for {@link usuario.Usuario#removeGrupo(grupo.Grupo)}.
	 */
	@Test
	public void testRemoveGrupo() {
		Usuario p = (Usuario) new Profesor("profe","profe","profe.profe@profe.com","profe");
		assertEquals(0, p.getListaGrupos().size());
		Grupo grupo = new Grupo(0, "g1", false, null);
		p.addGrupo(grupo);
		p.addGrupo(grupo);
		assertEquals(1, p.getListaGrupos().size());
		assertTrue(p.removeGrupo(grupo));
		assertFalse(p.removeGrupo(null));
		assertFalse(p.removeGrupo(grupo));
		assertEquals(0, p.getListaGrupos().size());		
	}

	/**
	 * Test method for {@link usuario.Usuario#addContacto(usuario.Usuario)}.
	 */
	@Test
	public void testAddContacto() {
		Usuario p = (Usuario) new Profesor("profe","profe","profe.profe@profe.com","profe");
		Usuario e = (Estudiante) new Estudiante("estu", "estu", "estu.estu@estu.es", "estu");
		assertEquals(0, p.getContactos().size());
		assertTrue(p.addContacto(e));
		assertEquals(1, p.getContactos().size());
	}

	/**
	 * Test method for {@link usuario.Usuario#removeContacto(usuario.Usuario)}.
	 */
	@Test
	public void testRemoveContacto() {
		Usuario p = (Usuario) new Profesor("profe","profe","profe.profe@profe.com","profe");
		Usuario e = (Estudiante) new Estudiante("estu", "estu", "estu.estu@estu.es", "estu");
		assertEquals(0, p.getContactos().size());
		assertTrue(p.addContacto(e));
		assertTrue(p.removeContacto(e));
		assertFalse(p.removeContacto(null));
		assertEquals(0, p.getContactos().size());
	}

	/**
	 * Test method for {@link usuario.Usuario#isProfesor()}.
	 */
	@Test
	public void testIsProfesor() {
		Usuario p = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		Usuario e = new Estudiante("Estudiante", "estu", "estu.e@estuadiante.gob", "estu");
		Usuario v = new Visitante("visi", "asd", "asd", "asd");
		assertFalse(e.isProfesor());
		assertFalse(v.isProfesor());
		assertTrue(p.isProfesor());
	}

	/**
	 * Test method for {@link usuario.Usuario#isVisitante()}.
	 */
	@Test
	public void testIsVisitante() {
		Usuario p = new Profesor("Profe", "profe", "p.p@mail.gob", "profe");
		Usuario e = new Estudiante("Estudiante", "estu", "estu.e@estuadiante.gob", "estu");
		Usuario v = new Visitante("visi", "asd", "asd", "asd");
		assertFalse(e.isVisitante());
		assertTrue(v.isVisitante());
		assertFalse(p.isVisitante());
	}

	/**
	 * Test method for {@link usuario.Usuario#addMensajeBuzon(mensaje.MensajeUsuario)}.
	 */
	@Test
	public void testAddMensajeBuzon() {
		Usuario e = new Estudiante("Estudiante", "estu", "estu.e@estuadiante.gob", "estu");
		MensajeUsuario mensaje = new MensajeUsuario(e, "cuerpo", "sujeto", e);
		assertTrue(e.addMensajeBuzon(mensaje));
		assertTrue(e.addMensajeBuzon(mensaje));
	}

	/**
	 * Test method for {@link usuario.Usuario#verMensaje(mensaje.Mensaje)}.
	 */
	@Test
	public void testVerMensaje() {
		Usuario e = new Estudiante("Estudiante", "estu", "estu.e@estuadiante.gob", "estu");
		MensajeUsuario mensaje = new MensajeUsuario(e, "cuerpo", "sujeto", e);
		assertTrue(e.addMensajeBuzon(mensaje));
		assertNotNull(e.verMensaje(mensaje));
	}

	/**
	 * Test method for {@link usuario.Usuario#removeMensaje(mensaje.MensajeUsuario)}.
	 */
	@Test
	public void testRemoveMensaje() {
		Usuario e = new Estudiante("Estudiante", "estu", "estu.e@estuadiante.gob", "estu");
		MensajeUsuario mensaje = new MensajeUsuario(e, "cuerpo", "sujeto", e);
		assertTrue(e.addMensajeBuzon(mensaje));
		assertEquals(1, e.getBuzon().getMensajes().size());
		assertTrue(e.removeMensaje(mensaje));
		assertEquals(0, e.getBuzon().getMensajes().size());
	}

}
