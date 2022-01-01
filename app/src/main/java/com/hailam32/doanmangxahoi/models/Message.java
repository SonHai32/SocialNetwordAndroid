package com.hailam32.doanmangxahoi.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

public class Message {
  @DocumentId()
  private String id;
  private String sendByID;
  private String textMessage;
  private ArrayList<ImageContent> imageMessage;
  private Timestamp created_at;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSendByID() {
    return sendByID;
  }

  public void setSendByID(String sendByID) {
    this.sendByID = sendByID;
  }

  public String getTextMessage() {
    return textMessage;
  }

  public void setTextMessage(String textMessage) {
    this.textMessage = textMessage;
  }

  public ArrayList<ImageContent> getImageMessage() {
    return imageMessage;
  }

  public void setImageMessage(ArrayList<ImageContent> imageMessage) {
    this.imageMessage = imageMessage;
  }

  public Timestamp getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Timestamp created_at) {
    this.created_at = created_at;
  }
}
