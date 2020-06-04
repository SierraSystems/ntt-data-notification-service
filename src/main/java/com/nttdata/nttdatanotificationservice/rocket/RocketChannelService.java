package com.nttdata.nttdatanotificationservice.rocket;

import com.nttdata.nttdatanotificationservice.rocket.models.RocketMessage;
import com.nttdata.nttdatanotificationservice.service.ChannelService;
import com.nttdata.nttdatanotificationservice.service.ChatApp;
import com.nttdata.nttdatanotificationservice.sources.notification.models.Notification;
import org.springframework.stereotype.Service;

@Service
public class RocketChannelService implements ChannelService {

    private static final String ROCKETCHATTEMPLATE = "%s: %s \n";

    @Override
    public ChatApp getChatApp() {
        return ChatApp.ROCKET_CHAT;
    }

    @Override
    public Object generatePayload(Notification notification, String webHookUrl) {
        RocketMessage rocketMessage = RocketMessage.defaultNttMessage(notification.getAppName());
        rocketMessage.setText(getRocketText(notification));

        return rocketMessage;
    }

    @SuppressWarnings("java:S1602")
    private String getRocketText(Notification notification) {
        final String[] text = {String.format(ROCKETCHATTEMPLATE, "App", notification.getAppName())};
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Search", notification.getOrigin()));
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Owner", notification.getOwner()));

        notification.getDetails().forEach((key, value) -> {
            text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, key, value.toString()));
        });

        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Message", notification.getMessage()));
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Search Link", notification.getReturnUrl()));

        return text[0];
    }
}
