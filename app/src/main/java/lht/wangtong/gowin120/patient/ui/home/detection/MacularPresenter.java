package lht.wangtong.gowin120.patient.ui.home.detection;

import com.alibaba.android.arouter.launcher.ARouter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.EyeHealthData;
import lht.wangtong.gowin120.patient.bean.StepBean;

/**
 * @author luoyc
 */
public class MacularPresenter extends BasePresenter<MacularContract.View> implements MacularContract.Presenter {


    @Inject
    public MacularPresenter() {
    }

    @Override
    public void loadSteps() {
        List<StepBean> stepBeans = new ArrayList<>();
        int imgSize = 5;
        for (int i = 0; i < imgSize; i++) {
            StepBean stepBean = new StepBean(-1, false);
            Random random = new Random();
            int imgPage = random.nextInt(6) + 1;
            switch (imgPage) {
                case 1:
                    //
                    stepBean.setDrawableId(R.drawable.macular_1);
                    break;
                case 2:
                    //
                    stepBean.setDrawableId(R.drawable.macular_2);
                    break;
                case 3:
                    //
                    stepBean.setDrawableId(R.drawable.macular_3);
                    break;
                case 4:
                    //
                    stepBean.setDrawableId(R.drawable.macular_4);
                    break;
                case 5:
                    //
                    stepBean.setDrawableId(R.drawable.macular_5);
                    break;
                case 6:
                    //
                    stepBean.setDrawableId(R.drawable.macular_6);
                    break;
                default:
                    break;
            }
            stepBeans.add(stepBean);
        }
        mView.initSetpView(stepBeans);
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
                .withParcelable("mEyeHealthData", new EyeHealthData(5, dataValue, mfamilyId))
                .navigation();
        mView.getThisContext().finish();
    }
}
