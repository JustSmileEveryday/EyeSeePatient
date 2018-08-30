package lht.wangtong.gowin120.patient.ui.home;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huantansheng.easyphotos.EasyPhotos;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.CourseListAdapter;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.bean.CourseInfo;
import lht.wangtong.gowin120.patient.bean.event.MyDynamicEvent;
import lht.wangtong.gowin120.patient.bean.event.SignEvent;
import lht.wangtong.gowin120.patient.bean.event.UserLoginEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.util.GlideEngine;
import lht.wangtong.gowin120.patient.util.LoginUtil;
import lht.wangtong.gowin120.patient.view.SignDialog;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


/**
 * 首页fragment
 *
 * @author luoyc
 * @date 2018/3/5
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, BGABanner.Adapter<ImageView, BannerInfo>, BGABanner.Delegate<ImageView, BannerInfo>
        , SwipeRefreshLayout.OnRefreshListener, CourseListAdapter.RequestLoadMoreListener, View.OnClickListener, CourseListAdapter.OnItemClickListener {

    public static final int GET_PHOTO = 105;
    @BindView(R.id.home_course_view)
    RecyclerView mHomeCourseRecyclerView;
    @BindView(R.id.home_swipelayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.home_sign_btn)
    ImageView mSignBtn;
    @Inject
    CourseListAdapter mHomeCourseAdapter;
    private View mHeaderView;
    private BGABanner mBannerAds;
    private ConstraintLayout.LayoutParams mBannerParams;
    private LinearLayout mAppointmentService;
    private LinearLayout mMyDynamic;
    private LinearLayout mReportCenter;
    private LinearLayout mOneCall;
    private ConstraintLayout mTryGlassesLayout;
    private ConstraintLayout mSurveyEyesYears;
    private ImageView mMineDynamicImg;
    private QBadgeView mQBadgeView;
    private ConstraintLayout mMoreLayout;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        /**设置活动RecyclerView*/
        mHomeCourseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeCourseAdapter.setOnLoadMoreListener(this, mHomeCourseRecyclerView);
        mHomeCourseAdapter.setOnItemClickListener(this);
        mHomeCourseRecyclerView.setAdapter(mHomeCourseAdapter);
        /**设置BannerHeadView*/
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.home_header_view, null);
        mBannerAds = mHeaderView.findViewById(R.id.home_banner);
        mAppointmentService = mHeaderView.findViewById(R.id.appointment_service);
        mReportCenter = mHeaderView.findViewById(R.id.screening_report);
        mMyDynamic = mHeaderView.findViewById(R.id.mine_dynamic);
        mMineDynamicImg = mHeaderView.findViewById(R.id.mine_dynamic_img);
        mOneCall = mHeaderView.findViewById(R.id.one_to_call);
        mMoreLayout = mHeaderView.findViewById(R.id.more_layout);
        mTryGlassesLayout = mHeaderView.findViewById(R.id.home_try_glasses_layout);
        mSurveyEyesYears = mHeaderView.findViewById(R.id.home_survey_eyes_years);
        initOnClick();
        initBanner();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initBanner() {
        //宽高比
        float rate;
        //广告图片高度
        int imgH = 450;
        //广告图片宽度
        int imgW = 1080;
        //当前比例
        rate = imgW * 1000 / imgH;
        WindowManager wm = (WindowManager) getActivity().getSystemService(
                Context.WINDOW_SERVICE);
        float width = wm.getDefaultDisplay().getWidth();
        mBannerParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, (int) (width * 1000 / rate));
        mBannerAds.setLayoutParams(mBannerParams);
        mBannerAds.setDelegate(this);
        mBannerAds.setAutoPlayAble(true);
        mBannerAds.setIsNeedShowIndicatorOnOnlyOnePage(false);
        mBannerAds.setAutoPlayInterval(5000);
        mBannerAds.setAllowUserScrollable(true);
        mHomeCourseAdapter.addHeaderView(mHeaderView);
    }

    @Override
    public void setHomeBanners(List<BannerInfo> banners) {
        mBannerAds.setAdapter(this);
        mBannerAds.setData(banners, null);
    }

    @Override
    public void initOnClick() {
        mAppointmentService.setOnClickListener(this);
        mAppointmentService.setOnClickListener(this);
        mReportCenter.setOnClickListener(this);
        mMyDynamic.setOnClickListener(this);
        mOneCall.setOnClickListener(this);
        mMoreLayout.setOnClickListener(this);
        mTryGlassesLayout.setOnClickListener(this);
        mSurveyEyesYears.setOnClickListener(this);
    }

    @Override
    public void oneCall() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.one_call_title)
                .content(R.string.one_call_content)
                .positiveText(R.string.sure)
                .negativeText(R.string.cancel)
                .positiveColor(Color.parseColor("#01C1B4"))
                .negativeColor(Color.parseColor("#3C4045"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mPresenter.saveServiceRecord();
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void setTodaySignStatus(int num) {
        //当前没有签到
        if (mQBadgeView == null) {
            mQBadgeView = new QBadgeView(getActivity());
            mQBadgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
            mQBadgeView.setGravityOffset(5, 7, true);
            mQBadgeView.bindTarget(mSignBtn);
        }
        mQBadgeView.setBadgeNumber(num);
        mQBadgeView.setBadgePadding(4, true);
    }

    @Override
    public void oneCallSuccess() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.one_call_sign)
                .content(R.string.one_call_content_success)
                .positiveText(R.string.sure)
                .negativeText(R.string.cancel)
                .positiveColor(Color.parseColor("#01C1B4"))
                .negativeColor(Color.parseColor("#3C4045"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void setCourseList(List<CourseInfo> courseInfos, int total) {
        mHomeCourseAdapter.setNewData(courseInfos);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (total == courseInfos.size()) {
            //已经完全加载
            mHomeCourseAdapter.loadMoreEnd();
            mHomeCourseAdapter.setEnableLoadMore(false);
        } else {
            mHomeCourseAdapter.setEnableLoadMore(true);
            mHomeCourseAdapter.loadMoreComplete();
        }
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable BannerInfo model, int position) {
        Glide.with(this)
                .load(Api.HOST_IMG + model.getImgUrl())
                .apply(new RequestOptions().error(R.drawable.mine_head_default_img)
                        .centerCrop()
                        .placeholder(R.drawable.home_banner_placeholder_img)
                        .error(R.drawable.home_banner_error_img)
                        .dontAnimate())
                .transition(withCrossFade())
                .into(itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable BannerInfo model, int position) {
        if (model.getProdType() == 3) {
            //文章
            switch (model.getArticleType()) {
                case 1:
                    //文章
                    ARouter.getInstance()
                            .build("/science/category/CategoryDetailActivity")
                            .withString("mArticleId", model.getProdId())
                            .navigation();
                    break;
                case 2:
                    //视频
                    ARouter.getInstance()
                            .build("/home/classroom/CourseDetailActivity")
                            .withString("mArticleId", model.getProdId())
                            .navigation();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @OnClick({R.id.home_scaner_btn, R.id.search_layout, R.id.home_sign_btn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_scaner_btn:
                //扫一扫
                ARouter.getInstance().build("/mine/scanercode/ScanerCodeActivity").navigation();
                break;
            case R.id.one_to_call:
                //一键呼叫
                oneCall();
                break;
            case R.id.appointment_service:
                //预约服务
                ARouter.getInstance().build("/home/appointment/AppointmentServiceActivity").navigation();
                break;
            case R.id.mine_dynamic:
                //我的动态
                addBadgeAtMyDynamic(0);
                ARouter.getInstance().build("/home/dynamic/MyDynamicActivity").navigation();
                break;
            case R.id.screening_report:
                //报告中心
                ARouter.getInstance().build("/mine/report/ReportCenterActivity")
                        .withString("mMemberName", LoginUtil.user.getMemberName())
                        .withString("mHeadPic", LoginUtil.user.getPicUrl())
                        .withInt("type", 1)
                        .navigation();
                break;
            case R.id.more_layout:
                //课堂 更多按钮
                ARouter.getInstance().build("/home/classroom/ClassroomListActivity")
                        .navigation();
                break;
            case R.id.search_layout:
                //搜索
                ARouter.getInstance().build("/ui/search/SearchActivity")
                        .withInt("mType", 1)
                        .navigation();
                break;
            case R.id.home_sign_btn:
                //签到
                SignDialog signDialog = new SignDialog(getActivity());
                signDialog.setCancelable(true);
                signDialog.show();
                break;
            case R.id.home_try_glasses_layout:
                //试镜间
                EasyPhotos.createAlbum(getActivity(), true, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                        .setFileProviderAuthority("lht.wangtong.gowin120.patient.fileprovider")//参数说明：见下方`FileProvider的配置`
                        .start(GET_PHOTO);
                break;
            case R.id.home_survey_eyes_years:
                //测眼龄
                ARouter.getInstance()
                        .build("/home/surveyeye/SurveyEyesActivity")
                        .navigation();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance()
                .build("/home/classroom/CourseDetailActivity")
                .withString("mArticleId", ((CourseInfo) adapter.getData().get(position)).getArticleId())
                .navigation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void loginEventBus(UserLoginEvent event) {
        if (event.isLogin()) {
            mPresenter.initData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onNewMyDynamic(MyDynamicEvent event) {
        if (event.isNewMessage()) {
            addBadgeAtMyDynamic(-1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSign(SignEvent event) {
        if (event.isUpdate()) {
            setTodaySignStatus(0);
        }
    }

    private void addBadgeAtMyDynamic(int num) {
        if (mQBadgeView == null) {
            mQBadgeView = new QBadgeView(getActivity());
            mQBadgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
            mQBadgeView.bindTarget(mMineDynamicImg);
            mQBadgeView.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState) {

                    }
                }
            });
        }
        mQBadgeView.setBadgeNumber(num);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
