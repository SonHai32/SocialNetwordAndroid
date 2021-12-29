package com.hailam32.doanmangxahoi.models.post;

import com.google.firebase.Timestamp;

import java.util.List;


public class Post {
  private String id;
  private String create_by_username;
  private String avatar_url;
  private Timestamp created_at;
  private String created_by_id;
  private List<String> liked_by_user_id;
  private PostContent post_content;
  private String commentID;

  public String getCommentID() {
    return commentID;
  }

  public void setCommentID(String commentID) {
    this.commentID = commentID;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCreate_by_username() {
    return create_by_username;
  }

  public void setCreate_by_username(String create_by_username) {
    this.create_by_username = create_by_username;
  }

  public String getAvatar_url() {
    return avatar_url;
  }

  public void setAvatar_url(String avatar_url) {
    this.avatar_url = avatar_url;
  }

  public Timestamp getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Timestamp created_at) {
    this.created_at = created_at;
  }

  public String getCreated_by_id() {
    return created_by_id;
  }

  public void setCreated_by_id(String created_by_id) {
    this.created_by_id = created_by_id;
  }

  public List<String> getLiked_by_user_id() {
    return liked_by_user_id;
  }

  public void setLiked_by_user_id(List<String> liked_by_user_id) {
    this.liked_by_user_id = liked_by_user_id;
  }

  public PostContent getPost_content() {
    return post_content;
  }

  public void setPost_content(PostContent post_content) {
    this.post_content = post_content;
  }
}

