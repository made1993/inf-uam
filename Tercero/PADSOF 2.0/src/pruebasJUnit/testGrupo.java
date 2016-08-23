package pruebasJUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import grupo.Grupo;
import grupo.GrupoColaborativo;
import grupo.GrupoEstudio;
import mensaje.MensajeGrupo;

import org.junit.Test;

import usuario.Estudiante;
import usuario.Profesor;
import usuario.Usuario;

public class testGrupo {

	@Test
	public void testGetTodosSubGrupos() {
		Grupo g1 = new Grupo(0, "g1", false, null);
		Grupo sg1 = new Grupo(0, "sg1", false, null);
		Grupo sg2 = new Grupo(0, "sg2", false, null);
		Grupo ssg1 = new Grupo(0, "ssg1", false, null);
		Grupo ssg2 = new Grupo(0, "ssg2", false, null);
		assertTrue(g1.addSubGrupo(sg1));
		assertTrue(g1.addSubGrupo(sg2));
		assertTrue(sg1.addSubGrupo(ssg1));
		assertTrue(sg2.addSubGrupo(ssg2));
		ArrayList<Grupo> g = g1.getTodosSubGrupos();
		assertEquals(sg1, g.get(0));
		assertEquals(ssg1, g.get(1));
		assertEquals(sg2, g.get(2));
		assertEquals(ssg2, g.get(3));
	}

	@Test
	public void testAddSubGrupo() {
		Grupo g1 = new Grupo(0, "g1", false, null);
		Grupo sg1 = new Grupo(0, "sg1", false, null);
		Grupo sg2 = new Grupo(0, "sg2", false, null);
		Grupo ssg1 = new Grupo(0, "ssg1", false, null);
		Grupo ssg2 = new Grupo(0, "ssg2", false, null);
		assertTrue(g1.addSubGrupo(sg1));
		assertTrue(g1.addSubGrupo(sg2));
		assertTrue(sg1.addSubGrupo(ssg1));
		assertTrue(sg2.addSubGrupo(ssg2));
	}

	@Test
	public void testAddUsuario() {
		Grupo g1 = new Grupo(0, "g1", false, null);
		Usuario u = new Estudiante("estu", "estu", "estu.estu@estu.com", "estu");
		assertTrue(g1.addUsuario(u));
		assertTrue(g1.addUsuario(u));
		assertEquals(1,g1.getListaUsuarios().size());
	}

	@Test
	public void testRemoveUsuario() {
		Grupo g1 = new Grupo(0, "g1", false, null);
		Usuario u = new Estudiante("estu", "estu", "estu.estu@estu.com", "estu");
		assertTrue(g1.addUsuario(u));
		assertEquals(1,g1.getListaUsuarios().size());
		assertTrue(g1.removeUsuario(u));
		assertEquals(0,g1.getListaUsuarios().size());
	}

	@Test
	public void testAddMensaje() {
		Grupo g1 = new Grupo(0, "g1", false, null);
		Usuario u = new Estudiante("estu", "estu", "estu.estu@estu.com", "estu");
		assertTrue(g1.addUsuario(u));
		MensajeGrupo men = new MensajeGrupo("cuerpo", u);
		assertTrue(g1.addMensaje(men));
		assertEquals(1,g1.getListaMensajes().size());
		assertTrue(g1.addMensaje(men));
		assertEquals(2,g1.getListaMensajes().size());
	}

	@Test
	public void testAddMensajeModerado() {
		Usuario u = new Estudiante("estu", "estu", "estu.estu@estu.com", "estu");
		Grupo g1 = new Grupo(0, "g1", false, u);
		assertTrue(g1.addUsuario(u));
		MensajeGrupo men = new MensajeGrupo("cuerpo", u);
		assertTrue(g1.addMensajeModerado(men));
		assertEquals(1,g1.getListaMensajes().size());
		assertTrue(g1.addMensajeModerado(men));
		assertEquals(2,g1.getListaMensajes().size());
	}

	@Test
	public void testIsGrupoEstudio() {
		Grupo g = new Grupo(0, "g", false, null);
		Profesor profesor = new Profesor("profe", "profe", "profe.profe@profe.ocm", "profe");
		Grupo ge = new GrupoEstudio(0, "ge", false, null, profesor);
		Grupo gc = new GrupoColaborativo(0, "gc");
		assertFalse(g.isGrupoEstudio());
		assertTrue(ge.isGrupoEstudio());
		assertFalse(gc.isGrupoEstudio());
	}

	@Test
	public void testIsGrupoColaborativo(){
	Grupo g = new Grupo(0, "g", false, null);
	Profesor profesor = new Profesor("profe", "profe", "profe.profe@profe.ocm", "profe");
	Grupo ge = new GrupoEstudio(0, "ge", false, null, profesor);
	Grupo gc = new GrupoColaborativo(0, "gc");
	assertFalse(g.isGrupoColaborativo());
	assertFalse(ge.isGrupoColaborativo());
	assertTrue(gc.isGrupoColaborativo());
	}

	@Test
	public void testContieneUsuario() {
		Grupo g = new Grupo(0, "g", false, null);
		Usuario u = new Estudiante("estu", "estu", "estu.estu@estu.com", "estu");
		Usuario u1 = new Estudiante("estu1", "estu", "estu1.estu@estu.com", "estu");
		assertFalse(g.contieneUsuario(u));
		assertFalse(g.contieneUsuario(u1));
		assertTrue(g.addUsuario(u));
		assertTrue(g.addUsuario(u1));
		assertTrue(g.contieneUsuario(u));
		assertTrue(g.contieneUsuario(u1));
	}

	@Test
	public void testBuscarGrupo() {
		Grupo g1 = new Grupo(0, "g1", false, null);
		Grupo sg1 = new Grupo(0, "sg1", false, null);
		assertTrue(g1.addSubGrupo(sg1));
		assertEquals(sg1, g1.buscarGrupo(sg1.getNombre()));
	}

	@Test
	public void testBuscarGrupoLista() {
		Grupo g1 = new Grupo(0, "g1", false, null);
		Grupo sg1 = new Grupo(0, "sg1", false, null);
		assertTrue(g1.addSubGrupo(sg1));
		assertEquals(sg1, g1.buscarGrupoLista(sg1.getNombre()).get(0));
	}

	@Test
	public void testGetTipoGrupo() {
		Usuario moderador = new Estudiante("moder", "moder", "moder.moder@moder.com", "moder");
		Grupo gpr = new Grupo(0, "gpr", true, null);
		Grupo gpu = new Grupo(0, "gpu", false, null);
		Grupo gprmod = new Grupo(0, "gpr", true, moderador);
		Grupo gpumod = new Grupo(0, "gpu", false, moderador);
		Profesor profesor = new Profesor("profe", "profe", "profe.profe@profe.ocm", "profe");
		Grupo gepu = new GrupoEstudio(0, "ge", false, null, profesor);
		Grupo gepr = new GrupoEstudio(0, "ge", true, null, profesor);
		Grupo gepumod = new GrupoEstudio(0, "ge", false, profesor, profesor);
		Grupo geprmod = new GrupoEstudio(0, "ge", true, profesor, profesor);
		Grupo gc = new GrupoColaborativo(0, "gc");

		assertEquals("Privado",gpr.getTipoGrupo());
		assertEquals("Publico",gpu.getTipoGrupo());
		assertEquals("Privado Moderado",gprmod.getTipoGrupo());
		assertEquals("Publico Moderado",gpumod.getTipoGrupo());
		assertEquals("Publico GE",gepu.getTipoGrupo());
		assertEquals("Privado GE",gepr.getTipoGrupo());
		assertEquals("Publico GE Moderado",gepumod.getTipoGrupo());
		assertEquals("Privado GE Moderado",geprmod.getTipoGrupo());
		assertEquals("Privado GC",gc.getTipoGrupo());
	}

	@Test
	public void testNotificarUsuarios() {
		Grupo gpu = new Grupo(0, "gpu", false, null);
		Usuario u = new Estudiante("estu", "estu", "estu.estu@estu.com", "estu");
		Usuario u1 = new Estudiante("estu1", "estu1", "estu.est2u@estu.com", "estu");
		gpu.addUsuario(u);
		gpu.addUsuario(u1);
		gpu.notificarUsuarios("notificacion");
		assertEquals(1, u.getBuzon().getMensajes().size());
		assertEquals(1, u1.getBuzon().getMensajes().size());
	}

	@Test
	public void testBuscarMensaje() {
		Grupo gpu = new Grupo(0, "gpu", false, null);
		Usuario u = new Estudiante("estu", "estu", "estu.estu@estu.com", "estu");
		gpu.addUsuario(u);
		MensajeGrupo men  = new MensajeGrupo("cuerpo", u);
		MensajeGrupo men1  = new MensajeGrupo("cuerpo", u);
		gpu.addMensaje(men);
		assertEquals(men,gpu.buscarMensaje(men.getIdMensaje()));
		assertNull(gpu.buscarMensaje(men1.getIdMensaje()));
	}

}
