package com.example.kolibreath.shconnection.base;

import android.app.Application;
import android.content.Context;

public class App extends Application {

  private static Context sContext;
  @Override public void onCreate() {
    super.onCreate();
    sContext = getApplicationContext();

    System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
    System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
    System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
  }

  public static Context getContext(){
    return sContext;
  }
}
