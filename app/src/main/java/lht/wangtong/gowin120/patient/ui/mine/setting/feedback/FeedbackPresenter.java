package lht.wangtong.gowin120.patient.ui.mine.setting.feedback;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/26
 */

public class FeedbackPresenter extends BasePresenter<FeedbackContact.View> implements FeedbackContact.Presenter {

    @Inject
    public FeedbackPresenter() {
    }

    @Override
    public void commitFeedback(String feedback, String contact) {
        HttpUtil.getObject(Api.saveAdviceFeedback.mapClear()
                        .addMap("advice", feedback)
                        .addBody(),
                String.class, new HttpUtil.objectCallback() {
                    @Override
                    public void result(boolean b, @Nullable Object obj) {
                        if (b) {
                            ToastUtils.showShort("提交成功");
                            mView.close();
                        }
                    }
                });
    }
}
