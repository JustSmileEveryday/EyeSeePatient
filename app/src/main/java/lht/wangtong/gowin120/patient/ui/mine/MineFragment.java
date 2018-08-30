package lht.wangtong.gowin120.patient.ui.mine;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.event.UserLoginEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.config.Constant;
import lht.wangtong.gowin120.patient.db.User;
import lht.wangtong.gowin120.patient.util.LoginUtil;
import lht.wangtong.gowin120.patient.view.OwnTabsItem;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author luoyc
 * @date 2018/3/5
 */

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View, View.OnClickListener {

    @BindView(R.id.mine_head_layout)
    RelativeLayout mHeadViewLayout;
    @BindView(R.id.mine_top)
    ImageView mTopView;
    @BindView(R.id.mine_head)
    CircleImageView mHeadView;
    @BindView(R.id.mine_name)
    TextView mUserName;
    @BindView(R.id.mine_family_manager)
    OwnTabsItem mFamilyManager;
    @BindView(R.id.mine_least_text)
    TextView mLeastText;
    private User mMemberInfo;
    private int mTopViewH = 0;
    private int mHeadViewH = 0;
    private int mCount = 0;
    private ConstraintLayout.LayoutParams mLayoutParams;


    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        EventBus.getDefault().register(this);
        initHeadView();
//        showGuide();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void loginEventBus(UserLoginEvent event) {
        if (event.isUpdate()) {
            if (LoginUtil.user != null) {
                mPresenter.getMemberInfo();
                return;
            }
        } else {
            if (event.isLogin()) {
                if (LoginUtil.user != null) {
                    mPresenter.getMemberInfo();
                    return;
                }
            } else {
                ARouter.getInstance().build("/ui/login/LoginActivity").navigation();
            }
        }
    }

    @Override
    public void initHeadView() {
        //获取当前headview的top距离
        // 向 ViewTreeObserver 注册方法，以获取控件尺寸
        ViewTreeObserver mTopViewViewTreeObserver = mTopView.getViewTreeObserver();
        mTopViewViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTopViewH = mTopView.getBottom();
                // 成功调用一次后，移除 Hook 方法，防止被反复调用
                // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                // 使用新方法 removeOnGlobalLayoutListener() 代替
                mTopView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        ViewTreeObserver mHeadViewViewTreeObserver = mHeadViewLayout.getViewTreeObserver();
        mHeadViewViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeadViewH = mHeadViewLayout.getHeight();
                int marginTop = mTopViewH - mHeadViewH / 2;
                mLayoutParams = (ConstraintLayout.LayoutParams) mHeadViewLayout.getLayoutParams();
                mLayoutParams.topMargin = marginTop;
                mHeadViewLayout.setLayoutParams(mLayoutParams);
                // 成功调用一次后，移除 Hook 方法，防止被反复调用
                // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                // 使用新方法 removeOnGlobalLayoutListener() 代替
                mHeadViewLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getCanGetCouponList();
    }

    @Override
    public void setUserData(User memberInfo) {
        mMemberInfo = memberInfo;
        Glide.with(this)
                .load(Api.HOST_IMG + memberInfo.getPicUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.mine_head_default_img)
                        .error(R.drawable.mine_head_default_img)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop())
                .transition(withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mHeadView.setImageDrawable(resource);
                    }
                });
        mUserName.setText(memberInfo.getMemberName());
    }

    @Override
    public void showGuide() {
        NewbieGuide.with(getActivity())
                .setLabel("mine_guide" + Constant.APPVERSION)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(mFamilyManager, HighLight.Shape.ROUND_RECTANGLE, 20, 0)
                        .setLayoutRes(R.layout.mine_guide_1, R.id.kown_btn)
                        .setEverywhereCancelable(false)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                RelativeLayout mGuideHeadLayout = view.findViewById(R.id.mine_head_layout);
                                mGuideHeadLayout.setLayoutParams(mLayoutParams);
                            }
                        }))
                .show();
    }

    @Override
    public void setCanGetCoupon(int count) {
        mCount = count;
        if (count == 0) {
            mLeastText.setVisibility(View.GONE);
        } else {
            mLeastText.setText(count + "张");
            mLeastText.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.mine_scaner_btn, R.id.mine_head_layout, R.id.setting_btn, R.id.mine_report, R.id.mine_appointment, R.id.mine_collection, R.id.mine_family_manager, R.id.mine_service,
            R.id.mine_balance, R.id.mine_integral, R.id.mine_coupons_layout})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_btn:
                ARouter.getInstance().build("/mine/setting/SettingActivity").navigation();
                break;
            case R.id.mine_head_layout:
                ARouter.getInstance().build("/mine/info/MineInfoActivity").withParcelable("mInfo", mMemberInfo).withInt("mType", 1).navigation();
                break;
            case R.id.mine_scaner_btn:
                ARouter.getInstance().build("/mine/scanercode/ScanerCodeActivity").navigation();
                break;
            case R.id.mine_report:
                ARouter.getInstance().build("/mine/report/ReportCenterActivity")
                        .withString("mMemberName", mMemberInfo.getMemberName())
                        .withString("mHeadPic", mMemberInfo.getPicUrl())
                        .withInt("type", 1)
                        .navigation();
                break;
            case R.id.mine_appointment:
                ARouter.getInstance().build("/mine/appointment/AppointmentActivity").navigation();
                break;
            case R.id.mine_collection:
                ARouter.getInstance().build("/mine/collection/MyCollectionActivity").navigation();
                break;
            case R.id.mine_family_manager:
                ARouter.getInstance().build("/mine/family/MyFamilyActivity").navigation();
                break;
            case R.id.mine_service:
                //我的服务
                ARouter.getInstance().build("/mine/service/MyServiceActivity").navigation();
                break;
            case R.id.mine_balance:
                //我的余额
                ARouter.getInstance().build("/mine/balance/MyBalanceActivity").navigation();
                break;
            case R.id.mine_integral:
                //我的积分
                ARouter.getInstance().build("/mine/integration/MyIntegrationActivity").navigation();
                break;
            case R.id.mine_coupons_layout:
                //我的优惠券
                ARouter.getInstance().build("/mine/coupons/MyCouponsActivity")
                        .withInt("count", mCount)
                        .navigation();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
