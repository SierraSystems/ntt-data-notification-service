package ca.bc.gov.splunknotificationservice.Model.Teams;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamsInput {
  private String type;
  private String id;
  private Boolean isMultiline;
  private String title;

  @JsonProperty("@type")
  public String getType() {
    return type;
  }

  @JsonProperty("@type")
  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean getMultiline() {
    return isMultiline;
  }

  public void setMultiline(Boolean multiline) {
    isMultiline = multiline;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
