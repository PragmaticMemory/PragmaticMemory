package fr.pragmaticmemory.TreeTable;
import fr.pragmaticmemory.TreeTable.JTreeTable.TreeCellRendererAdapter;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import static javax.swing.UIManager.getColor;
public class TreeTableCellRenderer extends JTree implements TableCellRenderer {
    /**
     * Last table/tree row asked to renderer.
     */
    protected int visibleRow;
    protected JTreeTable treetable;


    public TreeTableCellRenderer(TreeModel model, JTreeTable treetab) {
        super(model);
        treetable = treetab;
        initDefaultTreeCellRenderer();
        //avoid displaying labels on rupture ending with "..."
        setLargeModel(true);
    }


    private void initDefaultTreeCellRenderer() {
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setBackground(getColor("Table.Background"));
        renderer.setForeground(getColor("Table.Foreground"));
        renderer.setTextSelectionColor(getColor("Table.selectionForeground"));
        renderer.setBackgroundSelectionColor(getColor("Table.selectionBackground"));
        renderer.setBackgroundNonSelectionColor(getColor("Table.Background"));
        setCellRenderer(renderer);
    }


    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        TreeCellRenderer renderer = this.getCellRenderer();
        if (renderer instanceof TreeCellRendererAdapter) {
            TableCellRenderer tcr = ((TreeCellRendererAdapter)renderer).getTableCellRenderer();
            Component component = tcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, 0);
            if (component != null) {
                super.setBackground(component.getBackground());
            }
        }
        visibleRow = row;
        return this;
    }


    @Override
    public void setRowHeight(int rowHeight) {
        if (rowHeight <= 0) {
            return;
        }
        super.setRowHeight(rowHeight);
        if (treetable == null || treetable.getRowHeight() == rowHeight) {
            return;
        }
        treetable.setRowHeight(getRowHeight());
    }


    @Override
    public void setBounds(int posX, int posY, int weight, int height) {
        int width = weight;
        if (treetable.getShowVerticalLines()) {
            width--;
        }
        super.setBounds(posX, 0, width, treetable.getHeight());
    }


    @Override
    public void paint(Graphics graphics) {
        graphics.translate(0, -visibleRow * getRowHeight());
        super.paint(graphics);
    }


    @Override
    public String getToolTipText(MouseEvent event) {
        if (event == null) {
            return null;
        }
        Point point = event.getPoint();
        int selRow = getClosestRowForLocation(point.x, point.y);
        TreeCellRenderer renderer = this.getCellRenderer();

        if (selRow == -1 || renderer == null) {
            return null;
        }
        return treetable.getToolTipText(selRow, 0);
    }


    public void expandAll() {
        for (int i = 0; i < getRowCount(); i++) {
            expandRow(i);
        }
    }
}
