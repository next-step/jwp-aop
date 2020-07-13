package core.di.factory;

import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author KingCjy
 */
public class AnnotatedElementUtilsTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface A1 {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @A1
    public @interface A2 {
        @AliasFor(annotation = A1.class)
        String value() default "";
    }

    @A2("hi")
    public static class Class1 {

    }

    @Test
    public void aliasForTest() {
        Class1 class1 = new Class1();

        A1 a1 = AnnotatedElementUtils.findMergedAnnotation(class1.getClass(), A1.class);

        System.out.println(a1.value());
    }
}
