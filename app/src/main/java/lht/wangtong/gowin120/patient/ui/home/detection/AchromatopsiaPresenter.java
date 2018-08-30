package lht.wangtong.gowin120.patient.ui.home.detection;

import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.EyeHealthData;
import lht.wangtong.gowin120.patient.bean.StepBean;

/**
 * @author luoyc
 * @date 2018/3/19
 */

public class AchromatopsiaPresenter extends BasePresenter<AchromatopsiaContact.View> implements AchromatopsiaContact.Presenter {

    @Inject
    public AchromatopsiaPresenter() {
    }

    @Override
    public void loadSteps() {
        List<StepBean> stepBeans = new ArrayList<>();
        int imgSize = 5;
        for (int i = 0; i < imgSize; i++) {
            StepBean stepBean = new StepBean("", -1, false);
            Random random = new Random();
            int imgPage = random.nextInt(6) + 1;
            switch (imgPage) {
                case 1:
                    // 5
                    List<String> strings = new ArrayList<>();
                    strings.add("5");
                    strings.add("8");
                    strings.add("6");
                    loadStepsDetail(stepBean, R.drawable.achromatopsia_img_1, strings, "5");
                    break;
                case 2:
                    //
                    List<String> strings1 = new ArrayList<>();
                    strings1.add("5");
                    strings1.add("7");
                    strings1.add("2");
                    loadStepsDetail(stepBean, R.drawable.achromatopsia_img_2, strings1, "2");
                    break;
                case 3:
                    //
                    List<String> strings2 = new ArrayList<>();
                    strings2.add("0");
                    strings2.add("8");
                    strings2.add("6");
                    loadStepsDetail(stepBean, R.drawable.achromatopsia_img_3, strings2, "8");
                    break;
                case 4:
                    //
                    List<String> strings3 = new ArrayList<>();
                    strings3.add("70");
                    strings3.add("16");
                    strings3.add("10");
                    loadStepsDetail(stepBean, R.drawable.achromatopsia_img_4, strings3, "10");
                    break;
                case 5:
                    //
                    List<String> strings4 = new ArrayList<>();
                    strings4.add("55");
                    strings4.add("25");
                    strings4.add("26");
                    loadStepsDetail(stepBean, R.drawable.achromatopsia_img_5, strings4, "25");
                    break;
                case 6:
                    //
                    List<String> strings5 = new ArrayList<>();
                    strings5.add("71");
                    strings5.add("14");
                    strings5.add("74");
                    loadStepsDetail(stepBean, R.drawable.achromatopsia_img_6, strings5, "74");
                    break;
                default:
                    break;
            }
            stepBeans.add(stepBean);
        }
        mView.initSetpView(stepBeans);
    }

    @Override
    public void loadStepsDetail(StepBean stepBean, int drawalbe, List<String> strings, String rightText) {
        stepBean.setDrawableId(drawalbe);
        //随机打乱选择文字list
        Collections.shuffle(strings);
        stepBean.setChooseText1(strings.get(0));
        stepBean.setChooseText2(strings.get(1));
        stepBean.setChooseText3(strings.get(2));
        stepBean.setRightText(rightText);
    }

    @Override
    public void saveEyeHealthData(List<StepBean> stepBeans, String mfamilyId) {
        int chooseRight = 0;
        for (int i = 0; i < stepBeans.size(); i++) {
            if (stepBeans.get(i).isRight()) {
                chooseRight = chooseRight + 1;
            }
        }
        float chooseWrong = stepBeans.size() - chooseRight;
        float dataValueNum = chooseWrong / stepBeans.size();
        //格式化小数，.后跟几个零代表几位小数
        DecimalFormat df = new DecimalFormat("0.00");
        String dataValue = df.format(dataValueNum);
        ARouter.getInstance().build("/home/detection/DetectionCompleteActivity")
                .withParcelable("mEyeHealthData", new EyeHealthData(3, dataValue, mfamilyId))
                .navigation();
        mView.getThisContext().finish();
    }
}
