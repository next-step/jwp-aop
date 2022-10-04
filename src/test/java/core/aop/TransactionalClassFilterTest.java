package core.aop;

import static org.assertj.core.api.Assertions.assertThat;

import core.annotation.Component;
import core.annotation.ComponentScan;
import core.annotation.Service;
import core.annotation.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

class TransactionalClassFilterTest {

    @DisplayName("Transactional 어노테이션을 필터링하면 true 반환한다.")
    @Test
    void filterTest_1() {
        TransactionalClassFilter transactionalClassFilter
            = new TransactionalClassFilter();

        assertThat(transactionalClassFilter.matches(UseTransactionalClass.class)).isTrue();
    }

    @DisplayName("Transactional 이외의 어노테이션을 필터링하면 false 반환한다.")
    @Test
    void filterTest_2() {
        TransactionalClassFilter transactionalClassFilter
            = new TransactionalClassFilter();

        assertThat(transactionalClassFilter.matches(UnuseTransactionalClass.class)).isFalse();
    }

    @Transactional
    static class UseTransactionalClass { }

    @ActiveProfiles
    @ComponentScan
    static class UnuseTransactionalClass { }

}
