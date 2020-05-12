package ca.bc.gov.splunknotificationservice.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "splunk")
public class SplunkProperites {
    private String teamsUrl;
    private String rocketUrl;
    private String rocketMessageAlias;
    private String teamsMessageSummary;

    public String getTeamsUrl() { return teamsUrl; }

    public void setTeamsUrl(String teamsUrl) { this.teamsUrl = teamsUrl; }

    public String getRocketUrl() { return rocketUrl; }

    public void setRocketUrl(String rocketUrl) { this.rocketUrl = rocketUrl; }

    public String getRocketMessageAlias() { return rocketMessageAlias; }

    public void setRocketMessageAlias(String rocketMessageAlias) { this.rocketMessageAlias = rocketMessageAlias; }

    public String getTeamsMessageSummary() { return teamsMessageSummary; }

    public void setTeamsMessageSummary(String teamsMessageSummary) { this.teamsMessageSummary = teamsMessageSummary; }
}
