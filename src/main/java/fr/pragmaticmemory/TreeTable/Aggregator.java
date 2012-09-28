package fr.pragmaticmemory.TreeTable;
import javax.swing.tree.DefaultMutableTreeNode;
public interface Aggregator<E extends Comparable> {

    public E getAggregatedValue(DefaultMutableTreeNode node) throws Exception;


    public void setAggregateAttribute(String aggregateAttribute);


    public String getAggregateAttribute();
}
