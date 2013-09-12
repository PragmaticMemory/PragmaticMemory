package fr.pragmaticmemory.visitor;

public class ElementA implements IVisited {

    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}