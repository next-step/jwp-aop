package core.di.factory.example2;

import core.annotation.Bean;
import core.annotation.ComponentScan;
import core.annotation.Configuration;

/**
 * @author KingCjy
 */

// 재귀 stackoverflow 테스트
@ComponentScan("core.di.factory.example")
@Configuration
public class TestConfiguration2 {
    @Bean
    public MyBean myBean() {
        return new MyBean();
    }
}
