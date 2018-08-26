# 使用doc
> 推荐代码使用Kotlin编写，可以体验到扩展函数等一系列语法糖的丝滑体验

- Foreword:
 + 如果需要复写ToolbarActivity 或者其他父类中的方法，需要手动标注``open`` 关键字[reference](https://www.kotlincn.net/docs/reference/classes.html)

- Base Package:
``BaseActivity.kt`` 

``ToolbarActivity.kt`` 使用时务必注意在xml文件中<include> ``view_toolbar`` 这个layout

- extensions package 
封装对于Activity的扩展方法，在Kotlin代码中直接使用，在Java代码中需要使用``receiver``显式声明扩展对象