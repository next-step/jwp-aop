package core.di.beans.factory.config.converter;

import core.aop.ProxyBeanDefinition;
import core.di.beans.factory.support.InjectType;
import core.jdbc.JdbcTemplate;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.dao.UserDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("트랜잭션 BeanDefinition")
class TransactionBeanDefinitionConverterTest {
    private TransactionBeanDefinitionConverter converter = new TransactionBeanDefinitionConverter();

    @Test
    @DisplayName("지원하는지 안하는지")
    void support() {
        assertThat(converter.support(JdbcQuestionDao.class)).isFalse();
        assertThat(converter.support(UserDao.class)).isTrue();
        assertThat(converter.support(JdbcAnswerDao.class)).isTrue();
    }

    @Test
    @DisplayName("BeanDefinition을 잘 가져오는지")
    void convert() {
        ProxyBeanDefinition beanDefinition = (ProxyBeanDefinition) converter.convert(UserDao.class);

        assertThat(beanDefinition.getBeanClass()).isEqualTo(UserDao.class);
        assertThat(beanDefinition.getTargetBeanDefinition().getResolvedInjectMode())
                .isEqualTo(InjectType.INJECT_CONSTRUCTOR);
        assertThat(beanDefinition.getTargetConstructorParameterTypes()).hasSize(1);
        assertThat(beanDefinition.getTargetConstructorParameterTypes()[0]).isEqualTo(JdbcTemplate.class);
    }
}