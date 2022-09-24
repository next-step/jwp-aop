package core.aop;

public class HelloCGLibTarget{

    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String sayHi(String name) {
        return "Hi " + name;
    }

    public String sayThankYou(String name) {
        return "Thank You " + name;
    }

    public String pingpong(String name) {
        return "ping pong " + name;
    }
}
