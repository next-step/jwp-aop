package aop.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import aop.MethodMatchAdapter;

public class DynamicInvocationFactory {

    
    public static <T> T createProxy(Class<T> returnType, Object obj, MethodMatchAdapter methodMatchAdapter) {
    	return (T) Proxy.newProxyInstance(returnType.getClassLoader(),
    			new Class<?>[]{ returnType }, new DynamicInvocationHandler(obj, methodMatchAdapter));
    }
    
    private static class DynamicInvocationHandler implements InvocationHandler {
        private Object target;
        private final MethodMatchAdapter methodMatchAdapter;
        private final Map<String, Method> methods = new HashMap<>();
        
        public DynamicInvocationHandler(Object target, MethodMatchAdapter methodMatchAdapter) {
            this.target = target;
            this.methodMatchAdapter = methodMatchAdapter;
            for (Method method : target.getClass().getDeclaredMethods()) {
                this.methods.put(method.getName(), method);
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        	Method actualMethod = methods.get(method.getName());
        	boolean isMatch = methodMatchAdapter.matches(actualMethod, target.getClass(), args);
        	
        	if(isMatch) {
        		return methodMatchAdapter.adapterInvoke(actualMethod::invoke, target, args);
        	}
        	
            return actualMethod.invoke(target, args);
        }
    }
}