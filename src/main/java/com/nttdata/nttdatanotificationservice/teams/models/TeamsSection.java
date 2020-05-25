package com.nttdata.nttdatanotificationservice.teams.models;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TeamsSection {

  private String activityTitle;
  private String activitySubtitle;
  private String activityImage;
  private List<TeamsFact> facts = new ArrayList<>();
  private Boolean markdown;


  private TeamsSection(String activityTitle, String activitySubtitle, String activityImage, Boolean markdown) {
    this.activityTitle = activityTitle;
    this.activitySubtitle = activitySubtitle;
    this.activityImage = activityImage;
    this.markdown = markdown;
  }

  public String getActivityTitle() {
    return activityTitle;
  }

  public String getActivitySubtitle() {
    return activitySubtitle;
  }

  public String getActivityImage() {
    return activityImage;
  }

  public List<TeamsFact> getFacts() {
    return facts;
  }

  public void addFact(TeamsFact fact) {
    this.facts.add(fact);
  }

  public Boolean getMarkdown() {
    return markdown;
  }


  public static TeamsSection defaultNttSection(String activityTitle, String activitySubtitle, String image) {

    String activitySubtitleFormated = MessageFormat.format("From {0}", activitySubtitle);

    return new TeamsSection(activityTitle, activitySubtitleFormated, image, true);
    
  }
}
