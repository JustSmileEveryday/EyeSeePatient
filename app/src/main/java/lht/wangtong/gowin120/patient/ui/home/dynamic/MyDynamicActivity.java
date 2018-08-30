package lht.wangtong.gowin120.patient.ui.home.dynamic;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.MessageAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.MessageInfo;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.bean.event.ConsultEvent;
import lht.wangtong.gowin120.patient.view.CustomLoadMoreView;

/**
 * 我的动态
 *
 * @author luoyc
 * @date 2018/3/27
 */
@Route(path = "/home/dynamic/MyDynamicActivity")
public class MyDynamicActivity extends BaseActivity<MyDynamicPresenter> implements MyDynamicContact.View, SwipeRefreshLayout.OnRefreshListener, MessageAdapter.RequestLoadMoreListener, MessageAdapter.OnItemChildClickListener {
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Inject
    MessageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_dynamic;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.initData();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (((MessageInfo) adapter.getData().get(position)).getBusiType()) {
            case 6:
                //筛查报告提醒
                mPresenter.loadReportDetail(((MessageInfo) adapter.getData().get(position)).getBusiId());
                break;
            case 7:
                //预约服务提醒
                ARouter.getInstance()
                        .build("/mine/appointment/AppointmentDetailActivity")
                        .withString("mReservationServiceId", ((MessageInfo) adapter.getData().get(position)).getBusiId())
                        .navigation();
                break;
            case 8:
                //咨询服务通知
                EventBus.getDefault().post(new ConsultEvent(true));
                finish();
                break;
            case 9:
                //服务消息提醒
                ARouter.getInstance().build("/mine/service/MyServiceDetailActivity")
                        .withString("serviceRecordId", ((MessageInfo) adapter.getData().get(position)).getBusiId())
                        .navigation();
                break;
            default:
                break;
        }
    }

    @Override
    public void setMessage(List<MessageInfo> messageInfos, int total) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.setNewData(messageInfos);
        if (total == messageInfos.size()) {
            //已经完全加载
            mAdapter.loadMoreEnd();
            mAdapter.setEnableLoadMore(false);
        } else {
            mAdapter.setEnableLoadMore(true);
            mAdapter.loadMoreComplete();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void skipReportDetail(ReportInfo reportInfo) {
        ARouter.getInstance()
                .build("/mine/report/ReportDetailActivity")
                .withString("mComprehensiveReportPdfImgUrl", reportInfo.getComprehensiveReportPdfImgUrl())
                .withString("mPdfImgUrl", reportInfo.getPdfImgUrl())
                .navigation();
    }
}
