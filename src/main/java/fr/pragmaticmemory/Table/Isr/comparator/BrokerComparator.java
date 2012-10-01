package fr.pragmaticmemory.Table.Isr.comparator;
import fr.pragmaticmemory.Table.Isr.inputData.Broker;
import java.util.Comparator;

public class BrokerComparator implements Comparator<Broker> {
    public int compare(Broker o1, Broker o2) {
        return o1.getId() - o2.getId();
    }
}
