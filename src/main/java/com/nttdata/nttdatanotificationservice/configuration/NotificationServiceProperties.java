package com.nttdata.nttdatanotificationservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "splunk")
public class NotificationServiceProperties {

    private List<String> tokens;

    public List<String> getTokens() { return tokens; }

    public void setTokens(List<String> tokens) { this.tokens = tokens; }
}
