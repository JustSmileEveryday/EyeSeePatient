package lht.wangtong.gowin120.patient.ui.mine.integration;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.MyIntegralAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.IntegrationInfo;

/**
 * 我的积分
 *
 * @author luoyc
 */
@Route(path = "/mine/integration/MyIntegrationActivity")
public class MyIntegrationActivity extends BaseActivity<MyIntegrationPresenter> implements MyIntegrationContract.View, SwipeRefreshLayout.OnRefreshListener
        , MyIntegralAdapter.RequestLoadMoreListener {

    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.integration_list)
    RecyclerView mIntegrationList;
    @Inject
    MyIntegralAdapter mAdapter;
    private TextView mIntegrationNum;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_integration;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_my_integration_layout, null);
        mIntegrationNum = headerView.findViewById(R.id.my_integration_num);
        mIntegrationList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.addHeaderView(headerView);
        mAdapter.setOnLoadMoreListener(this, mIntegrationList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mIntegrationList.setAdapter(mAdapter);
        mPresenter.initData();
    }

    @Override
    public void setIntegrationNum(String num) {
        mIntegrationNum.setText(num);
    }

    @Override
    public void setIntegrationList(List<IntegrationInfo> intergrationList, int total) {
        mAdapter.setNewData(intergrationList);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (total == intergrationList.size()) {
            //已经完全加载
            mAdapter.loadMoreEnd();
            mAdapter.setEnableLoadMore(false);
        } else {
            mAdapter.setEnableLoadMore(true);
            mAdapter.loadMoreComplete();
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
}
