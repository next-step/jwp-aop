package core.beandefinition.example;

import core.annotation.Component;
import core.annotation.Inject;

@Component
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
