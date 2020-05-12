package ca.bc.gov.splunknotificationservice.Service;

import ca.bc.gov.splunknotificationservice.Configuration.SplunkProperites;
import ca.bc.gov.splunknotificationservice.Controller.AlertNotificationController;
import ca.bc.gov.splunknotificationservice.Model.RocketMessage;
import ca.bc.gov.splunknotificationservice.Model.SplunkAlert;
import ca.bc.gov.splunknotificationservice.Model.TeamsMessage;
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

    @Autowired
    SplunkProperites splunkProperites;
    Logger logger = LoggerFactory.getLogger(WebHookServiceImpl.class);
    public ResponseEntity<String> postMessage(SplunkAlert splunkAlert) {

        if (splunkProperites.getTeamsEnabled()) {
            logger.info("Posting message to Teams");
            post(splunkProperites.getTeamsUrl(), mapTeams(splunkAlert));
        }

        if (splunkProperites.getRocketEnabled()) {
            logger.info("Posting message to Rocket Chat");
            post(splunkProperites.getRocketUrl(), mapRocket(splunkAlert));
        }

        if (splunkProperites.getTeamsCardsEnabled()) {
            logger.info("Posting card to teams");
            //replace with map card
            post(splunkProperites.getTeamsUrl(), mapTeams(splunkAlert));
        }
        //TODO: Does splunk care about return?
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    private void post(String url, Object postObj) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(url, postObj, String.class);
        } catch (Exception e) {
            logger.error("Exception in post", e);
        }
    }


    private RocketMessage mapRocket(SplunkAlert splunkAlert) {
        RocketMessage  rocketMessage = new RocketMessage();
        String text = String.format("%s : %s", splunkAlert.getSearch_name(), splunkAlert.getResults_link());
        rocketMessage.setText(text);
        rocketMessage.setAlias(splunkProperites.getRocketMessageAlias());
        return rocketMessage;
    }
    private TeamsMessage mapTeams(SplunkAlert splunkAlert) {
        TeamsMessage teamsMessage = new TeamsMessage();
        teamsMessage.setText(splunkAlert.getResults_link());
        teamsMessage.setSummary(splunkAlert.getSearch_name());
        return teamsMessage;
    }
}
