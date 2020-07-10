package core.di.factory.example;

/**
 * @author KingCjy
 */
public class ExampleService {

    private final String message;

    public ExampleService(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
