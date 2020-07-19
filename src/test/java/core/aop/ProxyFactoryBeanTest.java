package core.aop;

import core.di.factory.example.proxy.ProxyTargetBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.cglib.UppercaseInterceptor;
import study.proxy.matcher.MethodMatcher;
import study.proxy.matcher.SayMethodMatcher;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyFactoryBeanTest {

    @DisplayName("target 의 Type 가져오기")
    @Test
    void getObjectType() {
        /* given */
        ProxyTargetBean target = new ProxyTargetBean();
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(target);

        /* when */
        Class<?> objectType = proxyFactoryBean.getObjectType();

        /* then */
        assertThat(objectType).isEqualTo(ProxyTargetBean.class);
    }

    @DisplayName("Callback 적용하여 Proxy Object 생성하기")
    @Test
    void getObject_callback() {
        /* given */
        ProxyTargetBean target = new ProxyTargetBean();
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(target);

        MethodMatcher methodMatcher = new SayMethodMatcher();
        proxyFactoryBean.addCallbacks(new UppercaseInterceptor(methodMatcher));

        /* when */
        ProxyTargetBean proxy = (ProxyTargetBean) proxyFactoryBean.getObject();

        /* then */
        assertThat(proxy.sayHello("nick")).isEqualTo("HELLO NICK");
    }

}
