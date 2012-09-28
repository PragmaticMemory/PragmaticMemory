package fr.pragmaticmemory.TreeTable;
public interface BeanValueGetter<E> {

    E getBeanValue(Object bean, String col) throws Exception;
}
