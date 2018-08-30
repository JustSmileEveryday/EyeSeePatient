package lht.wangtong.gowin120.patient.ui.home;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.bean.CourseInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/5
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private List<CourseInfo> consultInfos;
    private int pageNo = 1;

    @Inject
    public HomePresenter() {

    }


    @Override
    public void initData() {
        consultInfos = new ArrayList<>();
        loadHomeBanners();
        loadCourse();
        getTodaySignStatus();
    }

    @Override
    public void getTodaySignStatus() {
        HttpUtil.getObject(Api.getTodaySignStatus.mapClear()
                .addBody(), boolean.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    boolean isSign = (boolean) obj;
                    if (!isSign) {
                        mView.setTodaySignStatus(-1);
                    }
                }
            }
        });
    }

    @Override
    public void loadHomeBanners() {
        HttpUtil.getObjectList(Api.getBannerByType
                .mapClear()
                .addMap("bannerType", "1")
                .addMap("bannerSize", "common")
                .addBody(), BannerInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<BannerInfo> bannerInfos = new ArrayList<>();
                    bannerInfos.addAll((List<BannerInfo>) obj);
                    mView.setHomeBanners(bannerInfos);
                }
            }
        });
    }


    @Override
    public void refresh() {
        pageNo = 1;
        initData();
    }

    @Override
    public void loadMore() {
        pageNo++;
        loadCourse();
    }

    @Override
    public void saveServiceRecord() {
        HttpUtil.getObject(Api.saveServiceRecord.mapClear()
                .addMap("serviceType", "2")
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.oneCallSuccess();
                }
            }
        });
    }

    @Override
    public void loadCourse() {
        HttpUtil.getObjectListPage(Api.getHealthNewsByCatalog.mapClear()
                        .addPage(10, pageNo)
                        .addMap("catalogCode", "XAKT")
                        .addBody()
                , CourseInfo.class, new HttpUtil.objectListPageCallback() {
                    @Override
                    public void resultPage(boolean b, @Nullable Object obj, int total) {
                        if (b) {
                            consultInfos.addAll(obj != null ? (Collection<? extends CourseInfo>) obj : null);
                            mView.setCourseList(consultInfos, total);
                        }
                    }
                });
    }
}
