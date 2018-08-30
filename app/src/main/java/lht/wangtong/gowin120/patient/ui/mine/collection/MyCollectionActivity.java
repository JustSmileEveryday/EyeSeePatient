package lht.wangtong.gowin120.patient.ui.mine.collection;

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
import lht.wangtong.gowin120.patient.adapter.ScienceCategoryAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;
import lht.wangtong.gowin120.patient.view.CustomLoadMoreView;

/**
 * @author Luoyc
 * @date 2018/3/14
 */
@Route(path = "/mine/collection/MyCollectionActivity")
public class MyCollectionActivity extends BaseActivity<MyCollectionPresenter> implements MyCollectionContact.View, SwipeRefreshLayout.OnRefreshListener,
        ScienceCategoryAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.collection_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.collection_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.collection_null_layout)
    ConstraintLayout mConstraintLayout;
    @Inject
    ScienceCategoryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance()
                .build("/science/category/CategoryDetailActivity")
                .withString("mArticleId", ((ScienceCategoryInfo) adapter.getData().get(position)).getArticleId() + "")
                .navigation();
    }

    @Override
    public void setCollectionLits(List<ScienceCategoryInfo> infos, int total) {
        if (total > 0) {
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
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mConstraintLayout.setVisibility(View.GONE);
        } else {
            mSwipeRefreshLayout.setVisibility(View.GONE);
            mConstraintLayout.setVisibility(View.VISIBLE);
        }
    }
}
