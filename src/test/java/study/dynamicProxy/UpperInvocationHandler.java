package study.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.MethodMatcher;

public class UpperInvocationHandler implements InvocationHandler {
	private static final Logger logger = LoggerFactory.getLogger(UpperInvocationHandler.class);

	private final Object target;
	private final MethodMatcher methodMatcher;

	public UpperInvocationHandler(Object target, MethodMatcher methodMatcher) {
		this.target = target;
		this.methodMatcher = methodMatcher;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		logger.debug("invoke method name : {}", method.getName() + ", args : " + args[0]);

		Object result = method.invoke(target, args);

		if (methodMatcher.matches(method)) {
			return result.toString().toUpperCase();
		}

		return result;
	}
}
