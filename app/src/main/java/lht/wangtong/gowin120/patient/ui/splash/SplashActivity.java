package lht.wangtong.gowin120.patient.ui.splash;

import android.os.Handler;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.App;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.event.UserLoginEvent;
import lht.wangtong.gowin120.patient.db.Token;
import lht.wangtong.gowin120.patient.db.User;
import lht.wangtong.gowin120.patient.tencent.utils.PushUtil;
import lht.wangtong.gowin120.patient.util.LoginUtil;
import yue.wangtong.lht.tencent.event.MessageEvent;

/**
 * 启动页
 *
 * @author luoyc
 * @date 2018/3/28
 */

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContact.View {

    private Handler mHandler = null;
    private Runnable mRunnable = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        //初始化程序后台后消息推送
        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
//        SPUtils.getInstance().put("isFrist", false, true);
        mHandler = new Handler();
        //默认第一次为true
        getUserDB();
    }

    @Override
    public void getUserDB() {
        try {
            User mUser = SQLite.select().from(User.class).querySingle();
            if (mUser == null) {
                LoginUtil.getTokenNew();
                //3秒后执行Runnable中的run方法
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!SPUtils.getInstance().getBoolean("isFrist")) {
                            SPUtils.getInstance().put("isFrist", true, true);
                            showGuide(1);
                        } else {
                            ARouter.getInstance().build("/ui/login/LoginActivity").navigation();
                            finish();
                        }
                    }
                };
                mHandler.postDelayed(mRunnable, 2000);
            } else {
                LoginUtil.user = mUser;
                getTokenDB();
            }
        } catch (Exception e) {
            Log.e("this", "db失败！");
        }
    }

    @Override
    public void getTokenDB() {
        try {
            Token mToken = SQLite.select().from(Token.class).querySingle();
            if (mToken != null) {
                //登录
                LoginUtil.token = mToken;
                App.getInstance().loginTencent();
                JPushInterface.setAlias(this, 40, LoginUtil.user.getLoginName());
                EventBus.getDefault().postSticky(new UserLoginEvent(true, false));
                //3秒后执行Runnable中的run方法
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!SPUtils.getInstance().getBoolean("isFrist")) {
                            SPUtils.getInstance().put("isFrist", true, true);
                            showGuide(2);
                        } else {
                            ARouter.getInstance().build("/ui/MainActivity").navigation();
                            finish();
                        }
                    }
                };
                mHandler.postDelayed(mRunnable, 2000);
            } else {
                LoginUtil.getTokenNew();
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!SPUtils.getInstance().getBoolean("isFrist")) {
                            SPUtils.getInstance().put("isFrist", true, true);
                            showGuide(1);
                        } else {
                            ARouter.getInstance().build("/ui/login/LoginActivity").navigation();
                            finish();
                        }
                    }
                };
                mHandler.postDelayed(mRunnable, 2000);
            }
        } catch (Exception e) {
            Log.e("this", "token 获取失败！");
        }
    }

    @Override
    public void showGuide(int type) {
        ARouter.getInstance()
                .build("/ui/guide/GuideActivity")
                .withInt("type", type)
                .navigation();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }


}
