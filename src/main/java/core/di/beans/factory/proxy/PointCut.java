package core.di.beans.factory.proxy;

import java.lang.reflect.Method;

public interface PointCut {
	boolean matches(Method m);
}
