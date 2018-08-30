package lht.wangtong.gowin120.patient.ui.science.category;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.vondear.rxtool.view.RxToast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.CategoryCommentAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.ArticleDetailInfo;
import lht.wangtong.gowin120.patient.bean.CommentInfo;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.util.MyWebViewClient;
import lht.wangtong.gowin120.patient.util.ShareUtils;
import lht.wangtong.gowin120.patient.util.feature.ICallbackTab;
import lht.wangtong.gowin120.patient.view.CommentDialog;
import lht.wangtong.gowin120.patient.view.ShareDialog;
import q.rorbin.badgeview.QBadgeView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 文章详情页
 *
 * @author luoyc
 * @date 2018/3/29
 */
@Route(path = "/science/category/CategoryDetailActivity")
public class CategoryDetailActivity extends BaseActivity<CategoryDetailPresenter> implements CategoryDetailContact.View
        , CategoryCommentAdapter.OnItemChildClickListener {
    private final int COMMENT = 101;
    @BindView(R.id.health_title)
    TextView mTitle;
    @BindView(R.id.digest)
    TextView mDigest;
    @BindView(R.id.create_date)
    TextView mDate;
    @BindView(R.id.article_img)
    ImageView mArticleImg;
    @BindView(R.id.article_content)
    WebView mContent;
    @BindView(R.id.qq_friend)
    TextView qqFriendBtn;
    @BindView(R.id.hot_comment_list)
    RecyclerView hotCommentList;
    @BindView(R.id.comment_btn)
    ImageView commentBtn;
    @BindView(R.id.collection_btn)
    ImageView collectionBtn;
    @Autowired
    String mArticleId;
    @Inject
    CategoryCommentAdapter mAdapter;
    private ArticleDetailInfo mDetailInfo;
    //是否收藏
    private boolean isCollection = false;
    private QBadgeView mQBadgeView;
    private CommentDialog mCommentDialog;
    private ShareDialog mShareDialog;
    private UMWeb mWeb;
    private String mCopyContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category_detail;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        hotCommentList.setLayoutManager(new LinearLayoutManager(this));
        hotCommentList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        initWebView();
        mPresenter.loadArticle(mArticleId);
    }

    private void initWebView() {
        WebSettings settings = mContent.getSettings();
        //支持获取手势焦点
        mContent.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
//        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        }
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(mContent.getContext().getCacheDir().getAbsolutePath());

        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setRenderPriority(WebSettings.RenderPriority.NORMAL);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置
        // 设置web视图客户端
        mContent.setWebViewClient(new MyWebViewClient());
        // mweb.loadUrl("javascript:test('aa')");
    }

    @Override
    public void setArticle(ArticleDetailInfo detailInfo) {
        mDetailInfo = detailInfo;
        mTitle.setText(detailInfo.getTitle());
        mDigest.setText(detailInfo.getSummer());
        mDate.setText(detailInfo.getCreatedDate());
        Glide.with(this).load(Api.HOST_IMG + detailInfo.getBigImage()).transition(withCrossFade()).into(mArticleImg);
        mContent.loadDataWithBaseURL(null, detailInfo.getContent(), "text/html", "utf-8", null);
        if (detailInfo.getFavorId() > 0) {
            isCollection = true;
            collectionBtn.setBackground(getResources().getDrawable(R.drawable.category_collection_img));
        } else {
            isCollection = false;
            collectionBtn.setBackground(getResources().getDrawable(R.drawable.category_uncollection_img));
        }
        //分享所需
        mCopyContent = "http://www.eyesee8.com/share.html?articleId=" + mArticleId;
        mWeb = new UMWeb("http://www.eyesee8.com/share.html?articleId=" + mArticleId);
        //标题
        mWeb.setTitle(detailInfo.getTitle());
        //网络图片
        UMImage image = new UMImage(this, Api.HOST_IMG + detailInfo.getBigImage());
        //缩略图
        mWeb.setThumb(image);
        //描述
        mWeb.setDescription(detailInfo.getSummer());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadComment(mArticleId);
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void setShareImg(String path) {
        if (mDetailInfo != null) {
            mDetailInfo.setShareImg(path);
        }
    }

    @Override
    public void setComments(List<CommentInfo> commentInfos, int total) {
        mAdapter.setNewData(commentInfos);
        if (total > 0) {
            addBadge(total);
        }
    }

    @Override
    public void addBadge(int num) {
        if (mQBadgeView == null) {
            mQBadgeView = new QBadgeView(this);
            mQBadgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
            mQBadgeView.setBadgePadding(5, true);
            mQBadgeView.bindTarget(commentBtn);
            mQBadgeView.setExactMode(true);
        }
        mQBadgeView.setBadgeNumber(num);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    public void setCollection(int favorId) {
        mDetailInfo.setFavorId(favorId);
    }

    @OnClick({R.id.share_btn, R.id.wx_friend, R.id.wx_friend_scene, R.id.qq_friend, R.id.qq_zone,R.id.sina_share_btn, R.id.copy_btn, R.id.more_comment_btn, R.id.input, R.id.comment_btn, R.id.collection_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_btn:
                if (mWeb != null) {
                    if (mShareDialog == null) {
                        mShareDialog = new ShareDialog(this, mWeb, mCopyContent,1);
                    }
                    mShareDialog.show();
                }
                break;
            case R.id.wx_friend:
                if (mWeb != null) {
                    ShareUtils.getInstance().share(CategoryDetailActivity.this, mWeb, SHARE_MEDIA.WEIXIN);
                }
                break;
            case R.id.wx_friend_scene:
                if (mWeb != null) {
                    ShareUtils.getInstance().share(CategoryDetailActivity.this, mWeb, SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                break;
            case R.id.qq_friend:
                if (mWeb != null) {
                    ShareUtils.getInstance().share(CategoryDetailActivity.this, mWeb, SHARE_MEDIA.QQ);
                }
                break;
            case R.id.qq_zone:
                if (mWeb != null) {
                    ShareUtils.getInstance().share(CategoryDetailActivity.this, mWeb, SHARE_MEDIA.QZONE);
                }
                break;
            case R.id.sina_share_btn:
                if (mWeb != null) {
                    ShareUtils.getInstance().share(CategoryDetailActivity.this, mWeb, SHARE_MEDIA.SINA);
                }
                break;
            case R.id.copy_btn:
                if (mWeb != null) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", mCopyContent);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    RxToast.success("已复制到剪贴板");
                }
                break;
            case R.id.more_comment_btn:
                ARouter.getInstance().build("/science/category/CommentActivity")
                        .withString("mArticleId", mArticleId)
                        .withParcelable("mDetailInfo", mDetailInfo)
                        .navigation(this, COMMENT);
                break;
            case R.id.input:
                showCommentDailog();
                break;
            case R.id.comment_btn:
                ARouter.getInstance().build("/science/category/CommentActivity")
                        .withString("mArticleId", mArticleId)
                        .withParcelable("mDetailInfo", mDetailInfo)
                        .navigation(this, COMMENT);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == COMMENT) {
            ArticleDetailInfo mDetailInfo = data.getParcelableExtra("mDetailInfo");
            if (mDetailInfo.getFavorId() > 0) {
                isCollection = true;
                collectionBtn.setBackground(getResources().getDrawable(R.drawable.category_collection_img));
            } else {
                isCollection = false;
                collectionBtn.setBackground(getResources().getDrawable(R.drawable.category_uncollection_img));
            }
        }
    }
}
