package lht.wangtong.gowin120.patient.ui.service.category;

import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.ServiceDetailInfo;
import lht.wangtong.gowin120.patient.bean.ServiceRecordIdInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class ServiceDetailPresenter extends BasePresenter<ServiceDetailContract.View> implements ServiceDetailContract.Presenter {

    @Inject
    public ServiceDetailPresenter() {
    }

    @Override
    public void loadServiceDetail(String marketActivityId) {
        HttpUtil.getObject(Api.viewMarketActivity.mapClear()
                .addMap("marketActivityId", marketActivityId)
                .addBody(), ServiceDetailInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setServiceDetail((ServiceDetailInfo) obj);
                }
            }
        });
    }

    @Override
    public void saveService(String marketActivityId) {
        HttpUtil.getObject(Api.saveServiceRecordNew.mapClear()
                .addMap("marketActivityId", marketActivityId)
                .addBody(), ServiceRecordIdInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    ARouter.getInstance()
                            .build("/service/order/OrderActivity")
                            .withString("serviceRecordId", ((ServiceRecordIdInfo) obj).getServiceRecordId())
                            .navigation();
                }
            }
        });
    }
}
