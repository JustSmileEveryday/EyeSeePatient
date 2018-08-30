package lht.wangtong.gowin120.patient.ui.mine.family;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/14
 */

public class MyFamilyPresenter extends BasePresenter<MyFamilyContact.View> implements MyFamilyContact.Presenter {

    @Inject
    public MyFamilyPresenter() {
    }

    @Override
    public void loadFamilyLits() {
        HttpUtil.getObjectList(Api.queryFamilyInfo
                .mapClear()
                .addBody(), HomeFamilyInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<HomeFamilyInfo> infoList = new ArrayList<>();
                    infoList.addAll((List<HomeFamilyInfo>) obj);
                    mView.setFamilyLits(infoList);
                }
            }
        });
    }

    @Override
    public void initData() {
        loadFamilyLits();
    }
}
