package ca.bc.gov.splunknotificationservice.teams;

import ca.bc.gov.splunknotificationservice.service.ChannelService;
import ca.bc.gov.splunknotificationservice.service.ChatApp;
import ca.bc.gov.splunknotificationservice.splunk.models.SplunkAlert;
import ca.bc.gov.splunknotificationservice.teams.models.TeamsCard;
import ca.bc.gov.splunknotificationservice.teams.models.TeamsFact;
import ca.bc.gov.splunknotificationservice.teams.models.TeamsPotentialActions;
import ca.bc.gov.splunknotificationservice.teams.models.TeamsSection;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;

@Service
public class TeamsChannelService implements ChannelService {

    @Override
    public ChatApp getChatApp() {
        return ChatApp.TEAMS;
    }

    @Override
    public Object generatePayload(SplunkAlert splunkAlert) {
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
