package fr.pragmaticmemory.Table.Isr.inputData;

import java.math.BigDecimal;
public class RatedCbcIssuer {

    final private int issuerId;
    final int brokerId;
    final int criteriaId;
    final private BigDecimal note;


    public RatedCbcIssuer(int issuerId, int brokerId, int criteriaId, BigDecimal note) {
        this.issuerId = issuerId;
        this.brokerId = brokerId;
        this.criteriaId = criteriaId;
        this.note = note;
    }


    public int getBrokerId() {
        return brokerId;
    }


    public int getCriteriaId() {
        return criteriaId;
    }


    public int getIssuerId() {
        return issuerId;
    }


    public BigDecimal getNote() {
        return note;
    }
}
