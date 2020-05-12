package ca.bc.gov.splunknotificationservice.Model;

import java.util.ArrayList;

public class TeamsCard {
  private String summary;

  private ArrayList<TeamsSection> sections;
  private ArrayList<> potentialAction;

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
}
