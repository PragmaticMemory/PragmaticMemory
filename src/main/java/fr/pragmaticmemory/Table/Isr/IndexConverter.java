package fr.pragmaticmemory.Table.Isr;

import java.util.ArrayList;
import java.util.List;
public class IndexConverter {

    List<Integer> list = new ArrayList<Integer>();


    void add(int i) {
        if (!list.contains(i)) {
            list.add(i);
        }
    }


    Integer getIdOfIndex(int index) {
        return list.get(index);
    }


    int getIndexOfId(int id) {
        return list.indexOf(id);
    }


    int getSize() {
        return list.size();
    }
}
