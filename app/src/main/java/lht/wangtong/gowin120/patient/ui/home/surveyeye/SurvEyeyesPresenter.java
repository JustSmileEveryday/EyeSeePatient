package lht.wangtong.gowin120.patient.ui.home.surveyeye;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.EyeAgeProblemInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class SurvEyeyesPresenter extends BasePresenter<SurveyEyesContact.View> implements SurveyEyesContact.Presenter {

    @Inject
    public SurvEyeyesPresenter() {
    }

    @Override
    public void getEyeAgeProblem(String crowdAgeGroup) {
        HttpUtil.getObjectList(Api.getEyeAgeProblem.mapClear()
                .addMap("crowdAgeGroup", crowdAgeGroup)
                .addBody(), EyeAgeProblemInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){
                    List<EyeAgeProblemInfo> eyeAgeProblemInfos = new ArrayList<>();
                    eyeAgeProblemInfos.addAll((Collection<? extends EyeAgeProblemInfo>) obj);
                    mView.setEyeAgeProblem(eyeAgeProblemInfos);
                }
            }
        });
    }
}
