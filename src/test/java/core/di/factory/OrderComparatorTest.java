package core.di.factory;

import core.mvc.tobe.support.ArgumentResolver;
import core.mvc.tobe.support.HttpRequestArgumentResolver;
import core.mvc.tobe.support.ModelArgumentResolver;
import core.util.OrderComparator;
import next.security.LoginUserArgumentResolver;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
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

    @Test
    public void orderTest() {
        Set<ArgumentResolver> argumentResolvers = getArgumentResolvers()
                .stream()
                .sorted(OrderComparator.INSTANCE)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        argumentResolvers.forEach(resolver -> System.out.println(resolver.getClass()));
    }

    private Set<ArgumentResolver> getArgumentResolvers() {
        return new HashSet<>(Arrays.asList(
                new ModelArgumentResolver(),
                new LoginUserArgumentResolver()
        )
        );
    }

}
