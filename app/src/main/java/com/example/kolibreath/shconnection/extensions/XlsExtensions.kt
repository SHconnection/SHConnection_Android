package com.example.kolibreath.shconnection.extensions

import org.apache.poi.hssf.model.Workbook
import java.io.FileInputStream

class XlsExtensions(val path:String){

  val inputStream = FileInputStream(path)
  val book = XSSFWorkbook(`is`);
  companion object {

  }
}