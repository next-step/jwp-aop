package aop.cglib;

import java.lang.reflect.Method;

import aop.MethodMatchAdapter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class EnhancerFactory {

	public static <T> T createProxy(Class<T> targetClass, MethodMatchAdapter methodMatchAdapter) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(new InnerMatchInterceptor(methodMatchAdapter));
		return targetClass.cast(enhancer.create());
	}
	
	private static class InnerMatchInterceptor implements MethodInterceptor {
		
		private final MethodMatchAdapter methodMatchAdapter;
		
		public InnerMatchInterceptor(MethodMatchAdapter methodMatchAdapter) {
			this.methodMatchAdapter = methodMatchAdapter;
		}
		
		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			boolean isMatched = methodMatchAdapter.matches(method, obj.getClass(), args);
			
			if(isMatched) {
				return methodMatchAdapter.adapterInvoke(methodProxy::invokeSuper, obj, args);	
			}
			
			return methodProxy.invokeSuper(obj, args);
		}
	}
	
}
