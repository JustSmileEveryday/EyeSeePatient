package lht.wangtong.gowin120.patient.ui.home.classroom;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

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
import lht.wangtong.gowin120.patient.util.feature.ICallbackTab;
import lht.wangtong.gowin120.patient.view.CommentDialog;
import lht.wangtong.gowin120.patient.view.ShareDialog;
import q.rorbin.badgeview.QBadgeView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 课堂详情
 *
 * @author luoyc
 */

@Route(path = "/home/classroom/CourseDetailActivity")
public class CourseDetailActivity extends BaseActivity<CourseDetailPresenter> implements CourseDetailContact.View,
        CategoryCommentAdapter.OnItemChildClickListener {

    private final int COMMENT = 101;
    @BindView(R.id.hot_comment_list)
    RecyclerView hotCommentList;
    StandardGSYVideoPlayer detailPlayer;
    @BindView(R.id.course_title)
    TextView mTitle;
    @BindView(R.id.play_times)
    TextView playTimes;
    @BindView(R.id.comments)
    TextView comments;
    @BindView(R.id.create_date)
    TextView createDate;
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

    private boolean isPlay;
    private boolean isPause;

    private OrientationUtils orientationUtils;
    private ShareDialog mShareDialog;
    private UMWeb mWeb;
    private String mCopyContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        detailPlayer = findViewById(R.id.detail_player);
        hotCommentList.setLayoutManager(new LinearLayoutManager(this));
        hotCommentList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        initPlayer();
        mPresenter.loadArticle(mArticleId);
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
    public void initPlayer() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(CourseDetailActivity.this, true, true);
            }
        });
    }

    @Override
    public void setCourseInfo(ArticleDetailInfo detailInfo) {
        mDetailInfo = detailInfo;
        mCopyContent = "http://www.eyesee8.com/share-video.html?articleId=" + detailInfo.getArticleId();
        mWeb = new UMWeb("http://www.eyesee8.com/share-video.html?articleId=" + detailInfo.getArticleId());
        mWeb.setTitle(detailInfo.getTitle());
        //网络图片
        UMImage image = new UMImage(this, Api.HOST_IMG + detailInfo.getBigImage());
        //缩略图
        mWeb.setThumb(image);
        //描述
        mWeb.setDescription(detailInfo.getSummer());
        createDate.setText(detailInfo.getCreatedDate());
        playTimes.setText(detailInfo.getClickCount() + "");
        comments.setText(detailInfo.getCommentCount() + "");
        mTitle.setText(detailInfo.getTitle());
        if (detailInfo.getFavorId() > 0) {
            isCollection = true;
            collectionBtn.setBackground(getResources().getDrawable(R.drawable.category_collection_img));
        } else {
            isCollection = false;
            collectionBtn.setBackground(getResources().getDrawable(R.drawable.category_uncollection_img));
        }
        if (TextUtils.isEmpty(detailInfo.getClickUrl())) {
            return;
        }
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this)
                .load(Api.HOST_IMG + detailInfo.getBigImage())
                .transition(withCrossFade())
                .into(imageView);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setVideoTitle(detailInfo.getTitle())
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(detailInfo.getClickUrl())
                .setCacheWithPlay(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                }).setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        }).build(detailPlayer);
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        detailPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
        detailPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
        mPresenter.loadComment(mArticleId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            detailPlayer.getCurrentPlayer().release();
        }
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
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
    public void setCollection(int favorId) {
        mDetailInfo.setFavorId(favorId);
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

    @OnClick({R.id.more_comment_btn, R.id.input, R.id.comment_btn, R.id.collection_btn,R.id.share_btn})
    public void onClick(View view) {
        switch (view.getId()) {
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
                        .withBoolean("isShowCollection", true)
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
            case R.id.share_btn:
                //分享
                if (mWeb != null) {
                    if (mShareDialog == null) {
                        mShareDialog = new ShareDialog(this, mWeb, mCopyContent, 1);
                    }
                    mShareDialog.show();
                }
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
