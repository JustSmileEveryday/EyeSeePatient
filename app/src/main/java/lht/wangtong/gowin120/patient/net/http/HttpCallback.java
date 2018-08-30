package lht.wangtong.gowin120.patient.net.http;

import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;

import org.xutils.common.Callback;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.util.netanimation.AnimationUtil;


/**
 * @author sanmu
 * @date 2016/10/12 0012
 */
public abstract class HttpCallback implements Callback.CommonCallback<String> {
    public BaseApi api;
    Class mClass;
    private int PZ;


    public void setBase(BaseApi api, Class mClass) {
        setBase(api, mClass, 0);
    }

    public void setBase(BaseApi api, Class mClass, int PZ) {
        this.api = api;
        this.mClass = mClass;
        this.PZ = PZ;

        if (AnimationUtil.getProgress() != null && !AnimationUtil.getProgress().isShow()) {
            AnimationUtil.getProgress().show();
        }
    }


    @Override
    public void onSuccess(String result) {
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        ToastUtils.showShort(R.string.no_network_connection);
        result(false, null);
        Log.e("luoyc", " ex + " + ex.toString());
    }

    @Override
    public void onCancelled(CancelledException cex) {
        result(false, null);
    }

    @Override
    public void onFinished() {
        if (AnimationUtil.getProgress() != null) {
            AnimationUtil.getProgress().close();
            AnimationUtil.singleProgress = null;
        }
    }

    public void result(boolean b, @Nullable Object obj) {
    }

    public void result(boolean b, @Nullable Object obj, int total) {
    }


    public void resultPage(boolean b, @Nullable Object obj, int total) {
    }


}
