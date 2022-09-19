package study.dynamicProxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import study.example.Hello;
import study.example.HelloTarget;

public class UpperInvocationHandlerTest {
	@Test
	@DisplayName("메서드 반환 값 대문자 변환")
	public void toUpperCase() {
		Hello proxyInstance = (Hello) Proxy.newProxyInstance(UpperInvocationHandlerTest.class.getClassLoader(),
															 new Class[] {Hello.class },
															 new UpperInvocationHandler(new HelloTarget()));

		assertThat(proxyInstance.sayHello("dhlee")).isEqualTo("HELLO DHLEE");
		assertThat(proxyInstance.sayHi("dhlee")).isEqualTo("HI DHLEE");
		assertThat(proxyInstance.sayThankYou("dhlee")).isEqualTo("THANK YOU DHLEE");
	}
}
