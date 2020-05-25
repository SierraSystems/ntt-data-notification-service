package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.splunk.models.SplunkAlert;

public interface ChannelService {

    String IMAGE = "https://user-images.githubusercontent.com/51387119/82707419-ddb1c600-9c30-11ea-8bfa-b3c624b23cdd.png";

    ChatApp getChatApp();

    Object generatePayload(SplunkAlert splunkAlert);

}
