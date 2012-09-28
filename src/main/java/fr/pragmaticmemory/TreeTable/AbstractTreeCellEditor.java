package fr.pragmaticmemory.TreeTable;
import java.util.EventObject;
import javax.swing.CellEditor;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

public class AbstractTreeCellEditor implements CellEditor {
    protected EventListenerList listenerList = new EventListenerList();


    public Object getCellEditorValue() {
        return null;
    }


    public boolean isCellEditable(EventObject e) {
        return true;
    }


    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }


    public boolean stopCellEditing() {
        return true;
    }


    public void cancelCellEditing() {
    }


    public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class, l);
    }


    public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class, l);
    }


    /*
     * Notify all listeners that have registered interest for notification on this event type.
     * @see EventListenerList
     */
    protected void fireEditingStopped() {
        fireEvent(new EditingStoppedEventNotifier());
    }


    /*
     * Notify all listeners that have registered interest for notification on this event type.
     * @see EventListenerList
     */
    protected void fireEditingCanceled() {
        fireEvent(new EditingCancelledEventNotifier());
    }


    /*
    * Notify all listeners that have registered interest for
    * notification on this event type.
    * @see EventListenerList
    */
    protected void fireEvent(CellEditorEventNotifier eventNotifier) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CellEditorListener.class) {
                eventNotifier.notifyEvent((CellEditorListener)listeners[i + 1], new ChangeEvent(this));
            }
        }
    }


    interface CellEditorEventNotifier {
        void notifyEvent(CellEditorListener listener, ChangeEvent event);
    }

    class EditingCancelledEventNotifier implements CellEditorEventNotifier {
        public void notifyEvent(CellEditorListener listener, ChangeEvent event) {
            listener.editingCanceled(event);
        }
    }

    class EditingStoppedEventNotifier implements CellEditorEventNotifier {
        public void notifyEvent(CellEditorListener listener, ChangeEvent event) {
            listener.editingStopped(event);
        }
    }
}
