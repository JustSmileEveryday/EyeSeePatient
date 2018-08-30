package lht.wangtong.gowin120.patient.ui.home.detection;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import javax.inject.Inject;

import lht.wangtong.gowin120.patient.base.BasePresenter;

/**
 * @author luoyc
 * @date 2018/3/19
 */

public class EyeDetectionPresenter extends BasePresenter<EyeDetectionContact.View> implements EyeDetectionContact.Presenter {

    @Inject
    public EyeDetectionPresenter() {

    }


    @Override
    public void initData(String mfamilyId) {
        String[] titles = {"视力测试", "色盲测试", "散光测试", "黄斑测试"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        VisionTestFragment fragment = VisionTestFragment.newInstance();
        fragment.setMfamilyId(mfamilyId);
        fragments.add(fragment);
        AchromatopsiaFragment fragment1 = AchromatopsiaFragment.newInstance();
        fragment1.setMfamilyId(mfamilyId);
        fragments.add(fragment1);
        AstigmatismFragment fragment3 = AstigmatismFragment.newInstance();
        fragment3.setmFamilyId(mfamilyId);
        fragments.add(fragment3);
        MacularFragment fragment2 = MacularFragment.newInstance();
        fragment2.setmFamilyId(mfamilyId);
        fragments.add(fragment2);
        mView.initAdapter(titles, fragments);
    }
}
