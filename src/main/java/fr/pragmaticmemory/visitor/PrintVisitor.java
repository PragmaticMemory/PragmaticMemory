package fr.pragmaticmemory.visitor;

public class PrintVisitor implements IVisitor {

    public void visit(ElementA elementA) {
        System.out.println("ElementA");
        elementA.methodA();
    }


    public void visit(ElementB elementB) {
        System.out.println("ElementB");
        elementB.methodB();
    }
}
