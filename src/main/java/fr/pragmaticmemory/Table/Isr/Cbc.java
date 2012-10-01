package fr.pragmaticmemory.Table.Isr;

public class Cbc {
    final int id;
    static final int CLIENT_ID = 1;
    final int brokerId;
    final int criteriaId;


    public Cbc(int id, int brokerId, int criteriaId) {
        this.id = id;
        this.brokerId = brokerId;
        this.criteriaId = criteriaId;
    }


    public int getId() {
        return id;
    }


    public int getClientId() {
        return CLIENT_ID;
    }


    public int getBrokerId() {
        return brokerId;
    }


    public int getCriteriaId() {
        return criteriaId;
    }
}
