package core.exception;

import lombok.Getter;

@Getter
public enum JwpExceptionStatus {
    GET_FACTORY_OBJECT_ERROR("get factory object error");

    private String message;

    JwpExceptionStatus(String message) {
        this.message = message;
    }
}
