package com.hailam32.doanmangxahoi.models;

import com.google.common.base.Supplier;
import com.google.firebase.Timestamp;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
  private String id;
  private String email;
  private String display_name;
  private Timestamp bitrhday;
  private String phone;
  private Timestamp created_at;
  private String avatar_url;
  private String gender;
  private String place;
  private List<UserHobbies> hobbies;

  public List<UserHobbies> getHobbies() {
    return hobbies;
  }

  public void setHobbies(List<UserHobbies> hobbies) {
    this.hobbies = hobbies;
  }



  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDisplayName() {
    return display_name;
  }

  public void setDisplayName(String displayName) {
    this.display_name = displayName;
  }

  public Timestamp getBitrhday() {
    return bitrhday;
  }

  public void setBitrhday(Timestamp bitrhday) {
    this.bitrhday = bitrhday;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Timestamp getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Timestamp created_at) {
    this.created_at = created_at;
  }

  public String getAvatar_url() {
    return avatar_url;
  }

  public void setAvatar_url(String avatar_url) {
    this.avatar_url = avatar_url;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public User mapToUser(Map<String, Object> data) throws NoSuchFieldException, IllegalAccessException {
    for (Field f : User.class.getDeclaredFields()
    ) {
//      System.out.println(f.getType().ge);
      if (data.get(f.getName()) != null) {
        getClass().getDeclaredField(f.getName()).set(this, data.get(f.getName()));
      }
    }
    return this;
  }

  @Override
  public String toString() {
    return "User{" +
            "id='" + id + '\'' +
            ", email='" + email + '\'' +
            ", display_name='" + display_name + '\'' +
            ", bitrhday=" + bitrhday +
            ", phone='" + phone + '\'' +
            ", created_at=" + created_at +
            ", avatar_url='" + avatar_url + '\'' +
            ", gender='" + gender + '\'' +
            ", place='" + place + '\'' +
            ", hobbies=" + hobbies +
            '}';
  }
}