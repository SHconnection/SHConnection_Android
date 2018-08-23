import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

@RequiresApi(VERSION_CODES.JELLY_BEAN)
val permissions:Array<String> = arrayOf(
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.CAMERA)

val REQUEST_CODE = 100;

fun requestPermissions(context: Activity){
  val permission = checkPermissions(context)
  if(permission.size ==0)
    return
  else{
    ActivityCompat.requestPermissions(context,permissions,REQUEST_CODE)
  }
}


/**
 * 检测需要请求的权限 如果没有请求的权限放到另外的String[]
 */
fun checkPermissions(context: Context):Array<String>{
  val list = ArrayList<String>()
  for(permission in permissions){
    if(ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_DENIED)
        list.add(permission)
  }
  return list.toArray(Array(list.size){
    list.get(it)
  })
}


fun isGranted(grantedResult:IntArray):Boolean = grantedResult[0] == PackageManager.PERMISSION_GRANTED