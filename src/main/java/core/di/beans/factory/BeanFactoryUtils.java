package core.di.beans.factory;

import com.google.common.collect.Sets;
import core.annotation.Inject;
import core.annotation.PostConstruct;
import core.annotation.Qualifier;
import core.mvc.tobe.MethodParameter;
import core.util.ReflectionUtils;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.reflections.ReflectionUtils.getAllConstructors;
import static org.reflections.ReflectionUtils.withAnnotation;

public class BeanFactoryUtils {

    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 인자로 전달하는 클래스의 생성자 중 @Inject 애노테이션이 설정되어 있는 생성자를 반환
     *
     * @param clazz
     * @return
     * @Inject 애노테이션이 설정되어 있는 생성자는 클래스당 하나로 가정한다.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        Set<Constructor> injectedConstructors = getAllConstructors(clazz, withAnnotation(Inject.class));
        if (injectedConstructors.isEmpty()) {
            return null;
        }
        return injectedConstructors.iterator().next();
    }

    /**
     * 인자로 전달되는 클래스의 구현 클래스. 만약 인자로 전달되는 Class가 인터페이스가 아니면 전달되는 인자가 구현 클래스,
     * 인터페이스인 경우 BeanFactory가 관리하는 모든 클래스 중에 인터페이스를 구현하는 클래스를 찾아 반환
     *
     * @param injectedClazz
     * @param preInstanticateBeans
     * @return
     */
    public static Class<?> findConcreteClass(Class<?> injectedClazz, Set<Class<?>> preInstanticateBeans) {
        if (!injectedClazz.isInterface()) {
            return injectedClazz;
        }

        for (Class<?> clazz : preInstanticateBeans) {
            Set<Class<?>> interfaces = Sets.newHashSet(clazz.getInterfaces());
            if (interfaces.contains(injectedClazz)) {
                return clazz;
            }
        }

        throw new IllegalStateException(injectedClazz + "인터페이스를 구현하는 Bean이 존재하지 않는다.");
    }

    public static Object[] getParameters(BeanFactory beanFactory, Executable executable) {
        MethodParameter[] methodParameters = getMethodParameters(executable);

        Object[] parameters = new Object[methodParameters.length];

        for (int i = 0; i < methodParameters.length; i++) {
            String beanName = getBeanName(methodParameters[i]);
            parameters[i] = beanFactory.getBean(beanName, methodParameters[i].getType());
        }

        return parameters;
    }

    private static String getBeanName(MethodParameter methodParameter) {
        String name = methodParameter.getType().getName();
        Qualifier qualifier = methodParameter.getAnnotation(Qualifier.class);

        return qualifier == null ? name : qualifier.value();
    }

    private static MethodParameter[] getMethodParameters(Executable executable) {
        MethodParameter[] methodParameters = new MethodParameter[executable.getParameters().length];
        String[] parameterNames = getParameterNames(executable);
        Parameter[] parameters = executable.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            methodParameters[i] = new MethodParameter(executable, parameters[i].getType(), parameters[i].getAnnotations(), parameterNames[i]);
        }

        return methodParameters;
    }

    private static String[] getParameterNames(Executable executable) {
        if(executable instanceof Method) {
            return nameDiscoverer.getParameterNames((Method) executable);
        } else if(executable instanceof Constructor) {
            return nameDiscoverer.getParameterNames((Constructor<?>) executable);
        } else {
            throw new IllegalArgumentException("parameter executable must be a constructor or method");
        }
    }

    public static void injectField(BeanFactory beanFactory, Object instance, Field field) {
        try {
            String beanName = ReflectionUtils.getFieldBeanName(field);
            Object value = beanFactory.getBean(beanName, field.getType());
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(instance.getClass(), "illegal access '" + field.getName() + "'", e);
        }
    }

    public static Set<Field> findInjectFields(Class<?> targetClass) {
        return Arrays.stream(targetClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .collect(Collectors.toSet());
    }

    public static void invokePostConstructor(BeanFactory beanFactory, Object instance, Method method) {
        try {
            Object[] parameters = BeanFactoryUtils.getParameters(beanFactory, method);
            method.invoke(instance, parameters);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(method, "Cannot access method '" + method.getName() + "' is it public?", e);
        } catch (InvocationTargetException e) {
            throw new BeanInstantiationException(method, "Method threw exception", e.getTargetException());
        }
    }

    public static Set<Method> findPostConstructMethods(Class<?> targetClass) {
        return Arrays.stream(targetClass.getMethods())
                .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                .collect(Collectors.toSet());
    }

    public static Constructor<?> findInjectController(Class<?> targetClass) {
        return Arrays.stream(targetClass.getConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(Inject.class))
                .findAny()
                .orElseGet(() -> findPrimaryConstructor(targetClass));
    }

    private static Constructor<?> findPrimaryConstructor(Class<?> targetClass) {
        try {
            return targetClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new BeanInstantiationException(targetClass, "Constructor with @Inject not Found");
        }
    }
}
