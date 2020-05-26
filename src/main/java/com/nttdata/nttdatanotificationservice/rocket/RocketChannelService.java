package com.nttdata.nttdatanotificationservice.rocket;

import com.nttdata.nttdatanotificationservice.rocket.models.RocketMessage;
import com.nttdata.nttdatanotificationservice.service.ChannelService;
import com.nttdata.nttdatanotificationservice.service.ChatApp;
import com.nttdata.nttdatanotificationservice.splunk.models.SplunkAlert;
import org.springframework.stereotype.Service;

@Service
public class RocketChannelService implements ChannelService {

    private static final String ROCKETCHATTEMPLATE = "%s: %s \n";

    @Override
    public ChatApp getChatApp() {
        return ChatApp.ROCKET_CHAT;
    }

    @Override
    public Object generatePayload(SplunkAlert splunkAlert) {
        RocketMessage rocketMessage = RocketMessage.defaultNttMessage(splunkAlert.getSearch_name());
        rocketMessage.setText(getRocketText(splunkAlert));

        return rocketMessage;
    }

    private String getRocketText(SplunkAlert splunkAlert) {
        final String[] text = {String.format(ROCKETCHATTEMPLATE, "App", splunkAlert.getResult().getSource())};
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Search", splunkAlert.getSearch_name()));
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Owner", splunkAlert.getOwner()));

        splunkAlert.getResult().getDetails().forEach((key, value) -> {
            text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, key, value.toString()));
        });

        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Message", splunkAlert.getResult().getMessage()));
        text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Search Link", splunkAlert.getResults_link()));

        if (splunkAlert.getResult().getDashboard() != null) {
            text[0] = text[0].concat(String.format(ROCKETCHATTEMPLATE, "Dashboard Link", splunkAlert.getResult().getDashboard()));
        }

        return text[0];
    }
}
