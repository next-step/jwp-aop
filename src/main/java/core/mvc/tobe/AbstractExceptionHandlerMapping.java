package core.mvc.tobe;

public abstract class AbstractExceptionHandlerMapping implements ExceptionHandlerMapping, Ordered {

    private int order;

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
