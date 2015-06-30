package DAO;

import java.util.*;

/**
 * Interfaz para trabajar con un unico tipo de dato
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public interface ITable {

	/**
	 * Devuelve el tipo de dato para el que se ha creado la tabla
	 * 
	 * @return tipo de dato de la tabla
	 */
	public ITypeDescriptor getType();

	/**
	 * Lee el objeto de la tabla y lo convierte en una Entity
	 * 
	 * @param id
	 *            del objeto de la tabla
	 * @return entidad convertida a partir del id
	 */
	public IEntity getEntity(Long id);

	/**
	 * Actualiza la entidad y los indices, asignandole un ID si este era null, y
	 * devuelve el ID
	 * 
	 * @param e
	 *            entidad a actualizar
	 * @return id de la entidad actualizada
	 */
	public long updateEntity(IEntity e);

	/**
	 * Elimina la entidad, si existe, actualizando los indices si es necesario
	 * 
	 * @param e
	 *            entidad a eliminar
	 */
	public void delete(IEntity e);

	/**
	 * Busca las entidades que tengan el valor indicado
	 * 
	 * @param property
	 *            propiedad a buscar
	 * @param value
	 *            valor de la propiedad que se quiere buscar
	 * @return coleccion con los valores encontrados
	 */
	public Collection<Long> search(String property, Object value);

	/**
	 * Devuelve la lista de entidades que tienen la propiedad property, entre el
	 * valor from y to
	 * 
	 * @param property
	 *            propiedad a buscar
	 * @param from
	 *            valor desde el cual se va a buscar
	 * @param to
	 *            valor hasta el que se va a buscar
	 * @return coleccion con los valores encontrados
	 */
	public Collection<Long> search(String property, Object from, Object to);
}
