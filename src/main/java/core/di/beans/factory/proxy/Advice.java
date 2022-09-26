package core.di.beans.factory.proxy;

import net.sf.cglib.proxy.MethodInterceptor;

public abstract class Advice implements MethodInterceptor {

	protected final PointCut pointCut;

	public Advice(PointCut pointCut) {
		this.pointCut = pointCut;
	}
}
