package aop;

public interface MethodMatchAdapter extends MethodMatcher{

	
	Object adapterInvoke(Invoker invoker, Object obj, Object[] args) throws Throwable;
	
	
	interface Invoker {
		Object invoke(Object t, Object[] u) throws Throwable;
	}
}
