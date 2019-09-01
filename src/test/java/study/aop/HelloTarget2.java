package study.aop;

public class HelloTarget2 {
    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String sayHi(String name) {
        return "Hi " + name;
    }

    public String sayThankYou(String name) {
        return "Thank You " + name;
    }

    public String pingpong(String ping) {
        return "Pong " + ping;
    }
}
