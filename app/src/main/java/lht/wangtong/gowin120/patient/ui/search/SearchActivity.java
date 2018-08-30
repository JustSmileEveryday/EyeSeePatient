package lht.wangtong.gowin120.patient.ui.search;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.HotSearchAdapter;
import lht.wangtong.gowin120.patient.adapter.SearchHistoryAdapter;
import lht.wangtong.gowin120.patient.adapter.SearchReportAdapter;
import lht.wangtong.gowin120.patient.adapter.SearchScienceAdapter;
import lht.wangtong.gowin120.patient.adapter.SearchServiceAdapter;
import lht.wangtong.gowin120.patient.adapter.SearchVideoAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.CommentLabelInfo;
import lht.wangtong.gowin120.patient.bean.CourseInfo;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.bean.ScienceCategoryInfo;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;
import lht.wangtong.gowin120.patient.db.HistoryInfo;

/**
 * 搜索界面
 *
 * @author luoyc
 */
@Route(path = "/ui/search/SearchActivity")
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContact.View
        , TextWatcher, TextView.OnEditorActionListener {

    @BindView(R.id.input)
    EditText mInput;
    @BindView(R.id.clean_btn)
    ImageView cleanBtn;
    @BindView(R.id.cancel_btn)
    TextView cancelBtn;
    @BindView(R.id.hot_search_list)
    RecyclerView hotSearchListView;
    @BindView(R.id.clean_history_btn)
    TextView cleanHistoryBtn;
    @BindView(R.id.history_search_list)
    RecyclerView historySearchListView;
    @BindView(R.id.report_list)
    RecyclerView reportListView;
    @BindView(R.id.service_list)
    RecyclerView serviceListView;
    @BindView(R.id.science_list)
    RecyclerView scienceListView;
    @BindView(R.id.video_list)
    RecyclerView videoListView;
    @BindView(R.id.search_null_layout)
    ConstraintLayout searchNullLayout;
    @BindView(R.id.search_report_layout)
    LinearLayout searchReportLayout;
    @BindView(R.id.search_service_layout)
    LinearLayout searchServiceLayout;
    @BindView(R.id.search_science_layout)
    LinearLayout searchScienceLayout;
    @BindView(R.id.search_video_layout)
    LinearLayout searchVideoLayout;
    View mHistorySearchLayout;
    View mContentSearchLayout;
    @Inject
    SearchServiceAdapter mServiceAdapter;
    @Inject
    SearchScienceAdapter mScienceAdapter;
    @Inject
    SearchVideoAdapter mVideoAdapter;
    @Inject
    HotSearchAdapter mHotSearchAdapter;
    @Inject
    SearchHistoryAdapter mHistoryAdapter;
    @Inject
    SearchReportAdapter mReportAdapter;
    @Autowired
    int mType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mHistorySearchLayout = findViewById(R.id.history_search_layout);
        mContentSearchLayout = findViewById(R.id.content_search_layout);
        //设置热搜适配器
        hotSearchListView.setLayoutManager(new GridLayoutManager(this, 3));
        hotSearchListView.setAdapter(mHotSearchAdapter);
        //设置历史搜索适配器
        historySearchListView.setLayoutManager(new LinearLayoutManager(this));
        mHistoryAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        historySearchListView.setAdapter(mHistoryAdapter);
        //设置报告适配器
        reportListView.setLayoutManager(new LinearLayoutManager(this));
        mReportAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        reportListView.setAdapter(mReportAdapter);
        //设置服务适配器
        serviceListView.setLayoutManager(new LinearLayoutManager(this));
        mServiceAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        serviceListView.setAdapter(mServiceAdapter);
        //设置科普适配器
        scienceListView.setLayoutManager(new LinearLayoutManager(this));
        mScienceAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        scienceListView.setAdapter(mScienceAdapter);
        //设置视频适配器
        videoListView.setLayoutManager(new LinearLayoutManager(this));
        mVideoAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        videoListView.setAdapter(mVideoAdapter);
        mInput.addTextChangedListener(this);
        mInput.setOnEditorActionListener(this);
        initItemClick();
        mPresenter.initData(mType);
    }

    private void initItemClick() {
        mHotSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mInput.setText(((CommentLabelInfo) adapter.getData().get(position)).getKeyword());
                setEditPosition(((CommentLabelInfo) adapter.getData().get(position)).getKeyword());
            }
        });
        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mInput.setText(((HistoryInfo) adapter.getData().get(position)).getHistory());
                setEditPosition(((HistoryInfo) adapter.getData().get(position)).getHistory());
            }
        });
        mHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.deleteHistory((HistoryInfo) adapter.getData().get(position));
                adapter.getData().remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        mReportAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance()
                        .build("/mine/report/ReportDetailActivity")
                        .withString("mReportId", ((ReportInfo) adapter.getData().get(position)).getRadiographScreenId() + "")
                        .navigation();
            }
        });
        mServiceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build("/service/category/ServiceDetailActivity")
                        .withString("marketActivityId", ((ServiceCategoryInfo) adapter.getData().get(position)).getMarketActivityId() + "")
                        .navigation();
            }
        });
        mScienceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance()
                        .build("/science/category/CategoryDetailActivity")
                        .withString("mArticleId", ((ScienceCategoryInfo) adapter.getData().get(position)).getArticleId() + "")
                        .navigation();
            }
        });
        mVideoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance()
                        .build("/home/classroom/CourseDetailActivity")
                        .withString("mArticleId", ((CourseInfo) adapter.getData().get(position)).getArticleId())
                        .navigation();
            }
        });
    }

    @Override
    public void search(String content) {
        mPresenter.search(content, mType);
        setState(true);
    }

    @Override
    public void setHotSearchs(List<CommentLabelInfo> hotSearchInfos) {
        mHotSearchAdapter.setNewData(hotSearchInfos);
    }

    @Override
    public void setHistoryInfo(List<HistoryInfo> historyInfos) {
        mHistoryAdapter.setNewData(historyInfos);
    }

    @Override
    public void setReports(List<ReportInfo> reportInfos) {
        mReportAdapter.setNewData(reportInfos);
        if (reportInfos.size() == 0){
            searchReportLayout.setVisibility(View.GONE);
        }else {
            searchReportLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setCatelogs(List<ScienceCategoryInfo> categoryInfoList) {
        mScienceAdapter.setNewData(categoryInfoList);
        if (categoryInfoList.size() == 0){
            searchScienceLayout.setVisibility(View.GONE);
        }else {
            searchScienceLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setServices(List<ServiceCategoryInfo> serviceCategoryInfos) {
        mServiceAdapter.setNewData(serviceCategoryInfos);
        if (serviceCategoryInfos.size() == 0){
            searchServiceLayout.setVisibility(View.GONE);
        }else {
            searchServiceLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setVideos(List<CourseInfo> courseInfos) {
        mVideoAdapter.setNewData(courseInfos);
        if (courseInfos.size() == 0){
            searchVideoLayout.setVisibility(View.GONE);
        }else {
            searchVideoLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setState(boolean isSearch) {
        if (isSearch) {
            mHistorySearchLayout.setVisibility(View.GONE);
            mContentSearchLayout.setVisibility(View.VISIBLE);
            searchNullLayout.setVisibility(View.GONE);
        } else {
            mHistorySearchLayout.setVisibility(View.VISIBLE);
            mContentSearchLayout.setVisibility(View.GONE);
            searchNullLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void setEditPosition(String content) {
        mInput.requestFocus();
        mInput.setSelection(content.length());
    }

    @Override
    public void setNullLayout(boolean isShow) {
        if (isShow) {
            searchNullLayout.setVisibility(View.VISIBLE);
            mContentSearchLayout.setVisibility(View.GONE);
        }else {
            searchNullLayout.setVisibility(View.GONE);
            mContentSearchLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.clean_btn, R.id.cancel_btn, R.id.clean_history_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clean_btn:
                mInput.setText("");
                break;
            case R.id.cancel_btn:
                finish();
                break;
            case R.id.clean_history_btn:
                mPresenter.deleteAllHistory();
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0) {
            cleanBtn.setVisibility(View.VISIBLE);
        } else {
            cleanBtn.setVisibility(View.GONE);
            setState(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        //判断是否是“搜索”键
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            //隐藏软键盘
            KeyboardUtils.hideSoftInput(this);
            search(textView.getText().toString().trim());
            // 对应逻辑操作
            return true;
        }
        return false;
    }
}
