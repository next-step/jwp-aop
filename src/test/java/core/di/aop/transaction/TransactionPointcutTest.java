package core.di.aop.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TransactionPointcutTest {

    @DisplayName("@Transaction 애너테이션이 적용된 메서드인지 구분한다")
    @ParameterizedTest
    @CsvSource(value = {
        "withTransaction, true",
        "withoutTransaction, false"
    })
    void method_is_transaction_annotation_present(String methodName, boolean expected) throws NoSuchMethodException {
        final Method method = TransactionProxyConfig.class.getMethod(methodName);

        final TransactionPointcut transactionPointcut = new TransactionPointcut();
        final boolean actual = transactionPointcut.matches(method, TransactionProxyConfig.class);

        assertThat(actual).isEqualTo(expected);

    }
}
