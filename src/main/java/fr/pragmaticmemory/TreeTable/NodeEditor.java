package fr.pragmaticmemory.TreeTable;
import javax.swing.table.TableCellEditor;
public interface NodeEditor {
    TableCellEditor getTableCellEditor(Object node, TableCellEditor renderer);
}
