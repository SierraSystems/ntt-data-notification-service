package com.nttdata.nttdatanotificationservice.sources.splunk.models;

import com.google.gson.annotations.SerializedName;
import com.nttdata.nttdatanotificationservice.service.AlertModel;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;

public class SplunkAlert implements AlertModel {
    private String 	sid;
    @SerializedName("results_link")
    private String resultsLink;
    @SerializedName("search_name")
    private String searchName;
    private String owner;
    private SplunkResult result;

    public SplunkAlert(String sid, String results_link, String search_name, String owner,
        SplunkResult result) {
        this.sid = sid;
        this.resultsLink = results_link;
        this.searchName = search_name;
        this.owner = owner;
        this.result = result;
    }

    public String getSid() {return sid;}

    public void setSid(String sid) {this.sid = sid;}

    public String getResultsLink() {return resultsLink;}

    public void setResultsLink(String resultsLink) {this.resultsLink = resultsLink;}

    public String getSearchName() {return searchName;}

    public void setSearchName(String searchName) {this.searchName = searchName;}

    public String getOwner() {return owner;}

    public void setOwner(String owner) { this.owner = owner; }

    public SplunkResult getResult() {return result;}

    public void setResult(SplunkResult result) {this.result = result;}

    @Override
    public Notification convertToAlert() {
        Notification notification = new Notification();

        notification.setAppName(this.getResult().getSource());
        notification.setOrigin(this.getSearchName());
        notification.setOwner(this.getOwner());
        notification.setMessage(this.getResult().getMessage());
        notification.setReturnUrl(this.getResultsLink());

        notification.addDetails(this.getResult().getDetails());

        return notification;
    }
}
