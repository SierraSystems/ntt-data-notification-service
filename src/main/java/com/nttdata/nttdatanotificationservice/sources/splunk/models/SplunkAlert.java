package com.nttdata.nttdatanotificationservice.sources.splunk.models;

import com.nttdata.nttdatanotificationservice.service.AlertModel;
import com.nttdata.nttdatanotificationservice.sources.alert.models.Alert;

public class SplunkAlert implements AlertModel {
    private String 	sid;
    private String results_link;
    private String search_name;
    private String owner;
    private SplunkResult result;

    public String getSid() {return sid;}

    public void setSid(String sid) {this.sid = sid;}

    public String getResults_link() {return results_link;}

    public void setResults_link(String results_link) {this.results_link = results_link;}

    public String getSearch_name() {return search_name;}

    public void setSearch_name(String search_name) {this.search_name = search_name;}

    public String getOwner() {return owner;}

    public void setOwner(String owner) { this.owner = owner; }

    public SplunkResult getResult() {return result;}

    public void setResult(SplunkResult result) {this.result = result;}

    @Override
    public Alert convertToAlert() {
        Alert alert = new Alert();

        alert.setAppName(this.getResult().getSource());
        alert.setOrigin(this.getSearch_name());
        alert.setOwner(this.getOwner());
        alert.setMessage(this.getResult().getMessage());
        alert.setReturnUrl(this.getResults_link());

        alert.addDetails("dashboard", this.getResult().getDashboard());
        alert.addDetails(this.getResult().getDetails());

        return alert;
    }
}
