package lht.wangtong.gowin120.patient.tencent.business;

import com.tencent.TIMCallBack;
import com.tencent.TIMManager;

/**
 * 登录
 */
public class LoginBusiness {

    private static final String TAG = "LoginBusiness";

    private LoginBusiness() {
    }


    /**
     * 登录imsdk
     *
     * @param identify 用户id
     * @param userSig  用户签名
     * @param callBack 登录后回调
     */
    public static void loginIm(String identify, String userSig, TIMCallBack callBack) {

        //发起登录请求
        if (identify == null || userSig == null) {
            return;
        }
        //发起登录请求
        //用户帐号签名，由私钥加密获得，具体请参考文档
        TIMManager.getInstance().login(
                identify,
                userSig,
                callBack);
    }

    /**
     * 登出imsdk
     *
     * @param callBack 登出后回调
     */
    public static void logout(TIMCallBack callBack) {
        TIMManager.getInstance().logout(callBack);
    }
}
