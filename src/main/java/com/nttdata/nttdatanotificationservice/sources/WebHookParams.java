package com.nttdata.nttdatanotificationservice.sources;

import java.util.ArrayList;
import java.util.List;

public class WebHookParams {
  private List<WebHookUrls> webHookUrls = new ArrayList<>();

  public List<WebHookUrls> getWebHookUrls() {
    return webHookUrls;
  }

  public void setWebHookUrls(
      List<WebHookUrls> webHookUrls) {
    this.webHookUrls = webHookUrls;
  }
}
