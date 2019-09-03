package study.dynamicproxy;

/**
 * Created by hspark on 2019-09-04.
 */
class HelloTarget implements Hello {

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}

	@Override
	public String sayHi(String name) {
		return "Hi " + name;
	}

	@Override
	public String sayThankYou(String name) {
		return "Thank You " + name;
	}

	@Override
	public String pingpoing(String name) {
		return "Pong " + name;
	}
}
