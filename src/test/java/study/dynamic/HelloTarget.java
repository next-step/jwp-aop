package study.dynamic;

public class HelloTarget implements Hello {

    static final String TEXT_OF_HELLO = "Hello %s";
    static final String TEXT_OF_HI = "Hi %s";
    static final String TEXT_OF_THANK_YOU = "Thank You %s";

    @Override
    public String sayHello(String name) {
        return String.format(TEXT_OF_HELLO, name);
    }

    @Override
    public String sayHi(String name) {
        return String.format(TEXT_OF_HI, name);
    }

    @Override
    public String sayThankYou(String name) {
        return String.format(TEXT_OF_THANK_YOU, name);
    }
}
