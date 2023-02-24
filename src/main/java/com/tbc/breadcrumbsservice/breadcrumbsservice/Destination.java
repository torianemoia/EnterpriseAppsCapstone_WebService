package com.tbc.breadcrumbsservice.breadcrumbsservice;


public class Destination {

  private int destinationID;
  private String destinationName;
  private String destinationTag;
  private String directions;
  private int imgID;
  private int userID;

  public int getDestinationID() {
    return destinationID;
  }
  
  public void setDestinationID(int destinationID) {
    this.destinationID = destinationID;
  }
  public String getDestinationName() {
    return destinationName;
  }
  public void setDestinationName(String destinationName) {
    this.destinationName = destinationName;
  }
  public String getDestinationTag() {
    return destinationTag;
  }
  public void setDestinationTag(String destinationTag) {
    this.destinationTag = destinationTag;
  }
  public String getDirections() {
    return directions;
  }
  public void setDirections(String directions) {
    this.directions = directions;
  }
  public int getImgID() {
    return imgID;
  }
  public void setImgID(int imgID) {
    this.imgID = imgID;
  }
  public int getUserID() {
    return userID;
  }
  public void setUserID(int userID) {
    this.userID = userID;
  }


}