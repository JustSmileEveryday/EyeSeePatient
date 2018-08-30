package lht.wangtong.gowin120.patient.ui.mine.coupons;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.CouponsAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.CouponInfo;

/**
 * 领取优惠券
 *
 * @author luoyc
 */
@Route(path = "/mine/coupons/ReceiveCouponActivity")
public class ReceiveCouponActivity extends BaseActivity<ReceiveCouponPresenter> implements ReceiveCouponContact.View
        , CouponsAdapter.OnItemChildClickListener, View.OnClickListener {

    @BindView(R.id.course_list)
    RecyclerView mCourseListView;
    @Inject
    CouponsAdapter mCouponsAdapter;
    @Autowired
    int mType;
    ImageView mBackBtn;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_receive_coupon;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mBackBtn = findViewById(R.id.ordinary_titlebar_msg_img);
        mBackBtn.setOnClickListener(this);
        mCourseListView.setLayoutManager(new LinearLayoutManager(this));
        mCouponsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mCouponsAdapter.setOnItemChildClickListener(this);
        mCourseListView.setAdapter(mCouponsAdapter);
        mPresenter.getCanGetCouponList();
    }

    @Override
    public void setCanGetCoupon(CouponInfo canGetCoupon) {
        if (canGetCoupon != null && canGetCoupon.getCouponList() != null) {
            for (int i = 0; i < canGetCoupon.getCouponList().size(); i++) {
                canGetCoupon.getCouponList().get(i).setShow(true);
            }
            mCouponsAdapter.setNewData(canGetCoupon.getCouponList());
        }
    }

    @Override
    public void removeItem(int position) {
        mCouponsAdapter.getData().remove(position);
        mCouponsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.receive_btn:
                //立即领取
                mPresenter.getCoupon(((CouponInfo.CouponListBean) adapter.getData().get(position)).getCouponId() + "", position);
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ordinary_titlebar_msg_img:
                //返回
                if (mType == 1) {
                    //跳转首页
                    ARouter.getInstance().build("/ui/MainActivity")
                            .navigation();
                    finish();
                } else if (mType == 2) {
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
