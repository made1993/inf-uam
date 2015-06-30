package DAO;

/**
 * Interfaz para leer y escribir informacion de los objetos
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public interface IEntity {

	/**
	 * Devuelve el id de la entidad
	 * 
	 * @return id de la entidad
	 */
	public Long getId();

	/**
	 * Asigna un id a la entidad
	 * 
	 * @param id
	 *            a asignar a la entidad
	 */
	public void setId(Long id);

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos
	 */
	public String getType();

	/**
	 * Devuelve el valor de una propiedad de una entidad
	 * 
	 * @param property
	 *            de la entidad
	 * @return el objeto con el valor de la propiedad
	 */
	public Object getProperty(String property);

	/**
	 * Modifica el valor de una propiedad
	 * 
	 * @param property
	 *            propiedad a modifcar
	 * @param value
	 *            valor para asignar
	 */
	public void setProperty(String property, Object value);
}