package lht.wangtong.gowin120.patient.ui.home.surveyeye;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.EyeAgeProblemResult;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class SurveyResultPresenter extends BasePresenter<SurveyResultContact.View> implements SurveyResultContact.Presenter {

    @Inject
    public SurveyResultPresenter() {
    }

    @Override
    public void getEyeAgeProblemResult(String eyeAgeProblemIds) {
        HttpUtil.getObject(Api.getEyeAgeProblemResult.mapClear()
                .addMap("eyeAgeProblemIds", eyeAgeProblemIds)
                .addBody(), EyeAgeProblemResult.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){
                    mView.setResult((EyeAgeProblemResult) obj);
                }
            }
        });
    }
}
