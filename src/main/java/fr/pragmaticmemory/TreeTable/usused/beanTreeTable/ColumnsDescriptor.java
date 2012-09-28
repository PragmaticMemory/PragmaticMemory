package fr.pragmaticmemory.TreeTable.usused.beanTreeTable;
import fr.pragmaticmemory.TreeTable.Aggregator;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.ComparatorUtils;
/**
 * Descripteur d'attributs de bean. Utilisé avec un {@link com.agf.outilfo.swing.treetable.BeanTreeTableModel}. Permet
 * de définir les colonnes qui peuvent être éditer, grouper, sommer ainsi que le label de la colonne...
 */
public class ColumnsDescriptor implements IColumnsDescriptor {
    /**
     * Utilisé pour trier les ColumnInfo et faire des recherche avec la méthode {@link
     * java.util.Arrays#binarySearch(Object[], Object, java.util.Comparator)}
     */
    private static final Comparator columnComparator =
          new Comparator() {
              private String getString(Object o) {
                  if (o instanceof ColumnInfo) {
                      return ((ColumnInfo)o).getAttribute();
                  }
                  else {
                      return o.toString();
                  }
              }


              public int compare(Object o1, Object o2) {
                  String s1 = getString(o1);
                  String s2 = getString(o2);
                  return s1.compareTo(s2);
              }
          };
    /**
     * Utilisé pour trier les PropertyDescriptor et faire des recherche avec la méthode {@link
     * java.util.Arrays#binarySearch(Object[], Object, java.util.Comparator)}
     */
    private static final Comparator propertyComparator =
          new Comparator() {
              private String getString(Object o) {
                  if (o instanceof PropertyDescriptor) {
                      return ((PropertyDescriptor)o).getName();
                  }
                  else {
                      return o.toString();
                  }
              }


              public int compare(Object o1, Object o2) {
                  String s1 = getString(o1);
                  String s2 = getString(o2);
                  return s1.compareTo(s2);
              }
          };
    /**
     * Liste des colonnes disponibles pour ce bean
     */
    protected ColumnInfo[] columns = new ColumnInfo[0];

    protected Class beanClass;


    public ColumnInfo[] getColumns() {
        return columns;
    }


    public void setColumns(ColumnInfo[] columns) {
        if (columns == null) {
            columns = new ColumnInfo[0];
        }
        // On trie les colonnes par ordre alphabétique sur nom d'attribut
        Arrays.sort(columns, columnComparator);
        this.columns = columns;
    }


    public void addColumn(ColumnInfo column) {
        ColumnInfo[] newColumnInfos = new ColumnInfo[columns.length + 1];

        int count = 0;
        for (ColumnInfo columnInfo : columns) {
            newColumnInfos[count++] = columnInfo;
        }
        newColumnInfos[count] = column;
        Arrays.sort(newColumnInfos, columnComparator);
        this.columns = newColumnInfos;
    }


    public Class getBeanClass() {
        return beanClass;
    }


    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }


    public String[][] filterValidRuptures(String[][] ruptures) {
        if (ruptures == null) {
            return new String[0][];
        }
        List<String[]> valids = new ArrayList<String[]>(ruptures.length);
        for (String[] rupture : ruptures) {
            if (checkRupture(rupture)) {
                valids.add(rupture);
            }
        }
        String[][] result = new String[valids.size()][];
        int resultIndex = 0;
        for (Object valid : valids) {
            result[resultIndex++] = (String[])valid;
        }
        return result;
    }


    /**
     * Valide une séquence de ruptures ( vérifie que tous les attributs sur lesquels sont définis les ruptures existent
     * et sont "groupable")
     */
    public boolean checkRupture(String[] ruptures) {
        if (ruptures == null) {
            return false;
        }
        for (int i = ruptures.length; --i >= 0; ) {
            if (!isGroupable(ruptures[i])) {
                return false;
            }
        }
        return true;
    }


    public void initialize() {
        if (beanClass == null) {
            throw new IllegalStateException("Ne peut initialiser le descripteur, aucune classe de bean spécifié");
        }
        if (columns == null) {
            columns = new ColumnInfo[0];
            return;
        }
        PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(beanClass);

        Arrays.sort(properties, propertyComparator);
        int idx;
        int nbOk = 0;
        for (int i = columns.length; --i >= 0; ) {
            idx = Arrays.binarySearch(properties, columns[i].getAttribute(),
                                      propertyComparator);
            if (idx >= 0) {
                columns[i].setClazz(properties[idx].getPropertyType());
                nbOk++;
            }
            else {
                //TODO Warn si l'attribut n'existe pas
                columns[i] = null;
            }
        }
        ColumnInfo[] validColumns = new ColumnInfo[nbOk];
        int nbDone = 0;
        for (ColumnInfo column : columns) {
            if (column != null) {
                validColumns[nbDone++] = column;
            }
        }
        columns = validColumns;
    }


    public ColumnInfo getColumnInfo(String attribute) {
        int index = getIndex(attribute);
        if (index >= 0) {
            return columns[index];
        }
        return null;
    }


    public ColumnInfo getColumnInfo(int index) {
        if (columns != null && index >= 0 && index < columns.length) {
            return columns[index];
        }
        return null;
    }


    public int getIndex(String attribute) {
        if (columns == null) {
            return -1;
        }
        int index = 0;
        for (ColumnInfo column : columns) {
            if (column.getAttribute().equals(attribute)) {
                return index;
            }
            index++;
        }
        return -1;
        //return Arrays.binarySearch(columns, attribute, columnComparator);
    }


    public boolean contains(String attribute) {
        return getIndex(attribute) >= 0;
    }


    public boolean validateBean(Object bean) {
        if (beanClass == null) {
            throw new NullPointerException("Class de bean null");
        }
        return beanClass.isInstance(bean);
    }


    public int getColumnCount() {
        if (columns != null) {
            return columns.length;
        }
        return 0;
    }


    public String getAttribute(int index) {
        ColumnInfo column = getColumnInfo(index);
        if (column != null) {
            return column.getAttribute();
        }
        return null;
    }


    public String getLabel(int index) {
        ColumnInfo column = getColumnInfo(index);
        if (column != null) {
            return column.getLabel();
        }
        return null;
    }


    public Class getColumnClass(int index) {
        ColumnInfo column = getColumnInfo(index);
        if (column != null) {
            return column.getClazz();
        }
        return Object.class;
    }


    public boolean isGroupable(int index) {
        ColumnInfo column = getColumnInfo(index);
        return column != null && column.isGroupable();
    }


    public boolean isEditable(int index) {
        ColumnInfo column = getColumnInfo(index);
        return column != null && column.isEditable();
    }


    public Aggregator getAggregator(int index) {
        ColumnInfo column = getColumnInfo(index);
        if (column != null) {
            return column.getAggregator();
        }
        return null;
    }


    public boolean isAggregable(int index) {
        ColumnInfo column = getColumnInfo(index);
        return (column != null && column.getAggregator() != null);
    }


    public Comparator getComparator(int index) {
        ColumnInfo column = getColumnInfo(index);
        if (column != null && column.getComparator() != null) {
            return column.getComparator();
        }
        return ComparatorUtils.naturalComparator();
    }


    public String getLabel(String attribute) {
        ColumnInfo column = getColumnInfo(attribute);
        if (column != null) {
            return column.getLabel();
        }
        return null;
    }


    public Class getColumnClass(String attribute) {
        ColumnInfo column = getColumnInfo(attribute);
        if (column != null) {
            return column.getClazz();
        }
        return Object.class;
    }


    public boolean isGroupable(String attribute) {
        ColumnInfo column = getColumnInfo(attribute);
        if (column == null) {
            return false;
        }
        return column.isGroupable();
    }


    public boolean isEditable(String attribute) {
        ColumnInfo column = getColumnInfo(attribute);
        if (column == null) {
            return false;
        }
        return column.isEditable();
    }


    public Comparator getComparator(String attribute) {
        ColumnInfo column = getColumnInfo(attribute);
        if (column != null && column.getComparator() != null) {
            return column.getComparator();
        }
        return ComparatorUtils.naturalComparator();
    }
}
