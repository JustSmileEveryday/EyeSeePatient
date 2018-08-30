package lht.wangtong.gowin120.patient.ui.mine.coupons;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.CouponInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 */
public class ReceiveCouponPresenter extends BasePresenter<ReceiveCouponContact.View> implements ReceiveCouponContact.Presenter {

    @Inject
    public ReceiveCouponPresenter() {
    }

    @Override
    public void getCanGetCouponList() {
        HttpUtil.getObject(Api.getCanGetCouponList.mapClear()
                .addBody(), CouponInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setCanGetCoupon((CouponInfo) obj);
                }
            }
        });
    }

    @Override
    public void getCoupon(String couponId, final int position) {
        HttpUtil.getObject(Api.getCoupon.mapClear()
                .addMap("couponId", couponId)
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.removeItem(position);
                }
            }
        });
    }
}
