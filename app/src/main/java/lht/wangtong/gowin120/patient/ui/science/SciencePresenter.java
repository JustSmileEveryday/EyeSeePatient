package lht.wangtong.gowin120.patient.ui.science;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.bean.CatalogInfo;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 *
 * @author luoyc
 * @date 2018/3/5
 */

public class SciencePresenter extends BasePresenter<ScienceContract.View> implements ScienceContract.Presenter {

    @Inject
    public SciencePresenter() {
    }

    @Override
    public void loadScienceBanner() {
        HttpUtil.getObjectList(Api.getBannerByType
                .addMap("bannerType", "2")
                .addMap("bannerSize", "common")
                .addBody(), BannerInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<BannerInfo> bannerInfos = new ArrayList<>();
                    bannerInfos.addAll((List<BannerInfo>) obj);
                    List<String> tips = new ArrayList<>();
                    for (BannerInfo info : bannerInfos) {
                        tips.add(info.getTitle());
                    }
                    mView.setScienceBanner(bannerInfos, tips);
                }
            }
        });
    }

    @Override
    public void loadCategorys() {
        HttpUtil.getObjectList(Api.GetHealthNewsCatalog.mapClear()
                .addMap("catalogCode","KP")
                .addBody(), CatalogInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<CatalogInfo> infos  = new ArrayList<>();
                    infos.addAll((Collection<? extends CatalogInfo>) obj != null ? (Collection<? extends CatalogInfo>) obj : null);
                    mView.setCategorys(infos);
                }
            }
        });
    }
}
