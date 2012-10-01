package fr.pragmaticmemory.Table.Isr;

import fr.pragmaticmemory.TreeTable.JTreeTable;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MainIsr {

    public static void main(String[] args) {

        JTreeTable treeTable = buildTreeTable();
        JFrame jFrame = new JFrame();
        jFrame.setLayout(new FlowLayout());
        jFrame.add(new JScrollPane(treeTable));
        jFrame.pack();
        jFrame.setVisible(true);
    }


    private static JTreeTable buildTreeTable() {
        IsrTreeTableModel treeTableModel = new IsrTreeTableModel(DataSource.getIssuerList(),
                                                                 DataSource.getBrokerList(),
                                                                 DataSource.getCriteriaList(),
                                                                 DataSource.getRatingList());

        final JTreeTable treeTable = new JTreeTable(treeTableModel);
        treeTable.getTreeTableCellRenderer().setShowsRootHandles(true);
        return treeTable;
    }
}
