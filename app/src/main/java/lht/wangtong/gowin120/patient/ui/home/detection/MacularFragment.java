package lht.wangtong.gowin120.patient.ui.home.detection;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseFragment;
import lht.wangtong.gowin120.patient.bean.StepBean;
import lht.wangtong.gowin120.patient.widget.HorizontalStepView;

/**
 * 黄斑检查
 *
 * @author luoyc
 */
public class MacularFragment extends BaseFragment<MacularPresenter> implements MacularContract.View {
    @BindView(R.id.step_view)
    HorizontalStepView mHorizontalStepView;
    @BindView(R.id.eye_img)
    ImageView mMacularImg;
    private List<StepBean> stepBeans;
    private String mFamilyId = "";
    private int mPositon = 0;

    public static MacularFragment newInstance() {
        return new MacularFragment();
    }

    public void setmFamilyId(String mFamilyId) {
        this.mFamilyId = mFamilyId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_macular;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
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
        mPositon = positon;
        mMacularImg.setBackground(getActivity().getResources().getDrawable(stepBeans.get(positon).getDrawableId()));
    }

    @Override
    public void checkData(boolean isRight) {
        stepBeans.get(mPositon).setRight(isRight);
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
            mPresenter.saveEyeHealthData(stepBeans, mFamilyId);
        }
    }

    @Override
    public Activity getThisContext() {
        return getActivity();
    }

    @OnClick({R.id.no_problem_btn, R.id.problem_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no_problem_btn:
                checkData(true);
                break;
            case R.id.problem_btn:
                checkData(false);
                break;
            default:
                break;
        }
    }
}
