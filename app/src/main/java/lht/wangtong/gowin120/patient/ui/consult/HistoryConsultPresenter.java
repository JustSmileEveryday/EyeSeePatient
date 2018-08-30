package lht.wangtong.gowin120.patient.ui.consult;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.ConsultInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class HistoryConsultPresenter extends BasePresenter<HistoryConsultContact.View> implements HistoryConsultContact.Presenter {

    @Inject
    public HistoryConsultPresenter() {
    }

    @Override
    public void getMemberConsultList() {
        HttpUtil.getObjectListPage(Api.getMemberConsultList.mapClear()
                .addPage(20, 1)
                .addBody(), ConsultInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    List<ConsultInfo> consultInfos = new ArrayList<>();
                    consultInfos.addAll((Collection<? extends ConsultInfo>) obj);
                    mView.setConsultList(consultInfos);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        getMemberConsultList();
    }
}
