package tests;

import tienda.*;
import java.util.*;
import descriptores.*;

/**
 * Prueba de las clases de la tienda, TypeDescriptor, Entity, Index, Table y DAO
 * 
 * @author Alejandro Antonio Martin Almansa
 * @author Mario Garcia Roque
 *
 */
public class TestDAO {

	public static void main(String[] args) {

		DAO dao = new DAO();
		Collection<Long> coleccion;

		try {

			PeliculaTypeDescriptor tdpeli = Pelicula.getDescriptor();
			DirectorTypeDescriptor tddirector = Director.getDescriptor();

			Pelicula p = (Pelicula) tdpeli.newEntity();
			Director d = (Director) tddirector.newEntity();

			p.setTitulo("TLOTR");
			p.setProperty("genero", "Ciencia F");

			d.setNombre("nombre");
			d.setApellidos("apellidos");

			dao.registerType(tddirector);
			dao.updateEntity(d);

			p.setProperty("director", d.getId());

			dao.registerType(tdpeli);
			dao.updateEntity(p);

			System.out
					.println("Prueba getEntity, registerType y updateEntity: ");
			System.out.println("Salida esperada: ");
			System.out
					.println("	Pelicula: id=1, titulo=TLOTR, genero=CienciaF, Director=0");
			System.out
					.println("	Director: id=0, nombre=nombre, apellidos=apellidos");
			System.out.println("Pelicula: "
					+ dao.getEntity("Pelicula", p.getId()));
			System.out.println("Director de la pelicula: "
					+ dao.getEntity("Director", d.getId()));

			coleccion = dao.search("Director", "nombre", "nombre");
			if (coleccion.isEmpty()) {
				System.out.println("Busqueda de nombre sin exito");
			}

			System.out.println("Prueba search: (Salida esperada: 0)");
			for (Iterator<Long> iterador = coleccion.iterator(); iterador
					.hasNext();) {
				System.out.println("Elemento coleccion: " + iterador.next());
			}

			System.out.println("Prueba search: (Salida esperada: 1)");
			System.out.println("Busqueda de pelicula: "
					+ dao.search("Pelicula", "titulo", "TLOTR").toString());

			System.out.println("Prueba delete: (Salida esperada: false)");
			dao.delete(p);
			System.out.println("Busqueda de pelicula: "
					+ dao.search("Pelicula", "titulo", "TLOTR").contains(1L));

		} catch (Exception excep) {
			excep.printStackTrace();
		}

	}

}
