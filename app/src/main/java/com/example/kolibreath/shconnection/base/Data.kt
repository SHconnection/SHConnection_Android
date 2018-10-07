package com.example.kolibreath.shconnection.base

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
   */

  private var token: String? = null
  private var classes_id: List<Int>? = null

  fun getToken(): String? {
    return token
  }

  fun setToken(token: String) {
    this.token = token
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
 * 老师修改个人资料（通讯录）
 * 需要token
 */
data class Profile(
  val tel: String,
    val name:String,
    val wechat:String,
    val intro:String,
    val avatar:String
)

data class TeacherInfoBody(
  val tid:String,
    val token: String
)
/**
 * 老师的个人
 * 显示个人界面的信息相关
 * todo 需要去掉这个重复的类
 */
data class TeacherInfoData(
   var name:String,
    var tel:String,
    var intro:String,
    var avatar: String,
    var star:List<String>?,
    var comment:List<String>?
)

/**
 * 班主任创建班级
 */

class TeacherCreateClassBody{
  /**
   * main_teacher_wid : string
   * class_name : string
   * teachers_list : [{"name":"string","wid":"string"}]
   * children_list : [{"name":"string","sid":"string"}]
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
   * name : string
   * wid : string
   */(
    name: String,
    wid: String
  ) {

    var name: String? = name
    var wid: String? = wid
  }

  class ChildrenListBean
  /**
   * name : string
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