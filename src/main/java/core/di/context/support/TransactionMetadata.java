package core.di.context.support;

import java.lang.reflect.Method;
import java.util.Set;

public class TransactionMetadata {

    private Class<?> clazz;

    private Set<Method> transactionMehtods;

    public TransactionMetadata(Class<?> clazz, Set<Method> transactionMehtods) {
        this.clazz = clazz;
        this.transactionMehtods = transactionMehtods;
    }

    public Class<?> getBeanClass() {
        return clazz;
    }

    public Set<Method> getTransactionMehtods() {
        return transactionMehtods;
    }

    public boolean hasTransaction() {
        return !transactionMehtods.isEmpty();
    }

    public boolean isTransaction(Method method) {
        return transactionMehtods.contains(method);
    }
}
