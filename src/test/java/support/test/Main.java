package support.test;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
//        SingletonWithHolder.instance();
//        SingletonWithoutHolder.instance();

        System.out.println(SingletonWithoutHolder.class.getClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());
    }
}
