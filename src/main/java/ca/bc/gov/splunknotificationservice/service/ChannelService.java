package ca.bc.gov.splunknotificationservice.service;

import ca.bc.gov.splunknotificationservice.splunk.models.SplunkAlert;

public interface ChannelService {

    ChatApp getChatApp();

    Object generatePayload(SplunkAlert splunkAlert);

}
