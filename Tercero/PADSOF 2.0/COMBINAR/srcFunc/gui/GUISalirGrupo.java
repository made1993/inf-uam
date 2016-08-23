package gui;

import java.awt.event.ActionListener;

import javax.swing.*;

import mailUam.*;

public class GUISalirGrupo extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GUISalirGrupo(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Salir Grupo");
	}

	@Override
	public void setControlador(ActionListener c) {
		//TODO Auto-generated method stub
		super.setControlador(c);
	}
	
}
