package study.cglibProxy;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class MemberServiceCallbackFilter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if (method.getName().equals("regist")) {
            return 0; // RegistMemberInterceptor를 선택
        } else {
            return 1; // GetMemberInterceptor를 선택
        }
    }
}
