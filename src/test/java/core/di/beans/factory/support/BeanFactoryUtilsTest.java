package core.di.beans.factory.support;

import core.annotation.Transactional;
import core.aop.example.transactional.NonTransactionalService;
import core.aop.example.transactional.TransactionalMethodService;
import core.aop.example.transactional.TransactionalTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeanFactoryUtilsTest {

    @Test
    @DisplayName("@Transactional 적용 서비스 테스트")
    void isAnnotatedBean_forTransactional() {
        assertThat(BeanFactoryUtils.isAnnotatedBean(TransactionalTypeService.class, Transactional.class)).isTrue();
        assertThat(BeanFactoryUtils.isAnnotatedBean(TransactionalMethodService.class, Transactional.class)).isTrue();
    }

    @Test
    @DisplayName("@Transactional 미적용 서비스 테스트")
    void isAnnotatedBean_forNonTransactional() {
        assertThat(BeanFactoryUtils.isAnnotatedBean(NonTransactionalService.class, Transactional.class)).isFalse();
    }
}