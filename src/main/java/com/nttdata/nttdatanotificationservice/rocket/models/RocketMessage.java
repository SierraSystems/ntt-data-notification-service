package com.nttdata.nttdatanotificationservice.rocket.models;

public class RocketMessage {

    private static final String AVATAR = "https://user-images.githubusercontent.com/51387119/82707419-ddb1c600-9c30-11ea-8bfa-b3c624b23cdd.png";
    private static final String EMOJI = "";

    private String alias;
    private String avatar;
    private String emoji;
    private String text;

    public RocketMessage(String alias, String avatar, String emoji) {
        this.alias = alias;
        this.avatar = avatar;
        this.emoji = emoji;
    }

    public String getAlias() { return alias; }

    public void setAlias(String alias) {  this.alias = alias; }

    public String getAvatar() {  return avatar;  }

    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getEmoji() { return emoji; }

    public void setEmoji(String emoji) { this.emoji = emoji; }

    public String getText() { return text;  }

    public void setText(String text) { this.text = text; }

    public static RocketMessage defaultNttMessage(String alias) {
        return new RocketMessage(alias, AVATAR, EMOJI);
    }
}
