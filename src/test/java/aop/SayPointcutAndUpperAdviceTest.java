package aop;

import core.aop.SayPointcut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SayPointcutTest {

    @Test
    @DisplayName("say로 시작하는 메서드 확인")
    void pointcut_say_method() {
        SayPointcut sayPointcut = new SayPointcut();

        
    }
}
