package core.di.aop.exception;

public class ProxyGenerateException extends RuntimeException{

    public static final String TARGET_MESSAGE = "프록시 대상이 없어 프록시 객체를 생성할 수 없습니다.";
    public static final String SUPER_TYPE_MESSAGE = "프록시 대상의 타입이 없어 프록시 객체를 생성할 수 없습니다.";
    public static final String ADVISOR_MESSAGE = "Advisor가 없어 프록시 객체를 생성할 수 없습니다.";

    public ProxyGenerateException(final String message) {
        super(message);
    }

    public static ProxyGenerateException target() {
        return new ProxyGenerateException(TARGET_MESSAGE);
    }

    public static ProxyGenerateException superInterface() {
        return new ProxyGenerateException(SUPER_TYPE_MESSAGE);
    }

    public static ProxyGenerateException advisor() {
        return new ProxyGenerateException(ADVISOR_MESSAGE);
    }
}
