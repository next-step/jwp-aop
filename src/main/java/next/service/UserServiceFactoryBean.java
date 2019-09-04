package next.service;

import core.annotation.Component;
import core.di.beans.factory.support.FactoryBean;
import core.annotation.Inject;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import next.dao.UserDao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Component
public class UserServiceFactoryBean implements FactoryBean<UserService> {

    private final UserDao userDao;

    @Inject
    public UserServiceFactoryBean(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserService getObject() {
        Constructor constructor = UserService.class.getConstructors()[0];
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new UserServiceInterceptor());
        return (UserService) enhancer.create(constructor.getParameterTypes(), new Object[] {userDao});
    }

    @Override
    public Class<?> getType() {
        return UserService.class;
    }

    private static class UserServiceInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            // 전처리
            Object result = proxy.invokeSuper(obj, args);
            // 후처리
            return result;
        }
    }
}
