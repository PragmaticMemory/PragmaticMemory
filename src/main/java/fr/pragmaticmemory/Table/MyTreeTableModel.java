package fr.pragmaticmemory.Table;

import fr.pragmaticmemory.TreeTable.AbstractTreeTableModel;
public class MyTreeTableModel extends AbstractTreeTableModel {
    int array[][] = {{1, 2}, {3, 4}, {5, 6}};
    String columnName[] = {"Colonne 1", "Colonne 2"};


    public MyTreeTableModel() {
        root = "Root";
    }


    public int getColumnCount() {
        return columnName.length;
    }


    public String getColumnName(int col) {
        return columnName[col];
    }


    public Object getValueAt(Object node, int col) throws Exception {
        return 7;
    }


    public String getCellTipText(Object node, int col) {
        return null;
    }


    public Object getChild(Object parent, int index) {
        if ("Root".equals(parent)) {
            if (index == 0) {
                return "Child1";
            }
            if (index == 1) {
                return "Child2";
            }
        }
        return null;
    }


    public int getChildCount(Object parent) {
        if ("Root".equals(parent)) {
            return 2;
        }
        return 0;
    }
}
