package core.aop;

import core.aop.example.StringReturn;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("빈 인터셉터")
class BeanInterceptorTest {

    @Test
    @DisplayName("인터셉트 테스트")
    void intercept() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(StringReturn.class);
        enhancer.setCallback(new BeanInterceptor(
                new UpperStrAdvice(),
                new GetContainPointCut()
        ));

        StringReturn stringReturn = (StringReturn) enhancer.create();

        assertThat(stringReturn.getString()).isEqualTo("STRING");
        assertThat(stringReturn.stringValue()).isEqualTo("string");
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

    public class GetContainPointCut implements PointCut {

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object[] arguments) {
            return method.getName().startsWith("get");
        }
    }
}
