package DAO;

import java.util.Collection;

/**
 * Interfaz para crear un sistema de indexacion
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public interface IIndex {

	/**
	 * Funcion para relacionar value con key
	 * 
	 * @param key
	 *            para relacionar
	 * @param value
	 *            relacionado con key
	 */
	void add(Object key, Long value);

	/**
	 * Funcion para eliminar la relacion entre value y key
	 * 
	 * @param key
	 *            para eliminar su relacion
	 * @param value
	 *            para eliminar de key
	 */
	void delete(Object key, Long value);

	/**
	 * Lista los valores relacionados con key
	 * 
	 * @param key
	 *            para buscar
	 * @return coleccion con los valores relacionados con key
	 */
	Collection<Long> search(Object key);

	/**
	 * Devuelve la lista de valores desde from a to
	 * 
	 * @param from
	 *            valor desde el cual se va a buscar
	 * @param to
	 *            valor hasta el cual se va a buscar
	 * @return coleccion con los valores encontrados
	 */
	Collection<Long> search(Object from, Object to);
}
