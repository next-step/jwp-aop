package core.aop.example;

public class TestTarget implements TestInterface {

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
    public String pingPong(String name) {
        return "Pong " + name;
    }
}
