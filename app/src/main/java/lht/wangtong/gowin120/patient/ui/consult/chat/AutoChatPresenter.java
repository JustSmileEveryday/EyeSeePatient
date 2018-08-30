package lht.wangtong.gowin120.patient.ui.consult.chat;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.AutoMessage;
import lht.wangtong.gowin120.patient.bean.ConsultEmployeeInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class AutoChatPresenter extends BasePresenter<AutoChatContract.View> implements AutoChatContract.Presenter {

    @Inject
    public AutoChatPresenter() {
    }


    @Override
    public void getRobotAnswer(String question) {
        HttpUtil.getObject(Api.getRobotAnswer.mapClear()
                .addMap("question", question)
                .addBody(), AutoMessage.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){
                    mView.setMessage((AutoMessage) obj);
                }
            }
        });
    }

    @Override
    public void getConsultEmployeeInfo(String consultEmployeeType) {
        HttpUtil.getObject(Api.getConsultEmployeeInfo.mapClear()
                .addMap("consultEmployeeType",1)
                .addBody(), ConsultEmployeeInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){
                    mView.skipChatActivity((ConsultEmployeeInfo) obj);
                }
            }
        });
    }
}
