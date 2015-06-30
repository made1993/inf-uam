package control;

import gui.*;

import java.awt.event.*;

import javax.swing.*;

import usuario.*;
import mailUam.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase ControlLogin
 */
public class ControlLogin implements ActionListener {

	private MailUam modelo;
	private Ventana v;
	
	
	public ControlLogin(Ventana v, MailUam modelo) {
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

		GUILogin login = v.getLogin();
		if (source.equals(login.getBotonOk())) {
			if (login.getCampoCorreoText().equals("")
					|| login.getCampoContrasenaText().equals("")) {
				JOptionPane.showMessageDialog(login,
						"Debe introducir un id correcto", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Usuario usu = modelo.login(login.getCampoCorreoText(),
					login.getCampoContrasenaText());
			if (usu == null) {
				JOptionPane.showMessageDialog(login,
						"Correo o contrase�a no validos", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			GUIMenu menu = v.getMenu(); 
			menu.setValores();
			v.cambiarPanelMenu();
		}
		else if(source.equals(login.getBotonVisitante())){
			if (login.getCampoCorreoText().equals("")){
				JOptionPane.showMessageDialog(login,
						"Debe introducir un Nombre correcto", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Usuario usu = modelo.loginVisitante(login.getCampoCorreoText());
			if (usu == null) {
				JOptionPane.showMessageDialog(login,
						"Correo no valido", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			GUIMenu menu = v.getMenu(); 
			menu.setValores();
			v.cambiarPanelMenu();
		}else if (source.equals(login.getBotonSalir())) {
			getModelo().guardarGrupos();
			getModelo().guardarUsuarios();
			System.exit(0);
		}
	}

}
