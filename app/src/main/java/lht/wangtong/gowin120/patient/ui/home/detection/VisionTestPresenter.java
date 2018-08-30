package lht.wangtong.gowin120.patient.ui.home.detection;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.EyeHealthData;
import lht.wangtong.gowin120.patient.bean.VisionInfo;

/**
 * @author luoyc
 * @date 2018/3/19
 */

public class VisionTestPresenter extends BasePresenter<VisionTestContact.View> implements VisionTestContact.Presenter {

    @Inject
    public VisionTestPresenter() {
    }

    @Override
    public void initData() {
        List<VisionInfo> visionInfos = new ArrayList<>();
        Random random = new Random();
        int visionPage = 11;
        for (int i = 0; i < visionPage; i++) {
            VisionInfo info = new VisionInfo();
            if (i == 0) {
                info.setScale(1);
            } else {
                info.setScale((float) (1 / (1.258925 * i)));
            }
            info.setName(String.valueOf(4.0 + 0.1 * i));
            int imgPage = random.nextInt(4);
            switch (imgPage) {
                case 0:
                    //上
                    info.setDrawableId(R.drawable.vision_up_img);
                    info.setDirection(0);
                    setdrawable2(0, info);
                    break;
                case 1:
                    //下
                    info.setDrawableId(R.drawable.vision_down_img);
                    info.setDirection(1);
                    setdrawable2(1, info);
                    break;
                case 2:
                    //左
                    info.setDrawableId(R.drawable.vision_left_img);
                    info.setDirection(2);
                    setdrawable2(2, info);
                    break;
                case 3:
                    //右
                    info.setDrawableId(R.drawable.vision_right_img);
                    info.setDirection(3);
                    setdrawable2(3, info);
                    break;
                default:
                    break;
            }
            visionInfos.add(info);
        }
        mView.setVisionData(visionInfos);
    }

    @Override
    public void setdrawable2(int direction, VisionInfo visionInfo) {
        Random random = new Random();
        int imgPage = random.nextInt(4);
        switch (imgPage) {
            case 0:
                //上
                if (direction == 0) {
                    setdrawable2(0, visionInfo);
                } else {
                    visionInfo.setDrawableId2(R.drawable.vision_up_img);
                    visionInfo.setDirection2(0);
                }
                break;
            case 1:
                //下
                if (direction == 1) {
                    setdrawable2(1, visionInfo);
                } else {
                    visionInfo.setDrawableId2(R.drawable.vision_down_img);
                    visionInfo.setDirection2(1);
                }
                break;
            case 2:
                //左
                if (direction == 2) {
                    setdrawable2(2, visionInfo);
                } else {
                    visionInfo.setDrawableId2(R.drawable.vision_left_img);
                    visionInfo.setDirection2(2);
                }
                break;
            case 3:
                //右
                if (direction == 3) {
                    setdrawable2(3, visionInfo);
                } else {
                    visionInfo.setDrawableId2(R.drawable.vision_right_img);
                    visionInfo.setDirection2(3);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void saveEyeHealthData(String dataValue, String familyId) {
        ARouter.getInstance().build("/home/detection/DetectionCompleteActivity")
                .withParcelable("mEyeHealthData", new EyeHealthData(1, dataValue, familyId))
                .navigation();
        mView.getThisContext().finish();
    }
}
