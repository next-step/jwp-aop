package core.di.context.support;

import core.annotation.Transactional;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static java.util.stream.Collectors.toSet;

public class TransactionUtils {

    public static TransactionMetadata getTransactionMetadata(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Transactional.class)) {
            return new TransactionMetadata(clazz, Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> Modifier.isPublic(m.getModifiers()))
                    .filter(m -> !Modifier.isStatic(m.getModifiers()))
                    .collect(toSet()));

        }

        return new TransactionMetadata(clazz, Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Transactional.class))
                .collect(toSet()));
    }

}
