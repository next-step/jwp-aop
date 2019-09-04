package core.aop.dynamic;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import aop.dynamic.DynamicInvocationFactory;
import core.aop.UpperCaseResultInterceptor;

public class DynamicInvocationHandlerTest {


	@DisplayName("jdk dynamickInvoke proxy test")
	@Test
	public void invokeTest() {
		
		Hello proxiedHello = DynamicInvocationFactory.createProxy(Hello.class, new HelloTarget(), new UpperCaseResultInterceptor());
		
		assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
	}
}
