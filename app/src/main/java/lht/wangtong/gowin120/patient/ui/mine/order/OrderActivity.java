package lht.wangtong.gowin120.patient.ui.mine.order;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.MineOrderAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;

/**
 * 我的订单
 *
 * @author luoyc
 */
public class OrderActivity extends BaseActivity<OrderPresenter> implements OrderContact.View
        , SwipeRefreshLayout.OnRefreshListener, MineOrderAdapter.RequestLoadMoreListener, MineOrderAdapter.OnItemChildClickListener {

    @BindView(R.id.order_list)
    RecyclerView orderListView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    MineOrderAdapter orderAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_order;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        orderListView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter.setOnItemChildClickListener(this);
        orderAdapter.setOnLoadMoreListener(this, orderListView);
        orderListView.setAdapter(orderAdapter);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
