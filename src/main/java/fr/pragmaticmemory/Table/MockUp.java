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
        final JTable table = new JTable(new MyTableModel());
        JScrollPane tableScrollPane = new JScrollPane(table);
        JTreeTable treeTable= new JTreeTable(new MyTreeTableModel());
        JScrollPane treeTableScrollPane = new JScrollPane(treeTable);
        jFrame.setLayout(new FlowLayout());
        jFrame.add(tableScrollPane);
        jFrame.add(treeTableScrollPane);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
