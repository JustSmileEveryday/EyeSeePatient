package lht.wangtong.gowin120.patient.config;


import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;


/**
 * @author luoyc
 * @date 2016/10/18
 */
public class Constant {

    //正式环境
//    public static final String REQUEST_BASE_URL = "http://wappma.gowin120.com/";
    public static final String REQUEST_BASE_URL_IMG = "http://wappma.gowin120.com/img/";
    public static final String REQUEST_BASE_URL_IMG2 = "http://wappma.gowin120.com/img";

    //测试环境
    public static final String REQUEST_BASE_URL = "http://192.168.10.18:8087/";
//    public static final String REQUEST_BASE_URL_IMG = "http://192.168.10.18:8087/img/";
//    public static final String REQUEST_BASE_URL_IMG2 = "http://192.168.10.18:8087/img";

    public final static String APPVERSION = AppUtils.getAppVersionName("lht.wangtong.gowin120.patient");

    public final static String appId = "MB02";
    public final static String appSecret = "EJX1ZKFPGZAAHU";
    //操作系统，iOS11, 安卓+版本
    public final static String operaSystem = "ANDROID " + DeviceUtils.getSDKVersionName();
    // ANDROID \ IOS
    public final static String operaSysType = "ANDROID";
    public final static String dataSource = DeviceUtils.getAndroidID();          //机型
    public final static String operationMode = "2";                        //运营模式  1:所有  2：单体医院 3：网络专科 4：医联体
    public final static String operationCode = "";                          //运营模式参数

    //微信sdk需要的AppID
    public static final String APP_ID = "wx5d7f760ac6136336";

}
