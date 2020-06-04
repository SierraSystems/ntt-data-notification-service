package com.nttdata.nttdatanotificationservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "notification")
public class NotificationServiceProperties {
    private String updateCardBase;

    public String getUpdateCardBase() {
        return updateCardBase;
    }

    public void setUpdateCardBase(String updateCardBase) {
        this.updateCardBase = updateCardBase;
    }

    private List<String> tokens;

    public List<String> getTokens() { return tokens; }

    public void setTokens(List<String> tokens) { this.tokens = tokens; }
}
