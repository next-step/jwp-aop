package core.mvc.tobe;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {

//    private static final Map<Method, MethodParameter[]> methodParameterCache = new ConcurrentHashMap<>();
//    private List<ArgumentResolver> argumentResolvers;
//    private ParameterNameDiscoverer parameterNameDiscoverer;
    private ArgumentMatcher argumentMatcher;
    private Object target;
    private Method method;

//    public HandlerExecution(ParameterNameDiscoverer parameterNameDiscoverer, List<ArgumentResolver> argumentResolvers, Object target, Method method) {
//        this.parameterNameDiscoverer = parameterNameDiscoverer;
//        this.argumentResolvers = argumentResolvers;
//        this.target = target;
//        this.method = method;
//    }

    public HandlerExecution(ArgumentMatcher argumentMatcher, Object target, Method method) {
        this.argumentMatcher = argumentMatcher;
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MethodParameter[] methodParameters = argumentMatcher.getMethodParameters(method);
        Object[] arguments = new Object[methodParameters.length];

        for (int i = 0; i < methodParameters.length; i++) {
            arguments[i] = argumentMatcher.resolveArgument(methodParameters[i], request, response);
        }

        return (ModelAndView) method.invoke(target, arguments);
    }

//    private MethodParameter[] getMethodParameters() {
//        MethodParameter[] methodParameters = methodParameterCache.get(method);
//        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
//
//        if (methodParameters == null) {
//            methodParameters = new MethodParameter[method.getParameterCount()];
//            Class<?>[] parameterTypes = method.getParameterTypes();
//            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//
//            for (int i = 0; i < methodParameters.length; i++) {
//                methodParameters[i] = new MethodParameter(method, parameterTypes[i], parameterAnnotations[i], parameterNames[i]);
//            }
//
//            methodParameterCache.put(method, methodParameters);
//        }
//
//        return methodParameters;
//    }
//
//    private Object getArguments(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
//        for (ArgumentResolver resolver : argumentResolvers) {
//            if (resolver.supports(methodParameter)) {
//                return resolver.resolveArgument(methodParameter, request, response);
//            }
//        }
//
//        throw new IllegalStateException("No suitable resolver for argument: " + methodParameter.getType());
//    }


}
