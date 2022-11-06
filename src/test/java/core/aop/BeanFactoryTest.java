package core.aop;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.di.beans.factory.ProxyFactoryBean;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import study.cglib.mission1.Hello;
import study.cglib.mission1.HelloTarget;

public class BeanFactoryTest {

    @DisplayName("팩토리빈을 BeanFactory에서 꺼낼 수 있다.")
    @Test
    void name2() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfiguration.class);

        var helloTargetA = applicationContext.getBean(HelloTarget.class);
        var helloTargetB = applicationContext.getBean(HelloTarget.class);

        assertThat(helloTargetA).isEqualTo(helloTargetB);
    }

    @DisplayName("빈팩토리 빈은 객체를 생성할 수 있다")
    @Test
    void name() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfiguration.class);

        var bean = applicationContext.getBean(ProxyFactoryBean.class);
        Hello hello = (Hello)bean.getObject();

        var actual = hello.sayHi("hongbin");

        assertThat(actual).isEqualTo("HI HONGBIN");
    }
}
