package core.di.factory.example;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.di.beans.factory.proxy.ProxyFactoryBean;
import core.di.beans.factory.support.proxy.example.HelloFactoryBean;
import core.di.beans.factory.support.proxy.example.NameMatchMethodPointcut;
import org.apache.commons.dbcp2.BasicDataSource;
import study.proxy.example.HelloTarget;
import study.proxy.example.cglib.MethodCallLogInterceptor;

import javax.sql.DataSource;

@Configuration
public class ExampleConfig {
    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:~/jwp-basic;AUTO_SERVER=TRUE");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public HelloFactoryBean helloFactoryBean() throws Exception {
        return new HelloFactoryBean();
    }

    @Bean
    public ProxyFactoryBean helloTarget() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(HelloTarget.class);
        pfBean.addAdvice(new MethodCallLogInterceptor());
        pfBean.setPointcut(new NameMatchMethodPointcut("say", "talk"));

        return pfBean;
    }
}
