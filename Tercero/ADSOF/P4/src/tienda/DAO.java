package tienda;

import java.util.*;
import DAO.*;

/**
 * Clase para implementar la interfaz IDAO
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class DAO implements IDAO {

	private List<Table> tablas;

	/**
	 * Constructor de la clase DAO
	 */
	public DAO() {
		this.tablas = new ArrayList<Table>();
	}

	/**
	 * Registra un nuevo tipo en la base de datos
	 * 
	 * @param t
	 *            tipo de dato a registrar
	 */
	@Override
	public void registerType(ITypeDescriptor t) {

		this.tablas.add(new Table(t));
	}

	/**
	 * Lee el objeto de la base de datos, o de cache, si se encuentra en memoria
	 * 
	 * @param type
	 *            tipo de dato a leer de la base de datos
	 * @param id
	 *            id del dato que se quiere leer
	 * @return la entidad leida
	 */
	@Override
	public IEntity getEntity(String type, Long id) {

		for (Table tab : tablas) {
			if (tab.getType().getName().equals(type)) {
				return tab.getEntity(id);
			}
		}

		return null;
	}

	/**
	 * Actualiza la entidad, asignandole un ID si este era null, y devuelve el
	 * ID
	 * 
	 * @param e
	 *            entidad que se quiere actualizar
	 * @return id de la entidad
	 */
	@Override
	public long updateEntity(IEntity e) {

		for (Table tab : tablas) {
			if (tab.getType().getName().equals(e.getType())) {
				return tab.updateEntity(e);
			}
		}

		return -1L;
	}

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
	@Override
	public void delete(IEntity e) throws InstantiationException,
			IllegalAccessException {

		for (Table tab : tablas) {
			if (tab.getType().getName().equals(e.getType())) {
				tab.delete(e);
			}
		}

	}

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
	@Override
	public Collection<Long> search(String type, String property, Object value) {

		for (Table tab : tablas) {
			if (tab.getType().getName().equals(type)) {
				return tab.search(property, value);
			}
		}

		return Collections.emptySet();
	}

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
	@Override
	public Collection<Long> search(String type, String property, Object from,
			Object to) {

		for (Table tab : tablas) {
			if (tab.getType().getName().equals(type)) {
				return tab.search(property, from, to);
			}
		}

		return Collections.emptySet();
	}

}
