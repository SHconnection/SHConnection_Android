package com.example.kolibreath.shconnection.base.data

/**
 * 教师登录 的 body
 */
data class LoginBody (
    val tel:String,
    val password:String)

/**
 * 登录成功之后返回的token
 */
data class LoginToken(
  val token :String
)
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