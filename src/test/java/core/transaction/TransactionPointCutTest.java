package core.transaction;


import core.annotation.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TransactionPointCutTest {

    private static final TransactionPointCut INSTANCE = TransactionPointCut.getInstance();

    @Test
    @DisplayName("클래스나 메서드에 @Transactional 여부를 판단한다")
    void matchPath() {
        assertAll(
                () -> assertThat(INSTANCE.matches(ClassWithTransactional.class, ClassWithTransactional.class.getDeclaredMethod("methodWithTransactional"))).isTrue(),
                () -> assertThat(INSTANCE.matches(ClassWithoutTransactional.class, ClassWithTransactional.class.getDeclaredMethod("methodWithTransactional"))).isTrue(),
                () -> assertThat(INSTANCE.matches(ClassWithoutTransactional.class, ClassWithTransactional.class.getDeclaredMethod("methodWithoutTransactional"))).isFalse(),
                () -> assertThat(INSTANCE.matches(ClassWithTransactional.class, ClassWithTransactional.class.getDeclaredMethod("methodWithoutTransactional"))).isTrue()
        );
    }

    @Transactional
    static class ClassWithTransactional {

        @Transactional
        void methodWithTransactional() {
        }

        void methodWithoutTransactional() {
        }
    }

    static class ClassWithoutTransactional {
    }
}