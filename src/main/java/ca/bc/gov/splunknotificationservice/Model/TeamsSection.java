package ca.bc.gov.splunknotificationservice.Model;

import java.util.ArrayList;

public class TeamsSection {
  private String activityTitle;
  private String activitySubtitle;
  private String activityImage;
  private ArrayList<TeamsFact> facts;
  private Boolean markdown;

  public String getActivityTitle() {
    return activityTitle;
  }

  public void setActivityTitle(String activityTitle) {
    this.activityTitle = activityTitle;
  }

  public String getActivitySubtitle() {
    return activitySubtitle;
  }

  public void setActivitySubtitle(String activitySubtitle) {
    this.activitySubtitle = activitySubtitle;
  }

  public String getActivityImage() {
    return activityImage;
  }

  public void setActivityImage(String activityImage) {
    this.activityImage = activityImage;
  }

  public ArrayList<TeamsFact> getFacts() {
    return facts;
  }

  public void setFacts(ArrayList<TeamsFact> facts) {
    this.facts = facts;
  }

  public Boolean getMarkdown() {
    return markdown;
  }

  public void setMarkdown(Boolean markdown) {
    this.markdown = markdown;
  }
}
