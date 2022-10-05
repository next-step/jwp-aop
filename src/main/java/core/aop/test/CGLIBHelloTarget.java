package core.aop.test;

public class CGLIBHelloTarget {

    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String sayHi(String name) {
        return "Hi " + name;
    }

    public String sayThankYou(String name) {
        return "Thank You " + name;
    }

    public String pinpong(String name) {
        return "pingpong " + name;
    }
}
