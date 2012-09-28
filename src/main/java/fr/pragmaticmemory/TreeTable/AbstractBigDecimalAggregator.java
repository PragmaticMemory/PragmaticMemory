package fr.pragmaticmemory.TreeTable;
import java.math.BigDecimal;
public abstract class AbstractBigDecimalAggregator extends AbstractAggregator<BigDecimal> {

    BigDecimalBeanValueGetter beanValueGetter = new BigDecimalBeanValueGetter();


    protected BigDecimal getBeanValue(Object bean, String col) throws Exception {
        return beanValueGetter.getBeanValue(bean, col);
    }
}
