package com.hailam32.doanmangxahoi.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.hailam32.doanmangxahoi.enums.NotificationEnum;

public class Notification {

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @DocumentId()
  private String id;
  private User byUser;
  private Timestamp created_at;
  private String title;
  private boolean seen;


  public User getByUser() {
    return byUser;
  }

  public void setByUser(User byUser) {
    this.byUser = byUser;
  }

  public Timestamp getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Timestamp created_at) {
    this.created_at = created_at;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isSeen() {
    return seen;
  }

  public void setSeen(boolean seen) {
    this.seen = seen;
  }

  public NotificationEnum getType() {
    return type;
  }

  public void setType(NotificationEnum type) {
    this.type = type;
  }

  private NotificationEnum type;

  @Override
  public String toString() {
    return "Notification{" +
            "id='" + id + '\'' +
            ", byUser=" + byUser +
            ", created_at=" + created_at +
            ", title='" + title + '\'' +
            ", seen=" + seen +
            ", type=" + type +
            '}';
  }
}


