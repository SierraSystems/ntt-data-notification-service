package com.nttdata.nttdatanotificationservice.rocket;

import com.nttdata.nttdatanotificationservice.rocket.models.RocketMessage;
import com.nttdata.nttdatanotificationservice.service.ChannelService;
import com.nttdata.nttdatanotificationservice.service.ChatApp;
import com.nttdata.nttdatanotificationservice.splunk.models.SplunkAlert;
import org.springframework.stereotype.Service;

@Service
public class RocketChannelService implements ChannelService {

    private static final String ROCKETCHATTEMPLATE = "App: %s \n Search: %s \n Owner: %s \n Message: %s \n Link: %s ";

    @Override
    public ChatApp getChatApp() {
        return ChatApp.ROCKET_CHAT;
    }

    @Override
    public Object generatePayload(SplunkAlert splunkAlert) {
        RocketMessage rocketMessage = new RocketMessage();
        String text = String.format(ROCKETCHATTEMPLATE, splunkAlert.getResult().getSource(), splunkAlert.getSearch_name(), splunkAlert.getOwner(), splunkAlert.getResult().getMessage(), splunkAlert.getResults_link());
        rocketMessage.setText(text);
        rocketMessage.setAlias(splunkAlert.getSearch_name());
        rocketMessage.setAvatar("https://user-images.githubusercontent.com/51387119/82707419-ddb1c600-9c30-11ea-8bfa-b3c624b23cdd.png");
        return rocketMessage;
    }

}
