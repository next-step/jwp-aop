package core.di.beans.factory.aop;

import core.di.beans.factory.ProxyFactoryBean;
import core.di.beans.factory.support.DefaultBeanDefinition;
import core.exception.JwpException;
import core.exception.JwpExceptionStatus;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.beans.BeanUtils;

public class ProxyBeanDefinition extends DefaultBeanDefinition {
    private ProxyFactoryBean proxyFactoryBean;

    public ProxyBeanDefinition(Class<?> clazz, MethodInterceptor interceptor) {
        super(clazz);
        this.proxyFactoryBean = new ProxyFactoryBean(BeanUtils.instantiateClass(clazz), interceptor);
    }

    public Object getObject() {
        try {
            return proxyFactoryBean.getObject();
        } catch (Exception e) {
            throw new JwpException(JwpExceptionStatus.GET_FACTORY_OBJECT_ERROR, e);
        }
    }
}
