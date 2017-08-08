package in.cfcomputing.yggdrasil.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by gibugeorge on 23/01/2017.
 */
@ConfigurationProperties(prefix = "routing-engine")
public class RoutingEngineProperties {

    private boolean jmxEnabled = true;
    private boolean streamCachingEnabled;
    private boolean traceEnabled;
    private boolean messageHistoryEnabled = true;
    private boolean handleFault;
    private boolean autoStartup = true;
    private boolean allowUseOriginalMessage = true;

    public boolean isJmxEnabled() {
        return jmxEnabled;
    }

    public void setJmxEnabled(boolean jmxEnabled) {
        this.jmxEnabled = jmxEnabled;
    }

    public boolean isStreamCachingEnabled() {
        return streamCachingEnabled;
    }

    public void setStreamCachingEnabled(boolean streamCachingEnabled) {
        this.streamCachingEnabled = streamCachingEnabled;
    }

    public boolean isTraceEnabled() {
        return traceEnabled;
    }

    public void setTraceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
    }

    public boolean isMessageHistoryEnabled() {
        return messageHistoryEnabled;
    }

    public void setMessageHistoryEnabled(boolean messageHistoryEnabled) {
        this.messageHistoryEnabled = messageHistoryEnabled;
    }

    public boolean isHandleFault() {
        return handleFault;
    }

    public void setHandleFault(boolean handleFault) {
        this.handleFault = handleFault;
    }

    public boolean isAutoStartup() {
        return autoStartup;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public boolean isAllowUseOriginalMessage() {
        return allowUseOriginalMessage;
    }

    public void setAllowUseOriginalMessage(boolean allowUseOriginalMessage) {
        this.allowUseOriginalMessage = allowUseOriginalMessage;
    }

}
