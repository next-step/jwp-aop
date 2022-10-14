package core.di.beans.factory.support;

import core.di.beans.factory.aop.Aspect;
import core.di.beans.factory.aop.Target;
import next.hello.HelloTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultFactoryBeanTest {

    @DisplayName("DefaultBeanFactory를 HelloTarget을 가지고 생성하여 정상적으로 반영되는지 확인한다.")
    @ParameterizedTest
    @ValueSource(strings = "one")
    void construct(String name) throws Exception {
        Target target = new Target(HelloTarget.class, new HelloTarget());
        Aspect aspect = new Aspect((obj, method, args, proxy) -> String.valueOf(proxy.invokeSuper(obj, args)).toUpperCase(Locale.ROOT),
                                   (method, targetClass, args) -> method.getName().startsWith("say"));

        DefaultFactoryBean<HelloTarget> defaultFactoryBean = new DefaultFactoryBean<>(target, aspect);
        HelloTarget helloTarget = defaultFactoryBean.getObject();

        assertAll(
                () -> assertEquals(helloTarget.sayHello(name), "HELLO ONE"),
                () -> assertEquals(helloTarget.sayHi(name), "HI ONE"),
                () -> assertEquals(helloTarget.sayThankYou(name), "THANK YOU ONE")
        );
    }
}