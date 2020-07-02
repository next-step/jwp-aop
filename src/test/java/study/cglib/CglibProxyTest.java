package study.cglib;

import net.sf.cglib.proxy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;

public class CglibProxyTest {

    private Enhancer enhancer;

    @BeforeEach
    void setUp() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(PersonService.class);
    }

    @Test
    @DisplayName("반환 값을 같도록 구현하는 Proxy")
    void same_return() {
        enhancer.setCallback((FixedValue) () -> "Hello JavaJiGi!");
        PersonService proxy = (PersonService) enhancer.create();

        String res = proxy.sayHello(null);

        assertThat(res).isEqualTo("Hello JavaJiGi!");
    }

    @Test
    @DisplayName("메소드 signature에 따라 다르게 동작하는 Proxy")
    void method_signature() {
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                return "Hello JavaJiGi!";
            } else {
                return proxy.invokeSuper(obj, args);
            }
        });

        PersonService proxy = (PersonService) enhancer.create();

        assertThat(proxy.sayHello(null)).isEqualTo("Hello JavaJiGi!");
        assertThat(proxy.lengthOfName("SanJiGi")).isEqualTo(7);
    }

    @Test
    public void CGLIBToUppserCaseTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            Object returnValue = proxy.invokeSuper(obj, args);
            return returnValue instanceof String ? String.valueOf(returnValue).toUpperCase() : returnValue;
        });

        HelloTarget hello = (HelloTarget) enhancer.create();
        System.out.println(hello.sayHello("kingcjy"));

        assertThat(hello.sayHello("kingcjy")).isEqualTo("HELLO KINGCJY");
        assertThat(hello.sayHi("kingcjy")).isEqualTo("HI KINGCJY");
        assertThat(hello.sayThankYou("kingcjy")).isEqualTo("THANK YOU KINGCJY");
    }
}
