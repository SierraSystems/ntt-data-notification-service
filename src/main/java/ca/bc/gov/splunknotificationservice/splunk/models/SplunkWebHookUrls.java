package ca.bc.gov.splunknotificationservice.splunk.models;

import ca.bc.gov.splunknotificationservice.service.ChatApp;

public class SplunkWebHookUrls {

    private ChatApp chatApp;
    private String Url;

    public ChatApp getChatApp() {
        return chatApp;
    }

    public void setChatApp(ChatApp chatApp) {
        this.chatApp = chatApp;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
