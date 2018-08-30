package lht.wangtong.gowin120.patient.ui.consult.supposeask;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.IllnessQuestionInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class SupposeDetailPresenter extends BasePresenter<SupposeDetailContract.View> implements SupposeDetailContract.Presenter {

    @Inject
    public SupposeDetailPresenter() {
    }

    @Override
    public void loadDetail(String illnessQuestionId) {
        HttpUtil.getObject(Api.viewIllnessQuestion.mapClear()
                .addMap("illnessQuestionId", illnessQuestionId)
                .addBody(), IllnessQuestionInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setDetail((IllnessQuestionInfo) obj);
                }
            }
        });
    }

    @Override
    public void saveIsUseful(String illnessQuestionId, int type) {
        HttpUtil.getObject(Api.saveViewIllnessQuestion.mapClear()
                .addMap("illnessQuestionId", illnessQuestionId)
                .addMap("type", type)
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {

            }
        });
    }
}
