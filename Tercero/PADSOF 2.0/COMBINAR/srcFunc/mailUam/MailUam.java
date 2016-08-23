package mailUam;

import grupo.*;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import mensaje.*;
import usuario.Estudiante;
import usuario.Profesor;
import usuario.Usuario;
import usuario.Visitante;

public class MailUam {
	private static final String ficheroAlumnos = "ALUMNOS.TXT";
	private static final String ficheroProfesores = "PROFESORES.TXT";
	private static final String nmAplicacion = "aplicacion";
	private static final String nmUsuario = "usuario";
	private static final String nmGrupo = "grupo";
	private static final String nmDatos = "datos.obj";

	private static final String barraLinuxMAC = "/";
	private static final String barraWindows = "\\";

	private String barra;
	private ArrayList<Usuario> listaUsuarios;
	private ArrayList<Grupo> listaGrupos;
	private Usuario logged;

	/**
	 * Constructor mailUam
	 */
	public MailUam() {
		listaGrupos = new ArrayList<>();
		listaUsuarios = new ArrayList<>();
		if (getOS().equals("Windows 7")) {
			barra = barraWindows;
		} else {
			barra = barraLinuxMAC;
		}
		if (!existeDir(nmAplicacion)) {
			cargarDatos();
			crearDirectorios();
		} else {
			cargarGrupos();
			cargarUsuarios();
		}
		logged = null;
	}

	// GETTER Y SETTER

	/**
	 * @return the listaUsuarios
	 */
	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	/**
	 * @param listaUsuarios
	 *            the listaUsuarios to set
	 */
	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	/**
	 * @return the listaGrupos
	 */
	public ArrayList<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	/**
	 * @return the listaGrupos
	 */
	public ArrayList<Grupo> getListaTodosGrupos() {
		ArrayList<Grupo> lista = new ArrayList<>();
		for (Grupo g : listaGrupos) {
			if (!g.isGrupoColaborativo()) {
				if (g.getSubGrupos().size() > 0)
					lista.addAll(g.getTodosSubGrupos());
			}
		}
		return lista;
	}

	/**
	 * @param listaGrupos
	 *            the listaGrupos to set
	 */
	public void setListaGrupos(ArrayList<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	/**
	 * @param barra
	 *            the barra to set
	 */
	public void setBarra(String barra) {
		this.barra = barra;
	}

	/**
	 * @return the logged
	 */
	public Usuario getLogged() {
		return logged;
	}

	/**
	 * @param logged
	 *            the logged to set
	 */
	public void setLogged(Usuario logged) {
		this.logged = logged;
	}

	// METODOS NUEVOS

	/**
	 * Login de un usuario
	 * 
	 * @param correo
	 * @param password
	 * @return true si se ha podido loguear
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Usuario login(String correo, String password) {
		if (existeDir(nmAplicacion + barra + nmUsuario + barra + correo)
				&& !correo.equals("") && !password.equals("") && logged == null) {
			Usuario usr = buscarUsuario(correo);
			if (usr.getPassword().equals(password) && !usr.isVisitante()) {
				setLogged(usr);
				return usr;
			}
		}
		return null;
	}

	/**
	 * Login de visitante
	 * 
	 * @param correo
	 * @return true si se ha podido loguear
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Usuario loginVisitante(String correo) {
		cargarUsuarios();
		if (existeDir(nmAplicacion + barra + nmUsuario + barra + correo)
				&& !correo.equals("") && logged == null) {
			Usuario usr = buscarUsuario(correo);
			if (usr.isVisitante()) {
				setLogged(usr);
				return usr;
			}
			return null;
		}
		File f = new File(nmAplicacion + barra + nmUsuario + barra + correo);
		f.mkdir();
		Usuario usu = new Visitante("", "", correo, "");
		addUsuario(usu);
		guardarUsuario(usu);
		setLogged(usu);
		return usu;
	}

	/**
	 * Se desconecta el usuario del sistema
	 * 
	 * @param usuario
	 */
	public void logout() {
		if (logged != null)
			logged.setLastLogin(Calendar.getInstance());
		guardarUsuario();
		setLogged(null);
	}

	/**
	 * Crea un grupo en la aplicacion
	 * 
	 * @param nombre
	 *            nombre del grupo
	 * @return true si ha conseguido crear el grupo, false si no
	 */
	public boolean crearGrupoDir(String nombre) {
		return new File(nmAplicacion + barra + nmGrupo + barra + nombre)
				.mkdir();
	}

	/**
	 * Crea un subgrupo en la aplicacion
	 * 
	 * @param nombre
	 *            nombre del grupo
	 * @param privado
	 *            si es privado o no
	 * @param moderador
	 *            si el usuario es moderador, null si no quieres que el grupo
	 *            sea moderado
	 * @param profesor
	 *            si el grupo es de estudio le pasas el profesor de ese grupo
	 *            sino null
	 * @return true si ha conseguido crear el grupo, false si no
	 */
	public boolean crearSubGrupo(String nombre, boolean privado,
			Usuario moderador, Grupo grupo, Profesor profesor) {
		if (buscarGrupo(nombre) != null)
			return false;
		if (profesor == null) {
			if (grupo.addSubGrupo(new Grupo(grupo.getIdGrupo(), nombre,
					privado, moderador))) {
				guardarGrupos();
				return true;
			}
			return false;
		} else {
			if (grupo.addSubGrupo(new GrupoEstudio(grupo.getIdGrupo(), nombre,
					privado, moderador, profesor))) {
				guardarGrupos();
				return true;
			}
			return false;
		}
	}

	/**
	 * Envia un mensaje a un usuario
	 * 
	 * @param usuario
	 * @param cuerpo
	 * @param sujeto
	 * @return treu si se ha podido enviar el mensaje, false si no
	 */
	public boolean enviarMensaje(String correo, String cuerpo, String sujeto) {
		Usuario usu = buscarUsuario(correo);
		if (usu == null || logged == null) {// TODO EXcepcion, buzon de entrada
											// y enviados
			return false;
		}
		cargarUsuarios();
		return usu.addMensajeBuzon(new MensajeUsuario(0, usu, cuerpo, sujeto,
				logged)) && guardarUsuario(usu) && guardarUsuario();
	}

	/**
	 * Entra un usuario en un grupo
	 * 
	 * @param grupo
	 * @param usuario
	 * @return true si el usuario puede false si no
	 */
	public boolean unirseGrupo(String nombreGrupo, String correo) {
		cargarSistema();
		Grupo g = buscarGrupo(nombreGrupo);
		Usuario usu = buscarUsuario(correo);

		if (g == null)
			return false;
		if (correo == null) {
			usu = logged;
		}
		if (usu != null) {
			if (g.isPrivado())
				return false;
			if (g.isGrupoEstudio())
				return false;
			return g.addUsuario(usu) && guardarSistema();
		}
		return false;
	}

	private void cargarSistema() {
		cargarUsuarios();
		cargarGrupos();
	}

	private boolean guardarSistema() {
		guardarGrupos();
		return guardarUsuarios();
	}

	public boolean guardarUsuarios() {
		for (Usuario usuario : listaUsuarios)
			if (!guardarUsuario(usuario))
				return false;
		return true;
	}

	/**
	 * Buscar un usuario que tenga un cierto nombre
	 * 
	 * @param nombre
	 * @return lista de usuarios con el nombre identico
	 */
	public ArrayList<Usuario> buscarUsuarios(String nombre) {
		System.out.println("correo es:"+nombre);
		int i=0;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		for (Usuario u : listaUsuarios) {
			if (StringSimilarity.similarity(u.getCorreo(),nombre)>=0.5) {
				System.out.println(i);
				usuarios.add(u);
			}

			i++;
		}
		System.out.println(i);
		return usuarios;
	}

	/**
	 * Busca un usuario que tenga un cierto correo
	 * 
	 * @param correo
	 * @return Usuario que tenga el correo identico
	 */
	public Usuario buscarUsuario(String correo) {
		for (Usuario u : listaUsuarios) {
			if (u.getCorreo().equals(correo)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Busca un grupo pasandole un nombre para el grupo
	 * 
	 * @param nombreGrupo
	 * @return lista de grupos con el nombre nombreGrupo
	 */
	public ArrayList<Grupo> buscarGrupoLista(String nombreGrupo) {
		ArrayList<Grupo> grupo = new ArrayList<Grupo>();
		cargarGrupos();
		for (Grupo g : listaGrupos) {
			if (StringSimilarity.similarity(nombreGrupo, g.getNombre()) >= 0.5
					|| g.getNombre().equalsIgnoreCase(nombreGrupo)) {
				grupo.add(g);
			}
			grupo.addAll(g.buscarGrupoLista(nombreGrupo));
		}
		return grupo;
	}

	/**
	 * Busca un grupo pasandole un nombre para el grupo
	 * 
	 * @param nombreGrupo
	 * @return lista de grupos con el nombre nombreGrupo
	 */
	public Grupo buscarGrupo(String nombreGrupo) {
		for (Grupo g : listaGrupos) {
			if (g.getNombre().equals(nombreGrupo)) {
				return g;
			}
			if (!g.isGrupoColaborativo()) {
				Grupo sg = g.buscarGrupo(nombreGrupo);
				if (sg != null) {
					return sg;
				}
			}
		}
		return null;
	}

	/**
	 * Sirve para cargar los datos de los usuario de la aplicacion
	 * 
	 * @param fAlumnos
	 *            contiene el fichero de los alumnos
	 * @param fPprofesores
	 *            contiene el fichero de los profesoroes
	 * @return true si los carga, false si no
	 */
	public boolean cargarDatos() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(ficheroAlumnos);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			String[] ss, ss2, ss3;
			String correo, password, nombre, apellido;
			while ((linea = br.readLine()) != null) {
				// maria.martin@ddm.es:mamnds455
				if (linea.length() > 1) {
					ss = linea.split(":");
					ss2 = ss[0].split("@");
					ss3 = ss2[0].replace(".", " ").split(" ");// Si haces split
																// con '.' no
																// funciona
					correo = ss[0];
					password = ss[1];
					nombre = ss3[0];
					apellido = ss3[1];
					this.listaUsuarios.add(new Estudiante(nombre, apellido,
							correo, password));
				}
			}

			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(ficheroProfesores);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			while ((linea = br.readLine()) != null) {
				// maria.martin@ddm.es:mamnds455
				if (linea.length() > 1) {
					ss = linea.split(":");
					ss2 = ss[0].split("@");
					ss3 = ss2[0].replace(".", " ").split(" ");// Si haces split
																// con '.' no
																// funciona
					correo = ss[0];
					password = ss[1];
					nombre = ss3[0];
					apellido = ss3[1];
					this.listaUsuarios.add(new Profesor(nombre, apellido,
							correo, password));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * @return el sistema operativo del ordenador actual
	 */
	public String getOS() {
		return System.getProperty("os.name");
	}

	/**
	 * Crea los directorios de aplicacion, usuarios y grupos
	 * 
	 * @return true si los puede crear, false si no ha podido
	 */
	public boolean crearDirectorios() {
		File f = new File(nmAplicacion);
		f.mkdir();
		File fg = new File(nmAplicacion + barra + nmGrupo);
		fg.mkdir();
		File fu = new File(nmAplicacion + barra + nmUsuario);
		fu.mkdir();
		for (Usuario u : listaUsuarios) {
			File fusr = new File(fu.getPath() + barra + u.getCorreo());
			fusr.mkdir();
			guardarUsuario(u);
		}
		for (Grupo g : listaGrupos) {// Sobra TODO
			File fgr = new File(fg.getPath() + barra + g.getNombre());
			fgr.mkdir();
			guardarGrupo(g);
		}
		return true;

	}

	/**
	 * @return la barra que utiliza el sistema operativo
	 */
	public String getBarra() {
		return barra;
	}

	/**
	 * anade un grupo a la aplicacion
	 * 
	 * @param grupo
	 * @return true si ha podido false si no
	 */
	public boolean addGrupo(Grupo grupo) {
		return listaGrupos.add(grupo);
	}

	/**
	 * Anade un usuario a la aplicacion
	 * 
	 * @param u
	 * @return true si lo anade, false si no
	 */
	public boolean addUsuario(Usuario u) {
		return listaUsuarios.add(u);
	}

	/**
	 * anade un usuario a un grupo
	 * 
	 * @param usuario
	 * @param grupo
	 * @return true si lo ha anadido, false si no
	 */
	public boolean addUsuarioGrupo(Usuario usuario, Grupo grupo) {
		if (!grupo.contieneUsuario(usuario))
			return grupo.addUsuario(usuario);
		return false;
	}

	/**
	 * Anade un mensaje a un grupo
	 * 
	 * @param mensaje
	 * @param grupo
	 * @return true si lo anade, false si no
	 */
	public boolean addMensajeGrupo(MensajeGrupo mensaje, Grupo grupo) {
		cargarGrupo(grupo.getNombre());
		return grupo.addMensaje(mensaje) && guardarGrupo(grupo);
	}

	/**
	 * Comprueba si existe un directorio
	 * 
	 * @param dir
	 * @return true si existe el directorio, false si no
	 */
	public boolean existeDir(String dir) {
		if (dir == null)
			return false;
		File folder = new File(dir);
		if (folder.exists())
			return true;
		return false;
	}

	/**
	 * Imprime los usuarios en un direcorio que coincida con usr
	 * 
	 * @param usr
	 *            usuario que tiene que coincidir
	 * @throws IOException
	 */
	public void buscarUsuarioDir(String usr) throws IOException {// TODO
																	// Cambiara
																	// String
		Path dir = Paths.get(nmAplicacion + barra + nmUsuario);

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*"
				+ usr + "*")) {
			for (Path file : stream) {
				System.out.println(file);
			}
		}
	}

	/**
	 * Imprime los grupos que coincidan en un directorio
	 * 
	 * @param grupo
	 * @throws IOException
	 */
	public void buscarGrupoDir(String grupo) throws IOException {// TODO cambiar
																	// a string
		Path dir = Paths.get(nmAplicacion + barra + nmGrupo);

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*"
				+ grupo + "*")) {
			for (Path file : stream) {
				System.out.println(file);
			}
		}
	}

	/**
	 * carga un usuario a la aplicacion
	 * 
	 * @param correo
	 *            del usuario para cargar
	 * @return Usuario que quiere cargar.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Usuario cargarUsuario(String correo) {
		Usuario usr = null;
		if (correo == null)
			return null;
		try {
			
			FileInputStream fis = new FileInputStream(nmAplicacion + barra
					+ nmUsuario + barra + correo + barra + nmDatos);
			ObjectInputStream ois = new ObjectInputStream(fis);
			usr = (Usuario) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("ERROR AL CARGAR");
			return null;
		} finally {
			if(listaUsuarios.contains(usr)){
				listaUsuarios.remove(usr);
				System.out.println("se esta borrando");
			}
			this.listaUsuarios.add(usr);
		}
		return usr;
	}

	/**
	 * Guarda un usuario en la apliacion
	 * 
	 * @param usuario
	 * @return
	 */
	public boolean guardarUsuario() {
		FileOutputStream fos;
		if (logged == null)
			return false;
		try {
			fos = new FileOutputStream(nmAplicacion + barra + nmUsuario + barra
					+ logged.getCorreo() + barra + nmDatos);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(logged);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean guardarUsuario(Usuario usuario) {
		FileOutputStream fos;
		if (usuario == null)
			return false;
		try {
			fos = new FileOutputStream(nmAplicacion + barra + nmUsuario + barra
					+ usuario.getCorreo() + barra + nmDatos);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(usuario);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Carga un grupo
	 * 
	 * @param nombre
	 *            del grupo que quiere cargar
	 * @return Grupo que ha cargado
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public boolean cargarGrupo(String nombre) {
		Grupo grupo = null;
		if (nombre == null)
			return false;

		try {
			FileInputStream fis = new FileInputStream(nmAplicacion + barra
					+ nmGrupo + barra + nombre + barra + nmDatos);
			ObjectInputStream ois = new ObjectInputStream(fis);

			grupo = (Grupo) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
			return false;
		} finally {
			if (buscarListaGrupos(nombre)) {// TODO revisar
				if (!borrarGrupo(nombre))
					return false;// TODO igual excepocion
			}
			listaGrupos.add(grupo);
		}
		return true;
	}

	/**
	 * Borra un grupo del array listaGrupo
	 * 
	 * @param nombre
	 * @return
	 */
	public boolean borrarGrupo(String nombre) { // TODO igual private
		for (Grupo g : listaGrupos) {
			if (g.getNombre().equals(nombre))
				return listaGrupos.remove(g);
		}
		return false;
	}

	public boolean borrarMensajeUsuario(MensajeUsuario mensaje) {
		cargarUsuarios();
		if (logged == null)
			return false;
		return logged.removeMensaje(mensaje) && guardarUsuario();
	}

	/**
	 * Guarda un grupo en el fichero
	 * 
	 * @param grupo
	 * @return
	 */
	public boolean guardarGrupo(Grupo grupo) {
		if (grupo == null)
			return false;
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(nmAplicacion + barra + nmGrupo + barra
					+ grupo.getNombre() + barra + nmDatos);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeObject(grupo);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void guardarGrupos() {
		for (Grupo g : listaGrupos)
			guardarGrupo(g);
	}

	/**
	 * 
	 */
	public void cargarGrupos() {
		listaGrupos.clear();
		File f = new File(nmAplicacion + barra + nmGrupo);
		for (String g : f.list()) {
			cargarGrupo(g);
		}
	}

	/**
	 * 
	 */
	public void cargarUsuarios() {
		listaUsuarios.clear();
		File f = new File(nmAplicacion + barra + nmUsuario);
		for (String u : f.list()) {
			cargarUsuario(u);
		}
	}

	public boolean buscarListaGrupos(String nombre) {
		for (Grupo g : listaGrupos) {
			if (g.getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String imp = "";
		if (listaUsuarios.size() > 0) {
			for (Usuario u : listaUsuarios) {
				imp += u.getNombre() + " ,";
			}
			imp += "\n";
		}
		if (listaGrupos.size() > 0) {
			for (Grupo g : listaGrupos) {
				imp += g.toString() + "\n";
			}
		}
		return imp;
	}

	public void actualizarLogged() {
		String correo= logged.getCorreo();
		String password= logged.getPassword();
		if(logged== null){
			return;
		}
		if(logged.isVisitante()){
			logged=null;
			loginVisitante(correo);
		}
		else{
			logged=null;
			login(correo, password);
		}
		
		return;
		
	}

}