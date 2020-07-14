package study.objenesis;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;
import org.springframework.objenesis.ObjenesisHelper;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class ObjenesisTest {

    @Test
    public void instanceToProxyTest() {
        TestClass testClass = (TestClass) createProxy(TestClass.class, (obj, method, args, proxy) -> proxy.invokeSuper(obj, args));

        assertThat(testClass.getMessage()).isNull();
    }

    private Object createProxy(Class<?> targetClass, MethodInterceptor methodInterceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallbackType(methodInterceptor.getClass());

        Class<?> proxyClass = enhancer.createClass();
        Enhancer.registerCallbacks(proxyClass, new Callback[]{methodInterceptor});
        return ObjenesisHelper.newInstance(proxyClass);
    }
    
    public static class TestClass {
        private String message;

        public TestClass(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "TestClass{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }
}
