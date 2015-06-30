package descriptores;

import java.util.ArrayList;
import java.util.List;
import tienda.Pelicula;
import DAO.*;

/**
 * Clase para trabajar con el tipo de dato Pelicula
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class PeliculaTypeDescriptor implements ITypeDescriptor {

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos
	 */
	@Override
	public String getName() {
		return "Pelicula";
	}

	/**
	 * Devuelve las propiedades del tipo de datos Pelicula
	 * 
	 * @return lista de propiedades del tipo de datos
	 */
	@Override
	public List<String> getProperties() {
		List<String> pr = new ArrayList<String>();
		pr.add("id");
		pr.add("titulo");
		pr.add("director");
		pr.add("genero");
		return pr;
	}

	/**
	 * Indica el tipo de la propiedad
	 * 
	 * @param property
	 *            del tipo de dato
	 * @return tipo de dato de la propiedad
	 */
	@Override
	public Type getType(String property) {
		if (property.equalsIgnoreCase("director"))
			return Type.LONG;
		else if (property.equalsIgnoreCase("genero"))
			return Type.STRING;
		else if (property.equalsIgnoreCase("id"))
			return Type.LONG;
		else if (property.equalsIgnoreCase("titulo"))
			return Type.STRING;
		return null;
	}

	/**
	 * Creador de nueva entidad
	 * 
	 * @return la entidad creada
	 */
	@Override
	public IEntity newEntity() {

		return new Pelicula();
	}

}
