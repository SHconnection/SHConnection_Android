package com.example.kolibreath.shconnection.extensions

import com.example.kolibreath.shconnection.base.child
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFRow
import java.io.FileInputStream
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.LinkedList

class XlsUtils(private val path:String){

  private var mPath :String = path


    private val fileInputStream:FileInputStream = FileInputStream(mPath)
    private val workbook = XSSFWorkbook(fileInputStream)
    //默认只有一个sheet
    private val xssfSheet = workbook.getSheetAt(0)
    private val rowIterator:Iterator<Row> = xssfSheet.iterator()

  fun readSheet(){
    lateinit var row:XSSFRow
    val children =  LinkedList<child>()
    lateinit var child: child
    var columnNumber  = 1
    lateinit var name :String
    lateinit var sid :String
    while(rowIterator.hasNext()){
      row = rowIterator.next() as XSSFRow
      val cellIterator = row.cellIterator()
      while (cellIterator.hasNext()){
        val cell = cellIterator.next()
        //一行一行地读取
        when(cell.cellType){
          Cell.CELL_TYPE_NUMERIC -> {

          }
          Cell.CELL_TYPE_STRING -> {
            if(columnNumber == 1)
              name = cell.stringCellValue
            else
              sid = cell.stringCellValue
          }
        }
      }
      child = child(name, sid)
      children.add(child)
    }
  }


}