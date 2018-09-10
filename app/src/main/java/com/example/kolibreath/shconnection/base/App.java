package com.example.kolibreath.shconnection.base;

import android.app.Application;
import android.content.Context;
import com.example.kolibreath.shconnection.extensions.Preference;

public class App extends Application {

  private static Context sContext;
  @Override public void onCreate() {
    super.onCreate();
    sContext = getApplicationContext();
  }

  public static Context getContext(){
    return sContext;
  }
}
