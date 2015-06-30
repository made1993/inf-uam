/**
 * 
 */
package gui;

import java.util.*;

import javax.swing.table.*;

import mensaje.*;

/**
 * @author eps
 *
 */
public class GUITablaMensajeColaborativo extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Object[] titulos = { "De", "Asunto", "Fecha" };
	private ArrayList<MensajeGrupo> filas;

	public GUITablaMensajeColaborativo(ArrayList<MensajeGrupo> arrayList) {
		filas = new ArrayList<>();
		if (arrayList.size() > 0)
			filas.addAll(arrayList);
	}

	public GUITablaMensajeColaborativo(List<MensajeColaborativo> arrayList) {
		filas = new ArrayList<>();
		if (arrayList.size() > 0)
			filas.addAll(arrayList);
	}

	public Object[] getTitulos() {
		return titulos;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return titulos.length;
	}

	@Override
	public int getRowCount() {
		return filas.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		Object objeto = null;
		if (filas.size() > 0) {
			switch (arg1) {
			case 0:
				objeto = filas.get(arg0).getRemitente().getCorreo();
				break;
			case 1:
				objeto = filas.get(arg0).getCuerpo();
				break;
			case 2:
				objeto = filas.get(arg0).getFechaImp();
				break;
			}
		}
		return objeto;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	// Podemos sobreescribir getColumnName para devolver el título de una
	// columna

	@Override
	public String getColumnName(int col) {
		return titulos[col].toString();
	}

	// Podemos visualizar los datos de las celdas con el formato predefinido
	// sobreescribiendo getColumnClass.
	// Los boolean se mostrarán como un checkbox, y los números estarán
	// alineados a la derecha (ver figura).

	@Override
	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}

}
