package study.cglib;

public class PersonService {

    String sayHello(String name) {
        return "Hello " + name;
    }

    Integer lengthOfName(String name) {
        return name.length();
    }
}
