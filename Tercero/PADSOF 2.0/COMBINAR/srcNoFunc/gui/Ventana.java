package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mailUam.MailUam;
import control.ControlAnadirParticipante;
import control.ControlBuscarGrupo;
import control.ControlBuscarUsuario;
import control.ControlCrearGrupo;
import control.ControlCrearMensaje;
import control.ControlCrearPregunta;
import control.ControlCrearRespuesta;
import control.ControlListarPregunta;
import control.ControlLogin;
import control.ControlMensaje;
import control.ControlMenu;
import control.ControlMenuGrupo;
import control.ControlSalirGrupo;
import control.ControlVerGrupo;
import control.ControlVerMensaje;
import control.ControlVerPerfil;
import control.ControlVerRespuesta;

public class Ventana extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nombresGUI[] = { "salir", "Login","menuInicio", "menuGrupos",
			"Perfil", "menuMensajes", "verGrupo", "crearGrupo",
			"buscarGrupo", "verMensaje", "salirGrupo",
			"anadirParticipantesGrupo", "buscarUsuarios", "crearMensaje",
			"crearPregunta", "crearRespuesta","listarPreguntas", "verRespuestas" };	

	private CardLayout cards;
	private JPanel principal;
	private MailUam modelo;
	
	private GUILogin login; // Diapositiva 1
	private GUIMenu menu ; // Diapositiva 2
	private GUIMenuGrupo menuGrupos;// Diapositiva 3
	private GUIVerPerfil perfil; // Diapositiva 4
	private GUIMensaje mensajes; // Diapositiva 5
	private GUIVerGrupo verGrupos;// Diapositiva 6
	private GUICrearGrupo crearGrupo;// Diapositiva 7
	private GUIBuscarGrupo buscarGrupo;// Diapositiva 8
	private GUIVerMensaje verMensaje;// Diapositiva 9
	private GUISalirGrupo salirGrupo;// Diapositiva 10
	private GUIAnadirParticipante anadirParticipante;// Diapositiva 11
	private GUIBuscarUsuario buscarUsuarios;// Diapositiva 12
	private GUICrearMensaje  crearMensaje;// Diapositiva 13
	private GUICrearPregunta crearPregunta;// Diapositiva 14
	private GUICrearRespuesta crearRespuesta;// Diapositiva 15
	private GUIListarPregunta listarPreguntas;// Diapositiva 16
	private GUIVerRespuesta verRespuesta;// Diapositiva 17
	
	public Ventana(MailUam modelo) {
		cards = new CardLayout();
		principal = new JPanel();
		principal.setLayout(cards);
		this.modelo = modelo;

		setLayout(new BorderLayout());
		add(new JLabel("Mail UAM"), BorderLayout.NORTH);

		login = new GUILogin(); // Diapositiva 1
		menu = new GUIMenu(modelo); // Diapositiva 2
		menuGrupos= new GUIMenuGrupo(modelo);// Diapositiva 3
		perfil = new GUIVerPerfil(modelo);// Diapositiva 4
		mensajes = new GUIMensaje(modelo);// Diapositiva 5
		verGrupos= new GUIVerGrupo(modelo);// Diapositiva 6
		crearGrupo= new GUICrearGrupo(modelo);// Diapositiva 7
		buscarGrupo= new GUIBuscarGrupo(modelo);// Diapositiva 8
		verMensaje = new GUIVerMensaje(modelo);// Diapositiva 9
		salirGrupo = new GUISalirGrupo(modelo);// Diapositiva 10
		anadirParticipante = new GUIAnadirParticipante(modelo);// Diapositiva 11
		buscarUsuarios = new GUIBuscarUsuario(modelo);// Diapositiva 12
		crearMensaje = new GUICrearMensaje(modelo);// Diapositiva 13
		crearPregunta = new GUICrearPregunta(modelo);// Diapositiva 14
		crearRespuesta = new GUICrearRespuesta(modelo);// Diapositiva 15
		listarPreguntas = new GUIListarPregunta(modelo);// Diapositiva 16
		verRespuesta= new GUIVerRespuesta(modelo);// Diapositiva 17

		ControlLogin controlLogin = new ControlLogin(this, modelo);
		login.setControlador(controlLogin);
		
		ControlMenu controlmenu = new ControlMenu(this, modelo);
		menu.setControlador(controlmenu);
		
		ControlMenuGrupo controlmenuGrupo = new ControlMenuGrupo(this, modelo);
		menuGrupos.setControlador(controlmenuGrupo);
		
		ControlVerPerfil controlVerPerfil = new ControlVerPerfil(this, modelo);
		perfil.setControlador(controlVerPerfil);
		
		ControlMensaje controlMensaje = new ControlMensaje(this,modelo);
		mensajes.setControlador(controlMensaje);
		
		ControlVerGrupo controlVerGrupo = new ControlVerGrupo(this, modelo);
		verGrupos.setControlador(controlVerGrupo);
		
		ControlCrearGrupo controlCrearGrupo = new ControlCrearGrupo(this, modelo);
		crearGrupo.setControlador(controlCrearGrupo);
		
		ControlBuscarGrupo controlBuscarGrupo = new ControlBuscarGrupo(this, modelo);
		buscarGrupo.setControlador(controlBuscarGrupo);
		
		ControlVerMensaje controlVerMensaje = new ControlVerMensaje(this, modelo);
		verMensaje.setControlador(controlVerMensaje); 
		
		ControlSalirGrupo controlSalirGrupo = new ControlSalirGrupo(this, modelo);
		salirGrupo.setControlador(controlSalirGrupo);
		
		ControlAnadirParticipante controlAnadirParticipante = new ControlAnadirParticipante(this, modelo);
		anadirParticipante.setControlador(controlAnadirParticipante);
		
		ControlBuscarUsuario controlBuscarUsuario = new ControlBuscarUsuario(this, modelo);
		buscarUsuarios.setControlador(controlBuscarUsuario);
		
		ControlCrearMensaje controlCrearMensaje = new ControlCrearMensaje(this, modelo);
		crearMensaje.setControlador(controlCrearMensaje);
		
		ControlCrearPregunta controlCrearPregunta = new ControlCrearPregunta(this, modelo);
		crearPregunta.setControlador(controlCrearPregunta);
		
		ControlCrearRespuesta controlCrearRespuesta = new ControlCrearRespuesta(this, modelo);
		crearRespuesta.setControlador(controlCrearRespuesta);
		
		ControlListarPregunta controlListarPregunta = new ControlListarPregunta(this, modelo);
		listarPreguntas.setControlador(controlListarPregunta);
		
		ControlVerRespuesta controlVerRespuesta = new ControlVerRespuesta(this, modelo);
		verRespuesta.setControlador(controlVerRespuesta);
		
		principal.add(login, nombresGUI[1]);
		principal.add(menu,nombresGUI[2]);
		principal.add(menuGrupos,nombresGUI[3]);
		principal.add(perfil,nombresGUI[4]);
		principal.add(mensajes,nombresGUI[5]);
		principal.add(verGrupos,nombresGUI[6]);
		principal.add(crearGrupo,nombresGUI[7]);
		principal.add(buscarGrupo,nombresGUI[8]);
		principal.add(verMensaje,nombresGUI[9]);
		principal.add(salirGrupo,nombresGUI[10]);
		principal.add(anadirParticipante,nombresGUI[11]);
		principal.add(buscarUsuarios,nombresGUI[12]);
		principal.add(crearMensaje,nombresGUI[13]);
		principal.add(crearPregunta,nombresGUI[14]);
		principal.add(crearRespuesta,nombresGUI[15]);
		principal.add(listarPreguntas,nombresGUI[16]);
		principal.add(verRespuesta,nombresGUI[17]);

		cards.show(principal, nombresGUI[1]);
		add(principal, BorderLayout.CENTER);

		pack();

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Mail-UAM");
		setSize(800, 700);
		setResizable(false);
	}
	
	/**
	 * @return the nombresGUI
	 */
	public String[] getNombresGUI() {
		return nombresGUI;
	}
	
	
	/**
	 * @param nombresGUI the nombresGUI to set
	 */
	public void setNombresGUI(String[] nombresGUI) {
		this.nombresGUI = nombresGUI;
	}
	
	
	/**
	 * @return the cards
	 */
	public CardLayout getCards() {
		return cards;
	}
	
	
	/**
	 * @param cards the cards to set
	 */
	public void setCards(CardLayout cards) {
		this.cards = cards;
	}
	
	
	/**
	 * @return the principal
	 */
	public JPanel getPrincipal() {
		return principal;
	}
	
	
	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(JPanel principal) {
		this.principal = principal;
	}
	
	
	/**
	 * @return the app
	 */
	public MailUam getApp() {
		return modelo;
	}
	
	
	
	/**
	 * @return the login
	 */
	public GUILogin getLogin() {
		return login;
	}

	/**
	 * @return the menu
	 */
	public GUIMenu getMenu() {
		return menu;
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
	 * @return the menuGrupos
	 */
	public GUIMenuGrupo getMenuGrupos() {
		return menuGrupos;
	}

	/**
	 * @param menuGrupos the menuGrupos to set
	 */
	public void setMenuGrupos(GUIMenuGrupo menuGrupos) {
		this.menuGrupos = menuGrupos;
	}

	/**
	 * @return the perfil
	 */
	public GUIVerPerfil getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(GUIVerPerfil perfil) {
		this.perfil = perfil;
	}

	/**
	 * @return the mensajes
	 */
	public GUIMensaje getMensajes() {
		return mensajes;
	}

	/**
	 * @param mensajes the mensajes to set
	 */
	public void setMensajes(GUIMensaje mensajes) {
		this.mensajes = mensajes;
	}

	/**
	 * @return the verGrupos
	 */
	public GUIVerGrupo getVerGrupos() {
		return verGrupos;
	}

	/**
	 * @param verGrupos the verGrupos to set
	 */
	public void setVerGrupos(GUIVerGrupo verGrupos) {
		this.verGrupos = verGrupos;
	}

	/**
	 * @return the crearGrupo
	 */
	public GUICrearGrupo getCrearGrupo() {
		return crearGrupo;
	}

	/**
	 * @param crearGrupo the crearGrupo to set
	 */
	public void setCrearGrupo(GUICrearGrupo crearGrupo) {
		this.crearGrupo = crearGrupo;
	}

	/**
	 * @return the buscarGrupo
	 */
	public GUIBuscarGrupo getBuscarGrupo() {
		return buscarGrupo;
	}

	/**
	 * @param buscarGrupo the buscarGrupo to set
	 */
	public void setBuscarGrupo(GUIBuscarGrupo buscarGrupo) {
		this.buscarGrupo = buscarGrupo;
	}

	/**
	 * @return the verMensaje
	 */
	public GUIVerMensaje getVerMensaje() {
		return verMensaje;
	}

	/**
	 * @param verMensaje the verMensaje to set
	 */
	public void setVerMensaje(GUIVerMensaje verMensaje) {
		this.verMensaje = verMensaje;
	}

	/**
	 * @return the salirGrupo
	 */
	public GUISalirGrupo getSalirGrupo() {
		return salirGrupo;
	}

	/**
	 * @param salirGrupo the salirGrupo to set
	 */
	public void setSalirGrupo(GUISalirGrupo salirGrupo) {
		this.salirGrupo = salirGrupo;
	}

	/**
	 * @return the anadirParticipante
	 */
	public GUIAnadirParticipante getAnadirParticipante() {
		return anadirParticipante;
	}

	/**
	 * @param anadirParticipante the anadirParticipante to set
	 */
	public void setAnadirParticipante(GUIAnadirParticipante anadirParticipante) {
		this.anadirParticipante = anadirParticipante;
	}

	/**
	 * @return the buscarUsuarios
	 */
	public GUIBuscarUsuario getBuscarUsuarios() {
		return buscarUsuarios;
	}

	/**
	 * @param buscarUsuarios the buscarUsuarios to set
	 */
	public void setBuscarUsuarios(GUIBuscarUsuario buscarUsuarios) {
		this.buscarUsuarios = buscarUsuarios;
	}

	/**
	 * @return the crearMensaje
	 */
	public GUICrearMensaje getCrearMensaje() {
		return crearMensaje;
	}

	/**
	 * @param crearMensaje the crearMensaje to set
	 */
	public void setCrearMensaje(GUICrearMensaje crearMensaje) {
		this.crearMensaje = crearMensaje;
	}

	/**
	 * @return the crearPregunta
	 */
	public GUICrearPregunta getCrearPregunta() {
		return crearPregunta;
	}

	/**
	 * @param crearPregunta the crearPregunta to set
	 */
	public void setCrearPregunta(GUICrearPregunta crearPregunta) {
		this.crearPregunta = crearPregunta;
	}

	/**
	 * @return the crearRespuesta
	 */
	public GUICrearRespuesta getCrearRespuesta() {
		return crearRespuesta;
	}

	/**
	 * @param crearRespuesta the crearRespuesta to set
	 */
	public void setCrearRespuesta(GUICrearRespuesta crearRespuesta) {
		this.crearRespuesta = crearRespuesta;
	}

	/**
	 * @return the listarPreguntas
	 */
	public GUIListarPregunta getListarPreguntas() {
		return listarPreguntas;
	}

	/**
	 * @param listarPreguntas the listarPreguntas to set
	 */
	public void setListarPreguntas(GUIListarPregunta listarPreguntas) {
		this.listarPreguntas = listarPreguntas;
	}

	/**
	 * @return the verRespuesta
	 */
	public GUIVerRespuesta getVerRespuesta() {
		return verRespuesta;
	}

	/**
	 * @param verRespuesta the verRespuesta to set
	 */
	public void setVerRespuesta(GUIVerRespuesta verRespuesta) {
		this.verRespuesta = verRespuesta;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(GUILogin login) {
		this.login = login;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(GUIMenu menu) {
		this.menu = menu;
	}

	public void cambiarPanelLogin(){
		cards.show(principal, nombresGUI[1]);
	}
	
	public void cambiarPanelMenu(){
		cards.show(principal, nombresGUI[2]);
	}
	
	public void cambiarPanelMenuGrupos(){
		cards.show(principal, nombresGUI[3]);
	}

	public void cambiarPanelPerfil(){
		cards.show(principal, nombresGUI[4]);
	}
	
	public void cambiarPanelMenuMensajes(){
		cards.show(principal, nombresGUI[5]);
	}

	public void cambiarPanelVerGrupo(){
		cards.show(principal, nombresGUI[6]);
	}
	
	public void cambiarPanelCrearGrupo(){
		cards.show(principal, nombresGUI[7]);
	}
	
	public void cambiarPanelBuscarGrupo(){
		cards.show(principal, nombresGUI[8]);
	}
	
	public void cambiarPanelVerMensaje(){
		cards.show(principal, nombresGUI[9]);
	}
	
	public void cambiarPanelSalirGrupo(){
		cards.show(principal, nombresGUI[10]);
	}
	
	public void cambiarPanelAnadirParticipanteGrupo(){
		cards.show(principal, nombresGUI[11]);
	}
	
	public void cambiarPanelBuscarUsuarios(){
		cards.show(principal, nombresGUI[12]);
	}
	
	public void cambiarPanelCrearMensajes(){
		cards.show(principal, nombresGUI[13]);
	}
	
	public void cambiarPanelCrearPregunta(){
		cards.show(principal, nombresGUI[14]);
	}

	public void cambiarPanelCrearRespuesta(){
		cards.show(principal, nombresGUI[15]);
	}
	
	public void cambiarPanelListarPregunta(){
		cards.show(principal, nombresGUI[16]);
	}
	
	public void cambiarPanelVerRespuesta(){
		cards.show(principal, nombresGUI[17]);
	}
	
	
	
	
	
	
	
}
