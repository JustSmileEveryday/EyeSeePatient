package lht.wangtong.gowin120.patient.ui.service.category;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/4/2
 */

public class ServiceCategoryPresenter extends BasePresenter<ServiceCategoryContact.View> implements ServiceCategoryContact.Presenter {
    private List<ServiceCategoryInfo> categoryInfos;
    private int pageNo = 1;

    @Inject
    public ServiceCategoryPresenter() {
    }

    @Override
    public void initData(String serviceType) {
        categoryInfos = new ArrayList<>();
        loadServiceList(serviceType);
    }

    @Override
    public void loadServiceList(String serviceType) {
        HttpUtil.getObjectListPage(Api.getAllService.mapClear()
                .addMap("serviceType", serviceType)
                .addPage(10, pageNo)
                .addBody(), ServiceCategoryInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    categoryInfos.addAll(obj != null ? (Collection<? extends ServiceCategoryInfo>) obj : null);
                    mView.setServiceList(categoryInfos,total);
                }
            }
        });
    }

    @Override
    public void onRresh(String serviceType) {
        pageNo = 1;
        categoryInfos.clear();
        loadServiceList(serviceType);
    }

    @Override
    public void loadMore(String serviceType) {
        pageNo++;
        loadServiceList(serviceType);
    }
}
