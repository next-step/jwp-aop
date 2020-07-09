package core.aop.advice;

import com.google.common.collect.Lists;
import core.aop.advisor.Advisor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyMethodInvocation implements MethodInterceptor {
    private final List<Advisor> advisors = Lists.newArrayList();

    public ProxyMethodInvocation(List<Advisor> advisors) {
        if (!CollectionUtils.isEmpty(advisors)) {
            advisors.addAll(advisors);
        }
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MethodInvocation invocation = () -> proxy.invokeSuper(obj, args);

        for (int i = advisors.size() - 1; i >= 0; i--) {
            Advisor advisor = advisors.get(i);

            if (advisor.getPointcut().matches(method, obj.getClass(), args)) {
                MethodInvocation prevInvocation = invocation;
                invocation = () -> advisor.getAdvice().invoke(prevInvocation);
            }
        }

        return invocation.proceed();
    }
}
