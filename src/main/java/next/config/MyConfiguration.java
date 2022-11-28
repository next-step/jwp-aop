package next.config;

import static java.util.Arrays.*;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import core.annotation.Bean;
import core.annotation.ComponentScan;
import core.annotation.Configuration;
import core.jdbc.JdbcTemplate;
import core.mvc.tobe.HandlerConverter;
import core.mvc.tobe.support.ArgumentResolver;
import core.mvc.tobe.support.HttpRequestArgumentResolver;
import core.mvc.tobe.support.HttpResponseArgumentResolver;
import core.mvc.tobe.support.ModelArgumentResolver;
import core.mvc.tobe.support.PathVariableArgumentResolver;
import core.mvc.tobe.support.RequestParamArgumentResolver;
import next.controller.GlobalExceptionHandler;
import next.security.LoginUserArgumentResolver;

@Configuration
@ComponentScan({ "next", "core" })
public class MyConfiguration {
    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem://localhost/~/jwp-aop;MODE=MySQL;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean
    public HandlerConverter handlerConverter() {
        HandlerConverter handlerConverter = new HandlerConverter();
        handlerConverter.setArgumentResolvers(defaultArgumentResolvers());
        handlerConverter.addArgumentResolver(loginUserArgumentResolver());
        return handlerConverter;
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    LoginUserArgumentResolver loginUserArgumentResolver() {
        return new LoginUserArgumentResolver();
    }

    List<ArgumentResolver> defaultArgumentResolvers() {
        return asList(
                new HttpRequestArgumentResolver(),
                new HttpResponseArgumentResolver(),
                new RequestParamArgumentResolver(),
                new PathVariableArgumentResolver(),
                new ModelArgumentResolver()
        );
    }
}
