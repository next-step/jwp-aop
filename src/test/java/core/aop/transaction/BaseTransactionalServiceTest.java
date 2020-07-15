package core.aop.transaction;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Ignore
class BaseTransactionalServiceTest {
    protected ListAppender<ILoggingEvent> listAppender = initLogAppenderList();

    private ListAppender<ILoggingEvent> initLogAppenderList() {
        listAppender = new ListAppender<>();
        listAppender.start();
        return listAppender;
    }

    protected void assertLogMessages(List<ILoggingEvent> logs, String... messages) {
        logs.stream()
            .allMatch(log -> ArrayUtils.contains(messages, log.getMessage()));
    }

    protected boolean isLogEmpty(List<ILoggingEvent> logs) {
        return CollectionUtils.isEmpty(logs);
    }
}
