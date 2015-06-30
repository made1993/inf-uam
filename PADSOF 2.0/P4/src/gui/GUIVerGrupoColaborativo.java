package gui;

import grupo.GrupoColaborativo;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import usuario.Usuario;
import layout.SpringUtilities;
import mailUam.MailUam;
import mensaje.MensajeColaborativo;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase GUIVerGrupoColaborativo
 */
public class GUIVerGrupoColaborativo extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable tablaMensajes;
	private JPanel panelTabla;
	private JButton botonEntrar;
	private JButton botonEnviar;
	private JButton botonTerminar;
	private JTextField campoEscribir;
	private Usuario propietario;
	private GrupoColaborativo grupoColaborativo;
	private MensajeColaborativo mensajeColaborativo;
	private boolean principal;

	/**
	 * Constructor de GUIVerGrupoColaborativo
	 * 
	 * @param app
	 *            modelo de la aplicacion
	 */
	public GUIVerGrupoColaborativo(MailUam app) {
		super(app);
		setLabelTituloText("");
		botonEntrar = new JButton("Entrar");
		botonEnviar = new JButton("Enviar");
		botonTerminar = new JButton("Terminar");
		campoEscribir = new JTextField("Escribe tu mensaje...");
		panelTabla = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		p1.setLayout(new GridBagLayout());
		
		p2.add(botonEntrar);
		p2.add(botonTerminar);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		p1.add(p2,c);
		c.gridy = 1;
		p1.add(panelTabla,c);
		p3.add(campoEscribir);
		p3.add(botonEnviar);
		c.gridy = 2;
		p1.add(p3,c);
		
		
		add(p1);
	}

	/**
	 * @return the tablaMensajes
	 */
	public JTable getTablaMensajes() {
		return tablaMensajes;
	}

	/**
	 * @param tablaMensajes
	 *            the tablaMensajes to set
	 */
	public void setTablaMensajes(JTable tablaMensajes) {
		this.tablaMensajes = tablaMensajes;
	}

	/**
	 * @return the panelTabla
	 */
	public JPanel getPanelTabla() {
		return panelTabla;
	}

	/**
	 * @param panelTabla
	 *            the panelTabla to set
	 */
	public void setPanelTabla(JPanel panelTabla) {
		this.panelTabla = panelTabla;
	}

	/**
	 * @return the botonEntrar
	 */
	public JButton getBotonEntrar() {
		return botonEntrar;
	}

	/**
	 * @param botonEntrar
	 *            the botonEntrar to set
	 */
	public void setBotonEntrar(JButton botonEntrar) {
		this.botonEntrar = botonEntrar;
	}

	/**
	 * @return the botonEnviar
	 */
	public JButton getBotonEnviar() {
		return botonEnviar;
	}

	/**
	 * @param botonEnviar
	 *            the botonEnviar to set
	 */
	public void setBotonEnviar(JButton botonEnviar) {
		this.botonEnviar = botonEnviar;
	}

	/**
	 * @return the botonTerminar
	 */
	public JButton getBotonTerminar() {
		return botonTerminar;
	}

	/**
	 * @param botonTerminar
	 *            the botonTerminar to set
	 */
	public void setBotonTerminar(JButton botonTerminar) {
		this.botonTerminar = botonTerminar;
	}

	/**
	 * @return the campoEscribir
	 */
	public JTextField getCampoEscribir() {
		return campoEscribir;
	}

	/**
	 * @param campoEscribir
	 *            the campoEscribir to set
	 */
	public void setCampoEscribir(JTextField campoEscribir) {
		this.campoEscribir = campoEscribir;
	}

	/**
	 * @return the propietario
	 */
	public Usuario getPropietario() {
		return propietario;
	}

	/**
	 * @param propietario
	 *            the propietario to set
	 */
	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}

	/**
	 * @return the grupoColaborativo
	 */
	public GrupoColaborativo getGrupoColaborativo() {
		return grupoColaborativo;
	}

	/**
	 * @param grupoColaborativo
	 *            the grupoColaborativo to set
	 */
	public void setGrupoColaborativo(GrupoColaborativo grupoColaborativo) {
		this.grupoColaborativo = grupoColaborativo;
	}

	/**
	 * @return the mensajeColaborativo
	 */
	public MensajeColaborativo getMensajeColaborativo() {
		return mensajeColaborativo;
	}

	/**
	 * @param mensajeColaborativo
	 *            the mensajeColaborativo to set
	 */
	public void setMensajeColaborativo(MensajeColaborativo mensajeColaborativo) {
		this.mensajeColaborativo = mensajeColaborativo;
	}

	/**
	 * @return the principal
	 */
	public boolean isPrincipal() {
		return principal;
	}

	/**
	 * @param principal
	 *            the principal to set
	 */
	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	/**
	 * Instala los valores en la GUI
	 * 
	 * @param mensaje
	 *            mensaje en el que lista las respuestas de ese mensaje
	 */
	public void setValores(MensajeColaborativo mensaje) {
		principal = false;
		panelTabla.removeAll();
		setLabelTituloText(getlabelTitulo().getText() + "/"
				+ mensaje.getCuerpo());
		mensajeColaborativo = ((GrupoColaborativo) getM().buscarGrupo(
				getGrupoColaborativo().getNombre())).buscarMensaje(mensaje
				.getIdMensaje());
		if (mensajeColaborativo.isCerrado()) {
			botonEnviar.setEnabled(false);
		} else {
			botonEnviar.setEnabled(true);
		}
		System.out.println("Tamano"
				+ mensajeColaborativo.getRespuestas().size());
		AbstractTableModel modeloDatos = new GUITablaMensajeColaborativo(
				mensajeColaborativo.getRespuestas());
		tablaMensajes = new JTable(modeloDatos);
		tablaMensajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaMensajes
				.setPreferredScrollableViewportSize(new Dimension(500, 80));
		JScrollPane scroll = new JScrollPane(tablaMensajes);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelTabla.add(scroll);
		// tablaMensajes.setAutoCreateRowSorter(true);
		validate();
	}

	/**
	 * Instala los valores en la GUI
	 * 
	 * @param grupo
	 *            grupo que lista los mensajes del grupo colaborativo
	 */
	public void setValores(GrupoColaborativo grupo) {
		principal = true;
		panelTabla.removeAll();
		setLabelTituloText(grupo.getNombre());
		botonEnviar.setEnabled(true);
		grupoColaborativo = grupo;
		System.out.println("Tamano" + grupo.getListaMensajes().size());
		System.out.println(grupo.getNombre() + "\n" + grupo.getListaMensajes());
		AbstractTableModel modeloDatos = new GUITablaMensajeColaborativo(
				grupo.getListaMensajes());
		tablaMensajes = new JTable(modeloDatos);
		tablaMensajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaMensajes
				.setPreferredScrollableViewportSize(new Dimension(500, 80));
		JScrollPane scroll = new JScrollPane(tablaMensajes);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelTabla.add(scroll);
		validate();
	}

	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		botonEntrar.addActionListener(c);
		botonEnviar.addActionListener(c);
		botonTerminar.addActionListener(c);
	}

}
