package study.proxy.cglib;

class HelloTarget {
    String sayHello(String name) {
        return "Hello " + name;
    }

    String sayHi(String name) {
        return "Hi " + name;
    }

    String sayThankYou(String name) {
        return "Thank you " + name;
    }

    String pingpong(String name) {
        return "Pong " + name;
    }
}
