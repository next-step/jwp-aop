package core.aop;

import core.aop.example.SimpleTarget;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("어드바이스")
class AdviceTest {

    @Test
    @DisplayName("대문자 변환 advice")
    void doAdvice() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SimpleTarget.class);
        enhancer.setCallback(new BeanInterceptor(
                new UpperStrAdvice(),
                (method, targetClass, arguments) -> true)
        );

        SimpleTarget simpleTarget = (SimpleTarget) enhancer.create();

        assertThat(simpleTarget.getString()).isEqualTo("STRING");
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(simpleTarget::getInt);

    }

    public class UpperStrAdvice implements Advice {

        @Override
        public Object doAdvice(Object object, Method method, Object[] arguments, MethodProxy proxy) {
            try {
                return ((String) proxy.invokeSuper(object, arguments)).toUpperCase();
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        }
    }
}
