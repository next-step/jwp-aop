package core.di.factory;

import core.util.OrderComparator;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class OrderComparatorTest {

    class Order1 {

    }

    class Order2 {

    }

    @Order(3)
    class Order3 {

    }

    @Test
    public void onStreamTest() {
        Object[] orders = new Object[] { new Order2(), new Order3(), new Order1()};

        Class<?>[] classes = Arrays.stream(orders)
                .sorted(OrderComparator.INSTANCE)
                .map(object -> object.getClass())
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .toArray(new Class[]{});

        for (Class<?> aClass : classes) {
            System.out.println(aClass.getName());
        }

        assertThat(classes[0]).isEqualTo(Order2.class);
        assertThat(classes[1]).isEqualTo(Order1.class);
        assertThat(classes[2]).isEqualTo(Order3.class);
    }
}
