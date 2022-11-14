package core.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.config.MyConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class ControllerExceptionHandlerMappingTest {

    @DisplayName("Controller에 정의된 ExceptionHandler 애너테이션이 적용된 메서드를 실행할 수 있다")
    @Test
    void getExceptionHandlerInController() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        final ExceptionHandlerConverter bean = applicationContext.getBean(ExceptionHandlerConverter.class);
        final ControllerExceptionHandlerMapping exceptionHandlerMapping = new ControllerExceptionHandlerMapping(applicationContext, bean);
        exceptionHandlerMapping.initialize();


        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("exceptionHandler", new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다."));
        final MockHttpServletResponse response = new MockHttpServletResponse();

        final HandlerExecution handler = exceptionHandlerMapping.getHandler(request);

        final ModelAndView actual = handler.handle(request, response);

        assertThat(actual.getView()).isInstanceOf(JspView.class);
    }
}
