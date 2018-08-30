package lht.wangtong.gowin120.patient.ui.mine.appointment;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.AppointmentInfo;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/14
 */

public class AppointmentPresenter extends BasePresenter<AppointmentContact.View> implements AppointmentContact.Presenter {
    private int mPageNo = 1;
    private List<AppointmentInfo> infoList;

    @Inject
    public AppointmentPresenter() {
    }

    @Override
    public void initData() {
        infoList = new ArrayList<>();
        loadHomeFamilys();
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
    public void loadAppointmentLits(String familyId, final boolean isClear) {
        HttpUtil.getObjectListPage(Api.queryReservationServiceList.mapClear()
                .addMap("familyId", familyId)
                .addPage(10, mPageNo)
                .addBody(), AppointmentInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    if (isClear) {
                        infoList.clear();
                    }
                    infoList.addAll((List<AppointmentInfo>) obj);
                    mView.setAppointmentLits(infoList, total);
                }
            }
        });
    }

    @Override
    public void loadMore(String familyId) {
        mPageNo++;
        loadAppointmentLits(familyId,false);
    }

    @Override
    public void onRefresh(String familyId) {
        mPageNo = 1;
        infoList.clear();
        loadAppointmentLits(familyId,false);
    }

}
