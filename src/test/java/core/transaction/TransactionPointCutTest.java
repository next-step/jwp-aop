package core.transaction;

import core.annotation.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("트랜잭션 포인트 컷")
class TransactionPointCutTest {

    private static final TransactionPointCut INSTANCE = TransactionPointCut.instance();

    @Test
    @DisplayName("싱글톤 객체")
    void instance() {
        Assertions.assertThatNoException().isThrownBy(TransactionPointCut::instance);
    }

    @Test
    @DisplayName("클래스에 @Transactional 여부")
    void match_class() {
        assertAll(
                () -> assertThat(INSTANCE.matches(ClassWithTransactional.class)).isTrue(),
                () -> assertThat(INSTANCE.matches(ClassWithoutTransactional.class)).isFalse()
        );
    }

    @Test
    @DisplayName("메소드에 @Transactional 여부")
    void match_method() {
        //given
        Class<ClassWithTransactional> targetClass = ClassWithTransactional.class;
        //when, then
        assertAll(
                () -> assertThat(INSTANCE.matches(targetClass.getDeclaredMethod("methodWithTransactional"))).isTrue(),
                () -> assertThat(INSTANCE.matches(targetClass.getDeclaredMethod("methodWithoutTransactional"))).isFalse()
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
