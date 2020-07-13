package core.util;

import core.annotation.Component;
import core.annotation.Qualifier;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

public class ReflectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    public static <T> T newInstance(Class<T> clazz, Object... args) {

        Constructor constructor = getConstructorByArgs(clazz, args);

        if (constructor == null) {
            throw new IllegalArgumentException(clazz.getSimpleName() + " doesn't have args size constructor");
        }

        try {
            return clazz.cast(constructor.newInstance(args));
        } catch (IllegalAccessException e) {
            logger.warn("{} constructor access failed", constructor.getName());
        } catch(InvocationTargetException e) {
            logger.warn("{} target invalid", clazz.getSimpleName());
        } catch (InstantiationException e) {
            logger.warn("{} instantiation failed", clazz.getSimpleName());
        }

        throw new RuntimeException(clazz.getSimpleName() + " instantiation failed");
    }

    public static Constructor getConstructorByArgs(Class clazz, Object... args) {
        for (Constructor candidate : clazz.getConstructors()) {
            if (isMatched(candidate, args)) {
                return candidate;
            }
        }

        return null;
    }

    public static boolean isMatched(Constructor constructor, Object... args) {

        if (constructor.getParameterCount() != args.length) {
            return false;
        }

        final Class[] parameterTypes = constructor.getParameterTypes();
        for (int i = 0; i < args.length; i++) {
            if (parameterTypes[i] != args[i].getClass()) { // todo handle primitive type
                return false;
            }
        }

        return true;
    }

    public static Object convertStringValue(String value, Class<?> clazz) {
        if (clazz == String.class) {
          return clazz.cast(value);
        } else if (clazz == int.class || clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return Long.valueOf(value);
        } else if (clazz == float.class || clazz == Float.class) {
            return Float.valueOf(value);
        } else if (clazz == double.class || clazz == Double.class) {
            return Double.valueOf(value);
        }

        throw new IllegalArgumentException(clazz.getTypeName() + " is not supported");
    }

    public static boolean hasFieldMethod(Class<?> clazz, String methodName, Class<?> type) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName) && method.getParameterTypes()[0] == type) {
                return true;
            }
        }
        return false;
    }

    public static Set<Class<?>> getAnnotatedClasses(Reflections reflections, Class<? extends Annotation>... annotations) {
        Set<Class<?>> classes = new LinkedHashSet<>();

        for (Class<? extends Annotation> annotation : annotations) {
            classes.addAll(reflections.getTypesAnnotatedWith(annotation));
        }

        return classes;
    }

    public static String getComponentName(Class<?> targetClass) {
        Component component = AnnotatedElementUtils.findMergedAnnotation(targetClass, Component.class);
        return "".equals(component.value()) ? targetClass.getName() : component.value();
    }

    public static String getFieldBeanName(Field field) {
        Qualifier qualifier = field.getAnnotation(Qualifier.class);
        return qualifier == null ? field.getType().getName() : qualifier.value();
    }

}
