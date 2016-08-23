package descriptores;

import tienda.Autor;
import DAO.*;

/**
 * Clase para trabajar con el tipo de dato Autor
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class AutorTypeDescriptor extends PersonaTypeDescriptor {
	
	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos
	 */
	@Override
	public String getName() {
		return "Autor";
	}
	
	/**
	 * Creador de nueva entidad
	 * 
	 * @return la entidad creada
	 */
	@Override
	public IEntity newEntity() {
		
		return new Autor ();
	}

}
