/*
 * Team : AGF AM / OSI / EF / Simu & Restit
 *
 * Copyright (c) 2007 AGF Asset Management.
 */
package fr.pragmaticmemory.TreeTable;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.EventObject;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
//TODO
//import org.apache.log4j.Logger;
/**
 * <p>Composant swing JTreeTable, cette classe est un enrichissement de la classe du même nom disponible sur le site de Sun.</p> <p>Le JTreeTable est
 * un composant graphique permettant de présenter un tableau avec une structure arborescente en offrant les mêmes facilités d'IHM qu'un JTree. Il est
 * constitué d'une JTable dont le renderer de la première colonne est un JTree.</p>
 */
public class JTreeTable extends JTable {
    //TODO
//    protected static final Logger logger = Logger.getLogger(JTreeTable.class);
    protected TreeTableModel treeTableModel;
    /**
     * une classe dérivée de JTree.
     */
    protected TreeTableCellRenderer treeTableCellRenderer;
    /**
     * Adaptateur du TreeModel en TableModel
     */
    protected TreeTableModelAdapter adapter;
    protected ListToTreeSelectionModelWrapper selectionWrapper;
    /**
     * Gestionnaire du déploiement de l'arborescence du JTree
     */
    private final TreeTableExpander expander;
    protected NodeRenderer nodeRenderer;
    protected NodeEditor nodeEditor;


//    public JTreeTable() {
//        this(getDefaultModel());
//    }


    public JTreeTable(TreeTableModel model) {
        // Create the tree. It will be used as a renderer and editor.
        treeTableModel = model;
        treeTableCellRenderer = new TreeTableCellRenderer(model, this);
        // Install a tableModel representing the visible rows in the tree.
        adapter = new TreeTableModelAdapter(model, treeTableCellRenderer);
        super.setModel(adapter);
        // Install the expander
        expander = new TreeTableExpander(this);

        // Force the JTable and JTree to share their row selection models.
        selectionWrapper = new ListToTreeSelectionModelWrapper();
        treeTableCellRenderer.setSelectionModel(selectionWrapper);
        setSelectionModel(selectionWrapper.getListSelectionModel());

        setIntercellSpacing(new Dimension(0, 0));

        // And update the height of the trees row to match that of the table.
        if (treeTableCellRenderer.getRowHeight() < 1) {
            setRowHeight(18);
        }

        // sets a multiline header renderer
        MultiLineHeaderRenderer headerRenderer = new MultiLineHeaderRenderer();
        Enumeration anEnum = getColumnModel().getColumns();
        while (anEnum.hasMoreElements()) {
            ((TableColumn)anEnum.nextElement()).setHeaderRenderer(headerRenderer);
        }
        expandOnDblclick();
        collapseTree();
    }


    /**
     * Model d'exemple
     */
//    protected static TreeTableModel getDefaultModel() {
//        ColumnsDescriptor descriptor = new ColumnsDescriptor();
//        BeanTreeTableModel defaultModel = new BeanTreeTableModel();
//        defaultModel.setColumnDescriptor(descriptor);
//        return defaultModel;
//    }


    @Override
    public void tableChanged(TableModelEvent event) {
        super.tableChanged(event);
        repaint();
    }


    public void setTreeTableModel(TreeTableModel model) {
        treeTableModel = model;
        treeTableCellRenderer.setModel(model);
        adapter.setModel(model);
        adapter.setJTree(treeTableCellRenderer);
        super.setModel(adapter);

        // updates the expander
        treeTableCellRenderer.setSelectionModel(selectionWrapper);
        setSelectionModel(selectionWrapper.getListSelectionModel());

        setDefaultRenderer(TreeTableModel.class, treeTableCellRenderer);

        collapseTree();
    }


    /**
     * <p>Retourne le gestionnaire du mode d'expansion de l'arbre</p>
     */
    public TreeTableExpander getExpander() {
        return expander;
    }


    private void expandOnDblclick() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int column = JTreeTable.this.getSelectedColumn();
                if (convertColumnIndexToModel(column) == 0 && mouseEvent.getClickCount() == 2) {
                    int row = JTreeTable.this.getSelectedRow();
                    if (treeTableCellRenderer.isExpanded(row)) {
                        treeTableCellRenderer.collapseRow(row);
                    }
                    else {
                        treeTableCellRenderer.expandRow(row);
                    }
                }
            }
        });
        treeTableCellRenderer.setToggleClickCount(-1);
    }


    public void expandAll() {
        treeTableCellRenderer.expandAll();
    }


    public void expandTree() {
        for (int i = 0; i < treeTableCellRenderer.getRowCount(); i++) {
            treeTableCellRenderer.expandRow(i);
        }
    }


    protected String getToolTipText(int row, int col) {
        return treeTableModel.getCellTipText(((TreeTableModelAdapter)getModel()).nodeForRow(row), col);
    }


    public void collapseTree() {
        for (int i = treeTableCellRenderer.getRowCount() - 1; i >= 0; i--) {
            treeTableCellRenderer.collapseRow(i);
        }
    }


    /**
     * <p>Scrolle ce JTreeTable de façon que la cellule (rowIndex, vColIndex) soit visible</p>
     */
    public void scrollToVisible(int rowIndex, int vColIndex) {
        if (!(getParent() instanceof JViewport)) {
            return;
        }
        JViewport viewport = (JViewport)getParent();

        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        Rectangle rect = getCellRect(rowIndex, vColIndex, true);

        // The location of the viewport relative to the table
        Point pt = viewport.getViewPosition();

        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        rect.setLocation(rect.x - pt.x, rect.y - pt.y);
        // Calculate location of rect if it were at half-height of view
        int centerY = (viewport.getViewRect().height - rect.height) / 2;
        if (rect.y < centerY) {
            centerY = -centerY;
        }
        rect.translate(0, centerY);

        // Scroll the area into view
        viewport.scrollRectToVisible(rect);
    }


    /**
     * Overridden to message super and forward the method to the tree. Since the tree is not actually in the component hieachy it will never receive
     * this unless we forward it in this manner.
     */
    @Override
    public void updateUI() {
        super.updateUI();
        if (treeTableCellRenderer != null) {
            treeTableCellRenderer.updateUI();
        }

        // Use the tree's default foreground and background colors in the
        // table.
        LookAndFeel.installColorsAndFont(this, "Tree.background", "Tree.foreground", "Tree.font");
    }


    /* Workaround for BasicTableUI anomaly. Make sure the UI never tries to
     * paint the editor. The UI currently uses different techniques to
     * paint the renderers and editors and overriding setBounds() below
     * is not the right thing to do for an editor. Returning -1 for the
     * editing row in this case, ensures the editor is never painted.
     */
    @Override
    public int getEditingRow() {
        return (editingColumn == 0) ? -1 : editingRow;
//        return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1 : editingRow;
    }


    /**
     * Overridden to pass the new rowHeight to the tree.
     *
     * @param rowHeight nouvelle hauteur de ligne
     */
    @Override
    public void setRowHeight(int rowHeight) {
        super.setRowHeight(rowHeight);
        if (treeTableCellRenderer != null && treeTableCellRenderer.getRowHeight() != rowHeight) {
            treeTableCellRenderer.setRowHeight(getRowHeight());
        }
    }


    /**
     * Returns the tree that is being shared between the model.
     *
     * @return Renvoie l'arbre
     */
    public JTree getTreeTableCellRenderer() {
        if (treeTableCellRenderer == null) {
            throw new NullPointerException("Ce TreeTable contient un JTree = null");
        }
        return treeTableCellRenderer;
    }


    public TreeTableModel getTreeTableModel() {
        return treeTableModel;
    }


    /**
     * La définition du cellRenderer est déléguée aux TreeTableLigne via l'Adapter. Il faut faire une conversion d'index pour que la ligne interprète
     * correctement le numéro de colonne.
     *
     * @param row    ligne demandée
     * @param column colonne demandée
     *
     * @return TODO
     */
    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        TableColumn tableColumn = getColumnModel().getColumn(column);
        TableCellRenderer renderer = tableColumn.getCellRenderer();
        if (renderer == null) {
            renderer = getDefaultRenderer(getColumnClass(column));
        }

        if (nodeRenderer != null) {
            Object node = adapter.nodeForRow(row);
            renderer = nodeRenderer.getTableCellRenderer(node, renderer);
        }

        if (convertColumnIndexToModel(column) == 0) {
            TreeCellRenderer cellRenderer = treeTableCellRenderer.getCellRenderer();
            if (cellRenderer instanceof TreeCellRendererAdapter) {
                ((TreeCellRendererAdapter)cellRenderer).setTableCellRenderer(renderer);
            }
            else {
                treeTableCellRenderer.setCellRenderer(new TreeCellRendererAdapter(renderer));
            }
            renderer = treeTableCellRenderer;
        }
        return renderer;
    }


    public NodeRenderer getNodeRenderer() {
        return nodeRenderer;
    }


    public void setNodeRenderer(NodeRenderer nodeRenderer) {
        this.nodeRenderer = nodeRenderer;
    }


    public NodeEditor getNodeEditor() {
        return nodeEditor;
    }


    public void setNodeEditor(NodeEditor nodeEditor) {
        this.nodeEditor = nodeEditor;
    }


    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        TableColumn tableColumn = getColumnModel().getColumn(column);
        TableCellEditor editor = tableColumn.getCellEditor();
        if (editor == null) {
            editor = getDefaultEditor(getColumnClass(column));
        }

        if (nodeEditor != null) {
            Object node = adapter.nodeForRow(row);
            editor = nodeEditor.getTableCellEditor(node, editor);
        }

        if (convertColumnIndexToModel(column) == 0) {
            editor = new TreeTableCellEditor();
        }
        return editor;
    }


    public void setDefaultTreeRenderer(TreeCellRenderer tr) {
        treeTableCellRenderer.setCellRenderer(tr);
    }


    public void delayedRowSelect(TreePath path) {
        if (adapter != null) {
            SwingUtilities.invokeLater(new DelayedRowSelect(path, adapter.getDelayedThread()));
        }
    }


    @Override
    public Point getToolTipLocation(MouseEvent event) {
        Point point = event.getPoint();

        int hitColumnIndex = columnAtPoint(event.getPoint());
        int hitRowIndex = rowAtPoint(event.getPoint());

        if ((hitColumnIndex != -1) && (hitRowIndex != -1)) {
            int decal = -2;
            Rectangle cellRect = getCellRect(hitRowIndex, hitColumnIndex, false);

            int toX = event.getPoint().x - cellRect.x - 5;
            int toY = event.getPoint().y - cellRect.y + decal * this.getRowHeight();
            point.translate(-toX, -toY);
        }
        else {
            point.translate(20, 20);
        }
        return point;
    }


    @Override
    public String getToolTipText(MouseEvent event) {
        String tip = null;
        Point point = event.getPoint();

        // Locate the renderer under the event location
        int hitColumnIndex = columnAtPoint(point);
        int hitRowIndex = rowAtPoint(point);

        if ((hitColumnIndex != -1) && (hitRowIndex != -1)) {
            TableCellRenderer renderer = getCellRenderer(hitRowIndex, hitColumnIndex);
            Component component = prepareRenderer(renderer, hitRowIndex, hitColumnIndex);

            // Now have to see if the component is a JComponent before
            // getting the tip
            if (component instanceof JComponent) {
                // Convert the event to the renderer's coordinate system
                Rectangle cellRect;

                if (component instanceof JTree) {
                    cellRect = getCellRect(0, hitColumnIndex, false);
                }
                else {
                    cellRect = getCellRect(hitRowIndex, hitColumnIndex, false);
                }

                point.translate(-cellRect.x, -cellRect.y);

                MouseEvent newEvent = new MouseEvent(component,
                                                     event.getID(),
                                                     event.getWhen(),
                                                     event.getModifiers(),
                                                     point.x,
                                                     point.y,
                                                     event.getClickCount(),
                                                     event.isPopupTrigger());
                if (hitColumnIndex == 0) {
                }
                tip = ((JComponent)component).getToolTipText(newEvent);
            }
            if (tip == null) {
                if (hitColumnIndex >= 0) {
                    // retrouver l'index de la colonne dans le modèle
                    int colnum = convertColumnIndexToModel(hitColumnIndex);
                    tip = getToolTipText(hitRowIndex, colnum);
                }
            }
        }
        else {
            tip = getToolTipText();
        }
        return tip;
    }


    public TreeTableModelAdapter getAdapter() {
        return adapter;
    }


    public static class DefaultBean implements Comparable {
        private String firstname;
        private String lastname;
        private Integer age;
        private String sex;


        DefaultBean(String firstname, String lastname, Integer age, String sex) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.age = age;
            this.sex = sex;
        }


        public String getFirstname() {
            return firstname;
        }


        public String getLastname() {
            return lastname;
        }


        public Integer getAge() {
            return age;
        }


        public String getSex() {
            return sex;
        }


        public int compareTo(Object object) {
            DefaultBean bean = (DefaultBean)object;
            return getFirstname().compareTo(bean.getFirstname());
        }


        @Override
        public String toString() {
            return firstname;
        }
    }

    class TreeCellRendererAdapter implements TreeCellRenderer {
        TableCellRenderer tableCellRenderer;


        TreeCellRendererAdapter(TableCellRenderer tableCellRenderer) {
            this.tableCellRenderer = tableCellRenderer;
        }


        public TableCellRenderer getTableCellRenderer() {
            return tableCellRenderer;
        }


        public void setTableCellRenderer(TableCellRenderer tableCellRenderer) {
            this.tableCellRenderer = tableCellRenderer;
        }


        public Component getTreeCellRendererComponent(JTree tree,
                                                      Object value,
                                                      boolean selected,
                                                      boolean expanded,
                                                      boolean leaf,
                                                      int row,
                                                      boolean hasFocus) {
            JTreeTable.this.getCellRenderer(row, 0);
            return tableCellRenderer.getTableCellRendererComponent(JTreeTable.this, value, selected, false,
                                                                   row, 0);
        }
    }

    /**
     * TreeTableCellEditor implementation. Component returned is the JTree.
     */
    public class TreeTableCellEditor extends AbstractTreeCellEditor implements TableCellEditor {
        public Component getTableCellEditorComponent(JTable table,
                                                     Object value,
                                                     boolean isSelected,
                                                     int row,
                                                     int column) {
            return treeTableCellRenderer;
        }


        /**
         * Overridden to return false, and if the event is a mouse event it is forwarded to the tree.<p>The behavior for this is debatable, and should
         * really be offered as a property. By returning false, all keyboard actions are implemented in terms of the table. By returning true, the
         * tree would get a chance to do something with the keyboard events. For the most part this is ok. But for certain keys, such as left/right,
         * the tree will expand/collapse where as the table focus should really move to a different column. Page up/down should also be implemented in
         * terms of the table. By returning false this also has the added benefit that clicking outside of the bounds of the tree node, but still in
         * the tree column will select the row, whereas if this returned true that wouldn't be the case.</p> <p>By returning false we are also
         * enforcing the policy that the tree will never be editable (at least by a key sequence).</p>
         */
        @Override
        public boolean isCellEditable(EventObject eventObject) {
            if (eventObject instanceof MouseEvent) {
                for (int counter = getColumnCount() - 1; counter >= 0; counter--) {
                    if (convertColumnIndexToModel(counter) == 0) {
                        MouseEvent me = (MouseEvent)eventObject;
                        MouseEvent newME =
                              new MouseEvent(treeTableCellRenderer, me.getID(), me.getWhen(),
                                             me.getModifiers(),
                                             me.getX() - getCellRect(0, counter, true).x, me.getY(),
                                             me.getClickCount(),
                                             me.isPopupTrigger());
                        treeTableCellRenderer.dispatchEvent(newME);
                        break;
                    }
                }
            }
            return false;
        }
    }

    /**
     * <p>a header renderer that displays text on several lines</p>
     */
    public class MultiLineHeaderRenderer extends JTextPane implements TableCellRenderer {
        protected Font fontTreeLabels = new Font("Dialog", 0, 10);
        protected Font fontTreeNodes = new Font("Dialog", Font.BOLD, 11);


        public MultiLineHeaderRenderer() {
            setOpaque(true);
            setForeground(UIManager.getColor("TableHeader.foreground"));
            super.setBackground(UIManager.getColor("TableHeader.background"));
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setEditable(false);
            Style centre = addStyle("centre", null);
            StyleConstants.setAlignment(centre, StyleConstants.ALIGN_CENTER);
            StyleConstants.setFontFamily(centre, fontTreeLabels.getFontName());
            StyleConstants.setFontSize(centre, fontTreeLabels.getSize());
            setLogicalStyle(centre);
        }


        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            //setFont(table.getFont());
            String str = (value == null) ? "" : value.toString();
            setText(str);
            setSize(new Dimension(table.getColumnModel().getColumn(column).getWidth(),
                                  table.getRowHeight() * 3));
            return this;
        }
    }

    /**
     * ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel to listen for changes in the ListSelectionModel it maintains. Once a change
     * in the ListSelectionModel happens, the paths are updated in the DefaultTreeSelectionModel.
     */
    class ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel {
        /**
         * Set to true when we are updating the ListSelectionModel.
         */
        protected boolean updatingListSelectionModel;


        ListToTreeSelectionModelWrapper() {
            getListSelectionModel().addListSelectionListener(createListSelectionListener());
        }


        /**
         * Returns the list selection model. ListToTreeSelectionModelWrapper listens for changes to this model and updates the selected paths
         * accordingly.
         */
        ListSelectionModel getListSelectionModel() {
            return listSelectionModel;
        }


        /**
         * This is overridden to set <code>updatingListSelectionModel</code> and message super. This is the only place DefaultTreeSelectionModel
         * alters the ListSelectionModel.
         */
        @Override
        public void resetRowSelection() {
            if (!updatingListSelectionModel) {
                updatingListSelectionModel = true;
                try {
                    super.resetRowSelection();
                }
                finally {
                    updatingListSelectionModel = false;
                }
            }

            // Notice how we don't message super if
            // updatingListSelectionModel is true. If
            // updatingListSelectionModel is true, it implies the
            // ListSelectionModel has already been updated and the
            // paths are the only thing that needs to be updated.
        }


        /**
         * Creates and returns an instance of ListSelectionHandler.
         */
        protected ListSelectionListener createListSelectionListener() {
            return new ListSelectionHandler();
        }


        /**
         * If <code>updatingListSelectionModel</code> is false, this will reset the selected paths from the selected rows in the list selection
         * model.
         */
        protected void updateSelectedPathsFromSelectedRows() {
            if (!updatingListSelectionModel) {
                updatingListSelectionModel = true;
                try {
                    // This is way expensive, ListSelectionModel needs an
                    // enumerator for iterating.
                    int min = listSelectionModel.getMinSelectionIndex();
                    int max = listSelectionModel.getMaxSelectionIndex();

                    clearSelection();
                    if (min != -1 && max != -1) {
                        for (int counter = min; counter <= max; counter++) {
                            if (listSelectionModel.isSelectedIndex(counter)) {
                                TreePath selPath = treeTableCellRenderer.getPathForRow(counter);

                                if (selPath != null) {
                                    addSelectionPath(selPath);
                                }
                            }
                        }
                    }
                }
                finally {
                    updatingListSelectionModel = false;
                }
            }
        }


        /**
         * Class responsible for calling updateSelectedPathsFromSelectedRows when the selection of the list changse.
         */
        class ListSelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent event) {
                updateSelectedPathsFromSelectedRows();
            }
        }
    }

    class DelayedRowSelect implements Runnable {
        TreePath path;
        Thread toJoin;


        DelayedRowSelect(TreePath treePath, Thread thread) {
            path = treePath;
            toJoin = thread;
        }


        public void run() {
            if (toJoin != null && toJoin.isAlive()) {
                try {
                    toJoin.join();
                }
                catch (InterruptedException e) {
                    // TODO
//                    logger.debug("", e);
                }
            }
            getTreeTableCellRenderer().setSelectionPath(path);
            int rowIndex = getTreeTableCellRenderer().getRowForPath(path);
            scrollToVisible(rowIndex, 0);
            if (rowIndex != -1) {
                setRowSelectionInterval(rowIndex, rowIndex);
            }
            else {
                //TODO
//                logger.debug("Anomalie");
            }
        }
    }

    /**
     * <p>Encapsule les actions de déploiement des branches de l'arbre en conservant un indicateur du niveau d'exposition de l'arbre</p>
     */
    public class TreeTableExpander {
        /**
         * TreeTable
         */
        private JTreeTable treeTable;


        /**
         * Constructeur
         *
         * @throws NullPointerException attend un treeTable non null
         */
        private TreeTableExpander(JTreeTable treeTable) {
            if (treeTable == null) {
                throw new NullPointerException(
                      "Ne peut créer un objet TreeTableExpander avec un treeTable nul");
            }

            this.treeTable = treeTable;
        }


        protected JTree getTree() {
            if (treeTable == null) {
                throw new NullPointerException("Ce TreeTableExpander contient un TreeTable = null");
            }
            return treeTable.getTreeTableCellRenderer();
        }


        /**
         * fully expands the tree with all nodes and leaves
         */
        public void expandAll() {
            treeTable.expandTree();
        }


        /**
         * collapses the tree to the highest node level
         */
        public void collapseAll() {
            treeTable.collapseTree();
        }


        public void setExpansionLevel(int newExpLevel) {
            JTree tree = getTree();
            if (newExpLevel == 0) {
                collapseAll();
            }
            if (newExpLevel > 0) {
                for (int i = treeTable.getRowCount(); --i >= 0;) {
                    TreePath path = tree.getPathForRow(i);
                    if (path.getPathCount() < newExpLevel) {
                        tree.expandPath(path);
                    }
                    if (path.getPathCount() >= newExpLevel) {
                        tree.collapsePath(path);
                    }
                }
            }
        }


        public void changeExpansion(int num) {
            int current;
            int max = 0;
            for (int i = treeTable.getRowCount(); --i >= 0;) {
                TreePath path = treeTableCellRenderer.getPathForRow(i);
                current = path.getPathCount();
                if (current > max) {
                    max = current;
                }
            }
            setExpansionLevel(max + num);
        }
    }
}
