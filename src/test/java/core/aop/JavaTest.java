package core.aop;

import core.di.beans.factory.FactoryBean;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;


/**
 * @author KingCjy
 */
public class JavaTest {

    @Test
    public void test() {
        B a = new B();


        System.out.println("DD");

        System.out.println(((ParameterizedType)B.class.getGenericInterfaces()[0]).getRawType().getTypeName());
        System.out.println(A.class.getName());
        System.out.println(B.class.getInterfaces()[0].getName());

//                    this.type = (Class<?>) ((ParameterizedType) factoryBeanClass.getGenericInterfaces()[0]).getActualTypeArguments()[0];

        Class<?> t = Arrays.stream(B.class.getGenericInterfaces())
                .filter(type -> A.class.getName().equals(((ParameterizedType)type).getRawType().getTypeName()))
                .findFirst()
                .map(type -> (Class<?>) ((ParameterizedType) B.class.getGenericInterfaces()[0]).getActualTypeArguments()[0])
                .get();

        System.out.println(t);

    }


    public interface A<T> {
        T getObject();
    }

    public class B implements A<String> {

        @Override
        public String getObject() {
            return null;
        }
    }
}
