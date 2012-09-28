package fr.pragmaticmemory.TreeTable.usused.aggregator;
import org.apache.commons.beanutils.PropertyUtils;

public class DefaultBeanValueGetter<E> implements BeanValueGetter<E> {
    public E getBeanValue(Object bean, String col) throws Exception {
        if (bean != null) {
            return (E)PropertyUtils.getProperty(bean, col);
        }
        return null;
    }
}
