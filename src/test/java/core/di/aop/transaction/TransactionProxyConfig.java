package core.di.aop.transaction;

import core.annotation.Transactional;

public class TransactionProxyConfig {

    @Transactional
    public void withTransaction() {

    }

    public void withoutTransaction() {

    }
}
