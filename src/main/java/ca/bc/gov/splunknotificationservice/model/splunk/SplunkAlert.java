package ca.bc.gov.splunknotificationservice.model.splunk;

public class SplunkAlert {
    private String 	sid;
    private String results_link;
    private String search_name;
    private String owner;
    private String app;
    private SplunkResult result;

    public String getSid() {return sid;}

    public void setSid(String sid) {this.sid = sid;}

    public String getResults_link() {return results_link;}

    public void setResults_link(String results_link) {this.results_link = results_link;}

    public String getSearch_name() {return search_name;}

    public void setSearch_name(String search_name) {this.search_name = search_name;}

    public String getOwner() {return owner;}

    public void setOwner(String owner) { this.owner = owner; }

    public String getApp() {return app; }

    public void setApp(String app) {this.app = app;}

    public SplunkResult getResult() {return result;}

    public void setResult(SplunkResult result) {this.result = result;}

}
