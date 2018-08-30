package lht.wangtong.gowin120.patient.ui.science.category;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.CategoryCommentAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.ArticleDetailInfo;
import lht.wangtong.gowin120.patient.bean.CommentInfo;
import lht.wangtong.gowin120.patient.util.feature.ICallbackTab;
import lht.wangtong.gowin120.patient.view.CommentDialog;

/**
 * 科普评论
 *
 * @author luoyc
 */
@Route(path = "/science/category/CommentActivity")
public class CommentActivity extends BaseActivity<CommentPresenter> implements CommentContract.View
        , SwipeRefreshLayout.OnRefreshListener, CategoryCommentAdapter.RequestLoadMoreListener, CategoryCommentAdapter.OnItemChildClickListener {

    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.comment_list)
    RecyclerView mCommentRecyclerView;
    @BindView(R.id.input)
    TextView mInputBtn;
    @BindView(R.id.collection_btn)
    ImageView collectionBtn;
    @Autowired
    String mArticleId;
    @Autowired
    boolean isShowCollection = false;
    @Autowired
    ArticleDetailInfo mDetailInfo;
    @Inject
    CategoryCommentAdapter mAdapter;
    //是否收藏
    private boolean isCollection = false;
    //是否修改过
    private boolean isChange = false;
    private CommentDialog mCommentDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category_comment;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnLoadMoreListener(this, mCommentRecyclerView);
        mAdapter.setOnItemChildClickListener(this);
        mCommentRecyclerView.setAdapter(mAdapter);
        if (isCollection) {
            collectionBtn.setVisibility(View.GONE);
        }
        setArticle(mDetailInfo);
        mPresenter.initData(mArticleId);
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh(mArticleId);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.like_nums:
                mPresenter.supportComment(((CommentInfo) adapter.getData().get(position)).getInteractCommentId() + "");
                ((CommentInfo) adapter.getData().get(position)).setIsSupport("Y");
                ((CommentInfo) adapter.getData().get(position)).setSupportCount(((CommentInfo) adapter.getData().get(position)).getSupportCount() + 1);
                adapter.notifyItemChanged(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore(mArticleId);
    }

    @Override
    public void setArticle(ArticleDetailInfo detailInfo) {
        if (detailInfo.getFavorId() > 0) {
            isCollection = true;
            collectionBtn.setBackground(getResources().getDrawable(R.drawable.category_collection_img));
        } else {
            isCollection = false;
            collectionBtn.setBackground(getResources().getDrawable(R.drawable.category_uncollection_img));
        }
    }

    @Override
    public void setComments(List<CommentInfo> commentInfos, int total) {
        if (commentInfos.size() > 0) {
            mAdapter.setNewData(commentInfos);
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (total == commentInfos.size()) {
                //已经完全加载
                mAdapter.loadMoreEnd();
                mAdapter.setEnableLoadMore(false);
            } else {
                mAdapter.setEnableLoadMore(true);
                mAdapter.loadMoreComplete();
            }
        } else {

        }
    }

    @Override
    public void setCollection(int favorId) {
        isChange = true;
        mDetailInfo.setFavorId(favorId);
    }

    @Override
    public void showCommentDailog() {
        if (mCommentDialog == null) {
            mCommentDialog = new CommentDialog(this, new ICallbackTab() {
                @Override
                public void callBackTab(int i) {
                    if (i == 1) {
                        mPresenter.commentArticle(mArticleId, mCommentDialog.getContent());
                        mCommentDialog.setInput("");
                        mCommentDialog.dismiss();
                    }
                }
            });
        }
        mCommentDialog.show();
    }

    @OnClick({R.id.input, R.id.collection_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.input:
                showCommentDailog();
                break;
            case R.id.collection_btn:
                if (isCollection && mDetailInfo.getFavorId() > 0) {
                    //取消收藏
                    isCollection = false;
                    collectionBtn.setBackground(getResources().getDrawable(R.drawable.category_uncollection_img));
                    mPresenter.cancleCollection(mArticleId, mDetailInfo.getFavorId() + "");
                } else {
                    //收藏
                    isCollection = true;
                    mPresenter.collectionArticle(mArticleId);
                    collectionBtn.setBackground(getResources().getDrawable(R.drawable.category_collection_img));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isChange) {
            Intent data = new Intent();
            data.putExtra("mDetailInfo", mDetailInfo);
            setResult(RESULT_OK, data);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
