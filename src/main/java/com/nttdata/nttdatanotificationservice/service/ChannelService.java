package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.sources.alert.models.Alert;

public interface ChannelService {

    ChatApp getChatApp();

    Object generatePayload(Alert alert);

}
