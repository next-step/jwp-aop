package core.mvc;

import core.mvc.tobe.example.TestControllerAdvice;
import core.mvc.tobe.support.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Collections;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

@DisplayName("예외를 처리해주는 컨트롤러 어드바이스와 매칭하여 메소드를 실행 시키기 위한 클래스")
class DefaultExceptionAdaptorTest {
    private DefaultExceptionAdaptor defaultExceptionAdaptor;

    @BeforeEach
    void setEnv() {
        ArgumentResolvers argumentResolvers = () -> asList(
                new HttpRequestArgumentResolver(),
                new HttpResponseArgumentResolver(),
                new RequestParamArgumentResolver(),
                new PathVariableArgumentResolver(),
                new ModelArgumentResolver()
        );

        defaultExceptionAdaptor = new DefaultExceptionAdaptor(
                argumentResolvers.getResolvers(),
                Collections.singletonList(new TestControllerAdvice())
        );
    }

    @Test
    @DisplayName("예외를 잘 처리하는지")
    void handle() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        assertThatCode(() -> defaultExceptionAdaptor.handle(new IllegalArgumentException("hi"), request, response))
                .doesNotThrowAnyException();

        ModelAndView mav = defaultExceptionAdaptor.handle(new IllegalArgumentException("hi"), request, response);
        mav.getView().render(new HashMap<>(), request, response);

        assertThat(response.getRedirectedUrl()).isEqualTo("/users/loginForm");
    }

    @Test
    @DisplayName("원하는 예외가 없을때 해당 예외를 반환하는지")
    void noMatchingException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        assertThatNullPointerException()
                .isThrownBy(() -> defaultExceptionAdaptor.handle(new NullPointerException("hi"), request, response))
                .withMessage("hi");
    }
}
