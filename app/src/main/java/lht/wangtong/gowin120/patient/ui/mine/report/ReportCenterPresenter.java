package lht.wangtong.gowin120.patient.ui.mine.report;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;
import lht.wangtong.gowin120.patient.bean.SystemParamValue;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author Luoyc
 * @date 2018/3/13
 */

public class ReportCenterPresenter extends BasePresenter<ReportCenterContact.View> implements ReportCenterContact.Presenter {
    private int mPage = 1;
    private String familyId = ""; //为空表示自己
    private List<ServiceCategoryInfo> categoryInfos;

    @Inject
    public ReportCenterPresenter() {
    }


    @Override
    public void initData() {
        categoryInfos = new ArrayList<>();
        loadHomeFamilys();
        getSystemParamBykey("groupQrcodeImgUrl");
    }

    @Override
    public void loadHomeFamilys() {
        HttpUtil.getObjectList(Api.queryFamilyInfo.mapClear()
                .addBody(), HomeFamilyInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<HomeFamilyInfo> familyInfos = new ArrayList<>();
                    familyInfos.addAll((List<HomeFamilyInfo>) obj);
                    mView.setHomeFamilys(familyInfos);
                }
            }
        });
    }

    @Override
    public void loadReports(String familyId) {
        HttpUtil.getObjectListPage(Api.historyRadiographScreen.mapClear()
                .addPage(100, mPage)
                .addMap("familyId", familyId)
                .addBody(), ReportInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                     List<ReportInfo> reportInfos = new ArrayList<>();
                    reportInfos.addAll((Collection<? extends ReportInfo>) obj);
                    mView.setReports(reportInfos, total);
                }
            }
        });
    }

    @Override
    public void getRadiographScreenMarketActivityList(String familyId) {
        HttpUtil.getObjectList(Api.getRadiographScreenMarketActivityList.mapClear()
                .addMap("familyId", familyId)
                .addBody(), ServiceCategoryInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    categoryInfos.clear();
                    categoryInfos.addAll((Collection<? extends ServiceCategoryInfo>) obj);
                    mView.setRecommendService(categoryInfos);
                }
            }
        });
    }

    @Override
    public void onRefresh(String familyId) {
        mPage = 1;
        loadReports(familyId);
        getRadiographScreenMarketActivityList(familyId);
    }

    @Override
    public void getSystemParamBykey(String systemParamKey) {
        HttpUtil.getObject(Api.getSystemParamBykey.mapClear()
                .addMap("systemParamKey", systemParamKey)
                .addBody(), SystemParamValue.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setGroupQrcodeImgUrl(((SystemParamValue) obj).getSystemParamValue());
                }
            }
        });
    }
}
