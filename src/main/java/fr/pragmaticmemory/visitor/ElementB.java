package fr.pragmaticmemory.visitor;

public class ElementB implements IVisited {

    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public void methodB()
    {
        System.out.println("ElementB.MethodB()");
    }
}
