package core.mvc.tobe;

import core.annotation.web.RequestMethod;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import static core.util.PathPatternUtil.isUrlMatch;

public class HandlerKey implements Comparable<HandlerKey> {
    private static final PathPatternParser PATH_PATTERN_PARSER = new PathPatternParser();
    private String url;
    private PathPattern pathPattern;
    private RequestMethod requestMethod;

    public HandlerKey(String url, RequestMethod requestMethod) {
        this.url = url;
        this.pathPattern = PATH_PATTERN_PARSER.parse(url);
        this.requestMethod = requestMethod;
    }

    public String getUrl() {
        return url;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public boolean isMatch(HandlerKey handlerKey) {
        if (!requestMethod.equals(handlerKey.getRequestMethod())) {
            return false;
        }

        boolean result = isUrlMatch(url, handlerKey.getUrl());
        return result;
    }

    @Override
    public String toString() {
        return "HandlerKey [url=" + url + ", requestMethod=" + requestMethod + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((requestMethod == null) ? 0 : requestMethod.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HandlerKey other = (HandlerKey) obj;
        if (requestMethod != other.requestMethod)
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    // path를 정렬하고 좀더 상세한 path를 앞쪽에 두기 위함
    @Override
    public int compareTo(HandlerKey anotherKey) {
        return this.pathPattern.compareTo(anotherKey.pathPattern);
    }
}
