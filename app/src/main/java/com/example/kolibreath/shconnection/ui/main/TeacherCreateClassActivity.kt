package com.example.kolibreath.shconnection.ui.main

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.QRCodeUtil

class TeacherCreateClassActivity :ToolbarActivity() {

  private lateinit var mEdtTeacherName:EditText
  private lateinit var mEdtTeacherNumber:EditText
  private lateinit var mEdtClassName:EditText
  private lateinit var mBtnImportStudent:Button
  private lateinit var mBtnImportTeacher:Button

  //todo 需要在弄清楚child和 teacher的数据格式之后进行读取的测试
  private val mTeacherCreateClassApi = View.OnClickListener { }
  //todo 在创建完成的回调之后生成二维码

  private fun createQRcode(decodedString:String):Bitmap? = QRCodeUtil.createQRCodeBitmap(decodedString,480,480)

  private fun createClass(){
    mEdtTeacherName = findViewById(R.id.edt_teacher_name)
    mEdtTeacherNumber = findViewById(R.id.edt_teacher_number)
    mEdtClassName = findViewById(R.id.edt_class_name)

    mBtnImportStudent = findViewById(R.id.btn_import_students_info)
    mBtnImportTeacher = findViewById(R.id.btn_import_teacher_info)

    mBtnImportTeacher.setOnClickListener{
      //todo 读取教师的信息
    }
    mBtnImportStudent.setOnClickListener{
      //todo 读取学生的信息
    }
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_teacher_create_class)

    createClass()
  }
}