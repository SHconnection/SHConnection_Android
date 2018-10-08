package com.example.kolibreath.shconnection.base.widget;

import com.example.kolibreath.shconnection.extensions.QiniuExtension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.FuncN;

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

  public void setPicture_urls(List<String> picture_urls) {
    this.picture_urls = picture_urls;
  }

  public void test(){
    QiniuExtension.Companion.postPictures(new ArrayList<String>())
        .flatMap(new Func1<String, Observable<LinkedList<String>>>() {
          @Override public Observable<LinkedList<String>> call(String s) {

          }
        };

        ArrayList<String> list = new ArrayList<>();

    Observable.zip(list, new FuncN(){
      public String call(java.lang.Object... args){
        //ReturnType result; //to be made
        //preparatory code for using the args
        for (Object obj : args){
          //ReturnType retObj = (ReturnType)obj;
          //code to use the arg once at a time to combine N of them into one.
        }
        return null;
      }
    });

    Observable.create(new Observable.OnSubscribe<Object>() {
      @Override public void call(Subscriber<? super Object> subscriber) {

      }
    });
  }
}
