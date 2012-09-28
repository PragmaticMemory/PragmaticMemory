package fr.pragmaticmemory.TreeTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;
/**
 * This is a wrapper class takes a TreeTableModel and implements the table model interface. The implementation is trivial, with all of the event
 * dispatching support provided by the superclass: the AbstractTableModel.
 *
 * @author Philip Milne
 * @author Scott Violet
 * @version 1.2 10/27/98
 */
public class TreeTableModelAdapter extends AbstractTableModel {
    JTree tree;
    TreeTableModel treeTableModel;
    DelayedThreadFireTableDataChanged delayedThread = new DelayedThreadFireTableDataChanged();

    TreeExpansionListener treeExpListener = new TreeExpansionListener() {
        // Don't use fireTableRowsInserted() here; the selection model would get updated twice.
        public void treeExpanded(TreeExpansionEvent event) {
            fireTableDataChanged();
        }


        public void treeCollapsed(TreeExpansionEvent event) {
            fireTableDataChanged();
        }
    };
    TreeModelListener treeModListener = new TreeModelListener() {
        public void treeNodesChanged(TreeModelEvent event) {
            delayedFireTableDataChanged();
        }


        public void treeNodesInserted(TreeModelEvent event) {
            delayedFireTableDataChanged();
        }


        public void treeNodesRemoved(TreeModelEvent event) {
            delayedFireTableDataChanged();
        }


        public void treeStructureChanged(TreeModelEvent event) {
            delayedFireTableDataChanged();
        }
    };


    public TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree) {
        this.tree = tree;
        this.treeTableModel = treeTableModel;

        tree.addTreeExpansionListener(treeExpListener);

        // Install a TreeModelListener that can update the table when
        // tree changes. We use delayedFireTableDataChanged as we can
        // not be guaranteed the tree will have finished processing
        // the event before us.
        treeTableModel.addTreeModelListener(treeModListener);
    }


    public int getColumnCount() {
        return treeTableModel.getColumnCount();
    }


    @Override
    public String getColumnName(int column) {
        return treeTableModel.getColumnName(column);
    }


    @Override
    public Class getColumnClass(int column) {
        return treeTableModel.getColumnClass(column);
    }


    public int getRowCount() {
        return tree.getRowCount();
    }


    public Object nodeForRow(int row) {
        TreePath treePath = tree.getPathForRow(row);
        if (treePath == null) {
            return null;
        }
        return treePath.getLastPathComponent();
    }


    public Object getValueAt(int row, int column) {
        try {
            return treeTableModel.getValueAt(nodeForRow(row), column);
        }
        catch (Exception e) {
            // TODO
//            ErrorHandler.handle(null, e);
        }
        return null;
    }


    @Override
    public boolean isCellEditable(int row, int column) {
        return treeTableModel.isCellEditable(nodeForRow(row), column);
    }


    @Override
    public void setValueAt(Object value, int row, int column) {
        try {
            treeTableModel.setValueAt(value, nodeForRow(row), column);
            // recalculer les informations à afficher dans EcranSimu
            delayedFireTableDataChanged();
        }
        catch (Exception e) {
            // TODO
//            ErrorHandler.handle(null, e);
        }
    }


    /**
     * Invokes fireTableDataChanged after all the pending events have been processed. SwingUtilities.invokeLater is used to handle this.
     */
    private void delayedFireTableDataChanged() {
        SwingUtilities.invokeLater(delayedThread);
    }


    public void setModel(TreeTableModel ttm) {
        if (treeTableModel != null) {
            treeTableModel.removeTreeModelListener(treeModListener);
        }
        treeTableModel = ttm;
        treeTableModel.addTreeModelListener(treeModListener);
    }


    public void setJTree(JTree jTree) {
        if (tree != null) {
            tree.removeTreeExpansionListener(treeExpListener);
        }
        tree = jTree;
        tree.addTreeExpansionListener(treeExpListener);
    }


    public Thread getDelayedThread() {
        return delayedThread;
    }


    class DelayedThreadFireTableDataChanged extends Thread {
        @Override
        public void run() {
            fireTableDataChanged();
        }
    }
}
