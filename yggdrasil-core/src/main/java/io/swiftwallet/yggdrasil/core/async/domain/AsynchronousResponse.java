package io.swiftwallet.yggdrasil.core.async.domain;

public class AsynchronousResponse {
    public enum Status {
        SUCCESS,
        FAILURE
    }

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}