package com.nttdata.splunknotificationservice.splunk.models;

import java.util.ArrayList;
import java.util.List;

public class SplunkWebHookParams {
  private List<SplunkWebHookUrls> splunkWebHookUrls = new ArrayList<>();

  public List<SplunkWebHookUrls> getSplunkWebHookUrls() {
    return splunkWebHookUrls;
  }

  public void setSplunkWebHookUrls(
      List<SplunkWebHookUrls> splunkWebHookUrls) {
    this.splunkWebHookUrls = splunkWebHookUrls;
  }
}
