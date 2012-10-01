package fr.pragmaticmemory.Table.Isr.TreeTable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
public class RatingBean {

    private String issuerName;
    Map<Integer, BigDecimal> index2RatingMap = new HashMap<Integer, BigDecimal>();


    public RatingBean(String issuerName) {
        this.issuerName = issuerName;
    }


    public String getIssuerName() {
        return issuerName;
    }


    public void addRating(int columnIndex, BigDecimal note) {
        index2RatingMap.put(columnIndex, note);
    }


    public BigDecimal getRating(int columnIndex) {
        return index2RatingMap.get(columnIndex);
    }


    public boolean hasRatingForIndex(int columnIndex) {
        return index2RatingMap.containsKey(columnIndex);
    }
}
