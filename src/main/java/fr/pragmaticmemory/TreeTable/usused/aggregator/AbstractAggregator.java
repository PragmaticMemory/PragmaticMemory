package fr.pragmaticmemory.TreeTable.usused.aggregator;
public abstract class AbstractAggregator<E extends Comparable> implements Aggregator<E> {
    protected String aggregateAttribute;


    public void setAggregateAttribute(String aggregateAttribute) {
        this.aggregateAttribute = aggregateAttribute;
    }


    public String getAggregateAttribute() {
        return aggregateAttribute;
    }
}
