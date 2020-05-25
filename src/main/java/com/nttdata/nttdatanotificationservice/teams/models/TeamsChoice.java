package com.nttdata.nttdatanotificationservice.teams.models;

public class TeamsChoice {
    private String display;
    private String value;

    public TeamsChoice(String display, String value) {
        this.display = display;
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public String getValue() {
        return value;
    }
}

