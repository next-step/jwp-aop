package study.proxy;

public class HelloService {

    public String sayHello(final String name) {
        return "Hello " + name;
    }

    public String sayHi(final String name) {
        return "Hi " + name;
    }

    public String sayThankYou(final String name) {
        return "Thank You " + name;
    }

    public String pingPong(final String name) {
        return "PingPong " + name;
    }
}
