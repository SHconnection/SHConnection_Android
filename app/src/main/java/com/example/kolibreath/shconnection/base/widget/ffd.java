package com.example.kolibreath.shconnection.base.widget;

import java.util.List;

public class ffd {

  /**
   * main_teacher_wid : string
   * class_name : string
   * teachers_list : [{"name":"string","wid":"string"}]
   * children_list : [{"name":"string","sid":"string"}]
   */

  private String main_teacher_wid;
  private String class_name;
  private List<TeachersListBean> teachers_list;
  private List<ChildrenListBean> children_list;

  public String getMain_teacher_wid() {
    return main_teacher_wid;
  }

  public void setMain_teacher_wid(String main_teacher_wid) {
    this.main_teacher_wid = main_teacher_wid;
  }

  public String getClass_name() {
    return class_name;
  }

  public void setClass_name(String class_name) {
    this.class_name = class_name;
  }

  public List<TeachersListBean> getTeachers_list() {
    return teachers_list;
  }

  public void setTeachers_list(List<TeachersListBean> teachers_list) {
    this.teachers_list = teachers_list;
  }

  public List<ChildrenListBean> getChildren_list() {
    return children_list;
  }

  public void setChildren_list(List<ChildrenListBean> children_list) {
    this.children_list = children_list;
  }

  public static class TeachersListBean {
    /**
     * name : string
     * wid : string
     */

    private String name;
    private String wid;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getWid() {
      return wid;
    }

    public void setWid(String wid) {
      this.wid = wid;
    }
  }

  public static class ChildrenListBean {
    /**
     * name : string
     * sid : string
     */

    private String name;
    private String sid;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getSid() {
      return sid;
    }

    public void setSid(String sid) {
      this.sid = sid;
    }
  }
}
