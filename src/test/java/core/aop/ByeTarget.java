package core.aop;

public class ByeTarget implements Bye {

    @Override
    public String sayHi(String text) {
        return "hi " + text;
    }
}
