package fr.pragmaticmemory.generics;

public class ArrayStoreExceptionSample {
    public static void main(String[] args) {
        Object[] array = new Integer[4];
        try {
            array[0] = "Une cha�ne de caract�res.";
        }
        catch (Exception e) {
            System.out.println("Une exception " + e.getClass() + " +  a �t� lanc�e.");
        }
    }
}
