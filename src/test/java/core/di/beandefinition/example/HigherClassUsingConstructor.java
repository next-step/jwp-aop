package core.di.beandefinition.example;

import core.annotation.Inject;

public class HigherClassUsingConstructor {
    private LowerClass lowerClass;

    @Inject
    public HigherClassUsingConstructor(LowerClass lowerClass) {
        this.lowerClass = lowerClass;
    }

    public LowerClass getLowerClass() {
        return lowerClass;
    }
}
