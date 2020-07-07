package core.mvc;

import core.mvc.tobe.support.*;

import java.util.Arrays;
import java.util.List;

public class DefaultArgumentResolvers {
    private List<ArgumentResolver> resolvers = Arrays.asList(
            new HttpRequestArgumentResolver(),
            new HttpResponseArgumentResolver(),
            new RequestParamArgumentResolver(),
            new PathVariableArgumentResolver(),
            new ModelArgumentResolver()
    );

    public List<ArgumentResolver> getResolvers() {
        return resolvers;
    }
}
