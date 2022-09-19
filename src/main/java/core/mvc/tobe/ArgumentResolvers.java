package core.mvc.tobe;

import core.mvc.tobe.support.ArgumentResolver;
import core.mvc.tobe.support.HttpRequestArgumentResolver;
import core.mvc.tobe.support.HttpResponseArgumentResolver;
import core.mvc.tobe.support.ModelArgumentResolver;
import core.mvc.tobe.support.PathVariableArgumentResolver;
import core.mvc.tobe.support.RequestParamArgumentResolver;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public final class ArgumentResolvers {

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();
    private static final List<ArgumentResolver> DEFAULT_RESOLVERS = asList(
            new HttpRequestArgumentResolver(),
            new HttpResponseArgumentResolver(),
            new RequestParamArgumentResolver(),
            new PathVariableArgumentResolver(),
            new ModelArgumentResolver()
    );

    private final List<ArgumentResolver> resolvers;

    private ArgumentResolvers(List<ArgumentResolver> resolvers) {
        Assert.notNull(resolvers, "'resolvers' must not be null");
        this.resolvers = Collections.unmodifiableList(resolvers);
    }

    public static ArgumentResolvers defaultResolvers() {
        return DefaultArgumentResolversHolder.DEFAULT;
    }

    public static ArgumentResolvers withDefault(List<ArgumentResolver> addResolvers) {
        List<ArgumentResolver> resolvers = new ArrayList<>(DEFAULT_RESOLVERS);
        resolvers.addAll(addResolvers);
        return new ArgumentResolvers(resolvers);
    }

    public Object[] resolve(Method method, HttpServletRequest request, HttpServletResponse response) {
        return resolve(method, request, response, null);
    }

    public Object[] resolve(Method method, HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        String[] parameterNames = NAME_DISCOVERER.getParameterNames(method);
        return IntStream.range(0, method.getParameterCount())
                .mapToObj(i -> new MethodParameter(method, parameterTypes[i], parameterAnnotations[i], parameterNames[i]))
                .map(methodParameter -> resolveArgument(methodParameter, request, response, throwable))
                .toArray();
    }

    private Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
        if (Throwable.class.isAssignableFrom(methodParameter.getType()) && throwable != null) {
            return throwable;
        }
        return resolvers.stream()
                .filter(resolver -> resolver.supports(methodParameter))
                .map(resolver -> resolver.resolveArgument(methodParameter, request, response))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("No suitable resolver for argument: " + methodParameter));
    }

    private static class DefaultArgumentResolversHolder {
        private static final ArgumentResolvers DEFAULT = new ArgumentResolvers(DEFAULT_RESOLVERS);
    }
}
