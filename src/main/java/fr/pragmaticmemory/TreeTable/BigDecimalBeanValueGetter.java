package fr.pragmaticmemory.TreeTable;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;

public class BigDecimalBeanValueGetter implements BeanValueGetter<BigDecimal> {
    BeanValueGetter<BigDecimal> beanValueGetter;


    public BigDecimalBeanValueGetter() {
        this(new DefaultBeanValueGetter<BigDecimal>());
    }


    public BigDecimalBeanValueGetter(BeanValueGetter<BigDecimal> beanValueGetter) {
        this.beanValueGetter = beanValueGetter;
    }


    public BigDecimal getBeanValue(Object bean, String col) throws Exception {
        if (bean == null) {
            return new BigDecimal(0);
        }
        BigDecimal value = beanValueGetter.getBeanValue(bean, col);
        return (value == null) ? ZERO : value;
    }
}
