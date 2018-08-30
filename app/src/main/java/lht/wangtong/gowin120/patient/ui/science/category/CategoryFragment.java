package lht.wangtong.gowin120.patient.ui.science.category;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.ScienceCategoryAdapter;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;

/**
 * 科普 - 类目fragment
 *
 * @author luoyc
 * @date 2018/3/6
 */

public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryContract.View, SwipeRefreshLayout.OnRefreshListener, ScienceCategoryAdapter.RequestLoadMoreListener,
        ScienceCategoryAdapter.OnItemClickListener {

    @BindView(R.id.category_swipelayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.category_recylerview)
    RecyclerView mRecyclerView;
    @Inject
    ScienceCategoryAdapter mAdapter;
    private String catalogId = "";

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.initData();
        mPresenter.loadScienceArticle(catalogId);
    }

    @Override
    public void setScienceArticle(List<ScienceCategoryInfo> categoryInfos, int total) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.setNewData(categoryInfos);
        if (total == categoryInfos.size()) {
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
        mPresenter.refresh(catalogId);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance()
                .build("/science/category/CategoryDetailActivity")
                .withString("mArticleId", ((ScienceCategoryInfo) adapter.getData().get(position)).getArticleId() + "")
                .navigation();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore(catalogId);
    }
}
