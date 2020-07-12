package study.proxy.jdk;

public class HelloImplTarget implements Hello {

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
    public String pingpong(String name) {
        return "Pong " + name;
    }

}
