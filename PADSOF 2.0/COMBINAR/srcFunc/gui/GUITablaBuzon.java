package gui;
 
import java.util.ArrayList;
 
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
 
import mensaje.MensajeUsuario;
 
public class GUITablaBuzon extends AbstractTableModel {
 
    private Object[] titulos = { false, "De", "Asunto", "Fecha" };
    private ArrayList<Boolean> listaCheckBoxs;
    private ArrayList<MensajeUsuario> filas;
 
    public GUITablaBuzon(ArrayList<MensajeUsuario> m) {
        listaCheckBoxs = new ArrayList<>();
        filas = new ArrayList<>();
        for (int i = 0; i < m.size(); i++) {
            listaCheckBoxs.add(false);
        }
        filas.addAll(m);
 
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
        // TODO Auto-generated method stub
        return filas.size() + 1;
    }
 
    @Override
    public Object getValueAt(int arg0, int arg1) {
        // TODO Auto-generated method stub
        Object objeto = null;
        if (arg0 == 0) {
            objeto = titulos[arg1];
        } else {
            switch (arg1) {
            case 0:
                objeto = listaCheckBoxs.get(arg0 - 1);
                break;
            case 1:
                objeto = filas.get(arg0 - 1).getRemitente().getCorreo();
                break;
            case 2:
                objeto = filas.get(arg0 - 1).getSujeto();
                break;
            case 3:
                objeto = filas.get(arg0 - 1).getFechaImp();
                break;
            }
        }
        return objeto;
    }
 
    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 0;
    } // permite editar la primera fila
 
    // Si se quiere cambiar el valor de las celdas una vez creadas,
    // // sobreescribir setValueAt
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 0) {
            if (row == 0) {
                titulos[0] = (Boolean) value;
                fireTableCellUpdated(0, col);
                for (int i = 0 ; i < listaCheckBoxs.size()  ; i++) {
                    listaCheckBoxs.set(i, (Boolean) value);
                    fireTableCellUpdated(i+1, col);
                }
            } else {
                listaCheckBoxs.set(row-1, (Boolean) value);
                fireTableCellUpdated(row-1, col);
            }
        }
    }
 
    // Podemos sobreescribir getColumnName para devolver el título de una
    // columna
 
    @Override
    public String getColumnName(int col) {
        if (col == 0)
            return "checkbox";
        return titulos[col].toString();
    }
 
    // Podemos visualizar los datos de las celdas con el formato predefinido
    // sobreescribiendo getColumnClass.
    // Los boolean se mostrarán como un checkbox, y los números estarán
    // alineados a la derecha (ver figura).
 
    @Override
    public Class getColumnClass(int col) {
        if (col == 0)
            return Boolean.class;
        return getValueAt(0, col).getClass();
    }
 
}