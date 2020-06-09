package com.nttdata.nttdatanotificationservice.configuration;

import java.util.ArrayList;
import java.util.List;

public class WebHookParams {
  private List<WebHookUrls> webHookUrls = new ArrayList<>();

  public List<WebHookUrls> getWebHookUrls() {
    return webHookUrls;
  }

  public void setWebHookUrls(List<WebHookUrls> webHookUrls) {
    this.webHookUrls = webHookUrls;
  }

  public void addWebHookUrls(WebHookUrls webHookUrls) {
    this.webHookUrls.add(webHookUrls);
  }
}
