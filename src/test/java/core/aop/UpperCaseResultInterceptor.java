package core.aop;

import java.lang.reflect.Method;

import aop.MethodMatchAdapter;

public class UpperCaseResultInterceptor implements MethodMatchAdapter {

	@Override
	public boolean matches(Method m, Class<?> targetClass, Object[] args) {
		if(m.getName().startsWith("say")) {
			return true;
		}
		
		return false;
	}

	@Override
	public Object adapterInvoke(Invoker invoker, Object obj, Object[] args) throws Throwable {
		
		Object result = invoker.invoke(obj, args);
		
		if(result instanceof String) {
			return ((String) result).toUpperCase();
		}
		
		return result;
	}

}
