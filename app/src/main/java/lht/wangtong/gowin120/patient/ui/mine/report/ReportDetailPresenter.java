package lht.wangtong.gowin120.patient.ui.mine.report;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/14
 */

public class ReportDetailPresenter extends BasePresenter<ReportDetailContact.View> implements ReportDetailContact.Presenter {

    @Inject
    public ReportDetailPresenter() {
    }

    @Override
    public void getReportDetailInfo(String radiographScreenId) {
        HttpUtil.getObject(Api.viewRadiographScreen.mapClear()
                .addMap("radiographScreenId", radiographScreenId)
                .addBody(), ReportInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){
                    mView.setReportDetailInfo((ReportInfo) obj);
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
                    List<ServiceCategoryInfo> categoryInfos = new ArrayList<>();
                    categoryInfos.addAll((Collection<? extends ServiceCategoryInfo>) obj);
                    mView.setRecommendService(categoryInfos);
                }
            }
        });
    }
}
