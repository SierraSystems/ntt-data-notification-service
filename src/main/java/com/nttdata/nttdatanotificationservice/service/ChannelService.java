package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.sources.splunk.models.SplunkAlert;

public interface ChannelService {

    ChatApp getChatApp();

    Object generatePayload(SplunkAlert splunkAlert);

}
