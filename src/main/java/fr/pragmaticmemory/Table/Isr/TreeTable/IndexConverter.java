package fr.pragmaticmemory.Table.Isr.TreeTable;

import java.util.ArrayList;
import java.util.List;
public class IndexConverter {

    private List<Integer> list = new ArrayList<Integer>();


    public void add(int i) {
        if (!list.contains(i)) {
            list.add(i);
        }
    }


    public Integer getIdOfIndex(int index) {
        return list.get(index);
    }


    public int getIndexOfId(int id) {
        return list.indexOf(id);
    }


    public int getSize() {
        return list.size();
    }
}
