package io.swiftwallet.yggdrasil.core.adapters.domain;

import java.util.Map;

/**
 * Created by gibugeorge on 23/03/2017.
 */
public class ResourceEndpoint {

    private Map<String, String> params;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
