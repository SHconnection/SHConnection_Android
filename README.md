# 使用doc
> 推荐代码使用Kotlin编写，可以体验到扩展函数等一系列语法糖的丝滑体验
          
## Foreword:
 + 如果需要复写ToolbarActivity 或者其他父类中的方法，需要手动标注``open`` 关键字[reference](https://www.kotlincn.net/docs/reference/classes.html)

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

# 任务和进度
- 任务依赖情况
(基本内容封装 BaseActivity ToolbarActivity 网络请求模块) 
             -> MainActivity 开发 -> 各个Fragment开发 
                
- 封装基本的内容，引入依赖 完成
- MainActivity开发：    未完成

完成上面两步之后就可以独立开发，模块之间不再互相耦合