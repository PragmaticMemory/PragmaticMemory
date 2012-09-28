package fr.pragmaticmemory.TreeTable;
import java.util.Comparator;
/**
 * <p> Description complémentaire sur un attribut de bean utilisé dans les BeanTreeTable<br> Permet de définir, notamment,
 *
 * <ul> <li> si cet attribut est éditable, </li> <li> si on effectue des sommes automatiques au niveau des noeuds de branche sur cet attribut, </li>
 * <li> s'il est possible de définir une rupture sur cet attribut, </li> </ul> </p>
 */
public class ColumnInfo {
    protected String attribute;
    protected String label;
    protected Class clazz;
    protected boolean editable = false;
    protected boolean groupable = false;
    protected Comparator comparator;
    protected Aggregator aggregator;


    public ColumnInfo() {
    }


    public ColumnInfo(String attribute,
                      String label,
                      Class clazz,
                      Comparator comparator,
                      Aggregator aggregator,
                      boolean editable,
                      boolean groupable) {
        this.attribute = attribute;
        this.label = label;
        this.clazz = clazz;
        this.editable = editable;
        this.groupable = groupable;
        this.comparator = comparator;
        initializeAggregator(aggregator, attribute);
    }


    private void initializeAggregator(Aggregator aggreg, String attr) {
        if (aggreg == null) {
            return;
        }
        aggregator = aggreg;
        aggregator.setAggregateAttribute(attr);
        // TODO
        if (aggregator instanceof AbstractBigDecimalAggregator) {
            AbstractBigDecimalAggregator bigDecimalggregator = (AbstractBigDecimalAggregator)aggregator;
            comparator = new AggregateComparator(bigDecimalggregator);
        }
    }


    public Comparator getComparator() {
        return comparator;
    }


    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }


    public Aggregator getAggregator() {
        return aggregator;
    }


    public void setAggregator(Aggregator aggregator) {
        initializeAggregator(aggregator, attribute);
    }


    public boolean isGroupable() {
        return groupable;
    }


    public void setGroupable(boolean groupable) {
        this.groupable = groupable;
    }


    public boolean isEditable() {
        return editable;
    }


    public void setEditable(boolean editable) {
        this.editable = editable;
    }


    public String getAttribute() {
        return attribute;
    }


    public void setAttribute(String attribute) {
        this.attribute = attribute;
        initializeAggregator(aggregator, attribute);
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public Class getClazz() {
        return clazz;
    }


    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }


    @Override
    public String toString() {
        return "ColumnInfo{" +
               "attribute='" + attribute + '\'' +
               ", label='" + label + '\'' +
               ", comparator=" + (comparator != null) +
               '}' + "\n";
    }
}
