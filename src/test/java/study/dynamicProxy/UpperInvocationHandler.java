package study.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import study.MethodMatcher;

public class UpperInvocationHandler implements InvocationHandler {
	private static final Logger logger = LoggerFactory.getLogger(UpperInvocationHandler.class);

	private final Object target;
	private final Map<String, Method> methods = new HashMap<>();
	private final MethodMatcher methodMatcher;

	public UpperInvocationHandler(Object target, MethodMatcher methodMatcher) {
		this.target = target;
		this.methodMatcher = methodMatcher;

		for (Method method : target.getClass().getDeclaredMethods()) {
			this.methods.put(method.getName(), method);
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		logger.debug("invoke method name : {}", method.getName() + ", args : " + args[0]);

		Object result = methods.get(method.getName()).invoke(target, args);

		if (methodMatcher.matches(method, proxy.getClass(), args)) {
			return result.toString().toUpperCase();
		}

		return result;
	}
}
