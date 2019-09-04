package core.aop.cglib;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import aop.cglib.EnhancerFactory;
import core.aop.UpperCaseResultInterceptor;
import core.aop.dynamic.HelloTarget;

public class cglibTest {

	@DisplayName("cglib proxy test")
	@Test
	public void invokeTest() {
		
		HelloTarget proxiedHello = EnhancerFactory.createProxy(HelloTarget.class, new UpperCaseResultInterceptor());
		
		assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
	}
}
