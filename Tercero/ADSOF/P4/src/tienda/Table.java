package tienda;

import java.util.*;
import DAO.*;

/**
 * Clase para implementar la interfaz ITable
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class Table implements ITable {

	private Map<Long, Map<String, Object>> m;
	private ITypeDescriptor type;
	private static Long contador = 0L;
	private Map<String, IIndex> indices;

	/**
	 * Constructor de la clase Table
	 * 
	 * @param td
	 *            tipo de dato de la tabla
	 */
	public Table(ITypeDescriptor td) {
		this.m = new HashMap<Long, Map<String, Object>>();
		this.type = td;
		this.indices = new HashMap<String, IIndex>();
	}

	/**
	 * Devuelve el tipo de dato para el que se ha creado la tabla
	 * 
	 * @return tipo de dato de la tabla
	 */
	@Override
	public ITypeDescriptor getType() {
		return this.type;
	}

	/**
	 * Lee el objeto de la tabla y lo convierte en una Entity
	 * 
	 * @param id
	 *            del objeto de la tabla
	 * @return entidad convertida a partir del id
	 */
	@Override
	public IEntity getEntity(Long id) {

		IEntity entidad = this.getType().newEntity();
		List<String> propiedades = this.getType().getProperties();

		for (String p : propiedades) {
			entidad.setProperty(p, m.get(id).get(p));
		}

		return entidad;
	}

	/**
	 * Actualiza la entidad y los indices, asignandole un ID si este era null, y
	 * devuelve el ID
	 * 
	 * @param e
	 *            entidad a actualizar
	 * @return id de la entidad actualizada
	 */

	@Override
	public long updateEntity(IEntity e) {

		Map<String, Object> n_map = new HashMap<String, Object>();
		List<String> propiedades = this.type.getProperties();
		String propiedad;
		int i;
		Long cont_aux = contador;

		if (e.getId() != null) {
			this.m.remove(e.getId());
		}

		e.setId(contador);

		for (i = 0; i < propiedades.size(); i++) {

			Index ind_aux = new Index();
			propiedad = propiedades.get(i);
			n_map.put(propiedad, e.getProperty(propiedad));
			ind_aux.add(e.getProperty(propiedad), contador);
			indices.put(propiedad, ind_aux);
		}
		this.m.put(e.getId(), n_map);
		contador++;

		return cont_aux;

	}

	/**
	 * Elimina la entidad, si existe, actualizando los indices si es necesario
	 * 
	 * @param e
	 *            entidad a eliminar
	 */
	@Override
	public void delete(IEntity e) {

		List<String> propiedades = this.type.getProperties();

		for (String prop : propiedades) {
			this.indices.get(prop).delete(e.getProperty(prop), e.getId());
		}

		this.m.remove(e.getId());
	}

	/**
	 * Busca las entidades que tengan el valor indicado
	 * 
	 * @param property
	 *            propiedad a buscar
	 * @param value
	 *            valor de la propiedad que se quiere buscar
	 * @return coleccion con los valores encontrados
	 */
	@Override
	public Collection<Long> search(String property, Object value) {

		if (property == null || value == null) {
			return null;
		}

		return indices.get(property).search(value);
	}

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
	@Override
	public Collection<Long> search(String property, Object from, Object to) {

		return indices.get(property).search(from, to);
	}

}
