package tienda;

import descriptores.DiscoTypeDescriptor;

/**
 * Clase Disco
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class Disco extends Articulo {

	private Long interprete;
	private Long year;
	private static DiscoTypeDescriptor disco = new DiscoTypeDescriptor();

	/**
	 * Constructor de la clase Disco
	 */
	public Disco() {
	}

	/**
	 * Devuelve el interprete de un disco
	 * 
	 * @return el interprete de un disco
	 */
	public Long getInterprete() {
		return interprete;
	}

	/**
	 * Asigna un interprete a un disco
	 * 
	 * @param interprete
	 *            a asignar al disco
	 */
	public void setInterprete(Long interprete) {
		this.interprete = interprete;
	}

	/**
	 * Devuelve el anyo de un disco
	 * 
	 * @return el anyo del disco
	 */
	public Long getYear() {
		return year;
	}

	/**
	 * Asigna un anyo a un disco
	 * 
	 * @param year
	 *            anyo a asignar al disco
	 */
	public void setYear(Long year) {
		this.year = year;
	}

	/**
	 * Devuelve el nombre del tipo de datos
	 * 
	 * @return nombre del tipo de datos disco
	 */
	@Override
	public String getType() {
		return "Disco";
	}

	/**
	 * Metodo para obtener el typo de dato Disco
	 * 
	 * @return tipo de dato disco
	 */
	public static DiscoTypeDescriptor getDescriptor() {
		return disco;
	}

	/**
	 * Devuelve el valor de una propiedad de una entidad
	 * 
	 * @param property
	 *            de la entidad
	 * @return el objeto con el valor de la propiedad
	 */
	@Override
	public Object getProperty(String property) {
		if (property.equalsIgnoreCase("interprete"))
			return interprete;
		else if (property.equalsIgnoreCase("year"))
			return year;
		else if (property.equalsIgnoreCase("id"))
			return getId();
		else if (property.equalsIgnoreCase("titulo"))
			return getTitulo();

		return null;
	}

	/**
	 * Modifica el valor de una propiedad
	 * 
	 * @param property
	 *            propiedad a modifcar
	 * @param value
	 *            valor para asignar
	 */
	@Override
	public void setProperty(String property, Object value) {
		if (property.equalsIgnoreCase("interprete"))
			interprete = (Long) value;
		else if (property.equalsIgnoreCase("year"))
			year = (Long) value;
		else if (property.equalsIgnoreCase("id"))
			setId((Long) value);
		else if (property.equalsIgnoreCase("titulo"))
			setTitulo((String) value);

	}
}
