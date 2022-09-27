package core.aop;

public class HelloTarget implements Hello {

    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String sayHi(String name) {
        return "Hi " + name;
    }

    public String sayThankYou(String name) {
        return "Thank You " + name;
    }

    @Override
    public String pinpong(String name) {
        return "pingpong " + name;
    }
}
