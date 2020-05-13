package ca.bc.gov.splunknotificationservice.model.teams;

public class TeamsMessage {
    private String text;
    private String summary;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
