package lht.wangtong.gowin120.patient.ui.mine.setting.about;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.base.BaseActivity;
import lht.wangtong.gowin120.patient.bean.HospitalDetailInfo;

/**
 * 关于小艾
 * @author luoyc
 * @date 2018/3/26
 */
@Route(path = "/setting/about/AboutActivity")
public class AboutActivity extends BaseActivity<AboutPresenter> implements AboutContact.View{
    @BindView(R.id.about)
    TextView about;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.loadAbout();
    }


    @Override
    public void setAbout(HospitalDetailInfo detailInfo) {
        about.setText(detailInfo.getIntroduction());
    }
}
