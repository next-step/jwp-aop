package core.mvc.tobe;

import core.annotation.web.RequestMethod;

import java.util.Objects;

import static core.util.PathPatternUtil.isUrlMatch;

public class RequestHandlerKey implements HandlerKey {
    private String url;
    private RequestMethod requestMethod;

    public RequestHandlerKey(String url, RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public String getUrl() {
        return url;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    @Override
    public boolean isMatch(HandlerKey handlerKey) {
        if (!(handlerKey instanceof RequestHandlerKey)) {
            return false;
        }

        RequestHandlerKey requestHandlerKey = (RequestHandlerKey) handlerKey;
        if (!requestMethod.equals(requestHandlerKey.getRequestMethod())) {
            return false;
        }

        boolean result = isUrlMatch(url, requestHandlerKey.getUrl());
        return result;
    }

    @Override
    public String toString() {
        return "HandlerKey [url=" + url + ", requestMethod=" + requestMethod + "]";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestHandlerKey that = (RequestHandlerKey) o;
        return Objects.equals(url, that.url) && requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, requestMethod);
    }
}
