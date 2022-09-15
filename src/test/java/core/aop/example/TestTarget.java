package core.aop.example;

public class TestTarget {

    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String pingPong(String name) {
        return "Pong " + name;
    }
}
