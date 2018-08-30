package lht.wangtong.gowin120.patient.ui.home.detection;

import android.app.Activity;

import java.util.List;

import lht.wangtong.gowin120.patient.base.BaseContract;
import lht.wangtong.gowin120.patient.bean.VisionInfo;

/**
 * @author luoyc
 * @date 2018/3/19
 */

public interface VisionTestContact extends BaseContract {

    interface View extends BaseContract.BaseView {

        /**
         * 第一次设置视力
         *
         * @param visionInfo
         */
        void narrowVision(VisionInfo visionInfo);

        void setVisionData(List<VisionInfo> visionData);

        /**
         * 第二次设置视力
         *
         * @param visionInfo
         */
        void narrowVision2(VisionInfo visionInfo);

        void checkVision(int direction, int type);

        void startLeftDecetion();

        Activity getThisContext();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void initData();

        /**
         * 设置第二次测视力的图片
         *
         * @param visionInfo
         */
        void setdrawable2(int direction, VisionInfo visionInfo);

        void saveEyeHealthData(String dataValue, String familyId);

    }

}
