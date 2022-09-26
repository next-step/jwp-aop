package core.di.beans.proxy;

import java.lang.reflect.Method;

import core.di.beans.factory.proxy.Advice;
import core.di.beans.factory.proxy.PointCut;
import net.sf.cglib.proxy.MethodProxy;

public class UpperAfterAdvice extends Advice {
	public UpperAfterAdvice(PointCut pointCut) {
		super(pointCut);
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object returnValue = proxy.invokeSuper(obj, args);
		if (pointCut.matches(method)) {
			return returnValue.toString().toUpperCase();
		}
		return returnValue;
	}
}
