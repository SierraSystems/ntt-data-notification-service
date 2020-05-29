package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.sources.generic.models.GenericAlert;

public interface ChannelService {

    ChatApp getChatApp();

    Object generatePayload(GenericAlert genericAlert);

}
