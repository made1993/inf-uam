package control;

import grupo.*;
import gui.*;

import java.awt.event.*;

import javax.swing.JOptionPane;

import usuario.*;
import mailUam.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase ControlCrearGrupo
 */
public class ControlCrearGrupo implements ActionListener {

	private MailUam modelo;
	private Ventana v;

	/**
	 * Constructor de ControlCrearGrupo
	 * @param v ventana de la aplicacion
	 * @param modelo mdoelo de la aplicacion
	 */
	public ControlCrearGrupo(Ventana v, MailUam modelo) {
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

		GUICrearGrupo crearGrupo = getV().getCrearGrupo();

		System.out.println("Activando controlador: " + getClass());
		if (source.equals(crearGrupo.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(crearGrupo.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(crearGrupo.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(crearGrupo.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(crearGrupo.getBotonAnadir())) {
			Grupo grupo;
			boolean privado = false;
			Usuario moderado = null;
			if (getModelo().getLogged().isVisitante()) {
				JOptionPane
						.showMessageDialog(
								crearGrupo,
								"¿Que haces aqui? Los visitantes no pueden estar aqui",
								"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (crearGrupo.getCampoNombreText().equals(null)
					|| crearGrupo.getCampoNombreText().equals("")) {
				JOptionPane.showMessageDialog(crearGrupo,
						"El nombre esta vacio, rellenelo", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (getModelo().buscarGrupo(crearGrupo.getCampoNombreText()) != null) {
				JOptionPane.showMessageDialog(crearGrupo,
						"Ya existe un grupo con ese nombre", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (crearGrupo.getCheckPrivado().isSelected()) {
				privado = true;
			}
			if (crearGrupo.getCheckModerado().isSelected()) {
				moderado = getModelo().getLogged();
			}

			if (crearGrupo.getComboTipoGrupo().getSelectedItem().equals("Estudio")) {
				if (!getModelo().getLogged().isProfesor()) {
					JOptionPane.showMessageDialog(crearGrupo,
							"No le esta permitido crear ese tipo de grupos",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				grupo = new GrupoEstudio(0, crearGrupo.getCampoNombreText(),
						privado, moderado, (Profesor) getModelo().getLogged());
			} else if (crearGrupo.getComboTipoGrupo().getSelectedItem()
					.equals("Colaborativo")) {
				grupo = new GrupoColaborativo(0,
						crearGrupo.getCampoNombreText());
			} else {
				grupo = new Grupo(0, crearGrupo.getCampoNombreText(), privado,
						moderado);
			}
			getModelo().cargarGrupos();
			getModelo().cargarUsuarios();
			getModelo().actualizarLogged();
			getModelo().getLogged().addGrupo(grupo);
			grupo.addUsuario(getModelo().getLogged());
			getModelo().addGrupo(grupo);
			if (crearGrupo.getCheckSubgrupo().isSelected()) {
				int confirmado = JOptionPane.showConfirmDialog(crearGrupo,
						"Las propiedades de un grpuoe se heredan asu subgrupo¿Quieres continuar?");
				if (JOptionPane.OK_OPTION != confirmado) {
					System.out.println("No creamos el grupo");
					return;
				}
				System.out.println("Creamos el subgrupo");
				getModelo().cargarGrupos();
				String nombreGrupo = crearGrupo.getComboListaGrupo()
						.getSelectedItem().toString();
				Grupo padre = getModelo().buscarGrupo(nombreGrupo);
				if (padre.isGrupoColaborativo()) {
					JOptionPane
							.showMessageDialog(
									crearGrupo,
									"No esta permitido crear subgrupos de un grupo colaborativo",
									"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(padre.isGrupoEstudio()){
					if(	!getModelo().getLogged().getCorreo().equals(((GrupoEstudio)padre).getProfesor().getCorreo())){
						JOptionPane
						.showMessageDialog(
								crearGrupo,
								"No esta permitido crear subgrupos de este grupo si no eres el profesor que lo creo",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					grupo=  new GrupoEstudio(0, crearGrupo.getCampoNombreText(), padre.isPrivado(),
							padre.getModerador(), ((GrupoEstudio)padre).getProfesor());
					
				}
				else{
					grupo = new Grupo(0, crearGrupo.getCampoNombreText(), padre.isPrivado(), padre.getModerador());
				}
				padre.addSubGrupo(grupo);
				getModelo().guardarGrupos();
				getModelo().guardarUsuarios();
			} else {
				getModelo().crearGrupoDir(grupo.getNombre());
				getModelo().addGrupo(grupo);
				getModelo().guardarGrupos();
				getModelo().guardarUsuarios();
			}
			GUIAnadirParticipante anadirParticipante = getV()
					.getAnadirParticipante();
			anadirParticipante.setValores(grupo);
			getV().cambiarPanelAnadirParticipanteGrupo();

		}
	}

}
