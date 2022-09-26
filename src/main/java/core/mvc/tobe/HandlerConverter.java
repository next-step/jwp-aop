package core.mvc.tobe;

import core.di.context.ApplicationContext;
import core.mvc.tobe.support.ArgumentResolver;

import java.util.List;
import java.util.Map;

public interface HandlerConverter {

    void setArgumentResolvers(List<ArgumentResolver> argumentResolvers);

    void addArgumentResolver(ArgumentResolver argumentResolver);

    Map<? extends HandlerKey,? extends HandlerExecution> convert(ApplicationContext applicationContext);
}
