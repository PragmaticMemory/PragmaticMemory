package fr.pragmaticmemory.TreeTable.usused.aggregator;
import javax.swing.tree.DefaultMutableTreeNode;

public class AggregateComparator implements TreeTableColumnComparator {
    private Aggregator aggregator;


    public AggregateComparator(Aggregator aggregator) {
        this.aggregator = aggregator;
    }


    public int compare(Object object1, Object object2) {
        if (object1 == null) {
            return -1;
        }
        if (object2 == null) {
            return 1;
        }
        try {
            if (object1 instanceof DefaultMutableTreeNode && object2 instanceof DefaultMutableTreeNode) {
                return compareBranch((DefaultMutableTreeNode)object1, (DefaultMutableTreeNode)object2);
            }
            return compareLeaf((Comparable)object1, (Comparable)object2);
        }
        catch (Exception e) {
            // TODO
//            ErrorHandler.handle(null, e);
            return -1;
        }
    }


    private int compareLeaf(Comparable comparable1, Comparable comparable2) {
        return comparable1.compareTo(comparable2);
    }


    private int compareBranch(DefaultMutableTreeNode node1,
                              DefaultMutableTreeNode node2) throws Exception {
        Comparable value1 = aggregator.getAggregatedValue(node1);
        Comparable value2 = aggregator.getAggregatedValue(node2);
        return value1.compareTo(value2);
    }
}
