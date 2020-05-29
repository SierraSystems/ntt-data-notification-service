package com.nttdata.nttdatanotificationservice.sources.generic.models;

public class GenericAlert {
  private String username;
  private String appName;
  private String error;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
