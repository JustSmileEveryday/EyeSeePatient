package lht.wangtong.gowin120.patient.ui.mine.report;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.adapter.ReportDetailAdapter;
import lht.wangtong.gowin120.patient.adapter.ServiceCategoryAdapter;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.ReportInfo;
import lht.wangtong.gowin120.patient.bean.ServiceCategoryInfo;
import lht.wangtong.gowin120.patient.bean.event.RecommendServiceEvent;
import lht.wangtong.gowin120.patient.tencent.ui.ImageViewActivity;
import lht.wangtong.gowin120.patient.view.ReportDetailItemTwoView;
import lht.wangtong.gowin120.patient.view.ReportDetailItemView;
import lht.wangtong.gowin120.patient.view.ReportPositiveItemView;
import lht.wangtong.gowin120.patient.view.ShareDialog;

/**
 * @author Luoyc
 * @date 2018/3/14
 */
@Route(path = "/mine/report/ReportDetailActivity")
public class ReportDetailActivity extends BaseActivity<ReportDetailPresenter> implements ReportDetailContact.View
        , ReportDetailAdapter.OnItemClickListener {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.report_time)
    TextView reportTime;
    @BindView(R.id.report_open_img)
    ImageView reportOpenImg;
    @BindView(R.id.positive_result)
    TextView positiveResult;
    @BindView(R.id.doctor_report)
    TextView doctorReport;
    @BindView(R.id.protect_eyes_advice)
    TextView protectEyesAdvice;
    @BindView(R.id.total_check_advice_layout)
    LinearLayout totalCheckAdviceLayout;
    @BindView(R.id.report_important_open_img)
    ImageView reportImportantOpenImg;
    @BindView(R.id.report_important_num)
    TextView reportImportantNum;
    @BindView(R.id.report_important_layout)
    LinearLayout reportImportantLayout;
    @BindView(R.id.report_detail_open_btn)
    ImageView reportDetailOpenBtn;
    @BindView(R.id.report_detail_list)
    RecyclerView reportDetailListView;
    @BindView(R.id.more_layout)
    ConstraintLayout moreLayout;
    @BindView(R.id.recommend_service_list)
    RecyclerView recommendServiceListView;
    @BindView(R.id.report_detail_open)
    TextView mReportDetailOpen;
    @BindView(R.id.report_info_layout_1)
    LinearLayout reportInfoLayout1;
    @BindView(R.id.report_info_layout_2)
    LinearLayout reportInfoLayout2;
    @BindView(R.id.positive_result_layout)
    LinearLayout positiveResultLayout;
    @BindView(R.id.doctor_report_layout)
    LinearLayout doctorReportLayout;
    @BindView(R.id.protect_eyes_advice_layout)
    LinearLayout protectEyesAdviceLayout;
    @BindView(R.id.positive_result_array_layout)
    LinearLayout positiveResultArrayLayout;
    @Inject
    ReportDetailAdapter reportAdapter;
    @Inject
    ServiceCategoryAdapter mServiceAdapter;
    @Autowired
    String mReportId;
    private boolean mTotalIsOpen = false, mImportantIsOpen = false, mDetailIsOpen = true;
    private ReportInfo mReportInfo;
    private ShareDialog mShareDialog;
    private UMWeb mWeb;
    private String mCopyContent;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_detail;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        reportDetailListView.setLayoutManager(new LinearLayoutManager(this));
        reportAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        reportAdapter.setOnItemClickListener(this);
        reportDetailListView.setAdapter(reportAdapter);
        recommendServiceListView.setLayoutManager(new LinearLayoutManager(this));
        mServiceAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        recommendServiceListView.setAdapter(mServiceAdapter);
        mPresenter.getReportDetailInfo(mReportId);
    }


    @OnClick({R.id.report_open_img, R.id.report_important_open_img, R.id.report_detail_open_layout, R.id.more_layout, R.id.report_share_btn, R.id.share_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.report_open_img:
                //总检建议
                if (mTotalIsOpen) {
                    mTotalIsOpen = false;
                    totalCheckAdviceLayout.setVisibility(View.GONE);
                    reportOpenImg.setImageResource(R.drawable.report_detail_open_img);
                } else {
                    mTotalIsOpen = true;
                    totalCheckAdviceLayout.setVisibility(View.VISIBLE);
                    reportOpenImg.setImageResource(R.drawable.report_detail_close_img);
                }
                break;
            case R.id.report_important_open_img:
                //重点关注项
                if (mImportantIsOpen) {
                    mImportantIsOpen = false;
                    reportImportantLayout.setVisibility(View.GONE);
                    reportImportantOpenImg.setImageResource(R.drawable.report_detail_open_img);
                } else {
                    mImportantIsOpen = true;
                    reportImportantLayout.setVisibility(View.VISIBLE);
                    reportImportantOpenImg.setImageResource(R.drawable.report_detail_close_img);
                }
                break;
            case R.id.report_detail_open_layout:
                //详细报告
                if (mDetailIsOpen) {
                    mDetailIsOpen = false;
                    mReportDetailOpen.setText("详细报告展开");
                    reportDetailListView.setVisibility(View.GONE);
                    reportDetailOpenBtn.setImageResource(R.drawable.report_detail_open_btn_img);
                } else {
                    mReportDetailOpen.setText("详细报告收起");
                    mDetailIsOpen = true;
                    reportDetailListView.setVisibility(View.VISIBLE);
                    reportDetailOpenBtn.setImageResource(R.drawable.report_detail_close_btn_img);
                }
                break;
            case R.id.more_layout:
                //推荐服务更多
                ActivityUtils.finishActivity(ReportCenterActivity.class);
                EventBus.getDefault().postSticky(new RecommendServiceEvent(true));
                finish();
                break;
            case R.id.report_share_btn:
                //分享
                Intent data = new Intent(this, ReportShareActivity.class);
                data.putExtra("imgUrl", mReportInfo.getEyeseeSceneImgUrl());
                startActivity(data);
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
    public void setReportDetailInfo(ReportInfo reportDetailInfo) {
        if (reportDetailInfo == null) {
            return;
        }
        mReportInfo = reportDetailInfo;
        mCopyContent = "http://www.eyesee8.com/share-presentation.html?radiographScreenId=" + reportDetailInfo.getRadiographScreenId();
        mWeb = new UMWeb("http://www.eyesee8.com/share-presentation.html?radiographScreenId=" + reportDetailInfo.getRadiographScreenId());
        mWeb.setTitle("嗨！这是" + reportDetailInfo.getMemberName() + "的眼睛检查报告");
        mWeb.setDescription("看看他/她的眼睛亚健康了吗");
//        UMImage image = new UMImage(this, Api.HOST_IMG + reportDetailInfo.getBigImage());
//        //缩略图
//        mWeb.setThumb(image);
//        //描述
//        mWeb.setDescription(detailInfo.getSummer());
        name.setText(reportDetailInfo.getMemberName());
        reportTime.setText(reportDetailInfo.getCheckInDate());
        if (reportDetailInfo.getLeftAndRightCheck() != null) {
            for (int i = 0; i < reportDetailInfo.getLeftAndRightCheck().size(); i++) {
                setLeftAndRightCheck(reportDetailInfo.getLeftAndRightCheck().get(i));
            }
        }
        if (reportDetailInfo.getNoLeftAndRightCheck() != null) {
            for (int i = 0; i < reportDetailInfo.getNoLeftAndRightCheck().size(); i++) {
                setNoLeftAndRightCheck(reportDetailInfo.getNoLeftAndRightCheck().get(i));
            }
        }
//        if (reportDetailInfo.getPositiveResultText() != null && !TextUtils.isEmpty(reportDetailInfo.getPositiveResultText())) {
//            positiveResult.setText(reportDetailInfo.getPositiveResultText());
//            positiveResultLayout.setVisibility(View.VISIBLE);
//        } else {
//            positiveResultLayout.setVisibility(View.GONE);
//        }
        if (reportDetailInfo.getComprehensiveResultText() != null && !TextUtils.isEmpty(reportDetailInfo.getComprehensiveResultText())) {
            doctorReport.setText(reportDetailInfo.getComprehensiveResultText());
            doctorReportLayout.setVisibility(View.VISIBLE);
        } else {
            doctorReportLayout.setVisibility(View.GONE);
        }
        if (reportDetailInfo.getEyecareAdviceText() != null && !TextUtils.isEmpty(reportDetailInfo.getEyecareAdviceText())) {
            protectEyesAdvice.setText(reportDetailInfo.getEyecareAdviceText());
            protectEyesAdviceLayout.setVisibility(View.VISIBLE);
        } else {
            protectEyesAdviceLayout.setVisibility(View.GONE);
        }
        if (reportDetailInfo.getPositiveResultArray() != null && reportDetailInfo.getPositiveResultArray().size() > 0) {
            for (int i = 0; i < reportDetailInfo.getPositiveResultArray().size(); i++) {
                setPositiveResultArray(reportDetailInfo.getPositiveResultArray().get(i));
            }
            positiveResultArrayLayout.setVisibility(View.VISIBLE);
        } else {
            positiveResultArrayLayout.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(reportDetailInfo.getComprehensiveReportPdfImgUrl())) {
            if (reportDetailInfo.getComprehensiveReportPdfImgUrl().contains(",")) {
                String[] imgs = reportDetailInfo.getComprehensiveReportPdfImgUrl().split(",");
                List<String> reportImgs = new ArrayList<>();
                for (int i = 0; i < imgs.length; i++) {
                    reportImgs.add(imgs[i]);
                }
                setReportImgs(reportImgs);
            }
        } else if (TextUtils.equals(reportDetailInfo.getReportTemplateType(), "1") || reportDetailInfo.getReportTemplateType() == null) {
            if (!TextUtils.isEmpty(reportDetailInfo.getPdfImgUrl())) {
                List<String> reportImgs = new ArrayList<>();
                reportImgs.add(reportDetailInfo.getPdfImgUrl());
                setReportImgs(reportImgs);
            }
        }
        mPresenter.getRadiographScreenMarketActivityList(reportDetailInfo.getFamilyId());
    }

    @Override
    public void setReportImgs(List<String> reportImgs) {
        reportAdapter.setNewData(reportImgs);
    }

    @Override
    public void setRecommendService(List<ServiceCategoryInfo> categoryInfos) {
        mServiceAdapter.setNewData(categoryInfos);
    }

    @Override
    public void setLeftAndRightCheck(ReportInfo.LeftAndRightCheckBean leftAndRightCheckBean) {
        ReportDetailItemView reportDetailItemView = new ReportDetailItemView(this);
        reportDetailItemView.setInfo(leftAndRightCheckBean.getName(), leftAndRightCheckBean.getRightValue(), leftAndRightCheckBean.getLeftValue());
        reportInfoLayout1.addView(reportDetailItemView);
    }

    @Override
    public void setNoLeftAndRightCheck(ReportInfo.NoLeftAndRightCheckBean noLeftAndRightCheckBean) {
        ReportDetailItemTwoView reportDetailItemTwoView = new ReportDetailItemTwoView(this);
        reportDetailItemTwoView.setInfo(noLeftAndRightCheckBean.getName(), noLeftAndRightCheckBean.getValue());
        reportInfoLayout2.addView(reportDetailItemTwoView);
    }


    private void setPositiveResultArray(ReportInfo.PositiveResultArrayBean resultArrayBean) {
        ReportPositiveItemView reportPositiveItemView = new ReportPositiveItemView(this);
        reportPositiveItemView.setInfo(resultArrayBean.getName(), resultArrayBean.getValue());
        positiveResultArrayLayout.addView(reportPositiveItemView);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent data = new Intent(this, ImageViewActivity.class);
        data.putExtra("reportImg", (String) adapter.getData().get(position));
        startActivity(data);
    }
}
