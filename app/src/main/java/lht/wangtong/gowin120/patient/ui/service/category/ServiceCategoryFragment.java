package lht.wangtong.gowin120.patient.ui.service.category;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.ServiceCategoryAdapter;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;

/**
 * 服务列表
 *
 * @author Administrator
 * @date 2018/4/2
 */

public class ServiceCategoryFragment extends BaseFragment<ServiceCategoryPresenter> implements ServiceCategoryContact.View, SwipeRefreshLayout.OnRefreshListener
        , ServiceCategoryAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.category_swipelayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.category_recylerview)
    RecyclerView mRecyclerView;
    @Inject
    ServiceCategoryAdapter mAdapter;
    private String code = "";

    public static ServiceCategoryFragment newInstance() {
        return new ServiceCategoryFragment();
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_category;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.initData(code);
    }


    @Override
    public void setServiceList(List<ServiceCategoryInfo> serviceList, int total) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.setNewData(serviceList);
        if (total == serviceList.size()) {
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
        mPresenter.onRresh(code);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore(code);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ServiceCategoryInfo categoryInfo = (ServiceCategoryInfo) adapter.getData().get(position);
        ARouter.getInstance().build("/service/category/ServiceDetailActivity")
                .withString("marketActivityId", categoryInfo.getMarketActivityId() + "")
                .navigation();
    }
}
