package descriptores;

import tienda.Director;
import DAO.IEntity;

/**
 * Clase para trabajar con el tipo de dato Director
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class DirectorTypeDescriptor extends PersonaTypeDescriptor {

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos
	 */
	@Override
	public String getName() {
		return "Director";
	}

	/**
	 * Creador de nueva entidad
	 * 
	 * @return la entidad creada
	 */
	@Override
	public IEntity newEntity() {

		return new Director();
	}

}
