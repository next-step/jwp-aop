package core.aop;


import core.aop.example.di.SetterRequiredClass;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("프록시 팩토리 빈")
class ProxyFactoryBeanTest {
    private BeanInterceptor interceptor =
            new BeanInterceptor(
                    (object, method, arguments, proxy) -> {
                        try {
                            return ((String) proxy.invokeSuper(object, arguments)).toUpperCase();
                        } catch (Throwable throwable) {
                            throw new RuntimeException(throwable);
                        } },
                    (method, targetClass, arguments) -> true
    );

    @Test
    @DisplayName("프록시 생성시 superclass의 필드 셋팅하기 테스트")
    void superClassFieldSetting() throws IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SetterRequiredClass.class);
        enhancer.setCallbackType(interceptor.getClass());
        Class classForProxy = enhancer.createClass();
        Enhancer.registerCallbacks(classForProxy, new Callback[] {interceptor});
        Object createdProxy = classForProxy.getDeclaredConstructor().newInstance();
        SetterRequiredClass proxy = (SetterRequiredClass) createdProxy;

        assertThat(proxy.getString()).isEqualTo("");

        for (Field realField : FieldUtils.getAllFieldsList(SetterRequiredClass.class)) {
            if (Modifier.isStatic(realField.getModifiers()))
                continue;

            realField.setAccessible(true);
            realField.set(createdProxy, "injected");
        }

        assertThat(proxy.getString()).isEqualTo("INJECTED");
    }

}
