package com.example.kolibreath.shconnection.base.widget;

import java.util.List;

public class ffd {

  /**
   * classId : 0
   * teacherId : 0
   * type : string
   * content : string
   * picture_urls : ["string"]
   */

  private int classId;
  private int teacherId;
  private String type;
  private String content;
  private List<String> picture_urls;

  public int getClassId() {
    return classId;
  }

  public void setClassId(int classId) {
    this.classId = classId;
  }

  public int getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(int teacherId) {
    this.teacherId = teacherId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<String> getPicture_urls() {
    return picture_urls;
  }

}
