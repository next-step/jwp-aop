package proxy.util;

import core.di.beans.factory.support.BeanFactoryUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class UtilTest {

    @Test
    void beanUtil() {
        assertNotNull(BeanFactoryUtils.getInjectedConstructorWithDefault(DefCons.class));

        try {
            BeanFactoryUtils.getInjectedConstructorWithDefault(OneCons.class);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    private static class OneCons {

        private String name;

        public OneCons(String name) {
            System.out.println("one field Constructor");
            this.name = name;
        }
    }

    private static class DefCons {

        public DefCons() {
            System.out.println("Default Constructor");
        }
    }


}
