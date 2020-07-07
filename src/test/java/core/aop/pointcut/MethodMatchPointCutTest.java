package core.aop.pointcut;

import core.aop.BeanInterceptor;
import core.aop.BeanInterceptorTest;
import core.aop.pointcut.example.Target;
import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("메소드 명이 맞는 함수만 실행시키 위한 포인트 컷")
class MethodMatchPointCutTest {

    @Test
    @DisplayName("get으로 시작하는 함수만 advice를 실행 하는지")
    void matchTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallback(new BeanInterceptor(
                new BeanInterceptorTest.UpperStrAdvice(),
                new MethodMatchPointCut(
                        Arrays.stream(Target.class.getMethods())
                                .filter(method -> method.getName().startsWith("get"))
                                .collect(Collectors.toList())
                )
        ));

        Target target = (Target) enhancer.create();

        assertThat(target.getString()).isEqualTo("STRING");
        assertThat(target.stringValue()).isEqualTo("string");
    }
}