package fr.pragmaticmemory.TreeTable.usused.aggregator;
import javax.swing.tree.DefaultMutableTreeNode;
public interface Aggregator<E extends Comparable> {

    public E getAggregatedValue(DefaultMutableTreeNode node) throws Exception;


    public void setAggregateAttribute(String aggregateAttribute);


    public String getAggregateAttribute();
}
