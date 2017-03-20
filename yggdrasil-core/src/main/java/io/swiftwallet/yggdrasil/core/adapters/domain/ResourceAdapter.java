package io.swiftwallet.yggdrasil.core.adapters.domain;

import java.util.Map;

/**
 * Created by gibugeorge on 20/03/2017.
 */
public class ResourceAdapter {

    private String adapterCode;
    private String host;
    private String uri;
    private String protocol;

    private Map<String, String> params;

    public String getAdapterCode() {
        return adapterCode;
    }

    public void setAdapterCode(String adapterCode) {
        this.adapterCode = adapterCode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
