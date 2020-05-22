package com.nttdata.splunknotificationservice.splunk.models;

import com.nttdata.splunknotificationservice.service.ChatApp;

public class SplunkWebHookUrls {

    private ChatApp chatApp;
    private String url;

    public ChatApp getChatApp() {
        return chatApp;
    }

    public void setChatApp(ChatApp chatApp) {
        this.chatApp = chatApp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
