package core.di.beans.factory;

/**
 * @author KingCjy
 */
public interface BeanDefinition {
    Class<?> getType();
    String getName();
}
