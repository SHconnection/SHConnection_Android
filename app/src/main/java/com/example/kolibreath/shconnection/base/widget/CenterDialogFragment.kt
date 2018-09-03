package com.example.kolibreath.shconnection.base.widget

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.R.style

open class CenterDialogFragment:DialogFragment() {

  companion object {
    public fun newInstance(): BottomDialogFragment {
      val args = Bundle()
      val fragment: BottomDialogFragment = BottomDialogFragment()
      fragment.arguments = args
      return fragment
    }
  }

  fun createDialog(view: View): Dialog {
    val dialog = Dialog(context, style.BottomDialogStyle)
    dialog.setContentView(view)
    val window = dialog.window;
    window.setGravity(Gravity.CENTER)
    var wmlp = window.attributes
    wmlp.width = WindowManager.LayoutParams.MATCH_PARENT
    wmlp.height = WindowManager.LayoutParams.WRAP_CONTENT

    window.attributes = wmlp
    window.setBackgroundDrawableResource(R.drawable.bg_bottom_dialog)
    return dialog
  }
}