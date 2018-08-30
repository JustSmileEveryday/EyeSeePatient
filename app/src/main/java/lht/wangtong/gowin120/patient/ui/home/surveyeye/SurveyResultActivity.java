package lht.wangtong.gowin120.patient.ui.home.surveyeye;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.EyeAgeProblemResult;

/**
 * 测眼龄结果
 *
 * @author luoyc
 */
@Route(path = "/home/surveyeye/SurveyResultActivity")
public class SurveyResultActivity extends BaseActivity<SurveyResultPresenter> implements SurveyResultContact.View {


    @BindView(R.id.survey_result)
    TextView surveyResult;
    @BindView(R.id.survey_advice)
    TextView surveyAdvice;
    @Autowired
    String eyeAgeProblemIds;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_survey_result;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.getEyeAgeProblemResult(eyeAgeProblemIds);
    }


    @OnClick({R.id.back_home_btn, R.id.once_more_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_home_btn:
                finish();
                break;
            case R.id.once_more_btn:
                ARouter.getInstance()
                        .build("/home/surveyeye/SurveyEyesActivity")
                        .navigation();
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void setResult(EyeAgeProblemResult resultInfo) {
        surveyResult.setText(resultInfo.getResult());
        surveyAdvice.setText(resultInfo.getAdvice());
    }
}
