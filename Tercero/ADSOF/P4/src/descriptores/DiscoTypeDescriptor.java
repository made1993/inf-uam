package descriptores;

import java.util.ArrayList;
import java.util.List;
import tienda.Autor;
import DAO.*;

/**
 * Clase para trabajar con el tipo de dato Disco
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class DiscoTypeDescriptor implements ITypeDescriptor {

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos
	 */
	@Override
	public String getName() {
		return "Articulo";
	}

	/**
	 * Devuelve las propiedades del tipo de datos Disco
	 * 
	 * @return lista de propiedades del tipo de datos
	 */
	@Override
	public List<String> getProperties() {
		List<String> pr = new ArrayList<String>();
		pr.add("id");
		pr.add("titulo");
		pr.add("interprete");
		pr.add("year");
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
		if (property.equalsIgnoreCase("interprete"))
			return Type.LONG;
		else if (property.equalsIgnoreCase("year"))
			return Type.LONG;
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
		return new Autor();
	}

}
