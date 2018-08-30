package lht.wangtong.gowin120.patient.tencent;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.TIMConnListener;
import com.tencent.TIMManager;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.tencent.business.InitBusinessHelper;
import lht.wangtong.gowin120.patient.util.ActivityCollector;
import lht.wangtong.gowin120.patient.util.LoginUtil;
import yue.wangtong.lht.tencent.Constant;
import yue.wangtong.lht.tencent.event.MessageEvent;

/**
 * @author luoyc
 * @date 2018/4/12 0028
 */
public class Init implements ILiveLoginManager.TILVBStatusListener {

    public static Init getInstance() {
        return InitHolder.INSTANCE;
    }

    /**
     * 退出腾讯IM
     */
    public static void logoutIMSDK() {
        ILiveLoginManager.getInstance().iLiveLogout(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                MessageEvent.getInstance().clear();
                Log.e("luoyc", "  login out onSuccess");
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {

            }
        });
        TIMManager.getInstance().setConnectionListener(null);
    }

    public void initTencentIM() {
        InitBusinessHelper.getInstance().initApp(App.getAppContext(), Constant.SDK_APPID, Constant.ACCOUNT_TYPE);
        //设置网络连接监听器，连接建立／断开时回调
        // 连接监听器
        TIMManager.getInstance().setConnectionListener(new TIMConnListener() {
            @Override
            public void onConnected() {//连接建立
                //当前未登陆
                Log.e("this", "connected");
                if (TIMManager.getInstance().getLoginUser() == null || TextUtils.isEmpty(TIMManager.getInstance().getLoginUser())) {
                    Log.e("this", "is reLoginIM ");
                }
            }

            @Override
            public void onDisconnected(int code, String desc) {//连接断开
                //接口返回了错误码code和错误描述desc，可用于定位连接断开原因
                //错误码code含义请参见错误码表
                Log.e("this", "code:  " + code + "  disconnected:  " + desc);

            }

            @Override
            public void onWifiNeedAuth(String s) {
                Log.e("this", "onWifiNeedAuth:" + s);
            }
        });
        loginIMSDK();
    }

    public void loginIMSDK() {
        InitBusinessHelper.getInstance().reLoginIM(LoginUtil.user.getVideoUserId(), LoginUtil.user.getVideoMd5(), this);
    }

    @Override
    public void onForceOffline(int error, String message) {
        LoginUtil.user = null;
        ActivityCollector.finishAllActivity();
        LoginUtil.loginRe();
        Toast.makeText(App.getAppContext(), "该账号在其他设备上登陆！", Toast.LENGTH_SHORT).show();
    }


    private static class InitHolder {
        /**
         * 单例对象实例
         */
        static final Init INSTANCE = new Init();
    }
}
