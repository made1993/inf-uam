package control;

import gui.*;
import usuario.*;

import java.awt.event.*;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import org.w3c.dom.ls.LSInput;

import mailUam.MailUam;

public class ControlListarPregunta implements ActionListener {
	

	private MailUam modelo;
	private Ventana v;
	
	public ControlListarPregunta(Ventana v, MailUam modelo) {
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
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		System.out.println("Activando controlador: "+getClass());
		GUIListarPregunta listarPreguntas = v.getListarPreguntas();
		if(source.equals(listarPreguntas.getBotonMensajes())){
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		}else if(source.equals(listarPreguntas.getBotonGrupos())){
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		}else if(source.equals(listarPreguntas.getBotonVerPrefil())){
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		}else if(source.equals(listarPreguntas.getBotonSalir())){
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		}else if(source.equals(listarPreguntas.getButtonCrearPregunta())){
			System.out.println("crear pregunta");
			if(getModelo().getLogged() instanceof Profesor){
				v.getCrearPregunta().setValores(listarPreguntas.getGrupo());
				v.cambiarPanelCrearPregunta();
			}else{
				JOptionPane.showMessageDialog(listarPreguntas,
						"Debe ser Profesor", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}else if(source.equals(listarPreguntas.getButtonCrearRespuesta())){
			System.out.println("crear respuesta");
			if(getModelo().getLogged() instanceof Estudiante){
				for(JRadioButton b:listarPreguntas.getListaBotones()){
					if(b.isSelected()){
						v.getCrearRespuesta().setValores(listarPreguntas.getGrupo().getListaPreguntas()
								.get(Integer.decode(b.getText().split("a")[1])-1));
						v.cambiarPanelCrearRespuesta();
						return;
					}
				}
			}else{
				JOptionPane.showMessageDialog(listarPreguntas,
						"Debe ser Estudiante", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}else if(source.equals(listarPreguntas.getButtonVerRespuestas())){
			System.out.println("ver respuesta");
			getModelo().cargarGrupos();
			System.out.println(listarPreguntas.getGrupo().getListaPreguntas().get(0).getListaRespuestas());
			if(getModelo().getLogged() instanceof Profesor){
				for(JRadioButton b:listarPreguntas.getListaBotones()){
					if(b.isSelected()){
						getModelo().cargarGrupos();
						v.getVerRespuesta().setValores(listarPreguntas.getGrupo().
								getListaPreguntas().get(Integer.decode(b.getText().split("a")[1]) -1));
						v.cambiarPanelVerRespuesta();
						return;
					}
				}
				
			}else{
				JOptionPane.showMessageDialog(listarPreguntas,
						"Debe ser Profesor", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
	}

}
