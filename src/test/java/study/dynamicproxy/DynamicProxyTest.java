package study.dynamicproxy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

/**
 * Created by hspark on 2019-09-04.
 */
public class DynamicProxyTest {
	@Test
	void 다이나믹_프록시() {
		Hello hello = (Hello) Proxy.newProxyInstance(
			this.getClass().getClassLoader(),
			new Class[] { Hello.class },
			new StringUpperCaseDynamicInvocationHandler(new HelloTarget()));

		Assertions.assertThat(hello.sayHello("hwatu")).isEqualTo("HELLO HWATU");
		Assertions.assertThat(hello.sayHi("hwatu")).isEqualTo("HI HWATU");
		Assertions.assertThat(hello.sayThankYou("hwatu")).isEqualTo("THANK YOU HWATU");
	}
}
