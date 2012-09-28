package fr.pragmaticmemory.TreeTable.aggregator;
public interface BeanValueGetter<E> {

    E getBeanValue(Object bean, String col) throws Exception;
}
