package DAO;

import java.util.*;

/**
 * Interfaz para operar con los tipos de datos
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public interface IDAO {

	/**
	 * Registra un nuevo tipo en la base de datos
	 * 
	 * @param t
	 *            tipo de dato a registrar
	 */
	public void registerType(ITypeDescriptor t);

	/**
	 * Lee el objeto de la base de datos, o de cache, si se encuentra en memoria
	 * 
	 * @param type
	 *            tipo de dato a leer de la base de datos
	 * @param id
	 *            id del dato que se quiere leer
	 * @return la entidad leida
	 */
	public IEntity getEntity(String type, Long id);

	/**
	 * Actualiza la entidad, asignandole un ID si este era null, y devuelve el
	 * ID
	 * 
	 * @param e
	 *            entidad que se quiere actualizar
	 * @return id de la entidad
	 */
	public long updateEntity(IEntity e);

	/**
	 * Elimina la entidad
	 * 
	 * @param e
	 *            entidad a eliminar
	 * @throws InstantiationException
	 *             excepcion si no se instancia
	 * @throws IllegalAccessException
	 *             excepcion si se accede a memoria a la que no hay permiso
	 */
	public void delete(IEntity e) throws InstantiationException,
			IllegalAccessException;

	/**
	 * Busca las entidades que tengan el valor indicado
	 * 
	 * @param type
	 *            tipo de la entidad que se quiere buscar
	 * @param property
	 *            propiedad que se quiere buscar
	 * @param value
	 *            valor de la propiedad
	 * @return coleccion de ids de los datos encontrados, vacia si no se
	 *         encuentra nada
	 */
	public Collection<Long> search(String type, String property, Object value);

	/**
	 * Devuelve la lista de entidades que tienen la propiedad property, entre el
	 * valor from y to
	 * 
	 * @param type
	 *            tipo de la entidad que se quiere buscar
	 * @param property
	 *            propiedad que se quiere buscar
	 * @param from
	 *            valor desde el cual se quiere empezar a buscar
	 * @param to
	 *            valor hasta el que se quiere buscar
	 * @return coleccion de ids de los datos encontrados, vacia si no se
	 *         encuentra nada
	 */
	public Collection<Long> search(String type, String property, Object from,
			Object to);
}