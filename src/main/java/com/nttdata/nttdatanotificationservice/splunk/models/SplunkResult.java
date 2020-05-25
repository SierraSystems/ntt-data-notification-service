package com.nttdata.nttdatanotificationservice.splunk.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.LinkedHashMap;
import java.util.Map;

public class SplunkResult {
    private String _raw;
    private String source;
    private String message;

    private Map<String, Object> details = new LinkedHashMap<>();

    @JsonAnySetter
    void setDetails(String key, Object value) {
        details.put(key, value);
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public String get_raw() { return _raw; }

    public void set_raw(String _raw) { this._raw = _raw; }

    public String getSource() { return source; }

    public void setSource(String source) { this.source = source; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}
