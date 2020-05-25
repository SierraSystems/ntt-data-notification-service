package com.nttdata.nttdatanotificationservice.splunk.models;

public class SplunkResult {
    private String _raw;
    private String source;
    private String message;


    public String get_raw() { return _raw; }

    public void set_raw(String _raw) { this._raw = _raw; }

    public String getSource() { return source; }

    public void setSource(String source) { this.source = source; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}
