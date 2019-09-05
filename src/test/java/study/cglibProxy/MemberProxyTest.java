package study.cglibProxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MemberProxyTest {
    private static final Logger logger = LoggerFactory.getLogger(MemberProxyTest.class);

    @Test
    void memberServiceImplProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MemberServiceImpl.class);
        enhancer.setCallbacks(new Callback[] {new RegistMemberServiceInterceptor(),new GetMemberServiceInterceptor()});
        enhancer.setCallbackFilter(new MemberServiceCallbackFilter());
        Object proxy = enhancer.create();

        MemberServiceImpl memberService = (MemberServiceImpl) proxy;
        memberService.regist(new Member("1","changjun"));
        memberService.getMember("1");
    }
}
