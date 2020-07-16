package core.di.factory.example;

import core.annotation.Component;
import core.di.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

@Component
public class TestFactoryBean implements FactoryBean<TestBean> {

    @Override
    public TestBean getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestBean.class);
        enhancer.setCallback(NoOp.INSTANCE);
        return (TestBean) enhancer.create(new Class[]{String.class}, new Object[]{"test"});
    }
}
