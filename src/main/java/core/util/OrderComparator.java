package core.util;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Comparator;

/**
 * @author KingCjy
 */
public class OrderComparator implements Comparator<Object> {

    public static final OrderComparator INSTANCE = new OrderComparator();

    private OrderComparator() { }

    @Override
    public int compare(Object o, Object o2) {
        int order1 = getOrder(o);
        int order2 = getOrder(o2);

        if(order1 < order2) return -1;
        if(order1 > order2) return 1;
        return 0;
    }

    private int getOrder(Object object) {
        Order order = object.getClass().getAnnotation(Order.class);

        if(order == null) {
            return 1;
        }

        return Ordered.LOWEST_PRECEDENCE;
    }
}
