# 使用doc
> 推荐代码使用Kotlin编写，可以体验到扩展函数等一系列语法糖的丝滑体验

## Foreword:
 + 如果需要复写ToolbarActivity 或者其他父类中的方法，需要手动标注``open`` 关键字[reference](https://www.kotlincn.net/docs/reference/classes.html)

 + 命名规范：
  > 动态 News ： 发送动态的Activity, PostNewsActivity

 + 使用的依赖
 基本内容： Retrofit RxJava Gson 
 Kotlin 相关 Anko
 View   相关 materialedittext bottom-navigation-bar 
## base Package:
``BaseActivity.kt`` 

``ToolbarActivity.kt`` 使用时务必注意在xml文件中<include> ``view_toolbar`` 这个layout

``NetFactory`` 使用Sington封装了``RetrofitWrapper`` 已经测试过使用规则

eg:
````
 private fun test(){
    NetFactory
        .retrofitService
        .test(605519800)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object:Subscriber<Any>(){
          override fun onNext(t: Any?) {
          }

          override fun onCompleted() {
          }

          override fun onError(e: Throwable?) {
            e?.printStackTrace()
          }
        })
  }
````

## extensions package 
封装对于Activity的扩展方法，在Kotlin代码中直接使用，在Java代码中需要使用``receiver``显式声明扩展对象

## main package 内容的代码
- MainActivity 包含一个BottomNavigationBar 用于放置主要的三个Navigation Fragments
- 发送新的动态: PostNewsActivity

# 任务和进度
- 任务依赖情况
(基本内容封装 BaseActivity ToolbarActivity 网络请求模块) 
             -> MainActivity 开发 -> 各个Fragment开发 
                
- 封装基本的内容，引入依赖 完成
- MainActivity开发：    完成

- 用户注册登录（通过第二次讨论 修改逻辑） 完成/未测试
这部分逻辑可以参考[doc](https://github.com/SHconnection/SHConnection_doc/tree/master/android_doc)
中的草图和class_reference 参考每一个类 做的具体的功能

- 二维码扫描 完成/未测试
- 二维码生成 完成/未测试
- 二维码分享 未完成 

完成上面两步之后就可以独立开发，模块之间不再互相耦合