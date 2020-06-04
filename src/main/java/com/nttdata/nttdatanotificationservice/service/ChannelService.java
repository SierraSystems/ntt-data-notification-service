package com.nttdata.nttdatanotificationservice.service;

import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;

public interface ChannelService {

    ChatApp getChatApp();

    Object generatePayload(Notification notification, String webHookUrl);

}
