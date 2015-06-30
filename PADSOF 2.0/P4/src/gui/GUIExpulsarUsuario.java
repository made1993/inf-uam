package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

import layout.SpringUtilities;
import mailUam.*;
import grupo.*;
import usuario.*;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase GUIExpulsarUsuario
 */
public class GUIExpulsarUsuario extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton buttonExpulsar;
	private JPanel usuarios;
	private Grupo grupo;

	/**
	 * Constructor de GUIExpulsarUsuario
	 * 
	 * @param app
	 *            modelo de la aplicacion
	 */
	public GUIExpulsarUsuario(MailUam app) {
		super(app);
		setLabelTituloText("Explusar Usuarios");
		buttonExpulsar = new JButton("Expulsar usuarios seleccionados");
		usuarios = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		p2.setLayout(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		p2.add(usuarios,c);
		c.gridx = 0;
		c.gridy = 1;
		p2.add(buttonExpulsar,c);
		p1.add(p2);
		add(p1);
	}

	/**
	 * @return the buttonExpulsar
	 */
	public JButton getButtonExpulsar() {
		return buttonExpulsar;
	}

	/**
	 * @param buttonExpulsar
	 *            the buttonExpulsar to set
	 */
	public void setButtonExpulsar(JButton buttonExpulsar) {
		this.buttonExpulsar = buttonExpulsar;
	}

	/**
	 * @return the grupo
	 */
	public Grupo getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo
	 *            the grupo to set
	 */
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the usuarios
	 */
	public JPanel getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios
	 *            the usuarios to set
	 */
	public void setUsuarios(JPanel usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Instala los valores en la GUI
	 * @param grupo grupo para listar los usuarios
	 */
	public void setValores(Grupo grupo) {
		this.grupo = grupo;
		usuarios.removeAll();
		System.out.println("tamano grupos" + grupo.getListaUsuarios().size());
		SpringLayout layout = new SpringLayout();
		usuarios.setLayout(layout);
		for (Usuario u : grupo.getListaUsuarios()) {
			usuarios.add(new JCheckBox(u.getCorreo()));
		}
		SpringUtilities.makeCompactGrid(usuarios, grupo.getListaUsuarios().size(),1, 6, 6, 6, 6);
	}

	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		buttonExpulsar.addActionListener(c);
	}

}
