package control;

import gui.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import mailUam.MailUam;
import mensaje.MensajeUsuario;

public class ControlMensaje implements ActionListener {

	private MailUam modelo;
	private Ventana v;

	public ControlMensaje(Ventana v, MailUam modelo) {
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
		System.out.println("Activando controlador: " + getClass());
		GUIMensaje menu = v.getMensajes();

		int cont = 0;
		ArrayList<MensajeUsuario> mensajesSelect = new ArrayList<>();
		
		for (int i = 1;i<menu.getTablaEntrada().getRowCount();i++) {
			if(menu.getTablaEntrada().getValueAt(i, 0) instanceof Boolean){
				Boolean b = (Boolean) menu.getTablaEntrada().getValueAt(i, 0);
				if(b){
					mensajesSelect.add(getModelo().getLogged().getBuzon().getMensajes().get(i-1));
				}
			}
		}

		if (source.equals(menu.getBotonMensajes())) {
			System.out.println("Cambiando a Mensajes");
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(menu.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(menu.getBotonVerPrefil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(menu.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(menu.getBotonBorrar())) {
			if (mensajesSelect.size() > 0) {
				for (MensajeUsuario m : mensajesSelect) {
					getModelo().getLogged().getBuzon().removeMensaje(m);
				}
				getModelo().guardarUsuario();
				JOptionPane.showMessageDialog(null,
						"Los mensajes se han eliminado correctamente");
				menu.setValores();
				return;
			} else {
				JOptionPane.showMessageDialog(menu,
						"Seleccione un mensaje para borrarlo", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if (source.equals(menu.getBotonRedactar())) {
			if (mensajesSelect.size() != 0) {
				JOptionPane.showMessageDialog(null,"No hace falta seleccionar un mensaje si quiere reenviar o responder seleccione el boton adecuado");
			}
			v.cambiarPanelCrearMensajes();
		} else if (source.equals(menu.getBotonReenviar())) {
			if (mensajesSelect.size() != 1) {
				JOptionPane.showMessageDialog(menu,
						"Seleccione un mensaje para reenviar", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			GUICrearMensaje crearMensaje = v.getCrearMensaje();
			// TODO crearMensaje.setValores(m);
			v.cambiarPanelCrearMensajes();
		} else if (source.equals(menu.getBotonResponder())) {
			if (mensajesSelect.size() != 1) {
				JOptionPane.showMessageDialog(menu,
						"Seleccione un mensaje para responder", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			GUICrearMensaje crearMensaje = v.getCrearMensaje();
			// TODO crearMensaje.setValores(m);
			v.cambiarPanelCrearMensajes();
		} else if (source.equals(menu.getBotonVer())) {
			if (mensajesSelect.size() != 1) {
				JOptionPane.showMessageDialog(menu,
						"Seleccione un mensaje para ver", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			GUIVerMensaje verMensaje= getV().getVerMensaje();
			verMensaje.setValores(mensajesSelect.get(0));
			v.cambiarPanelVerMensaje();
		}else if (source.equals(menu.getBotonBuscar())) {
			if (mensajesSelect.size() != 0) {
				JOptionPane.showMessageDialog(null,"No hace falta seleccionar un mensaje si quiere buscar");
			}
			menu.setValores();
			return;
		}
	}

}
