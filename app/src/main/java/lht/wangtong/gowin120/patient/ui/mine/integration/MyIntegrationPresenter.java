package lht.wangtong.gowin120.patient.ui.mine.integration;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.IntegrationInfo;
import lht.wangtong.gowin120.patient.bean.MemberInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class MyIntegrationPresenter extends BasePresenter<MyIntegrationContract.View> implements MyIntegrationContract.Presenter {

    private int pageNo = 1;
    private List<IntegrationInfo> mIntegrationInfos;

    @Inject
    public MyIntegrationPresenter() {
    }


    @Override
    public void initData() {
        mIntegrationInfos = new ArrayList<>();
        getMemberInfo();
        getIntegration();
    }

    @Override
    public void getMemberInfo() {
        HttpUtil.getObject(Api.MEMBERINFO.mapClear()
                        .addBody(), MemberInfo.class,
                new HttpUtil.objectCallback() {
                    @Override
                    public void result(boolean b, @Nullable Object obj) {
                        if (b) {
                            MemberInfo info = (MemberInfo) obj;
                            mView.setIntegrationNum(info.getBonusUsable());
                        }
                    }
                });
    }

    @Override
    public void getIntegration() {
        HttpUtil.getObjectListPage(Api.queryMemberPointIo.mapClear()
                .addPage(10, pageNo)
                .addBody(), IntegrationInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    mIntegrationInfos.addAll(obj != null ? (Collection<IntegrationInfo>) obj : null);
                    mView.setIntegrationList(mIntegrationInfos, total);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        mIntegrationInfos.clear();
        getIntegration();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        getIntegration();
    }
}
