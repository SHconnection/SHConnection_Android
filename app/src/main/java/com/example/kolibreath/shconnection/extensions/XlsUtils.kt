package com.example.kolibreath.shconnection.extensions

import com.example.kolibreath.shconnection.base.TeacherCreateClassBody
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.util.LinkedList

class XlsUtils(path:String){

  private var mPath :String = path



  fun getChildren():LinkedList<TeacherCreateClassBody.ChildrenListBean>{
    val fileInputStream = FileInputStream(mPath)
    lateinit var workbook:XSSFWorkbook
    try{
      workbook = XSSFWorkbook(fileInputStream)
    }catch (e:Exception){
      e.printStackTrace()
    }
    val xssfSheet = workbook.getSheetAt(0)
    val rowIterator:Iterator<Row> = xssfSheet.iterator()

    lateinit var row:XSSFRow
    val children =  LinkedList<TeacherCreateClassBody.ChildrenListBean>()
    lateinit var child: TeacherCreateClassBody.ChildrenListBean
    lateinit var name :String
    lateinit var sid :String

    //孩子格式为 ： 张三 0123
    //行迭代
    while(rowIterator.hasNext()){
      row = rowIterator.next() as XSSFRow
      val cellIterator = row.cellIterator()

      while (cellIterator.hasNext()){
        val cell = cellIterator.next()
        when(cell.cellType){
          Cell.CELL_TYPE_NUMERIC -> {
            sid = cell.numericCellValue.toString()
          }
          Cell.CELL_TYPE_STRING -> {
              name = cell.stringCellValue
          }
        }
      }
      child = TeacherCreateClassBody.ChildrenListBean(name, sid)
      children.add(child)
    }
    return children
  }

  fun getTeachers():LinkedList<TeacherCreateClassBody.TeachersListBean> {

    val fileInputStream = FileInputStream(mPath)
    lateinit var workbook:XSSFWorkbook
    try{
      workbook = XSSFWorkbook(fileInputStream)
    }catch (e:Exception){
      e.printStackTrace()
    }

    lateinit var row:XSSFRow
    val teachers =  LinkedList<TeacherCreateClassBody.TeachersListBean>()
    lateinit var teacher: TeacherCreateClassBody.TeachersListBean
    lateinit var name :String
    lateinit var sid :String

    val xssfSheet = workbook.getSheetAt(0)
    val rowIterator:Iterator<Row> = xssfSheet.iterator()

    //孩子格式为 ： 张三 0123
    //行迭代
    while(rowIterator.hasNext()){
      row = rowIterator.next() as XSSFRow
      val cellIterator = row.cellIterator()

      while (cellIterator.hasNext()){
        val cell = cellIterator.next()
        when(cell.cellType){
          Cell.CELL_TYPE_NUMERIC -> {
            sid = cell.numericCellValue.toString()
          }
          Cell.CELL_TYPE_STRING -> {
              name = cell.stringCellValue
          }
        }
      }
      teacher = TeacherCreateClassBody.TeachersListBean(name, sid)
      teachers.add(teacher)
    }
    return teachers
  }
}