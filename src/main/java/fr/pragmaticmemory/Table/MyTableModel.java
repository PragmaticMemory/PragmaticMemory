package fr.pragmaticmemory.Table;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
    int array[][] = {{1, 2}, {3, 4}, {5, 6}};
    String columnName[] = {"Colonne 1", "Colonne 2"};


    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }


    public int getRowCount() {
        return array.length;
    }


    public int getColumnCount() {
        return array[0].length;
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        return array[rowIndex][columnIndex];
    }
}
