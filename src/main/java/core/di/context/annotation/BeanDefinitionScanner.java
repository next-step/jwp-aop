package core.di.context.annotation;

public interface BeanDefinitionScanner {

    void doScan(Object... basePackages);
}
