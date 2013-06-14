package fr.pragmaticmemory.fileProcessing.processor.arrayFormatter;

import java.util.Iterator;
public class ArrayIterator implements Iterator<ArrayLine> {
    private Iterator<ArrayLine> iterator;


    public ArrayIterator(Iterator<ArrayLine> iterator) {
        this.iterator = iterator;
    }


    public boolean hasNext() {
        return iterator.hasNext();
    }


    public ArrayLine next() {
        return iterator.next();
    }


    public void remove() {
        iterator.remove();
    }
}
