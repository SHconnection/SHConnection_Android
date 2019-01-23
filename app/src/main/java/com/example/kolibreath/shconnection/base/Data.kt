package com.example.kolibreath.shconnection.base

import java.io.Serializable

/**
 * 教师登录 的 body
 */
data class TeacherLoginBody (
    val wid:String,
    val password:String){}

/**
 * 家长登录的login body
 */

data class ParentLoginBody(
  val sid:String,
    val password: String
)
/**
 * 登录成功之后返回的token
 * 老师和家长的登录之后地地返回指有区别
 */
class TeacherLoginToken(){

  /**
   * classes_id : [0]
   * token : string
   * tvName : string
   */

  private var token: String? = null
  private var name: String? = null
  private var classes_id: List<Int>? = null

  fun getToken(): String? {
    return token
  }

  fun setToken(token: String) {
    this.token = token
  }

  fun getName(): String? {
    return name
  }

  fun setName(name: String) {
    this.name = name
  }

  fun getClasses_id(): List<Int>? {
    return classes_id
  }

  fun setClasses_id(classes_id: List<Int>) {
    this.classes_id = classes_id
  }
}

/**
 * 家长登录完成之后地返回值
 */
class ParentLoginToken(){
  /**
   * class_id : 0
   * token : string
   */

  private var class_id: Int = 0
  private var token: String? = null

  fun getClass_id(): Int {
    return class_id
  }

  fun setClass_id(class_id: Int) {
    this.class_id = class_id
  }

  fun getToken(): String? {
    return token
  }

  fun setToken(token: String) {
    this.token = token
  }
}
/**
 * 老师注册
 */
data class TeacherSignupBody(
    val tel:String,
    val password: String,
    val name: String,
    val typeId:Int
)
/**
 * 老师注册成功返回的token
 */
data class TeacherSignupToken(
  val tid:String
)

data class TeacherInit(
  val wid:String,
    val password: String,
    val name:String,
    val subject:String
)

/**
 * 家长加入一个班级
 */
data class ParentInit(
  val sid:String,
    val password:String
)

/**
 * 班主任创建班级
 */

class TeacherCreateClassBody{
  /**
   * main_teacher_wid : string
   * class_name : string
   * teachers_list : [{"tvName":"string","id":"string"}]
   * children_list : [{"tvName":"string","sid":"string"}]
   */

  private var main_teacher_wid: String? = null
  private var class_name: String? = null
  private var teachers_list: List<TeachersListBean>? = null
  private var children_list: List<ChildrenListBean>? = null

  constructor(wid: String,class_name: String,teachers_list: List<TeachersListBean>,
      children_list: List<ChildrenListBean>){
    this.children_list = children_list;
    this.main_teacher_wid = wid
    this.class_name = class_name
    this.teachers_list= teachers_list
  }
  fun getMain_teacher_wid(): String? {
    return main_teacher_wid
  }

  fun setMain_teacher_wid(main_teacher_wid: String) {
    this.main_teacher_wid = main_teacher_wid
  }

  fun getClass_name(): String? {
    return class_name
  }

  fun setClass_name(class_name: String) {
    this.class_name = class_name
  }

  fun getTeachers_list(): List<TeachersListBean>? {
    return teachers_list
  }

  fun setTeachers_list(teachers_list: List<TeachersListBean>) {
    this.teachers_list = teachers_list
  }

  fun getChildren_list(): List<ChildrenListBean>? {
    return children_list
  }

  fun setChildren_list(children_list: List<ChildrenListBean>) {
    this.children_list = children_list
  }

  class TeachersListBean
  /**
   * tvName : string
   * id : string
   */(
    name: String,
    wid: String
  ) {

    var name: String? = name
    var wid: String? = wid
  }

  class ChildrenListBean
  /**
   * tvName : string
   * sid : string
   */(
    name: String,
    sid: String
  ) {

    var name: String? = name
    var sid: String? = sid
  }
}

/**
 * 老师创建班级之后地返回值
 */
class CreatedClassId(){
  /**
   * class_id : 8
   */

  private var class_id: Int = 0

  fun getClass_id(): Int {
    return class_id
  }

  fun setClass_id(class_id: Int) {
    this.class_id = class_id
  }
}


/**
 * 发送一个新的feed
 */
data class FeedBody(
    val class_id: Int,
    val content: String,
    val picture_urls: List<String>,
    val type: String
)


data class MainTeacherSignUpBody(val wid:String,
    val password:String,val name:String)


data class Feed(
    public val feeds: List<FeedX>,
    public val hasnext: Boolean,
    val nums: Int,
    val pagenum: Int
)

data class FeedX(
    val commentnum: Int,
    val comments: List<Comment>,
    val content: String,
    val id: Int,
    val picture_urls: List<String>,
    val read_status: String,
    val readed: Boolean,
    val teacherSimpleInfo: TeacherSimpleInfo,
    val time: String,
    val type: String
)

data class TeacherSimpleInfo(
    val avatar: String,
    val name: String,
    val wid: Int
)

data class Comment(
    val content: String,
    val username: String
)

/**
 * 老师和家长的信息数据结构
 */

 data class Person(
    val avatar: String,
    val intro: String,
    val name: String,
    val subject: String,
    val tel: String,
    val title: String,
    val wechat: String
):Serializable