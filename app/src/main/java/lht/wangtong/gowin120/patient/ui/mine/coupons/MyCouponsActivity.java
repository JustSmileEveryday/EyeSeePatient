package lht.wangtong.gowin120.patient.ui.mine.coupons;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.CouponsAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.CouponInfo;

/**
 * 我的优惠券
 *
 * @author luoyc
 */
@Route(path = "/mine/coupons/MyCouponsActivity")
public class MyCouponsActivity extends BaseActivity<MyCouponsPresenter> implements MyCouponsContact.View {

    @BindView(R.id.coupon_num)
    TextView couponNum;
    @BindView(R.id.get_coupon_layout)
    ConstraintLayout getCouponLayout;
    @BindView(R.id.coupons_list)
    RecyclerView couponsListView;
    @BindView(R.id.collection_null_layout)
    ConstraintLayout mConstraintLayout;
    @Inject
    CouponsAdapter mCouponsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupons;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        couponsListView.setLayoutManager(new LinearLayoutManager(this));
        mCouponsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        couponsListView.setAdapter(mCouponsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getCanGetCouponList();
        mPresenter.getMyCouponList();
    }

    @Override
    public void setIsHaveCoupon(int count) {
        if (count == 0) {
            getCouponLayout.setVisibility(View.GONE);
        } else {
            couponNum.setText(count + "");
            getCouponLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setMyCouponList(CouponInfo couponInfo) {
        if (couponInfo != null && couponInfo.getCouponList() != null) {
            if (couponInfo.getCouponList().size() > 0) {
                mConstraintLayout.setVisibility(View.GONE);
                mCouponsAdapter.setNewData(couponInfo.getCouponList());
            } else {
                mConstraintLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.get_coupon_btn)
    public void onViewClicked() {
        ARouter.getInstance().build("/mine/coupons/ReceiveCouponActivity")
                .withInt("mType", 2)
                .navigation();
    }
}
