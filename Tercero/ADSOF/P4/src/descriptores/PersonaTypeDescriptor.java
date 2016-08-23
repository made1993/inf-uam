package descriptores;

import java.util.ArrayList;
import java.util.List;
import DAO.ITypeDescriptor;

/**
 * Clase para trabajar con los tipos de dato que heredan de Persona
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public abstract class PersonaTypeDescriptor implements ITypeDescriptor {

	/**
	 * Devuelve las propiedades del tipo de datos Persona (Autpr, Director)
	 * 
	 * @return lista de propiedades del tipo de datos
	 */
	@Override
	public List<String> getProperties() {
		List<String> propiedades = new ArrayList<>();

		propiedades.add("nombre");
		propiedades.add("apellidos");
		propiedades.add("id");

		return propiedades;
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
		if (property.equalsIgnoreCase("nombre"))
			return Type.STRING;
		else if (property.equalsIgnoreCase("apellidos"))
			return Type.STRING;
		else if (property.equals("id"))
			return Type.LONG;

		return null;
	}

}
