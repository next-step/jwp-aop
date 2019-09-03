package study.cglib;

/**
 * Created by hspark on 2019-09-04.
 */
class HelloTarget {
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
