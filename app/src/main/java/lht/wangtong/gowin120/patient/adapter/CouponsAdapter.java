package lht.wangtong.gowin120.patient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.bean.CouponInfo;

/**
 * 优惠券adapter
 *
 * @author luoyc
 */
public class CouponsAdapter extends BaseQuickAdapter<CouponInfo.CouponListBean, BaseViewHolder> {

    @Inject
    CouponsAdapter() {
        super(R.layout.item_coupons_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponInfo.CouponListBean item) {
        helper.setText(R.id.coupons_num, "￥" + item.getFaceValue());
        helper.setText(R.id.coupon_total, "满" + item.getCanUseTotal() + "使用");
        helper.setText(R.id.coupon_apply_to, item.getApplyTypeText());
        helper.setText(R.id.coupons_validity, item.getCanUseDateStart() + "-" + item.getCanUseDateEnd());
        if (item.isShow()) {
            helper.addOnClickListener(R.id.receive_btn);
            helper.setGone(R.id.receive_btn, true);
        } else {
            helper.setGone(R.id.receive_btn, false);
        }
    }
}
