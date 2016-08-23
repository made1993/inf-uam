package tienda;

import DAO.*;

/**
 * Clase para implementar las funciones del id general a todas las entidades
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public abstract class AbstractEntity implements IEntity {

	private Long id;

	/**
	 * Devuelve el id de la entidad
	 * 
	 * @return id de la entidad
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Asigna un id a la entidad
	 * 
	 * @param id
	 *            a asignar a la entidad
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
