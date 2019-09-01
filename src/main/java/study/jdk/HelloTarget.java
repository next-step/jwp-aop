package study.jdk;

/**
 * Created by youngjae.havi on 2019-09-01
 */
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
    public String pingpong(String name) {
        return "pong " + name;
    }
}
