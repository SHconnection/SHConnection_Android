package com.example.kolibreath.shconnection.base.widget

import REQUEST_CODE_IMAGE_CAPTURE
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.extensions.createView
import com.example.kolibreath.shconnection.extensions.openAlbum
import com.example.kolibreath.shconnection.extensions.openCamera
import org.jetbrains.anko._Gallery
import org.jetbrains.anko.layoutInflater

/**
 * 选择是照相还是从相簿中选择
 *
 **/

class ActionSelectDialog:BottomDialogFragment(){

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


      val view = context!!.createView(R.layout.view_action_select)
      val dialog = createDialog(view)

      val btnCamera = view.findViewById<TextView>(R.id.btn_select_from_camera)
      val btnGallery = view.findViewById<TextView>(R.id.btn_select_from_gallery)

    //todo missing listener
     btnGallery.setOnClickListener {}

    btnCamera.setOnClickListener { context!!.openCamera {
      val activity = context as Activity
      activity.startActivityForResult(it,REQUEST_CODE_IMAGE_CAPTURE)
    } }

      return dialog
  }
}