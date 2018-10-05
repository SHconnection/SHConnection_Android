package com.example.kolibreath.shconnection.extensions

import com.example.kolibreath.shconnection.base.TeacherCreateClassBody
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.util.LinkedList

class XlsUtils(private val path:String){

  private var mPath :String = path


    private val fileInputStream:FileInputStream = FileInputStream(mPath)
    private val workbook = XSSFWorkbook(fileInputStream)
    //默认只有一个sheet
    private val xssfSheet = workbook.getSheetAt(0)
    private val rowIterator:Iterator<Row> = xssfSheet.iterator()

  fun getChildren():LinkedList<TeacherCreateClassBody.ChildrenListBean>{
    lateinit var row:XSSFRow
    val children =  LinkedList<TeacherCreateClassBody.ChildrenListBean>()
    lateinit var child: TeacherCreateClassBody.ChildrenListBean
    lateinit var name :String
    lateinit var sid :String

    //孩子格式为 ： 张三 0123
    var flag = true
    //行迭代
    while(rowIterator.hasNext()){
      row = rowIterator.next() as XSSFRow
      val cellIterator = row.cellIterator()

      while (cellIterator.hasNext()){
        val cell = cellIterator.next()
        when(cell.cellType){
          Cell.CELL_TYPE_NUMERIC -> {

          }
          Cell.CELL_TYPE_STRING -> {
            if (flag) {
              name = cell.stringCellValue
              flag = false
            } else {
              sid = cell.stringCellValue
              flag = true
            }
          }
        }
      }
      child = TeacherCreateClassBody.ChildrenListBean(name, sid)
      children.add(child)
    }
    return children
  }

  fun getTeachers():LinkedList<TeacherCreateClassBody.TeachersListBean> {
    lateinit var row:XSSFRow
    val teachers =  LinkedList<TeacherCreateClassBody.TeachersListBean>()
    lateinit var teacher: TeacherCreateClassBody.TeachersListBean
    lateinit var name :String
    lateinit var sid :String

    //孩子格式为 ： 张三 0123
    var flag = true
    //行迭代
    while(rowIterator.hasNext()){
      row = rowIterator.next() as XSSFRow
      val cellIterator = row.cellIterator()

      while (cellIterator.hasNext()){
        val cell = cellIterator.next()
        when(cell.cellType){
          Cell.CELL_TYPE_NUMERIC -> {

          }
          Cell.CELL_TYPE_STRING -> {
            if (flag) {
              name = cell.stringCellValue
              flag = false
            } else {
              sid = cell.stringCellValue
              flag = true
            }
          }
        }
      }
      teacher = TeacherCreateClassBody.TeachersListBean(name, sid)
      teachers.add(teacher)
    }
    return teachers
  }
}