package fr.pragmaticmemory.TreeTable;

import javax.swing.table.TableCellRenderer;

public interface NodeRenderer {

    TableCellRenderer getTableCellRenderer(Object node, TableCellRenderer renderer);
}
