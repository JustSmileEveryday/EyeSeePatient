package lht.wangtong.gowin120.patient.ui.consult;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.bean.ConsultEmployeeInfo;
import lht.wangtong.gowin120.patient.bean.IllnessQuestionInfoId;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class NewConsultPresenter extends BasePresenter<NewConsultContact.View> implements NewConsultContact.Presenter {
    private int pageNo = 1;
    private List<IllnessQuestionInfoId> questionInfoIds;

    @Inject
    public NewConsultPresenter() {
    }

    @Override
    public void initData() {
        questionInfoIds = new ArrayList<>();
        loadSupposeList();
        getConsult();
    }

    @Override
    public void loadSupposeList() {
        HttpUtil.getObjectListPage(Api.getIllnessQuestionList.mapClear()
                .addPage(10, pageNo)
                .addBody(), IllnessQuestionInfoId.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    questionInfoIds.addAll((Collection<? extends IllnessQuestionInfoId>) obj);
                    mView.setSupposeList(questionInfoIds, total);
                }
            }
        });
    }

    @Override
    public void getConsult() {
        HttpUtil.getObjectList(Api.getBannerByType.mapClear()
                .addMap("bannerType", "4")
                .addBody(), BannerInfo.class, new HttpUtil.objectListCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    List<BannerInfo> bannerInfos = new ArrayList<>();
                    bannerInfos.addAll((Collection<? extends BannerInfo>) obj);
                    mView.setAutoConsult(bannerInfos);
                }
            }
        });
    }

    @Override
    public void getConsultEmployeeInfo(final String consultEmployeeType) {
        HttpUtil.getObject(Api.getConsultEmployeeInfo.mapClear()
                .addMap("consultEmployeeType", consultEmployeeType)
                .addBody(), ConsultEmployeeInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.skipChatActivity(consultEmployeeType, (ConsultEmployeeInfo) obj);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        questionInfoIds.clear();
        getConsult();
        loadSupposeList();
    }

    @Override
    public void loadMore() {
        pageNo++;
        loadSupposeList();
    }
}
