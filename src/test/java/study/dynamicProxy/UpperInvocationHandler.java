package study.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpperInvocationHandler implements InvocationHandler {
	private static final Logger logger = LoggerFactory.getLogger(UpperInvocationHandler.class);

	private Object target;
	private final Map<String, Method> methods = new HashMap<>();

	public UpperInvocationHandler(Object target) {
		this.target = target;
		for (Method method : target.getClass().getDeclaredMethods()) {
			this.methods.put(method.getName(), method);
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		logger.debug("invoke method name : {}", method.getName() + ", args : " + args[0]);

		Object result = methods.get(method.getName()).invoke(target, args);

		return result.toString().toUpperCase();
	}
}
