package lht.wangtong.gowin120.patient.ui.service;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;
import lht.wangtong.gowin120.patient.util.CommonInfoUtils;

/**
 *
 * @author Luoyc
 * @date 2018/3/5
 */

public class ServicePresenter extends BasePresenter<ServiceContract.View> implements ServiceContract.Presenter {

    @Inject
    public ServicePresenter() {
    }

    @Override
    public void loadServiceBanner() {
        HttpUtil.getObjectList(Api.getBannerByType
                .addMap("bannerType", "3")
                .addMap("bannerSize", "common")
                .addBody(), BannerInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<BannerInfo> bannerInfos = new ArrayList<>();
                    bannerInfos.addAll(obj != null ? (List<BannerInfo>) obj : null);
                    mView.setServiceBanner(bannerInfos);
                }
            }
        });
    }

    @Override
    public void loadCategorys() {
       List<CommonCdInfo> cdInfoList = CommonInfoUtils.getCommonCdInfo("ChargeServiceType");
       mView.setCategorys(cdInfoList);
    }
}
