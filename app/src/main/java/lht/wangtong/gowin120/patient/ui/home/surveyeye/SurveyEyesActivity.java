package lht.wangtong.gowin120.patient.ui.home.surveyeye;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.EyeAgeProblemInfo;
import lht.wangtong.gowin120.patient.db.CommonCdInfo;
import lht.wangtong.gowin120.patient.util.CommonInfoUtils;

/**
 * 测眼龄
 *
 * @author luoyc
 */
@Route(path = "/home/surveyeye/SurveyEyesActivity")
public class SurveyEyesActivity extends BaseActivity<SurvEyeyesPresenter> implements SurveyEyesContact.View, OnDismissListener {

    @BindView(R.id.question_view)
    TextView questionView;
    private List<String> textInfos;
    private OptionsPickerView pvOptions;
    private List<CommonCdInfo> cdInfoList;
    private List<EyeAgeProblemInfo> eyeAgeProblemInfos;
    private int position = 0;
    private boolean isSure = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_survey_eyes;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        textInfos = new ArrayList<>();
        cdInfoList = new ArrayList<>();
        cdInfoList = CommonInfoUtils.getCommonCdInfo("CrowdAgeGroup");
        for (CommonCdInfo info : cdInfoList) {
            textInfos.add(info.getNameZh());
        }
        showAge();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setEyeAgeProblem(List<EyeAgeProblemInfo> eyeAgeProblemInfoList) {
        eyeAgeProblemInfos = new ArrayList<>();
        eyeAgeProblemInfos.addAll(eyeAgeProblemInfoList);
        if (eyeAgeProblemInfos.size() > 0) {
            questionView.setText((position + 1) + "、" + eyeAgeProblemInfos.get(0).getProblem());
        }
    }

    @Override
    public void showAge() {
        if (pvOptions == null) {
            pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    isSure = true;
                    for (int i = 0; i < cdInfoList.size(); i++) {
                        if (TextUtils.equals(textInfos.get(options1), cdInfoList.get(i).getNameZh())) {
                            mPresenter.getEyeAgeProblem(cdInfoList.get(i).getCode());
                        }
                    }

                }
            })
                    .setSubmitText("确定")//确定按钮文字
                    .setCancelText("取消")//取消按钮文字
                    .setSubCalSize(18)//确定和取消文字大小
                    .setContentTextSize(20)//滚轮文字大小
                    .isCenterLabel(true)
                    .setLineSpacingMultiplier(2f)//滚轮间距设置（1.2-2.0倍，此为文字高度的间距倍数）
                    .setSubmitColor(getResources().getColor(R.color.time_text_color))//确定按钮文字颜色
                    .setCancelColor(getResources().getColor(R.color.time_text_color))//取消按钮文字颜色
                    .setOutSideCancelable(false)
                    .build();
        }
        pvOptions.setOnDismissListener(this);
        pvOptions.setPicker(textInfos);
        pvOptions.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setProblem() {
        position++;
        if (position < eyeAgeProblemInfos.size()) {
            questionView.setText((position + 1) + "、" + eyeAgeProblemInfos.get(position).getProblem());
        } else {
            //测试完毕
            String eyeAgeProblemIds = "";
            for (int i = 0; i < eyeAgeProblemInfos.size(); i++) {
                if (eyeAgeProblemInfos.get(i).isTrue()) {
                    eyeAgeProblemIds = eyeAgeProblemInfos.get(i).getEyeAgeProblemId() + ",";
                }
            }
            if (TextUtils.isEmpty(eyeAgeProblemIds) && eyeAgeProblemIds.contains(",")) {
                eyeAgeProblemIds.substring(0, eyeAgeProblemIds.length() - 2);
            }
            ARouter.getInstance()
                    .build("/home/surveyeye/SurveyResultActivity")
                    .withString("eyeAgeProblemIds", eyeAgeProblemIds)
                    .navigation();
            finish();
        }
    }

    @OnClick({R.id.sure_btn, R.id.cancel_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sure_btn:
                //是
                if (eyeAgeProblemInfos != null && eyeAgeProblemInfos.size() > 0) {
                    eyeAgeProblemInfos.get(position).setTrue(true);
                    setProblem();
                }
                break;
            case R.id.cancel_btn:
                //否
                if (eyeAgeProblemInfos != null && eyeAgeProblemInfos.size() > 0) {
                    eyeAgeProblemInfos.get(position).setTrue(false);
                    setProblem();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDismiss(Object o) {
        if (!isSure) {
            finish();
        }
    }
}
