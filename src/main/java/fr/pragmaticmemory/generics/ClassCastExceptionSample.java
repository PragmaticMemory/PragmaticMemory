package fr.pragmaticmemory.generics;

public class ClassCastExceptionSample {
    public static void main(String[] args) {
        Object object = 3;
        try {
            String string = (String)object;
        }
        catch (Exception e) {
            System.out.println("Une exception " + e.getClass() + " +  a été lancée.");
        }
    }
}
