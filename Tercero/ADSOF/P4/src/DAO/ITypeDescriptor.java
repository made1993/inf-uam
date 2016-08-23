package DAO;

import java.util.List;

/**
 * Interfaz para trabajar con un unico tipo de dato
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public interface ITypeDescriptor {

	public enum Type {
		LONG, DOUBLE, STRING;
	}

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos
	 */
	public String getName();

	/**
	 * Devuelve las propiedades del tipo de datos
	 * 
	 * @return lista de propiedades del tipo de datos
	 */
	public List<String> getProperties();

	/**
	 * Indica el tipo de la propiedad
	 * 
	 * @param property
	 *            del tipo de dato
	 * @return tipo de dato de la propiedad
	 */
	public Type getType(String property);

	/**
	 * Creador de nueva entidad
	 * 
	 * @return la entidad creada
	 */
	public IEntity newEntity();

}
