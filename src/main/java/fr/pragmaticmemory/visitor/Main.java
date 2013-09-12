package fr.pragmaticmemory.visitor;

public class Main {

    public static void main(String[] args) {
        ElementA elementA = new ElementA();
        ElementB elementB = new ElementB();
        IVisitor visitor = new PrintVisitor();

        elementA.accept(visitor);
        elementB.accept(visitor);
    }
}
