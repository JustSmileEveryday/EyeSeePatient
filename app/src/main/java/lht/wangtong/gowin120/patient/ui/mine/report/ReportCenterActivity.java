package lht.wangtong.gowin120.patient.ui.mine.report;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.HomeFamilyAdapter;
import lht.wangtong.gowin120.patient.adapter.MyReportAdapter;
import lht.wangtong.gowin120.patient.adapter.ServiceCategoryAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;
import lht.wangtong.gowin120.patient.bean.event.RecommendServiceEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.view.ScrollArrowDrawable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 报告中心
 *
 * @author luoyc
 * @date 2018/3/13
 */
@Route(path = "/mine/report/ReportCenterActivity")
public class ReportCenterActivity extends BaseActivity<ReportCenterPresenter> implements ReportCenterContact.View, SwipeRefreshLayout.OnRefreshListener
        , HomeFamilyAdapter.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.report_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.report_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.right_text)
    TextView rightText;
    @Inject
    MyReportAdapter mReportAdapter;
    @Autowired
    String mMemberName;
    @Autowired
    String mHeadPic;
    @Autowired
    int type;
    @Inject
    ServiceCategoryAdapter mServiceAdapter;
    private HomeFamilyAdapter mFamilyAdapter;
    private View mHeaderView;
    private View mFooterView;
    private TextView mReportDate;
    private TextView mReportName;
    private TextView mReportNums;
    private CardView mReportLayout;
    private ImageView mGroupImg;
    private ConstraintLayout mReportNullLayout;
    private TextView mAppointmentBtn;
    private List<HomeFamilyInfo> familyInfoList;
    private ScrollArrowDrawable mScrollArrowDrawable;
    private String familyId = "";
    private ConstraintLayout moreBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_center;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        rightText.setText("申请报告");
        rightText.setVisibility(View.VISIBLE);
        rightText.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        addItemOnClick();
//        mReportAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mReportAdapter);
        /**设置HeadView*/
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.report_center_head_view, null);
        RecyclerView mFamilyRecyclerView = mHeaderView.findViewById(R.id.family_RecyclerView);
        mGroupImg = mHeaderView.findViewById(R.id.group_img);
        mScrollArrowDrawable = new ScrollArrowDrawable(mFamilyRecyclerView, Color.WHITE);
        mFamilyRecyclerView.setBackgroundDrawable(mScrollArrowDrawable);
        mReportDate = mHeaderView.findViewById(R.id.date);
        mReportName = mHeaderView.findViewById(R.id.report_name);
        mReportNums = mHeaderView.findViewById(R.id.copies);
        mReportLayout = mHeaderView.findViewById(R.id.report_layout);
        mReportNullLayout = mHeaderView.findViewById(R.id.report_null_layout);
        mAppointmentBtn = mHeaderView.findViewById(R.id.appointment_btn);
        /**设置成员RecyclerView*/
        familyInfoList = new ArrayList<>();
        mFamilyAdapter = new HomeFamilyAdapter(familyInfoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFamilyRecyclerView.setLayoutManager(layoutManager);
        mFamilyAdapter.setOnItemClickListener(this);
        mAppointmentBtn.setOnClickListener(this);
        mFamilyRecyclerView.setAdapter(mFamilyAdapter);
        /**设置footerView*/
        mFooterView = LayoutInflater.from(this).inflate(R.layout.report_centre_footer_view, null);
        moreBtn = mFooterView.findViewById(R.id.more_layout);
        moreBtn.setOnClickListener(this);
        RecyclerView recommendServiceListView = mFooterView.findViewById(R.id.recommend_service_list);
        recommendServiceListView.setLayoutManager(new LinearLayoutManager(this));
        mServiceAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recommendServiceListView.setAdapter(mServiceAdapter);
        mReportAdapter.addHeaderView(mHeaderView);
        mReportAdapter.addFooterView(mFooterView);
        mPresenter.initData();
        initOnClick();
    }

    private void initOnClick() {
        mServiceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ServiceCategoryInfo categoryInfo = (ServiceCategoryInfo) adapter.getData().get(position);
                ARouter.getInstance().build("/service/category/ServiceDetailActivity")
                        .withString("marketActivityId", categoryInfo.getMarketActivityId() + "")
                        .navigation();
            }
        });
    }


    @Override
    public void onRefresh() {
        mPresenter.onRefresh(familyId);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mScrollArrowDrawable.setSelectPosition(position);
        mReportName.setText(familyInfoList.get(position).getName() + "的报告");
        if (!familyInfoList.get(position).isChoose()) {
            //未选中
            familyInfoList.get(position).setChoose(true);
            for (int i = 0; i < familyInfoList.size(); i++) {
                if (i != position) {
                    familyInfoList.get(i).setChoose(false);
                }
            }
            familyId = familyInfoList.get(position).getFamilyId();
            adapter.notifyDataSetChanged();
            mPresenter.loadReports(familyInfoList.get(position).getFamilyId());
            mPresenter.getRadiographScreenMarketActivityList(familyId);
        }
    }

    @Override
    public void setHomeFamilys(List<HomeFamilyInfo> familyInfos) {
        familyInfoList.clear();
        HomeFamilyInfo mine = new HomeFamilyInfo();
        mine.setName(mMemberName);
        mine.setPicUrl(mHeadPic);
        mine.setChoose(true);
        mine.setFamilyId("");
        familyInfoList.add(mine);
        familyInfoList.addAll(familyInfos);
        mFamilyAdapter.notifyDataSetChanged();
        //初始化第一次为自己
        mReportName.setText(mMemberName + "的报告");
        mPresenter.loadReports("");
        mPresenter.getRadiographScreenMarketActivityList("");
    }

    @Override
    public void setReports(List<ReportInfo> reportInfos, int total) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (total > 0) {
            //有报告
            setReportLayout(true);
            mReportAdapter.setNewData(reportInfos);

//            if (total == reportInfos.size()) {
//                //已经完全加载
//                mReportAdapter.loadMoreEnd();
//                mReportAdapter.setEnableLoadMore(false);
//            } else {
//                mReportAdapter.setEnableLoadMore(true);
//                mReportAdapter.loadMoreComplete();
//            }
            mReportDate.setText("最新报告更新于：" + reportInfos.get(0).getCheckInDate());
            mReportNums.setText(total + "");
        } else {
            setReportLayout(false);
        }
        mReportAdapter.notifyDataSetChanged();
    }

    @Override
    public void setReportLayout(boolean isShow) {
        if (isShow) {
            //有报告
            mReportLayout.setVisibility(View.VISIBLE);
            mReportNullLayout.setVisibility(View.GONE);
        } else {
            //无报告
            mReportLayout.setVisibility(View.GONE);
            mReportNullLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setRecommendService(List<ServiceCategoryInfo> categoryInfos) {
        mServiceAdapter.setNewData(categoryInfos);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appointment_btn:
                ARouter.getInstance().build("/home/appointment/AppointmentServiceActivity").navigation();
                finish();
                break;
            case R.id.more_layout:
                //推荐服务更多
                EventBus.getDefault().postSticky(new RecommendServiceEvent(true));
                finish();
                break;
            case R.id.right_text:
                //申请报告
                ARouter.getInstance().build("/mine/report/ApplyReportActivity").navigation();
                break;
            default:
                break;
        }
    }

    @Override
    public void addItemOnClick() {
        mReportAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance()
                        .build("/mine/report/ReportDetailActivity")
                        .withString("mReportId", ((ReportInfo) adapter.getData().get(position)).getRadiographScreenId() + "")
                        .navigation();
                ((ReportInfo) adapter.getData().get(position)).setIsRead(1);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setGroupQrcodeImgUrl(String imgUrl) {
        Glide.with(this)
                .load(Api.HOST_IMG + imgUrl)
                .transition(withCrossFade())
                .into(mGroupImg);
    }

    private void showDialog(final String reprotImg) {
        new MaterialDialog.Builder(this)
                .title("提示\n您确定要发送此报告给医生么？")
                .content("如果你点击确定，医生将能够查看您的报告，并可针对报告中相关疑点为您解答。")
                .contentColor(Color.parseColor("#797E83"))
                .positiveText(R.string.sure)
                .negativeText(R.string.cancel)
                .positiveColor(Color.parseColor("#01C1B4"))
                .negativeColor(Color.parseColor("#3C4045"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        Intent data = new Intent();
                        data.putExtra("reprotImg", reprotImg);
                        setResult(RESULT_OK, data);
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
