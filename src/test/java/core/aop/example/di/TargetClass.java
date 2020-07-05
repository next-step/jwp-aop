package core.aop.example.di;

import core.annotation.Inject;

public class TargetClass {
    private String string = "string";
    private SomeComponent someComponent;

    @Inject
    public TargetClass(SomeComponent someComponent) {
        this.someComponent = someComponent;
    }

    public String getString() {
        return string;
    }

    public String stringValue() {
        return string;
    }

    public SomeComponent someComponent() {
        return someComponent;
    }
}
