package lht.wangtong.gowin120.patient.ui.mine.setting;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.event.UserLoginEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.Token;
import lht.wangtong.gowin120.patient.db.User;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.tencent.Init;
import lht.wangtong.gowin120.patient.util.DataCleanManager;
import lht.wangtong.gowin120.patient.util.LoginUtil;

import static com.tencent.bugly.imsdk.Bugly.applicationContext;

/**
 * @author luoyc
 * @date 2018/3/26
 */

public class SettingPresenter extends BasePresenter<SettingContact.View> implements SettingContact.Presenter {

    @Inject
    public SettingPresenter() {
    }

    @Override
    public void loginOut() {
        ToastUtils.showShort("退出登录");
        Init.logoutIMSDK();
        JPushInterface.setAlias(mView.getThisContext(), 40, "");
        User oldUser = SQLite.select().from(User.class).querySingle();
        if (oldUser != null) {
            oldUser.delete();
        }
        LoginUtil.user = null;
        Token oldToken = SQLite.select().from(Token.class).querySingle();
        if (oldToken != null) {
            oldToken.delete();
        }
        EventBus.getDefault().post(new UserLoginEvent(false, false));
        HttpUtil.getObject(Api.LOGOUT.mapClear()
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {

                    ARouter.getInstance().build("/ui/login/LoginActivity").navigation();
                    mView.close();
                }
            }
        });
    }

    @Override
    public void wipeCache() {
        EventBus.clearCaches();
        Glide.get(mView.getThisContext()).clearMemory();
        DataCleanManager.clearAllCache(mView.getThisContext());
        GSYVideoManager.instance().clearAllDefaultCache(mView.getThisContext());
        mView.setCookies();
    }


}
