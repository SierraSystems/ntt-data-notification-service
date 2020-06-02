package com.nttdata.nttdatanotificationservice.configuration;

import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;

public class NotificationBody {
  private WebHookParams webHookParams = new WebHookParams();
  private Notification notification = new Notification();

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
}
