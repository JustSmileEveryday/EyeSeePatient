package lht.wangtong.gowin120.patient.ui.mine.balance;

import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.BalanceAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.BalanceInfo;

/**
 * 余额明细
 *
 * @author luoyc
 */
@Route(path = "/mine/balance/BalanceActivity")
public class BalanceActivity extends BaseActivity<BalancePresenter> implements BalanceContact.View, SwipeRefreshLayout.OnRefreshListener
        , BalanceAdapter.RequestLoadMoreListener, BalanceAdapter.OnItemClickListener {

    @BindView(R.id.balance_rv)
    RecyclerView mbalanceRv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.no_data_layout)
    ConstraintLayout mNoDataLayout;
    @Inject
    BalanceAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_balance;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mbalanceRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setOnLoadMoreListener(this, mbalanceRv);
        mAdapter.setOnItemClickListener(this);
        mbalanceRv.setAdapter(mAdapter);
        mPresenter.initData();
    }

    @Override
    public void setBalance(List<BalanceInfo> balanceInfos, int total) {
        if (balanceInfos.size() > 0) {
            setState(true);
            mAdapter.setNewData(balanceInfos);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (total == balanceInfos.size()) {
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
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mNoDataLayout.setVisibility(View.GONE);
        } else {
            mSwipeRefreshLayout.setVisibility(View.GONE);
            mNoDataLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.onLoadMore();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance()
                .build("/mine/balance/BalanceDetailActivity")
                .withString("mMemberAccountIoId", ((BalanceInfo) adapter.getData().get(position)).getMemberAccountIoId())
                .navigation();
    }
}
