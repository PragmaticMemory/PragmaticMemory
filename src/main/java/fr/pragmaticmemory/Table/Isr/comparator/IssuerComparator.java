package fr.pragmaticmemory.Table.Isr.comparator;
import fr.pragmaticmemory.Table.Isr.inputData.Issuer;
import java.util.Comparator;

public class IssuerComparator implements Comparator<Issuer> {
    public int compare(Issuer o1, Issuer o2) {
        return o1.getId() - o2.getId();
    }
}
