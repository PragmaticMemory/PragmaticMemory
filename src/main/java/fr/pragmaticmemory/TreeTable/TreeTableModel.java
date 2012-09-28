package fr.pragmaticmemory.TreeTable;
import javax.swing.tree.TreeModel;
/**
 * TreeTableModel is the model used by a JTreeTable. It extends TreeModel to add methods for getting
 * inforamtion about the set of columns each node in the TreeTableModel may have. Each column, like a column
 * in a TableModel, has a name and a type associated with it. Each node in the TreeTableModel can return a
 * value for each of the columns and set that value if isCellEditable() returns true.
 *
 * @author Philip Milne
 * @author Scott Violet
 */
public interface TreeTableModel extends TreeModel {
    /**
     * Retourne le nombre de colonnes disponibles
     *
     * @return int >= 0
     */
    public int getColumnCount();


    /**
     * Retourne le nom de la colonne <code>col</code>.
     *
     * @param col colonne
     */
    public String getColumnName(int col);


    /**
     * Retourne la classe de la colonne <code>col</code>.
     *
     * @param col colonne
     */
    public Class getColumnClass(int col);


    /**
     * Retourne la valeur de la colonne <code>col</code> pour le noeud <code>node</code>
     *
     * @param col colonne
     */
    public Object getValueAt(Object node, int col) throws Exception;


    /**
     * La colonne <code>column</code> est-elle éditable pour le noeud <code>node</code> is editable.
     *
     * @param col colonne
     */
    public boolean isCellEditable(Object node, int col);


    /**
     * Positionne la valeur de la colonne <code>col</code> pour le noeud <code>node</code>
     */
    public void setValueAt(Object aValue, Object node, int col) throws Exception;


    /**
     * Renvoie le tooltip associé à ce noeud pour la colonne donnée
     *
     * @param node noeud concerné
     * @param col  colonne
     *
     * @return Une chaine de caractère au format html
     */
    public String getCellTipText(Object node, int col);
}
