package core.mvc.tobe;

public interface Ordered {

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    int getOrder();
}
