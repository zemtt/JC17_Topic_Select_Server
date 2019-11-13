package com.jc17.select.serverCore.resources.Utils.ReturnObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReturnObject {
    private int error_code = 0;
    private Object data = null;

    @JsonProperty
    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    @JsonProperty
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
