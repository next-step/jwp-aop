package core.di.beans.factory.definition;

/**
 * @author KingCjy
 */
public interface BeanDefinition {
    Class<?> getType();
    String getName();
}
