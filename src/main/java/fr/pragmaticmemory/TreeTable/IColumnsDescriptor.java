package fr.pragmaticmemory.TreeTable;
import java.util.Comparator;
public interface IColumnsDescriptor {
    public int getColumnCount();


    public ColumnInfo getColumnInfo(int idx);


    boolean contains(String attribute);


    Comparator getComparator(String attribute);


    boolean validateBean(Object bean);


    Class getBeanClass();


    String getLabel(int index);


    Class getColumnClass(int index);


    String getAttribute(int index);


    int getIndex(String attribute);


    boolean isEditable(int index);


    Aggregator getAggregator(int index);


    boolean isAggregable(int index);
}
