package com.example.kolibreath.shconnection.base


/**
 * feed流
 */

class FeedBean(
        /**
     * pagenum: 0,
     * nums": 0,
     * hasnext": true,
     * feeds": [{
     * id: 0,
     * classId: 0,
     * teacherSimpleInfo: {"id": 0,"name": "string","avatar": "string"},
     * type: string,
     * content: "string",
     * likes: 0,
     * liked: true,
     * picture_urls": ["string"]  }]
     */
    var id:Int,
        var classId: Int,
        var teacherSimpleInfo: TeacherSimpleInfo,
        var type: String,
        var content: String,
        var likes: Int,
        var liked: Boolean,
        var picture_urls: List<String>){
    class TeacherSimpleInfo(
        var id:Int,
        var name:String,
        var avatar: String)
}

class FeedDetails(
        /**
     *
     */
    var feedInfo: List<FeedInfo>,
        var type: String,
        var content: String,
        var likes: Int,
        var liked: Boolean,
        var picture_urls: List<String>
){
    class FeedInfo(
        /**
         *
         */
        var id: Int,
        var classId: Int,
        var teacherSimpleInfo: FeedBean.TeacherSimpleInfo
    )
}

/**
 * 返回班级通讯录
 */
class Person
/**
 * tel : string
 * name : string
 * avatar : string
 */(
        tel: String,
        name: String,
        avatar: String
) {
    var tel: String? = tel
    var name: String? = name
    var avatar: String? = avatar

}

class AddressBean {

    private var teacher: List<Person>?=null
    private var parent: List<Person>?=null
    fun setTeacher(teacher: List<Person>) {
        this.teacher = teacher
    }

    fun getTeacher(): List<Person>?{
        return teacher
    }

    fun setParent(parent: List<Person>){
        this.parent = parent
    }

    fun getParent(): List<Person>?{
        return parent
    }

}

/**
 * 某个feed的所有评论
 */
class FeedComments(
    /**
     *
     */
    var comments: List<Comments>
){
    class Comments(
        /**
         * id : int
         *
         */
        var id: Int,
        var feedInt: Int,
        var content: String,
        var like: Int,
        var userSimpleInfo: UserSimpleInfo
    ){
        class UserSimpleInfo(
            var utype: Int,
            var uid: Int,
            var uname: String,
            var avatar: String
        )
    }
}


/**
 * 返回老师端评价列表
 */
class TeacherList(
        var trachers: List<ParentList.Person>
)

/**
 * 返回家长端评价列表
 */
class ParentList(
        var teacher: List<Person>,
        var parent: List<Person>
){
    class Person(var tid:String,var name: String)

}

/**
 * 一个老师的所有评价
 */
class TeacherCommentAll(
        /**
     * evals : [{ child : string , eval : string }],
     * teacher : string
     */
    var evals: List<Evals>,
        var teacher: String
){
    class Evals(var child: String,var eval: String)
}

/**
 * 一个孩子的所有评价
 */
class ChildCommentAll(
        /**
         * evals : [{ child : string , eval : string }],
         * child : string
         */
        var evals: List<Evals>,
        var child: String
){
    class Evals(var child: String,var eval: String)
}

/**
 * 某老师对某学生评价
 */
class TeacherPerComment(
        var eval: List<Evals>,
        var teacher:String
){
    class Evals(var eval: String)
}

/**
 * 家长对孩子对评价
 */
class ParentPerComment(
        var eval:List<Evals>,
        var child:String
){
    class Evals(var eval: String)
}

/**
 * 发送评价内容
 */
class CommentBody{
    /**
     * comment : string,
     * score : [{"name": "string","score": 0}]
     */

    private var comment: String ?= null
    private var score: List<Score> ?= null

    fun setComment(comment: String?){
        this.comment = comment
    }

    fun getComment() : String?{
        return comment
    }

    fun setScore(score: List<Score>?){
        this.score = score
    }

    fun getScore() : List<Score>?{
        return score
    }

    class Score(var name:String,var score:Int)
}

class ScoreComment(
    /**
     * evals: [{
     * time : string,
     * score : [{name : string, score : 0}]
     * content : string
     * }]
     */
    var evals: List<Evals>
){
    class Evals(
            var time: String,
            var score: List<Score>,
            var content: String
    ){
        class Score(var name: String, var score: Int)
    }
}