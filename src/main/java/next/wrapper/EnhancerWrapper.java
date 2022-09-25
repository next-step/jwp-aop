package next.wrapper;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

public class EnhancerWrapper {
    private final Enhancer enhancer = new Enhancer();

    public EnhancerWrapper(Class<?> superClass, Callback callback) {
        enhancer.setSuperclass(superClass);
        enhancer.setCallback(callback);
    }

    public Object create() {
        return enhancer.create();
    }
}
