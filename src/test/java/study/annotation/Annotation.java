package study.annotation;

import core.annotation.Component;
import core.annotation.web.Controller;
import next.controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class Annotation {
    @Test
    @DisplayName("어노테이션의 어노테이션")
    void annotationsAnnotation() {
        assertThat(Controller.class.isAnnotationPresent(Component.class)).isTrue();
    }

    @Test
    @DisplayName("어노테이션의 어노테이션")
    void annotationFromClass() {
        boolean anyMatch = Arrays.stream(UserController.class.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().isAnnotationPresent(Component.class));

        assertThat(anyMatch).isTrue();
    }
}
