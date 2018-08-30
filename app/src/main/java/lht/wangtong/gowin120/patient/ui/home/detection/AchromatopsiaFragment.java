package lht.wangtong.gowin120.patient.ui.home.detection;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.StepBean;
import lht.wangtong.gowin120.patient.widget.HorizontalStepView;

/**
 * 色盲测试
 *
 * @author luoyc
 * @date 2018/3/19
 */

public class AchromatopsiaFragment extends BaseFragment<AchromatopsiaPresenter> implements AchromatopsiaContact.View, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.step_view)
    HorizontalStepView mHorizontalStepView;
    @BindView(R.id.sure_btn)
    TextView mSureBtn;
    @BindView(R.id.not_see_btn)
    TextView mCancleBtn;
    @BindView(R.id.achromatopsia)
    ImageView mAchromatopsia;
    @BindView(R.id.achromatopsia_text_1)
    RadioButton mAchromatopsiaText1;
    @BindView(R.id.achromatopsia_text_2)
    RadioButton mAchromatopsiaText2;
    @BindView(R.id.achromatopsia_text_3)
    RadioButton mAchromatopsiaText3;
    @BindView(R.id.choose_text_layout)
    RadioGroup mRadioGroup;
    private List<StepBean> stepBeans;
    private int mPositon = 0;
    private String mfamilyId = "";

    public static AchromatopsiaFragment newInstance() {
        return new AchromatopsiaFragment();
    }

    public void setMfamilyId(String mfamilyId) {
        this.mfamilyId = mfamilyId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_achromatopsia_test;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        mRadioGroup.setOnCheckedChangeListener(this);
        mPresenter.loadSteps();
    }

    @Override
    public void initSetpView(List<StepBean> stepBeanList) {
        stepBeans = stepBeanList;
        mHorizontalStepView
                .setStepViewTexts(stepBeans)//总步骤
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.stepsviewindicator_finish))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.stepsviewindicator_uncompleted))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.stepsviewindicator_finish))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.stepsviewindicator_uncompleted))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted_img))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.default_img))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.wrong_img));//设置StepsViewIndicator AttentionIcon
        setStepView(0);
    }

    @Override
    public void setStepView(int positon) {
        mRadioGroup.clearCheck();
        mSureBtn.setClickable(false);
        mSureBtn.setBackgroundResource(R.drawable.green_btn_bg_5_unclick);
        mPositon = positon;
        mAchromatopsia.setBackground(getActivity().getResources().getDrawable(stepBeans.get(positon).getDrawableId()));
        mAchromatopsiaText1.setText(stepBeans.get(positon).getChooseText1());
        mAchromatopsiaText2.setText(stepBeans.get(positon).getChooseText2());
        mAchromatopsiaText3.setText(stepBeans.get(positon).getChooseText3());
    }

    @Override
    public void checkData() {
        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.achromatopsia_text_1:
                if (TextUtils.equals(mAchromatopsiaText1.getText().toString(), stepBeans.get(mPositon).getRightText())) {
                    stepBeans.get(mPositon).setRight(true);
                } else {
                    stepBeans.get(mPositon).setRight(false);
                }
                break;
            case R.id.achromatopsia_text_2:
                if (TextUtils.equals(mAchromatopsiaText2.getText().toString(), stepBeans.get(mPositon).getRightText())) {
                    stepBeans.get(mPositon).setRight(true);
                } else {
                    stepBeans.get(mPositon).setRight(false);
                }
                break;
            case R.id.achromatopsia_text_3:
                if (TextUtils.equals(mAchromatopsiaText3.getText().toString(), stepBeans.get(mPositon).getRightText())) {
                    stepBeans.get(mPositon).setRight(true);
                } else {
                    stepBeans.get(mPositon).setRight(false);
                }
                break;
            default:
                break;
        }
        if (stepBeans.get(mPositon).isRight()) {
            stepBeans.get(mPositon).setState(1);
        } else {
            stepBeans.get(mPositon).setState(0);
        }
        mHorizontalStepView.setStepViewTexts(stepBeans);
        mHorizontalStepView.ondrawIndicator();
        if (mPositon < stepBeans.size() - 1) {
            setStepView(mPositon + 1);
        } else {
            mPresenter.saveEyeHealthData(stepBeans, mfamilyId);
        }
    }

    @Override
    public void notSeeData() {
        stepBeans.get(mPositon).setRight(false);
        stepBeans.get(mPositon).setState(0);
        mHorizontalStepView.setStepViewTexts(stepBeans);
        mHorizontalStepView.ondrawIndicator();
        if (mPositon < stepBeans.size() - 1) {
            setStepView(mPositon + 1);
        } else {
            mPresenter.saveEyeHealthData(stepBeans, mfamilyId);
        }
    }

    @Override
    public Activity getThisContext() {
        return getActivity();
    }


    @OnClick({R.id.not_see_btn, R.id.sure_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.not_see_btn:
                notSeeData();
                break;
            case R.id.sure_btn:
                checkData();
                break;
            default:
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        mSureBtn.setClickable(true);
        mSureBtn.setBackgroundResource(R.drawable.green_btn_bg_5);
    }
}
