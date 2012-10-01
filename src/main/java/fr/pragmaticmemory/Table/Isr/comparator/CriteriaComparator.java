package fr.pragmaticmemory.Table.Isr.comparator;
import fr.pragmaticmemory.Table.Isr.inputData.Criteria;
import java.util.Comparator;

public class CriteriaComparator implements Comparator<Criteria> {
    public int compare(Criteria o1, Criteria o2) {
        return o1.getId() - o2.getId();
    }
}
