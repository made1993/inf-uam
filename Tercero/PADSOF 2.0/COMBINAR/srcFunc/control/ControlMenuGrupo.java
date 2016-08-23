package control;

import grupo.Grupo;
import gui.*;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import mailUam.MailUam;

public class ControlMenuGrupo implements ActionListener {
	

	private MailUam modelo;
	private Ventana v;

	public ControlMenuGrupo(Ventana v, MailUam modelo) {
		this.modelo = modelo;
		this.v = v;
	}

	
	
	public MailUam getModelo() {
		return modelo;
	}



	public void setModelo(MailUam modelo) {
		this.modelo = modelo;
	}



	public Ventana getV() {
		return v;
	}



	public void setV(Ventana v) {
		this.v = v;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Object source = arg0.getSource();

		GUIMenuGrupo menuGrupos = getV().getMenuGrupos();
		
		if(source.equals(menuGrupos.getBotonMensajes())){
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		}else if(source.equals(menuGrupos.getBotonGrupos())){
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		}else if(source.equals(menuGrupos.getBotonVerPrefil())){
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		}else if(source.equals(menuGrupos.getBotonSalir())){
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		}else if (source.equals(menuGrupos.getCrearGrupo())) {
			getV().getCrearGrupo().setValores();
			if(getModelo().getLogged().isVisitante()){
				JOptionPane.showMessageDialog(menuGrupos,
						"Los visitantes no pueden crear un grupo", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			getV().cambiarPanelCrearGrupo();
		} else if (source.equals(menuGrupos.getOk())) {
			GUIBuscarGrupo buscarGrupos = getV().getBuscarGrupo();
			String busqueda = menuGrupos.getBusquedaText();
			if(busqueda == null || busqueda.equals("")){
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
			GUIVerGrupo verGrupo = getV().getVerGrupos();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    menuGrupos.getArbol().getLastSelectedPathComponent();
			if(node != null){
				System.out.println(node.toString());
				verGrupo.setValores(getModelo().buscarGrupo(node.toString()));//TODO setValores
				getV().cambiarPanelVerGrupo();
			}else{
				JOptionPane.showMessageDialog(menuGrupos,
						"Seleccione un grupo primero", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		} else if (source.equals(menuGrupos.getSalirGrupo())) {
			int confirmado = JOptionPane.showConfirmDialog(menuGrupos,
					"¿Deseas Borrar el Grupo?");//TODO terminar
			if (JOptionPane.OK_OPTION == confirmado)
				System.out.println("confirmado");
			else
				System.out.println("vale... no borro nada...");
			return;
		}else{
			//TODO
			// private ArrayList<JButton> salirGrupo;
						// private ArrayList<JButton> ver;

		}
	}

}
