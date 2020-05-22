package com.nttdata.nttdatanotificationservice.teams;

import com.nttdata.nttdatanotificationservice.service.ChannelService;
import com.nttdata.nttdatanotificationservice.service.ChatApp;
import com.nttdata.nttdatanotificationservice.splunk.models.SplunkAlert;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsAction;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsCard;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsChoice;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsFact;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsInput;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsPotentialActions;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsSection;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
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

        TeamsFact teamsFactStatus = new TeamsFact();
        teamsFactStatus.setName("Status");
        teamsFactStatus.setValue("Open");

        ArrayList<TeamsFact> facts = new ArrayList<>();
        facts.add(teamsFactApp);
        facts.add(teamsFactSearch);
        facts.add(teamsFactOwner);
        facts.add(teamsFactMessage);
        facts.add(teamsFactStatus);

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

        TeamsPotentialActions potentialActionsStatus = new TeamsPotentialActions();
        potentialActionsStatus.setType("ActionCard");
        potentialActionsStatus.setName("Update Status");

        TeamsInput statusInput = new TeamsInput();
        statusInput.setType("MultichoiceInput");
        statusInput.setId("statuslist");
        statusInput.setTitle("Update Status");

        TeamsChoice assigned = new TeamsChoice();
        assigned.setDisplay("In Progress");
        assigned.setValue("1");

        TeamsChoice inProgress = new TeamsChoice();
        inProgress.setDisplay("In Review");
        inProgress.setValue("2");

        TeamsChoice closed = new TeamsChoice();
        closed.setDisplay("Closed");
        closed.setValue("3");

        ArrayList<TeamsChoice> choices = new ArrayList<>();
        choices.add(assigned);
        choices.add(inProgress);
        choices.add(closed);

        statusInput.setChoices(choices);

        ArrayList<TeamsInput> inputs = new ArrayList<>();
        inputs.add(statusInput);

        potentialActionsStatus.setInputs(inputs);

        TeamsAction statusAction = new TeamsAction();
        statusAction.setType("HttpPOST");
        statusAction.setName("OK");
        statusAction.setTarget("https://webhook.site/b1990094-ff1a-4668-b19e-e3bbddf8a717");

        ArrayList<TeamsAction> actions = new ArrayList<>();
        actions.add(statusAction);

        potentialActionsStatus.setActions(actions);

        ArrayList<TeamsPotentialActions> potentialActions = new ArrayList<>();
        potentialActions.add(potentialActionsLink);
        potentialActions.add(potentialActionsStatus);

        teamsCard.setPotentialAction(potentialActions);

        Gson teamsCardGson = new Gson();
        statusAction.setBody(teamsCardGson.toJson(teamsCard, TeamsCard.class).toString());

        return  teamsCard;
    }
}
