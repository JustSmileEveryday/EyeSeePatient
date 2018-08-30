package lht.wangtong.gowin120.patient.ui.home.dynamic;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.MessageInfo;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/27
 */

public class MyDynamicPresenter extends BasePresenter<MyDynamicContact.View> implements MyDynamicContact.Presenter {
    private int pageNo = 1;
    private List<MessageInfo> messageInfos;

    @Inject
    public MyDynamicPresenter() {
    }

    @Override
    public void initData() {
        messageInfos = new ArrayList<>();
        loadMessage();
    }

    @Override
    public void loadMessage() {
        HttpUtil.getObjectListPage(Api.getMyMessageList.mapClear()
                .addPage(10, pageNo)
                .addMap("busiType", "")
                .addMap("sendDate", "")
                .addMap("msgBody", "")
                .addBody(), MessageInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    messageInfos.addAll((Collection<? extends MessageInfo>) obj != null ? (Collection<? extends MessageInfo>) obj : null);
                    mView.setMessage(messageInfos, total);
                }
            }
        });
    }

    @Override
    public void loadMore() {
        pageNo++;
        loadMessage();
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        messageInfos.clear();
        loadMessage();
    }

    @Override
    public void loadReportDetail(String radiographScreenId) {
        HttpUtil.getObject(Api.viewRadiographScreen.mapClear()
                .addMap("radiographScreenId", radiographScreenId)
                .addBody(), ReportInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){
                    mView.skipReportDetail((ReportInfo) obj);
                }
            }
        });
    }
}
