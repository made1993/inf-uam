package gui;

import java.awt.Label;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import mailUam.*;
import mensaje.MensajeUsuario;
import usuario.*;

public class GUIMensaje extends GUIMenu{

	
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
		p1.add(botonRedactar);
		p1.add(botonReenviar);
		p1.add(botonResponder);
		p1.add(botonBorrar);
		p1.add(botonVer);
		p1.add(pestanaMensajes);
		p1.add(campoBuscar);
		p1.add(botonBuscar);
		add(p1);
	}
	
	

	/**
	 * @return the pestanaMensajes
	 */
	public JTabbedPane getPestanaMensajes() {
		return pestanaMensajes;
	}



	/**
	 * @param pestanaMensajes the pestanaMensajes to set
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
	 * @param panelEntrada the panelEntrada to set
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
	 * @param panelEnviados the panelEnviados to set
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
	 * @param botonRedactar the botonRedactar to set
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
	 * @param botonReenviar the botonReenviar to set
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
	 * @param botonBorrar the botonBorrar to set
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
	 * @param botonResponder the botonResponder to set
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
	 * @param botonVer the botonVer to set
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
	 * @param tablaEntrada the tablaEntrada to set
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
	 * @param campoBuscar the campoBuscar to set
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
	 * @param botonBuscar the botonBuscar to set
	 */
	public void setBotonBuscar(JButton botonBuscar) {
		this.botonBuscar = botonBuscar;
	}



	public void setValores() {
		// TODO Auto-generated method stub
		System.out.println("AET VALORES MENU MENSAJES");
		getM().actualizarLogged();
		getM().cargarUsuarios();
		System.out.println(getM().buscarUsuario(getM().getLogged().getCorreo()));
		panelEntrada.removeAll();
		AbstractTableModel miTabla = new GUITablaBuzon(getM().getLogged().getBuzon().getMensajes());
		tablaEntrada = new JTable(miTabla);
		tablaEntrada.setTableHeader(new JTableHeader());
		panelEntrada.add(tablaEntrada);
		tablaEntrada.setAutoCreateRowSorter(true);
		TableRowSorter filtro = new TableRowSorter(miTabla);
		String texto=getCampoBuscar().getText();
		RowFilter<GUITablaBuzon, String> rf = RowFilter.regexFilter(texto);
		filtro.setRowFilter(rf);
		tablaEntrada.setRowSorter(filtro);
		
//		panelEnviados = new JPanel();
//		for(MensajeUsuario m:logged.getBuzon().getMensajes()){
//			panelEnviados.add(new Label(m.getRemitente().getCorreo()+" - "+m.getSujeto()));
//		}
		
	}
	
	@Override
	public void setControlador(ActionListener c) {
		//TODO Auto-generated method stub
		super.setControlador(c);
		botonBorrar.addActionListener(c);
		botonRedactar.addActionListener(c);
		botonReenviar.addActionListener(c);
		botonResponder.addActionListener(c);
		botonVer.addActionListener(c);
		botonBuscar.addActionListener(c);
	}

}
