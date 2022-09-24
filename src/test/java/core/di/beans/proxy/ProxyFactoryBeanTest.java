package core.di.beans.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.di.beans.factory.proxy.ProxyFactoryBean;
import study.cglib.HelloTarget;

public class ProxyFactoryBeanTest {
	@Test
	@DisplayName("Proxy Bean 생성 테스트")
	public void create() throws Exception {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(HelloTarget.class);
		HelloTarget object = (HelloTarget) proxyFactoryBean.getObject();
		assertThat(object.sayHello("dhlee")).isEqualTo("Hello dhlee");
	}
}
