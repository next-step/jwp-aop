package core.mvc;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterceptorTest {

    private static AtomicInteger count;
    private DispatcherServlet dispatcher;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        count = new AtomicInteger();

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac);
        dispatcher = new DispatcherServlet();
        dispatcher.addHandlerMapping(ahm);
        dispatcher.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        dispatcher.addInterceptor(new TestAuthorizationInterceptor());
        dispatcher.addInterceptor(new TestBeforeInterceptor());
        dispatcher.addInterceptor(new TestAfterInterceptor());

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @DisplayName("순서가 있는 인터셉터 테스트")
    @Test
    void intercept_order() throws ServletException, IOException {
        request.setRequestURI("/users");
        request.setMethod("GET");

        dispatcher.service(request, response);

        assertEquals(TestAuthorizationInterceptor.BEFORE_EXECUTED, 1);
        assertEquals(TestBeforeInterceptor.BEFORE_EXECUTED, 2);
        assertEquals(TestAfterInterceptor.BEFORE_EXECUTED, 3);
        assertEquals(TestAfterInterceptor.AFTER_EXECUTED, 4);
        assertEquals(TestBeforeInterceptor.AFTER_EXECUTED, 5);
        assertEquals(TestAuthorizationInterceptor.AFTER_EXECUTED, 6);
    }

    @DisplayName("권한 처리에 따른 인터셉터 테스트")
    @Test
    void intercept_authorization() throws ServletException, IOException {
        request.setRequestURI("/admin/user");
        request.setMethod("GET");

        dispatcher.service(request, response);

        assertEquals(TestAuthorizationInterceptor.BEFORE_EXECUTED, 1);
        assertEquals(TestBeforeInterceptor.BEFORE_EXECUTED, -1);
        assertEquals(TestAfterInterceptor.AFTER_EXECUTED, -1);
    }

    private static class TestAuthorizationInterceptor implements HandlerInterceptor {

        static Integer BEFORE_EXECUTED = -1;
        static Integer AFTER_EXECUTED = -1;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            BEFORE_EXECUTED = count.incrementAndGet();

            return !request.getRequestURI().startsWith("/admin");
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) {
            AFTER_EXECUTED = count.incrementAndGet();
        }
    }

    private static class TestAfterInterceptor implements HandlerInterceptor {

        static Integer BEFORE_EXECUTED = -1;
        static Integer AFTER_EXECUTED = -1;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            BEFORE_EXECUTED = count.incrementAndGet();
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) {
            AFTER_EXECUTED = count.incrementAndGet();
        }
    }

    private static class TestBeforeInterceptor implements HandlerInterceptor {

        static Integer BEFORE_EXECUTED = -1;
        static Integer AFTER_EXECUTED = -1;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            BEFORE_EXECUTED = count.incrementAndGet();
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) {
            AFTER_EXECUTED = count.incrementAndGet();
        }
    }

}
