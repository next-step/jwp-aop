package proxy.pointcut;

import next.config.MyConfiguration;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    private static final PointcutParser parser =
            PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

    @DisplayName("Execution Pointcut Test selected")
    @Test
    void execution_selected() throws NoSuchMethodException {

        final PointcutExpression pe1 =
                parser.parsePointcutExpression("execution(public void proxy.pointcut.JunExample.test())");

        assertTrue(pe1.couldMatchJoinPointsInType(JunExample.class));
        assertFalse(pe1.couldMatchJoinPointsInType(JunExample2.class));

        final ShadowMatch matchTest = pe1.matchesMethodExecution(JunExample.class.getMethod("test"));

        assertTrue(matchTest.alwaysMatches());
        assertTrue(matchTest.maybeMatches());
        assertFalse(matchTest.neverMatches());
    }

    @DisplayName("Execution Pointcut Test")
    @ParameterizedTest(name = "pointcut: {0}, clazz: {1}")
    @MethodSource("samplePointcut")
    void execution_all(String pointcut, Class<?> clazz) throws NoSuchMethodException {
        final PointcutExpression pe2 = parser.parsePointcutExpression(pointcut);

        assertTrue(pe2.couldMatchJoinPointsInType(clazz));

        final ShadowMatch matchTest = pe2.matchesMethodExecution(clazz.getMethod("test"));
        final ShadowMatch matchTest2 = pe2.matchesMethodExecution(clazz.getMethod("test2"));

        assertTrue(matchTest.maybeMatches());
        assertTrue(matchTest2.maybeMatches());
    }

    private static Stream<Arguments> samplePointcut() {
        return Stream.of(
                Arguments.of("execution(* proxy.pointcut.*.*())",
                        JunExample.class),
                Arguments.of("execution(* proxy.pointcut.*.*())",
                        JunExample2.class)
        );
    }

    @Test
    void withAnnotation() {
        final PointcutExpression pe =
                parser.parsePointcutExpression("@within(core.annotation.Service)");

        assertTrue(pe.couldMatchJoinPointsInType(MyService.class));
        assertTrue(pe.matchesStaticInitialization(MyService.class).maybeMatches());
        assertFalse(pe.matchesStaticInitialization(MyConfiguration.class).maybeMatches());
        assertTrue(pe.couldMatchJoinPointsInType(MyConfiguration.class));

    }

}
