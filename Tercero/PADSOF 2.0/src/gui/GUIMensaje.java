package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.*;

import layout.SpringUtilities;
import mailUam.*;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase GUIMensaje
 */
public class GUIMensaje extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTabbedPane pestanaMensajes;
	private JPanel panelEntrada;
	private JPanel panelEnviados;
	private JButton botonRedactar;
	private JButton botonReenviar;
	private JButton botonResponder;
	private JButton botonBorrar;
	private JButton botonVer;
	private JTextField campoBuscar;
	private JButton botonBuscar;
	private JTable tablaEntrada;

	/**
	 * Constructor de GUIMensaje
	 * 
	 * @param modelo
	 *            modelo de la aplicacion
	 */
	public GUIMensaje(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Menu Mensaje");
		botonRedactar = new JButton("Redactar");
		botonReenviar = new JButton("Reenviar");
		botonResponder = new JButton("Responder");
		botonBorrar = new JButton("Borrar");
		botonVer = new JButton("Ver");
		pestanaMensajes = new JTabbedPane();
		panelEntrada = new JPanel();
		panelEnviados = new JPanel();
		botonBuscar = new JButton("Buscar");
		campoBuscar = new JTextField(10);
		pestanaMensajes.addTab("Entrada", panelEntrada);
		pestanaMensajes.addTab("Enviados", panelEnviados);

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();

		SpringLayout layout = new SpringLayout();
		p2.setLayout(layout);

		p3.setLayout(layout);

		GridBagConstraints c = new GridBagConstraints();
		p1.setLayout(new GridBagLayout());

		p2.add(botonRedactar);
		p2.add(botonReenviar);
		p2.add(botonResponder);
		p2.add(botonBorrar);
		p2.add(botonVer);
		p3.add(campoBuscar);
		p3.add(botonBuscar);
		c.gridx = 0;
		c.gridy = 0;
		p1.add(p2, c);
		c.gridx = 0;
		c.gridy = 1;
		p1.add(p3, c);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 4;
		p1.add(pestanaMensajes, c);

		SpringUtilities.makeCompactGrid(p2, 1, 5, 6, 6, 6, 6);

		SpringUtilities.makeCompactGrid(p3, 1, 2, 6, 6, 6, 6);
		this.setVisible(true);

		add(p1);
	}

	/**
	 * @return the pestanaMensajes
	 */
	public JTabbedPane getPestanaMensajes() {
		return pestanaMensajes;
	}

	/**
	 * @param pestanaMensajes
	 *            the pestanaMensajes to set
	 */
	public void setPestanaMensajes(JTabbedPane pestanaMensajes) {
		this.pestanaMensajes = pestanaMensajes;
	}

	/**
	 * @return the panelEntrada
	 */
	public JPanel getPanelEntrada() {
		return panelEntrada;
	}

	/**
	 * @param panelEntrada
	 *            the panelEntrada to set
	 */
	public void setPanelEntrada(JPanel panelEntrada) {
		this.panelEntrada = panelEntrada;
	}

	/**
	 * @return the panelEnviados
	 */
	public JPanel getPanelEnviados() {
		return panelEnviados;
	}

	/**
	 * @param panelEnviados
	 *            the panelEnviados to set
	 */
	public void setPanelEnviados(JPanel panelEnviados) {
		this.panelEnviados = panelEnviados;
	}

	/**
	 * @return the botonRedactar
	 */
	public JButton getBotonRedactar() {
		return botonRedactar;
	}

	/**
	 * @param botonRedactar
	 *            the botonRedactar to set
	 */
	public void setBotonRedactar(JButton botonRedactar) {
		this.botonRedactar = botonRedactar;
	}

	/**
	 * @return the botonReenviar
	 */
	public JButton getBotonReenviar() {
		return botonReenviar;
	}

	/**
	 * @param botonReenviar
	 *            the botonReenviar to set
	 */
	public void setBotonReenviar(JButton botonReenviar) {
		this.botonReenviar = botonReenviar;
	}

	/**
	 * @return the botonBorrar
	 */
	public JButton getBotonBorrar() {
		return botonBorrar;
	}

	/**
	 * @param botonBorrar
	 *            the botonBorrar to set
	 */
	public void setBotonBorrar(JButton botonBorrar) {
		this.botonBorrar = botonBorrar;
	}

	/**
	 * @return the botonResponder
	 */
	public JButton getBotonResponder() {
		return botonResponder;
	}

	/**
	 * @param botonResponder
	 *            the botonResponder to set
	 */
	public void setBotonResponder(JButton botonResponder) {
		this.botonResponder = botonResponder;
	}

	/**
	 * @return the botonBorrar
	 */
	public JButton getBotonVer() {
		return botonVer;
	}

	/**
	 * @param botonVer
	 *            the botonVer to set
	 */
	public void setBotonVer(JButton botonVer) {
		this.botonVer = botonVer;
	}

	/**
	 * @return the tablaEntrada
	 */
	public JTable getTablaEntrada() {
		return tablaEntrada;
	}

	/**
	 * @param tablaEntrada
	 *            the tablaEntrada to set
	 */
	public void setTablaEntrada(JTable tablaEntrada) {
		this.tablaEntrada = tablaEntrada;
	}

	/**
	 * @return the campoBuscar
	 */
	public JTextField getCampoBuscar() {
		return campoBuscar;
	}

	/**
	 * @param campoBuscar
	 *            the campoBuscar to set
	 */
	public void setCampoBuscar(JTextField campoBuscar) {
		this.campoBuscar = campoBuscar;
	}

	/**
	 * @return the botonBuscar
	 */
	public JButton getBotonBuscar() {
		return botonBuscar;
	}

	/**
	 * @param botonBuscar
	 *            the botonBuscar to set
	 */
	public void setBotonBuscar(JButton botonBuscar) {
		this.botonBuscar = botonBuscar;
	}

	/**
	 * Instala los valores en la GUI
	 */
	public void setValores() {
		System.out.println("SET VALORES MENU MENSAJES");
		getM().actualizarLogged();
		getM().cargarUsuarios();
		panelEntrada.removeAll();
		AbstractTableModel miTabla = new GUITablaBuzon(getM().getLogged()
				.getBuzon().getMensajes());
		tablaEntrada = new JTable(miTabla);
		tablaEntrada.setTableHeader(new JTableHeader());
		tablaEntrada.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaEntrada.setPreferredScrollableViewportSize(new Dimension(500, 80));
		JScrollPane scroll = new JScrollPane(tablaEntrada);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelEntrada.add(scroll);
		tablaEntrada.setAutoCreateRowSorter(true);
		TableRowSorter filtro = new TableRowSorter(miTabla);
		String texto = getCampoBuscar().getText();
		RowFilter<GUITablaBuzon, String> rf = RowFilter.regexFilter(texto);
		filtro.setRowFilter(rf);
		tablaEntrada.setRowSorter(filtro);
	}

	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		botonBorrar.addActionListener(c);
		botonRedactar.addActionListener(c);
		botonReenviar.addActionListener(c);
		botonResponder.addActionListener(c);
		botonVer.addActionListener(c);
		botonBuscar.addActionListener(c);
	}

}
