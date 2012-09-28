package fr.pragmaticmemory.TreeTable;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
/**
 * Implémentation de base de {@link com.agf.outilfo.swing.treetable.TreeTableModel}
 */
public abstract class AbstractTreeTableModel implements TreeTableModel {
    protected Object root;
    protected EventListenerList listenerList = new EventListenerList();


    public Object getRoot() {
        return root;
    }


    public boolean isLeaf(Object node) {
        return getChildCount(node) == 0;
    }


    public void valueForPathChanged(TreePath path, Object newValue) {
    }


    public int getIndexOfChild(Object parent, Object child) {
        for (int i = 0; i < getChildCount(parent); i++) {
            if (getChild(parent, i).equals(child)) {
                return i;
            }
        }
        return -1;
    }


    public void addTreeModelListener(TreeModelListener treeModelListener) {
        listenerList.add(TreeModelListener.class, treeModelListener);
    }


    public void removeTreeModelListener(TreeModelListener listener) {
        listenerList.remove(TreeModelListener.class, listener);
    }


    /*
     * Notify all listeners that have registered interest for notification on this event type.
     * The event instance is lazily created using the parameters passed into the fire method.
     * @see EventListenerList
     */
    public void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
        fireEvent(source, path, childIndices, children, new NodesChangedEventNotifier());
    }


    /*
     * Notify all listeners that have registered interest for notification on this event type.
     * The event instance is lazily created using the parameters passed into the fire method.
     * @see EventListenerList
     */
    public void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
        fireEvent(source, path, childIndices, children, new NodesInsertedEventNotifier());
    }


    /*
     * Notify all listeners that have registered interest for notification on this event type.
     * The event instance is lazily created using the parameters passed into the fire method.
     * @see EventListenerList
     */
    public void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
        fireEvent(source, path, childIndices, children, new NodesRemovedEventNotifier());
    }


    /*
     * Notify all listeners that have registered interest for notification on this event type.
     * The event instance is lazily created using the parameters passed into the fire method.
     * @see EventListenerList
     */
    public void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
        fireEvent(source, path, childIndices, children, new StructureChangedEventNotifier());
    }


    private void fireEvent(Object source, Object[] path, int[] childIndices, Object[] children, TreeModelEventNotifier eventNotifier) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent event = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (event == null) {
                    event = new TreeModelEvent(source, path, childIndices, children);
                }
                eventNotifier.notifyEvent((TreeModelListener)listeners[i + 1], event);
            }
        }
    }


    public void fireTreeStructureChanged(Object source, Object[] path) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent event = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (event == null) {
                    event = new TreeModelEvent(source, path);
                }
                ((TreeModelListener)listeners[i + 1]).treeStructureChanged(event);
            }
        }
    }


    public Class getColumnClass(int column) {
        return Object.class;
    }


    /**
     * By default, make the column with the Tree in it the only editable one. Making this column editable causes the JTable to forward mouse and
     * keyboard events in the Tree column to the underlying JTree.
     */
    public boolean isCellEditable(Object node, int column) {
        return column == 0;
    }


    public void setValueAt(Object aValue, Object node, int column) throws Exception {
    }


    interface TreeModelEventNotifier {
        void notifyEvent(TreeModelListener listener, TreeModelEvent event);
    }

    class NodesChangedEventNotifier implements TreeModelEventNotifier {
        public void notifyEvent(TreeModelListener listener, TreeModelEvent event) {
            listener.treeNodesChanged(event);
        }
    }

    class NodesInsertedEventNotifier implements TreeModelEventNotifier {
        public void notifyEvent(TreeModelListener listener, TreeModelEvent event) {
            listener.treeNodesInserted(event);
        }
    }

    class NodesRemovedEventNotifier implements TreeModelEventNotifier {
        public void notifyEvent(TreeModelListener listener, TreeModelEvent event) {
            listener.treeNodesRemoved(event);
        }
    }

    class StructureChangedEventNotifier implements TreeModelEventNotifier {
        public void notifyEvent(TreeModelListener listener, TreeModelEvent event) {
            listener.treeStructureChanged(event);
        }
    }
}
