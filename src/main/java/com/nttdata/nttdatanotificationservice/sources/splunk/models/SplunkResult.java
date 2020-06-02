package com.nttdata.nttdatanotificationservice.sources.splunk.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.LinkedHashMap;
import java.util.Map;

public class SplunkResult {
    private String source;
    private String message;
    private String dashboard;

    private Map<String, Object> details = new LinkedHashMap<>();

    @JsonAnySetter
    public void setDetails(String key, Object value) {
        details.put(key, value);
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public String getSource() { return source; }

    public void setSource(String source) { this.source = source; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getDashboard() {
        return dashboard;
    }

    public void setDashboard(String dashboard) {
        this.dashboard = dashboard;
    }
}
