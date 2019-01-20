package com.example.kolibreath.shconnection.extensions

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper


//todo what is the point of data bast helper?
class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "LibraryDatabase", null, 1) {
    private lateinit var database: SQLiteDatabase
  companion object {
    private var instance: DatabaseHelper? = null

    @Synchronized
    fun getInstance(context: Context): DatabaseHelper {
      if (instance == null) {
        instance = DatabaseHelper(context.applicationContext)
      }
      return instance!!
    }
  }

  override fun onCreate(database: SQLiteDatabase) {
    this@DatabaseHelper.database  = database
  }

  override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//    dropTable(Book.TABLE_NAME, true)
  }

}
