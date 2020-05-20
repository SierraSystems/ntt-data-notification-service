package ca.bc.gov.splunknotificationservice.service;

import ca.bc.gov.splunknotificationservice.configuration.SplunkProperites;
import ca.bc.gov.splunknotificationservice.splunk.models.SplunkAlert;
import ca.bc.gov.splunknotificationservice.splunk.models.SplunkWebHookUrls;
import com.google.common.base.Strings;
import com.google.gson.Gson;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableConfigurationProperties(SplunkProperites.class)
public class WebHookServiceImpl implements WebHookService {

    private static final String ROCKETCHATTEMPLATE = "App: %s \n Search: %s \n Owner: %s \n Message: %s \n Link: %s ";

    @Autowired
    SplunkProperites splunkProperites;

    Logger logger = LoggerFactory.getLogger(WebHookServiceImpl.class);

    @Autowired
    private List<ChannelService> channelServices;

    public ResponseEntity<String> postMessage(SplunkAlert splunkAlert, String routes) {
        Gson gson = new Gson();

        byte[] decodedRoutesBytes = Base64.getUrlDecoder().decode(routes);
        String decodedRoutesUrl = new String(decodedRoutesBytes);

        SplunkWebHookUrls splunkWebHookUrls = gson.fromJson(decodedRoutesUrl, SplunkWebHookUrls.class);

        String rocketUrl = splunkWebHookUrls.getRocketUrl();
        String teamsUrl =  splunkWebHookUrls.getTeamsUrl();

        if (!Strings.isNullOrEmpty(rocketUrl)) {

            Optional<ChannelService> channelService = channelServices.stream().filter(x -> x.getChatApp() == ChatApp.ROCKET_CHAT).findFirst();

            channelService.ifPresent(service -> {
                post(rocketUrl, service.generatePayload(splunkAlert));
            });

            logger.info("Posting message to Rocket Chat");
        }

        if (!Strings.isNullOrEmpty(teamsUrl)) {

            Optional<ChannelService> channelService = channelServices.stream().filter(x -> x.getChatApp() == ChatApp.TEAMS).findFirst();

            channelService.ifPresent(service -> {
                post(teamsUrl, service.generatePayload(splunkAlert));
            });

        }

        //TODO: Does splunk care about return?
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    private void post(String url, Object postObj) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(url, postObj, String.class);
        } catch (Exception e) {
            logger.error("Exception in post", e);
        }
    }

}
