package lht.wangtong.gowin120.patient.ui.mine.service;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.ServiceRecordInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class MyServiceDetailPresenter extends BasePresenter<MyServiceDetailContact.View> implements MyServiceDetailContact.Presenter{

    @Inject
    public MyServiceDetailPresenter() {
    }

    @Override
    public void loadService(String serviceRecordId) {
        HttpUtil.getObject(Api.viewServiceRecord.mapClear()
                .addMap("serviceRecordId", serviceRecordId)
                .addBody(), ServiceRecordInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setServiceDetail((ServiceRecordInfo) obj);
                }
            }
        });
    }
}
