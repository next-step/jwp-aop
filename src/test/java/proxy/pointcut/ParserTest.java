package proxy.pointcut;

import next.config.MyConfiguration;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    private static final PointcutParser parser =
            PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

    @Test
    void execution_parser() throws NoSuchMethodException {

        final PointcutExpression pe1 =
                parser.parsePointcutExpression("execution(public void proxy.pointcut.JunExample.test())");
        assertTrue(pe1.couldMatchJoinPointsInType(JunExample.class));
        assertFalse(pe1.couldMatchJoinPointsInType(JunExample2.class));

        Method m1 = JunExample.class.getMethod("test");

        final ShadowMatch shadowMatch1 = pe1.matchesMethodExecution(m1);
        assertTrue(shadowMatch1.alwaysMatches());
        assertTrue(shadowMatch1.maybeMatches());
        assertFalse(shadowMatch1.neverMatches());

        final PointcutExpression pe2 = parser.parsePointcutExpression("execution(* proxy.pointcut.*.*())");

        assertTrue(pe2.couldMatchJoinPointsInType(JunExample.class));
        assertTrue(pe2.couldMatchJoinPointsInType(JunExample2.class));

        Method m2_1 = JunExample.class.getMethod("test");
        Method m2_2 = JunExample.class.getMethod("test2");
        Method m2_3 = JunExample2.class.getMethod("test");
        Method m2_4 = JunExample2.class.getMethod("test2");

        final ShadowMatch shadowMatch2_1 = pe2.matchesMethodExecution(m2_1);
        final ShadowMatch shadowMatch2_2 = pe2.matchesMethodExecution(m2_2);
        final ShadowMatch shadowMatch2_3 = pe2.matchesMethodExecution(m2_3);
        final ShadowMatch shadowMatch2_4 = pe2.matchesMethodExecution(m2_4);

        assertTrue(shadowMatch2_1.maybeMatches());
        assertTrue(shadowMatch2_2.maybeMatches());
        assertTrue(shadowMatch2_3.maybeMatches());
        assertTrue(shadowMatch2_4.maybeMatches());
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
