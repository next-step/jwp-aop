package core.di.beans.factory.aop;

import com.google.common.collect.Lists;
import net.sf.cglib.proxy.Callback;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Aspect {
    private final Pointcut pointcut;
    private final List<Advice> advices = Lists.newArrayList(NoOpAdvice.getInstance());

    public Aspect(Pointcut pointcut, Advice... advices) {
        this.pointcut = pointcut;
        addAdvice(advices);
    }

    public void addAdvice(Advice advice) {
        advices.add(advice);
    }

    public void addAdvice(Advice[] advices) {
        this.advices.addAll(Arrays.asList(advices));
    }

    public Callback[] toArrayAdvice() {
        return advices.toArray(Advice[]::new);
    }

    public void clear() {
        advices.clear();
    }

    public int matches(Method method, Target target) {
        return pointcut.matches(method, target);
    }
}
