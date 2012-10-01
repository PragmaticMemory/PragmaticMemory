package fr.pragmaticmemory.Table.Isr;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
public class RatingBean {

    private String issuerName;
    Map<Integer, BigDecimal> index2RatingMap = new HashMap<Integer, BigDecimal>();


    public RatingBean(String issuerName) {
        this.issuerName = issuerName;
    }


    void add(int columnIndex, BigDecimal note) {
        index2RatingMap.put(columnIndex, note);
    }
}
