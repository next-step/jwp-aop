package core.di.beans.factory.support;

import java.util.Iterator;
import java.util.List;

public class AspectInvocationChain {

    private Object result = null;
    private Iterator<AspectBean> aspects;

    public AspectInvocationChain(List<AspectBean> aspectBeans) {
        this.aspects = aspectBeans.iterator();
    }

    public Object invoke(ProxyInvocation proxyInvocation) {
        if (aspects.hasNext()) {
            Object invocationResult = aspects.next().invoke(proxyInvocation, this);
            this.result = (this.result == null ? invocationResult : this.result);
        }

        return this.result;
    }

}
