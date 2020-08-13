package com.nttdata.nttdatanotificationservice.sources.notification.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Notification {
  private String appName;
  private String origin;
  private String owner;
  private String message;
  private String returnUrl;

  private Map<String, Object> details = new LinkedHashMap<>();

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getReturnUrl() {
    return returnUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }

  public Map<String, Object> getDetails() {
    return details;
  }

  public void addDetails(Map<String, Object> details) {
    this.details.putAll(details);
  }
}
