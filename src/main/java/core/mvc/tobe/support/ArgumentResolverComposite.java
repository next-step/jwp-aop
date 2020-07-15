package core.mvc.tobe.support;

import core.mvc.tobe.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author KingCjy
 */
public class ArgumentResolverComposite implements ArgumentResolver {

    private Set<ArgumentResolver> argumentResolvers;

    public ArgumentResolverComposite(ArgumentResolver...argumentResolvers) {
        this.argumentResolvers = new LinkedHashSet<>(Arrays.asList(argumentResolvers));
    }

    @Override
    public boolean supports(MethodParameter methodParameter) {
        return getArgumentResolver(methodParameter).isPresent();
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        return getArgumentResolver(methodParameter)
                .map(resolver -> resolver.resolveArgument(methodParameter, request, response))
                .orElse(null);
    }

    private Optional<ArgumentResolver> getArgumentResolver(MethodParameter methodParameter) {
        return argumentResolvers.stream()
                .filter(argumentResolver -> argumentResolver.supports(methodParameter))
                .findAny();
    }
}
