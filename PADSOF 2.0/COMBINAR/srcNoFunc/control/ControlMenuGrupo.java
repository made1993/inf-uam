package control;

import grupo.Grupo;
import gui.*;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import usuario.Usuario;
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
			if(getModelo().getLogged().isVisitante()){
				JOptionPane.showMessageDialog(menuGrupos,
						"Los visitantes no pueden crear un grupo", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			System.out.println("Cambiando a crear grupo");
			GUICrearGrupo crearGrupo = v.getCrearGrupo();
			crearGrupo.setValores();
			v.cambiarPanelCrearGrupo();
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
				verGrupo.setValores(getModelo().buscarGrupo(node.toString()));
				if(verGrupo.getGrupo()==null){
					JOptionPane.showMessageDialog(menuGrupos,
							"Seleccione el grupo correcto", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				getV().cambiarPanelVerGrupo();
			}else{
				JOptionPane.showMessageDialog(menuGrupos,
						"Seleccione un grupo primero", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
		} else if (source.equals(menuGrupos.getSalirGrupo())) {
			int confirmado = JOptionPane.showConfirmDialog(menuGrupos,
					"¿Deseas Salir del Grupo?");
			if (JOptionPane.OK_OPTION == confirmado){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	                    menuGrupos.getArbol().getLastSelectedPathComponent();
				if(node != null){
					System.out.println(node.toString());
					getModelo().cargarGrupos();
					getModelo().cargarUsuarios();
					Grupo g = getModelo().buscarGrupo(node.toString());
					Usuario u = getModelo().getLogged();
					System.out.println("listaUsua"+g.getListaUsuarios());
					System.out.println("listaGrupoa"+u.getListaGrupos());
					Boolean b1 = g.removeUsuario(u);
					Boolean b2 = u.removeGrupo(g);
					System.out.println(b1.toString()+" "+b2+"");
					if(b1 && b2){
						getModelo().guardarUsuario(u);
						getModelo().guardarGrupo(g);
						menuGrupos.setValores();
					}else{
						JOptionPane.showMessageDialog(menuGrupos,
								"Error al Salir del grupo", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				System.out.println("confirmado");
			}else{
				System.out.println("vale... no borro nada...");
			}
			return;
		}
	}

}
