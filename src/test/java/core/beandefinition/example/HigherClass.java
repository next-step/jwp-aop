package core.beandefinition.example;

import core.annotation.Component;
import core.annotation.Inject;

//injection을 field로 직접 하는 경우
@Component
public class HigherClass {
    @Inject
    private LowerClass lowerClass;

    public LowerClass getLowerClass() {
        return lowerClass;
    }
}
