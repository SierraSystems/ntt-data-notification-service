package com.nttdata.nttdatanotificationservice.rocket.models;

import com.nttdata.nttdatanotificationservice.Config;

public class RocketMessage {

    private String alias;
    private String avatar;
    private String emoji;
    private String text;

    private RocketMessage(String alias, String avatar) {
        this.alias = alias;
        this.avatar = avatar;
    }

    public String getAlias() { return alias; }

    public String getAvatar() {  return avatar;  }

    public String getEmoji() { return emoji; }

    public void setEmoji(String emoji) { this.emoji = emoji; }

    public String getText() { return text;  }

    public void setText(String text) { this.text = text; }

    public static RocketMessage defaultNttMessage(String alias) {
        return new RocketMessage(alias, Config.IMAGE);
    }
}
