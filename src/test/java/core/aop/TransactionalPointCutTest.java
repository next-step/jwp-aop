package core.aop;

import core.annotation.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TransactionalPointCutTest {
    @Test
    @DisplayName("@Transactional 애노테이션이 붙은 클래스 혹은 메서드를 찾는다.")
    void findTransactionalMethodOrClass() throws NoSuchMethodException {
        TransactionalPointCut transactionalPointCut = TransactionalPointCut.getInstance();
        Method transactionalMethod = TransactionalMethod.class.getMethod("transactionalMethod");
        Method noTransactionalMethod = NoTransactionalClass.class.getMethod("noTransactionalMethod");

        assertAll(
                () -> assertThat(transactionalPointCut.matches(transactionalMethod, TransactionalMethod.class)).isTrue(),
                () -> assertThat(transactionalPointCut.matches(transactionalMethod, NoTransactionalClass.class)).isTrue(),
                () -> assertThat(transactionalPointCut.matches(noTransactionalMethod, TransactionalClass.class)).isTrue(),
                () -> assertThat(transactionalPointCut.matches(noTransactionalMethod, TransactionalMethod.class)).isFalse()
        );
    }

    @Transactional
    class TransactionalClass {
    }

    class TransactionalMethod {
        @Transactional
        public void transactionalMethod() {
        }
    }

    class NoTransactionalClass {
        public void noTransactionalMethod() {
        }
    }
}
