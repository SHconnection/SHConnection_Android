val REQUEST_CODE_IMAGE_ALBUM = 0xFF;
val REQUEST_CODE_IMAGE_CAPTURE = 0xFE
val testing = true;

/**
 * 发送新的动态部分
 */
const val TAG_NEWS = "news"
const val TAG_INFOMATION = "infomation"

/**
 * 图片选择部分
 */
const val IMG_LIST = "0XFD"
const val POSITION = "0XFC"
const val RESULT_CODE_VIEW_IMG = 0xFB
const val MAX_SELECT_PIC_NUM  = 5
const val REQUEST_CODE_MAIN = 0xFA

/**
 * 班级id的String
 */
const val CLASS_ID  = "sClassID"
/**
 * 老师或者是家长 默认的类型没有
 */

const val USER_TYPE = "mUserType"

const val USER_PARENT = 0x1
const val USER_TEACHER = 0x2
//没有选择任何一个类型
const val USER_NONE = 0x03


/**
 * 确定是自己或者是别人的user profile
 */

const val PROFILE_TYPE=  "user_profile"


/**
 * preference
 */

const val LOGIN_TOKENp = "sLoginToken"
const val CUR_CLASS = "sCurrentClassId"

/**
 * 所有出现过的表名
 */

val names:List<String> = arrayListOf("classId")

//表名 只有一个item ：id string
const val CLASSED_IDS  = "sClassesIDs"

/**
 * 从ScanActivity 到另外地一个ACtivity 中
 * 路由 表示从那一个Activity 中 路由过来的值
 */
   //表示这个Intent 键地名字
const val ROUTER_NAME = "sRouterNamne"
const val SCAN_RESULT = "sScanResult"
 const val JOIN_CLASS_ACTIVITY = 0xFF