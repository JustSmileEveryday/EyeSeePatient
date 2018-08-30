package lht.wangtong.gowin120.patient.ui.mine.appointment;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.AppointmentAdapter;
import lht.wangtong.gowin120.patient.adapter.HomeFamilyAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.AppointmentInfo;
import lht.wangtong.gowin120.patient.bean.HomeFamilyInfo;
import lht.wangtong.gowin120.patient.util.LoginUtil;
import lht.wangtong.gowin120.patient.view.CustomLoadMoreView;
import lht.wangtong.gowin120.patient.view.ScrollArrowDrawable;

/**
 * 我的预约
 *
 * @author luoyc
 * @date 2018/3/14
 */
@Route(path = "/mine/appointment/AppointmentActivity")
public class AppointmentActivity extends BaseActivity<AppointmentPresenter> implements AppointmentContact.View, SwipeRefreshLayout.OnRefreshListener,
        AppointmentAdapter.RequestLoadMoreListener, AppointmentAdapter.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.appointment_SwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.appointment_recyclerView)
    RecyclerView mRecyclerView;
    @Inject
    AppointmentAdapter mAdapter;
    private HomeFamilyAdapter mFamilyAdapter;
    private RecyclerView mFamilyRecyclerView;
    private List<HomeFamilyInfo> familyInfoList;
    private ConstraintLayout mReportNullLayout;
    private String familyId = "";
    private ScrollArrowDrawable mScrollArrowDrawable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
        /**设置HeadView*/
        View mHeaderView = LayoutInflater.from(this).inflate(R.layout.my_appointment_header_view, null);
        mReportNullLayout = mHeaderView.findViewById(R.id.appointment_null_layout);
        TextView mAppointmentBtn = mHeaderView.findViewById(R.id.appointment_btn);
        mFamilyRecyclerView = mHeaderView.findViewById(R.id.family_RecyclerView);
        mScrollArrowDrawable = new ScrollArrowDrawable(mFamilyRecyclerView, Color.WHITE);
        mFamilyRecyclerView.setBackgroundDrawable(mScrollArrowDrawable);
        /**设置成员RecyclerView*/
        familyInfoList = new ArrayList<>();
        mFamilyAdapter = new HomeFamilyAdapter(familyInfoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFamilyRecyclerView.setLayoutManager(layoutManager);
        mFamilyRecyclerView.setAdapter(mFamilyAdapter);
        mAdapter.addHeaderView(mHeaderView);
        mAppointmentBtn.setOnClickListener(this);
        initItemClick();
        mPresenter.initData();
    }

    private void initItemClick() {
        mFamilyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mScrollArrowDrawable.setSelectPosition(position);
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
                    mPresenter.loadAppointmentLits(familyInfoList.get(position).getFamilyId(), true);
                }
            }
        });
    }

    @Override
    public void setHomeFamilys(List<HomeFamilyInfo> familyInfos) {
        familyInfoList.clear();
        HomeFamilyInfo mine = new HomeFamilyInfo();
        mine.setName(LoginUtil.user.getMemberName());
        mine.setPicUrl(LoginUtil.user.getPicUrl());
        mine.setChoose(true);
        mine.setFamilyId("");
        familyInfoList.add(mine);
        familyInfoList.addAll(familyInfos);
        mFamilyAdapter.notifyDataSetChanged();
        mPresenter.loadAppointmentLits("", false);
    }

    @Override
    public void setAppointmentLits(List<AppointmentInfo> infos, int total) {
        if (infos.size() > 0) {
            setState(true);
            mAdapter.setNewData(infos);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (total == infos.size()) {
                //已经完全加载
                mAdapter.loadMoreEnd();
                mAdapter.setEnableLoadMore(false);
            } else {
                mAdapter.setEnableLoadMore(true);
                mAdapter.loadMoreComplete();
            }
        } else {
            setState(false);
        }
    }

    @Override
    public void setState(boolean isShow) {
        if (isShow) {
            //有预约
            mReportNullLayout.setVisibility(View.GONE);
        } else {
            //无预约
            mReportNullLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh(familyId);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore(familyId);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance()
                .build("/mine/appointment/AppointmentDetailActivity")
                .withString("mReservationServiceId", ((AppointmentInfo) adapter.getData().get(position)).getReservationServiceId())
                .navigation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appointment_btn:
                ARouter.getInstance().build("/home/appointment/AppointmentServiceActivity").navigation();
                finish();
                break;
            default:
                break;
        }
    }
}
