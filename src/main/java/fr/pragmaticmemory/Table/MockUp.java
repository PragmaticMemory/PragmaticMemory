package fr.pragmaticmemory.Table;

//import com.agf.outilfo.swing.treetable.JTreeTable;
import fr.pragmaticmemory.TreeTable.JTreeTable;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
public class MockUp {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        JTable table = buildTable();
        JTreeTable treeTable = buildTreeTable();
        jFrame.setLayout(new FlowLayout());
        jFrame.add(new JScrollPane(table));
        jFrame.add(new JScrollPane(treeTable));
        jFrame.pack();
        jFrame.setVisible(true);
    }


    private static JTreeTable buildTreeTable() {
        final JTreeTable treeTable = new JTreeTable(new MyTreeTableModel());
        treeTable.getTreeTableCellRenderer().setShowsRootHandles(true);
        return treeTable;
    }


    private static JTable buildTable() {
        return new JTable(new MyTableModel());
    }
}
