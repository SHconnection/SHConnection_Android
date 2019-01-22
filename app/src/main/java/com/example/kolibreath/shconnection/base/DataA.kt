package com.example.kolibreath.shconnection.base






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

    override fun toString(): String {
        var teacherString = ""
        for(t in teacher!!){
            teacherString += "teacher tvAvatar "+t.avatar+"\n"
            teacherString += "teacher tvName   "+t.name +"\n"
            teacherString += "teacher tel    " +t.tel+"\n"
        }

        var parentString = ""
        for(t in parent!!){
            parentString += "parent tvAvatar   "+t.avatar+"\n"
            parentString += "parent tvName     "+t.name +"\n"
            parentString += "parent tel      " +   t.tel+"\n"
        }
            return teacherString + parentString
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
     * tvComment : string,
     * score : [{"tvName": "string","score": 0}]
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
     * score : [{tvName : string, score : 0}]
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