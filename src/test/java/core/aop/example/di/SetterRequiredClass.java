package core.aop.example.di;

public class SetterRequiredClass {
    private String string = "";

    public void setString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
