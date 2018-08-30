package lht.wangtong.gowin120.patient.ui.mine.service;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.MyServiceInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class MyServicePresenter extends BasePresenter<MyServiceContact.View> implements MyServiceContact.Presenter {
    private List<MyServiceInfo> myServiceInfos;
    private int pageNo = 1;

    @Inject
    public MyServicePresenter() {
    }


    @Override
    public void initData() {
        myServiceInfos = new ArrayList<>();
        loadMyServices();
    }

    @Override
    public void loadMyServices() {
        HttpUtil.getObjectListPage(Api.queryServiceRecord.mapClear()
                .addPage(10, pageNo)
                .addBody(), MyServiceInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    myServiceInfos.addAll(obj != null ? (Collection<? extends MyServiceInfo>) obj : null);
                    mView.setServices(myServiceInfos, total);
                }
            }
        });
    }

    @Override
    public void onRresh() {
        pageNo = 1;
        if (myServiceInfos != null) {
            myServiceInfos.clear();
        }
        loadMyServices();
    }

    @Override
    public void loadMore() {
        pageNo++;
        loadMyServices();
    }
}
