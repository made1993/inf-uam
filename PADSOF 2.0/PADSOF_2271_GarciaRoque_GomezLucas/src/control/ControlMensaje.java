package control;

import grupo.*;
import gui.*;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import usuario.Usuario;
import mailUam.MailUam;
import mensaje.*;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase ControlMensaje
 */
public class ControlMensaje implements ActionListener {

	private MailUam modelo;
	private Ventana v;
	public static final String SUJETOMODERAR = "Para moderar grupo: ";

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

		ArrayList<MensajeUsuario> mensajesSelect = new ArrayList<>();

		for (int i = 1; i < menu.getTablaEntrada().getRowCount(); i++) {
			if (menu.getTablaEntrada().getValueAt(i, 0) instanceof Boolean) {
				Boolean b = (Boolean) menu.getTablaEntrada().getValueAt(i, 0);
				if (b) {
					mensajesSelect.add(getModelo().getLogged().getBuzon()
							.getMensajes().get(i - 1));
				}
			}
		}
		MensajeUsuario mensajeU = null;
		if (mensajesSelect.size() > 0) {
			mensajeU = mensajesSelect.get(0);
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
		} else if (source.equals(menu.getBotonVerPerfil())) {
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
				JOptionPane
						.showMessageDialog(
								null,
								"No hace falta seleccionar un mensaje si quiere reenviar o responder seleccione el boton adecuado");
			}
			GUICrearMensaje crearMensaje = v.getCrearMensaje();
			crearMensaje.setValores();
			v.cambiarPanelCrearMensajes();
		} else if (source.equals(menu.getBotonReenviar())) {
			if (mensajesSelect.size() != 1) {
				JOptionPane.showMessageDialog(menu,
						"Seleccione un mensaje para reenviar", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			GUICrearMensaje crearMensaje = v.getCrearMensaje();
			crearMensaje.setValoresReenviar(mensajeU);
			v.cambiarPanelCrearMensajes();
		} else if (source.equals(menu.getBotonResponder())) {
			if (mensajesSelect.size() != 1) {
				JOptionPane.showMessageDialog(menu,
						"Seleccione un mensaje para responder", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			GUICrearMensaje crearMensaje = v.getCrearMensaje();
			crearMensaje.setValoresResponder(mensajeU);
			v.cambiarPanelCrearMensajes();
		} else if (source.equals(menu.getBotonVer())) {
			if (mensajesSelect.size() != 1) {
				JOptionPane.showMessageDialog(menu,
						"Seleccione un mensaje para ver", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			GUIVerMensaje verMensaje = getV().getVerMensaje();
			String[] s = mensajeU.getSujeto().split(": ");
			if (!s[0].equals(SUJETOMODERAR.split(": ")[0])) {
				System.out.println(mensajeU.getSujeto() + " " + s[0]);
				verMensaje.setValores(mensajeU);
				v.cambiarPanelVerMensaje();
			} else {
				int confirmado = JOptionPane.showConfirmDialog(
						menu,
						"¿Desea aceptar el siguiente mensaje: "
								+ mensajeU.getCuerpo() + " de "
								+ mensajeU.getRemitente().getCorreo()
								+ " en el grupo: " + s[1] + "?");// TODO
																	// terminar
				System.out.println("" + confirmado);
				if (JOptionPane.OK_OPTION == confirmado) {
					getModelo().cargarUsuarios();
					Grupo g = getModelo().buscarGrupo(s[1]);
					g.addMensajeModerado(new MensajeGrupo(mensajeU.getCuerpo(),
							mensajeU.getRemitente()));
					getModelo().guardarGrupos();
					getModelo().actualizarLogged();
					System.out.println(getModelo().getLogged().removeMensaje(
							mensajeU));
					getModelo().guardarUsuario(getModelo().getLogged());
				} else if (JOptionPane.CANCEL_OPTION == confirmado) {
					System.out.println("No haces nada");
				} else if (JOptionPane.NO_OPTION == confirmado) {
					System.out.println("no option");
					getModelo().cargarUsuarios();
					Usuario u = getModelo().buscarUsuario(
							mensajeU.getRemitente().getCorreo());
					u.addMensajeBuzon(new MensajeUsuario(mensajeU
							.getRemitente(), "Se ha denegado el mensaje: "
							+ mensajeU.getCuerpo(), "Mensaje denegado de "
							+ s[1], getModelo().getLogged()));

					getModelo().actualizarLogged();
					getModelo().getLogged().removeMensaje(mensajeU);
					getModelo().guardarUsuario(getModelo().getLogged());
					getModelo().guardarUsuario(mensajeU.getRemitente());
				}
			}
			menu.setValores();
		} else if (source.equals(menu.getBotonBuscar())) {
			if (mensajesSelect.size() != 0) {
				JOptionPane
						.showMessageDialog(null,
								"No hace falta seleccionar un mensaje si quiere buscar");
			}
			menu.setValores();
			return;
		}
	}

}
