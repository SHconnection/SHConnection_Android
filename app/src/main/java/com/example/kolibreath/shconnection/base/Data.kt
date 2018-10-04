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
 * 班主任导入孩子
 */
data class child(
    val name:String,
    val sid:String
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

