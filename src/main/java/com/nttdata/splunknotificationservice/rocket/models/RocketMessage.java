package ca.bc.gov.splunknotificationservice.rocket.models;

public class RocketMessage {
    private String alias;
    private String avatar;
    private String emoji;
    private String text;

    public String getAlias() { return alias; }

    public void setAlias(String alias) {  this.alias = alias; }



    public String getAvatar() {  return avatar;  }

    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getEmoji() { return emoji; }

    public void setEmoji(String emoji) { this.emoji = emoji; }

    public String getText() { return text;  }

    public void setText(String text) { this.text = text; }

}
