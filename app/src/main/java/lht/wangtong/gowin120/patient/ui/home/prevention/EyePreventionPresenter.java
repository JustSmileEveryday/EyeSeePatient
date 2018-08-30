package lht.wangtong.gowin120.patient.ui.home.prevention;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BasePresenter;
import lht.wangtong.gowin120.patient.bean.EyePreventionInfo;
import lht.wangtong.gowin120.patient.bean.EyeSignInCount;
import lht.wangtong.gowin120.patient.bean.EyeSignInInfo;
import lht.wangtong.gowin120.patient.bean.event.SignEvent;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.net.http.HttpUtil;

/**
 * @author luoyc
 * @date 2018/3/20
 */

public class EyePreventionPresenter extends BasePresenter<EyePreventionContact.View> implements EyePreventionContact.Presenter {

    @Inject
    public EyePreventionPresenter() {
    }

    @Override
    public void initData(String familyId) {
        loadEyePrevention();
        loadEyeSignInCount(familyId);
    }

    @Override
    public void loadEyeSignInCount(String familyId) {
        HttpUtil.getObject(Api.getSigninCount.mapClear()
                .addMap("familyId", familyId)
                .addBody(), EyeSignInCount.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    mView.setEyeSignInCount((EyeSignInCount) obj);
                    EventBus.getDefault().post(new SignEvent(true));
                }
            }
        });
    }

    @Override
    public void loadEyePrevention() {
        List<EyePreventionInfo> preventionInfos = new ArrayList<>();
        EyePreventionInfo preventionInfo1 = new EyePreventionInfo("步骤一 探天应穴", "以左右大拇指罗纹面接左右眉头下面的上眶角处。其他四指散开弯曲如弓状，支在前额上，按探面不要大。", R.drawable.prevention_img_1, -1);
        EyePreventionInfo preventionInfo2 = new EyePreventionInfo("步骤二 挤按睛明穴", "以左手或右手大拇指按鼻根部，先向下按、然后向上挤。", R.drawable.prevention_img_2, -1);
        EyePreventionInfo preventionInfo3 = new EyePreventionInfo("步骤三 揉四白穴", "先以左右食指与中指并拢，放在靠近鼻翼两侧，大拇指支撑在下腭骨凹陷处，然后放下中指，在面颊中央按揉。注意穴位不需移动，按揉面不要太大。", R.drawable.prevention_img_3, R.drawable.prevention_img_4);
        EyePreventionInfo preventionInfo4 = new EyePreventionInfo("步骤四 按太阳穴、轮刮眼眶(太阳、攒竹、鱼腰、丝竹空、瞳子骱、承泣等)", "拳起四指，以左右大拇指罗纹面按住太阳穴，以左右食指第二节内侧面轮刮眼眶上下一圈，上侧从眉头开始，到眉梢为止，下面从内眼角起至外眼角止，先上后下，轮刮上下一圈。", R.drawable.prevention_img_5, R.drawable.prevention_img_6);
        preventionInfos.add(preventionInfo1);
        preventionInfos.add(preventionInfo2);
        preventionInfos.add(preventionInfo3);
        preventionInfos.add(preventionInfo4);
        mView.setEyePrevention(preventionInfos);
    }

    @Override
    public void saveSignin(final String familyId) {
        HttpUtil.getObject(Api.saveSignin.mapClear()
                .addMap("familyId", familyId)
                .addBody(), String.class, new HttpUtil.objectCallback() {
            @Override
            public void result(boolean b, @Nullable Object obj) {
                if (b) {
                    ToastUtils.showShort("打卡成功");
                    loadEyeSignInCount(familyId);
                }
            }
        });
    }

    @Override
    public void loadEyeSignIn(String familyId, String signinDateStart, String signinDateEnd, int pageNo) {
        HttpUtil.getObjectListPage(Api.getSigninList.mapClear()
                .addPage(10, pageNo)
                .addMap("familyId", familyId)
                .addMap("signinDateStart", signinDateStart)
                .addMap("signinDateEnd", signinDateEnd)
                .addBody(), EyeSignInInfo.class, new HttpUtil.objectListPageCallback() {
            @Override
            public void resultPage(boolean b, @Nullable Object obj, int total) {
                if (b) {
                    List<EyeSignInInfo> inInfos = new ArrayList<>();
                    inInfos.addAll((Collection<? extends EyeSignInInfo>) obj);
                    mView.setEyeSignIn(inInfos, total);
                }
            }
        });
    }
}
