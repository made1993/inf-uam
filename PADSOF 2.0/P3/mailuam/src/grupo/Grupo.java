package grupo;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import mailUam.MailUam;
import mensaje.MensajeGrupo;
import mensaje.MensajeUsuario;
import usuario.Usuario;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase Grupo
 */

public class Grupo implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String SUJETOMODERAR="Para moderar grupo: ";
	
	private int idGrupo;
	private String nombre;
	private boolean privado;
	private Usuario  moderador;
	private ArrayList<Usuario> listaUsuarios;
	private ArrayList<MensajeGrupo> listaMensajes; 
	private ArrayList<Grupo> subGrupos;
	
	
	/**
	 * Constructor de grupo
	 * @param idGrupo
	 * @param nombre
	 * @param privado
	 * @param moderador
	 */
	public Grupo(int idGrupo, String nombre, boolean privado, Usuario moderador) {
		this.idGrupo = idGrupo;
		this.nombre = nombre;
		this.privado = privado;
		this.moderador = moderador;
		this.listaUsuarios = new ArrayList<Usuario>();
		if(moderador!=null)
			listaUsuarios.add(moderador);
		this.listaMensajes = new ArrayList<MensajeGrupo>();
		this.subGrupos = new ArrayList<Grupo>();
	}
	
	
	/**
	 * @return the listaUsuarios
	 */
	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	
	/**
	 * @param listaUsuarios the listaUsuarios to set
	 */
	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	/**
	 * @return the listaMensajes
	 */
	public ArrayList<MensajeGrupo> getListaMensajes() {
		return listaMensajes;
	}
	
	/**
	 * @param listaMensajes the listaMensajes to set
	 */
	public void setListaMensajes(ArrayList<MensajeGrupo> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	
	
	
	/**
	 * @return the subGrupos
	 */
	public ArrayList<Grupo> getSubGrupos() {
		return subGrupos;
	}

	/**
	 * @param subGrupos the subGrupos to set
	 */
	public void setSubGrupos(ArrayList<Grupo> subGrupos) {
		this.subGrupos = subGrupos;
	}

	/**
	 * @return the idGrupo
	 */
	public int getIdGrupo() {
		return idGrupo;
	}
	
	/**
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return the privado
	 */
	public boolean isPrivado() {
		return privado;
	}
	
	/**
	 * @param privado the privado to set
	 */
	public void setPrivado(boolean privado) {
		this.privado = privado;
	}
	
	/**
	 * @return the moderador
	 */
	public Usuario getModerador() {
		return moderador;
	}
	
	/**
	 * @param moderador the moderador to set
	 */
	public void setModerador(Usuario moderador) {
		this.moderador = moderador;
	}
	
	/**
	 * anade un subgrupo a un grupo
	 * @param nombreSubgrupo
	 * @param privado
	 * @param moderador
	 * @param idGrupo
	 * @return true si se ha anadido, false si no
	 */
	public boolean addSubgrupo(String nombreSubgrupo,boolean privado, Usuario moderador,int idGrupo){
		for(Grupo g:subGrupos){
			if(g.nombre.equals(nombreSubgrupo))
				return false;
		}
		return subGrupos.add(new Grupo(idGrupo, nombreSubgrupo, privado, moderador));
	}
	

	/**
	 * Anade un subgrupo
	 * @param subgrupo
	 * @return true si anade el subgrupo false si no
	 */
	public boolean addSubGrupo(Grupo subgrupo) {
		return subGrupos.add(subgrupo);
	}
	
	/**
	 * Anade un usuario a un grupo
	 * @param usuario
	 * @return true si anade a un usuario false si no
	 */
	public boolean addUsuario(Usuario usuario){
		for(Usuario u: listaUsuarios)
			if(u.getCorreo().equals(usuario.getCorreo()))
				return false;
		return listaUsuarios.add(usuario);
	}
	
	/**
	 * Elimina a un usuario de un grupo
	 * @param usuario
	 * @return true si lo elimina false si no
	 */
	public boolean removeUsuario(Usuario usuario){
		for(Usuario u: listaUsuarios)
			if(u.getCorreo().equals(usuario.getCorreo()))
				return listaUsuarios.remove(u);
		return false;
	}
	
	/**
	 * Anade un mensaje a un grupo
	 * @param id
	 * @param cuerpo
	 * @param remitente
	 * @return true si anade el mensaje false si no
	 */
	public boolean addMensaje(int id,String cuerpo, Usuario remitente){
		if(moderador==null)
			return listaMensajes.add(new MensajeGrupo(id,cuerpo, Calendar.getInstance(),this ,remitente));
		else 
			return remitente.addMensajeBuzon(new MensajeUsuario(id,moderador,cuerpo,Calendar.getInstance(),SUJETOMODERAR+getNombre(),remitente));
	}
	/**
	 * Solo para cargarGrupo
	 * @param men
	 * @return true si lo crea, false si no puede
	 */
	public boolean addMensaje(MensajeGrupo men) {
			return listaMensajes.add(men);	
	}
	
	/**
	 * @return true si lo es false si no
	 */
	public boolean isGrupoEstudio() {
		return false;
	}
	
	/**
	 * Carga un grupo
	 * @param nombre del grupo que quiere cargar
	 * @return Grupo que ha cargado
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Grupo cargarGrupo(String nombre) throws IOException, ClassNotFoundException{
		if(nombre==null)
			return null;
		MailUam m=new MailUam();
		FileInputStream fis = new FileInputStream("aplicacion"+m.getBarra()+"grupo"+
										m.getBarra()+nombre+m.getBarra()+"datos.obj");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Grupo grupo =(Grupo)ois.readObject();
		ois.close();
		return  grupo;
	}
	
	/**
	 * Guarda un grupo en la aplicacion
	 * @return true si lo ha guardado, false si no lo ha podido guardar
	 * @throws IOException
	 */
	public boolean guardarGrupo() throws IOException{
		MailUam m=new MailUam();
		FileOutputStream fos = new FileOutputStream("aplicacion"+m.getBarra()+"grupo"+
									m.getBarra()+getNombre()+m.getBarra()+"datos.obj");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.close();
		m=null;
		return false;
	}

	/**
	 * comprueba si contiene usuario en la lista de usuarios
	 * @param usuario
	 * @return true si lo encuentra, false si no
	 */
	public boolean contieneUsuario(Usuario usuario) {
		for (Usuario u:listaUsuarios){
			if (u.equals(usuario)){
				return true;
			}
		}
		return false;
	}

	

	@Override
	public String toString() {
		String imp="";
		imp +="Nombre: "+getNombre()+"\n"; 
		
		imp+="Mensajes: \n";
		for(MensajeGrupo m:listaMensajes){
			imp+="\t"+m.getRemitente().getNombre()+":"+m.getCuerpo()+"\n";
		}
		
		return imp;
	}


	
	
	
}
