package study.aop.jdkproxy;

import study.aop.SayMethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler extends SayMethodMatcher implements InvocationHandler {
	private final Object target;
	private final Map<String, Method> methods = new HashMap<>();

	public DynamicInvocationHandler(Object target) {
		this.target = target;
		Arrays.stream(target.getClass().getDeclaredMethods())
			.forEach(method -> this.methods.put(method.getName(), method));
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.printf("invoke method name: %s, args: %s%n", method.getName(), Arrays.toString(args));
		final Object result = methods.get(method.getName()).invoke(target, args);
		if (matches(method, target.getClass(), args)) {
			return result.toString().toUpperCase();
		}
		return result;
	}
}
