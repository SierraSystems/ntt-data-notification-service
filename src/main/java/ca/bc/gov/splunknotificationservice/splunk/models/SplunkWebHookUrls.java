package ca.bc.gov.splunknotificationservice.splunk.models;

public class SplunkWebHookUrls {
    private String rocketUrl;
    private String teamsUrl;

    public String getRocketUrl() {
        return rocketUrl;
    }

    public void setRocketUrl(String rocketUrl) {
        this.rocketUrl = rocketUrl;
    }

    public String getTeamsUrl() {
        return teamsUrl;
    }

    public void setTeamsUrl(String teamsUrl) {
        this.teamsUrl = teamsUrl;
    }
}
