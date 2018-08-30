package lht.wangtong.gowin120.patient.ui.consult;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.AutoConsultAdapter;
import lht.wangtong.gowin120.patient.adapter.SupposeAdapter;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.BannerInfo;
import lht.wangtong.gowin120.patient.bean.ConsultEmployeeInfo;
import lht.wangtong.gowin120.patient.bean.IllnessQuestionInfoId;
import lht.wangtong.gowin120.patient.config.Constant;
import lht.wangtong.gowin120.patient.view.CustomLoadMoreView;

/**
 * 新的咨询
 *
 * @author luoyc
 */
public class NewConsultFragment extends BaseFragment<NewConsultPresenter> implements NewConsultContact.View, SupposeAdapter.OnItemClickListener
        , SwipeRefreshLayout.OnRefreshListener, SupposeAdapter.RequestLoadMoreListener {
    RecyclerView mAdviceRecyclerView;
    @BindView(R.id.article_list)
    RecyclerView mArticleRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    SupposeAdapter mSupposeAdapter;
    @Inject
    AutoConsultAdapter mConsultAdapter;
    private int mGuideViewH;
    private int mGuideViewL;
    private int mGuideViewT;

    public static NewConsultFragment newInstance() {
        return new NewConsultFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_consult;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mArticleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mArticleRecyclerView.setAdapter(mSupposeAdapter);
        View headerView = getLayoutInflater().inflate(R.layout.new_consult_fragment_header_layout, null);
        mAdviceRecyclerView = headerView.findViewById(R.id.advice_list);
        mSupposeAdapter.addHeaderView(headerView);
        mSupposeAdapter.setOnLoadMoreListener(this, mArticleRecyclerView);
        mSupposeAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdviceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAdviceRecyclerView.setAdapter(mConsultAdapter);
        initItemClick();
        mPresenter.initData();
    }


    private void initItemClick() {
        mSupposeAdapter.setOnItemClickListener(this);
        mConsultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (((BannerInfo) adapter.getData().get(position)).getDispOrder()) {
                    case 1:
                        // 机器人聊天
                        ARouter.getInstance().build("/consult/chat/AutoChatActivity").navigation();
                        break;
                    case 2:
                        //运营人员
                        mPresenter.getConsultEmployeeInfo("1");
                        break;
                    case 3:
                        //医生
                        mPresenter.getConsultEmployeeInfo("2");
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance()
                .build("/consult/supposeask/SupposeDetailActivity")
                .withString("illnessQuestionId", ((IllnessQuestionInfoId) adapter.getData().get(position)).getIllnessQuestionId())
                .navigation();
    }

    @Override
    public void setSupposeList(List<IllnessQuestionInfoId> supposeList, int total) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mSupposeAdapter.setNewData(supposeList);
        if (total == supposeList.size()) {
            //已经完全加载
            mSupposeAdapter.loadMoreEnd();
            mSupposeAdapter.setEnableLoadMore(false);
        } else {
            mSupposeAdapter.setEnableLoadMore(true);
            mSupposeAdapter.loadMoreComplete();
        }
    }

    @Override
    public void setAutoConsult(List<BannerInfo> bannerInfos) {
        mConsultAdapter.setNewData(bannerInfos);
//        if (bannerInfos != null && bannerInfos.size() > 3) {
//            mAdviceRecyclerView.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (mArticleRecyclerView.getChildAt(1) != null) {
//                        showGuide();
//                    }
//                }
//            });
//        }
    }

    @Override
    public void skipChatActivity(String consultEmployeeType, ConsultEmployeeInfo employeeInfo) {
        if (TextUtils.equals(consultEmployeeType, "1")) {
            //运营人员
            ARouter.getInstance().build("/tencent/ui/ChatActivity")
                    .withParcelable("mEmployeeInfo", employeeInfo)
                    .withInt("mChatType", 101)
                    .navigation();
        } else {
            //医生
            ARouter.getInstance().build("/tencent/ui/ChatActivity")
                    .withParcelable("mEmployeeInfo", employeeInfo)
                    .withInt("mChatType", 102)
                    .navigation();
        }

    }

    @Override
    public void showGuide() {
        NewbieGuide.with(getActivity())
                .setLabel("consult_guide_1"+ Constant.APPVERSION)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(mAdviceRecyclerView.getChildAt(2), HighLight.Shape.CIRCLE, 20, 0)
                        .setLayoutRes(R.layout.consult_guide_layout_1, R.id.guide_next_btn)
                        .setEverywhereCancelable(false)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                setGuideParms(view, 1);
                            }
                        }))
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(mArticleRecyclerView.getChildAt(1), HighLight.Shape.RECTANGLE)
                        .setLayoutRes(R.layout.consult_guide_layout_2, R.id.kown_btn)
                        .setEverywhereCancelable(false)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                setGuideParms(view, 2);
                            }
                        }))
                .show();
    }

    private void setGuideParms(View view, int type) {
        if (type == 1) {
            final ImageView mGuideLayout = view.findViewById(R.id.consult_guide_word_1);
            // 向 ViewTreeObserver 注册方法，以获取控件尺寸
            ViewTreeObserver mTopViewViewTreeObserver = mAdviceRecyclerView.getChildAt(2).getViewTreeObserver();
            mTopViewViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mGuideViewH = mAdviceRecyclerView.getChildAt(2).getBottom() + (mAdviceRecyclerView.getChildAt(2).getBottom() - mAdviceRecyclerView.getChildAt(2).getTop());
                    mGuideViewL = mAdviceRecyclerView.getChildAt(2).getLeft() + (mAdviceRecyclerView.getChildAt(2).getRight() - mAdviceRecyclerView.getChildAt(2).getLeft()) / 2;
                    // 成功调用一次后，移除 Hook 方法，防止被反复调用
                    // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                    // 使用新方法 removeOnGlobalLayoutListener() 代替
                    mAdviceRecyclerView.getChildAt(2).getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
            ViewTreeObserver mGuideLayoutViewTreeObserver = mGuideLayout.getViewTreeObserver();
            mGuideLayoutViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    LinearLayout.LayoutParams mLayoutParams = (LinearLayout.LayoutParams) mGuideLayout.getLayoutParams();
                    mLayoutParams.topMargin = mGuideViewH + 60;
                    mLayoutParams.leftMargin = mGuideViewL - 30;
                    mGuideLayout.setLayoutParams(mLayoutParams);
                    // 成功调用一次后，移除 Hook 方法，防止被反复调用
                    // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                    // 使用新方法 removeOnGlobalLayoutListener() 代替
                    mGuideLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        } else {
            final LinearLayout mContentLayout = view.findViewById(R.id.content_layout);
            ViewTreeObserver mTopViewViewTreeObserver = mArticleRecyclerView.getChildAt(1).getViewTreeObserver();
            mTopViewViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mGuideViewT = mArticleRecyclerView.getChildAt(1).getTop();
                    // 成功调用一次后，移除 Hook 方法，防止被反复调用
                    // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                    // 使用新方法 removeOnGlobalLayoutListener() 代替
                    mArticleRecyclerView.getChildAt(1).getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
            ViewTreeObserver mContentLayoutViewTreeObserver = mContentLayout.getViewTreeObserver();
            mContentLayoutViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    RelativeLayout.LayoutParams mLayoutParams = (RelativeLayout.LayoutParams) mContentLayout.getLayoutParams();
                    mLayoutParams.bottomMargin = ScreenUtils.getScreenHeight() - mGuideViewT - 40;
                    mContentLayout.setLayoutParams(mLayoutParams);
                    // 成功调用一次后，移除 Hook 方法，防止被反复调用
                    // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                    // 使用新方法 removeOnGlobalLayoutListener() 代替
                    mContentLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }
}
