package com.nttdata.nttdatanotificationservice.rocket;

import com.nttdata.nttdatanotificationservice.rocket.models.RocketMessage;
import com.nttdata.nttdatanotificationservice.service.ChannelService;
import com.nttdata.nttdatanotificationservice.service.ChatApp;
import com.nttdata.nttdatanotificationservice.sources.generic.models.GenericAlert;
import org.springframework.stereotype.Service;

@Service
public class RocketChannelService implements ChannelService {

    private static final String ROCKETCHATTEMPLATE = "%s: %s \n";

    @Override
    public ChatApp getChatApp() {
        return ChatApp.ROCKET_CHAT;
    }

    @Override
    public Object generatePayload(GenericAlert genericAlert) {
        RocketMessage rocketMessage = RocketMessage.defaultNttMessage(genericAlert.getAppName());
        rocketMessage.setText(getRocketText(genericAlert));

        return rocketMessage;
    }

    @SuppressWarnings("java:S1602")
    private String getRocketText(GenericAlert genericAlert) {
        final String[] text = {String.format(ROCKETCHATTEMPLATE, "App", genericAlert.getAppName())};
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Search", genericAlert.getOrigin()));
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Owner", genericAlert.getOwner()));

        genericAlert.getDetails().forEach((key, value) -> {
            text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, key, value.toString()));
        });

        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Message", genericAlert.getMessage()));
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Search Link", genericAlert.getReturnUrl()));

        return text[0];
    }
}
