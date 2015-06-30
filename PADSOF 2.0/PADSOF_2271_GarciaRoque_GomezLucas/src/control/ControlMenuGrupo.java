package control;

import gui.*;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import usuario.Usuario;
import mailUam.MailUam;
import grupo.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase ControlMenuGrupo
 */
public class ControlMenuGrupo implements ActionListener {

	private MailUam modelo;
	private Ventana v;

	/**
	 * Constructor de ControlMenuGrupo
	 * @param v ventana de la aplicacion
	 * @param modelo modelo de la aplicacion
	 */
	public ControlMenuGrupo(Ventana v, MailUam modelo) {
		this.modelo = modelo;
		this.v = v;
	}

	

	/**
	 * @return the modelo
	 */
	public MailUam getModelo() {
		return modelo;
	}



	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(MailUam modelo) {
		this.modelo = modelo;
	}



	/**
	 * @return the v
	 */
	public Ventana getV() {
		return v;
	}



	/**
	 * @param v the v to set
	 */
	public void setV(Ventana v) {
		this.v = v;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {

		Object source = arg0.getSource();

		GUIMenuGrupo menuGrupos = getV().getMenuGrupos();

		if (source.equals(menuGrupos.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(menuGrupos.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(menuGrupos.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(menuGrupos.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(menuGrupos.getCrearGrupo())) {
			getV().getCrearGrupo().setValores();
			if (getModelo().getLogged().isVisitante()) {
				JOptionPane.showMessageDialog(menuGrupos,
						"Los visitantes no pueden crear un grupo", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			getV().cambiarPanelCrearGrupo();
		} else if (source.equals(menuGrupos.getOk())) {
			GUIBuscarGrupo buscarGrupos = getV().getBuscarGrupo();
			String busqueda = menuGrupos.getBusquedaText();
			if (busqueda == null || busqueda.equals("")) {
				JOptionPane.showMessageDialog(menuGrupos,
						"Rellene el campo de Grupo", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			ArrayList<Grupo> listaGrupos = new ArrayList<>();
			listaGrupos.addAll(getModelo().buscarGrupoLista(busqueda));
			buscarGrupos.setValores(listaGrupos);
			getV().cambiarPanelBuscarGrupo();
		} else if (source.equals(menuGrupos.getVer())) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) menuGrupos
					.getArbol().getLastSelectedPathComponent();
			if (node != null) {
				System.out.println(node.toString());
				Grupo g = getModelo().buscarGrupo(node.toString());
				if (g.isGrupoColaborativo()) {
					GUIVerGrupoColaborativo verGrupoColaborativo = getV().getVerGrupoColaborativo();
					verGrupoColaborativo.setValores((GrupoColaborativo)getModelo().buscarGrupo(g.getNombre()));
					getV().cambiarPanelVerGrupoColaborativo();
				} else {
					GUIVerGrupo verGrupo = getV().getVerGrupos();
					verGrupo.setValores(g);
					getV().cambiarPanelVerGrupo();
				}
			} else {
				JOptionPane.showMessageDialog(menuGrupos,
						"Seleccione un grupo primero", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

		} else if (source.equals(menuGrupos.getSalirGrupo())) {
			int confirmado = JOptionPane.showConfirmDialog(menuGrupos,
					"¿Deseas Borrar el Grupo?");
			if (JOptionPane.OK_OPTION == confirmado) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) menuGrupos
						.getArbol().getLastSelectedPathComponent();
				if (node != null) {
					System.out.println(node.toString());
					getModelo().cargarGrupos();
					getModelo().cargarUsuarios();
					Grupo g = getModelo().buscarGrupo(node.toString());
					Usuario u = getModelo().getLogged();
					System.out.println("listaGrupoa" + u.getListaGrupos().size());
					Boolean b1 = g.removeUsuario(u);
					Boolean b2 = u.removeGrupo(g);
					System.out.println(b1.toString() + " " + b2 + "");
					if (b1 && b2) {
						getModelo().guardarUsuario(u);
						getModelo().actualizarLogged();
						getModelo().guardarGrupos();
						menuGrupos.setValores();
					} else {
						JOptionPane.showMessageDialog(menuGrupos,
								"Error al Salir del grupo", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				menuGrupos.setValores();
				System.out.println("confirmado");
			} else {
				System.out.println("vale... no borro nada...");
			}
			return;
		}
	}

}
