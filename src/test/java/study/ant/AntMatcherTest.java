package study.ant;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;

import static core.di.beans.factory.support.AntMatcherUtils.fullMethodName;
import static core.di.beans.factory.support.AntMatcherUtils.matches;
import static org.assertj.core.api.Assertions.assertThat;

public class AntMatcherTest {
    private static final Logger logger = LoggerFactory.getLogger(AntMatcherTest.class);

    @Test
    void match() throws NoSuchMethodException {
        String fqdn = fullQualifiedName(ArrayList.class);
        logger.debug("Full Qualified Name : {}", fqdn);
        String method = fullMethodName(ArrayList.class, ArrayList.class.getDeclaredMethod(
                "get", int.class));

        assertThat(matches("java.util.*", fqdn)).isTrue();
        assertThat(matches("java.util.ArrayList", fqdn)).isTrue();
        assertThat(matches("java.util.Array*", fqdn)).isTrue();
        assertThat(matches("java.util.Array*", method)).isTrue();
        assertThat(matches("java.util.ArrayList.*", "java.util.ArrayList.add")).isTrue();
        assertThat(matches("java.util.ArrayList", "java.util.ArrayList.add")).isFalse();
        assertThat(matches("java.util.ArrayList.", "java.util.ArrayList.add")).isFalse();
    }

    private String fullQualifiedName(Class clazz) {
        return clazz.getName();
    }

}
