package com.nttdata.splunknotificationservice.service;

import com.nttdata.splunknotificationservice.splunk.models.SplunkAlert;

public interface ChannelService {

    ChatApp getChatApp();

    Object generatePayload(SplunkAlert splunkAlert);

}
