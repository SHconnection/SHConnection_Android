package com.example.kolibreath.shconnection.ui

import LOGIN_TOKEN
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toolbar
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.ui.ToolbarActivity
import com.example.kolibreath.shconnection.extensions.getValue
import org.jetbrains.anko.find
import org.jetbrains.anko.webView

class GraphActivity: ToolbarActivity(){

    lateinit var mWebView:WebView

    companion object {
        fun start(context:Activity){
            context.startActivity(Intent(context,GraphActivity::class.java))
        }
    }


    private fun initViews(){
        mWebView = find(R.id.webview_graph)
        webView().loadUrl("http://39.108.79.110:3000/chart/"+ getValue(LOGIN_TOKEN,""))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        initViews()
    }
}