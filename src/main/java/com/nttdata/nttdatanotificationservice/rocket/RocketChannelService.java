package com.nttdata.nttdatanotificationservice.rocket;

import com.nttdata.nttdatanotificationservice.rocket.models.RocketMessage;
import com.nttdata.nttdatanotificationservice.service.ChannelService;
import com.nttdata.nttdatanotificationservice.service.ChatApp;
import com.nttdata.nttdatanotificationservice.sources.alert.models.Alert;
import org.springframework.stereotype.Service;

@Service
public class RocketChannelService implements ChannelService {

    private static final String ROCKETCHATTEMPLATE = "%s: %s \n";

    @Override
    public ChatApp getChatApp() {
        return ChatApp.ROCKET_CHAT;
    }

    @Override
    public Object generatePayload(Alert alert) {
        RocketMessage rocketMessage = RocketMessage.defaultNttMessage(alert.getAppName());
        rocketMessage.setText(getRocketText(alert));

        return rocketMessage;
    }

    @SuppressWarnings("java:S1602")
    private String getRocketText(Alert alert) {
        final String[] text = {String.format(ROCKETCHATTEMPLATE, "App", alert.getAppName())};
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Search", alert.getOrigin()));
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Owner", alert.getOwner()));

        alert.getDetails().forEach((key, value) -> {
            text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, key, value.toString()));
        });

        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Message", alert.getMessage()));
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Search Link", alert.getReturnUrl()));

        return text[0];
    }
}
