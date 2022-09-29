//package core.aop;
//
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.List;
//import net.sf.cglib.proxy.Enhancer;
//
//public class TransactionFactoryBean implements FactoryBean<Object> {
//
//    private final Enhancer enhancer = new Enhancer();
//    private final Object target;
//    private final List<Advisor> advisors;
//
//    public TransactionFactoryBean(Object target) {
//        this.target = target;
//        Advice advice = new TransactionalAdvice();
//        PointCut pointCut = new TransactionalPointCut();
//        Advisor advisor = new Advisor(advice, pointCut);
//        this.advisors = Arrays.asList(advisor);
//    }
//
//    @Override
//    public Object getObject() throws Exception {
//        Method[] declaredMethods = target.getClass().getDeclaredMethods();
//        for (Method declaredMethod : declaredMethods) {
//            for (Advisor advisor : advisors) {
//                if (advisor.fitPointCut(declaredMethod, target, declaredMethod.getParameterTypes())) {
//                    enhancer.setSuperclass(target.getClass());
//                    enhancer.setCallback(advisor.getAdvice());
//                    return enhancer.create();
//                }
//            }
//        }
//
//        return target;
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return this.target.getClass();
//    }
//
//}
