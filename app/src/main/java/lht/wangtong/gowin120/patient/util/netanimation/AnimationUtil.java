package lht.wangtong.gowin120.patient.util.netanimation;

import lht.wangtong.gowin120.patient.ui.splash.SplashActivity;
import lht.wangtong.gowin120.patient.util.ActivityCollector;


/**
 * @author luoyc
 * @date 2016/11/11 0011
 */
public class AnimationUtil {
    public static ProgressDialog singleProgress = null;

    public static ProgressDialog getProgress() {
        if (ActivityCollector.activityStack.size() == 0) {
            singleProgress = null;
            return singleProgress;
        }
        if (singleProgress == null) {
            if (ActivityCollector.currentActivity() instanceof SplashActivity) {
                return null;
            }
            try {
                singleProgress = new ProgressDialog(ActivityCollector.currentActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return singleProgress;
    }
}
