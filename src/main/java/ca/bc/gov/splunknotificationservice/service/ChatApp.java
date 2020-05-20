package ca.bc.gov.splunknotificationservice.service;

public enum ChatApp {

    TEAMS("teams"),
    ROCKET_CHAT("rocketChat");

    private String name;

    ChatApp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
