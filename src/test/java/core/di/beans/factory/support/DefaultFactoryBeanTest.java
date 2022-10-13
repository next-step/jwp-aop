package core.di.beans.factory.support;

import next.hello.HelloTarget;
import next.interceptor.UppercaseMethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultFactoryBeanTest {

    @DisplayName("DefaultBeanFactory를 생성한다.")
    @Test
    void construct() throws Exception {
        DefaultFactoryBean<HelloTarget> defaultFactoryBean =
                new DefaultFactoryBean<>(HelloTarget.class, new UppercaseMethodInterceptor());
        Object object = defaultFactoryBean.getObject();
        assertThat(object).isInstanceOf(HelloTarget.class);
    }
}