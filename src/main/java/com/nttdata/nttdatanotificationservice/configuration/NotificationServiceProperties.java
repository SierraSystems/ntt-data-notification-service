package com.nttdata.nttdatanotificationservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "notification")
public class NotificationServiceProperties {
    private String teamsCardBase;

    public String getTeamsCardBase() {
        return teamsCardBase;
    }

    public void setTeamsCardBase(String teamsCardBase) {
        this.teamsCardBase = teamsCardBase;
    }

    private List<String> tokens;

    public List<String> getTokens() { return tokens; }

    public void setTokens(List<String> tokens) { this.tokens = tokens; }
}
