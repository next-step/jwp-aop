package study.dynamicproxy;

/**
 * Created by hspark on 2019-09-04.
 */
class HelloTarget implements Hello {
	public String sayHello(String name) {
		return "Hello " + name;
	}

	public String sayHi(String name) {
		return "Hi " + name;
	}

	public String sayThankYou(String name) {
		return "Thank You " + name;
	}
}
