package com.nttdata.nttdatanotificationservice.configuration;

import com.google.gson.Gson;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;

public class NotificationBody {
  private WebHookParams webHookParams = new WebHookParams();
  private Notification notification = new Notification();
  private String response;

  public WebHookParams getWebHookParams() {
    return webHookParams;
  }

  public void setWebHookParams(
      WebHookParams webHookParams) {
    this.webHookParams = webHookParams;
  }

  public Notification getNotification() {
    return notification;
  }

  public void setNotification(
      Notification notification) {
    this.notification = notification;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public String toJson() {
    Gson jsonParser = new Gson();
    return jsonParser.toJson(this, NotificationBody.class);
  }

}
