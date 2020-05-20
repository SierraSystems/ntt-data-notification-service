package ca.bc.gov.splunknotificationservice.service;

import ca.bc.gov.splunknotificationservice.model.rocket.RocketMessage;
import ca.bc.gov.splunknotificationservice.model.splunk.SplunkAlert;
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
        return rocketMessage;
    }

}
