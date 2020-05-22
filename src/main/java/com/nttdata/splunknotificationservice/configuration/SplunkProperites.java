package com.nttdata.splunknotificationservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "splunk")
public class SplunkProperites {

    private String token;
    private List<String> tokens;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getTokens() { return tokens; }

    public void setTokens(List<String> tokens) { this.tokens = tokens; }
}
