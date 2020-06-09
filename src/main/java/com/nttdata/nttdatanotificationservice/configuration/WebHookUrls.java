package com.nttdata.nttdatanotificationservice.configuration;

import com.nttdata.nttdatanotificationservice.service.ChatApp;

public class WebHookUrls {

    private ChatApp chatApp;
    private String url;

    public WebHookUrls(ChatApp chatApp, String url) {
        this.chatApp = chatApp;
        this.url = url;
    }

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
