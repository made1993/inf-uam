package descriptores;

import java.util.ArrayList;
import java.util.List;
import tienda.Libro;
import DAO.*;

/**
 * Clase para trabajar con el tipo de dato Libro
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class LibroTypeDescriptor implements ITypeDescriptor {

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos
	 */
	@Override
	public String getName() {
		return "Libro";
	}

	/**
	 * Devuelve las propiedades del tipo de datos Libro
	 * 
	 * @return lista de propiedades del tipo de datos
	 */
	@Override
	public List<String> getProperties() {
		List<String> pr = new ArrayList<String>();
		pr.add("id");
		pr.add("titulo");
		pr.add("autor");
		pr.add("editorial");
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
		if (property.equalsIgnoreCase("autor"))
			return Type.LONG;
		else if (property.equalsIgnoreCase("editorial"))
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

		return new Libro();
	}

}
