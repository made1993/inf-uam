package tienda;

/**
 * Clase Persona (Autor, Director)
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */

public abstract class Persona extends AbstractEntity {

	private String nombre;
	private String apellidos;

	/**
	 * Constructor de la clase persona
	 */
	public Persona() {

	}

	/**
	 * Devuelve el nombre de una persona
	 * 
	 * @return el nombre de una persona
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna un nombre a una persona
	 * 
	 * @param nombre
	 *            a asignar a la persona
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve los apellidos de una persona
	 * 
	 * @return los apellidos de una persona
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Asigna apellidos a una persona
	 * 
	 * @param apellidos
	 *            a asignar a la persona
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Devuelve el valor de una propiedad de una entidad
	 * 
	 * @param property
	 *            de la entidad
	 * @return el objeto con el valor de la propiedad
	 */
	@Override
	public Object getProperty(String property) {
		if (property.equalsIgnoreCase("nombre"))
			return this.getNombre();
		else if (property.equalsIgnoreCase("apellidos"))
			return this.getApellidos();
		else if (property.equals("id"))
			return this.getId();
		return null;
	}

	/**
	 * Modifica el valor de una propiedad
	 * 
	 * @param property
	 *            propiedad a modifcar
	 * @param value
	 *            valor para asignar
	 */
	@Override
	public void setProperty(String property, Object value) {
		if (property.equals("nombre"))
			this.setNombre((String) value);
		else if (property.equals("apellidos"))
			this.setApellidos((String) value);
		else if (property.equals("id"))
			this.setId((Long) value);
	}

}
