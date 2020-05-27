package com.nttdata.nttdatanotificationservice.teams;

import com.google.gson.Gson;
import com.nttdata.nttdatanotificationservice.splunk.models.SplunkAlert;
import com.nttdata.nttdatanotificationservice.splunk.models.SplunkResult;
import com.nttdata.nttdatanotificationservice.teams.models.TeamsCard;
import org.junit.jupiter.api.*;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeamsChannelServiceTest {

    private TeamsChannelService sut;

    private static final String splunkAlertJson = "{\n" +
            "\t\"result\": {\n" +
            "\t\t\"message\" : \"message\",\n" +
            "\t\t\"other\" : \"other\",\n" +
            "\t\t\"_raw\" : \"_raw\",\n" +
            "\t\t\"source\": \"source\",\n" +
            "\t\t\"dashboard\": \"dashboard_link\"\n" +
            "\t},\n" +
            "\t\"sid\" : \"sid\",\n" +
            "\t\"results_link\" : \"result_links\",\n" +
            "\t\"search_name\" : \"search_name\",\n" +
            "\t\"owner\" : \"owner\",\n" +
            "\t\"app\" : \"app\"\n" +
            "}";

    private static final String teamsResult = "{\"type\":\"MessageCard\",\"context\":\"http://schema.org/extensions\",\"themeColor\":\"0076D7\",\"summary\":\"search_name\",\"sections\":[{\"activityTitle\":\"search_name\",\"activitySubtitle\":\"From source\",\"activityImage\":\"https://user-images.githubusercontent.com/51387119/82707419-ddb1c600-9c30-11ea-8bfa-b3c624b23cdd.png\",\"facts\":[{\"name\":\"App\",\"value\":\"source\"},{\"name\":\"Search\",\"value\":\"search_name\"},{\"name\":\"Owner\",\"value\":\"owner\"},{\"name\":\"other\",\"value\":\"other\"},{\"name\":\"Message\",\"value\":\"message\"},{\"name\":\"Status\",\"value\":\"Open\"}],\"markdown\":true}],\"potentialAction\":[{\"type\":\"ViewAction\",\"name\":\"View in Splunk\",\"target\":[\"result_links\"],\"inputs\":[],\"actions\":[]},{\"type\":\"ViewAction\",\"name\":\"Splunk Dashboard\",\"target\":[\"dashboard_link\"],\"inputs\":[],\"actions\":[]},{\"type\":\"ActionCard\",\"name\":\"Update Status\",\"target\":[],\"inputs\":[{\"type\":\"MultichoiceInput\",\"id\":\"statuslist\",\"title\":\"Update Status\",\"choices\":[{\"display\":\"In Progress\",\"value\":\"1\"},{\"display\":\"In Review\",\"value\":\"2\"},{\"display\":\"Closed\",\"value\":\"3\"}]}],\"actions\":[{\"type\":\"HttpPOST\",\"name\":\"OK\",\"target\":\"https://webhook.site/b1990094-ff1a-4668-b19e-e3bbddf8a717\"}]}]}";

    @BeforeAll
    public void setUp() {
        sut = new TeamsChannelService();
    }


    @Test
    @DisplayName("CASE 1: with valid splunk notification")
    public void withValidSplunkNotificationShouldProduceTeamObject() {

        Gson gson = new Gson();
        SplunkAlert splunkAlert = gson.fromJson(splunkAlertJson, SplunkAlert.class);
        //Work around to get other fields mapped. Gson wouldn't map.
        splunkAlert.getResult().setDetails("other","other");

        TeamsCard actual = (TeamsCard) sut.generatePayload(splunkAlert);


        Assertions.assertEquals("http://schema.org/extensions", actual.getContext());
        Assertions.assertEquals("search_name", actual.getSummary());
        Assertions.assertEquals("0076D7", actual.getThemeColor());
        Assertions.assertEquals("MessageCard", actual.getType());
        Assertions.assertEquals(3, actual.getPotentialAction().size());


        Assertions.assertEquals("View in Splunk", actual.getPotentialAction().get(0).getName());
        Assertions.assertEquals("ViewAction", actual.getPotentialAction().get(0).getType());
        Assertions.assertEquals(0, actual.getPotentialAction().get(0).getActions().size());

        Assertions.assertEquals(0, actual.getPotentialAction().get(0).getInputs().size());


        Assertions.assertEquals("Splunk Dashboard", actual.getPotentialAction().get(1).getName());
        Assertions.assertEquals("ViewAction", actual.getPotentialAction().get(1).getType());
        Assertions.assertEquals(0, actual.getPotentialAction().get(1).getActions().size());

        Assertions.assertEquals(0, actual.getPotentialAction().get(1).getInputs().size());

        Assertions.assertEquals("Update Status", actual.getPotentialAction().get(2).getName());
        Assertions.assertEquals("ActionCard", actual.getPotentialAction().get(2).getType());
        Assertions.assertEquals(1, actual.getPotentialAction().get(2).getActions().size());

        Assertions.assertEquals("HttpPOST", actual.getPotentialAction().get(2).getActions().get(0).getType());
        Assertions.assertEquals("https://webhook.site/b1990094-ff1a-4668-b19e-e3bbddf8a717", actual.getPotentialAction().get(2).getActions().get(0).getTarget());
        Assertions.assertEquals("OK", actual.getPotentialAction().get(2).getActions().get(0).getName());
        Assertions.assertEquals(teamsResult, actual.getPotentialAction().get(2).getActions().get(0).getBody());



        Assertions.assertEquals(1, actual.getPotentialAction().get(2).getInputs().size());

        Assertions.assertEquals("statuslist", actual.getPotentialAction().get(2).getInputs().get(0).getId());
        Assertions.assertEquals("Update Status", actual.getPotentialAction().get(2).getInputs().get(0).getTitle());
        Assertions.assertEquals("MultichoiceInput", actual.getPotentialAction().get(2).getInputs().get(0).getType());
        Assertions.assertEquals(null, actual.getPotentialAction().get(2).getInputs().get(0).getMultiline());
        Assertions.assertEquals(null, actual.getPotentialAction().get(2).getInputs().get(0).getMultiSelect());

        Assertions.assertEquals(3, actual.getPotentialAction().get(2).getInputs().get(0).getChoices().size());

        Assertions.assertEquals("In Progress", actual.getPotentialAction().get(2).getInputs().get(0).getChoices().get(0).getDisplay());
        Assertions.assertEquals("1", actual.getPotentialAction().get(2).getInputs().get(0).getChoices().get(0).getValue());
        Assertions.assertEquals("In Review", actual.getPotentialAction().get(2).getInputs().get(0).getChoices().get(1).getDisplay());
        Assertions.assertEquals("2", actual.getPotentialAction().get(2).getInputs().get(0).getChoices().get(1).getValue());
        Assertions.assertEquals("Closed", actual.getPotentialAction().get(2).getInputs().get(0).getChoices().get(2).getDisplay());
        Assertions.assertEquals("3", actual.getPotentialAction().get(2).getInputs().get(0).getChoices().get(2).getValue());

        Assertions.assertEquals(1, actual.getSections().size());

        Assertions.assertEquals("https://user-images.githubusercontent.com/51387119/82707419-ddb1c600-9c30-11ea-8bfa-b3c624b23cdd.png", actual.getSections().get(0).getActivityImage());
        Assertions.assertEquals("From source", actual.getSections().get(0).getActivitySubtitle());
        Assertions.assertEquals("search_name", actual.getSections().get(0).getActivityTitle());
        Assertions.assertEquals(6, actual.getSections().get(0).getFacts().size());

        Assertions.assertEquals("App", actual.getSections().get(0).getFacts().get(0).getName());
        Assertions.assertEquals("source", actual.getSections().get(0).getFacts().get(0).getValue());
        Assertions.assertEquals("Search", actual.getSections().get(0).getFacts().get(1).getName());
        Assertions.assertEquals("search_name", actual.getSections().get(0).getFacts().get(1).getValue());
        Assertions.assertEquals("Owner", actual.getSections().get(0).getFacts().get(2).getName());
        Assertions.assertEquals("owner", actual.getSections().get(0).getFacts().get(2).getValue());
        Assertions.assertEquals("other", actual.getSections().get(0).getFacts().get(3).getName());
        Assertions.assertEquals("other", actual.getSections().get(0).getFacts().get(3).getValue());
        Assertions.assertEquals("Message", actual.getSections().get(0).getFacts().get(4).getName());
        Assertions.assertEquals("message", actual.getSections().get(0).getFacts().get(4).getValue());
        Assertions.assertEquals("Status", actual.getSections().get(0).getFacts().get(5).getName());
        Assertions.assertEquals("Open", actual.getSections().get(0).getFacts().get(5).getValue());

        Assertions.assertEquals(true, actual.getSections().get(0).getMarkdown());

    }


}
