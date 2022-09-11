package study.proxy;

public class HelloTarget implements Hello {

    @Override
    public String sayHello(final String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHi(final String name) {
        return "Hi " + name;
    }

    @Override
    public String sayThankYou(final String name) {
        return "Thank You " + name;
    }

    @Override
    public String pingPong(final String name) {
        return "PingPong " + name;
    }
}
