package fr.pragmaticmemory.Table.Isr.inputData;

public class Issuer {

    private int id;
    private int parentId;
    private String name;


    public Issuer(int id, int parentId, String name) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }


    public int getId() {
        return id;
    }


    public int getParentId() {
        return parentId;
    }


    public String getName() {
        return name;
    }
}
