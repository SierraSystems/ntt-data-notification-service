package ca.bc.gov.splunknotificationservice.service;

import ca.bc.gov.splunknotificationservice.configuration.SplunkProperites;
import ca.bc.gov.splunknotificationservice.model.rocket.RocketMessage;
import ca.bc.gov.splunknotificationservice.model.splunk.SplunkAlert;
import ca.bc.gov.splunknotificationservice.model.splunk.SplunkWebHookUrls;
import ca.bc.gov.splunknotificationservice.model.teams.TeamsCard;
import ca.bc.gov.splunknotificationservice.model.teams.TeamsFact;
import ca.bc.gov.splunknotificationservice.model.teams.TeamsPotentialActions;
import ca.bc.gov.splunknotificationservice.model.teams.TeamsSection;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Base64;

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

    private static final String ROCKETCHATTEMPLATE = "App: %s \n Search: %s \n Owner: %s \n Link: %s ";
    @Autowired
    SplunkProperites splunkProperites;
    Logger logger = LoggerFactory.getLogger(WebHookServiceImpl.class);
    public ResponseEntity<String> postMessage(SplunkAlert splunkAlert, String routes) {
        Gson gson = new Gson();

        byte[] decodedRoutesBytes = Base64.getUrlDecoder().decode(routes);
        String decodedRoutesUrl = new String(decodedRoutesBytes);

        SplunkWebHookUrls splunkWebHookUrls = gson.fromJson(decodedRoutesUrl, SplunkWebHookUrls.class);

        String rocketUrl = splunkWebHookUrls.getRocketUrl();
        String teamsUrl =  splunkWebHookUrls.getTeamsUrl();

        if (!Strings.isNullOrEmpty(rocketUrl)) {
            logger.info("Posting message to Rocket Chat");
            post(rocketUrl, mapRocket(splunkAlert));
        }

        if (!Strings.isNullOrEmpty(teamsUrl)) {
            logger.info("Posting card to teams");
            post(teamsUrl, mapTeamsCard(splunkAlert));
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


    private RocketMessage mapRocket(SplunkAlert splunkAlert) {
        RocketMessage  rocketMessage = new RocketMessage();
        String text = String.format(ROCKETCHATTEMPLATE, splunkAlert.getResult().getSource(), splunkAlert.getSearch_name(), splunkAlert.getOwner(), splunkAlert.getResults_link());
        rocketMessage.setText(text);
        rocketMessage.setAlias(splunkAlert.getSearch_name());
        return rocketMessage;
    }

    private TeamsCard mapTeamsCard(SplunkAlert splunkAlert) {
        TeamsCard teamsCard = new TeamsCard();
        teamsCard.setType("MessageCard");
        teamsCard.setContext("http://schema.org/extensions");
        teamsCard.setThemeColor("0076D7");
        teamsCard.setSummary(splunkAlert.getSearch_name());

        TeamsSection teamsSection = new TeamsSection();
        teamsSection.setActivityTitle(splunkAlert.getSearch_name());
        teamsSection.setActivitySubtitle(MessageFormat.format("From {0}", splunkAlert.getResult().getSource()));
        teamsSection.setActivityImage("https://teamsnodesample.azurewebsites.net/static/img/image4.png");

        TeamsFact teamsFactApp = new TeamsFact();
        teamsFactApp.setName("App");
        teamsFactApp.setValue(splunkAlert.getResult().getSource());

        TeamsFact teamsFactSearch = new TeamsFact();
        teamsFactSearch.setName("Search");
        teamsFactSearch.setValue(splunkAlert.getSearch_name());

        TeamsFact teamsFactOwner = new TeamsFact();
        teamsFactOwner.setName("Owner");
        teamsFactOwner.setValue(splunkAlert.getOwner());

        TeamsFact teamsFactMessage = new TeamsFact();
        teamsFactMessage.setName("Message");
        teamsFactMessage.setValue(splunkAlert.getResult().getMessage());

        TeamsFact teamsFactLink = new TeamsFact();
        teamsFactLink.setName("Link");
        teamsFactLink.setValue(splunkAlert.getResults_link());

        ArrayList<TeamsFact> facts = new ArrayList<>();
        facts.add(teamsFactApp);
        facts.add(teamsFactSearch);
        facts.add(teamsFactOwner);
        facts.add(teamsFactMessage);
//        facts.add(teamsFactLink);

        teamsSection.setFacts(facts);
        teamsSection.setMarkdown(true);

        ArrayList<TeamsSection> sections = new ArrayList<>();
        sections.add(teamsSection);

        teamsCard.setSections(sections);

        TeamsPotentialActions potentialActionsLink = new TeamsPotentialActions();
        potentialActionsLink.setType("ViewAction");
        potentialActionsLink.setName("View in Splunk");
        ArrayList<String> link = new ArrayList<>();
        link.add(splunkAlert.getResults_link());
        potentialActionsLink.setTarget(link);

        ArrayList<TeamsPotentialActions> potentialActions = new ArrayList<>();
        potentialActions.add(potentialActionsLink);

        teamsCard.setPotentialAction(potentialActions);

        return  teamsCard;
    }
}
