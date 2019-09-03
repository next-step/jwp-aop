package study.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hspark on 2019-09-04.
 */
class StringUpperCaseDynamicInvocationHandler implements InvocationHandler {
	private Object target;
	private final Map<String, Method> methods = new HashMap();

	public StringUpperCaseDynamicInvocationHandler(Object target) {
		this.target = target;
		for (Method method : target.getClass().getDeclaredMethods()) {
			this.methods.put(method.getName(), method);
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("invoke method name : " + method.getName() + ", args : " + args[0]);
		Object result = methods.get(method.getName()).invoke(target, args);
		if (result instanceof String) {
			return ((String) result).toUpperCase();
		}
		return result;
	}
}

