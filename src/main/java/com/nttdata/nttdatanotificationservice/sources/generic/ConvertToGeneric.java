package com.nttdata.nttdatanotificationservice.sources.generic;

import com.nttdata.nttdatanotificationservice.sources.generic.models.GenericAlert;
import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkAlert;

public class ConvertToGeneric {

  public static GenericAlert splunkToGeneric(SplunkAlert splunkAlert) {
    GenericAlert genericAlert = new GenericAlert();

    genericAlert.setAppName(splunkAlert.getResult().getSource());
    genericAlert.setOrigin(splunkAlert.getSearch_name());
    genericAlert.setOwner(splunkAlert.getOwner());
    genericAlert.setMessage(splunkAlert.getResult().getMessage());
    genericAlert.setReturnUrl(splunkAlert.getResults_link());

    genericAlert.addDetails("dashboard" ,splunkAlert.getResult().getDashboard());
    genericAlert.addDetails(splunkAlert.getResult().getDetails());

    return genericAlert;
  }
}
