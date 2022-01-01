package com.hailam32.doanmangxahoi.models.post;

import com.hailam32.doanmangxahoi.models.ImageContent;

import java.util.List;

public class PostContent {
  private String text_content;
  private List<ImageContent> image_content;

  public String getText_content() {
    return text_content;
  }

  public void setText_content(String text_content) {
    this.text_content = text_content;
  }

  public List<ImageContent> getImage_content() {
    return image_content;
  }

  public void setImage_content(List<ImageContent> image_content) {
    this.image_content = image_content;
  }

  public List<String> getHashtag() {
    return hashtag;
  }

  public void setHashtag(List<String> hashtag) {
    this.hashtag = hashtag;
  }

  private List<String> hashtag;
}
