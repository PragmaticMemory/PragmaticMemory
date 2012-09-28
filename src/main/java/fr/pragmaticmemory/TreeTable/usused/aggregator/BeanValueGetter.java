package fr.pragmaticmemory.TreeTable.usused.aggregator;
public interface BeanValueGetter<E> {

    E getBeanValue(Object bean, String col) throws Exception;
}
