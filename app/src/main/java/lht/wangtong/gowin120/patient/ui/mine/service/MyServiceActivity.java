package lht.wangtong.gowin120.patient.ui.mine.service;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.MyServiceAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.MyServiceInfo;

/**
 * 我的服务
 *
 * @author luoyc
 */
@Route(path = "/mine/service/MyServiceActivity")
public class MyServiceActivity extends BaseActivity<MyServicePresenter> implements MyServiceContact.View, SwipeRefreshLayout.OnRefreshListener, MyServiceAdapter.RequestLoadMoreListener
        , MyServiceAdapter.OnItemClickListener {
    @BindView(R.id.service_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.service_list)
    RecyclerView mRecyclerView;
    @Inject
    MyServiceAdapter mServiceAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_service;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mServiceAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mServiceAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mServiceAdapter);
        mPresenter.initData();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MyServiceInfo info = (MyServiceInfo) adapter.getData().get(position);
        if (TextUtils.equals(info.getPayStatus(),"1")){
            //未支付
            ARouter.getInstance().build("/service/order/OrderActivity")
                    .withString("serviceRecordId", info.getServiceRecordId())
                    .navigation();
        }else {
            //订购成功
            ARouter.getInstance().build("/mine/service/MyServiceDetailActivity")
                    .withString("serviceRecordId", info.getServiceRecordId())
                    .navigation();
        }
    }

    @Override
    public void setServices(List<MyServiceInfo> myServiceInfos, int total) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mServiceAdapter.setNewData(myServiceInfos);
        if (total == myServiceInfos.size()) {
            //已经完全加载
            mServiceAdapter.loadMoreEnd();
            mServiceAdapter.setEnableLoadMore(false);
        } else {
            mServiceAdapter.setEnableLoadMore(true);
            mServiceAdapter.loadMoreComplete();
        }
        mServiceAdapter.notifyDataSetChanged();
    }
}
