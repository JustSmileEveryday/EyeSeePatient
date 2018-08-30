package lht.wangtong.gowin120.patient.ui.mine.coupons;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.CouponInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * 我的优惠券
 *
 * @author luoyc
 */
public class MyCouponsPresenter extends BasePresenter<MyCouponsContact.View> implements MyCouponsContact.Presenter {

    @Inject
    public MyCouponsPresenter() {
    }

    @Override
    public void getMyCouponList() {
        HttpUtil.getObject(Api.getMyCouponList.mapClear()
                .addBody(), CouponInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b){
                    mView.setMyCouponList((CouponInfo) obj);
                }
            }
        });
    }

    @Override
    public void getCanGetCouponList() {
        HttpUtil.getObject(Api.getCanGetCouponList.mapClear()
                .addBody(), CouponInfo.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    CouponInfo couponInfo = (CouponInfo) obj;
                    mView.setIsHaveCoupon(couponInfo.getCount());
                }
            }
        });
    }
}
