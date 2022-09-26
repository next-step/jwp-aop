package core.di.beans.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.di.beans.factory.proxy.Advice;
import core.di.beans.factory.proxy.PointCut;
import core.di.beans.factory.proxy.ProxyFactoryBean;
import study.cglib.HelloTarget;

public class ProxyFactoryBeanTest {
	@Test
	@DisplayName("Proxy Bean 생성 테스트")
	public void create() {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(HelloTarget.class);
		HelloTarget object = (HelloTarget) proxyFactoryBean.getObject();
		assertThat(object.sayHello("dhlee")).isEqualTo("Hello dhlee");
	}

	@Test
	@DisplayName("Target, Pointcut, Advice 적용 테스트")
	public void upperTest() {
		// given
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(HelloTarget.class);
		PointCut pointCut = (method) -> method.getName().startsWith("say");
		Advice advice = new UpperAfterAdvice(pointCut);
		proxyFactoryBean.setAdvice(advice);

		// when
		HelloTarget proxyInstance = (HelloTarget) proxyFactoryBean.getObject();

		// then
		assertThat(proxyInstance.sayHi("dhlee")).isEqualTo("HI DHLEE");
		assertThat(proxyInstance.sayHello("dhlee")).isEqualTo("HELLO DHLEE");
		assertThat(proxyInstance.sayThankYou("dhlee")).isEqualTo("THANK YOU DHLEE");
		assertThat(proxyInstance.pingpong("dhlee")).isEqualTo("Pong dhlee");
	}
}
